package com.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chenbaolin on 2017/4/26.
 */

public class BaseRespnseData<T>{
    @SerializedName("tokenStr")
    private String tokenStr;
    @SerializedName("appUser")
    private UserInfo appUser;

    public String getTokenStr() {
        return tokenStr;
    }
    public BaseRespnseData setTokenStr(String tokenStr) {
        this.tokenStr = tokenStr;
        return this;
    }

    public UserInfo getAppUser() {
        return appUser;
    }

    public BaseRespnseData setAppUser(UserInfo appUser) {
        this.appUser = appUser;
        return this;
    }
}
