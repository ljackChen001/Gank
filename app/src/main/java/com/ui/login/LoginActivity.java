package com.ui.login;

import android.support.design.widget.Snackbar;
import android.widget.Button;
import android.widget.LinearLayout;

import com.base.BaseActivity;
import com.base.util.LogUtils;
import com.entity.LoginResult;
import com.ui.gank.R;

import butterknife.BindView;

/**
 * Created by chenbaolin on 2017/4/16.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContrat.View {
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.layout)
    LinearLayout layout;


    @Override
    public int setLayoutResouceId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        login.setOnClickListener(v -> mPresenter.login("18161194339", "1"));
    }

    @Override
    protected LoginPresenter onCreatePresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void showSuccessMsg(String s) {
        Snackbar.make(layout, s, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showFailedMsg(String s) {

    }

    @Override
    public void clearEditText() {

    }

    @Override
    public void onSucceed(LoginResult data) {
        LoginResult.ResponseDataEntity.AppUserEntity results=data.getResponseData().getAppUser();
        if (data != null) {
            LogUtils.d(results.getUserType()+"");
            LogUtils.d("getResults：：" + data.getResponseData().getTokenStr());
        }
    }

    @Override
    public void onFail(String err) {

    }
}
