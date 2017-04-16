package com.base.helper;

/**
 * Created by chenbaolin on 2017/4/15.
 */

public class APIException extends Exception {
    public int code;
    public String message;

    public APIException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}