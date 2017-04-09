package com.ui;

import com.api.Api;
import com.base.helper.RxSchedulers;
import com.model.Gank;

import io.reactivex.Flowable;

/**
 * Created by chenbaolin on 2017/4/7.
 */
public class MainModel implements MainContract.Model {
//    @Override
//    public Flowable<Gank> getGankData() {
//        return Api.getInstance().getApiService().getGankData("1").compose(RxSchedulers.<Gank>io_main());
//    }

    @Override
    public Flowable<Gank> getGankData() {
        return Api.getInstance().getApiService()
                .getGankData("1")
                .compose(RxSchedulers.io_main());
    }

}
