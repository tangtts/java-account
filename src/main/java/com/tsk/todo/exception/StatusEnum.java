package com.tsk.todo.exception;

/**
 * @author Tsk
 * @date 2024/6/26 0026
 */
public enum StatusEnum {
    SUCCESS(200 ,"请求处理成功"),
    UNAUTHORIZED(401 ,"用户认证失败"),
    FORBIDDEN(403 ,"权限不足"),
    SERVICE_ERROR(500, "服务器去旅行了，请稍后重试"),
    PARAM_INVALID(1000, "无效的参数"),
    SQL_ERROR(1001, "数据库异常"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许")
    ;
    public final Integer code;

    public final String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

