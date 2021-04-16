package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysUserPostConverter;
import com.jvfast.module.sys.mapper.SysUserPostMapper;
import com.jvfast.module.sys.model.entity.SysUserPost;
import com.jvfast.module.sys.model.param.SysUserPostAddParam;
import com.jvfast.module.sys.model.param.SysUserPostQueryPageParam;
import com.jvfast.module.sys.model.param.SysUserPostUpdateParam;
import com.jvfast.module.sys.model.vo.SysUserPostQueryVo;
import com.jvfast.module.sys.service.SysUserPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 岗位表 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements SysUserPostService {


    private final SysUserPostMapper sysUserPostMapper;

    /**
     * 添加SysUserJob
     *
     * @param sysUserPostAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysUserPost(SysUserPostAddParam sysUserPostAddParam) {
        // 对象转换
        SysUserPost sysUserJob = SysUserPostConverter.INSTANCE.convertSysUserPostAddParam(sysUserPostAddParam);
        boolean savedResult = save(sysUserJob);
        return savedResult;
    }

    /**
     * 通过id更新SysUserJob
     *
     * @param sysUserPostUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysUserPostById(SysUserPostUpdateParam sysUserPostUpdateParam) {
        //对象转换
        SysUserPost sysUserPost = SysUserPostConverter.INSTANCE.convertSysUserPostUpdateParam(sysUserPostUpdateParam);
        boolean updatedResult = updateById(sysUserPost);
        return updatedResult;
    }

    /**
     * 通过id删除SysUserJob
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysUserPostById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    /**
     * 通过id查询SysUserJob
     *
     * @param idParam
     * @return
     */
    @Override
    public SysUserPost getSysUserJobById(IdParam idParam) {
        Long id = idParam.getId();
        SysUserPost getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysUserJobQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysUserPostQueryVo> listSysUserPostQueryVo() {
        List<SysUserPost> sysUserPosts = list();
        List<SysUserPostQueryVo> listSysUserPostQueryVo = SysUserPostConverter.INSTANCE.convertSysUserPostList(sysUserPosts);
        return listSysUserPostQueryVo;
    }

    /**
     * 查询SysUserJobQueryVo的分页结果
     *
     * @param sysUserPostQueryPageParam
     * @return
     */
    @Override
    public IPage<SysUserPostQueryVo> pageSysUserPostQueryVo(SysUserPostQueryPageParam sysUserPostQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = sysUserPostQueryPageParam.getPageNo();
        Integer pageSize = sysUserPostQueryPageParam.getPageSize();
        String keyword = sysUserPostQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysUserPostQueryVo> listSysUserPostQueryVo = sysUserPostMapper.getSysUserJobPage(pagingData, sysUserPostQueryPageParam);
        pagingData.setRecords(listSysUserPostQueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
