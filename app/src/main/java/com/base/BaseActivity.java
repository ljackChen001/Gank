package com.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.base.helper.RxBus;
import com.base.util.LogUtils;
import com.base.util.StatusBarUtil;
import com.ui.gank.R;
import com.view.widget.SwipeBackLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenbaolin on 2017/4/4.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    public P mPresenter;
    public Unbinder unbinder;
    public RxBus rxBus;
    public Context mContext;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.d("BaseActivity-->>", getClass().getSimpleName());
        super.onCreate(savedInstanceState);
        mContext=this;
        View rootView = getLayoutInflater().inflate(this.setLayoutResouceId(), null, false);
        setContentView(setLayoutResouceId(), rootView);
        unbinder = ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_48b54c), 0);
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
        }
        unbinder.unbind();
    }


    public void setContentView(int layoutResID, View rootView) {
        boolean isNotSwipeBack = layoutResID == R.layout.activity_main || layoutResID == R.layout.activity_welcome;
        super.setContentView(isNotSwipeBack ? rootView : getContainer(rootView));
    }

    private View getContainer(View rootView) {
        rootView.setBackgroundColor(getResources().getColor(R.color.alpha_white));
//        rootView.setBackgroundResource(R.drawable.header);
        View container = getLayoutInflater().inflate(R.layout.activity_base, null, false);
        SwipeBackLayout swipeBackLayout = (SwipeBackLayout) container.findViewById(R.id.swipeBackLayout);
        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        View ivShadow = container.findViewById(R.id.iv_shadow);
        swipeBackLayout.addView(rootView);
        swipeBackLayout.setOnSwipeBackListener((fa, fs) -> ivShadow.setAlpha(1 - fs));
        return container;
    }


    public abstract int setLayoutResouceId();

    public abstract void initView();

    protected abstract P onCreatePresenter();


}
