package com.base.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TimeUtils {
    public static String getNowTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getNowFen() {
        Date mTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String timeString = formatter.format(mTime);
        return timeString;
    }

    /*将字符串转为时间戳*/
    @SuppressLint("SimpleDateFormat")
    public static long getHHmm(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getStringHHmm(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(time);
        return dateString;
    }


    public static int getNowMonth() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        String dateString = formatter.format(currentTime);
        return Integer.parseInt(dateString);
    }

    public static int getNowYear() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String dateString = formatter.format(currentTime);
        return Integer.parseInt(dateString);
    }

    public static String getNowYearMonth() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static long changeYearMonth(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    public static String getYearMonthHH(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(time);
        return dateString;
    }

    public static String getYearMonthHH1(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(time);
        return dateString;
    }

    public static String getYearMonthDD(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        String dateString = formatter.format(time);
        return dateString;
    }

    public static String getMonthTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日 HH:mm");
        String dateString = formatter.format(time);
        return dateString;
    }

    public static String getMonthDay(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        String dateString = formatter.format(time);
        return dateString;
    }

    public static String getMonthDay1(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
        String dateString = formatter.format(time);
        return dateString;
    }

    public static String getMonthfirstDay(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String dateString = formatter.format(time) + "-01";
        return dateString;
    }

    public static String getMonthTime2(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
        String dateString = formatter.format(time);
        return dateString;
    }

    public static String getTimeForpattern(long time, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateString = formatter.format(time);
        return dateString;
    }

    public static String getYearMonth(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(time);
        return dateString;
    }

    public static int getWeekOfDate(long time) {

        Date date = new Date(time);
        // String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"
        // };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        @SuppressLint("WrongConstant") int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return w;
    }

    public static String getWeekString(long time) {
        int week = getWeekOfDate(time);
        String value = "";
        switch (week) {
            case 0:
                value = "周日";
                break;
            case 1:
                value = "周一";
                break;
            case 2:
                value = "周二";
                break;
            case 3:
                value = "周三";
                break;
            case 4:
                value = "周四";
                break;
            case 5:
                value = "周五";
                break;
            case 6:
                value = "周六";
                break;
        }
        return value;
    }


    /*将字符串转为时间戳*/
    @SuppressLint("SimpleDateFormat")
    public static long getStringToDate(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    /*将字符串转为时间戳*/
    @SuppressLint("SimpleDateFormat")
    public static long getStringToDate1(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd");
        Date date = new Date();
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    /*将字符串转为时间戳*/
    @SuppressLint("SimpleDateFormat")
    public static long getStringToDateTime(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    /*将字符串转为时间戳*/
    @SuppressLint("SimpleDateFormat")
    public static long getStringToDateTime1(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date date = new Date();
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static long changetime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    public static long changemonth(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    public static long getTime(String time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        try {
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    public static String getYesterday() {
        Date dNow = new Date(); // 当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(dNow);// 把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
        dBefore = calendar.getTime(); // 得到前一天的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        String defaultStartDate = sdf.format(dBefore); // 格式化前一天
        return defaultStartDate;
    }

    // 上周一
    public static String getPreviousWeekday() {
        int weeks = -1;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    // 上个月
    public static String getPreviousMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);// 减一个月，变为上月的1号
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 下个月
    public static String getlastMonthFirst(String time) {
        Date dNow = new Date(changemonth(time));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.setTime(dNow);
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        return sdf.format(lastDate.getTime());
    }

    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        @SuppressLint("WrongConstant") int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    // 获取第二天
    public static String getSecondDay(String time) {
        Date dNow = new Date(changeYearMonth(time));
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(dNow);// 把当前时间赋给日历
        calendar.add(Calendar.DATE, 1); // 设置为前一天
        dBefore = calendar.getTime(); // 得到前一天的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        String defaultStartDate = sdf.format(dBefore);
        return defaultStartDate;
    }

    // 获取6天后
    public static String getsevenDay(String time) {
        Date dNow = new Date(changeYearMonth(time));
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(dNow);// 把当前时间赋给日历
        calendar.add(Calendar.DATE, 6); // 设置为前一天
        dBefore = calendar.getTime(); // 得到前一天的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        String defaultStartDate = sdf.format(dBefore);
        return defaultStartDate;
    }

    // 获取周一
    public static String getMondayOfThisWeek(String time) {
        Date dNow = new Date(changeYearMonth(time));
        Calendar c = Calendar.getInstance();
        c.setTime(dNow);
        @SuppressLint("WrongConstant")
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        c.add(Calendar.DATE, -day_of_week + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        return sdf.format(c.getTime());
    }


    public static String changenumber(int num) {
        String number;
        if (num + 1 < 10) {
            number = "0" + (num + 1);
        } else {
            number = "" + (num + 1);
        }
        return number;

    }

    public static String daychang(int day) {
        String str = "";
        if (day < 10) {
            str = "0" + day;
        } else {
            str = day + "";
        }
        return str;
    }
}
