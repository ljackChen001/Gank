package com.entity;

import java.io.Serializable;

/**
 * Created by chenbaolin on 2017/4/16.
 */

public class UserInfo implements Serializable{
    private String id;
    private String userName;
    private String userNickname;
    private String userSource;
    private String userLastLogin;
    private String userLogin;
    private String userPassword;
    private String userType;;
    private String userCreateTime;
    private String userAddress;
    private String userMobile;
    private String  userImg;
    private String userState;
    private String  userTag;
    private String userFinance;
    private String verificationCode;

    public String getId() {
        return id;
    }

    public UserInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public UserInfo setUserNickname(String userNickname) {
        this.userNickname = userNickname;
        return this;
    }

    public String getUserSource() {
        return userSource;
    }

    public UserInfo setUserSource(String userSource) {
        this.userSource = userSource;
        return this;
    }

    public String getUserLastLogin() {
        return userLastLogin;
    }

    public UserInfo setUserLastLogin(String userLastLogin) {
        this.userLastLogin = userLastLogin;
        return this;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public UserInfo setUserLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public UserInfo setUserPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public UserInfo setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public String getUserCreateTime() {
        return userCreateTime;
    }

    public UserInfo setUserCreateTime(String userCreateTime) {
        this.userCreateTime = userCreateTime;
        return this;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public UserInfo setUserAddress(String userAddress) {
        this.userAddress = userAddress;
        return this;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public UserInfo setUserMobile(String userMobile) {
        this.userMobile = userMobile;
        return this;
    }

    public String getUserImg() {
        return userImg;
    }

    public UserInfo setUserImg(String userImg) {
        this.userImg = userImg;
        return this;
    }

    public String getUserState() {
        return userState;
    }

    public UserInfo setUserState(String userState) {
        this.userState = userState;
        return this;
    }

    public String getUserTag() {
        return userTag;
    }

    public UserInfo setUserTag(String userTag) {
        this.userTag = userTag;
        return this;
    }

    public String getUserFinance() {
        return userFinance;
    }

    public UserInfo setUserFinance(String userFinance) {
        this.userFinance = userFinance;
        return this;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public UserInfo setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
        return this;
    }
}
