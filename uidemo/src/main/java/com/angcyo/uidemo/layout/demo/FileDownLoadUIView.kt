package com.angcyo.uidemo.layout.demo

import android.support.v4.util.ArraySet
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.view.UIIViewImpl
import com.angcyo.uiview.widget.ExEditText
import com.angcyo.uiview.widget.RProgressBar
import com.liulishuo.FDown
import com.liulishuo.FDownListener
import com.liulishuo.filedownloader.BaseDownloadTask

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/03/27 09:51
 * 修改人员：Robi
 * 修改时间：2018/03/27 09:51
 * 修改备注：
 * Version: 1.0.0
 */
class FileDownLoadUIView : BaseItemUIView() {

    companion object {
        private var count = 0
        fun get(): UIIViewImpl {
            return if (count++ % 2 == 0) {
                FileDownLoadUIView()
            } else {
                FileDownLoadUIView2()
            }
        }
    }

    private var downloadId1 = 0
    private var downloadId2 = 0

    private val arraySet = ArraySet<FDownListener>()

    override fun createItems(items: MutableList<SingleItem>) {
        for (i in 0..20) {
            items.add(object : SingleItem() {
                override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                    val urlEditText: ExEditText = holder.exV(R.id.edit_text)
                    val progressBar1: RProgressBar = holder.v(R.id.progress_bar1)
                    val progressBar2: RProgressBar = holder.v(R.id.progress_bar2)
                    val tipView1 = holder.tv(R.id.tip_view1)
                    val tipView2 = holder.tv(R.id.tip_view2)
                    val idView1 = holder.tv(R.id.id_view1)
                    val idView2 = holder.tv(R.id.id_view2)

                    holder.click(R.id.download_1) {
                        downloadId1 = FDown.build(urlEditText.string()).download(object : FDownListener() {
                            override fun onProgress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int, progress: Float, scale: String?) {
                                super.onProgress(task, soFarBytes, totalBytes, progress, scale)
                                progressBar1.curProgress = progress.toInt()
                                tipView1.text = scale
                            }
                        })
                        idView1.text = downloadId1.toString()
                    }
                    holder.click(R.id.download_2) {
                        downloadId2 = FDown.build(urlEditText.string()).download(object : FDownListener() {
                            override fun onProgress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int, progress: Float, scale: String?) {
                                super.onProgress(task, soFarBytes, totalBytes, progress, scale)
                                progressBar2.curProgress = progress.toInt()
                                tipView2.text = scale
                            }
                        })
                        idView2.text = downloadId2.toString()
                    }

                    holder.click(R.id.pause_1) {
                        FDown.pause(downloadId1)
                        val listener: FDownListener = object : FDownListener() {

                        }
                        listener.listenerUrl = "www.baidu.com"
                        L.e("call: onBindView -> ${arraySet.contains(listener)}")
                        arraySet.add(listener)
                        L.e("call: onBindView -> ${arraySet.size}")
                    }
                    val listener = FDownListener()
                    holder.click(R.id.pause_2) {
                        FDown.pause(downloadId2)
                        L.e("call: onBindView -> ${arraySet.contains(listener)}")
                        arraySet.add(listener)
                        L.e("call: onBindView -> ${arraySet.size}")
                    }
                }

                override fun getItemLayoutId(): Int {
                    return R.layout.item_file_download_layout
                }

            })
        }
    }
}