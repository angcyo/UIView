package com.angcyo.uiview.base;

import android.graphics.Color;
import android.support.annotation.NonNull;

/**
 * 默认显示内容布局, 并且底色是白色
 * Created by angcyo on 2016-12-18.
 */

public abstract class UIContentView extends UIBaseRxView {
    @NonNull
    @Override
    protected LayoutState getDefaultLayoutState() {
        return LayoutState.CONTENT;
    }

    @Override
    public int getDefaultBackgroundColor() {
        return Color.WHITE;
    }
}
