package com.ui;

import android.widget.TextView;
import android.widget.Toast;

import com.base.BaseActivity;
import com.model.Gank;
import com.ui.gank.R;

import java.util.List;
import java.util.Random;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    TextView tv1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
       tv1= (TextView) findViewById(R.id.tv1);
       tv1.setOnClickListener(v -> mPresenter.getGankData());

    }

    @Override
    protected MainPresenter onCreatePresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void onSucceed(Gank data) {
        Toast.makeText(this, "请求成功", Toast.LENGTH_SHORT).show();
        List<Gank.Result> results = data.getResults();
        tv1.setText(results.get(new Random().nextInt(10)).toString());
    }

    @Override
    public void onFail(String err) {

    }

    @Override
    public void hideDialog() {

    }
}
