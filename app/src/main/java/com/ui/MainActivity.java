package com.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.App;
import com.adapter.DrawerAdapter;
import com.base.ActivityStartUtils;
import com.base.BaseActivity;
import com.base.helper.RxBus;
import com.base.util.ActivityCollector;
import com.base.util.LogUtils;
import com.base.util.SnackbarUtils;
import com.base.util.SpUtil;
import com.base.util.StatusBarUtil;
import com.base.util.TimeUtils;
import com.entity.UserInfo;
import com.jakewharton.rxbinding2.view.RxView;
import com.ui.chooseCar.ChooseCarActivity;
import com.ui.component.cityselect.PickCityActivity;
import com.ui.component.wheelview.DateUtils;
import com.ui.component.wheelview.JudgeDate;
import com.ui.component.wheelview.ScreenInfo;
import com.ui.component.wheelview.WheelWeekDate;
import com.ui.gank.R;
import com.ui.login.LoginActivity;
import com.view.widget.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

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
    @BindView(R.id.btn_submit_oreder)
    Button btnSubmitOreder;
    private long exitTime = 0;
    private WheelWeekDate wheelWeekMainDate;
    private String beginTime;
    private int year, month, day, hours, minute;
    private String startTime;//取车时间
    private String endTime;//还车时间


    @Override
    public int setLayoutResouceId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setTranslucentForDrawerLayout(this, drawerlayout, 50);
        }
    }

    @Override
    public void initView() {

        StatusBarUtil.setColorNoTranslucentForDrawerLayout
                (this, drawerlayout, getResources().getColor(R.color.color_48b54c));
        rxBus = RxBus.getInstance();
        initAppBarTool();
        initDrawer();
        initBanner();
        initListener();
        initData();
        citySelectLayout.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PickCityActivity.class)));
        btnSubmitOreder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                mPresenter.getMyCollectCars();
                LogUtils.d(SpUtil.get(App.getAppContext(), "token", "") + "");
                //                LogUtils.d(SpUtil.get(App.getAppContext(), "userPhone", "") + "'");
                if (SpUtil.contains(App.getAppContext(), "userInfo")) {
                    UserInfo userInfo = SpUtil.get(App.getAppContext(), "userInfo", UserInfo
                            .class);
                    LogUtils.d("userInfo" + userInfo.getUserMobile() + "---" + userInfo.getUserCreateTime() + userInfo
                            .getUserName());
                }
                ActivityStartUtils.getInstance().readyGo(ChooseCarActivity.class);
            }
        });

    }

    @SuppressLint({"WrongConstant", "SetTextI18n"})
    private void initData() {
        String time = DateUtils.currentMonth();
        Calendar calendar = Calendar.getInstance();
        if (JudgeDate.isDate(time, "MM-DD")) {
            try {
                calendar.setTime(new Date(time));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        tvMonth.setText(DateUtils.getNowDay());
        tvWeek.setText(DateUtils.getWeekString() + " " + DateUtils.getHour() + ":" + DateUtils.getMinute());
        if (SpUtil.contains(App.getAppContext(), "userInfo")) {
            UserInfo userInfo = SpUtil.get(App.getAppContext(), "userInfo", UserInfo.class);

        }
    }


    private void initListener() {
        //        currentTime = TimeUtils.getCurrentTimeInString();
        //        layoutGetcarTime.setOnClickListener(v -> initPickTime(0));
        //        layoutReturnCar.setOnClickListener(v -> initPickTime(1));
        RxView.clicks(layoutGetcarTime).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        layoutReturnCar.setClickable(false);
                        initPickTime(0);
                    }
                });
        RxView.clicks(layoutReturnCar).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> initPickTime(1));

    }

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
                if (drawerItemHeader.login.equals("登录")) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void onSucceed(Object data) {
    }

    @Override
    public void onFail(String err) {
    }

    @Override
    public void hideDialog() {
    }


    /**
     * 先实现这个功能 有时间要把这个方法重构
     *
     * @param type
     */
    @SuppressLint({"WrongConstant", "SetTextI18n"})
    private void initPickTime(int type) {
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        View menuView = LayoutInflater.from(this).inflate(R.layout.show_week_popup_window, null);
        final PopupWindow mPopupWindow = new PopupWindow(menuView, width,
                ActionBar.LayoutParams.WRAP_CONTENT);
        ScreenInfo screenInfoDate = new ScreenInfo(this);
        wheelWeekMainDate = new WheelWeekDate(menuView, true);
        wheelWeekMainDate.screenheight = screenInfoDate.getHeight();
        wheelWeekMainDate.initDateTimePicker(year, month, day, hours, minute);
        mPopupWindow.setAnimationStyle(R.style.AnimationPreview);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAtLocation(layoutGetcarTime, Gravity.BOTTOM, 0, 0);
        mPopupWindow.setOnDismissListener(() -> backgroundAlpha(1f));
        backgroundAlpha(0.6f);
        TextView tv_cancle = (TextView) menuView.findViewById(R.id.tv_cancle);
        TextView tv_ensure = (TextView) menuView.findViewById(R.id.tv_ensure);
        TextView tv_pop_title = (TextView) menuView.findViewById(R.id.tv_pop_title);
        if (type == 0) {
            tv_pop_title.setText("取车时间");
        } else {
            tv_pop_title.setText("还车时间");
        }
        tv_cancle.setOnClickListener(arg0 -> mPopupWindow.dismiss());
        tv_ensure.setOnClickListener(arg0 -> {
            beginTime = wheelWeekMainDate.getTime();
            LogUtils.d(beginTime + "----");
            if (!TextUtils.isEmpty(beginTime)) {
                LogUtils.d(beginTime + "||");
                LogUtils.d(TimeUtils.getNowTime() + "|||");
                LogUtils.d("2017-" + beginTime.substring(0, 5) + beginTime
                        .substring(8, beginTime.length()) + "||||");
                if (type == 0) {
                    startTime = DateUtils.getYear() + "-" + beginTime.substring
                            (0, 5) + beginTime.substring(8, beginTime.length());
                    //2个小时时间差 true>2
                    Boolean timeDifference = TimeUtils.compareTwoTime2(TimeUtils.getNowTime(),
                            startTime, 2 * 60 * 60 * 1000);
                    if (timeDifference) {
                        tvMonth.setText(beginTime.substring(0, 5));
                        tvWeek.setText(beginTime.substring(6, beginTime.length()));
                        initPickTime(1);
                    } else {
                        mPopupWindow.dismiss();
                        SnackbarUtils.showSnacker(rootlayout, "不能小于当前时间2个小时！");
                    }
                } else {
                    LogUtils.d(beginTime + "===");
                    endTime = DateUtils.getYear() + "-" + beginTime.substring
                            (0, 5) + beginTime.substring(8, beginTime.length());
                    //一个小时起租
                    Boolean timeDifference = TimeUtils.compareTwoTime2(startTime,
                            endTime, 60 * 60 * 1000);
                    if (timeDifference) {
                        tvMonthReturn.setText(beginTime.substring(0, 5));
                        tvWeekReturn.setText(beginTime.substring(6, beginTime.length()));
                        long str = TimeUtils.compareTwoTime2(startTime, endTime);
                        LogUtils.d(str + "取余运算");
                        if (str % (24 * 60 * 60 * 1000) > 0) {
                            tvDay.setText(str / (24 * 60 * 60 * 1000) + 1 + "");
                        } else {
                            tvDay.setText(str / (24 * 60 * 60 * 1000) + "");
                        }
                    } else {
                        mPopupWindow.dismiss();
                        SnackbarUtils.showSnacker(rootlayout, "最少要租1个小时哦！");
                    }

                }

            }
            mPopupWindow.dismiss();
            backgroundAlpha(1f);
        });
    }

    public void backgroundAlpha(float bgAlpha) {
        layoutReturnCar.setClickable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);

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
}
