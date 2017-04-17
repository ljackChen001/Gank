package com.base;


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
    protected void addSubscription(Disposable disposable) {
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


}
