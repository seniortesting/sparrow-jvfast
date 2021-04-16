package com.jvfast.module.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.demo.model.entity.DemoTest;
import com.jvfast.module.demo.model.param.DemoTestQueryPageParam;
import com.jvfast.module.demo.model.vo.DemoTestQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 *
 * Mapper接口
 * @author Walter Hu
*/
@Mapper
public interface DemoTestMapper extends BaseMapper<DemoTest> {

    /**
    * DemoTest分页查询结果
    *
    * @param page, queryPageParam
    * @return List<DemoTestQueryVo>
    */
    List<DemoTestQueryVo> pageDemoTest(IPage page, @Param("query") DemoTestQueryPageParam queryPageParam) throws DataAccessException;
    /***************************** 以下为扩展接口 ******************************************************/
  }
