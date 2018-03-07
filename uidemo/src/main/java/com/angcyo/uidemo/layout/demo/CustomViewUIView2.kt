package com.angcyo.uidemo.layout.demo

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.SWEEP_GRADIENT
import android.view.View
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.UI

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/02/05 17:24
 * 修改人员：Robi
 * 修改时间：2018/02/05 17:24
 * 修改备注：
 * Version: 1.0.0
 */
class CustomViewUIView2 : BaseItemUIView() {

    override fun getTitleString(): String {
        return "自定义View演示2"
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        startCountDown()
    }

    private fun startCountDown() {
        startCountDown(3) {
            val backImageView = uiTitleBarContainer.backImageView
            backImageView.setBackgroundColor(Color.RED)
            backImageView.setImageDrawable(null)
            backImageView.showText = it.toString()
            if (it <= 0) {
                backImageView.showText = ""
                backImageView.setImageResource(R.drawable.base_back)
                backImageView.setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val drawableView = holder.v<View>(R.id.drawable_view)
                val bgDrawable = GradientDrawable().apply {
                    gradientType = SWEEP_GRADIENT
                    colors = intArrayOf(Color.RED, Color.BLUE)
                }
                UI.setBackgroundDrawable(drawableView, bgDrawable)
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_custom2_view1
            }
        })
    }
}