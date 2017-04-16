package com.ui.login;

import com.App;
import com.api.RetrofitUtil;
import com.base.util.LogUtils;
import com.base.util.MD5Util;
import com.base.util.WiFiIpUtils;
import com.entity.LoginResult;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by chenbaolin on 2017/4/16.
 */

public class LoginPresenter extends LoginContrat.Presenter {
    private String timestamp = String.valueOf(System.currentTimeMillis());
    private String wifiip = WiFiIpUtils.getWIFIiP(App.getAppContext());

    public LoginPresenter(LoginContrat.View view) {
        mView = view;
        mModel = new LoginModel();
    }


    @Override
    public void login(String userPhone, String code) {
        ResourceSubscriber resourceSubscriber = new ResourceSubscriber<LoginResult>() {
            @Override
            public void onNext(LoginResult result) {
                LogUtils.d("result请求成功");
                if (result != null) {
                    LogUtils.d("有数据有数据有数据有数据有数据");
                    mView.onSucceed(result);
                } else {
                    LogUtils.d("没 -----------有数据有数据有数据有数据有数据");
                }
            }

            @Override
            public void onError(Throwable t) {
                mView.onFail(t.getMessage());
                onComplete();
            }

            @Override
            public void onComplete() {
                this.dispose();
            }
        };
        addSubscription(RetrofitUtil.getInstance().startObservable(
                mModel.login(userPhone,
                        timestamp,
                        "1234",
                        "",
                        wifiip,
                        "02",
                        code,
                        MD5Util.md5(userPhone + "al" + timestamp + "1234" + wifiip + code)
                                .toUpperCase()),
                resourceSubscriber));
    }
}
