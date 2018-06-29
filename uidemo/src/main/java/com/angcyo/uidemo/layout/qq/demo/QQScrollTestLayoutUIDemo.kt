package com.angcyo.uidemo.layout.qq.demo

import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uidemo.layout.qq.ScrollTestLayout
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.widget.RSeekBar

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/29 09:33
 * 修改人员：Robi
 * 修改时间：2018/06/29 09:33
 * 修改备注：
 * Version: 1.0.0
 */
class QQScrollTestLayoutUIDemo : BaseItemUIView() {
    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                val testLayout: ScrollTestLayout = holder.v(R.id.scroll_test_layout)

                val seekBar: RSeekBar = holder.v(R.id.seek_bar)
                seekBar.addOnProgressChangeListener(object : RSeekBar.OnProgressChangeListener {
                    override fun onProgress(progress: Int, fromTouch: Boolean) {
                        testLayout.refreshLayout((testLayout.measuredHeight * progress / 100f).toInt())
                    }

                    override fun onStartTouch() {
                    }

                    override fun onStopTouch() {
                    }

                })
            }

            override fun getItemLayoutId(): Int {
                return R.layout.view_qq_scroll_test
            }

        })
    }

}