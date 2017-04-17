package com.ui.login;

import com.base.BaseModel;
import com.base.BasePresenter;
import com.base.BaseView;
import com.entity.LoginResult;

import io.reactivex.Flowable;

/**
 * Created by chenbaolin on 2017/4/16.
 */

public interface LoginContrat {

    interface View extends BaseView {

        void onSucceed(LoginResult data);

        void onFail(String err);


    }

    interface Model extends BaseModel {
        Flowable<LoginResult> login(String userPhone, String time, String appkeyId, String deviceToken,
                                    String ip, String source, String verificationResponseCode, String additional);
    }

    abstract class Presenter extends BasePresenter<LoginContrat.View, LoginContrat.Model> {
        public abstract void login(String userPhone, String code);
    }
}
