package com;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.base.util.SpUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.Stack;

import io.realm.Realm;

/**
 * Created by chenbaolin on 2017/4/4.
 */

public class App extends Application {
    private static App mApp;
    public Stack<Activity> store;
    public HashMap<String, Object> mCurActivityExtra;
    private RefWatcher mRefWatcher;

    public void onCreate() {
        super.onCreate();
        mApp = this;
        Realm.init(this);
        SpUtil.init(this);
        //        AppCompatDelegate.setDefaultNightMode(SpUtil.isNight() ? AppCompatDelegate.MODE_NIGHT_YES :
        // AppCompatDelegate.MODE_NIGHT_NO);
        store = new Stack<>();
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
            store.add(activity);
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
            store.remove(activity);
        }
    }

    /**
     * 获取当前的Activity
     *
     * @return
     */
    public Activity getCurActivity() {
        return store.lastElement();
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
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
