package com.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.base.helper.RxBus;
import com.base.util.LogUtils;
import com.model.Gank;
import com.ui.gank.R;

/**
 * Created by chenbaolin on 2017/4/15.
 */

public class home extends AppCompatActivity {

    private RxBus rxBus = RxBus.getInstance();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        findViewById(R.id.tv1).setOnClickListener(v -> {

            Gank gank = new Gank("1", "1");

            rxBus.send("first", new String("ssssssssssssssssssssssssssssssssss"));
            LogUtils.i("发送数据！" + gank.toString());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i("前：："+rxBus.hasObservable());
        rxBus.removeObserverable("first");
        LogUtils.i("后：："+rxBus.hasObservable());
    }
}
