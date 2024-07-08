package com.tsk.todo.resp;

import lombok.Data;

/**
 * @author Tsk
 * @date 2024/6/26 0026
 */
@Data
 public class CommonResp<T>  {
    private boolean success = true;

    private String message = "操作成功";

    private T data;

    public CommonResp(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public CommonResp(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public CommonResp(boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    public CommonResp(T data){
        this.data = data;
    }
    static public CommonResp success(String message,Object data){
        return  new CommonResp(message,data);
    }

    static public CommonResp success(Object data){
        return  new CommonResp(data);
    }

    static public CommonResp fail(String message){
        return new CommonResp(false,message);
    }
}
