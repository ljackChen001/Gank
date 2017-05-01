package com.ui;

import com.api.RentalCar;
import com.base.util.LogUtils;
import com.entity.BaseRespnseData;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbaolin on 2017/4/7.
 */

public class MainPresenter extends MainContract.Presenter {

    public MainPresenter(MainContract.View view) {
        mView = view;
        mModel = new MainModel();
    }

    @Override
    public void getMyCollectCars() {
        mModel.getMyCollectCars().subscribe(new Consumer<BaseRespnseData<RentalCar>>() {
            @Override
            public void accept(BaseRespnseData<RentalCar> result) throws Exception {
                LogUtils.d(result.toString()+"");
                if(result!=null){
                    LogUtils.d(result.toString());
                }
            }
        });
    }

    //    @Override
    //    public void getGankData() {
    //        LogUtils.i("SSS");
    //        ResourceSubscriber resourceSubscriber = new ResourceSubscriber<Gank>() {
    //            @Override
    //            public void onNext(Gank gank) {
    ////                mView.onSucceed(gank);
    //            }
    //
    //            @Override
    //            public void onError(Throwable t) {
    //
    //            }
    //
    //            @Override
    //            public void onComplete() {
    //                this.dispose();
    //            }
    //        };
    //        //订阅并把返回值添加到CompositeDisposable
    //        addSubscription(RetrofitUtil.getInstance().startObservable(mModel.getGankData(),resourceSubscriber));
    //    }
}
