package com.angcyo.uidemo.layout.demo

import android.graphics.Color
import android.graphics.PixelFormat
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import com.angcyo.library.utils.L
import com.angcyo.rtbs.X5WebUIView
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uidemo.x5.X5Utils
import com.angcyo.uidemo.x5.X5WebView
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RBaseAdapter
import com.angcyo.uiview.utils.RUtils
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

    private lateinit var webView: X5WebView
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


        webView = mViewHolder.v(R.id.web_view)
        val editView: ExEditText = mViewHolder.v(R.id.edit_text)

        editView.text = SpannableStringBuilder("http://wap.klgwl.com/index/openapp")

        val recyclerView: RRecyclerView = v(R.id.recycler_view)
        val adapter = object : RBaseAdapter<String>(mActivity) {
            override fun getItemLayoutId(viewType: Int): Int {
                return R.layout.item_single_text_view
            }

            override fun onBindView(holder: RBaseViewHolder, position: Int, bean: String) {
                holder.tv(R.id.text_view).text = bean
                holder.tv(R.id.text_view).setTextColor(Color.WHITE)
                holder.tv(R.id.text_view).setTextSize(TypedValue.COMPLEX_UNIT_PX, 9 * density())
                holder.itemView.setBackgroundResource(R.drawable.base_tran_round_shape)
            }
        }
        recyclerView.adapter = adapter

        fun addToLast(text: String) {
            adapter.addLastItem(text)
            recyclerView.scrollToLastBottom(true)
        }

        mViewHolder.click(R.id.go_button) {
            webView.loadUrl(editView.string())
            addToLast("go->" + editView.string())
        }

        click(R.id.new_button) {
            startIView(X5WebUIView(webView.url))
        }

        X5Utils.initWebSetting(webView)
        X5Utils.initWebViewClient(webView, object : WebViewClient() {
            override fun shouldOverrideUrlLoading(webView: WebView, url: String?): Boolean {
                L.e("call: shouldOverrideUrlLoading -> $url")
                if (!TextUtils.isEmpty(url)) {
                    addToLast("load->" + url)

                    if (url!!.startsWith("http")) {
                        webView.loadUrl(url)
                    } else {
                        RUtils.openAppFromUrl(mActivity, url)
                    }
                }
                return true
            }
        })
        X5Utils.initWebChromeClient(webView)

        X5Utils.initDownloadListener(webView, object : DownloadListener {
            override fun onDownloadStart(url: String?, p1: String?, p2: String?, p3: String?, p4: Long) {
                L.e("call: onDownloadStart -> $url")
                addToLast("down->" + url)

                T_.show("下载:$url")
            }
        })
    }

    override fun onBackPressed(): Boolean {
        if (webView.canGoBack()) {
            webView.goBack()
            return false
        }
        return true
    }
}