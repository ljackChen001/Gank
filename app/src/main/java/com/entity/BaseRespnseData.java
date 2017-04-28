package com.entity;

import java.io.Serializable;

/**
 * Created by chenbaolin on 2017/4/26.
 */

public class BaseRespnseData<T> implements Serializable{
    private String tokenStr;
    private T appUser;

    public String getTokenStr() {
        return tokenStr;
    }
    public BaseRespnseData setTokenStr(String tokenStr) {
        this.tokenStr = tokenStr;
        return this;
    }

    public T getAppUser() {
        return appUser;
    }

    public BaseRespnseData setAppUser(T appUser) {
        this.appUser = appUser;
        return this;
    }
}
