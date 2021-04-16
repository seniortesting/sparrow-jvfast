package com.jvfast.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.sys.model.entity.SysNotice;
import com.jvfast.module.sys.model.param.SysNoticeQueryPageParam;
import com.jvfast.module.sys.model.vo.SysNoticeQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 通知公告表 Mapper 接口
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@Mapper
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

    /**
     * <p>
     * sys_notice分页查询结果
     * </p>
     *
     * @author Walter Hu
     * @since 2019-11-18
     */
    List<SysNoticeQueryVo> getSysNoticePage(IPage page, @Param("query") SysNoticeQueryPageParam queryPageParam);

    /***************************** 以下为扩展接口 ******************************************************/
}
