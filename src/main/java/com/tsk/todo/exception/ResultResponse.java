package com.tsk.todo.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Tsk
 * @date 2024/6/26 0026
 */
@Setter
@Getter
public class ResultResponse<T> implements Serializable {
    private static final long serialVersionUID = -1133637474601003587L;

    /**
     * 接口响应状态码
     */
    private Integer code;

    /**
     * 接口响应信息
     */
    private String msg;

    /**
     * 接口响应的数据
     */
    private T data;

    public static <T> ResultResponse<T> success(T data) {

        ResultResponse<T> response = new ResultResponse<>();
        response.setData(data);
        response.setCode(StatusEnum.SUCCESS.code);
        return response;
    }
    public static <T> ResultResponse<T> error(StatusEnum statusEnum) {
        return error(statusEnum, statusEnum.message);
    }

    public static <T> ResultResponse<T> error(StatusEnum statusEnum, String errorMsg) {
        ResultResponse<T> response = new ResultResponse<>();
        response.setCode(statusEnum.code);
        response.setMsg(errorMsg);
        return response;
    }
}
