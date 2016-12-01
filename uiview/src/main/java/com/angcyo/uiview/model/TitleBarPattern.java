package com.angcyo.uiview.model;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by angcyo on 2016-11-05.
 */

public class TitleBarPattern {

    /**
     * 标题栏背景颜色
     */
    public int mTitleBarBGColor = Color.TRANSPARENT;
    /**
     * 是否显示返回按钮
     */
    public boolean isShowBackImageView;
    /**
     * 标题
     */
    public String mTitleString;
    public float mTitleSize = -1;
    /**
     * 左边的按钮
     */
    public ArrayList<TitleBarItem> mLeftItems = new ArrayList<>();
    /**
     * 右边的按钮
     */
    public ArrayList<TitleBarItem> mRightItems = new ArrayList<>();

    private TitleBarPattern(String titleString) {
        mTitleString = titleString;
    }

    public static TitleBarPattern fix(TitleBarPattern from, TitleBarPattern to) {
        if (to == null) {
            to = from;
        }
        to.isShowBackImageView = from.isShowBackImageView;
        if (to.mTitleSize == -1) {
            to.mTitleSize = from.mTitleSize;
        }
        if (TextUtils.isEmpty(to.mTitleString)) {
            to.mTitleString = from.mTitleString;
        }
        if (to.mTitleBarBGColor == Color.TRANSPARENT) {
            to.mTitleBarBGColor = from.mTitleBarBGColor;
        }
        if (to.mLeftItems.size() == 0) {
            to.mLeftItems.addAll(from.mLeftItems);
        }
        if (to.mRightItems.size() == 0) {
            to.mRightItems.addAll(from.mRightItems);
        }
        return to;
    }

    public static TitleBarPattern build() {
        return build("");
    }

    public static TitleBarPattern build(String title) {
        return new TitleBarPattern(title);
    }

    public TitleBarPattern setTitleBarBGColor(@ColorInt int titleBarBGColor) {
        mTitleBarBGColor = titleBarBGColor;
        return this;
    }

    public TitleBarPattern setShowBackImageView(boolean showBackImageView) {
        isShowBackImageView = showBackImageView;
        return this;
    }

    public TitleBarPattern setTitleString(String titleString) {
        mTitleString = titleString;
        return this;
    }

    public TitleBarPattern setTitleSize(float size) {
        this.mTitleSize = size;
        return this;
    }

    public TitleBarPattern setTextViewSize(TextView textView) {
        if (mTitleSize != -1) {
            textView.setTextSize(mTitleSize);
        }
        return this;
    }

    public TitleBarPattern setLeftItems(ArrayList<TitleBarItem> leftItems) {
        mLeftItems = leftItems;
        return this;
    }

    public TitleBarPattern setRightItems(ArrayList<TitleBarItem> rightItems) {
        mRightItems = rightItems;
        return this;
    }

    public static class TitleBarItem {
        public String text;
        @DrawableRes
        public int res = -1;

        public View.OnClickListener listener;

        private TitleBarItem() {

        }

        public static TitleBarItem build() {
            return new TitleBarItem();
        }

        public TitleBarItem setText(String text) {
            this.text = text;
            return this;
        }

        public TitleBarItem setRes(int res) {
            this.res = res;
            return this;
        }

        public TitleBarItem setListener(View.OnClickListener listener) {
            this.listener = listener;
            return this;
        }
    }
}
