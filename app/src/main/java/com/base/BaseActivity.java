package com.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.base.util.LogUtils;

/**
 *
 * Created by chenbaolin on 2017/4/4.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    public P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResouceId());
        LogUtils.i("BaseActivity.onCreate");
        if (onCreatePresenter() != null) {
            mPresenter = onCreatePresenter();
        }
        initView();

    }

    @Override
    protected void onDestroy() {
        LogUtils.i("onDestroy:");
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    public abstract int setLayoutResouceId();

    public abstract void initView();

    protected abstract P onCreatePresenter();


}
