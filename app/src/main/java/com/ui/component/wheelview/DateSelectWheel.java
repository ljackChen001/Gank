package com.ui.component.wheelview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.base.util.LogUtils;
import com.base.util.TimeUtils;
import com.ui.gank.R;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by chenbaolin on 2017/4/18.
 */

public class DateSelectWheel extends Dialog implements WheelView.OnWheelChangedListener, android.view.View.OnClickListener {
    WheelView day, hour, min;
    private String[] days;
    private String[] hours;
    private String[] mins;
    private TextView tv_time;
    Calendar calendar = Calendar.getInstance(Locale.CHINA);
    private int type;

    public DateSelectWheel(Context context, int type) {
        super(context, R.style.Transparent);
        this.type = type;
        initViews();
    }

    @SuppressLint("ResourceType")
    private void initViews() {
        setContentView(R.layout.time_select);
        tv_time = (TextView) findViewById(R.id.tv_time);
        findViewById(R.id.sure).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
        day = (WheelView) findViewById(R.id.day_wheel);
        hour = (WheelView) findViewById(R.id.hour_wheel);
        min = (WheelView) findViewById(R.id.min_wheel);
        if (type == 1) {
            tv_time.setText("取车时间");
        } else {
            tv_time.setText("还车时间");
        }
        day.setBackgroundResource(Color.TRANSPARENT);
        hour.setBackgroundResource(Color.TRANSPARENT);
        min.setBackgroundResource(Color.TRANSPARENT);
        initData();
        day.setAdapter(new ArrayWheelAdapter<>(days));
        day.setVisibleItems(3);
        day.setCyclic(true);
        day.setCurrentItem(0);

        hour.setAdapter(new ArrayWheelAdapter<>(hours));
        hour.setVisibleItems(3);
        hour.setCyclic(true);
        hour.setCurrentItem(0);

        min.setAdapter(new ArrayWheelAdapter<>(mins));
        min.setVisibleItems(3);
        min.setCyclic(true);
        min.setCurrentItem(0);
        day.addChangingListener(this);
        hour.addChangingListener(this);
        min.addChangingListener(this);
        getWindow().setWindowAnimations(android.R.style.Animation_InputMethod);
        setParams();

    }

    private void setParams() {
        WindowManager.LayoutParams lay = getWindow().getAttributes();
        lay.width = getContext().getResources().getDisplayMetrics().widthPixels;
    }

    @SuppressLint("WrongConstant")
    private void setCurrentSelect() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int curDate = calendar.get(Calendar.DATE);
        if (type == 1) {
            this.day.setCurrentItem(curDate - 1);
        } else {
            this.day.setCurrentItem(curDate);
        }
        if (hour >= 24)
            this.hour.setCurrentItem(0);
        else
            this.hour.setCurrentItem(hour + 2);
        if (min >= 59)
            this.min.setCurrentItem(0);
        else
            this.min.setCurrentItem(min);
    }

    private TimeCallBack mTimeCallBack;

    public void setCallBack(TimeCallBack mTimeCallBack) {
        this.mTimeCallBack = mTimeCallBack;
        show();
    }

    public interface TimeCallBack {
        void handle(String... params);
    }


    @Override
    public void show() {
        setCurrentSelect();
        super.show();
    }

    @SuppressLint("WrongConstant")
    private void initData() {
        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
        int moth = getDay(curYear, curMonth);
        LogUtils.d(moth + "'");
        for (int j = 0; j < 2; j++) {
            days = new String[moth+j];
            for (int i = 0; i < moth; i++) {
                long time = TimeUtils.getStringToDate(curYear + "-" + curMonth + "-" + (i + 1));
                days[i] = curMonth + "月" + (i + 1) + "日" + TimeUtils.getWeekString(time);
            }
        }
        // 初始化小时
        hours = new String[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = i + "点";
        }
        mins = new String[60];
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                mins[i] = ("0" + String.valueOf(i)) + "分";
            } else {
                mins[i] = (i == 0 ? "" : String.valueOf(i)) + "分";
            }
        }

    }

    private String getSelectHour(int value) {
        if (value < 10) {
            return "0" + value;
        }
        return String.valueOf(value);
    }

    private int[] selectIndex = new int[3];

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        switch (wheel.getId()) {
            case R.id.day_wheel:
                selectIndex[0] = newValue;
                break;
            case R.id.min_wheel:
                selectIndex[2] = newValue;
                break;
            case R.id.hour_wheel:
                selectIndex[1] = newValue;
                break;
        }
    }

    @SuppressLint("WrongConstant")
    private void setTime() {
        int curYear = calendar.get(Calendar.YEAR);
        @SuppressLint("WrongConstant") int curMonth = calendar.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
        if (mTimeCallBack != null) {
            long time = TimeUtils.getStringToDate(curYear + "-" + curMonth + "-" + ((selectIndex[0] + 1)));
            mTimeCallBack.handle(getSelectHour(curMonth) + "-" + getSelectHour(selectIndex[0] + 1), TimeUtils
                    .getWeekString(time), getSelectHour(selectIndex[1]), getSelectHour(selectIndex[2]));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure:
                dismiss();
                setTime();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    /**
     * @param year
     * @param month
     * @return
     */
    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }


}
