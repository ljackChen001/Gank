package com.ui.login;

import android.widget.Button;

import com.App;
import com.api.RetrofitUtil;
import com.base.helper.RxSchedulers;
import com.base.util.LogUtils;
import com.base.util.MD5Util;
import com.base.util.WiFiIpUtils;
import com.entity.BaseRespnseData;
import com.entity.HttpResult;
import com.entity.UserInfo;
import com.ui.gank.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by chenbaolin on 2017/4/16.
 * 连接类
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
        ResourceSubscriber resourceSubscriber = new ResourceSubscriber<HttpResult<BaseRespnseData<UserInfo>>>() {
            @Override
            public void onNext(HttpResult<BaseRespnseData<UserInfo>> result) {
                LogUtils.d("result请求成功::");
                LogUtils.d(result.getResponseData().getAppUser().getUserMobile());
                if (result != null) {
                    if (result.getResponseCode() == 0) {
                        mView.onSucceed(result.getResponseData().getAppUser());
                    } else {
                        mView.onFail("账号或密码错误,请重新输入！");
                    }
                }
            }
            @Override
            public void onError(Throwable t) {
                LogUtils.d(t.getMessage());
                LogUtils.d("网络连接失败");
                mView.onFail(t.getMessage());
                onComplete();
            }

            @Override
            public void onComplete() {
                this.dispose();
            }
        };
        /**
         * 登录和注册
         */
        addSubscription(RetrofitUtil.getInstance().startObservable(
                mModel.login(userPhone, timestamp, "1234", "", wifiip, "02", code,
                        MD5Util.md5(userPhone + "al" + timestamp + "1234" + wifiip + code).toUpperCase()),
                resourceSubscriber));
    }

    @Override
    public void sendeCode(String userPhone) {
//        Disposable disposable = mModel.sendCode(userPhone, 2 + "")
//                .subscribe(result -> {
//                    if (result != null && result.getResponseCode() == 20000) {
//                        LogUtils.d(result.getResponseDescription());
//                        mView.onSucceed(result);
//                    } else {
//                        mView.onFail("验证码获取失败！");
//                    }
//                });
//        addSubscription(disposable);

//        mModel.sendCode(userPhone,"2")
//                .subscribe(new BaseSubscriber<HttpResult>());
    }

    /**
     * 点击获取验证码，10S倒计时，利用Rxjava进行线程切换
     */
    public void getSureCode(Button view) {
        final long count = 60;
        Disposable disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .compose(RxSchedulers.io_main_observable())
                .take(count + 1)
                .map(aLong -> count - aLong)
                .doOnSubscribe(disposable1 -> {
                    view.setEnabled(false);
                    view.setBackgroundResource(R.drawable.gray_btn_normal);
                }).subscribe(aLong -> {
                    view.setText("剩余(" + aLong + ")秒");
                    if (aLong == 0) {
                        view.setEnabled(true);
                        view.setText("获取验证码");
                        view.setBackgroundResource(R.drawable.ripple_bg);
                    }
                });
        addSubscription(disposable);
    }
}
