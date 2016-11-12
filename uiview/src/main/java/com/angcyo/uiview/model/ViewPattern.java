package com.angcyo.uiview.model;

import android.view.View;

import com.angcyo.uiview.view.IView;

/**
 * Created by angcyo on 2016-11-12.
 */

public class ViewPattern {
    public IView mIView;
    public View mView;

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
}
