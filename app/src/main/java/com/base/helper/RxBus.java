package com.base.helper;


import android.util.ArrayMap;

import com.base.RxBusResult;
import com.base.util.LogUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by chenbaolin on 2017/4/8.
 */

public class RxBus {
    private static RxBus defaultRxBus;
    private Subject<Object> bus;
    private final ArrayMap<String, Object> tags = new ArrayMap<>();

    private RxBus() {
        bus = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (null == defaultRxBus) {
            synchronized (RxBus.class) {
                if (null == defaultRxBus) {
                    defaultRxBus = new RxBus();
                }
            }
        }
        return defaultRxBus;
    }
    /**
     * 发送事件消息
     *
     * @param tag    用于区分事件
     * @param object 事件的参数
     */
    public void send(String tag, Object object) {
        if (hasObservable()) {
            bus.onNext(object);
            if (!tags.containsKey(tag)) {
                tags.put(tag, object);
            }
        }else{
        LogUtils.i("未订阅不能发送数据");
        }
    }
    /*
     * 是否有Observable订阅
     */
    public boolean hasObservable() {
        return bus.hasObservers();
    }

    /*
     * 转换为特定类型的Obserbale
     */
    public <T> Observable<T> toObservable(Class<T> type) {
        return bus.ofType(type);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    /**
     * 主线程中执行
     *
     * @param tag
     * @param rxBusResult
     */
    public void toObserverable(final String tag, final RxBusResult rxBusResult) {

            bus.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> {
                        if (tags.containsKey(tag)) {
                            rxBusResult.onRxBusResult(o);
                            LogUtils.i(tag+"订阅成功！"+tags.size());
                        }
                    });

    }

    /**
     * 移除tag
     *
     * @param tag
     */
    public void removeObserverable(String tag) {
        if (tags.containsKey(tag)) {
            tags.remove(tag);
        }
    }

    /**
     * 退出应用时，清空资源
     */
    public void release() {
        tags.clear();
        bus = null;
    }

}
