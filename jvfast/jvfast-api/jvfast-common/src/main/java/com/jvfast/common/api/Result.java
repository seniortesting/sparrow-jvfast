package com.jvfast.common.api;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -7604766932017737115L;
    protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS Z";

    @JsonProperty(
            value = "timestamp",
            index = 1
    )
    private String timestamp;

    @JsonProperty(
            value = "path",
            index = 2
    )
    private String path;

    @JsonProperty(
            value = "code",
            index = 3
    )
    private int code;
    @JsonProperty(
            value = "message",
            index = 4
    )
    private String message;

    @JsonProperty(
            value = "message",
            index = 5
    )
    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(
            value = "data",
            index = 6
    )
    private T data;

    private Result(ResultCode responseCode, String msg, T data, String path) {
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        this.path = path;
        this.code = responseCode.getCode();
        this.message = StrUtil.isNotEmpty(msg) ? msg : responseCode.getMsg();
        this.success = ResultCode.SUCCESS.getCode() == code;
        this.data = data;
    }

    public static <T> Result<T> status(boolean flag) {
        return flag ? success(null) : fail(ResultCode.BUSSINESS_ERROR);
    }

    /**
     * 成功
     *
     * @param data
     * @return org.jvfast.api.Result<T>
     * @author Walter Hu
     * @date 2019/12/8
     * @since 1.8
     */
    public static <T> Result<T> success(T data) {
        return success(data, null);
    }

    public static <T> Result<T> success(String msg) {
        return success(null, msg);
    }

    public static <T> Result<T> success(T data, String msg) {
        return success(msg, data, null);
    }

    public static <T> Result<T> success(String msg, T data, String path) {
        return new Result(ResultCode.SUCCESS, msg, data, path);
    }

    /**
     * 失败
     *
     * @param code
     * @return org.jvfast.api.Result<T>
     * @author Walter Hu
     * @date 2019/12/8
     * @since 1.8
     */
    public static <T> Result<T> fail(ResultCode code) {
        return fail(code, null);
    }

    public static <T> Result<T> fail(String msg) {
        return fail(ResultCode.BUSSINESS_ERROR, msg);
    }

    public static <T> Result<T> fail(ResultCode code, String msg) {
        return fail(code, msg, null);
    }

    public static <T> Result<T> fail(ResultCode code, String msg, T data) {
        return fail(code, msg, data, null);
    }

    public static <T> Result<T> fail(ResultCode code, String msg, T data, String path) {
        return new Result(code, msg, data, path);
    }
}
