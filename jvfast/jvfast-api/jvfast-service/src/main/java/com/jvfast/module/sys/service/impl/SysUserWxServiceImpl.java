package com.jvfast.module.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jvfast.common.exception.DaoNotFoundException;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.converter.SysUserWxConverter;
import com.jvfast.module.sys.mapper.SysUserWxMapper;
import com.jvfast.module.sys.model.entity.SysUser;
import com.jvfast.module.sys.model.entity.SysUserWx;
import com.jvfast.module.sys.model.param.*;
import com.jvfast.module.sys.model.vo.SysUserWxQueryVo;
import com.jvfast.module.sys.service.SysUserService;
import com.jvfast.module.sys.service.SysUserWxService;
import com.jvfast.notification.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 微信用户 服务实现类
 * </p>
 *
 * @author Walter Hu
 * @since 2020-02-03
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysUserWxServiceImpl extends ServiceImpl<SysUserWxMapper, SysUserWx> implements SysUserWxService {
    private final SysUserWxMapper sysUserWxMapper;

    private final SysUserService sysUserService;
    private final SmsService smsService;

    /**
     * 添加SysUserWx
     *
     * @param sysUserWxAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysUserWx(SysUserWxAddParam sysUserWxAddParam) {
        // 对象转换
        SysUserWx sysUserWx = SysUserWxConverter.INSTANCE.convertSysUserWxAddParam(sysUserWxAddParam);
        boolean savedResult = save(sysUserWx);
        return savedResult;
    }

    /**
     * 通过id更新SysUserWx
     *
     * @param sysUserWxUpdateParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysUserWxById(SysUserWxUpdateParam sysUserWxUpdateParam) {
        //对象转换
        SysUserWx sysUserWx = SysUserWxConverter.INSTANCE.convertSysUserWxUpdateParam(sysUserWxUpdateParam);
        boolean updatedResult = updateById(sysUserWx);
        return updatedResult;
    }

    /**
     * 通过id删除SysUserWx
     *
     * @param idParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysUserWxById(IdParam idParam) {
        Long id = idParam.getId();
        boolean removedResult = removeById(id);
        return removedResult;
    }

    /**
     * 通过id批量删除SysUserWx
     *
     * @param idBatchParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeSysUserWxByIds(IdBatchParam idBatchParam) {
        List<Long> ids = idBatchParam.getIds();
        boolean removedResult = removeByIds(ids);
        return removedResult;
    }

    /**
     * 通过id查询SysUserWx
     *
     * @param idParam
     * @return
     */
    @Override
    public SysUserWx getSysUserWxById(IdParam idParam) {
        Long id = idParam.getId();
        SysUserWx getResult = getById(id);
        return getResult;
    }

    /**
     * 查询SysUserWxQueryVo的所有结果
     *
     * @return
     */
    @Override
    public List<SysUserWxQueryVo> listSysUserWxQueryVo() {
        LambdaQueryWrapper<SysUserWx> lambdaQueryWrapper = Wrappers.<SysUserWx>lambdaQuery();
        List<SysUserWx> listsysUserWx = list(lambdaQueryWrapper);
        List<SysUserWxQueryVo> listSysUserWxQueryVo = SysUserWxConverter.INSTANCE.convertSysUserWxList(listsysUserWx);
        return listSysUserWxQueryVo;
    }

    /**
     * 查询SysUserWxQueryVo的分页结果
     *
     * @param sysUserWxQueryPageParam
     * @return
     */
    @Override
    public IPage<SysUserWxQueryVo> pageSysUserWxQueryVo(SysUserWxQueryPageParam sysUserWxQueryPageParam) throws DataAccessException {
        // 请求传递的分页参数
        Integer pageNo = sysUserWxQueryPageParam.getPageNo();
        Integer pageSize = sysUserWxQueryPageParam.getPageSize();
        String keyword = sysUserWxQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        List<SysUserWxQueryVo> listSysUserWxQueryVo = sysUserWxMapper.pageSysUserWx(pagingData, sysUserWxQueryPageParam);
        pagingData.setRecords(listSysUserWxQueryVo);
        return pagingData;
    }

    /***************************** 以下为扩展接口 ******************************************************/
    @Override
    public SysUserWxQueryVo getUserWxByOpenId(SysUserWxOpenIdQueryParam sysUserWxOpenIdQueryParam) {
        String openId = sysUserWxOpenIdQueryParam.getOpenId();
        LambdaQueryWrapper<SysUserWx> sysUserWxLambdaQueryWrapper = Wrappers.<SysUserWx>lambdaQuery()
                .select(SysUserWx::getId,
                        SysUserWx::getUserId,
                        SysUserWx::getOpenId,
                        SysUserWx::getNickname,
                        SysUserWx::getCity,
                        SysUserWx::getProvince,
                        SysUserWx::getCountry,
                        SysUserWx::getSex,
                        SysUserWx::getHeadImgUrl)
                .eq(SysUserWx::getOpenId, openId);
        log.info("查询微信用户信息通过openid: {}", openId);
        SysUserWx sysUserWx = getOne(sysUserWxLambdaQueryWrapper, false);
        log.info("查询微信用户信息通过openid,返回的微信用户信息是: {}", sysUserWx.toString());
        SysUserWxQueryVo sysUserWxQueryVo = SysUserWxConverter.INSTANCE.convertSysUserWx(sysUserWx);
        return sysUserWxQueryVo;
    }

    @Override
    public Boolean bindWpUser(SysUserWxBindingParam sysUserWxBindingParam) {
        boolean savedSuccess;
        String appId = sysUserWxBindingParam.getAppId();
        String openId = sysUserWxBindingParam.getOpenId();
        String userName = sysUserWxBindingParam.getUserName();
        String code = sysUserWxBindingParam.getCode();
        log.info("绑定参数是: {}", sysUserWxBindingParam.toString());
        // 检查验证码是否正确
        boolean validVerifyCode = smsService.checkVerifyCode(userName, code);
        if (!validVerifyCode) {
            String errorMsg = StrUtil.format("验证码错误！");
            log.error("手机验证码验证错误");
            throw new DaoNotFoundException(errorMsg);
        }

        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getId, SysUser::getPhone, SysUser::getUserName)
                .eq(SysUser::getPhone, userName)
                .or()
                .eq(SysUser::getUserName, userName);
        SysUser sysUser = sysUserService.getOne(sysUserLambdaQueryWrapper, false);
        log.info("查询参数,用户名: {}, 手机号码: {},的用户个数是:{}", userName, userName, sysUser);
        if (sysUser != null) {
            log.info("获取的用户信息是: {}", sysUser.toString());
            Long sysUserId = sysUser.getId();
            LambdaQueryWrapper<SysUserWx> sysUserWxLambdaQueryWrapper = Wrappers.<SysUserWx>lambdaQuery()
                    .select(SysUserWx::getId)
                    .eq(SysUserWx::getAppId, appId)
                    .eq(SysUserWx::getOpenId, openId);
            SysUserWx sysUserWx = getOne(sysUserWxLambdaQueryWrapper, false);
            SysUserWx saveSysUserWx = new SysUserWx();
            saveSysUserWx.setAppId(appId);
            saveSysUserWx.setOpenId(openId);
            saveSysUserWx.setUserId(sysUserId.toString());
            if (sysUserWx == null) {
                log.info("没有绑定过用户: {}", sysUserWx == null);
                // 插入对应的记录
                savedSuccess = save(saveSysUserWx);
            } else {
                log.info("用户已经绑定过: {}", sysUserWx.toString());
                saveSysUserWx.setId(sysUserWx.getId());
                savedSuccess = updateById(saveSysUserWx);
            }
        } else {
            // 没有对应的用户
            throw new DaoNotFoundException("不存在的账号:" + userName);
        }
        return savedSuccess;
    }

    @Override
    public SysUserWxQueryVo getBindStatus(SysUserWxBindingStatusParam bindingParam) {
        SysUserWxQueryVo sysUserWxQueryVo = null;
        String appId = bindingParam.getAppId();
        String openId = bindingParam.getOpenId();
        LambdaQueryWrapper<SysUserWx> sysUserWxLambdaQueryWrapper = Wrappers.<SysUserWx>lambdaQuery()
                .select(SysUserWx::getId, SysUserWx::getOpenId, SysUserWx::getUserId)
                .eq(SysUserWx::getAppId, appId)
                .eq(SysUserWx::getOpenId, openId);
        SysUserWx sysUserWx = getOne(sysUserWxLambdaQueryWrapper, false);
        if (sysUserWx != null) {
            String userId = sysUserWx.getUserId();
            if (StrUtil.isNotBlank(userId)) {
                // 已经绑定过
                sysUserWxQueryVo = SysUserWxConverter.INSTANCE.convertSysUserWx(sysUserWx);
            }
        }
        return sysUserWxQueryVo;
    }
}
