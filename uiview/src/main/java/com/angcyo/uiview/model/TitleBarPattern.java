package com.angcyo.uiview.model;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.view.View;

import com.angcyo.uiview.R;

import java.util.ArrayList;

/**
 * Created by angcyo on 2016-11-05.
 */

public class TitleBarPattern {
    /**
     * 标题栏背景颜色
     */
    @ColorInt
    public int mTitleBarBGColor = R.color.theme_color_primary;

    /**
     * 是否显示返回按钮
     */
    public boolean isShowBackImageView;

    /**
     * 标题
     */
    public String mTitleString;


    /**
     * 左边的按钮
     */
    public ArrayList<TitleBarItem> mLeftItems = new ArrayList<>();

    /**
     * 右边的按钮
     */
    public ArrayList<TitleBarItem> mRightItems = new ArrayList<>();

    public static TitleBarPattern build() {
        return new TitleBarPattern();
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
