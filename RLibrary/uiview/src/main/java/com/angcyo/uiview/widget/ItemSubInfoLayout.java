package com.angcyo.uiview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.angcyo.uiview.R;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/11 08:43
 * 修改人员：Robi
 * 修改时间：2017/04/11 08:43
 * 修改备注：
 * Version: 1.0.0
 */
public class ItemSubInfoLayout extends LinearLayout {

    public static int DEFAULT_TEXT_SIZE = 15;//sp
    public static int DEFAULT_DARK_TEXT_SIZE = 14;//sp
    public static int DEFAULT_DRAW_PADDING_SIZE = 10;//dp
    public static int DEFAULT_TEXT_COLOR = Color.parseColor("#333333");
    public static int DEFAULT_DARK_TEXT_COLOR = Color.parseColor("#999999");
    RTextView itemTextView, darkTextView;
    /**
     * 主要的文本信息属性
     */
    private String itemText, itemTag;
    private int itemTextSize;//px
    private int itemTextColor;
    /**
     * 次要的文本信息属性
     */
    private String itemDarkText, itemDarkTag;
    private int itemDarkTextSize;//px
    private int itemDarkTextColor;
    private int itemDarkId = View.NO_ID;

    public ItemSubInfoLayout(Context context) {
        this(context, null);
    }

    public ItemSubInfoLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemSubInfoLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ItemSubInfoLayout);

        itemText = array.getString(R.styleable.ItemSubInfoLayout_item_text);
        itemTextSize = (int) array.getDimension(R.styleable.ItemSubInfoLayout_item_textSize, dpToPx(DEFAULT_TEXT_SIZE));
        itemTextColor = array.getColor(R.styleable.ItemSubInfoLayout_item_textColor, DEFAULT_TEXT_COLOR);

        itemDarkText = array.getString(R.styleable.ItemSubInfoLayout_item_dark_text);
        itemDarkTextSize = (int) array.getDimension(R.styleable.ItemSubInfoLayout_item_dark_textSize, dpToPx(DEFAULT_DARK_TEXT_SIZE));
        itemDarkTextColor = array.getColor(R.styleable.ItemSubInfoLayout_item_dark_textColor, DEFAULT_DARK_TEXT_COLOR);

        itemDarkId = array.getResourceId(R.styleable.ItemSubInfoLayout_item_dark_id, View.NO_ID);
        itemDarkTag = array.getString(R.styleable.ItemSubInfoLayout_item_dark_tag);
        itemTag = array.getString(R.styleable.ItemSubInfoLayout_item_text_tag);

        array.recycle();

        initLayout();
    }

    private void initLayout() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        itemTextView = new RTextView(getContext());
        darkTextView = new RTextView(getContext());

        itemTextView.setTag(itemTag);
        itemTextView.setText(itemText);
        itemTextView.setTextColor(itemTextColor);
        itemTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemTextSize);

        darkTextView.setId(itemDarkId);
        darkTextView.setTag(itemDarkTag);
        darkTextView.setText(itemDarkText);
        darkTextView.setTextColor(itemDarkTextColor);
        darkTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemDarkTextSize);
        darkTextView.setMaxLines(1);
        darkTextView.setSingleLine(true);
        darkTextView.setEllipsize(TextUtils.TruncateAt.END);

        LayoutParams itemParams = new LayoutParams(0, -1, 2);
        LayoutParams darkParams = new LayoutParams(0, -1, 8);

        addView(itemTextView, itemParams);
        addView(darkTextView, darkParams);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            setMeasuredDimension(getMeasuredWidth(),
                    getResources().getDimensionPixelOffset(R.dimen.default_button_height));
        }
    }

    public float dpToPx(float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
        return px;
    }

    public RTextView getIteitemTextView() {
        return itemTextView;
    }

    public RTextView getDarkTextView() {
        return darkTextView;
    }

    public void setItemDarkText(String itemDarkText) {
        this.itemDarkText = itemDarkText;
        darkTextView.setText(itemDarkText);
    }

    public void setItemDarkTag(String darkTag) {
        itemDarkTag = darkTag;
        darkTextView.setTag(darkTag);
    }

    public void setItemTextTag(String tag) {
        itemTag = tag;
        itemTextView.setTag(tag);
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
        itemTextView.setText(itemText);
    }
}
