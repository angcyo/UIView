package com.angcyo.uiview.utils;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/07 9:26
 * 修改人员：Robi
 * 修改时间：2016/12/07 9:26
 * 修改备注：
 * Version: 1.0.0
 */
public class Web {
    public static WebView initWebView(WebView webView, WebChromeClient webChromeClient) {
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptEnabled(true);//注意此处
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        webView.getSettings().setBuiltInZoomControls(false);//支持缩放手势
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDisplayZoomControls(true);//不显示缩放控件
//        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        //缩放网页,以便显示整个网页
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
//        webView.setInitialScale(1);

//        webView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; zh-tw) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16");

        webView.setWebChromeClient(webChromeClient);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.getSettings().setDefaultTextEncodingName("utf-8");
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });

        return webView;
    }
}
