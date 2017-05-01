package com.base;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.ui.gank.R;

import java.util.ArrayList;
import java.util.List;

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
 *
 */

public abstract class BaseListActivity<T> extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {
    private static final String TAG = "BaseListActivity";

    /**
     * 普通list布局
     */
    protected static final int LINEAR_LAYOUT_MANAGER = 0;
    /**
     * grid布局
     */
    protected static final int GRID_LAYOUT_MANAGER = 1;

    /**
     * 瀑布流布局
     */
    protected static final int STAGGERED_GRID_LAYOUT_MANAGER = 2;

    /**
     * 默认为0单行布局
     */
    private int mListType = 0;
    /**
     * 排列方式默认垂直
     */
    private boolean mIsVertical = true;

    /**
     * grid布局与瀑布流布局默认行数
     */
    private int mSpanCount = 1;

    protected RecyclerView mRecyclerView;
    protected CblListAdapter mAdapter;
    /**
     * 子布局id
     */
    private int layoutResId = -1;

    private LoadMoreView loadMoreView;

    /**
     * 每页数量  默认10
     */
    private int pageSize = 10;

    public int getPageSize() {
        return pageSize;
    }

    @Override
    protected void initView() {
        initItemLayout();
        mRecyclerView = (RecyclerView) findViewById(R.id.base_list_rv);
        chooseListType(mListType, mIsVertical);
        if (-1 == layoutResId) {
            throw new RuntimeException("layoutResId is null!");
        }
        mAdapter = new CblListAdapter(layoutResId, new ArrayList<T>());
        initSetting();
        mAdapter.setLoadMoreView(getLoadMoreView());
        mRecyclerView.setAdapter(mAdapter);

    }

    /**
     * 设置每页数量（开启loading才会使用）
     *
     * @param pageSize 每页数量
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 设置load界面的多种状态 没有更多、loading、加载失败三种三种状态
     *
     * @param loadMoreView load界面多状态布局
     */
    protected void setLoadMordTypeLayout(LoadMoreView loadMoreView) {
        this.loadMoreView = loadMoreView;
    }


    public LoadMoreView getLoadMoreView() {
        return loadMoreView == null ? new LoadMoreView() {
            @Override
            public int getLayoutId() {
                return R.layout.quick_view_load_more;
            }

            @Override
            protected int getLoadingViewId() {
                return R.id.load_more_loading_view;
            }

            @Override
            protected int getLoadFailViewId() {
                return R.id.load_more_load_fail_view;
            }

            @Override
            protected int getLoadEndViewId() {
                return R.id.load_more_load_end_view;
            }
        } : loadMoreView;
    }

    /**
     * 设置子布局layout
     *
     * @param layoutResId 子布局layout
     */
    public void setLayoutResId(@LayoutRes int layoutResId) {
        this.layoutResId = layoutResId;
    }

    /**
     * 初始化子布局
     */
    protected abstract void initItemLayout();


    /**
     * 初始化各种状态处理
     * 在这个方法里处理的是recyclerview的所有的初始化，
     * 包括对他的展示形式，是list或grid或瀑布流
     */
    protected abstract void initSetting();

    /**
     * 是否打开加载更多
     */
    public void openLoadMore() {
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
    }

    /**
     * @param type       布局管理type
     * @param isVertical 是否是垂直的布局 ，true垂直布局，false横向布局
     */
    public void setListType(int type, boolean isVertical) {
        mListType = type;
        mIsVertical = isVertical;
    }

    protected void setSpanCount(int spanCount) {
        if (spanCount > 0)
            mSpanCount = spanCount;
    }

    /**
     * @param listType 选择布局种类
     */
    private void chooseListType(int listType, boolean isVertical) {
        switch (listType) {
            case LINEAR_LAYOUT_MANAGER:
                //设置布局管理器
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

                linearLayoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager
                        .HORIZONTAL);

                mRecyclerView.setLayoutManager(linearLayoutManager);
                break;
            case GRID_LAYOUT_MANAGER:

                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, mSpanCount);

                gridLayoutManager.setOrientation(isVertical ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL);

                mRecyclerView.setLayoutManager(gridLayoutManager);
                break;
            case STAGGERED_GRID_LAYOUT_MANAGER:
                //设置布局管理器
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager
                        (mSpanCount, isVertical ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager
                                .HORIZONTAL);

                mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
                break;
            default:
                //设置布局管理器
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);

                layoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);

                mRecyclerView.setLayoutManager(layoutManager);
                break;
        }
    }

    /**
     * adapter内的处理
     *
     * @param baseViewHolder BaseViewHolder
     * @param t              泛型T
     */
    protected abstract void MyHolder(BaseViewHolder baseViewHolder, T t);

    @Override
    public void onLoadMoreRequested() {
        loadingMoreLister();
    }

    /**
     * adapter的加载更多监听
     */
    protected abstract void loadingMoreLister();


    public class CblListAdapter extends BaseQuickAdapter<T, BaseViewHolder> {

        public CblListAdapter(int layoutResId, List<T> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, T t) {
            MyHolder(baseViewHolder, t);
        }
    }

}
