package com.mingtai.base.model;

/**
 * Created by zkzc-mcy on 2017/9/12.
 * 接口返回结果封装类
 */
public class ApiResult<T extends Object> {

    public static final int ERROR_VALID = 3;
    public static final int ERROR_AUTH = 2;
    public static final int ERROR_PERM = 4;
    public static final int ERROR_INNER = 5;

    public static final String DATE_FORMATE = "yyyy-MM-dd HH:mm:ss";

    private boolean success = true;

    private int code = 1;

    private String message = "success";

    private T data;

    public ApiResult(){

    }

    public ApiResult(T data){
        this.data = data;
    }

    public ApiResult(int code, String message){
        this.code = code;
        if(code != 1){
            this.success = false;
        }
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
