package com.jvfast.common.config.mybatis;

import com.jvfast.common.config.JVFastCommonProperties;
import com.jvfast.common.exception.DemoModeException;
import com.jvfast.common.exception.GlobalExceptionResponseBodyAdvice;
import com.jvfast.common.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Properties;

/**
 * @description: 演示模式拦截器
 * 参考说明: https://blog.csdn.net/wuyuxing24/article/details/89343951
 * @project: jvfast
 * @author: Walter Hu
 * @create: 2020-01-07 21:36
 **/
@Slf4j
@Component
@ConditionalOnProperty(prefix = "jvfast", name = "demo-enabled", havingValue = "true")
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class DemoInteceptor implements Interceptor {

    // 排除对应的日志记录表
    private static final String[] WHITELIST_TABLES = {"monitor_log", "monitor_login_history", "qrtz_job_log", "sys_user", "sys_feedback", "sys_user_wx", "demo_test", "porn_maomi"};

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Boolean demoEnabled = SpringUtil.getBean(JVFastCommonProperties.class).getDemoEnabled();
        if (demoEnabled) {
            Object[] args = invocation.getArgs();
            MappedStatement mappedStatement = (MappedStatement) args[0];
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
            // 如果是查询操作直接放行
            log.info("查询语句类型: {}，select语句直接放行", sqlCommandType);
            if (SqlCommandType.SELECT.equals(sqlCommandType)) {
                return invocation.proceed();
            }
            // 如果是其他的sql操作,比如,插入,更新,删除等
            Object parameter = null;
            // 获取参数，if语句成立，表示sql语句有参数，参数格式是map形式
            if (args.length > 1) {
                parameter = invocation.getArgs()[1];
            }
            // String sqlId = mappedStatement.getId(); // 获取到节点的id,即sql语句的id
            // BoundSql就是封装myBatis最终产生的sql类
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            String sql = boundSql.getSql();
            boolean matchedTable = Arrays.stream(WHITELIST_TABLES).anyMatch(table -> sql.toLowerCase().contains(table));
            log.info("查询语句是否是白名单表: {}", matchedTable);
            if (matchedTable) {
                return invocation.proceed();
            } else {
                throw new DemoModeException(GlobalExceptionResponseBodyAdvice.DEMO_ERROR_MSG);
            }
        } else {
            return invocation.proceed();
        }
    }

    @Override
    public Object plugin(Object target) {
        // 当目标类是Executor类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
