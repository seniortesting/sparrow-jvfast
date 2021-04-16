package com.jvfast.module.sys.controller;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Limit;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.api.ResultCode;
import com.jvfast.common.config.captcha.VerificationCodeException;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.exception.DaoException;
import com.jvfast.common.exception.DaoExistException;
import com.jvfast.common.exception.DaoNotFoundException;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.common.shiro.service.JWTTokenService;
import com.jvfast.common.util.SpringUtil;
import com.jvfast.module.monitor.model.entity.MonitorLoginHistory;
import com.jvfast.module.sys.event.LoginHistoryEvent;
import com.jvfast.module.sys.model.dto.SysUserDeptDTO;
import com.jvfast.module.sys.model.dto.SysUserRoleDTO;
import com.jvfast.module.sys.model.entity.SysUser;
import com.jvfast.module.sys.model.param.*;
import com.jvfast.module.sys.model.vo.SysMenuQueryVo;
import com.jvfast.module.sys.model.vo.SysUserLoginTokenVo;
import com.jvfast.module.sys.model.vo.SysUserQueryVo;
import com.jvfast.module.sys.service.LoginService;
import com.jvfast.module.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "Authentication接口")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private final SysUserService sysUserService;
    private final LoginService loginService;
    private final JWTTokenService JWTTokenService;

    /**
     * 添加系统用户信息表
     */
    @Log(title = "注册用户", businessType = BusinessTypeEnum.ADD)
    @PostMapping({"/reg", "/mreg"})
    @ApiOperation(value = "注册账号", notes = "添加账号并签发JWT", response = Result.class)
    public Result<SysUserLoginTokenVo> addSysUser(@Valid @RequestBody SysUserRegisterParam sysUserRegisterParam,
                                                  HttpServletRequest httpServletRequest,
                                                  HttpServletResponse response) {
        ResultCode sysUserResponseCode;
        String errorMsg = "";
        boolean loginStatus = false;
        try {
            SysUserLoginTokenVo sysUserInfoVo = loginService.register(sysUserRegisterParam, httpServletRequest);
            // 记录日志
            loginStatus = true;
            // 设置token响应头
            response.setHeader(JWTTokenService.getJWTProperties().getTokenHeader(), sysUserInfoVo.getToken());
            return Result.success(sysUserInfoVo);
        } catch (VerificationCodeException e) {
            sysUserResponseCode = ResultCode.BAD_REQUEST;
            errorMsg = e.getMessage();
        } catch (DaoNotFoundException e) {
            sysUserResponseCode = ResultCode.NOT_FOUND;
            errorMsg = e.getMessage();
        } catch (DaoExistException e) {
            sysUserResponseCode = ResultCode.DUPLICATED_ERROR;
            errorMsg = e.getMessage();
        } catch (DaoException e) {
            sysUserResponseCode = ResultCode.BUSSINESS_ERROR;
            errorMsg = e.getMessage();
        } catch (Exception e) {
            sysUserResponseCode = ResultCode.BUSSINESS_ERROR;
            errorMsg = e.getMessage();
        } finally {
            // 记录登录日志
            MonitorLoginHistory monitorLoginHistory = new MonitorLoginHistory();
            monitorLoginHistory.setUserName(sysUserRegisterParam.getUsername());
            monitorLoginHistory.setLoginStatus(BooleanUtil.toInt(loginStatus));
            monitorLoginHistory.setLoginMessage(errorMsg);
            SpringUtil.publishEvent(new LoginHistoryEvent(monitorLoginHistory));
        }
        return Result.fail(sysUserResponseCode, errorMsg);
    }


    @PostMapping({"/login", "/mlogin"})
    @ApiOperation(value = "登陆", notes = "用户登录并签发JWT", response = SysUserLoginTokenVo.class)
    public Result login(@Valid @RequestBody SysUserLoginParam sysUserLoginParam,
                        HttpServletRequest httpServletRequest,
                        HttpServletResponse HttpServletResponse) {
        ResultCode sysUserResponseCode;
        String errorMsg = "";
        boolean loginStatus = false;
        try {
            SysUserLoginTokenVo sysUserLoginTokenVo = loginService.login(sysUserLoginParam, httpServletRequest);
            loginStatus = true;
            // 设置token响应头
            HttpServletResponse.setHeader(JWTTokenService.getJWTProperties().getTokenHeader(), sysUserLoginTokenVo.getToken());
            return Result.success(sysUserLoginTokenVo);
        } catch (VerificationCodeException e) {
            sysUserResponseCode = ResultCode.BAD_REQUEST;
            errorMsg = e.getMessage();
        } catch (DaoNotFoundException e) {
            sysUserResponseCode = ResultCode.NOT_FOUND;
            errorMsg = e.getMessage();
        } finally {
            // 记录登录日志
            MonitorLoginHistory monitorLoginHistory = new MonitorLoginHistory();
            monitorLoginHistory.setUserName(sysUserLoginParam.getUsername());
            monitorLoginHistory.setLoginStatus(BooleanUtil.toInt(loginStatus));
            monitorLoginHistory.setLoginMessage(errorMsg);
            SpringUtil.publishEvent(new LoginHistoryEvent(monitorLoginHistory));
        }
        return Result.fail(sysUserResponseCode, errorMsg);
    }

    @PostMapping("/info")
    @ApiOperation(value = "获取用户信息", notes = "获取当前登录用户信息", response = SysUserLoginTokenVo.class)
    public Result getUserInfo() {
        SysUserLoginTokenVo sysUserLoginTokenVo = loginService.getUserInfo();
        return Result.success(sysUserLoginTokenVo);
    }

    @PostMapping("/menu")
    @ApiOperation(value = "顶部菜单", notes = "获取顶部菜单列表", response = SysMenuQueryVo.class)
    public Result<List<SysMenuQueryVo>> getTopMenu() {
        List<SysMenuQueryVo> loginSysUserTokenVo = loginService.getTopMenu();
        return Result.success(loginSysUserTokenVo);
    }

    @PostMapping("/router")
    @ApiOperation(value = "侧边栏菜单", notes = "获取侧边栏菜单列表", response = SysMenuQueryVo.class)
    public Result<List<SysMenuQueryVo>> getRouters() {
        List<SysMenuQueryVo> loginSysUserTokenVo = loginService.getRouters();
        return Result.success(loginSysUserTokenVo);
    }

    @PostMapping("/verify")
    @ApiOperation(value = "根据用户名检查是否用户存在", notes = "忘记密码,找回密码", response = SysUserQueryVo.class)
    public Result forgetPasswdCheck(@Valid @RequestBody SysUserNameParam sysUserNameParam) {
        String usenrame = sysUserNameParam.getUsername();
        SysUserQueryVo sysUser = loginService.existUser(usenrame);
        return Result.success(sysUser);
    }

    @Log(title = "忘记密码", businessType = BusinessTypeEnum.OTHER)
    @PostMapping("/forgetpwd")
    @Limit(count = 3, seconds = 3600)
    @ApiOperation(value = "通过邮箱发送忘记密码操作", notes = "忘记密码,找回密码", response = SysUser.class)
    public Result forgetPasswd(@Valid @RequestBody SysUserForgetPasswdParam sysUserForgetPasswdParam) {
        ResultCode sysUserResponseCode;
        String errorMsg;
        try {
            SysUser sysUser = loginService.forgetPassword(sysUserForgetPasswdParam);
            return Result.success(sysUser);
        } catch (VerificationCodeException e) {
            sysUserResponseCode = ResultCode.BAD_REQUEST;
            errorMsg = e.getMessage();
        }
        return Result.fail(sysUserResponseCode, errorMsg);
    }

    @Log(title = "找回密码", businessType = BusinessTypeEnum.OTHER)
    @Limit(count = 3, seconds = 3600)
    @PostMapping("/forgetpwd/verify")
    @ApiOperation(value = "验证发送的邮件验证码是否正确或者手机号码对应的的短信验证码是否正确", notes = "忘记密码,找回密码", response = SysUserQueryVo.class)
    public Result forgetPasswdCheck(@Valid @RequestBody SysUserForgetPasswdCheckParam sysUserForgetPasswdCheckParam) {
        ResultCode sysUserResponseCode;
        String errorMsg = "";
        try {
            SysUserQueryVo sysUser = loginService.forgetPasswordVerify(sysUserForgetPasswdCheckParam);
            return Result.success(sysUser);
        } catch (DaoNotFoundException e) {
            sysUserResponseCode = ResultCode.NOT_FOUND;
            errorMsg = e.getMessage();
        }
        return Result.fail(sysUserResponseCode, errorMsg);
    }

    /**
     * 退出登录操作
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出当前用户信息", response = Boolean.class)
    public Result logout(HttpServletRequest request) {
        loginService.logout(request);
        return Result.success("退出成功");
    }

    /**
     * 修改系统用户信息表
     */
    @RequiresPermissions("sys:user:add")
    @Log(title = "添加用户信息", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加SysUser对象", notes = "添加用户信息表", response = Result.class)
    public Result<Boolean> addSysUser(@Valid @RequestBody SysUserAddParam sysUserAddParam) {
        boolean flag = sysUserService.addSysUser(sysUserAddParam);
        return Result.status(flag);
    }

    /**
     * 修改系统用户信息表
     */
//    @RequiresPermissions("sys:user:update")
    @Log(title = "修改用户信息", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改SysUser对象", notes = "修改系统用户信息表", response = Result.class)
    public Result<Boolean> updateSysUser(@Valid @RequestBody SysUserUpdateParam sysUserUpdateParam) {
        boolean flag = sysUserService.updateSysUserById(sysUserUpdateParam);
        return Result.status(flag);
    }

    /**
     * 删除系统用户信息表
     */
    @RequiresPermissions("sys:user:remove")
    @Log(title = "删除用户", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除SysUser对象", notes = "删除系统用户信息表", response = Result.class)
    public Result<Boolean> deleteSysUser(@Valid @RequestBody IdParam idParam) {
        boolean flag = sysUserService.removeSysUserById(idParam);
        return Result.status(flag);
    }

    /**
     * 批量删除用户表
     */
    @RequiresPermissions("sys:user:remove")
    @Log(title = "批量删除用户", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除SysUser对象", notes = "批量删除用户表", response = Result.class)
    public Result<Boolean> deleteSysUsers(@Valid @RequestBody IdBatchParam idBatchParam) {
        boolean flag = sysUserService.removeSysUsersById(idBatchParam);
        return Result.status(flag);
    }

    /**
     * 获取系统用户信息表
     */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取SysUserVo对象详情", notes = "系统用户信息表", response = SysUser.class)
    public Result<SysUserDeptDTO> getSysUser(@Valid @RequestBody IdParam idParam) {
        SysUserDeptDTO sysUser = sysUserService.getSysUserById(idParam);
        return Result.success(sysUser);
    }

    /**
     * 系统用户信息表分页列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "获取SysUserQueryVo分页列表", notes = "系统用户信息表分页列表", response = SysUserQueryVo.class)
    public Result<IPage<SysUserDeptDTO>> getSysUserPageList(@Valid @RequestBody(required = false) SysUserQueryPageParam sysUserQueryPageParam) throws Exception {
        IPage<SysUserDeptDTO> paging = sysUserService.pageSysUserQueryVo(sysUserQueryPageParam);
        return Result.success(paging);
    }

    /**
     * 批量重置用户密码表
     */
    @Log(title = "批量重置用户密码", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/resetpwd")
    @ApiOperation(value = "批量重置SysUser对象密码", notes = "批量重置用户密码表", response = Result.class)
    public Result<Boolean> resetSysUserPasswds(@Valid @RequestBody IdBatchParam idBatchParam) {
        boolean flag = sysUserService.resetPasswd(idBatchParam);
        return Result.status(flag);
    }

    @PostMapping("/role")
    @ApiOperation(value = "根据用户ID获取用户的角色信息", notes = "获取用户的角色信息", response = Result.class)
    public Result<List<SysUserRoleDTO>> getUserRoles(@RequestBody @Valid IdParam idParam) {
        List<SysUserRoleDTO> userRolesByUserId = sysUserService.getUserRolesByUserId(idParam);
        return Result.success(userRolesByUserId);
    }
    /***************************** 以下为扩展接口 ******************************************************/
}
