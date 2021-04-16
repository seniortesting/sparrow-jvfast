package com.jvfast.job.util;

import com.jvfast.common.constant.NotificationConst;
import com.jvfast.common.util.NotificationUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class JobUtil {

    /**
     * 发送批处理微信公众号通知
     * @param startTime
     * @param jobName
     * @param openIds
     * @param miniPath
     * @param title
     * @param proposer
     * @param department
     * @param content
     * @param remark
     */
    public static void sendMPNotificaiton(long startTime,
                                          String jobName,
                                          NotificationConst.DefaultOpenIds[] openIds,
                                          String miniPath,
                                          String title,
                                          String proposer,
                                          String department,
                                          String content,
                                          String remark) {
        long runTime = System.currentTimeMillis() - startTime;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(runTime);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(runTime);
        String elapseTime = String.format("%d分钟, %d秒",
                minutes,
                seconds - TimeUnit.MINUTES.toSeconds(minutes));
        log.info("批处理任务执行完毕，任务名称：{} 总共耗时{}", jobName, elapseTime);
        // 发送公众号消息
        for (NotificationConst.DefaultOpenIds defaultOpenIds : openIds) {
            String userOpenId = defaultOpenIds.getOpenId();
            NotificationUtil.sendMpReportNotification(userOpenId, miniPath, title, proposer, elapseTime, department, content, remark);
        }
    }
}
