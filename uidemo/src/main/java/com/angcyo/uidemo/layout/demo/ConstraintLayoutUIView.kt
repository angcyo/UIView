package com.angcyo.uidemo.layout.demo

import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.recycler.RBaseViewHolder

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/08/26 16:07
 * 修改人员：Robi
 * 修改时间：2017/08/26 16:07
 * 修改备注：
 * Version: 1.0.0
 */
class ConstraintLayoutUIView : BaseItemUIView() {

    override fun getItemLayoutId(position: Int) = when (position) {
        0 -> R.layout.view_constraint_layout
        1 -> R.layout.view_constraint_linearlayout
        else -> R.layout.view_constraint_layout
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {

            }
        })
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {

            }
        })

        //使用约束布局模仿 帧布局
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {

            }

            override fun getItemLayoutId(): Int {
                return R.layout.view_constraint_framelayout
            }
        })

        //横向LinearLayout
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {

            }

            override fun getItemLayoutId(): Int {
                return R.layout.view_constraint_h_linearlayout
            }
        })

        //纵向LinearLayout
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {

            }

            override fun getItemLayoutId(): Int {
                return R.layout.view_constraint_v_linearlayout
            }
        })

        //横向撑满布局
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {

            }

            override fun getItemLayoutId(): Int {
                return R.layout.view_constraint_h_fill_linearlayout
            }
        })


    }
}
