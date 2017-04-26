package com.base.helper;

import com.Constants;

/**
 * Created by chenbaolin on 2017/4/15.
 */

public class APIException extends RuntimeException  {
    private int mErrorCode;

    public APIException(int errorCode, String errorMessage) {
        super(errorMessage);
        mErrorCode = errorCode;
    }

    /**
     * 判断是否是token失效
     *
     * @return 失效返回true, 否则返回false;
     */
    public boolean isTokenExpried() {
        return mErrorCode == Constants.TOKEN_EXPRIED;
    }
}