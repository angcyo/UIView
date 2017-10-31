package com.angcyo.uidemo.x5;

import android.annotation.SuppressLint;
import android.view.View;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/10/31 15:40
 * 修改人员：Robi
 * 修改时间：2017/10/31 15:40
 * 修改备注：
 * Version: 1.0.0
 */
public class X5Utils {
    @SuppressLint("SetJavaScriptEnabled")
    public static void initWebSetting(WebView webView) {
        WebSettings webSetting = webView.getSettings();
        webSetting.setDefaultTextEncodingName("utf-8");

        webSetting.setJavaScriptEnabled(true);

        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);//如果为true时, 会影响下载文件事件
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(webView.getContext().getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(webView.getContext().getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(webView.getContext().getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        //long time = System.currentTimeMillis();
        CookieSyncManager.createInstance(webView.getContext());
        CookieSyncManager.getInstance().sync();
    }

    public static void initWebViewClient(WebView webView, final WebViewClient webViewClient) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (webViewClient != null) {
                    return webViewClient.shouldOverrideUrlLoading(view, url);
                } else {
                    view.loadUrl(url);
                    return true;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (webViewClient != null) {
                    webViewClient.onPageFinished(view, url);
                }
            }
        });
    }

    public static void initWebChromeClient(WebView webView) {
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            // /////////////////////////////////////////////////////////
            //

            /**
             * 全屏播放配置
             */
            @Override
            public void onShowCustomView(View view,
                                         IX5WebChromeClient.CustomViewCallback customViewCallback) {
            }

            @Override
            public void onHideCustomView() {
            }

            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                                     JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3);
            }
        });

    }

    public static void initDownloadListener(WebView webView, final DownloadListener downloadListener) {

        webView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String arg0, String arg1, String arg2,
                                        String arg3, long arg4) {
                if (downloadListener != null) {
                    downloadListener.onDownloadStart(arg0, arg1, arg2, arg3, arg4);
                }
            }
        });
    }
}
