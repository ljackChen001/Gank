package com.ui;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adapter.DrawerAdapter;
import com.base.BaseActivity;
import com.base.helper.RxBus;
import com.base.util.ActivityCollector;
import com.base.util.StatusBarUtil;
import com.base.util.TimeUtils;
import com.model.Gank;
import com.ui.component.cityselect.PickCityActivity;
import com.ui.component.wheelview.DateSelectWheel;
import com.ui.gank.R;
import com.ui.login.LoginActivity;
import com.view.widget.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_drawer)
    RecyclerView rvDrawer;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    @BindView(R.id.rootlayout)
    CoordinatorLayout rootlayout;
    @BindView(R.id.tool_left)
    ImageView toolLeft;
    @BindView(R.id.tool_content)
    TextView toolContent;
    @BindView(R.id.tool_right)
    ImageView toolRight;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.city_select_layout)
    LinearLayout citySelectLayout;
    @BindView(R.id.layout_getcar_time)
    LinearLayout layoutGetcarTime;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_month_return)
    TextView tvMonthReturn;
    @BindView(R.id.tv_week_return)
    TextView tvWeekReturn;
    @BindView(R.id.layout_return_car)
    LinearLayout layoutReturnCar;
    private long exitTime = 0;
    private String currentTime;

    @Override
    public int setLayoutResouceId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        StatusBarUtil.setColorNoTranslucentForDrawerLayout
                (this, drawerlayout, getResources().getColor(R.color.color_48b54c));
        rxBus = RxBus.getInstance();
        initAppBarTool();
        initDrawer();
        initBanner();
        initTime();
        citySelectLayout.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PickCityActivity.class)));
    }

    private void initTime() {
        currentTime = TimeUtils.getCurrentTimeInString();
        layoutGetcarTime.setOnClickListener(v -> {
            try {
                getCarTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        layoutReturnCar.setOnClickListener(v -> {
            try {
                returnCarTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    public void getCarTime() throws ParseException {
        new DateSelectWheel(mContext, 1).setCallBack(params -> {
//            String str1 = params[0];//月日
//            String str2 = params[1];//周几
//            String str3 = params[2];//时
//            String str4 = params[3];//分
//            String strQutime = TimeUtils.getNowYear() + "-" + str1;
//            long lonQutine = TimeUtils.getStringToDate(strQutime) + DEFAULT_TIME;
//            tvMonthReturn.setText(TimeUtils.getMonthDay(lonQutine));
//            tvWeekReturn.setText(TimeUtils.getWeekString(lonQutine) + TimeUtils.getNowFen());
//            tvDay.setText("1");
//            String QuTime = str3 + ":" + str4;
//            long LongQuTime = TimeUtils.getHHmm(QuTime);
//            long sysoutTime = TimeUtils.getHHmm(TimeUtils.getNowFen());
//            if (LongQuTime - sysoutTime < 120 * 60 * 1000) {
//                tvMonth.setText(TimeUtils.getMonthDay(systime));
//                long time = TimeUtils.getHHmm(TimeUtils.getNowFen()) + 120 * 60 * 1000;
//                tvWeek.setText(TimeUtils.getWeekString(systime) + TimeUtils.getStringHHmm(time));
//                ToastUtil.show("取车时间应大于当前2小时");
//            } else {
//                tvMonth.setText(str1);
//                tvWeek.setText(str2 + str3 + ":" + str4);
//                returnCarTime();
//            }
        });
    }

    public void returnCarTime() throws ParseException {
        new DateSelectWheel(mContext, 2).setCallBack(params -> {
//            String str1 = params[0];//月日
//            String str2 = params[1];//周几
//            String str3 = params[2];//时
//            String str4 = params[3];//分
//            String mTextquday = tvMonth.getText().toString();
//            String QuTime1 = tvWeek.getText().toString().substring(2);
//            long LongQuTime1 = TimeUtils.getStringToDateTime(TimeUtils.getNowYear() + "-" +
//                    mTextquday + " " + QuTime1);
//            long huanTime = TimeUtils.getStringToDateTime(TimeUtils.getNowYear() + "-" + str1 + " " +
//                    "" + str3 + ":" + str4);
//            String quday = mTextquday.substring(3);
//            String huanday = str1.substring(3);
//            if (huanTime - LongQuTime1 < 24 * 60 * 60 * 1000) {
//                ToastUtil.show("还车时间应大于取车时间24小时!");
//            } else {
//                if (huanTime - LongQuTime1 > 20 * 24 * 60 * 60 * 1000) {
//                    ToastUtil.show("还车时间不能超过20天");
//                } else {
//                    tvDay.setText((Integer.parseInt(huanday) - Integer.parseInt(quday)) + "");
//                    tvMonthReturn.setText(str1);
//                    tvWeekReturn.setText(str2 + str3 + ":" + str4);
//
//                }
//
//
//            }
        });
    }

    /**
     * 初始化轮播图
     */
    private void initBanner() {
        List<String> images = new ArrayList<>();
        images.add("http://img.hb.aicdn.com/0598d3964e1c2842acc90786799237d2a4050d562190a-7e1Fzr_fw658");
        images.add("http://img.hb.aicdn.com/82e6c5de2168ea6d18992e691fd0bef35d6622281bd83-7pUbfV_fw658");
        images.add("http://img.hb.aicdn.com/81009d16f8076fb33b6688d4a8a4aab50f16f9f51ed8e-9hEdSM_fw658");
        images.add("http://img.hb.aicdn.com/c666ffe3162c06015e282e86c0cb055c7508edf24aa59-L1LOcd_fw658");
        images.add("http://img.hb.aicdn.com/cd57b86a7579922b1892166fb5471fb808948ac07044e-4uLcMO_fw658");

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Stack);
        //设置标题集合（当banner样式有显示title时）
        //banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    /**
     * 初始化AppBarTool
     */
    private void initAppBarTool() {
        toolLeft.setVisibility(View.VISIBLE);
        toolRight.setVisibility(View.VISIBLE);
        toolLeft.setOnClickListener(v -> {
            if (!drawerlayout.isDrawerOpen(Gravity.LEFT)) {
                drawerlayout.openDrawer(Gravity.LEFT);
            }
        });
        toolRight.setOnClickListener(v -> {

        });
    }


    /**
     * 抽屉初始化
     */
    private void initDrawer() {
        DrawerAdapter drawerAdapter = new DrawerAdapter();
        drawerAdapter.setOnItemClickListener(new DrawerAdapter.OnItemClickListener() {
            @Override
            public void itemClick(DrawerAdapter.DrawerItemHeader drawerItemHeader) {
                switch (drawerItemHeader.login) {
                    case R.string.drawer_menu_login:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        break;
                }
            }

            @Override
            public void itemClick(DrawerAdapter.DrawerItemNormal drawerItemNormal) {
                switch (drawerItemNormal.titleRes) {
                    case R.string.drawer_menu_rental_order:
                        break;
                    case R.string.drawer_menu_my_vehicle:
                        break;
                    case R.string.drawer_menu_tenant_order:
                        break;
                    case R.string.drawer_menu_common_tenant:
                        break;
                    case R.string.drawer_menu_my_collection:
                        break;
                    case R.string.drawer_menu_my_purse:
                        break;
                    case R.string.drawer_menu_discount_coupon:
                        break;
                    case R.string.drawer_menu_setting:
                        break;
                }
                drawerlayout.closeDrawer(GravityCompat.START);
            }
        });
        rvDrawer.setLayoutManager(new LinearLayoutManager(this));
        rvDrawer.setAdapter(drawerAdapter);
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
    }

    @Override
    public void onFail(String err) {
    }

    @Override
    public void hideDialog() {
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (drawerlayout.isDrawerOpen(Gravity.LEFT)) {
                drawerlayout.closeDrawers();
            } else if ((System.currentTimeMillis() - exitTime) > 2000) {
                Snackbar.make(rootlayout, "再按一次退出程序", 2000).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityCollector.getInstance().AppExit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxBus.clear();
    }

}
