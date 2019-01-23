package com.angcyo.uidemo.layout.demo

import android.graphics.Color
import android.graphics.PixelFormat
import android.net.Uri
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import com.angcyo.library.utils.L
import com.angcyo.picker.media.OnMediaSelectorObserver
import com.angcyo.picker.media.RPicker
import com.angcyo.picker.media.bean.MediaItem
import com.angcyo.rtbs.DownloadFileBean
import com.angcyo.rtbs.X5FileDownloadUIView
import com.angcyo.rtbs.X5WebUIView
import com.angcyo.rtbs.X5WebView
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RBaseAdapter
import com.angcyo.uiview.utils.RUtils
import com.angcyo.uiview.utils.T_
import com.angcyo.uiview.utils.Tip
import com.angcyo.uiview.widget.ExEditText
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.CallBackFunction
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebView
import java.io.File


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
    override fun inflateContentLayout(baseContentLayout: ContentLayout, inflater: LayoutInflater) {
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

        webView.registerHandler("onVideoPlay") { data, _ ->
            L.e("registerHandler onVideoPlay ->$data")
            T_.info(data)
        }

        click(R.id.go_button) {
            webView.loadUrl(editView.string())
            addToLast("go->" + editView.string())
        }
        click(R.id.cookie_button) {
            val url = "http://wap.klgwl.com/survey/detail"
            webView.synCookies(mActivity, url, "test=angcyo")
            editView.setInputText(url)
            webView.loadUrl(editView.string())
            addToLast("go->" + editView.string())
        }

        click(R.id.new_button) {
            startIView(X5WebUIView(webView.url))
        }

        click(R.id.js_call) {
            webView.registerHandler("setShareAppMessage", BridgeHandler { data, function ->
                L.e("registerHandler setShareAppMessage ->$data")
                T_.info(data)
            })

            webView.callHandler("functionInJs", "data in java.", CallBackFunction {
                Tip.tip("CallBack :$it")
            })
        }
        click(R.id.js_url) {
            editView.setInputText("http://wap.klgwl.com/static/android.html")
            webView.loadUrl(editView.string())
            addToLast("go->" + editView.string())
        }

        click(R.id.upload_file) {
            editView.setInputText("http://wap.klgwl.com/feedback/add")
            webView.loadUrl(editView.string())
            addToLast("go->" + editView.string())
        }

//        X5Utils.initWebSetting(webView)
//        X5Utils.initWebViewClient(webView, object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(webView: WebView, url: String?): Boolean {
//                L.e("call: shouldOverrideUrlLoading -> $url")
//                if (!TextUtils.isEmpty(url)) {
//                    addToLast("load->" + url)
//
//                    if (url!!.startsWith("http")) {
//                        webView.loadUrl(url)
//                    } else {
//                        RUtils.openAppFromUrl(mActivity, url)
//                    }
//                }
//                return true
//            }
//        })
//        X5Utils.initWebChromeClient(webView)
//
//        X5Utils.initDownloadListener(webView, object : DownloadListener {
//            override fun onDownloadStart(url: String?, p1: String?, p2: String?, p3: String?, p4: Long) {
//                L.e("call: onDownloadStart -> $url")
//                addToLast("down->" + url)
//
//                T_.show("下载:$url")
//            }
//        })

        webView.setMyDownloadListener { url, userAgent, contentDisposition, mime, length ->
            if (TextUtils.equals(downloadUrl, url)) {

            } else {
                downloadUrl = url
                val dialog = X5FileDownloadUIView()
                dialog.mDownloadFileBean = DownloadFileBean()
                dialog.mDownloadFileBean.url = url
                dialog.mDownloadFileBean.userAgent = userAgent
                dialog.mDownloadFileBean.fileType = mime
                dialog.mDownloadFileBean.fileSize = length
                dialog.mDownloadFileBean.fileName = RUtils.getFileNameFromAttachment(contentDisposition)
                dialog.mDownloadFileBean.contentDisposition = contentDisposition
                dialog.mDownloadListener = X5FileDownloadUIView.OnDownloadListener { bean ->
                    downloadUrl = ""
                    if (bean == null) {

                    }
                }
                mParentILayout.startIView(dialog)
            }
        }

        webView.onWebViewListener = object : X5WebView.OnWebViewListener {

            override fun onPageFinished(webView: WebView, url: String?) {

            }

            override fun onProgressChanged(webView: WebView, progress: Int) {
            }

            override fun onOpenFileChooser(uploadFile: ValueCallback<Uri>, acceptType: String?, captureType: String?): Boolean {
                if (!TextUtils.isEmpty(acceptType)) {
                    if (acceptType!!.startsWith("image")) {
                        RPicker.pickerImage(mParentILayout, 1, object : OnMediaSelectorObserver {
                            override fun onMediaSelector(mediaItemList: MutableList<MediaItem>) {
                                uploadFile.onReceiveValue(Uri.fromFile(File(mediaItemList[0].path)))
                            }
                        })
                        return true
                    }
                }
                return false
            }

            override fun onOverScroll(scrollY: Int) {
            }

            override fun onScroll(left: Int, top: Int, dx: Int, dy: Int) {
            }

            override fun onReceivedTitle(webView: WebView, title: String?) {
            }
        }
    }

    private var downloadUrl = ""

    override fun onBackPressed(): Boolean {
        if (webView.canGoBack()) {
            webView.goBack()
            return false
        }
        return true
    }

    override fun onViewShow(viewShowCount: Long) {
        super.onViewShow(viewShowCount)
        webView.onResume()
        webView.resumeTimers()
    }

    override fun onViewHide() {
        super.onViewHide()
        webView.onPause()
        webView.pauseTimers()
    }

    override fun onViewUnload() {
        super.onViewUnload()
        webView.destroy()
    }

}