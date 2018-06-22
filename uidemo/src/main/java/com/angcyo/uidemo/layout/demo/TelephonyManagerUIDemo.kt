package com.angcyo.uidemo.layout.demo

import android.content.Context
import android.telephony.TelephonyManager
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.kotlin.setTextSizeDp
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.Reflect

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/22 09:28
 * 修改人员：Robi
 * 修改时间：2018/06/22 09:28
 * 修改备注：
 * Version: 1.0.0
 */
class TelephonyManagerUIDemo : BaseItemUIView() {
    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
//                val builder = StringBuilder()
                val telephonyManager: TelephonyManager = mActivity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

//                builder.append(Reflect.logObject(telephonyManager, true))
                holder.tv(R.id.text_view).setTextSizeDp(12f)
                holder.tv(R.id.text_view).text = Reflect.logObject(telephonyManager, true)
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_text_view
            }
        })
    }

}