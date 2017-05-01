package com.ui.component.wheelview;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtils {
    public static final String yyyyMMddHHmm = "MM-dd HH:mm";

    public static String currentTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return sdf.format(calendar.getTime());
    }

    /**
     * 当前时间
     * @return 时间戳类型
     */
    public static long getCurrentForLong() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(currentTime);

        return getStringToDateTime(dateString);
    }

    public static String formateStringH(String dateStr, String pattren) {
        Date date = parseDate(dateStr, pattren);
        try {
            String str = dateToString(date, DateUtils.yyyyMMddHHmm);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return dateStr;
        }
    }

    public static Date parseDate(String dateStr, String type) {
        SimpleDateFormat df = new SimpleDateFormat(type);
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    public static String dateToString(Date date, String pattern)
            throws Exception {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date stringToDate(String dateStr, String pattern)
            throws Exception {
        return new SimpleDateFormat(pattern).parse(dateStr);
    }

    public static String currentTimeDeatil(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 格式化对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return sdf.format(calendar.getTime());

    }

    public static String currentMonth() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return sdf.format(calendar.getTime());

    }

    public static String lastMonth(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date d;
        try {
            d = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            calendar.add(Calendar.MONTH, -1);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public static String nextMonth(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date d;
        try {
            d = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            calendar.add(Calendar.MONTH, +1);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;

    }

    public static String lastDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");// 格式化对象
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return sdf.format(date);
    }

    // 前7天数据
    public static String lastSevenDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");// 格式化对象
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        date = calendar.getTime();
        return sdf.format(date);
    }

    // 前14天数据
    public static String lastFourteenDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");// 格式化对象
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -14);
        date = calendar.getTime();
        return sdf.format(date);
    }

    // 本月第一天数据
    public static String currentFDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");// 格式化对象
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        sdf.format(calendar.getTime());
        return sdf.format(calendar.getTime());
    }

    // 本月最后天数据
    public static String currentLDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");// 格式化对象
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        sdf.format(calendar.getTime());
        return sdf.format(calendar.getTime());
    }

    public static String currentLDaySchedule() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化对象
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        sdf.format(calendar.getTime());
        return sdf.format(calendar.getTime());
    }

    // 上个月第一天数据
    public static String currentFFday() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        return df.format(gcLast.getTime());

    }

    // 上个月最后一天数据
    public static String currentLLday() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");// 格式化对象
        Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH - 1));
        Date theDate = calendar.getTime();
        return df.format(theDate);
    }

    // 上个月最后一天数据
    public static String currentLLdaySchedule(String date) {
        try {
            String aa = date.toString() + "-01";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 格式化对象
            Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
            calendar.setTime(df.parse(aa));
            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date theDate = calendar.getTime();
            return df.format(theDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    // 下一个月最后一天数据
    public static String lastLLdaySchedule(String date) {
        try {
            String aa = date.toString() + "-01";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 格式化对象
            Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
            calendar.setTime(df.parse(aa));
            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date theDate = calendar.getTime();
            return df.format(theDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    // 获取最近三十天的数据

    public static String lastThrDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");// 格式化对象
        Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间\

        // calendar.add(Calendar.MONTH, -1); //

        // if (calendar.get(calendar.DAY_OF_MONTH)==1) {
        //
        // }
        calendar.add(Calendar.DATE, -30);

        Date theDate = calendar.getTime();
        return df.format(theDate);
    }

    public static String[] weekName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getWeekDay() {
        return Calendar.getInstance(Locale.CHINA).get(Calendar.DAY_OF_WEEK);
    }

    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static CustomDate getNextSunday() {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 7 - getWeekDay() + 1);
        CustomDate date = new CustomDate(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        return date;
    }

    @SuppressLint("WrongConstant")
    public static int[] getWeekSunday(int year, int month, int day, int pervious) {
        int[] time = new int[3];
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.add(Calendar.DAY_OF_MONTH, pervious);
        time[0] = c.get(Calendar.YEAR);
        time[1] = c.get(Calendar.MONTH) + 1;
        time[2] = c.get(Calendar.DAY_OF_MONTH);
        return time;

    }

    public static int getWeekDayFromDate(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFromString(year, month));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return week_index;
    }

    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(int year, int month) {
        String dateString = year + "-" + (month > 9 ? month : ("0" + month))
                + "-01";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    public static boolean isToday(CustomDate date) {
        return (date.year == DateUtils.getYear() &&
                date.month == DateUtils.getMonth()
                && date.day == DateUtils.getCurrentMonthDay());
    }

    public static boolean isCurrentMonth(CustomDate date) {
        return (date.year == DateUtils.getYear() &&
                date.month == DateUtils.getMonth());
    }
    //    获取做给定年月的最后一天

    public static String getMonthend(int year, int month) {

        Calendar calendar = Calendar.getInstance();
        // 设置时间,当前时间不用设置
        // calendar.setTime(new Date());
        // 设置日期为本月最大日期
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

        // 打印
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(calendar.getTime()));
        return format.format(calendar.getTime());
    }

    public static String getMonthStart(int year, int month) {

        Calendar calendar = Calendar.getInstance();
        // 设置时间,当前时间不用设置
        // calendar.setTime(new Date());
        // 设置日期为本月最大日期
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DATE, 1);

        // 打印
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(calendar.getTime()));
        return format.format(calendar.getTime());
    }


    // 昨天
    public static String currentYesterday() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化对象
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.setTime(date);
        return sdf.format(calendar.getTime());
    }

    // 本周的第一天
    public static String currentWeekone() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.DAY_OF_WEEK) == 0) {
            calendar.add(Calendar.DATE, -6);
        } else {

            calendar.add(Calendar.DATE, 2 - calendar.get(Calendar.DAY_OF_WEEK));
        }

        return sdf.format(calendar.getTime());
    }

    // 获取当前的时分
    public static String currentHourMinute() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");// 格式化对象

        return sdf.format(date);
    }

    //取时分
    public static String getStringHHmm(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(time);
        return dateString;
    }

    public static String getWeekString() {
        int week = getWeekOfDate(getCurrentForLong());
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

    public static int getNowYear() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String dateString = formatter.format(currentTime);
        return Integer.parseInt(dateString);
    }

    public static int getNowMonth() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        String dateString = formatter.format(currentTime);
        return Integer.parseInt(dateString);
    }

    public static String getNowDay() {
        Date mTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        String timeString = formatter.format(mTime);
        return timeString;
    }
    public static String getNowYearMonth() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /***************************************转换*****************************************/
    	/*将字符串转为时间戳*/
    @SuppressLint("SimpleDateFormat")
    public static long getStringToDateTime(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try{
            date = formatter.parse(time);
        } catch(ParseException e) {
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
        try{
            date = formatter.parse(time);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }


}
