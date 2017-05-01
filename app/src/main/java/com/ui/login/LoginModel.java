package com.ui.login;

import com.api.RetrofitUtil;
import com.base.helper.RxSchedulers;
import com.entity.BaseRespnseData;
import com.entity.HttpResult;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by chenbaolin on 2017/4/16.
 */

public class LoginModel implements LoginContrat.Model {


    @Override
    public Flowable <HttpResult<BaseRespnseData>> login(String userPhone, String time, String appkeyId, String
            deviceToken, String ip, String source, String verificationResponseCode, String additional) {
        return RetrofitUtil.getInstance().getApiService()
                .login(userPhone,
                        time,
                        appkeyId,
                        deviceToken,
                        ip,
                        source,
                        verificationResponseCode,
                        additional)
                .compose(RxSchedulers.io_main_flowable());
    }

    @Override
    public Observable<HttpResult> sendCode(String userPhone, String codeType) {
        return RetrofitUtil.getInstance().getApiService()
                .sendCode(userPhone, codeType)
                .compose(RxSchedulers.io_main_observable());
    }
}
