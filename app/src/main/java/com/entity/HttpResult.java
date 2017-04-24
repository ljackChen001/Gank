package com.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by chenbaolin on 2017/4/25.
 */

public class HttpResult<T> implements Serializable {
    @SerializedName("responseCode")
    public String code;
    @SerializedName("responseDescription")
    public String msg;
    public boolean hasmore;
    @SerializedName("responseData")
    public T data;

    public static String SUCCESS = "0";
    public static String SIGN_OUT = "101";//token验证失败
    public static String SHOWTOAST = "102";//显示Toast

    public boolean isSuccess() {
        return SUCCESS.equals(code);
    }

    public boolean isTokenInvalid() {
        return SIGN_OUT.equals(code);
    }

    public boolean isShowToast() {
        return SHOWTOAST.equals(code);
    }

    public boolean hasMore() {
        return hasmore;
    }
}