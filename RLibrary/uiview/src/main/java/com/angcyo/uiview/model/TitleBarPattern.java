package com.angcyo.uiview.model;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.angcyo.uiview.widget.RTitleCenterLayout;

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
     * 返回按钮的图片资源
     */
    @DrawableRes
    public int backImageRes = 0;
    /**
     * 标题进入的动画
     */
    public boolean titleAnim = false;

    /**
     * 隐藏标题
     */
    public boolean titleHide = false;

    /**
     * 标题是否显示在内容的上面, 否则内容就会显示在标题的下面
     */
    public boolean isFloating = false;

    /**
     * 当 {@link #isFloating} 为true时, 可以 使用此参数自动设置 内容的 paddingTop值
     */
    public boolean isFixContentHeight = false;
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

    public OnInitTitleLayout mOnInitTitleLayout;

    private TitleBarPattern(String titleString) {
        mTitleString = titleString;
    }

    public static TitleBarPattern fix(TitleBarPattern from, TitleBarPattern to) {
        if (to == null) {
            to = from;
        }
        to.isShowBackImageView = from.isShowBackImageView;
        to.titleAnim = from.titleAnim;
        to.isFloating = from.isFloating;
        to.isFixContentHeight = from.isFixContentHeight;
        to.titleHide = from.titleHide;
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

    public static TitleBarItem buildItem() {
        return new TitleBarItem();
    }

    public static TitleBarItem buildText(String text, View.OnClickListener listener) {
        return new TitleBarItem(text, listener);
    }

    public static TitleBarItem buildImage(@DrawableRes int icoRes, View.OnClickListener listener) {
        return new TitleBarItem(icoRes, listener);
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

    public TitleBarPattern setTitleString(Context context, @StringRes int res) {
        mTitleString = context.getResources().getString(res);
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

    public TitleBarPattern setTitleAnim(boolean titleAnim) {
        this.titleAnim = titleAnim;
        return this;
    }

    public TitleBarPattern setTitleHide(boolean titleHide) {
        this.titleHide = titleHide;
        return this;
    }

    public TitleBarPattern setFloating(boolean floating) {
        isFloating = floating;
        return this;
    }

    public TitleBarPattern setFixContentHeight(boolean fixContentHeight) {
        isFixContentHeight = fixContentHeight;
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

    public TitleBarPattern addLeftItem(TitleBarItem leftItem) {
        mLeftItems.add(leftItem);
        return this;
    }

    public TitleBarPattern addRightItem(TitleBarItem rightItem) {
        mRightItems.add(rightItem);
        return this;
    }

    public TitleBarPattern setBackImageRes(int backImageRes) {
        this.backImageRes = backImageRes;
        return this;
    }

    public TitleBarPattern setOnInitTitleLayout(OnInitTitleLayout onInitTitleLayout) {
        mOnInitTitleLayout = onInitTitleLayout;
        return this;
    }

    public interface OnInitTitleLayout {
        void onInitLayout(RTitleCenterLayout parent);
    }

    public static class TitleBarItem {
        public String text;
        @DrawableRes
        public int res = -1;
        public View.OnClickListener listener;
        public int visibility = View.VISIBLE;
        @ColorInt
        public int textColor = -1;

        private TitleBarItem() {

        }

        public TitleBarItem(String text, View.OnClickListener listener) {
            this.text = text;
            this.listener = listener;
        }

        public TitleBarItem(@DrawableRes int res, View.OnClickListener listener) {
            this.res = res;
            this.listener = listener;
        }

        @Deprecated
        public static TitleBarItem build(String text, View.OnClickListener listener) {
            return new TitleBarItem(text, listener);
        }

        @Deprecated
        public static TitleBarItem build(@DrawableRes int icoRes, View.OnClickListener listener) {
            return new TitleBarItem(icoRes, listener);
        }

        @Deprecated
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

        public TitleBarItem setVisibility(int visibility) {
            this.visibility = visibility;
            return this;
        }

        public TitleBarItem setListener(View.OnClickListener listener) {
            this.listener = listener;
            return this;
        }

        public TitleBarItem setTextColor(@ColorInt int textColor) {
            this.textColor = textColor;
            return this;
        }
    }
}
