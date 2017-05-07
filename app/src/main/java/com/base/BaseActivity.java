package com.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.base.helper.RxBus;
import com.base.util.ActivityCollector;
import com.base.util.LogUtils;
import com.base.util.StatusBarUtil;
import com.ui.gank.R;
import com.view.widget.SwipeBackLayout;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenbaolin on 2017/4/4.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    public P mPresenter;
    public Unbinder unbinder;
    public RxBus rxBus;
    public Context mContext;
    private String TAG = this.getClass().getSimpleName();
    protected Toolbar toolbar;
    protected TextView tvTitle;
    public int menuResId;
    public String menuStr;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        ActivityCollector.getInstance().addActivity(new WeakReference<>(this));
        mContext = this;
        View rootView = getLayoutInflater().inflate(setLayoutResouceId(), null, false);
        setContentView(setLayoutResouceId(), rootView);
        unbinder = ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.color_48b54c), 50);
        }
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        if (onCreatePresenter() != null) {
            mPresenter = onCreatePresenter();
        }
        initTool();
        initToolBar();
        initView();
        LogUtils.d("生命周期：" + TAG + "==>>onCreate");
    }

    private void initTool() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.tool_content);
        if (toolbar != null) {
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }


    public void setContentView(int layoutResID, View rootView) {
        boolean isNotSwipeBack = layoutResID == R.layout.activity_main || layoutResID == R.layout.activity_welcome;
        super.setContentView(isNotSwipeBack ? rootView : getContainer(rootView));
    }

    private View getContainer(View rootView) {
        rootView.setBackgroundColor(getResources().getColor(R.color.alpha_white));
        //        rootView.setBackgroundResource(R.drawable.header);
        View container = getLayoutInflater().inflate(R.layout.activity_base, null, false);
        SwipeBackLayout swipeBackLayout = (SwipeBackLayout) container.findViewById(R.id.swipeBackLayout);
        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        View ivShadow = container.findViewById(R.id.iv_shadow);
        swipeBackLayout.addView(rootView);
        swipeBackLayout.setOnSwipeBackListener((fa, fs) -> ivShadow.setAlpha(1 - fs));
        return container;
    }

    OnClickListener onClickListenerTopLeft;
    OnClickListener onClickListenerTopRight;

    public interface OnClickListener {
        void onClick();
    }

    /**
     * 设置ToolBar中间文字
     *
     * @param title
     */
    protected void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
    }

    /**
     * ToolBar左边按钮
     */
    protected void setTopLeftButton() {
        setTopLeftButton(R.drawable.ic_main_left, null);
    }

    protected void setTopLeftButton(int iconResId, OnClickListener onClickListener) {
        toolbar.setNavigationIcon(iconResId);
        this.onClickListenerTopLeft = onClickListener;
    }

    protected void setTopRightButton(String menuStr, OnClickListener onClickListener) {
        this.onClickListenerTopRight = onClickListener;
        this.menuStr = menuStr;
    }

    protected void setTopRightButton(String menuStr, int menuResId, OnClickListener onClickListener) {
        this.menuResId = menuResId;
        this.menuStr = menuStr;
        this.onClickListenerTopRight = onClickListener;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuResId != 0 || !TextUtils.isEmpty(menuStr)) {
            getMenuInflater().inflate(R.menu.menu_activity_base_top_bar, menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menuResId != 0) {
            menu.findItem(R.id.menu_1).setIcon(menuResId);
        }
        if (!TextUtils.isEmpty(menuStr)) {
            menu.findItem(R.id.menu_1).setTitle(menuStr);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onClickListenerTopLeft.onClick();
        } else if (item.getItemId() == R.id.menu_1) {
            onClickListenerTopRight.onClick();
        }

        return true; // true 告诉系统我们自己处理了点击事件
    }


    protected abstract int setLayoutResouceId();

    /**
     * 初始化view
     */
    protected abstract void initView();

    protected abstract void initToolBar();

    protected abstract P onCreatePresenter();

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d("生命周期：" + TAG + "==>>onStart");

    }

    @Override
    protected void onStop() {
        LogUtils.d("生命周期：" + TAG + "==>>onStop");
        super.onStop();
    }

    @Override
    protected void onPause() {
        LogUtils.d("生命周期：" + TAG + "==>>onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.d("生命周期：" + TAG + "==>>onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d("生命周期：" + TAG + "==>>onResume");
    }

    @Override
    protected void onDestroy() {
        LogUtils.d("生命周期：" + TAG + "==>>onDestroy");
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
        unbinder.unbind();
        ActivityCollector.getInstance().removeActivity(new WeakReference<>(this));
        super.onDestroy();
    }

}
