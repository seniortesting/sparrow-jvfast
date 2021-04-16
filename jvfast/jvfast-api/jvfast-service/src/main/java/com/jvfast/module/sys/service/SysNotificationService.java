package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysNotification;
import com.jvfast.module.sys.model.param.SysNotificationAddParam;
import com.jvfast.module.sys.model.param.SysNotificationQueryPageParam;
import com.jvfast.module.sys.model.param.SysNotificationUpdateParam;
import com.jvfast.module.sys.model.vo.SysNotificationQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息) 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2020-01-22
 */
public interface SysNotificationService extends IService<SysNotification> {

    /**
     * 添加SysNotification
     *
     * @param sysNotification
     * @return
     */
    boolean saveSysNotification(SysNotificationAddParam sysNotification);

    /**
     * 通过id更新SysNotification
     *
     * @param sysNotification
     * @return
     */
    boolean updateSysNotificationById(SysNotificationUpdateParam sysNotification);

    /**
     * 通过id删除SysNotification
     *
     * @param idParam
     * @return
     */
    boolean removeSysNotificationById(IdParam idParam);

    /**
     * 通过id批量删除SysNotification
     *
     * @param idBatchParam
     * @return
     */
    boolean removeSysNotificationByIds(IdBatchParam idBatchParam);

    /**
     * 通过id查询SysNotification
     *
     * @param idParam
     * @return
     */
    SysNotification getSysNotificationById(IdParam idParam);

    /**
     * 查询SysNotificationQueryVo的所有结果
     *
     * @return
     */
    List<SysNotificationQueryVo> listSysNotificationQueryVo(SysNotificationQueryPageParam sysNotificationQueryPageParam);

    /**
     * 查询SysNotificationQueryVo的分页结果
     *
     * @param sysNotificationQueryPageParam
     * @return
     */
    IPage<SysNotificationQueryVo> pageSysNotificationQueryVo(SysNotificationQueryPageParam sysNotificationQueryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
    boolean readNotifications(IdBatchParam idBatchParam);
}
