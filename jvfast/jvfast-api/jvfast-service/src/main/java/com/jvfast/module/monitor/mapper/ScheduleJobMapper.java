package com.jvfast.module.monitor.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.monitor.model.param.ScheduleJobQueryPageParam;
import com.jvfast.module.monitor.model.vo.ScheduleJobVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScheduleJobMapper {
    /**
     * 功能描述: 获取任务
     *
     * @Param:
     * @return:
     * @Author: Walter Hu
     * @Date: 2018/12/29 0029
     */
    IPage<ScheduleJobVo> getJobs(IPage page, @Param("query") ScheduleJobQueryPageParam queryPageParam);
}
