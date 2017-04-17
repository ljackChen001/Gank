package com.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.base.helper.RxBus;
import com.base.util.ActivityCollector;
import com.base.util.LogUtils;
import com.base.util.StatusBarUtil;
import com.ui.gank.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenbaolin on 2017/4/4.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    public P mPresenter;
    public Unbinder unbinder;
    public RxBus rxBus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.d("BaseActivity-->>", getClass().getSimpleName());
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().addActivity(this);
        setContentView(setLayoutResouceId());
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_48b54c), 0);
        unbinder=ButterKnife.bind(this);
        if (onCreatePresenter() != null) {
            mPresenter = onCreatePresenter();
        }
        initView();
    }

    @Override
    protected void onDestroy() {
        LogUtils.i(getClass().getSimpleName() + "-->>:onDestroy:");
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
            unbinder.unbind();
        }
        ActivityCollector.getInstance().finishActivity();
    }

    public abstract int setLayoutResouceId();

    public abstract void initView();

    protected abstract P onCreatePresenter();


}
