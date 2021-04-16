package com.jvfast.common.config.mybatis;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MybatisConfig {

    /**
     * 分页拦截器
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
         paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
         paginationInterceptor.setLimit(500);
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 攻击 SQL 阻断解析器、加入解析链作用！阻止恶意的全表更新删除
        sqlParserList.add(new BlockAttackSqlParser());

        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;

    }
//    /**
//     * SQL执行效率插件
//     * 新版移除
    //     */
//    @Bean
//    @Profile({"dev", "test"})// 设置 dev test 环境开启
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        // 设置最大的
//        Properties properties = new Properties();
//        // maxTime SQL 执行最大时长，超过自动停止运行，有助于发现问题。
//        properties.setProperty("maxTime", "5000");
//        properties.setProperty("format", "true");
//        properties.setProperty("writeInLog", "true");
//        performanceInterceptor.setProperties(properties);
//        return performanceInterceptor;
//    }

    @Bean
    @Profile({"dev", "test"})// 设置 dev test 环境开启
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        // 更新数据的时候用到了乐观锁概念
//        当要更新一条记录的时候，希望这条记录没有被别人更新
        OptimisticLockerInterceptor optimisticLockerInterceptor = new OptimisticLockerInterceptor();
        return optimisticLockerInterceptor;
    }
}
