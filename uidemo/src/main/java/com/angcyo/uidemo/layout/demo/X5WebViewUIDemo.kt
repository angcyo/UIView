package com.angcyo.uidemo.layout.demo

import android.graphics.PixelFormat
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uidemo.x5.X5Utils
import com.angcyo.uidemo.x5.X5WebView
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.utils.T_
import com.angcyo.uiview.widget.ExEditText
import com.tencent.smtt.sdk.DownloadListener
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/10/31 15:42
 * 修改人员：Robi
 * 修改时间：2017/10/31 15:42
 * 修改备注：
 * Version: 1.0.0
 */
class X5WebViewUIDemo : BaseContentUIView() {
    override fun inflateContentLayout(baseContentLayout: ContentLayout?, inflater: LayoutInflater?) {
        inflate(R.layout.view_x5_web_view)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        mActivity.window.setFormat(PixelFormat.TRANSLUCENT)

        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                mActivity.window.setFlags(
                        android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
            }
        } catch (e: Exception) {
        }


        val webView: X5WebView = mViewHolder.v(R.id.web_view)
        val editView: ExEditText = mViewHolder.v(R.id.edit_text)

        editView.text = SpannableStringBuilder("http://wap.klgwl.com/index/openapp")

        mViewHolder.click(R.id.go_button) {
            webView.loadUrl(editView.string())
        }

        X5Utils.initWebSetting(webView)
        X5Utils.initWebViewClient(webView, object : WebViewClient() {
            override fun shouldOverrideUrlLoading(webView: WebView, url: String?): Boolean {
                L.e("call: shouldOverrideUrlLoading -> $url")
                webView.loadUrl(url)
                return true
            }
        })
        X5Utils.initWebChromeClient(webView)

        X5Utils.initDownloadListener(webView, object : DownloadListener {
            override fun onDownloadStart(url: String?, p1: String?, p2: String?, p3: String?, p4: Long) {
                L.e("call: onDownloadStart -> $url")
                T_.show("下载:$url")
            }
        })
    }
}