package com.angcyo.uidemo.layout.demo

import android.widget.TextView
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.widget.RExTextView

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/11/09 08:34
 * 修改人员：Robi
 * 修改时间：2017/11/09 08:34
 * 修改备注：
 * Version: 1.0.0
 */
class RegularTestUIDemo : BaseItemUIView() {
    override fun createItems(items: MutableList<SingleItem>?) {
        items?.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.tv(R.id.tip_view).text = "Url正则测试"
                holder.eV(R.id.edit_text).hint = RExTextView.patternUrl.pattern()

                holder.click(R.id.button) {
                    (it as TextView).text = RExTextView.isWebUrl(holder.exV(R.id.edit_text).string()).toString()
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_regular_input_tip_layout
            }
        })
        items?.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.tv(R.id.tip_view).text = "Phone正则测试"
                holder.eV(R.id.edit_text).hint = RExTextView.patternPhone.pattern()
                holder.click(R.id.button) {
                    (it as TextView).text = RExTextView.isPhone(holder.exV(R.id.edit_text).string()).toString()
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_regular_input_tip_layout
            }
        })
        items?.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.tv(R.id.tip_view).text = "Tel正则测试"
                holder.eV(R.id.edit_text).hint = RExTextView.patternTel.pattern()
                holder.click(R.id.button) {
                    (it as TextView).text = RExTextView.isTel(holder.exV(R.id.edit_text).string()).toString()
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_regular_input_tip_layout
            }
        })
        items?.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.tv(R.id.tip_view).text = "Number正则测试"
                holder.eV(R.id.edit_text).hint = RExTextView.patternNumber.pattern()
                holder.click(R.id.button) {
                    (it as TextView).text = RExTextView.isNumber(holder.exV(R.id.edit_text).string()).toString()
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_regular_input_tip_layout
            }
        })
    }

    override fun haveSoftInput(): Boolean {
        return true
    }

}