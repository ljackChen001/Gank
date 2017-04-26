package com.base.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.ui.gank.R;

import java.io.IOException;

import io.reactivex.subscribers.ResourceSubscriber;

public class BaseSubscriber<T> extends ResourceSubscriber<T> {

    protected Context mContext;

    public BaseSubscriber(Context context) {
        this.mContext = context;
    }

    @Override
    public void onNext(T t) {
    }


    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        Log.w("Subscriber onError", e);
        if (e instanceof HttpException) {
            // We had non-2XX http error
            Toast.makeText(mContext, mContext.getString(R.string.server_internal_error), Toast.LENGTH_SHORT).show();
        } else if (e instanceof IOException) {
            // A network or conversion error happened
            Toast.makeText(mContext, mContext.getString(R.string.cannot_connected_server), Toast.LENGTH_SHORT).show();
        } else if (e instanceof APIException) {
            APIException exception = (APIException) e;
            if (exception.isTokenExpried()) {
                //处理token失效对应的逻辑
            } else {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
