package com.base.helper;

import com.Constants;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chenbaolin on 2017/4/26.
 */

public class HttpStatus {

    @SerializedName("code")
    private int mCode;
    @SerializedName("message")
    private String mMessage;

    public int getCode() {
        return mCode;
    }

    public String getMessage() {
        return mMessage;
    }

    /**
     * API是否请求失败
     *
     * @return 失败返回true, 成功返回false
     */
    public boolean isCodeInvalid() {
        return mCode != Constants.WEB_RESP_CODE_SUCCESS;
    }
}
