package com.jvfast.common.exception;

import com.google.common.collect.Lists;
import com.jvfast.common.api.Result;
import com.jvfast.common.api.ResultCode;
import com.jvfast.common.config.JVFastCommonProperties;
import com.jvfast.common.config.jackson.JacksonUtil;
import com.jvfast.common.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/***
 *
 *
 *
 * @author: Walter Hu
 * @date: 2019/5/24 0024
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
@Order(-1)
public class GlobalExceptionResponseBodyAdvice implements ResponseBodyAdvice<Object> {


    private final JVFastCommonProperties jvFastCommonProperties;
    public static final String DEMO_ERROR_MSG = "演示模式，不允许操作! 如需帮助, 邮件联系: alterhu2020@gmail.com";

    /**
     * 数据库层操作异常
     *
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({
            DataAccessException.class,
            DaoException.class,
            DemoModeException.class,
            PersistenceException.class
    })
    public Result handleServiceExceptionHandler(Exception exception) {
        String errorMsg = null != exception.getMessage() ? exception.getMessage() : "请求数据处理失败";
        boolean demoModeException = exception.getMessage().contains(DemoModeException.class.getName());
        if (exception instanceof DemoModeException || demoModeException) {
            errorMsg = DEMO_ERROR_MSG;
        }
        log.error(errorMsg, exception);
        return Result.fail(ResultCode.DATABASE_ERROR, errorMsg);
    }

    /**
     * 登陆注册token异常处理,登录账号相关异常
     *
     * @param exception
     * @return
     * @see org.apache.shiro.authc.DisabledAccountException
     * @see org.apache.shiro.authc.LockedAccountException
     * @see org.apache.shiro.authc.CredentialsException
     * @see org.apache.shiro.authc.UnknownAccountException
     * @see org.apache.shiro.authc.ExpiredCredentialsException
     * ....
     */
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result authenticationExceptionHandler(AuthenticationException exception) {
        final String errorMsg = null != exception.getMessage() ? exception.getMessage() : "账号授权异常";
        log.error(errorMsg, exception);
        return Result.fail(ResultCode.BUSSINESS_ERROR, errorMsg);
    }

    /**
     * 未认证异常处理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result unauthenticatedExceptionHandler(AuthorizationException exception) {
        String errorMsg = "账号认证异常";
        if (exception instanceof UnauthenticatedException) {
            errorMsg = "授权访问失败";
            return Result.fail(ResultCode.UN_AUTHORIZED, errorMsg);
        } else if (exception instanceof UnauthorizedException) {
            errorMsg = "没有权限访问异常";
            return Result.fail(ResultCode.FORBIDDEN, errorMsg);
        }
        log.error(errorMsg, exception);
        return Result.fail(ResultCode.UN_AUTHORIZED, errorMsg);
    }

    /***
     *
     * @description: 捕获请求的http类型，参数格式等不正确的错误
     *
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({
            MethodNotAllowedException.class,
            HttpMediaTypeException.class
    })
    public Result handleMessageExceptions(Exception exception) {
        if (exception instanceof HttpMediaTypeNotSupportedException) {
            HttpMediaTypeNotSupportedException ex = (HttpMediaTypeNotSupportedException) exception;
            String errorMsg = ex.getMessage();
            log.error(errorMsg, exception);
            return Result.fail(ResultCode.MEDIA_TYPE_UNSUPPORTED, errorMsg);
        } else {
            MethodNotAllowedException ex = (MethodNotAllowedException) exception;
            String errorMsg = ex.getMessage();
            log.error(errorMsg, exception);
            return Result.fail(ResultCode.MESSAGE_METHOD_NOT_ALLOWED, errorMsg);
        }
    }

    /***
     *
     * @description: 捕获请求的http类型，参数格式等不正确的错误
     *
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            BindException.class,
            BadRequestException.class
    })
    public Result handleParameterExceptions(Exception exception) {
        String errorMsg = "请求参数错误";
        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;
            BindingResult bindingResult = ex.getBindingResult();
            errorMsg = handleBindErrors(bindingResult);
        } else if (exception instanceof BindException) {
            BindException bindException = (BindException) exception;
            BindingResult bindingResult = bindException.getBindingResult();
            errorMsg = handleBindErrors(bindingResult);
        } else if (exception instanceof BadRequestException) {
            errorMsg = exception.getMessage();
        }
        log.error(errorMsg, exception);
        Result webResponse = Result.fail(ResultCode.BAD_REQUEST, errorMsg);
        return webResponse;
    }

    /**
     * 业务层普通异常处理
     *
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = BusinessException.class)
    public Result handleServiceExceptionHandler(BusinessException exception) {
        final String errorMsg = null != exception.getMessage() ? exception.getMessage() : "请求业务处理失败";
        log.error(errorMsg, exception);
        return Result.fail(ResultCode.BUSSINESS_ERROR, errorMsg);
    }

    /**
     * 最后的默认的异常处理
     *
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public Result handleExceptionHandler(Exception exception) {
        final String errorMsg = null != exception.getMessage() ? exception.getMessage() : "请求未知严重异常";
        log.error(errorMsg, exception);
        return Result.fail(ResultCode.EXCEPTION_ERROR, errorMsg);
    }

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        String returnTypeName = returnType.getParameterType().getName();
        return !ResponseEntity.class.getName().equals(returnTypeName);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response
    ) {
        JVFastCommonProperties.Encrypt encrypt = jvFastCommonProperties.getEncrypt();
        boolean enabledEncrypt = encrypt.getEnabled();
        String returnTypeName = returnType.getParameterType().getName();
        String requestUrl = request.getURI().getPath();
        if (Result.class.getName().equals(returnTypeName)) {
            Result bodyResponse = (Result) body;
            bodyResponse.setPath(requestUrl);
            // 进行加密操作
            String encodeWebResponse = encodeWebResponse(enabledEncrypt, bodyResponse);
            if (encodeWebResponse != null) {
                return encodeWebResponse;
            }
            return bodyResponse;
        }
        String voidType = "void";
        if (voidType.equals(returnTypeName)) {
            Result voidWebResponse = Result.success("");
            String encodeWebResponse = encodeWebResponse(enabledEncrypt, voidWebResponse);
            if (encodeWebResponse != null) {
                return encodeWebResponse;
            }
            return voidWebResponse;
        }
        // Not json data ,just return the data
        if (!selectedContentType.includes(MediaType.APPLICATION_JSON_UTF8)
                || !selectedContentType.includes(MediaType.APPLICATION_JSON)
        ) {
            return body;
        }
        Result webResponse = Result.success(body, requestUrl);
        // 进行加密操作
        String encodeWebResponse = encodeWebResponse(enabledEncrypt, webResponse);
        if (encodeWebResponse != null) {
            return encodeWebResponse;
        }
        return webResponse;
    }

    private String handleBindErrors(BindingResult bindingResult) {
        String errorMsg = "";
        if (bindingResult.hasErrors()) {
            // 返回错误结果
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errorMessages = Lists.newArrayList();
            fieldErrors.forEach(fieldError -> {
                String fieldErrorDefaultMessage = fieldError.getDefaultMessage();
                errorMessages.add(fieldErrorDefaultMessage);
            });

            errorMsg = errorMessages.stream().collect(Collectors.joining(","));
        }
        return errorMsg;
    }

    private String encodeWebResponse(boolean enabledEncrypt, Result webResponse) {
        // 进行加密操作
        if (enabledEncrypt) {
            JVFastCommonProperties.Encrypt encrypt = jvFastCommonProperties.getEncrypt();
            String aesSecretKey = encrypt.getSecretKey();
            String encodeJsonResponse = null;
            try {
                String strJsonResponse = JacksonUtil.toStr(webResponse);
                encodeJsonResponse = EncryptUtil.AESEncode(strJsonResponse, aesSecretKey);
            } catch (Exception e) {
                System.out.println("加密返回结果报错: " + e.getMessage());
            }
            return encodeJsonResponse;
        }
        return null;
    }
}
