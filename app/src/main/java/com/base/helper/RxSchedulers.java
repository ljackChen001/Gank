package com.base.helper;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 封装 Rx 线程
 * Created by chenbaolin on 16/5/6.
 */
public  class RxSchedulers {
    public static <T> FlowableTransformer<T, T> io_main_flowable() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new RxException<>(Throwable::printStackTrace))
                .onErrorResumeNext(Flowable.<T>empty());
    }

    public static <T> ObservableTransformer<T, T> io_main_observable() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new RxException<>(Throwable::printStackTrace))
                .onErrorResumeNext(Observable.<T>empty());
    }

}
