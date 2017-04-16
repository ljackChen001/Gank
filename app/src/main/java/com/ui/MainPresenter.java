package com.ui;

import com.api.RetrofitUtil;
import com.base.util.LogUtils;
import com.model.Gank;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by chenbaolin on 2017/4/7.
 */

public class MainPresenter extends MainContract.Presenter {

    public MainPresenter(MainContract.View view) {
        mView = view;
        mModel = new MainModel();
    }

    @Override
    public void getGankData() {
        LogUtils.i("SSS");
        ResourceSubscriber resourceSubscriber = new ResourceSubscriber<Gank>() {
            @Override
            public void onNext(Gank gank) {
                mView.onSucceed(gank);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                this.dispose();
            }
        };
        //订阅并把返回值添加到CompositeDisposable
        addSubscription(RetrofitUtil.getInstance().startObservable(mModel.getGankData(),resourceSubscriber));
    }
}
