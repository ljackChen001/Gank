package com.ui.login;

import com.api.RetrofitUtil;
import com.base.helper.RxSchedulers;
import com.base.util.LogUtils;
import com.entity.LoginResult;

import io.reactivex.Flowable;

/**
 * Created by chenbaolin on 2017/4/16.
 */

public class LoginModel implements LoginContrat.Model {

    @Override
    public Flowable<LoginResult> login(String userPhone, String time, String appkeyId, String deviceToken, String ip, String
            source, String verificationResponseCode, String additional) {
        LogUtils.d("LoginModel:"+time,ip);
        return RetrofitUtil.getInstance().getApiService()
                .login(userPhone,
                        time,
                        appkeyId,
                        deviceToken,
                        ip,
                        source,
                        verificationResponseCode,
                        additional)
                .compose(RxSchedulers.io_main());
    }
}
