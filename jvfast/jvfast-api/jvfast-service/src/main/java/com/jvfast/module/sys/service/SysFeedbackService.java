package com.jvfast.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysFeedback;
import com.jvfast.module.sys.model.param.SysFeedbackAddParam;
import com.jvfast.module.sys.model.param.SysFeedbackQueryPageParam;
import com.jvfast.module.sys.model.param.SysFeedbackUpdateParam;
import com.jvfast.module.sys.model.vo.SysFeedbackQueryVo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 建议反馈表 服务类
 * </p>
 *
 * @author Walter Hu
 * @since 2020-01-09
 */
public interface SysFeedbackService extends IService<SysFeedback> {

    /**
     * 添加SysFeedback
     *
     * @param sysFeedback
     * @return
     */
    boolean saveSysFeedback(SysFeedbackAddParam sysFeedback);

    /**
     * 通过id更新SysFeedback
     *
     * @param sysFeedback
     * @return
     */
    boolean updateSysFeedbackById(SysFeedbackUpdateParam sysFeedback);

    /**
     * 通过id删除SysFeedback
     *
     * @param idParam
     * @return
     */
    boolean removeSysFeedbackById(IdParam idParam);

    /**
     * 通过id批量删除SysFeedback
     *
     * @param idBatchParam
     * @return
     */
    boolean removeSysFeedbackByIds(IdBatchParam idBatchParam);

    /**
     * 通过id查询SysFeedback
     *
     * @param idParam
     * @return
     */
    SysFeedback getSysFeedbackById(IdParam idParam);

    /**
     * 查询SysFeedbackQueryVo的所有结果
     *
     * @return
     */
    List<SysFeedbackQueryVo> listSysFeedbackQueryVo(IdParam idParam);

    /**
     * 查询SysFeedbackQueryVo的分页结果
     *
     * @param sysFeedbackQueryPageParam
     * @return
     */
    IPage<SysFeedbackQueryVo> pageSysFeedbackQueryVo(SysFeedbackQueryPageParam sysFeedbackQueryPageParam) throws DataAccessException;
    /***************************** 以下为扩展接口 ******************************************************/
}
