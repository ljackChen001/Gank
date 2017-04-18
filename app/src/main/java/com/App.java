package com;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.base.util.ActivityCollector;
import com.base.util.LogUtils;
import com.base.util.SpUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;

import io.realm.Realm;

/**
 * Created by chenbaolin on 2017/4/4.
 */

public class App extends Application {
    private static App mApp;
    public HashMap<String, Object> mCurActivityExtra;
    private RefWatcher mRefWatcher;

    public void onCreate() {
        super.onCreate();
        mApp = this;
        Realm.init(this);
        SpUtil.init(this);
        //        AppCompatDelegate.setDefaultNightMode(SpUtil.isNight() ? AppCompatDelegate.MODE_NIGHT_YES :
        // AppCompatDelegate.MODE_NIGHT_NO);
        LogUtils.setDebug(true);
        registerActivityLifecycleCallbacks(new SwitchBackgroundCallbacks());
        initBugly();
        initLeakCanary();
    }

    public static App getAppContext() {
        return mApp;
    }


    private class SwitchBackgroundCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            ActivityCollector.getInstance().addActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            ActivityCollector.getInstance().finishActivity(activity);
        }
    }

    /**
     * 获取当前的Activity
     *
     * @return
     */
    public Activity getCurActivity() {

        return ActivityCollector.getInstance().currentActivity();
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
}
