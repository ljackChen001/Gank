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

        //得到用户填写的信息
        String getUsername();

        String getPassword();


        //登录成功或失败后，返回信息的方法
        void showSuccessMsg(String s);

        void showFailedMsg(String s);

        //清楚数据
        void clearEditText();

        //        void showDialog();
        void onSucceed(LoginResult data);

        void onFail(String err);

        //        void hideDialog();

    }

    interface Model extends BaseModel {
        Flowable<LoginResult> login(String userPhone, String time, String appkeyId, String deviceToken,
                                    String ip, String source, String verificationResponseCode, String additional);
    }

    abstract class Presenter extends BasePresenter<LoginContrat.View, LoginContrat.Model> {
        public abstract void login(String userPhone, String code);
    }
}
