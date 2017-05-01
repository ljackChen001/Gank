package com.base.exception;

/**
 * Created by chenbaolin on 2017/4/15.
 */

public class APIException extends Exception {
    private int code;
    //异常信息
    private String msg;

    public APIException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;

    }
    public APIException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public APIException setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 判断是否是token失效
     *
     * @return 失效返回true, 否则返回false;
     */
    public boolean isTokenExpried() {
        return code == ResponseCode.TOKEN_INVALID;
    }
}