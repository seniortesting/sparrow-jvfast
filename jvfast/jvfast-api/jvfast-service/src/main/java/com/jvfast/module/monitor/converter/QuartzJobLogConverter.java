package com.jvfast.module.monitor.converter;

import com.jvfast.quartz.entity.QuartzJobLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MonitorLoginHistory对象转换接口
 */
@Mapper
//@Mapper(componentModel = "spring")
public interface QuartzJobLogConverter {

    QuartzJobLogConverter INSTANCE = Mappers.getMapper(QuartzJobLogConverter.class);

    /**
     * 添加操作
     * JobLog对象转换为QuartzJobLog
     *
     * @param quartzJobLog
     * @return
     */
    com.jvfast.module.monitor.model.entity.QuartzJobLog convertJobLog(QuartzJobLog quartzJobLog);
}
