package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysNotification;
import com.jvfast.module.sys.model.param.SysNotificationAddParam;
import com.jvfast.module.sys.model.param.SysNotificationUpdateParam;
import com.jvfast.module.sys.model.vo.SysNotificationQueryVo;
import com.jvfast.notification.common.entity.NotificationVo;
import com.jvfast.notification.common.enums.NotificationTypeEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysNotification对象转换接口
 */
@Mapper(uses = {NotificationTypeEnum.class})
//@Mapper(componentModel = "spring")
public interface SysNotificationConverter {

    SysNotificationConverter INSTANCE = Mappers.getMapper(SysNotificationConverter.class);

    /**
     * 发送消息转换
     *
     * @param notificationVo
     * @return
     */
    @Mappings({
            @Mapping(source = "notificationType.code", target = "notificationType")
    })
    SysNotification convertNotification(NotificationVo notificationVo);

    /**
     * 添加操作
     * SysNotificationAddParam对象转换为SysNotification
     *
     * @param sysNotificationAddParam
     * @return
     */
    @Mappings({
            @Mapping(source = "notificationType.code", target = "notificationType")
    })
    SysNotification convertSysNotificationAddParam(SysNotificationAddParam sysNotificationAddParam);

    /**
     * 修改操作
     * SysNotificationUpdateParam对象转换为SysNotification
     *
     * @param sysNotificationUpdateParam
     * @return
     */
    @Mappings({
            @Mapping(source = "notificationType.code", target = "notificationType")
    })
    SysNotification convertSysNotificationUpdateParam(SysNotificationUpdateParam sysNotificationUpdateParam);

    /**
     * 查询列表转化实体
     * SysNotification对象转换为SysNotificationQueryVo
     *
     * @param sysNotification
     * @return
     */
    List<SysNotificationQueryVo> convertSysNotificationList(List<SysNotification> sysNotification);

    /**
     * 查询分页操作
     * SysNotification对象转换为SysNotificationQueryVo
     *
     * @param sysNotification
     * @return
     */
    SysNotificationQueryVo convertSysNotification(SysNotification sysNotification);


}
