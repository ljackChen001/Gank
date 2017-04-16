package com.entity;

/**
 * Created by chenbaolin on 2017/1/11.
 */

public class BaseResponse<T> {
    private boolean success;//请求是否成功
    private int resultCode;//状态吗
    private String msg;//返回的提示消息
    private T data;//泛型接收result

    public boolean isSuccess() {
        return success;
    }

    public BaseResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public int getResultCode() {
        return resultCode;
    }

    public BaseResponse setResultCode(int resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BaseResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public BaseResponse setData(T data) {
        this.data = data;
        return this;
    }
}