package com.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.App;
import com.bumptech.glide.Glide;
import com.ui.gank.R;
import com.view.widget.CircleImageView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chenbaolin on 2017/4/15.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    private static final int TYPE_DIVIDER = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_HEADER = 2;

    private List<DrawerItem> dataList = Arrays.asList(
            new DrawerItemHeader("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492292572099&di=6cf7e54a0c3627a119531abc346b01cd&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fbaike%2Fpic%2Fitem%2Fa6efce1b9d16fdfac666d143b08f8c5494ee7b10.jpg",R.string.drawer_menu_login),
            new DrawerItemNormal(R.drawable.ic_menu_order, R.string.drawer_menu_rental_order),
            new DrawerItemNormal(R.drawable.ic_menu_my_vehicle, R.string.drawer_menu_my_vehicle),
            new DrawerItemNormal(R.drawable.ic_menu_order, R.string.drawer_menu_tenant_order),
            new DrawerItemNormal(R.drawable.ic_menu_common_tenant, R.string.drawer_menu_common_tenant),
            new DrawerItemNormal(R.drawable.ic_menu_my_collection, R.string.drawer_menu_my_collection),
            new DrawerItemNormal(R.drawable.ic_menu_my_purse, R.string.drawer_menu_my_purse),
            new DrawerItemNormal(R.drawable.ic_menu_discount_coupon, R.string.drawer_menu_discount_coupon),
            new DrawerItemNormal(R.drawable.ic_menu_setting, R.string.drawer_menu_setting)
            //            new DrawerItemDivider(),
    );


    @Override
    public int getItemViewType(int position) {
        DrawerItem drawerItem = dataList.get(position);
        if (drawerItem instanceof DrawerItemDivider) {
            return TYPE_DIVIDER;
        } else if (drawerItem instanceof DrawerItemNormal) {
            return TYPE_NORMAL;
        } else if (drawerItem instanceof DrawerItemHeader) {
            return TYPE_HEADER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return (dataList == null || dataList.size() == 0) ? 0 : dataList.size();
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DrawerViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_DIVIDER:
                viewHolder = new DividerViewHolder(inflater.inflate(R.layout.item_drawer_divider, parent, false));
                break;
            case TYPE_HEADER:
                viewHolder = new HeaderViewHolder(inflater.inflate(R.layout.item_drawer_header, parent, false));
                break;
            case TYPE_NORMAL:
                viewHolder = new NormalViewHolder(inflater.inflate(R.layout.item_drawer_normal, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {
        final DrawerItem item = dataList.get(position);
        if (holder instanceof NormalViewHolder) {
            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
            final DrawerItemNormal itemNormal = (DrawerItemNormal) item;
            normalViewHolder.iv.setBackgroundResource(itemNormal.iconRes);
            normalViewHolder.tv.setText(itemNormal.titleRes);

            normalViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.itemClick(itemNormal);

                    }
                }
            });
        } else if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            final DrawerItemHeader itemHeader = (DrawerItemHeader) item;
            Glide.with(App.getAppContext()).load(itemHeader.headerIcon).into(headerViewHolder.sdv_icon);
//            headerViewHolder.sdv_icon.setBackgroundResource(itemHeader.headerIcon);
            headerViewHolder.tv_login.setText(itemHeader.login);
            headerViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.itemClick(itemHeader);
                    }
                }
            });
        }

    }

    public OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void itemClick(DrawerItemHeader drawerItemHeader);
        void itemClick(DrawerItemNormal drawerItemNormal);
    }


    //-------------------------item数据模型------------------------------
    // drawerlayout item统一的数据模型
    public interface DrawerItem {
    }

    //有图片和文字的item
    public class DrawerItemNormal implements DrawerItem {
        public int iconRes;
        public int titleRes;

        public DrawerItemNormal(int iconRes, int titleRes) {
            this.iconRes = iconRes;
            this.titleRes = titleRes;
        }

    }

    //分割线item
    public class DrawerItemDivider implements DrawerItem {
        public DrawerItemDivider() {
        }
    }

    //头部item
    public class DrawerItemHeader implements DrawerItem {
        public String headerIcon;
        public int login;
        public DrawerItemHeader(@Nullable String headerIcon, int login) {
            this.headerIcon = headerIcon;
            this.login = login;
        }
    }

    //----------------------------------ViewHolder数据模型---------------------------
    //抽屉ViewHolder模型
    public class DrawerViewHolder extends RecyclerView.ViewHolder {

        public DrawerViewHolder(View itemView) {
            super(itemView);
        }
    }

    //有图标有文字ViewHolder
    public class NormalViewHolder extends DrawerViewHolder {
        public View view;
        public TextView tv;
        public ImageView iv;

        public NormalViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv = (TextView) itemView.findViewById(R.id.tv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

    //分割线ViewHolder
    public class DividerViewHolder extends DrawerViewHolder {

        public DividerViewHolder(View itemView) {
            super(itemView);
        }
    }

    //头部ViewHolder
    public class HeaderViewHolder extends DrawerViewHolder {
        public View view;
        private CircleImageView sdv_icon;
        private TextView tv_login;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            sdv_icon = (CircleImageView) itemView.findViewById(R.id.drawer_menu_login_icon);
            tv_login = (TextView) itemView.findViewById(R.id.drawer_menu_login);
        }
    }
}
