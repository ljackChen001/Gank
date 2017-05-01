package com.base;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.base.util.ActivityCollector;

import java.lang.ref.WeakReference;

/***********************************************************************
 **                            _ooOoo_  
 **                           o8888888o  
 **                           88" . "88  
 **                           (| -_- |)  
 **                            O\ = /O  
 **                        ____/`---'\____  
 **                      .   ' \\| |// `.  
 **                       / \\||| : |||// \  
 **                     / _||||| -:- |||||- \  
 **                       | | \\\ - /// | |  
 **                     | \_| ''\---/'' | |  
 **                      \ .-\__ `-` ___/-. /  
 **                   ___`. .' /--.--\ `. . __  
 **                ."" '< `.___\_<|>_/___.' >'"".  
 **               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
 **                 \ \ `-. \_ __\ /__ _/ .-` / /  
 **         ======`-.____`-.___\_____/___.-`____.-'======  
 **                            `=---='  
 **         .............................................  
 **                  佛祖镇楼                  BUG辟易  
 **    佛曰:  
 **    写字楼里写字间，写字间里程序员；  程序人员写程序，又拿程序换酒钱。  
 **    酒醒只在网上坐，酒醉还来网下眠；  酒醉酒醒日复日，网上网下年复年。  
 **    酒醉酒醒日复日，网上网下年复年。  酒醉酒醒日复日，网上网下年复年。  
 **    酒醉酒醒日复日，网上网下年复年。  但愿老死电脑间，不愿鞠躬老板前；  
 **    奔驰宝马贵者趣，公交自行程序员。  别人笑我忒疯癫，我笑自己命太贱；  
 **                  不见满街漂亮妹，哪个归得程序员？
 ***********************************************************************
 * Created by ChenBaoLin on 2017/5/1.
 * Activity跳转工具类
 * 先用着 后面再看看路由for Android
 */

public class ActivityStartUtils {
    private static ActivityStartUtils activityUtils = new ActivityStartUtils();

    private ActivityStartUtils() {
    }

    /***
     * 获得AppManager的实例
     *
     * @return AppManager实例
     */
    public static ActivityStartUtils getInstance() {
        if (activityUtils == null) activityUtils = new ActivityStartUtils();
        return activityUtils;
    }

    /**
     * 界面跳转
     *
     * @param clazz 目标Activity
     */
    public void readyGo(Class<?> clazz) {
        readyGo(clazz, null);
    }

    /**
     * 跳转界面，  传参
     *
     * @param clazz  目标Activity
     * @param bundle 数据
     */
    public void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(ActivityCollector.getInstance().getTopActivity(), clazz);
        if (null != bundle)
            intent.putExtras(bundle);
        ActivityCollector.getInstance().getTopActivity().startActivity(intent);
    }

    /**
     * 跳转界面并关闭当前界面
     *
     * @param clazz 目标Activity
     */
    public void readyGoThenKill(Class<?> clazz) {
        readyGoThenKill(clazz, null);
    }

    /**
     * @param clazz  目标Activity
     * @param bundle 数据
     */
    public void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        readyGo(clazz, bundle);
        ActivityCollector.getInstance().getTopActivity().finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz       目标Activity
     * @param requestCode 发送判断值
     */
    public void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(ActivityCollector.getInstance().getTopActivity(), clazz);
        ActivityCollector.getInstance().getTopActivity().startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz       目标Activity
     * @param requestCode 发送判断值
     * @param bundle      数据
     */
    public void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(ActivityCollector.getInstance().getTopActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        ActivityCollector.getInstance().getTopActivity().startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult( fragment要返回值的方法)
     *
     * @param clazz       目标Activity
     * @param requestCode 发送判断值
     */
    public void readyGoForResult(WeakReference<Fragment> fragment, Class<?> clazz, int requestCode) {
        Intent intent = new Intent(ActivityCollector.getInstance().getTopActivity(), clazz);
        fragment.get().startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle ( fragment要返回值的方法)
     *
     * @param clazz       目标Activity
     * @param requestCode 发送判断值
     * @param bundle      数据
     */
    public void readyGoForResult(WeakReference<Fragment> fragment, Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(ActivityCollector.getInstance().getTopActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        fragment.get().startActivityForResult(intent, requestCode);
    }


}