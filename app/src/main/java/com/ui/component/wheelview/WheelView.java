/*
 *  Android Wheel Control.
 *  https://code.google.com/p/android-wheel/
 *  
 *  Copyright 2010 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ui.component.wheelview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.ui.gank.R;

import java.util.LinkedList;
import java.util.List;


/**
 * Numeric wheel view.
 *
 * @author Yuri Kanivets
 */
public class WheelView extends View {
    /**
     * 滚动花费时间 Scrolling duration
     */
    private static final int SCROLLING_DURATION = 400;
    /**
     * 最小的滚动值, 每次最少滚动一个单位
     */
    private static final int MIN_DELTA_FOR_SCROLLING = 1;
    /**
     * 当前值与标签文本颜色
     */
    private static final int VALUE_TEXT_COLOR = 0xFF000000;
    /**
     * 项目文本颜色
     */
    private static final int ITEMS_TEXT_COLOR = 0x55333333;

    /**
     * 顶部和底部的阴影颜色
     */
    private static final int[] SHADOWS_COLORS = new int[]{0xFFFFFFFF,
            0x00FFFFFF, 0x00FFFFFF};
    /**
     * 额外的条目高度
     */
    private static final int ADDITIONAL_ITEM_HEIGHT = 30;

    /**
     * 文字字号
     */
    //	public int TEXT_SIZE;
    public int TEXT_SIZE = 15;
    /**
     * 顶部 和 底部 条目的隐藏大小,
     * 如果是正数 会隐藏一部份,
     * 0 顶部 和 底部的字正好紧贴 边缘,
     * 负数时 顶部和底部 与 字有一定间距
     */

    private final int ITEM_OFFSET = TEXT_SIZE / 5;

    private static final int ADDITIONAL_ITEMS_SPACE = 10;

    /**
     * 标签抵消
     */
    private static final int LABEL_OFFSET = 8;

    private static final int PADDING = 10;
    /**
     * 默认的可显示的条目数
     */
    private static final int DEF_VISIBLE_ITEMS = 5;

    /**
     * WheelView 适配器
     */
    private WheelAdapter adapter = null;
    /**
     * 当前显示的条目索引
     */
    private int currentItem = 0;
    /**
     * 条目宽度
     */
    private int itemsWidth = 0;
    /**
     * 标签宽度
     */
    private int labelWidth = 0;
    /**
     * 可见的条目数
     */
    private int visibleItems = DEF_VISIBLE_ITEMS;
    /**
     * 条目高度
     */
    private int itemHeight = 0;
    /**
     * 绘制普通条目画笔
     */
    private TextPaint itemsPaint;
    /**
     * 绘制选中条目画笔
     */
    private TextPaint valuePaint;
    /**
     * 普通条目布局
     * StaticLayout 布局用于控制 TextView 组件, 一般情况下不会直接使用该组件,
     * 除非你自定义一个组件 或者 想要直接调用  Canvas.drawText() 方法
     */
    private StaticLayout itemsLayout;
    private StaticLayout labelLayout;
    /**
     * 选中条目布局
     */
    private StaticLayout valueLayout;
    /**
     * 标签 在选中条目的右边出现
     */
    private String label;
    /**
     * 选中条目的背景图片
     */
    private Drawable centerDrawable;
    /**
     * 顶部阴影图片
     */
    private GradientDrawable topShadow;
    /**
     * 底部阴影图片
     */
    private GradientDrawable bottomShadow;
    /**
     * 是否在滚动
     */
    private boolean isScrollingPerformed;
    /**
     * 滚动的位置
     */
    private int scrollingOffset;
    /**
     * 手势检测器
     */
    private GestureDetector gestureDetector;
    /**
     * Scroll 类封装了滚动动作.
     * 开发者可以使用 Scroll 或者 Scroll 实现类 去收集产生一个滚动动画所需要的数据, 返回一个急冲滑动的手势.
     * 该对象可以追踪随着时间推移滚动的偏移量, 但是这些对象不会自动向 View 对象提供这些位置.
     * 如果想要使滚动动画看起来比较平滑, 开发者需要在适当的时机  获取 和 使用新的坐标;
     */
    private Scroller scroller;
    /**
     * 之前所在的 y 轴位置
     */
    private int lastScrollY;
    /**
     * 是否循环
     */
    boolean isCyclic = false;
    /**
     * 条目改变监听器集合  封装了条目改变方法, 当条目改变时回调
     */
    private List<OnWheelChangedListener> changingListeners = new LinkedList<>();
    /**
     * 条目滚动监听器集合, 该监听器封装了 开始滚动方法, 结束滚动方法
     */
    private List<OnWheelScrollListener> scrollingListeners = new LinkedList<>();

    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initData(context);
    }

    /**
     * 构造方法
     */
    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    /**
     * Constructor
     */
    public WheelView(Context context) {
        super(context);
        initData(context);
    }

    /**
     * 初始化数据
     */
    private void initData(Context context) {
        gestureDetector = new GestureDetector(context, gestureListener);
        /*
         * 是否允许长按操作,
         * 如果设置为 true 用户按下不松开, 会返回一个长按事件,
         * 如果设置为 false, 按下不松开滑动的话 会收到滚动事件.
         */
        gestureDetector.setIsLongpressEnabled(false);
        //使用默认的 时间 和 插入器 创建一个滚动器
        scroller = new Scroller(context);
    }

    /**
     * 获取该 WheelView 的适配器
     *
     * @return 返回适配器
     */
    public WheelAdapter getAdapter() {
        return adapter;
    }

    /**
     * 设置适配器
     *
     * @param adapter 要设置的适配器
     */
    public void setAdapter(WheelAdapter adapter) {
        this.adapter = adapter;
        invalidateLayouts();
        invalidate();
    }

    /**
     * 设置 Scroll 的插入器
     *
     * @param interpolator the interpolator
     */
    public void setInterpolator(Interpolator interpolator) {
        //强制停止滚动
        scroller.forceFinished(true);
        //创建一个 Scroll 对象
        scroller = new Scroller(getContext(), interpolator);
    }

    /**
     * Gets count of visible items
     *
     * @return the count of visible items
     */
    public int getVisibleItems() {
        return visibleItems;
    }

    /**
     * 获取课件条目数
     *
     * @return the count of visible items
     */
    public void setVisibleItems(int count) {
        visibleItems = count;
        invalidate();
    }

    /**
     * 获取标签
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * 设置标签
     *
     * @param newLabel the label to set
     */
    public void setLabel(String newLabel) {
        if (label == null || !label.equals(newLabel)) {
            label = newLabel;
            labelLayout = null;
            invalidate();
        }
    }

    /**
     * 添加 WheelView 选择的元素改变监听器
     *
     * @param listener the listener
     */
    public void addChangingListener(OnWheelChangedListener listener) {
        changingListeners.add(listener);
    }


    /**
     * 移除 WheelView 元素改变监听器
     *
     * @param listener the listener
     */
    public void removeChangingListener(OnWheelChangedListener listener) {
        changingListeners.remove(listener);
    }


    /**
     * 回调元素改变监听器集合的元素改变监听器元素的元素改变方法
     *
     * @param oldValue 旧的 WheelView选中的值
     * @param newValue 新的 WheelView选中的值
     */
    protected void notifyChangingListeners(int oldValue, int newValue) {
        for (OnWheelChangedListener listener : changingListeners) {
            listener.onChanged(this, oldValue, newValue);
        }
    }

    /**
     * 添加 WheelView 滚动监听器
     *
     * @param listener the listener
     */
    public void addScrollingListener(OnWheelScrollListener listener) {
        scrollingListeners.add(listener);
    }


    /**
     * 移除 WheelView 滚动监听器
     *
     * @param listener the listener
     */
    public void removeScrollingListener(OnWheelScrollListener listener) {
        scrollingListeners.remove(listener);
    }

    /**
     * 通知监听器开始滚动
     */
    protected void notifyScrollingListenersAboutStart() {
        for (OnWheelScrollListener listener : scrollingListeners) {
            listener.onScrollingStarted(this);
        }
    }

    /**
     * 通知监听器结束滚动
     */
    protected void notifyScrollingListenersAboutEnd() {
        for (OnWheelScrollListener listener : scrollingListeners) {
            //回调开始滚动方法
            listener.onScrollingFinished(this);
        }
    }


    /**
     * 获取当前选中元素的索引
     *
     * @return 当前元素索引
     */
    public int getCurrentItem() {
        return currentItem;
    }

    /**
     * 设置当前元素的位置, 如果索引是错误的 不进行任何操作
     * -- 需要考虑该 WheelView 是否能循环
     * -- 根据是否需要滚动动画来确定是 ①滚动到目的位置 还是 ②晴空所有条目然后重绘
     *
     * @param index    要设置的元素索引值
     * @param animated 动画标志位
     */
    public void setCurrentItem(int index, boolean animated) {
        if (adapter == null || adapter.getItemsCount() == 0) {
            return; // throw?
        }
        if (index < 0 || index >= adapter.getItemsCount()) {
            if (isCyclic) {
                while (index < 0) {
                    index += adapter.getItemsCount();
                }
                index %= adapter.getItemsCount();
            } else {
                return; // throw?
            }
        }
        if (index != currentItem) {
            if (animated) {
                scroll(index - currentItem, SCROLLING_DURATION);
            } else {
                invalidateLayouts();

                int old = currentItem;
                currentItem = index;

                notifyChangingListeners(old, currentItem);

                invalidate();
            }
        }
    }

    /**
     * 设置当前选中的条目, 没有动画, 当索引出错不做任何操作
     *
     * @param index 要设置的索引
     */
    public void setCurrentItem(int index) {
        setCurrentItem(index, false);
    }

    /**
     * 获取 WheelView 是否可以循环
     * -- 如果可循环 : 第一个之前是最后一个, 最后一个之后是第一个;
     * -- 如果不可循环 : 到第一个就不能上翻, 最后一个不能下翻
     *
     * @return
     */
    public boolean isCyclic() {
        return isCyclic;
    }

    /**
     * 设置 WheelView 循环标志
     *
     * @param isCyclic the flag to set
     */
    public void setCyclic(boolean isCyclic) {
        this.isCyclic = isCyclic;

        invalidate();
        invalidateLayouts();
    }

    /**
     * 使布局无效
     * 将 选中条目 和 普通条目设置为 null, 滚动位置设置为0
     */
    private void invalidateLayouts() {
        itemsLayout = null;
        valueLayout = null;
        scrollingOffset = 0;
    }

    /**
     * 初始化资源
     */
    private void initResourcesIfNecessary() {
        if (itemsPaint == null) {
            itemsPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG
                    | Paint.FAKE_BOLD_TEXT_FLAG);
            // itemsPaint.density = getResources().getDisplayMetrics().density;
            itemsPaint.setTextSize(TEXT_SIZE);
        }

        if (valuePaint == null) {
            valuePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG
                    | Paint.FAKE_BOLD_TEXT_FLAG | Paint.DITHER_FLAG);
            // valuePaint.density = getResources().getDisplayMetrics().density;
            valuePaint.setTextSize(TEXT_SIZE);
            valuePaint.setShadowLayer(0.1f, 0, 0.1f, 0xFFC0C0C0);
        }

        if (centerDrawable == null) {
            centerDrawable = getContext().getResources().getDrawable(
                    R.drawable.wheel_val);
        }

        if (topShadow == null) {
            topShadow = new GradientDrawable(Orientation.TOP_BOTTOM,
                    SHADOWS_COLORS);
        }

        if (bottomShadow == null) {
            bottomShadow = new GradientDrawable(Orientation.BOTTOM_TOP,
                    SHADOWS_COLORS);
        }

         /*
         * 设置 View 组件的背景
         */
        setBackgroundResource(R.drawable.wheel_bg);
    }

    /**
     * 计算布局期望的高度
     *
     * @param layout 组件的布局的
     * @return 布局需要的高度
     */
    private int getDesiredHeight(Layout layout) {
        if (layout == null) {
            return 0;
        }

        int desired = getItemHeight() * visibleItems - ITEM_OFFSET * 2
                - ADDITIONAL_ITEM_HEIGHT;

        // Check against our minimum height
        desired = Math.max(desired, getSuggestedMinimumHeight());

        return desired;
    }

    /**
     * 根据条目获取字符串
     *
     * @param index 条目索引
     * @return 条目显示的字符串
     */
    private String getTextItem(int index) {
        if (adapter == null || adapter.getItemsCount() == 0) {
            return null;
        }
        int count = adapter.getItemsCount();
        if ((index < 0 || index >= count) && !isCyclic) {
            return null;
        } else {
            while (index < 0) {
                index = count + index;
            }
        }

        index %= count;
        return adapter.getItem(index);
    }

    /**
     * 根据当前值创建 字符串
     *
     * @param useCurrentValue 是否在滚动
     * @return the text
     * 生成的字符串
     */
    private String buildText(boolean useCurrentValue) {
        StringBuilder itemsText = new StringBuilder();
        int addItems = visibleItems / 2 + 1;

        for (int i = currentItem - addItems; i <= currentItem + addItems; i++) {
            if (useCurrentValue || i != currentItem) {
                String text = getTextItem(i);
                if (text != null) {
                    itemsText.append(text);
                }
            }
            if (i < currentItem + addItems) {
                itemsText.append("\n");
            }
        }

        return itemsText.toString();
    }

    /**
     * 返回 条目的字符串
     *
     * @return 条目最大宽度
     */
    private int getMaxTextLength() {
        WheelAdapter adapter = getAdapter();
        if (adapter == null) {
            return 0;
        }

        //如果获取的最大条目宽度不为 -1, 可以直接返回该条目宽度
        int adapterLength = adapter.getMaximumLength();
        if (adapterLength > 0) {
            return adapterLength;
        }

        String maxText = null;
        int addItems = visibleItems / 2;
        /*
         * 遍历当前显示的条目, 获取字符串长度最长的那个, 返回这个最长的字符串长度
         */
        for (int i = Math.max(currentItem - addItems, 0); i < Math.min(
                currentItem + visibleItems, adapter.getItemsCount()); i++) {
            String text = adapter.getItem(i);
            if (text != null
                    && (maxText == null || maxText.length() < text.length())) {
                maxText = text;
            }
        }

        return maxText != null ? maxText.length() : 0;
    }

    /**
     * 获取每个条目的高度
     *
     * @return 条目的高度
     */
    private int getItemHeight() {
        //如果条目高度不为 0, 直接返回
        if (itemHeight != 0) {
            return itemHeight;
            //如果条目的高度为 0, 并且普通条目布局不为null, 条目个数大于 2
        } else if (itemsLayout != null && itemsLayout.getLineCount() > 2) {
             /*
             * itemsLayout.getLineTop(2) : 获取顶部第二行上面的垂直(y轴)位置, 如果行数等于
             */
            itemHeight = itemsLayout.getLineTop(2) - itemsLayout.getLineTop(1);
            return itemHeight;
        }
        //如果上面都不符合, 使用整体高度处以 显示条目数
        return getHeight() / visibleItems;
    }

    /**
     * 计算宽度并创建文字布局
     *
     * @param widthSize 输入的布局宽度
     * @param mode      布局模式
     * @return 计算的宽度
     */
    private int calculateLayoutWidth(int widthSize, int mode) {
        initResourcesIfNecessary();

        int width = widthSize;
        //获取最长的条目显示字符串字符个数
        int maxLength = getMaxTextLength();
        if (maxLength > 0) {
               /*
             * 使用方法 FloatMath.ceil() 方法有以下警告
             * Use java.lang.Math#ceil instead of android.util.FloatMath#ceil() since it is faster as of API 8
             */
            //float textWidth = FloatMath.ceil(Layout.getDesiredWidth("0", itemsPaint));
            //向上取整  计算一个字符串宽度
            float textWidth = (float) Math.ceil(Layout.getDesiredWidth("0",
                    itemsPaint));
            //获取字符串总的宽度
            itemsWidth = (int) (maxLength * textWidth);
        } else {

            itemsWidth = 0;
        }
        //总宽度加上一些间距
        itemsWidth += ADDITIONAL_ITEMS_SPACE; // make it some more

        //计算 label 的长度
        labelWidth = 0;
        if (label != null && label.length() > 0) {
            labelWidth = (int) (float) Math.ceil(Layout.getDesiredWidth(label,
                    valuePaint));
        }

        boolean recalculate = false;
        if (mode == MeasureSpec.EXACTLY) {
            width = widthSize;
            recalculate = true;
        } else {
            width = itemsWidth + labelWidth + 2 * PADDING;
            if (labelWidth > 0) {
                width += LABEL_OFFSET;
            }

            // Check against our minimum width
            width = Math.max(width, getSuggestedMinimumWidth());

            if (mode == MeasureSpec.AT_MOST && widthSize < width) {
                width = widthSize;
                recalculate = true;
            }
        }

        if (recalculate) {
            // recalculate width
            int pureWidth = width - LABEL_OFFSET - 2 * PADDING;
            if (pureWidth <= 0) {
                itemsWidth = labelWidth = 0;
            }
            if (labelWidth > 0) {
                double newWidthItems = (double) itemsWidth * pureWidth
                        / (itemsWidth + labelWidth);
                itemsWidth = (int) newWidthItems;
                labelWidth = pureWidth - itemsWidth;
            } else {
                itemsWidth = pureWidth + LABEL_OFFSET; // no label
            }
        }

        if (itemsWidth > 0) {
            createLayouts(itemsWidth, labelWidth);
        }

        return width;
    }

    /**
     * 创建布局
     *
     * @param widthItems 布局条目宽度
     * @param widthLabel label 宽度
     */
    private void createLayouts(int widthItems, int widthLabel) { /*
         * 创建普通条目布局
         * 如果 普通条目布局 为 null 或者 普通条目布局的宽度 大于 传入的宽度, 这时需要重新创建布局
         * 如果 普通条目布局存在, 并且其宽度小于传入的宽度, 此时需要将
         */

        if (itemsLayout == null || itemsLayout.getWidth() > widthItems) {
             /*
             * android.text.StaticLayout.StaticLayout(
             * CharSequence source, TextPaint paint,
             * int width, Alignment align,
             * float spacingmult, float spacingadd, boolean includepad)
             * 传入参数介绍 :
             * CharSequence source : 需要分行显示的字符串
             * TextPaint paint : 绘制字符串的画笔
             * int width : 条目的宽度
             * Alignment align : Layout 的对齐方式, ALIGN_CENTER 居中对齐, ALIGN_NORMAL 左对齐, Alignment.ALIGN_OPPOSITE 右对齐
             * float spacingmult : 行间距, 1.5f 代表 1.5 倍字体高度
             * float spacingadd : 基础行距上增加多少 , 真实行间距 等于 spacingmult 和 spacingadd 的和
             * boolean includepad :
             */
            itemsLayout = new StaticLayout(buildText(isScrollingPerformed),
                    itemsPaint, widthItems,
                    widthLabel > 0 ? Layout.Alignment.ALIGN_OPPOSITE
                            : Layout.Alignment.ALIGN_CENTER, 1,
                    ADDITIONAL_ITEM_HEIGHT, false);
        } else {

            //调用 Layout 内置的方法 increaseWidthTo 将宽度提升到指定的宽度
            itemsLayout.increaseWidthTo(widthItems);
        }
        /*
         * 创建选中条目
         */
        if (!isScrollingPerformed
                && (valueLayout == null || valueLayout.getWidth() > widthItems)) {
            String text = getAdapter() != null ? getAdapter().getItem(
                    currentItem) : null;
            valueLayout = new StaticLayout(text != null ? text : "",
                    valuePaint, widthItems,
                    widthLabel > 0 ? Layout.Alignment.ALIGN_OPPOSITE
                            : Layout.Alignment.ALIGN_CENTER, 1,
                    ADDITIONAL_ITEM_HEIGHT, false);
        } else if (isScrollingPerformed) {
            valueLayout = null;
        } else {
            valueLayout.increaseWidthTo(widthItems);
        }

        if (widthLabel > 0) {
            if (labelLayout == null || labelLayout.getWidth() > widthLabel) {
                labelLayout = new StaticLayout(label, valuePaint, widthLabel,
                        Layout.Alignment.ALIGN_NORMAL, 1,
                        ADDITIONAL_ITEM_HEIGHT, false);
            } else {
                labelLayout.increaseWidthTo(widthLabel);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取宽度 和 高度的模式 和 大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = calculateLayoutWidth(widthSize, widthMode);

        int height;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getDesiredHeight(itemsLayout);

            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (itemsLayout == null) {
            if (itemsWidth == 0) {
                calculateLayoutWidth(getWidth(), MeasureSpec.EXACTLY);
            } else {
                createLayouts(itemsWidth, labelWidth);
            }
        }

        if (itemsWidth > 0) {
            canvas.save();
            // Skip padding space and hide a part of top and bottom items
            canvas.translate(PADDING, -ITEM_OFFSET);
            drawItems(canvas);
            drawValue(canvas);
            canvas.restore();
        }

        drawCenterRect(canvas);
        drawShadows(canvas);
    }

    /**
     * Draws shadows on top and bottom of control
     *
     * @param canvas the canvas for drawing
     */
    private void drawShadows(Canvas canvas) {
        topShadow.setBounds(0, 0, getWidth(), getHeight() / visibleItems);
        topShadow.draw(canvas);

        bottomShadow.setBounds(0, getHeight() - getHeight() / visibleItems,
                getWidth(), getHeight());
        bottomShadow.draw(canvas);
    }


    /**
     * 绘制选中条目
     *
     * @param canvas 画布
     */
    private void drawValue(Canvas canvas) {
        valuePaint.setColor(VALUE_TEXT_COLOR);
        //将当前 View 状态属性值 转为整型集合, 赋值给 普通条目布局的绘制属性
        valuePaint.drawableState = getDrawableState();

        Rect bounds = new Rect();
        //获取选中条目布局的边界
        itemsLayout.getLineBounds(visibleItems / 2, bounds);

        // draw label
        if (labelLayout != null) {
            canvas.save();
            canvas.translate(itemsLayout.getWidth() + LABEL_OFFSET, bounds.top);
            labelLayout.draw(canvas);
            canvas.restore();
        }

        // draw current value
        if (valueLayout != null) {
            canvas.save();
            canvas.translate(0, bounds.top + scrollingOffset);
            valueLayout.draw(canvas);
            canvas.restore();
        }
    }

    /**
     * 绘制普通条目
     *
     * @param canvas 画布
     */
    private void drawItems(Canvas canvas) {
        canvas.save();

        int top = itemsLayout.getLineTop(1);
        canvas.translate(0, -top + scrollingOffset);

        itemsPaint.setColor(ITEMS_TEXT_COLOR);
        itemsPaint.drawableState = getDrawableState();
        itemsLayout.draw(canvas);

        canvas.restore();
    }


    /**
     * 绘制当前选中条目的背景图片
     *
     * @param canvas 画布
     */
    private void drawCenterRect(Canvas canvas) {
        int center = getHeight() / 2;
        int offset = getItemHeight() / 2;
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        //		centerDrawable.setBounds(0, center - offset, getWidth(), center
        //				+ offset);
        //		centerDrawable.draw(canvas);
        canvas.drawLine(0, center - offset, getWidth(), center - offset, paint);
        canvas.drawLine(0, center + offset, getWidth(), center + offset, paint);
    }

    /*
        * 继承自 View 的触摸事件, 当出现触摸事件的时候, 就会回调该方法
        * (non-Javadoc)
        * @see android.view.View#onTouchEvent(android.view.MotionEvent)
        */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        WheelAdapter adapter = getAdapter();
        if (adapter == null) {
            return true;
        }
 /*
         * gestureDetector.onTouchEvent(event) : 分析给定的动作, 如果可用, 调用 手势检测器的 onTouchEvent 方法
         * -- 参数解析 : ev , 触摸事件
         * -- 返回值 : 如果手势监听器成功执行了该方法, 返回true, 如果执行出现意外 返回 false;
         */
        if (!gestureDetector.onTouchEvent(event)
                && event.getAction() == MotionEvent.ACTION_UP) {
            justify();
        }
        return true;
    }

    /**
     * 滚动 WheelView
     *
     * @param delta
     *            滚动的值
     */
    private void doScroll(int delta) {
        scrollingOffset += delta;

        int count = scrollingOffset / getItemHeight();
        int pos = currentItem - count;
        if (isCyclic && adapter.getItemsCount() > 0) {
            // fix position by rotating
            while (pos < 0) {
                pos += adapter.getItemsCount();
            }
            pos %= adapter.getItemsCount();
        } else if (isScrollingPerformed) {
            //
            if (pos < 0) {
                count = currentItem;
                pos = 0;
            } else if (pos >= adapter.getItemsCount()) {
                count = currentItem - adapter.getItemsCount() + 1;
                pos = adapter.getItemsCount() - 1;
            }
        } else {
            // fix position
            pos = Math.max(pos, 0);
            pos = Math.min(pos, adapter.getItemsCount() - 1);
        }

        int offset = scrollingOffset;
        if (pos != currentItem) {
            setCurrentItem(pos, false);
        } else {
            invalidate();
        }

        // update offset
        scrollingOffset = offset - count * getItemHeight();
        if (scrollingOffset > getHeight()) {
            scrollingOffset = scrollingOffset % getHeight() + getHeight();
        }
    }

    // gesture listener
    private SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
        public boolean onDown(MotionEvent e) {
            if (isScrollingPerformed) {
                scroller.forceFinished(true);
                clearMessages();
                return true;
            }
            return false;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            startScrolling();
            doScroll((int) -distanceY);
            return true;
        }
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            lastScrollY = currentItem * getItemHeight() + scrollingOffset;
            int maxY = isCyclic ? 0x7FFFFFFF : adapter.getItemsCount()
                    * getItemHeight();
            int minY = isCyclic ? -maxY : 0;
            scroller.fling(0, lastScrollY, 0, (int) -velocityY / 2, 0, 0, minY,
                    maxY);
            setNextMessage(MESSAGE_SCROLL);
            return true;
        }
    };

    // Messages
    private final int MESSAGE_SCROLL = 0;
    private final int MESSAGE_JUSTIFY = 1;

    /**
     * Set next message to queue. Clears queue before.
     *
     * @param message the message to set
     */
    private void setNextMessage(int message) {
        clearMessages();
        animationHandler.sendEmptyMessage(message);
    }

    /**
     * Clears messages from queue
     */
    private void clearMessages() {
        animationHandler.removeMessages(MESSAGE_SCROLL);
        animationHandler.removeMessages(MESSAGE_JUSTIFY);
    }

    // animation handler
    private Handler animationHandler = new Handler() {
        public void handleMessage(Message msg) {
            scroller.computeScrollOffset();
            int currY = scroller.getCurrY();
            int delta = lastScrollY - currY;
            lastScrollY = currY;
            if (delta != 0) {
                doScroll(delta);
            }

            // scrolling is not finished when it comes to final Y
            // so, finish it manually
            if (Math.abs(currY - scroller.getFinalY()) < MIN_DELTA_FOR_SCROLLING) {
                currY = scroller.getFinalY();
                scroller.forceFinished(true);
            }
            if (!scroller.isFinished()) {
                animationHandler.sendEmptyMessage(msg.what);
            } else if (msg.what == MESSAGE_SCROLL) {
                justify();
            } else {
                finishScrolling();
            }
        }
    };

    /**
     * Justifies wheel
     */
    private void justify() {
        if (adapter == null) {
            return;
        }

        lastScrollY = 0;
        int offset = scrollingOffset;
        int itemHeight = getItemHeight();
        boolean needToIncrease = offset > 0 ? currentItem < adapter
                .getItemsCount() : currentItem > 0;
        if ((isCyclic || needToIncrease)
                && Math.abs((float) offset) > (float) itemHeight / 2) {
            if (offset < 0)
                offset += itemHeight + MIN_DELTA_FOR_SCROLLING;
            else
                offset -= itemHeight + MIN_DELTA_FOR_SCROLLING;
        }
        if (Math.abs(offset) > MIN_DELTA_FOR_SCROLLING) {
            scroller.startScroll(0, 0, 0, offset, SCROLLING_DURATION);
            setNextMessage(MESSAGE_JUSTIFY);
        } else {
            finishScrolling();
        }
    }

    /**
     * Starts scrolling
     */
    private void startScrolling() {
        if (!isScrollingPerformed) {
            isScrollingPerformed = true;
            notifyScrollingListenersAboutStart();
        }
    }

    /**
     * Finishes scrolling
     */
    void finishScrolling() {
        if (isScrollingPerformed) {
            notifyScrollingListenersAboutEnd();
            isScrollingPerformed = false;
        }
        invalidateLayouts();
        invalidate();
    }

    /**
     * Scroll the wheel
     * <p>
     * items to scroll
     *
     * @param time scrolling duration
     */
    public void scroll(int itemsToScroll, int time) {
        scroller.forceFinished(true);

        lastScrollY = scrollingOffset;
        int offset = itemsToScroll * getItemHeight();

        scroller.startScroll(0, lastScrollY, 0, offset - lastScrollY, time);
        setNextMessage(MESSAGE_SCROLL);

        startScrolling();
    }

}
