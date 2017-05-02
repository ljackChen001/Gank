package com.ui.component.cityselect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.base.util.ActivityCollector;
import com.base.util.ToastUtil;
import com.ui.gank.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.indexablerv.IndexableLayout;
import me.yokeyword.indexablerv.SimpleHeaderAdapter;

/**
 * Created by chenbaolin on 2017/4/17.
 */

public class PickCityActivity extends AppCompatActivity {
    @BindView(R.id.tool_content)
    TextView toolContent;
    private List<CityEntity> mDatas;
    private SearchFragment mSearchFragment;
    private SearchView mSearchView;
    private FrameLayout mProgressBar;
    private SimpleHeaderAdapter mHotCityAdapter;
    public Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().addActivity(new WeakReference<>(this));
        setContentView(R.layout.activity_pick_city);
        unbinder = ButterKnife.bind(this);
        initAppBarTool();

        mSearchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_fragment);
        IndexableLayout indexableLayout = (IndexableLayout) findViewById(R.id.indexableLayout);
        mSearchView = (SearchView) findViewById(R.id.searchview);
        mProgressBar = (FrameLayout) findViewById(R.id.progress);


        //indexableLayout.setLayoutManager(new LinearLayoutManager(this));
        indexableLayout.setLayoutManager(new GridLayoutManager(this, 3));

        // setAdapter
        CityAdapter adapter = new CityAdapter(this);
        indexableLayout.setAdapter(adapter);
        // set Datas
        mDatas = initDatas();

        // 快速排序。  排序规则设置为：只按首字母  （默认全拼音排序）  效率很高，是默认的10倍左右。  按需开启～
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);

        adapter.setDatas(mDatas, datas -> {
            // 数据处理完成后回调
            mSearchFragment.bindDatas(mDatas);
            mProgressBar.setVisibility(View.GONE);
        });

        // set Center OverlayView
        indexableLayout.setOverlayStyle_Center();

        // set Listener
        adapter.setOnItemContentClickListener((v, originalPosition, currentPosition, entity) -> {
            if (originalPosition >= 0) {
                ToastUtil.show("选中:" + entity.getName() + "  当前位置:" + currentPosition + " " +
                        " 原始所在数组位置:" + originalPosition);
            } else {
                ToastUtil.show("选中Header:" + entity.getName() + "  当前位置:" + currentPosition);
            }
        });

        adapter.setOnItemTitleClickListener((v, currentPosition, indexTitle) -> ToastUtil.show("选中:" + indexTitle + "  " +
                "当前位置:" + currentPosition));

        // 添加 HeaderView DefaultHeaderAdapter接收一个IndexableAdapter, 使其布局以及点击事件和IndexableAdapter一致
        // 如果想自定义布局,点击事件, 可传入 new IndexableHeaderAdapter

        mHotCityAdapter = new SimpleHeaderAdapter<>(adapter, "热", "热门城市", iniyHotCityDatas());
        // 热门城市
        indexableLayout.addHeaderAdapter(mHotCityAdapter);
        // 定位
        final List<CityEntity> gpsCity = iniyGPSCityDatas();
        final SimpleHeaderAdapter gpsHeaderAdapter = new SimpleHeaderAdapter<>(adapter, "定", "当前城市", gpsCity);
        indexableLayout.addHeaderAdapter(gpsHeaderAdapter);

        // 显示真实索引
        //        indexableLayout.showAllLetter(false);

        // 模拟定位
        indexableLayout.postDelayed(() -> {
            gpsCity.get(0).setName("杭州市");
            gpsHeaderAdapter.notifyDataSetChanged();
        }, 3000);

        // 搜索Demo
        initSearch();
    }

    private void initAppBarTool() {
        toolContent.setText("选择开户城市");
        //        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        //
        //        setSupportActionBar(toolbar);
        //        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //        getSupportActionBar().setTitle("选择城市");
    }

    //    // 更新数据点击事件
    //    public void update(View view) {
    //        List<CityEntity> list = new ArrayList<>();
    //        list.add(new CityEntity("杭州市"));
    //        list.add(new CityEntity("北京市"));
    //        list.add(new CityEntity("上海市"));
    //        list.add(new CityEntity("广州市"));
    //        mHotCityAdapter.addDatas(list);
    //        Toast.makeText(this, "更新数据", Toast.LENGTH_SHORT).show();
    //    }

    private List<CityEntity> initDatas() {
        List<CityEntity> list = new ArrayList<>();
        List<String> cityStrings = Arrays.asList(getResources().getStringArray(R.array.city_array));
        for (String item : cityStrings) {
            CityEntity cityEntity = new CityEntity();
            cityEntity.setName(item);
            list.add(cityEntity);
        }
        return list;
    }

    private List<CityEntity> iniyHotCityDatas() {
        List<CityEntity> list = new ArrayList<>();
        list.add(new CityEntity("杭州市"));
        list.add(new CityEntity("北京市"));
        list.add(new CityEntity("上海市"));
        list.add(new CityEntity("广州市"));
        return list;
    }

    private List<CityEntity> iniyGPSCityDatas() {
        List<CityEntity> list = new ArrayList<>();
        list.add(new CityEntity("定位中..."));
        return list;
    }

    private void initSearch() {
        getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0) {
                    if (mSearchFragment.isHidden()) {
                        getSupportFragmentManager().beginTransaction().show(mSearchFragment).commit();
                    }
                } else {
                    if (!mSearchFragment.isHidden()) {
                        getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();
                    }
                }

                mSearchFragment.bindQueryText(newText);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!mSearchFragment.isHidden()) {
            // 隐藏 搜索
            mSearchView.setQuery(null, false);
            getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        ActivityCollector.getInstance().removeActivity(new WeakReference<>(this));
    }
}
