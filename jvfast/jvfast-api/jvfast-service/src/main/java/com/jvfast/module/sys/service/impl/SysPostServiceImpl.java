package com.jvfast.module.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysPostConverter;
import com.jvfast.module.sys.mapper.SysPostMapper;
import com.jvfast.module.sys.model.entity.SysPost;
import com.jvfast.module.sys.model.param.SysPostAddParam;
import com.jvfast.module.sys.model.param.SysPostQueryPageParam;
import com.jvfast.module.sys.model.param.SysPostUpdateParam;
import com.jvfast.module.sys.model.vo.SysPostQueryVo;
import com.jvfast.module.sys.service.SysPostService;
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
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {


    private final SysPostMapper sysPostMapper;

    /**
     * 添加SysPost
     *
     * @param sysPostAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysPost(SysPostAddParam sysPostAddParam) {
        // 对象转换
        SysPost sysPost = SysPostConverter.INSTANCE.convertSysPostAddParam(sysPostAddParam);
        boolean savedResult = save(sysPost);
        return savedResult;
    }

    /**
     * 通过id更新SysPost
     *
     * @param sysPostUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysPostById(SysPostUpdateParam sysPostUpdateParam) {
        //对象转换
        SysPost sysPost = SysPostConverter.INSTANCE.convertSysPostUpdateParam(sysPostUpdateParam);
        boolean updatedResult = updateById(sysPost);
        return updatedResult;
    }

    /**
     * 通过id删除SysPost
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysPostById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    @Override
    public boolean removeSysPostsById(IdBatchParam idParam) {
        List<Long> ids = idParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }

    /**
     * 通过id查询SysPost
     *
     * @param idParam
     * @return
     */
    @Override
    public SysPost getSysPostById(IdParam idParam) {
        Long id = idParam.getId();
        SysPost getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysPostQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysPostQueryVo> listSysPostQueryVo() {
        List<SysPost> listsysPost = list();
        List<SysPostQueryVo> listSysPostQueryVo = SysPostConverter.INSTANCE.convertSysPostList(listsysPost);
        return listSysPostQueryVo;
    }

    /**
     * 查询SysPostQueryVo的分页结果
     *
     * @param sysPostQueryPageParam
     * @return
     */
    @Override
    public IPage<SysPostQueryVo> pageSysPostQueryVo(SysPostQueryPageParam sysPostQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = sysPostQueryPageParam.getPageNo();
        Integer pageSize = sysPostQueryPageParam.getPageSize();
        String keyword = sysPostQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysPostQueryVo> listSysPostQueryVo = sysPostMapper.getSysPostPage(pagingData, sysPostQueryPageParam);
        pagingData.setRecords(listSysPostQueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
