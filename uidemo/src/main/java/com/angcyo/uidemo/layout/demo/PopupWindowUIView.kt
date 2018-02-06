package com.angcyo.uidemo.layout.demo

import android.graphics.Color
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.base.UIWindow
import com.angcyo.uiview.recycler.RBaseViewHolder

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/06/29 16:00
 * 修改人员：Robi
 * 修改时间：2017/06/29 16:00
 * 修改备注：
 * Version: 1.0.0
 */
class PopupWindowUIView : BaseItemUIView() {

    override fun getItemLayoutId(viewType: Int): Int {
        return R.layout.view_popup_window
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items?.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
//                holder.click(R.id.button1) { view ->
//                    RPopupWindow.build(mActivity)
//                            .layout(R.layout.popup_window, null)
//                            .showAsDropDown(view)
//                }
//                holder.click(R.id.button2) { view ->
//                    RPopupWindow.build(mActivity)
//                            .layout(R.layout.popup_window, null)
//                            .showAsDropDown(view)
//                }
//                holder.click(R.id.button3) { view ->
//                    RPopupWindow.build(mActivity)
//                            .layout(R.layout.popup_window, null)
//                            .showAsDropDown(view, 100, 100, Gravity.TOP)
//                }
//                holder.click(R.id.button4) { view ->
//                    RPopupWindow.build(mActivity)
//                            .layout(R.layout.popup_window, null)
//                            .showAsDropDown(view, 0, 100)
//                }
//                holder.click(R.id.button5) { view ->
//                    RPopupWindow.build(mActivity)
//                            .layout(R.layout.popup_window, null)
//                            .showAsDropDown(view)
//                }

                holder.click(R.id.button1) { view ->
                    UIWindow.build(view)
                            .layout(R.layout.popup_window)
                            .show(mParentILayout)
                }
                holder.click(R.id.button2) { view ->
                    UIWindow.build(view)
                            .layout(R.layout.popup_window)
                            .setDimBehind(false).show(mParentILayout)
                }
                holder.click(R.id.button3) { view ->
                    UIWindow.build(view)
                            .layout(R.layout.popup_window)
                            .setDimColor(Color.RED).show(mParentILayout)
                }
                holder.click(R.id.button4) { view ->
                    UIWindow.build(view)
                            .layout(R.layout.popup_window)
                            .setCanCanceledOnOutside(false).show(mParentILayout)
                }
                holder.click(R.id.button5) { view ->
                    UIWindow.build(view)
                            .layout(R.layout.popup_window)
                            .show(mParentILayout)
                }
            }
        })
    }
}