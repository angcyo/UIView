package com.angcyo.uidemo.layout.qq.demo

import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.recycler.RBaseViewHolder

/**
 * Created by angcyo on 2018-08-21.
 * Email:angcyo@126.com
 */
class QQProgressBarUIDemo : BaseItemUIView() {
    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {

            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_qq_progress_bar_layout
            }

        })
    }

}