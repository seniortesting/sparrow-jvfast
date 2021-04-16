package com.jvfast.module.monitor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.module.monitor.model.entity.QuartzJobLog;
import com.jvfast.module.monitor.service.QuartzJobLogService;
import com.jvfast.module.sys.mapper.QuartzJobLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * <p>
 * Quartz任务的执行记录 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-21
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class QuartzJobLogServiceImpl extends ServiceImpl<QuartzJobLogMapper, QuartzJobLog> implements QuartzJobLogService {
    /***************************** 以下为扩展接口 ******************************************************/
}
