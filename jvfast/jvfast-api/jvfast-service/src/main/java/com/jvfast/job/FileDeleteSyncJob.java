package com.jvfast.job;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jvfast.common.config.async.AsyncTask;
import com.jvfast.common.constant.NotificationConst;
import com.jvfast.common.enums.BusinessStatusEnum;
import com.jvfast.common.param.IdParam;
import com.jvfast.common.util.DateUtil;
import com.jvfast.common.util.SpringUtil;
import com.jvfast.job.util.JobUtil;
import com.jvfast.module.sys.model.entity.SysFileUpload;
import com.jvfast.module.sys.service.SysFileUploadService;
import com.jvfast.quartz.entity.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class FileDeleteSyncJob extends QuartzJob {

    public static final int EXPIRED_DAYS = -7;
    /**
     * 需要发送微信通常的微信用户
     */
    private static final NotificationConst.DefaultOpenIds[] OPEN_IDS = {NotificationConst.DefaultOpenIds.WALTER};

    /**
     * 定时清理没有使用的图片文件
     *
     * @param context
     * @return void
     * @author Walter Hu
     * @date 2019/11/21
     * @since 1.8
     */
    @Override
    public void run(JobExecutionContext context) {
        // 获取任务信息
        JobKey jobDetail = context.getJobDetail().getKey();
        String jobName = jobDetail.getName();
        // 获取对应的bean
        AsyncTask asyncTask = SpringUtil.getBean(AsyncTask.class);
        // 查询所有的文件没有激活的
        AtomicInteger totalNum = new AtomicInteger(0);
        AtomicInteger successNum = new AtomicInteger(0);
        AtomicInteger failNum = new AtomicInteger(0);
        LocalDateTime lastExpiredDays = DateUtil.plusDays(LocalDateTime.now(), EXPIRED_DAYS);
        long startTime = System.currentTimeMillis();
        try {
            SysFileUploadService fileUploadService = SpringUtil.getBean(SysFileUploadService.class);
            log.info("准备执行删除文件操作，过期七天时间设置: {}  ", lastExpiredDays.toString());
            LambdaQueryWrapper<SysFileUpload> queryWrapper = Wrappers.<SysFileUpload>lambdaQuery()
                    .select(SysFileUpload::getId, SysFileUpload::getFilePath, SysFileUpload::getUrl)
                    .eq(SysFileUpload::getStatus, false)
                    .isNull(SysFileUpload::getLastBatchTime)
                    // 更新失败的
                    .or(i -> i.eq(SysFileUpload::getLastBatchStatus, false)
                            .isNotNull(SysFileUpload::getLastBatchTime))
                    .le(SysFileUpload::getUpdateTime, lastExpiredDays);
            List<SysFileUpload> expiredFileUploads = fileUploadService.list(queryWrapper);
            int expiredSize = expiredFileUploads.size();
            totalNum.set(expiredSize);
            log.info("查询到满足条件可删除的文件个数是: {}", expiredSize);
            asyncTask.executeTask(() -> {
                for (SysFileUpload expiredFileUpload : expiredFileUploads) {
                    Long id = expiredFileUpload.getId();
                    IdParam idParam = new IdParam(id);
                    log.info("删除文件,记录ID: {}", id);
                    boolean removedSuccess = fileUploadService.removeFileById(idParam);
                    SysFileUpload fileUpload = new SysFileUpload();
                    fileUpload.setId(id);
                    fileUpload.setLastBatchTime(LocalDateTime.now());
                    if (removedSuccess) {
                        fileUpload.setLastBatchStatus(BusinessStatusEnum.SUCCESS.ordinal());
                        successNum.incrementAndGet();
                        log.info("删除文件成功,更新该条文件记录为： {}", fileUpload.toString());
                    } else {
                        fileUpload.setLastBatchStatus(BusinessStatusEnum.FAIL.ordinal());
                        failNum.incrementAndGet();
                        log.info("删除文件失败,更新该条文件记录为： {}", fileUpload.toString());
                    }
                    boolean result = fileUploadService.updateById(fileUpload);
                    log.info("更新文件执行记录结束，更新结果是: {}", result);
                }
            });

        } catch (Exception e) {
            log.error("任务执行异常，任务名称：{},异常信息: {}", jobName, e.getMessage());
        } finally {
            // 发送公众号消息
            String miniPath = "pages/index/index";
            String title = StrUtil.format("批处理{}完成,感谢订阅！", jobName);
            String proposer = "开发组";
            String department = "开发部门";
            String content = StrUtil.format("批处理的上传文件个数: {},处理成功: {},处理失败: {}", totalNum, successNum, failNum);
            String remark = "获取更多资讯，点击进入小程序查看";
            JobUtil.sendMPNotificaiton(startTime, jobName, OPEN_IDS, miniPath, title, proposer, department, content, remark);
        }
    }
}
