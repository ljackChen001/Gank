package com.ui.login;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.base.BaseActivity;
import com.base.util.LogUtils;
import com.base.util.NetWorkUtil;
import com.base.util.SnackbarUtils;
import com.base.util.ValidateUtils;
import com.entity.UserInfo;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.ui.gank.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by chenbaolin on 2017/4/16.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContrat.View {
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    public int setLayoutResouceId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        //校验账号密码
        Observable<CharSequence> ObservablePhone = RxTextView.textChanges(etPhone);
        Observable<CharSequence> ObservableCode = RxTextView.textChanges(etCode);
        Observable.combineLatest(ObservablePhone, ObservableCode, (charSequence, charSequence2)
                -> ValidateUtils.isMobileNo(charSequence.toString()) && ValidateUtils.isPassword
                (charSequence2.toString())).subscribe(aBoolean -> {
            if (aBoolean) {
                btnLogin.setBackgroundResource(R.drawable.ripple_bg);
                btnLogin.setEnabled(true);
            } else {
                if (ValidateUtils.isMobileNo(etPhone.getText().toString())) {
                    btnGetCode.setBackgroundResource(R.drawable.ripple_bg);
                    btnGetCode.setEnabled(true);
                } else {
                    btnGetCode.setBackgroundResource(R.drawable.gray_btn_normal);
                    btnGetCode.setEnabled(false);
                }
                btnLogin.setBackgroundResource(R.drawable.gray_btn_normal);
                btnLogin.setEnabled(false);

            }
        });
        RxView.clicks(btnGetCode).share().throttleFirst(10, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (!TextUtils.isEmpty(etPhone.getText()) && NetWorkUtil.isNetConnected(this)) {
                        mPresenter.sendeCode(etPhone.getText().toString());
                        mPresenter.getSureCode(btnGetCode);
                    } else {
                        SnackbarUtils.showSnacker(layout, "网络异常，请检查您的网络状态");
                    }
                });
        RxView.clicks(btnLogin).throttleFirst(5, TimeUnit.SECONDS)
                .subscribe(o -> mPresenter.login(etPhone.getText().toString(), etCode.getText().toString()));
    }

    @Override
    protected LoginPresenter onCreatePresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void onSucceed(Object result) {

//        HttpResult httpResult= (HttpResult) result;
//        BaseRespnseData respnseData= (BaseRespnseData) httpResult.getResponseData();

        UserInfo userInfo= (UserInfo) result;
        LogUtils.d(userInfo.getUserMobile()+"ssssss");

    }
//        HttpResult httpResult= (HttpResult) result;
//        BaseRespnseData baseRespnseData= (BaseRespnseData) httpResult.getResponseData();
//        UserInfo userInfo= (UserInfo) baseRespnseData.getData();
//        LogUtils.d("成功1：" + userInfo.getUserMobile());
//        BaseRespnseData httpResult = (BaseRespnseData) ((HttpResult) result).getResponseData();
//        LogUtils.d("成功：" + ((UserInfo) httpResult.getData()).getUserMobile() + "");
//        HttpResult<BaseRespnseData<UserInfo>> loginResult = (HttpResult<BaseRespnseData<UserInfo>>) result;
//                    LogUtils.d("成功了2：" + loginResult.getResponseData().getData().getUserMobile() + "");
//        if (httpResult.getData() instanceof UserInfo) {
//            HttpResult<BaseRespnseData<UserInfo>> loginResult = (HttpResult<BaseRespnseData<UserInfo>>) result;
//            LogUtils.d("成功了2：" + ((UserInfo) httpResult.getData()).getUserMobile() + "");
//            ActivityCollector.getInstance().finishActivity();
//        }



    @Override
    public void onFail(String err) {
        Snackbar.make(layout, err, 2000).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void hideDialog() {
    }
}
