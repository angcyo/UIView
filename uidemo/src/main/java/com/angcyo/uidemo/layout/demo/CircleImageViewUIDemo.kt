package com.angcyo.uidemo.layout.demo

import android.widget.VideoView
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.dialog.UIFileSelectorDialog
import com.angcyo.uiview.recycler.RBaseViewHolder

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/05/21 10:24
 * 修改人员：Robi
 * 修改时间：2018/05/21 10:24
 * 修改备注：
 * Version: 1.0.0
 */
class CircleImageViewUIDemo : BaseItemUIView() {
    var clickCount = 0

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                holder.click(R.id.video_layout) {
                    startIView(UIFileSelectorDialog().apply {
                        onFileSelector = {
                            val videoView: VideoView = if (clickCount++ % 2 == 0) {
                                holder.v(R.id.video_view2)
                            } else {
                                holder.v(R.id.video_view)
                            }
                            //videoView.requestFocus()
                            videoView.setVideoPath(it.absolutePath)
                            videoView.start()
                        }
                    })
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.circle_image_view_layout
            }
        })
    }

}