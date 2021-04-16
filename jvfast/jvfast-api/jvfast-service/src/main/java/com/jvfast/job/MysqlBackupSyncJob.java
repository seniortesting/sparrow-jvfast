package com.jvfast.job;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import com.jvfast.common.constant.NotificationConst;
import com.jvfast.common.util.SpringUtil;
import com.jvfast.job.util.JobUtil;
import com.jvfast.quartz.entity.QuartzJob;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.connection.ShardingConnection;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author Walter
 */
@Slf4j
@Component
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class MysqlBackupSyncJob extends QuartzJob {

    private static final NotificationConst.DefaultOpenIds[] OPEN_IDS = {NotificationConst.DefaultOpenIds.WALTER};

    /**
     * 每天定时备份所有的数据库表
     * 命令参考：mysqldump --all-databases
     *
     * @param context
     * @throws JobExecutionException
     */
    @Override
    protected void run(JobExecutionContext context) throws JobExecutionException {
        // 获取任务信息
        JobKey jobDetail = context.getJobDetail().getKey();
        String jobName = jobDetail.getName();

        DataSource dataSource = SpringUtil.getBean(DataSource.class);

        // 命令行执行：
        // 1. mysqldump --all-databases --default-character-set=utf8 --host=127.0.0.1 --port=1876 --user='s' --password='test' --result-file=/opt/backup/mysql-20200226.sql
        // 2. mysqldump --databases test --default-character-set=utf8 --host=127.0.0.1 --port=1876 --user='s' --password='test' --result-file=/opt/backup/mysql-20200226.sql
        long startTime = System.currentTimeMillis();
        String result = "";
        String database = "";
        try {
            ShardingConnection connection = (ShardingConnection) dataSource.getConnection();
            Map<String, DataSource> dataSourceMap = connection.getDataSourceMap();
            // 获取master的机器
            HikariDataSource masterDataSource = (HikariDataSource) dataSourceMap.get("ds0master");
            String username = masterDataSource.getUsername();
            String password = masterDataSource.getPassword();
            String jdbcUrl = masterDataSource.getJdbcUrl();
            // 获取对应的数据库名称，机器和端口
            String cleanURI = jdbcUrl.substring(5);
            URI uri = URI.create(cleanURI);
            String host = uri.getHost();
            int port = uri.getPort();
            database = uri.getPath().substring(1);
            String nowDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
            String outPath = StrUtil.format("/opt/backup/{}-{}.sql", database, nowDate);
            FileUtil.mkParentDirs(outPath);
            String command = StrUtil.format("mysqldump --databases {} --default-character-set=utf8 --host={} --port={} --user='{}' --password='{}' --result-file={}",
                    database, host, port, username, password, outPath);
            log.info("mysql备份，命令是: {}",command);
            result = RuntimeUtil.execForStr(command);
        } catch (Exception e) {
            result = e.getMessage();
            log.error("执行mysql定时备份任务报错 ", e);
        } finally {
            // 发送邮件通知
            String miniPath = "pages/index/index";
            String title = StrUtil.format("批处理{}完成,备份数据库: {}", jobName, database);
            String proposer = "开发组";
            String department = "开发部门";
            String content = StrUtil.format("mysql备份完成，结果:{}", result);
            String remark = "获取更多资讯，点击进入小程序查看";
            JobUtil.sendMPNotificaiton(startTime, jobName, OPEN_IDS, miniPath, title, proposer, department, content, remark);
        }
    }
}
