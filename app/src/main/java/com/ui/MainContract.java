package com.ui;

import com.base.BaseModel;
import com.base.BasePresenter;
import com.base.BaseView;
import com.model.Gank;

import io.reactivex.Flowable;

/**
 * Created by chenbaolin on 2017/4/7.
 */

public interface MainContract {

    interface View extends BaseView {

        void showDialog();

        void onSucceed(Gank data);

        void onFail(String err);

        void hideDialog();

    }

    interface Model extends BaseModel {
        Flowable<Gank> getGankData();
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getGankData();
    }
}
