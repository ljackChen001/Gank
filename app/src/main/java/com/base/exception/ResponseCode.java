package com.base.exception;

/**
 * Created by chenbaolin on 2017/4/29.
 * 服务器返回的状态码responseCode
 */
public class ResponseCode {
    public static final int SUCCESS = 0;
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络错误
     */
    public static final int NETWORD_ERROR = 1002;
    /**
     * 协议出错
     */
    public static final int HTTP_ERROR = 1003;
    /**
     * token失效 登录失败
     */
    public static final int TOKEN_INVALID = -99999;

    /**
     * 登录密码/验证码错误
     */
    public static final int PASSWORD_ERROR = -9;


}
