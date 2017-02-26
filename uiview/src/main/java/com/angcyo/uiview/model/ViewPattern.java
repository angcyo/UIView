package com.angcyo.uiview.model;

import android.view.View;

import com.angcyo.uiview.view.IView;

/**
 * Created by angcyo on 2016-11-12.
 */

public class ViewPattern {
    public IView mIView;
    public View mView;
    public boolean isAnimToEnd = false;//正在播放退出动画
    public boolean isAnimToStart = false;//正在播放进场动画

    public boolean interrupt = false;//在还没有启动完成的时候, 马上调用结束. 需要中断启动操作

    public ViewPattern(IView IView) {
        mIView = IView;
    }

    public ViewPattern(IView IView, View view) {
        mIView = IView;
        mView = view;
    }

    public ViewPattern setIView(IView IView) {
        mIView = IView;
        return this;
    }

    public ViewPattern setView(View view) {
        mView = view;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(mIView.getClass().getSimpleName());
        builder.append(" isAnimToStart:" + isAnimToStart);
        builder.append(" isAnimToEnd:" + isAnimToEnd);
        builder.append(" interrupt:" + interrupt);
        return builder.toString();
    }
}
