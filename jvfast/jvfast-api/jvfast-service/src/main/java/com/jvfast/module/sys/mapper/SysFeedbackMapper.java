package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysFeedback;
import com.jvfast.module.sys.model.param.SysFeedbackQueryPageParam;
import com.jvfast.module.sys.model.vo.SysFeedbackQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 建议反馈表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2020-01-09
 */
@Mapper
public interface SysFeedbackMapper extends BaseMapper<SysFeedback> {

    /**
     * <p>
     * sys_feedback分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2020-01-09
     */
    List<SysFeedbackQueryVo> pageSysFeedback(IPage page, @Param("query") SysFeedbackQueryPageParam queryPageParam) throws DataAccessException;

    /***************************** 以下为扩展接口 ******************************************************/
}
