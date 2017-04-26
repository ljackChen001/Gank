package com.entity;

/**
 * Created by chenbaolin on 2017/1/11.
 */

public class HttpResult<T>   {
    private int responseCode;//状态吗
    private String responseDescription;//返回的提示消息
    private T responseData;//泛型接收result

    public static String SUCCESS = "0";
    public static String SIGN_OUT = "101";//token验证失败
    public static String SHOWTOAST = "102";//显示Toast

    public boolean isSuccess() {
        return SUCCESS.equals(responseCode);
    }

    public boolean isTokenInvalid() {
        return SIGN_OUT.equals(responseCode);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public T getResponseData() {
        return responseData;
    }
}