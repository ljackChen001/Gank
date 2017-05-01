package com.ui;

import com.api.RentalCar;
import com.base.BaseModel;
import com.base.BasePresenter;
import com.base.BaseView;
import com.entity.BaseRespnseData;

import io.reactivex.Flowable;

/**
 * Created by chenbaolin on 2017/4/7.
 */

public interface MainContract {

    interface View extends BaseView {

        void onSucceed(Object data);

        void onFail(String err);

    }
    interface Model extends BaseModel {
        Flowable<BaseRespnseData<RentalCar>> getMyCollectCars();
    }

    abstract class Presenter extends BasePresenter<BaseView, Model> {

        public abstract  void getMyCollectCars();

    }
}
