package com.angcyo.uidemo.layout.demo

import android.text.method.KeyListener
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.widget.PasswordInputEditText

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/07/04 16:50
 * 修改人员：Robi
 * 修改时间：2017/07/04 16:50
 * 修改备注：
 * Version: 1.0.0
 */
class PasswordInputUIView : BaseItemUIView() {

    override fun getItemLayoutId(viewType: Int): Int {
        return R.layout.item_password_input
    }

    override fun haveSoftInput(): Boolean {
        return true
    }

    var keyListener: KeyListener? = null

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder?.click(R.id.button) {
                    val view: PasswordInputEditText = holder!!.v(R.id.password_input_view)
                    //view.isEnabled = false
                    if (keyListener == null) {
                        keyListener = view.keyListener
                        view.keyListener = null
                    } else {
                        view.keyListener = keyListener
                        keyListener = null
                    }
                }
            }
        })
    }
}