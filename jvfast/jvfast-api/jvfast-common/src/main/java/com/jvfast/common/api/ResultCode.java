package com.jvfast.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author Administrator
 */

@Getter
@AllArgsConstructor
public enum ResultCode implements IResultCode {

    /**
     * 操作成功
     */
    SUCCESS(0, "操作成功"),
    /**
     * 数据格式错误
     * 405
     */
    MESSAGE_METHOD_NOT_ALLOWED(405, "请求方法错误"),
    /**
     * 数据格式错误
     * 415
     */
    MEDIA_TYPE_UNSUPPORTED(415, "请求数据格式错误"),
    /**
     * 参数校验失败
     * 400
     */
    BAD_REQUEST(400, "参数校验错误"),
    /**
     * 业务异常
     * 500
     */
    BUSSINESS_ERROR(500, "业务操作失败"),

    /**
     * 503
     *
     * @author Walter Hu
     */
    DATABASE_ERROR(503, "数据处理失败"),
    /**
     * 404 没找到请求
     */
    NOT_FOUND(404, "请求资源无法找到错误"),
    /**
     * 重复记录
     * 409
     */
    DUPLICATED_ERROR(409, "重复提交错误"),
    /**
     * 未捕获的异常
     * 502
     */
    EXCEPTION_ERROR(502, "未捕获的数据异常"),
    /**
     * 请求未授权
     * 401
     */
    UN_AUTHORIZED(401, "未授权错误"),
    /**
     * 请求没有权限
     * 403
     */
    FORBIDDEN(403, "权限不足错误"),
    /**
     * 超限次数
     */
    LIMIT_EXCEED(1111, "次数超限错误"),

    ;

    private final int code;
    private final String msg;
}
