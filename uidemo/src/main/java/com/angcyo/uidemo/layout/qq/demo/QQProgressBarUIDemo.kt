package com.angcyo.uidemo.layout.qq.demo

import com.angcyo.animcheckviewdemo.RDrawGradientProgress
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.widget.AnimCheckView

/**
 * Created by angcyo on 2018-08-21.
 * Email:angcyo@126.com
 */
class QQProgressBarUIDemo : BaseItemUIView() {
    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                val animCheckView: AnimCheckView = holder.v(R.id.anim_check_view)
                holder.click(R.id.gradient1) {
                    animCheckView.drawGradientProgress.progressStatus = RDrawGradientProgress.STATUS_NONE
                }
                holder.click(R.id.gradient2) {
                    animCheckView.drawGradientProgress.progressStatus = RDrawGradientProgress.STATUS_GRADIENT
                }
                holder.click(R.id.gradient3) {
                    animCheckView.drawGradientProgress.progressStatus = RDrawGradientProgress.STATUS_FINISH
                }
                holder.click(R.id.gradient4) {
                    animCheckView.drawGradientProgress.progressStatus = RDrawGradientProgress.STATUS_SUCCEED
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_qq_progress_bar_layout
            }

        })
    }

}