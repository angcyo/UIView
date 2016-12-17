package com.angcyo.uiview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：支持滚动事件监听的WebView
 * 创建人员：Robi
 * 创建时间：2016/12/17 14:46
 * 修改人员：Robi
 * 修改时间：2016/12/17 14:46
 * 修改备注：
 * Version: 1.0.0
 */
public class ExWebView extends WebView {

    private OnScrollChangedCallback mOnScrollChangedCallback;

    public ExWebView(Context context) {
        super(context);
    }

    public ExWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ExWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl,
                                   final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l, t, l - oldl, t - oldt);
        }
    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(
            final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    /**
     * Impliment in the activity/fragment/view that you want to listen to the webview
     */
    public interface OnScrollChangedCallback {
        void onScroll(int left, int top, int dx, int dy);
    }
}
