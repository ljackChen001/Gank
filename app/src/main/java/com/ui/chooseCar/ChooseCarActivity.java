package com.ui.chooseCar;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.base.BaseActivity;
import com.base.BasePresenter;
import com.ui.gank.R;

import butterknife.BindView;

public class ChooseCarActivity extends BaseActivity {


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;


    @Override
    public int setLayoutResouceId() {
        return R.layout.activity_choose_car;
    }


    @Override
    public void initView() {
        tabLayout.addTab(tabLayout.newTab().setText("综合"));
        tabLayout.addTab(tabLayout.newTab().setText("价格"));
        tabLayout.addTab(tabLayout.newTab().setText("接单率"));
        tabLayout.addTab(tabLayout.newTab().setText("筛选"));

    }

    @Override
    protected void initToolBar() {
        setTitle("选择车辆");


    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

}
