package com.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.LogUtils;

/**
 * Created by chenbaolin on 2017/4/9.
 */

public abstract class BaseFragement<P extends BasePresenter> extends Fragment {

    protected BaseActivity mActivity;
    protected View mRootView;
    private boolean isVisible = false;//当前Fragment是否可见
    private boolean isInitView = false;//是否与View建立起映射关系
    private boolean isFirstLoad = true;//是否是第一次加载数据

    protected P mPresenter;
    public View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        LogUtils.i("G A N K:" + this.getClass().getSimpleName());
        mRootView = inflater.inflate(setLayoutResouceId(), container, false);
        //        mViews=new SparseArray<>();
        if (null != getArguments()) {
            getBundleExtras(getArguments());
        }
        isInitView = true;
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        LogUtils.i("   " + this.getClass().getSimpleName());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.i("context" + "   " + this.getClass().getSimpleName());
        mActivity = (BaseActivity) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (onCreatePresenter() != null) {
            mPresenter = onCreatePresenter();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    /**
     * 实现Fragment懒加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LogUtils.i("isVisibleToUser " + isVisibleToUser + "   " + this.getClass().getSimpleName());
        if (isVisibleToUser) {
            this.isVisible = true;
            lazyLoadData();
        } else {
            this.isVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void lazyLoadData() {
        if (isFirstLoad) {
            LogUtils.i("第一次加载 " + " isInitView  " + isInitView + "  isVisible  " + isVisible + "   " + this.getClass()
                    .getSimpleName());
        } else {
            LogUtils.i("不是第一次加载" + " isInitView  " + isInitView + "  isVisible  " + isVisible + "   " + this.getClass()
                    .getSimpleName());
        }
        if (!isFirstLoad || !isVisible || !isInitView) {
            LogUtils.i("不加载" + "   " + this.getClass().getSimpleName());
            return;
        }

        LogUtils.i("完成数据第一次加载");
        initView(view);
        initData();
        isFirstLoad = false;
    }

    /**
     * 获取bundle信息
     *
     * @param bundle
     */
    protected abstract void getBundleExtras(Bundle bundle);

    protected abstract int setLayoutResouceId();

    protected abstract P onCreatePresenter();

    protected abstract void initView(View view);

    protected abstract void initData();


}
