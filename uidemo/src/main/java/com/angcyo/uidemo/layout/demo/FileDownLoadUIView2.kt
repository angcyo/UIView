package com.angcyo.uidemo.layout.demo

import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseRecyclerUIView
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
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
class FileDownLoadUIView2 : BaseRecyclerUIView<DownItem>() {

    override fun getDefaultLayoutState(): LayoutState {
        return LayoutState.CONTENT
    }

    override fun getDefaultBackgroundColor(): Int {
        return getColor(R.color.base_chat_bg_color)
    }

    private fun getDatas(): List<DownItem> {
        val list = mutableListOf<DownItem>()
        for (i in 0..30) {
            list.add(DownItem())
        }
        return list
    }

    override fun createAdapter(): RExBaseAdapter<String, DownItem, String> {
        return object : RExBaseAdapter<String, DownItem, String>(mActivity, getDatas()) {

            override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: DownItem) {
                super.onBindDataView(holder, posInData, dataBean)
                val urlEditText: ExEditText = holder.exV(R.id.edit_text)
                val progressBar1: RProgressBar = holder.v(R.id.progress_bar1)
                val progressBar2: RProgressBar = holder.v(R.id.progress_bar2)
                val tipView1 = holder.tv(R.id.tip_view1)
                val tipView2 = holder.tv(R.id.tip_view2)
                val idView1 = holder.tv(R.id.id_view1)
                val idView2 = holder.tv(R.id.id_view2)

                holder.click(R.id.download_1) {
                    if (dataBean.listener1 == null) {
                        dataBean.listener1 = object : FDownListener() {
                            override fun onProgress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int, progress: Float, scale: String?) {
                                super.onProgress(task, soFarBytes, totalBytes, progress, scale)
                                progressBar1.curProgress = progress.toInt()
                                tipView1.text = scale
                            }
                        }
                    }

                    dataBean.id1 = FDown.build(urlEditText.string()).download(dataBean.listener1)
                    idView1.text = dataBean.id1.toString()
                }
                holder.click(R.id.download_2) {
                    if (dataBean.listener2 == null) {
                        dataBean.listener2 = object : FDownListener() {
                            override fun onProgress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int, progress: Float, scale: String?) {
                                super.onProgress(task, soFarBytes, totalBytes, progress, scale)
                                progressBar2.curProgress = progress.toInt()
                                tipView2.text = scale
                            }
                        }
                    }

                    dataBean.id2 = FDown.build(urlEditText.string()).download(dataBean.listener2)
                    idView2.text = dataBean.id2.toString()
                }

                holder.click(R.id.pause_1) {
                    FDown.pause(dataBean.id1)
                }
                holder.click(R.id.pause_2) {
                    FDown.pause(dataBean.id2)
                }
            }

            override fun getItemLayoutId(viewType: Int): Int {
                return R.layout.item_file_download_layout
            }
        }
    }
}

data class DownItem(var id1: Int = -1, var id2: Int = -1, var url: String = "",
                    var listener1: FDownListener? = null, var listener2: FDownListener? = null)