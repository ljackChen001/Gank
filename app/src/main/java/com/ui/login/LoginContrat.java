package com.ui.login;

import com.base.BaseModel;
import com.base.BasePresenter;
import com.base.BaseView;
import com.entity.BaseRespnseData;
import com.entity.HttpResult;
import com.entity.UserInfo;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by chenbaolin on 2017/4/16.
 */

public interface LoginContrat {

    interface View extends BaseView {
        void onSucceed(Object result);
        void onFail(String err);
    }
    interface Model extends BaseModel {
        Flowable<HttpResult<BaseRespnseData<UserInfo>>> login(String userPhone, String time, String appkeyId, String deviceToken,
                                                              String ip, String source, String verificationResponseCode, String additional);

        Observable<HttpResult> sendCode(String userPhone, String codeType);
    }

    abstract class Presenter extends BasePresenter<LoginContrat.View, LoginContrat.Model> {
        public abstract void login(String userPhone, String code);
        public abstract void sendeCode(String userPhone);
    }
}
