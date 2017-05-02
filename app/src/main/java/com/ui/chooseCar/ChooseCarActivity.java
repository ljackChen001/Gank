package com.ui.chooseCar;

import android.os.Bundle;

import com.base.BaseActivity;
import com.base.BasePresenter;
import com.ui.gank.R;

public  class ChooseCarActivity extends BaseActivity {


    @Override
    public int setLayoutResouceId() {
        return R.layout.activity_choose_car;
    }

    @Override
    public void initView() {

    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
