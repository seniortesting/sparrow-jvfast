package com.jvfast.module.sys.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jvfast.common.config.JVFastCommonProperties;
import com.jvfast.common.config.async.AsyncTask;
import com.jvfast.common.config.captcha.Captcha;
import com.jvfast.common.config.captcha.VerificationCodeException;
import com.jvfast.common.config.redis.RedisClient;
import com.jvfast.common.constant.HttpConst;
import com.jvfast.common.constant.NotificationConst;
import com.jvfast.common.constant.SysConst;
import com.jvfast.common.entity.TreeManager;
import com.jvfast.common.exception.BadRequestException;
import com.jvfast.common.exception.DaoException;
import com.jvfast.common.exception.DaoExistException;
import com.jvfast.common.exception.DaoNotFoundException;
import com.jvfast.common.shiro.config.ShiroProperties;
import com.jvfast.common.shiro.constant.SecurityConst;
import com.jvfast.common.shiro.constant.SecurityMessageConst;
import com.jvfast.common.shiro.entity.JWTToken;
import com.jvfast.common.shiro.model.RedisLoginSysUserVo;
import com.jvfast.common.shiro.service.JWTRedisService;
import com.jvfast.common.shiro.util.ShiroUtil;
import com.jvfast.common.util.EncryptUtil;
import com.jvfast.common.util.IPUtil;
import com.jvfast.common.util.RegUtil;
import com.jvfast.common.util.ServletUtil;
import com.jvfast.module.sys.converter.ShiroConverter;
import com.jvfast.module.sys.converter.SysUserConverter;
import com.jvfast.module.sys.model.entity.SysUser;
import com.jvfast.module.sys.model.entity.SysUserDept;
import com.jvfast.module.sys.model.param.SysUserForgetPasswdCheckParam;
import com.jvfast.module.sys.model.param.SysUserForgetPasswdParam;
import com.jvfast.module.sys.model.param.SysUserLoginParam;
import com.jvfast.module.sys.model.param.SysUserRegisterParam;
import com.jvfast.module.sys.model.vo.SysMenuQueryVo;
import com.jvfast.module.sys.model.vo.SysUserLoginTokenVo;
import com.jvfast.module.sys.model.vo.SysUserQueryVo;
import com.jvfast.notification.mail.entity.MailVo;
import com.jvfast.notification.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

    private final Captcha captcha;
    private final JVFastCommonProperties jvFastCommonProperties;
    private final com.jvfast.common.shiro.service.JWTTokenService JWTTokenService;
    private final JWTRedisService loginRedisService;

    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;
    private final SysUserDeptService sysUserDeptService;

    private final AsyncTask asyncTask;
    private final RedisClient redisClient;

    private final com.jvfast.notification.mail.service.MailService MailService;
    private final SmsService smsService;

    private static final String LOGIN_EMAIL_CACHE_KEY = "forget:email:{}";

    /**
     * 用户注册
     *
     * @param sysUserRegisterParam
     * @return
     */
    public SysUserLoginTokenVo register(SysUserRegisterParam sysUserRegisterParam, HttpServletRequest httpServletRequest) {
        // 注册用户
        // 手机注册: 手机号码+验证码
        // 用户名注册: 用户名 +密码 (手机号码/邮箱 可选) + 验证码
        SysUserLoginTokenVo loginUser;
        String errorMsg;
        String username = sysUserRegisterParam.getUsername();
        String passwd = sysUserRegisterParam.getPasswd();
        String code = sysUserRegisterParam.getCode();
        String captchaId = sysUserRegisterParam.getCaptchaId();

        if (StrUtil.isEmpty(username)) {
            errorMsg = StrUtil.format("注册用户名 {} 不能为空", username);
            log.error(errorMsg);
            throw new BadRequestException(errorMsg);
        }
        if (StrUtil.isEmpty(passwd)) {
            errorMsg = StrUtil.format("注册密码{}不能为空", passwd);
            throw new BadRequestException(errorMsg);
        }
        // 比对验证码
        boolean captchaEnabled = jvFastCommonProperties.getCaptcha().getEnabled();
        boolean isMobile = httpServletRequest.getRequestURI().contains(HttpConst.MOBILE_IDENTIFY);
        if (captchaEnabled && !isMobile) {
            // 验证图片验证码输入是否正确
            verifyCaptchaCode(captchaId, code);
        }
        if (StrUtil.isEmpty(captchaId)) {
            if (StrUtil.isEmpty(code)) {
                errorMsg = StrUtil.format("手机验证码{}不能为空", code);
                log.error(errorMsg);
                throw new BadRequestException(errorMsg);
            }
            // 校验验证码和手机号码,验证码60秒过期
            boolean verifyPassed = verifySMSCode(username, code);
            if (!verifyPassed) {
                errorMsg = StrUtil.format("验证码错误！");
                log.error("手机验证码验证错误");
                throw new DaoNotFoundException(errorMsg);
            }
            // 对应的手机号码就是用户名,保存时用
            sysUserRegisterParam.setUsername(username);
        }
        SysUser foundSysUser = loadSysUserByUserName(sysUserRegisterParam.getUsername());
        // 进一步验证查询的用户信息
        if (foundSysUser != null) {
            errorMsg = StrUtil.format("用户{}已经注册,不能重复注册", username);
            log.error(errorMsg);
            throw new DaoExistException(errorMsg);
        }
        // 录入用户信息
        foundSysUser = ShiroConverter.INSTANCE.convertRegisterParamToSysUser(sysUserRegisterParam);
        if (StrUtil.isNotEmpty(passwd)) {
            String encryptPasswd = EncryptUtil.encryptPasswd(passwd);
            foundSysUser.setPasswd(encryptPasswd);
        }
        if (RegUtil.match(RegUtil.EMAIL, username)) {
            foundSysUser.setEmail(username);
        }
        if (RegUtil.match(RegUtil.CHINA_PHONE, username)) {
            foundSysUser.setPhone(username);
        }
        boolean savedSysUser = sysUserService.save(foundSysUser);
        if (!savedSysUser) {
            errorMsg = StrUtil.format("保存注册用户信息失败");
            log.error(errorMsg);
            throw new DaoException(errorMsg);
        }
        // 录入注册的新用户的部门和角色信息
        SysUserDept sysUserDept = new SysUserDept();
        sysUserDept.setUserId(foundSysUser.getId());
        sysUserDept.setDeptId(SecurityConst.DEFAULT_DEPTMENT_ID);
        sysUserDeptService.save(sysUserDept);

        // token和角色等信息获取
        loginUser = generateJWTAndCacheUser(foundSysUser);
        return loginUser;
    }

    /**
     * controller调用
     *
     * @param sysUserLoginParam
     * @return
     */
    public SysUserLoginTokenVo login(SysUserLoginParam sysUserLoginParam, HttpServletRequest httpServletRequest) {
        SysUserLoginTokenVo loginUser;
        String username = sysUserLoginParam.getUsername();
        String passwd = sysUserLoginParam.getPasswd();
        String phone = sysUserLoginParam.getPhone();
        String smsCode = sysUserLoginParam.getCode();
        if (StrUtil.isNotEmpty(username) && StrUtil.isNotEmpty(passwd)) {
            // 校验对应的验证码是否正确
            boolean captchaEnabled = jvFastCommonProperties.getCaptcha().getEnabled();
            boolean isMobile = httpServletRequest.getRequestURI().contains(HttpConst.MOBILE_IDENTIFY);
            if (captchaEnabled && !isMobile) {
                String code = sysUserLoginParam.getCode();
                String captchaId = sysUserLoginParam.getCaptchaId();
                verifyCaptchaCode(captchaId, code);
            }
        } else if (StrUtil.isNotEmpty(phone) && StrUtil.isNotEmpty(smsCode)) {
            boolean verifyPassed = verifySMSCode(phone, smsCode);
            if (!verifyPassed) {
                String errorMsg = StrUtil.format("验证码错误！");
                log.error("手机验证码验证错误");
                throw new DaoNotFoundException(errorMsg);
            }
            sysUserLoginParam.setUsername(phone);
        } else {
            throw new UnknownAccountException(SecurityMessageConst.MSG_ACCOUNT_INFO_EMPTY);
        }
        SysUser foundSysUser = loadSysUserByUserName(sysUserLoginParam.getUsername());
        // 进一步验证查询的用户信息
        if (foundSysUser != null) {
            Boolean status = foundSysUser.getStatus();
            if (!status) {
                throw new DisabledAccountException(SecurityMessageConst.DISABLED_ACCOUNT);
            }
            if (StrUtil.isNotEmpty(username) && StrUtil.isNotEmpty(passwd)) {
                String foundSysUserPasswd = foundSysUser.getPasswd();
                boolean passwdMatched = EncryptUtil.checkPasswd(passwd, foundSysUserPasswd);
                if (!passwdMatched) {
                    throw new IncorrectCredentialsException(SecurityMessageConst.INCORRECT_CREDENTIALS);
                }
            }
        } else {
            throw new UnknownAccountException(SecurityMessageConst.UNKNOWN_ACCOUNT);
        }
        loginUser = generateJWTAndCacheUser(foundSysUser);
        return loginUser;
    }

    /**
     * 获取用户基本信息
     *
     * @param
     * @return com.jvfast.module.sys.model.vo.LoginSysUserTokenVo
     * @author Walter Hu
     * @date 2019/12/18
     * @since 1.8
     */
    public SysUserLoginTokenVo getUserInfo() {
        SysUserLoginTokenVo loginUser = new SysUserLoginTokenVo();
        String token = JWTTokenService.getToken(ServletUtil.getRequest());
        RedisLoginSysUserVo loginSysUserRedisVo = getRedisLoginUser();
        loginUser.setToken(token);
        loginUser.setUser(loginSysUserRedisVo);
        return loginUser;
    }

    /**
     * 根据用户信息获取对应的菜单列表
     *
     * @param
     * @return java.util.List<com.jvfast.module.sys.model.vo.SysMenuQueryVo>
     * @author Walter Hu
     * @date 2019/12/18
     * @since 1.8
     */
    public List<SysMenuQueryVo> getRouters() {
        List<SysMenuQueryVo> sysMenuQueryVoList = Lists.newArrayList();
        RedisLoginSysUserVo loginSysUserRedisVo = getRedisLoginUser();
        // 获取对应的用户信息
        if (Objects.nonNull(loginSysUserRedisVo)) {
            Set<String> roles = loginSysUserRedisVo.getRoles();
            Long userId = loginSysUserRedisVo.getId();
            if (roles.contains(SecurityConst.ADMIN_ROLE)) {
                // 获取所有的菜单
                sysMenuQueryVoList = sysMenuService.listRouterByUserId(null);
            } else {
                sysMenuQueryVoList = sysMenuService.listRouterByUserId(userId);
            }
        }
        // 将对应的数据转换为树形结构
        if (!sysMenuQueryVoList.isEmpty()) {
            sysMenuQueryVoList = TreeManager.merge(sysMenuQueryVoList, SysConst.DEFAULT_PARENT_ID);
        }
        return sysMenuQueryVoList;
    }

    public List<SysMenuQueryVo> getTopMenu() {
        List<SysMenuQueryVo> sysMenuQueryVoList = Lists.newArrayList();
        RedisLoginSysUserVo loginSysUserRedisVo = getRedisLoginUser();
        // 获取对应的用户信息
        if (Objects.nonNull(loginSysUserRedisVo)) {
            Set<String> roles = loginSysUserRedisVo.getRoles();
            if (roles.contains(SecurityConst.ADMIN_ROLE)) {
                // 获取所有的菜单
                sysMenuQueryVoList = sysMenuService.listTopMenuByUserId(null);
            } else {
                Long userId = loginSysUserRedisVo.getId();
                sysMenuQueryVoList = sysMenuService.listTopMenuByUserId(userId);
            }
        }
        return sysMenuQueryVoList;
    }

    /**
     * 找回密码已基本两种方式:
     * 1. 此处为通过邮箱找回逻辑
     * 2. 通过手机号码加短信验证码请看 @see
     *
     * @param sysUserForgetPasswdParam
     */
    public SysUser forgetPassword(SysUserForgetPasswdParam sysUserForgetPasswdParam) {
        String username = sysUserForgetPasswdParam.getUsername();
        String code = sysUserForgetPasswdParam.getCode();
        String captchaId = sysUserForgetPasswdParam.getCaptchaId();
        if (StrUtil.isEmpty(username)) {
            String errorMsg = StrUtil.format("找回密码账号不能为空");
            log.error(errorMsg);
            throw new BadRequestException(errorMsg);
        }
        // 检验验证码
        // 校验对应的验证码是否正确
        Boolean captchaEnabled = jvFastCommonProperties.getCaptcha().getEnabled();
        if (captchaEnabled) {
            verifyCaptchaCode(captchaId, code);
        }
        // 检查输入值
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                .select(
                        SysUser::getId,
                        SysUser::getUserName,
                        SysUser::getEmail,
                        SysUser::getPhone,
                        SysUser::getStatus
                );
        if (RegUtil.match(RegUtil.EMAIL, username)) {
            queryWrapper.eq(SysUser::getEmail, username);
        } else if (RegUtil.match(RegUtil.CHINA_PHONE, username)) {
            queryWrapper.eq(SysUser::getPhone, username);
        } else {
            queryWrapper.eq(SysUser::getUserName, username);
        }
        queryWrapper.eq(SysUser::getStatus, SysConst.ENABLED);
        SysUser foundSysUser = sysUserService.getOne(queryWrapper, false);
        if (foundSysUser == null) {
            // 没有注册过的用户
            String errorMsg = StrUtil.format("输入账号{}没有注册!", username);
            log.error(errorMsg);
            throw new DaoNotFoundException(errorMsg);
        }
        // 如果是邮箱进行发送验证码
        if (RegUtil.match(RegUtil.EMAIL, username)) {
            String emailVerifyCode = IdUtil.fastSimpleUUID();
            String emailSubject = "找回密码";
            String emailTo = username;
            String emailDate = DateUtil.now();
            String url = "https://jvfast.pingbook.top/changepwd/" + emailVerifyCode;
            log.info("发送邮箱验证码: {}", emailVerifyCode);
            SysUser finalFoundSysUser = foundSysUser;
            asyncTask.executeTask(() -> {
                // 1. 记录在redis缓存中
                final SysUser sysUser = finalFoundSysUser;
                Duration expiredHours = Duration.ofHours(NotificationConst.EMAIL_VERIFYCODE_EXPIRED_HOURS);
                redisClient.setValue(StrUtil.format(LOGIN_EMAIL_CACHE_KEY, emailVerifyCode), sysUser, expiredHours);
                // 2.发送邮件通知模板
                MailVo MailVo = new MailVo();
                HashMap<String, Object> model = Maps.newHashMap();
                MailVo.setTo(username);
                MailVo.setSubject(emailSubject);
                model.put("name", emailTo);
                model.put("date", emailDate);
                model.put("url", url);
                MailVo.setModel(model);
                MailVo.setTemplate(NotificationConst.EMAIL_FORGET_PASSWORD);
                MailService.sendEmail(MailVo);
            });
        }
        // 查找到对应的用户
        return foundSysUser;
    }

    /**
     * 1. 为找回密码通过邮件点击进入的验证逻辑
     * 2. 手机号码和验证码点击后验证是否正确的操作
     * 最后的修改密码操作参数使用 {@link SysUserService#updateSysUserById(com.jvfast.module.sys.model.param.SysUserUpdateParam)}
     *
     * @param sysUserForgetPasswdCheckParam
     * @return
     */
    public SysUserQueryVo forgetPasswordVerify(SysUserForgetPasswdCheckParam sysUserForgetPasswdCheckParam) {
        String errorMsg;
        String phone = sysUserForgetPasswdCheckParam.getPhone();
        String code = sysUserForgetPasswdCheckParam.getCode();
        if (StrUtil.isEmpty(code) && StrUtil.isEmpty(phone)) {
            errorMsg = StrUtil.format("找回密码邮箱或者电话号码不能为空");
            log.error(errorMsg);
            throw new BadRequestException(errorMsg);
        }
        // 此处验证是通过邮箱点击来进行密码修改的
        String username = "";
        if (StrUtil.isEmpty(phone) && StrUtil.isNotEmpty(code)) {
            // 查找用户用于继续操作
            SysUser sysUser = verifyEmailCode(code);
            username = sysUser.getEmail();
        } else if (StrUtil.isNotEmpty(phone) && StrUtil.isNotEmpty(code)) {
            // 验证手机验证码
            boolean verifyPassed = verifySMSCode(phone, code);
            if (!verifyPassed) {
                errorMsg = StrUtil.format("验证码错误！");
                log.error("手机验证码验证错误");
                throw new DaoNotFoundException(errorMsg);
            }
            username = phone;
        }
        // 查找到对应的用户
        SysUser sysUser = loadSysUserByUserName(username);
        if (sysUser == null) {
            errorMsg = StrUtil.format("没有找到对应的用户数据");
            log.error(errorMsg);
            throw new UnknownAccountException(errorMsg);
        }
        // 删除对应的缓存用户信息
        expiredEmailCode(code);
        SysUserQueryVo sysUserInfoVo = SysUserConverter.INSTANCE.convertSysUser(sysUser);
        return sysUserInfoVo;
    }

    /**
     * 退出登录
     *
     * @param httpServletRequest
     */
    public void logout(HttpServletRequest httpServletRequest) {
        Subject subject = ShiroUtil.getSubject();
        //注销
        subject.logout();
        // 获取token
        String token = JWTTokenService.getToken(httpServletRequest);
        if (null != token) {
            // 删除Redis缓存信息
            loginRedisService.deleteLoginInfo(token);
            log.info("登出成功,username:{},token:{}", token);
        }
    }

    /**
     * 检查用户是否存在
     *
     * @param username
     * @return
     */
    public SysUserQueryVo existUser(String username) {
        SysUser sysUser = loadSysUserByUserName(username);
        SysUserQueryVo sysUserQueryVo = SysUserConverter.INSTANCE.convertSysUser(sysUser);
        if (Objects.isNull(sysUserQueryVo)) {
            throw new UnknownAccountException(SecurityMessageConst.UNKNOWN_ACCOUNT);
        }
        return sysUserQueryVo;

    }

    /**
     * 通过用户名查询用户相关信息
     * 用户名如果符合邮箱格式带邮箱查询
     * 用户名如果符合电话号码则带电话号码查询
     *
     * @param username 用户名
     * @return
     */
    private SysUser loadSysUserByUserName(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                .select(
                        SysUser::getId,
                        SysUser::getUserName,
                        SysUser::getPasswd,
                        SysUser::getNickName,
                        SysUser::getPhone,
                        SysUser::getEmail,
                        SysUser::getAvatar,
                        SysUser::getSex,
                        SysUser::getAddress,
                        SysUser::getSignature,
                        SysUser::getStatus,
                        SysUser::getLastLoginTime,
                        SysUser::getLastLoginIp
                );
        queryWrapper.eq(SysUser::getUserName, username);
        if (RegUtil.match(RegUtil.EMAIL, username)) {
            queryWrapper.or().eq(SysUser::getEmail, username);
        }
        if (RegUtil.match(RegUtil.CHINA_PHONE, username)) {
            queryWrapper.or().eq(SysUser::getPhone, username);
        }
        SysUser sysUser = sysUserService.getOne(queryWrapper, false);
        log.info("用户账号查询，账号: {},查询结果是: {}", username, Objects.isNull(sysUser)?"":sysUser.toString());
        return sysUser;
    }

    /**
     * 图形验证码验证
     *
     * @param captchaId
     * @param code
     */
    private void verifyCaptchaCode(String captchaId, String code) {
        if (StrUtil.isNotEmpty(captchaId) && StrUtil.isNotEmpty(code)) {
            String cacheCaptchaCode = captcha.getCaptcha(captchaId);
            if (null == cacheCaptchaCode) {
                throw new VerificationCodeException(SecurityMessageConst.CAPTCHA_NOTFOUND);
            }
            if (!code.equalsIgnoreCase(cacheCaptchaCode)) {
                throw new VerificationCodeException(SecurityMessageConst.CAPTCHA_INCORRECT);
            }
        } else {
            throw new VerificationCodeException(SecurityMessageConst.CAPTCHA_EMPTY);
        }
    }

    /**
     * 短信验证码验证
     *
     * @param phone
     * @param code
     * @return
     */
    private boolean verifySMSCode(String phone, String code) {
        boolean validVerifyCode = smsService.checkVerifyCode(phone, code);
        return validVerifyCode;
    }

    /**
     * 邮箱激活码验证
     *
     * @param code
     * @return
     */
    private SysUser verifyEmailCode(String code) {
        String errorMsg;
        if (StrUtil.isEmpty(code)) {
            errorMsg = StrUtil.format("邮箱验证码{}不能为空", code);
            log.error(errorMsg);
            throw new BadRequestException(errorMsg);
        }
        SysUser sysUser = redisClient.getValue(StrUtil.format(LOGIN_EMAIL_CACHE_KEY, code));
        // 检查是否过期
        if (Objects.isNull(sysUser)) {
            // 表示已经过期
            errorMsg = StrUtil.format("邮箱验证码{}已经过期或者不存在", code);
            log.error(errorMsg);
            throw new DaoNotFoundException(errorMsg);
        }
        return sysUser;
    }

    /**
     * 删除验证码的缓存信息
     *
     * @param code
     * @return
     */
    private Boolean expiredEmailCode(String code) {
        String errorMsg;
        if (StrUtil.isEmpty(code)) {
            errorMsg = StrUtil.format("邮箱验证码{}不能为空", code);
            log.error(errorMsg);
            throw new BadRequestException(errorMsg);
        }
        return redisClient.deleteKey(StrUtil.format(LOGIN_EMAIL_CACHE_KEY, code));
    }

    /**
     * 产生token并缓存用户信息到redis
     *
     * @param sysUser
     * @return
     */
    private SysUserLoginTokenVo generateJWTAndCacheUser(SysUser sysUser) {
        SysUserLoginTokenVo loginUser = new SysUserLoginTokenVo();
        String userId = sysUser.getId().toString();
        // 获取角色权限信息
        RedisLoginSysUserVo redisLoginSysUserVo = createLoginUserRoleAndPermissions(userId);
        // 1. 产生对应的jwt token,并配置对应的shiro token
        String token = JWTTokenService.createToken(userId);
        ShiroProperties.JWT jwtProperties = JWTTokenService.getJWTProperties();
        String secret = jwtProperties.getSecretKey();
        Integer expiredHours = jwtProperties.getExpiredHours();
        JWTToken jwtToken = JWTToken.build(token, userId, secret, expiredHours);
        // 2. 缓存对应用的jwt token信息
        RedisLoginSysUserVo redisLoginSysUser = ShiroConverter.INSTANCE.convertSysUserToRedisSysUser(sysUser);
        redisLoginSysUser.setRoles(redisLoginSysUserVo.getRoles());
        redisLoginSysUser.setPermissions(redisLoginSysUserVo.getPermissions());

        loginRedisService.cacheLoginInfo(jwtToken, redisLoginSysUser);
        //3. 执行对应的 jwt token拦截器,进入到JWTRealm中进行处理, 该步骤可删除不用
        Subject subject = ShiroUtil.getSubject();
        subject.login(jwtToken);

        loginUser.setToken(token);
        loginUser.setUser(redisLoginSysUser);
        // 更新最后登录日志
        String requestIp = IPUtil.getRequestIp();
        updateLastLoginInfo(sysUser.getId(), requestIp);

        return loginUser;
    }

    /**
     * 根据用户id获取用户的权限信息
     *
     * @param userId
     * @return com.jvfast.common.shiro.model.RedisLoginSysUserVo
     * @author Walter Hu
     * @date 2019/12/18
     * @since 1.8
     */
    private RedisLoginSysUserVo createLoginUserRoleAndPermissions(String userId) {
        // 获取对应的该用户的权限信息
        RedisLoginSysUserVo redisLoginSysUserVo = new RedisLoginSysUserVo();
        Set<String> permissions = Sets.newHashSet();
        Set<String> roles = sysRoleService.getRoleCodesByUserId(userId);
        if (roles.contains(SecurityConst.ADMIN_ROLE)) {
            roles.clear();
            roles.add(SecurityConst.ADMIN_ROLE);
            permissions.add(SecurityConst.ADMIN__PERMISSION_PATTERN);
        } else {
            if (!roles.isEmpty()) {
                Set<SysMenuQueryVo> menus = sysMenuService.listPermissionByRoleCodes(roles);
                for (SysMenuQueryVo menuQueryVo : menus) {
                    String permission = menuQueryVo.getPermission();
                    if (StrUtil.isNotEmpty(permission)) {
                        permissions.addAll(Arrays.asList(permission.trim().split(",")));
                    }
                }
            }
        }

        redisLoginSysUserVo.setRoles(roles);
        redisLoginSysUserVo.setPermissions(permissions);
        return redisLoginSysUserVo;
    }

    /**
     * 保存对应的登录信息
     *
     * @param userId
     * @param requestIp
     */
    private void updateLastLoginInfo(Long userId, String requestIp) {
        asyncTask.executeTask(() -> {
            SysUser sysUser = new SysUser();
            sysUser.setId(userId);
            sysUser.setLastLoginTime(LocalDateTime.now());
            sysUser.setLastLoginIp(requestIp);
            sysUserService.updateById(sysUser);
        });
    }

    /**
     * 得到当前登录的用户信息从redis缓存中获取
     *
     * @param
     * @return com.jvfast.common.shiro.model.RedisLoginSysUserVo
     * @author Walter Hu
     * @date 2019/12/23
     * @since 1.8
     */
    private RedisLoginSysUserVo getRedisLoginUser() {
        String token = JWTTokenService.getToken(ServletUtil.getRequest());
        RedisLoginSysUserVo loginSysUserRedisVo = loginRedisService.getLoginSysUserRedisVo(token);
        return loginSysUserRedisVo;
    }
}
