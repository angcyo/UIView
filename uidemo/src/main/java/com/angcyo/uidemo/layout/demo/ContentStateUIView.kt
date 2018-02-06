package com.angcyo.uidemo.layout.demo

import android.graphics.Color
import android.view.Gravity
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.Tip

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/08/01 14:03
 * 修改人员：Robi
 * 修改时间：2017/08/01 14:03
 * 修改备注：
 * Version: 1.0.0
 */
class ContentStateUIView : BaseItemUIView() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar()
                .setTitleGravity(Gravity.LEFT)
                .setShowDarkLoading(true)
                .setTitleBarBGColor(Color.BLUE)
                .setTitleString("标题在左边")
                .addLeftItem(TitleBarPattern.TitleBarItem("左边") {
                    Tip.tip("测试 左边")
                })
                .addRightItem(TitleBarPattern.TitleBarItem("右边") {
                    Tip.tip("测试 右边")
                })
                .addRightItem(TitleBarPattern.TitleBarItem("按钮按钮按钮按钮") {
                    Tip.tip("按钮按钮按钮按钮")
                })
                .addRightItem(TitleBarPattern.TitleBarItem(R.drawable.hot_package) {
                    Tip.tip("红包")
                })
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        showLoadView()
        postDelayed(2000) {
            hideLoadView()
        }
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items?.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val itemInfo = holder.item(R.id.base_item_info_layout)
                itemInfo.setItemText("显示加载状态")
                itemInfo.setOnClickListener { showLoadLayout() }
            }
        })
        items?.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val itemInfo = holder.item(R.id.base_item_info_layout)
                itemInfo.setItemText("显示空数据状态")
                itemInfo.setOnClickListener { showEmptyLayout() }
            }
        })
        items?.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val itemInfo = holder.item(R.id.base_item_info_layout)
                itemInfo.setItemText("显示无网络状态")
                itemInfo.setOnClickListener { showNonetLayout { showContentLayout() } }
            }
        })
        items?.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val itemInfo = holder.item(R.id.base_item_info_layout)
                itemInfo.setItemText("显示错误状态")
                itemInfo.setOnClickListener { showErrorLayout() }
            }
        })
        items?.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val itemInfo = holder.item(R.id.base_item_info_layout)
                itemInfo.setItemText("显示内容状态")
                itemInfo.setOnClickListener { showContentLayout() }
            }
        })
    }
}