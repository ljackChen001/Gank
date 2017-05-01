package com.base.exception;

import android.util.Log;

import com.base.util.ToastUtil;
import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.functions.Consumer;

/**
 * 网络异常
 * Created by chenbaolin on 2017/4/23.
 */

public class RxException<T extends Throwable> implements Consumer<T> {
    private static final String TAG = "RxException";

    private static final String SOCKE_TTIMEOUT_EXCEPTION = "网络连接超时，请检查您的网络状态，稍后重试";
    private static final String CONNECT_EXCEPTION = "网络连接异常，请检查您的网络状态";
    private static final String UNKNOWN_HOST_EXCEPTION = "网络异常，请检查您的网络状态";
    private static final String TOKEN_INVALID = "登录失效,请重新登录";
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    private Consumer<? super Throwable> onError;

    public RxException(Consumer<? super Throwable> onError) {
        this.onError = onError;
    }


    /**
     * @param t
     * @throws Exception
     */
    @Override
    public void accept(T t) throws Exception {
        APIException ex;
        if (t instanceof SocketTimeoutException) {
            Log.e(TAG, "onError: SocketTimeoutException----" + SOCKE_TTIMEOUT_EXCEPTION);
            ToastUtil.show(SOCKE_TTIMEOUT_EXCEPTION);
            onError.accept(new Throwable(SOCKE_TTIMEOUT_EXCEPTION));

        } else if (t instanceof ConnectException) {
            Log.e(TAG, "onError: ConnectException-----" + CONNECT_EXCEPTION);
            ToastUtil.show(CONNECT_EXCEPTION);
            onError.accept(new Throwable(CONNECT_EXCEPTION));

        } else if (t instanceof UnknownHostException) {
            Log.e(TAG, "onError: UnknownHostException-----" + UNKNOWN_HOST_EXCEPTION);
            ToastUtil.show(UNKNOWN_HOST_EXCEPTION);
            onError.accept(new Throwable(UNKNOWN_HOST_EXCEPTION));

        }else if (t instanceof IOException) {
            Log.e(TAG, "onError: IOException-----" + CONNECT_EXCEPTION);
            ToastUtil.show(CONNECT_EXCEPTION);
            onError.accept(new Throwable(CONNECT_EXCEPTION));

        } else if (t instanceof APIException) {
            APIException exception = (APIException) t;
            if (exception.isTokenExpried()) {
//                ToastUtil.show(TOKEN_INVALID);
                exception.setMsg("未登录");
                onError.accept(new Throwable(TOKEN_INVALID));
                //处理token失效对应的逻辑\
            }

        } else if (t instanceof HttpException) {//HTTP错误
            HttpException httpException = (HttpException) t;
            ex = new APIException(t, ResponseCode.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ToastUtil.show("服务器连接异常");
                    ex.setMsg("服务器连接异常");  //均视为网络错误
                    break;
            }
            onError.accept(ex);

        } else if (t instanceof JsonParseException
                || t instanceof JSONException
                || t instanceof ParseException) {
            ex = new APIException(t, ResponseCode.PARSE_ERROR);
            ex.setMsg("解析错误");            //均视为解析错误
            onError.accept(ex);

        } else {
            Log.e(TAG, "onError:----" + t.getMessage());
            ex = new APIException(t, ResponseCode.UNKNOWN);
            ex.setMsg("未知错误");          //未知错误
            onError.accept(t);
        }
    }
}
