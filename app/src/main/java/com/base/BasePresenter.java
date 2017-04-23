package com.base;


import com.entity.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by chenbaolin on 2017/4/3.
 * V <====> P <====> M
 */

public abstract class BasePresenter<V extends BaseView, M extends BaseModel> {
    protected V mView;
    protected M mModel;
    //对RxJava进行管理
    private CompositeDisposable mCompositeDisposable;

    //加入到订阅列表
    public void addSubscription(Disposable disposable) {
        if (disposable == null) return;
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void dispose(Disposable disposable) {
        if (disposable != null) {
            mCompositeDisposable.delete(disposable);
        }
    }

    //取消所有的订阅防止内存泄漏
    protected void unSubscribe() {
        if (null != mView) {
            mView = null;
        }
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }
    /**
     * Rx优雅处理服务器返回
     *
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {
        return upstream ->{
            return upstream.flatMap(result -> {
                        if (result.isSuccess()) {
                            return createData(result.getResponseData());
                        } else if (result.isTokenInvalid()) {
                            //处理token失效，tokenInvalid方法在BaseActivity 实现
//                            tokenInvalid();
                        } else {
                            return Observable.error(new Exception(result.getResponseDescription()));
                        }
                        return Observable.empty();
                    }

            );
        };
    }

    private <T> Observable<T> createData(final T t) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(t);
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

}
