package com.angcyo.uidemo.refresh;

import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.container.ContentLayout;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.rsen.RefreshLayout;
import com.angcyo.uiview.utils.Web;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/05 17:54
 * 修改人员：Robi
 * 修改时间：2016/12/05 17:54
 * 修改备注：
 * Version: 1.0.0
 */
public class WebviewUIView extends UIContentView {

    private RefreshLayout mRefreshLayout;

    @Override
    protected void inflateContentLayout(ContentLayout baseContentLayout, LayoutInflater inflater) {
        mRefreshLayout = new RefreshLayout(mActivity);
        mRefreshLayout.setTag("refresh");
        WebView webView = new WebView(mActivity);
        Web.initWebView(webView, null);
        webView.loadUrl("https://www.baidu.com/s?word=angcyo");
        mRefreshLayout.addView(webView);
        baseContentLayout.addView(mRefreshLayout);
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);
        showContentLayout();
    }

    @Override
    protected TitleBarPattern getTitleBar() {
        return null;
    }
}
