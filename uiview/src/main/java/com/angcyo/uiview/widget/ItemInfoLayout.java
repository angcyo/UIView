package com.angcyo.uiview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.uiview.R;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/17 12:41
 * 修改人员：Robi
 * 修改时间：2016/12/17 12:41
 * 修改备注：
 * Version: 1.0.0
 */
public class ItemInfoLayout extends RelativeLayout {

    public static int DEFAULT_TEXT_SIZE = 15;//sp
    public static int DEFAULT_DARK_TEXT_SIZE = 14;//sp
    public static int DEFAULT_DRAW_PADDING_SIZE = 10;//dp

    public static int DEFAULT_TEXT_COLOR = Color.parseColor("#333333");
    public static int DEFAULT_DARK_TEXT_COLOR = Color.parseColor("#999999");
    TextView mTextView, mDarkTextView;
    /**
     * 主要的文本信息属性
     */
    private String itemText;
    private int itemTextSize;//px
    private int itemTextColor;
    /**
     * 次要的文本信息属性
     */
    private String itemDarkText;
    private int itemDarkTextSize;//px
    private int itemDarkTextColor;
    /**
     * 文本与图标的距离,px
     */
    private int drawPadding;
    /**
     * 左边的图标, 右边的图标, 附加的图标
     */
    @DrawableRes
    private int leftDrawableRes = -1, rightDrawableRes = -1, darkDrawableRes = -1;

    public ItemInfoLayout(Context context) {
        this(context, null);
    }

    public ItemInfoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemInfoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ItemInfoLayout);

        itemText = array.getString(R.styleable.ItemInfoLayout_item_text);
        itemTextSize = (int) array.getDimension(R.styleable.ItemInfoLayout_item_textSize, dpToPx(DEFAULT_TEXT_SIZE));
        itemTextColor = array.getColor(R.styleable.ItemInfoLayout_item_textColor, DEFAULT_TEXT_COLOR);

        itemDarkText = array.getString(R.styleable.ItemInfoLayout_item_dark_text);
        itemDarkTextSize = (int) array.getDimension(R.styleable.ItemInfoLayout_item_dark_textSize, dpToPx(DEFAULT_DARK_TEXT_SIZE));
        itemDarkTextColor = array.getColor(R.styleable.ItemInfoLayout_item_dark_textColor, DEFAULT_DARK_TEXT_COLOR);

        drawPadding = (int) array.getDimension(R.styleable.ItemInfoLayout_item_draw_padding, DEFAULT_DRAW_PADDING_SIZE);

        leftDrawableRes = array.getResourceId(R.styleable.ItemInfoLayout_item_left_res, leftDrawableRes);
        rightDrawableRes = array.getResourceId(R.styleable.ItemInfoLayout_item_right_res, rightDrawableRes);
        darkDrawableRes = array.getResourceId(R.styleable.ItemInfoLayout_item_dark_res, darkDrawableRes);

        array.recycle();

        initLayout();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemInfoLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public float dpToPx(float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
        return px;
    }

    private void initLayout() {
        mTextView = new TextView(getContext());
        mDarkTextView = new TextView(getContext());

        mTextView.setText(itemText);
        mTextView.setTextColor(itemTextColor);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemTextSize);
        mTextView.setCompoundDrawablePadding(drawPadding);
        mTextView.setGravity(Gravity.CENTER_VERTICAL);
        setLeftDrawableRes(leftDrawableRes);

        mDarkTextView.setText(itemDarkText);
        mDarkTextView.setTextColor(itemDarkTextColor);
        mDarkTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemDarkTextSize);
        mDarkTextView.setCompoundDrawablePadding(drawPadding);
        mDarkTextView.setGravity(Gravity.CENTER_VERTICAL);
        setRightDrawableRes(rightDrawableRes);
        setDarkDrawableRes(darkDrawableRes);

        LayoutParams params = new LayoutParams(-2, -2);
        params.addRule(RelativeLayout.CENTER_VERTICAL);

        LayoutParams paramsDark = new LayoutParams(-2, -2);
        paramsDark.addRule(RelativeLayout.CENTER_VERTICAL);
        paramsDark.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        addView(mTextView, params);
        addView(mDarkTextView, paramsDark);
    }

    public void setDarkDrawableRes(int darkDrawableRes) {
        this.darkDrawableRes = darkDrawableRes;
        final Drawable[] compoundDrawables = mDarkTextView.getCompoundDrawables();
        if (darkDrawableRes == -1) {
            mDarkTextView.setCompoundDrawablesWithIntrinsicBounds(
                    null, compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
        } else {
            mDarkTextView.setCompoundDrawablesWithIntrinsicBounds(
                    getResources().getDrawable(darkDrawableRes),
                    compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
        }
    }

    public void setRightDrawableRes(int rightDrawableRes) {
        this.rightDrawableRes = rightDrawableRes;
        final Drawable[] compoundDrawables = mDarkTextView.getCompoundDrawables();
        if (rightDrawableRes == -1) {
            mDarkTextView.setCompoundDrawablesWithIntrinsicBounds(
                    compoundDrawables[0], compoundDrawables[1], null, compoundDrawables[3]);
        } else {
            mDarkTextView.setCompoundDrawablesWithIntrinsicBounds(
                    compoundDrawables[0], compoundDrawables[1],
                    getResources().getDrawable(rightDrawableRes), compoundDrawables[3]);
        }
    }

    public void setLeftDrawableRes(int leftDrawableRes) {
        this.leftDrawableRes = leftDrawableRes;
        final Drawable[] compoundDrawables = mTextView.getCompoundDrawables();
        if (leftDrawableRes == -1) {
            mTextView.setCompoundDrawablesWithIntrinsicBounds(null,
                    compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
        } else {
            mTextView.setCompoundDrawablesWithIntrinsicBounds(
                    getResources().getDrawable(leftDrawableRes),
                    compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
        }
    }

    public void setItemDarkText(String itemDarkText) {
        this.itemDarkText = itemDarkText;
        mDarkTextView.setText(itemDarkText);
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
        mTextView.setText(itemText);
    }
}
