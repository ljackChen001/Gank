package com;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.base.util.ActivityCollector;
import com.base.util.LogUtils;
import com.base.util.SpUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.realm.Realm;

/**
 * Created by chenbaolin on 2017/4/4.
 */

public class App extends Application {
    private static App mApp;
    public HashMap<String, Object> mCurActivityExtra;
    private RefWatcher mRefWatcher;

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void onCreate() {
        super.onCreate();
        mApp = this;
        Realm.init(this);
        LogUtils.setDebug(true);
        registerActivityLifecycleCallbacks(new SwitchBackgroundCallbacks());
        initBugly();
        initLeakCanary();
    }


    public static App getAppContext() {
        return mApp;
    }


    @SuppressLint("NewApi")
    private class SwitchBackgroundCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            LogUtils.e("====>>", activity + "onActivityCreated");
            ActivityCollector.getInstance().addActivity(new WeakReference<>(activity));
        }

        @Override
        public void onActivityStarted(Activity activity) {
            LogUtils.e("====>>", activity + "onActivityStarted");
        }

        @Override
        public void onActivityResumed(Activity activity) {
            LogUtils.e("====>>", activity + "onActivityResumed");
        }

        @Override
        public void onActivityPaused(Activity activity) {
            LogUtils.e("====>>", activity + "onActivityPaused");
        }

        @Override
        public void onActivityStopped(Activity activity) {
            LogUtils.e("====>>", activity + "onActivityStopped");
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            LogUtils.e("====>>", activity + "onActivitySaveInstanceState");
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            LogUtils.e("====>>", activity + "onActivityDestroyed");
            ActivityCollector.getInstance().removeActivity(new WeakReference<>(activity));

            StringBuilder builder = new StringBuilder();
            builder.append("栈内剩余：");
            for (int i = 0; i < ActivityCollector.activityStack.size(); i++) {
                builder.append(ActivityCollector.activityStack.get(i).getClass().getSimpleName() + "    ");
            }
            LogUtils.e(builder.toString());

        }
    }


    /**
     * Bugly崩溃收集初始化
     */
    public void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(), "a6be694fd6", false);
    }

    /**
     * 内存泄漏检测初始化
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }


    public static void saveUserInfo(String token, String userName, String userNickName,
                                    String userPhone, String userId,
                                    String userHeadImg) {
        if (userName == null) {
            userName = "";
        }
        LogUtils.d("saveUserInfo：" + userName + userPhone);
        SpUtil.putAndApply(getAppContext(), "token", token);
        SpUtil.putAndApply(getAppContext(), "userId", userId);
        SpUtil.putAndApply(getAppContext(), "userName", userName);
        SpUtil.putAndApply(getAppContext(), "userNickName", userNickName);
        SpUtil.putAndApply(getAppContext(), "userPhone", userPhone);
        SpUtil.putAndApply(getAppContext(), "userHeadImg", userHeadImg);

    }
}
