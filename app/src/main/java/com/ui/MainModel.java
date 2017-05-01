package com.ui;

import com.api.RentalCar;
import com.api.RetrofitUtil;
import com.base.helper.RxSchedulers;
import com.entity.BaseRespnseData;

import io.reactivex.Flowable;

/**
 * Created by chenbaolin on 2017/4/7.
 */
public class MainModel implements MainContract.Model {

    @Override
    public Flowable<BaseRespnseData<RentalCar>> getMyCollectCars() {
        return RetrofitUtil.getInstance().getApiService().getMyCollectCars()
                .compose(RxSchedulers.io_main_flowable())
                .compose(RetrofitUtil.handleLiveResult());
    }
    //    @Override
//    public Flowable<Gank> getGankData() {
//        return RetrofitUtil.getInstance().getApiService().getGankData("1").compose(RxSchedulers.<Gank>io_main());
//    }

}
