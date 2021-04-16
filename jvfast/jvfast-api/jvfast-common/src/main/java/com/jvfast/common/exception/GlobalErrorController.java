package com.jvfast.common.exception;

import com.jvfast.common.api.Result;
import com.jvfast.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Walter
 * @see org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
 * @see org.springframework.boot.autoconfigure.web.ErrorProperties
 * @see org.springframework.boot.autoconfigure.web.ServerProperties
 */
@RestController
@Slf4j
public class GlobalErrorController implements ErrorController {
    private static final String PATH = "/error";

    private ErrorAttributes errorAttributes;

    public GlobalErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping(value = PATH)
    public Result handle404Exception(HttpServletRequest request, HttpServletResponse response) {
        final String requestURI = request.getRequestURI();
        final int status = response.getStatus();
        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> body = errorAttributes.getErrorAttributes(webRequest, true);
        String msg = body.get("message") != null ? body.get("message").toString() : "fail";
        log.error("请求地址: {}, /error/异常返回结果: {}", requestURI, body);
        /* int internalStatusCode = (int) body.get("active"); */
        body.remove("active");
        body.remove("timestamp");
        body.remove("message");

        ResultCode webResponseCode = ResultCode.NOT_FOUND;
        Result webResponse = Result.fail(webResponseCode, msg, null, requestURI);
        return webResponse;
    }
}
