package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysNotification;
import com.jvfast.module.sys.model.param.SysNotificationQueryPageParam;
import com.jvfast.module.sys.model.vo.SysNotificationQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息) Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2020-01-22
 */
@Mapper
public interface SysNotificationMapper extends BaseMapper<SysNotification> {

    /**
     * <p>
     * sys_notification分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2020-01-22
     */
    List<SysNotificationQueryVo> pageSysNotification(IPage page, @Param("query") SysNotificationQueryPageParam queryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
