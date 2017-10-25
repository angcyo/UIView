package com.angcyo.uidemo.layout.demo

import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.dialog.UIFileSelectorDialog
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.widget.RTextView

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/10/24 18:21
 * 修改人员：Robi
 * 修改时间：2017/10/24 18:21
 * 修改备注：
 * Version: 1.0.0
 */

class Mp3PlayUIDemo : BaseItemUIView() {
    override fun createItems(items: MutableList<SingleItem>?) {
        items?.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val textView: RTextView = holder.v(R.id.text_view)
                holder.click(R.id.selector_button) {
                    startIView(UIFileSelectorDialog {
                        textView.text = it.absolutePath
                    })
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_mp3_play
            }
        })
    }
}