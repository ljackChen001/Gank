package com.base.helper;

import android.annotation.SuppressLint;

import com.entity.HttpResult;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Consumer;

/**
 * Created by chenbaolin on 2017/4/25.
 */

@SuppressLint("NewApi")
public class Respone1<T extends Observable> implements Consumer<T> {


    private Consumer<? super Observable> Observable;

    public Respone1(Consumer<? super Observable> Observable) {
        this.Observable = Observable;
    }


    /**
     * Rx优雅处理服务器返回
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<HttpResult<T>, T> handleResult() {
        return upstream -> {
            return upstream.flatMap(result -> {
                        if (result.isSuccess()) {
                            return createData(result.data);
                        } else if (result.isTokenInvalid()) {
                            //处理token失效，tokenInvalid方法在BaseActivity 实现
                            //tokenInvalid();
                        } else {
                            return io.reactivex.Observable.error(new Exception(result.msg));
                        }
                        return io.reactivex.Observable.empty();
                    }

            );
        };
    }

    private static <T> Observable<T> createData(final T t) {
        return io.reactivex.Observable.create(subscriber -> {
            try {
                subscriber.onNext(t);
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }


    @Override
    public void accept(T t) throws Exception {
    }
}
