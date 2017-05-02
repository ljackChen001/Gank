package com.entity;

import com.base.exception.ResponseCode;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by chenbaolin on 2017/1/11.
 */

public class HttpResult<T> implements Serializable {
    @SerializedName("responseCode")
    private int responseCode;//状态码
    @SerializedName("responseDescription")
    private String responseDescription;//返回的提示消息
    @SerializedName("responseData")
    private T responseData;//泛型接收result


    public boolean isSuccess() {
        return ResponseCode.SUCCESS == responseCode;
    }

    public boolean isTokenInvalid() {
        return ResponseCode.TOKEN_INVALID == responseCode;
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