package com.angcyo.uidemo.layout.demo

import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.viewgroup.SliderMenuLayout

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/04/03 15:11
 * 修改人员：Robi
 * 修改时间：2018/04/03 15:11
 * 修改备注：
 * Version: 1.0.0
 */
class SliderMenuUIView : BaseItemUIView() {

    private var slideMenuLayout: SliderMenuLayout? = null

    override fun getTitleShowString(): String {
        return "超仿QQ侧滑菜单"
    }

    override fun onBackPressed(): Boolean {
        if (slideMenuLayout != null && slideMenuLayout!!.isMenuOpen()) {
            slideMenuLayout!!.closeMenu()
            return false
        }
        return super.onBackPressed()
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                slideMenuLayout = holder.itemView as SliderMenuLayout
                slideMenuLayout?.sliderCallback = object : SliderMenuLayout.SimpleSliderCallback() {
                    override fun onMenuSlider(ratio: Float) {
                        super.onMenuSlider(ratio)
                        L.e("call: onMenuSlider -> $ratio")
                    }
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_slder_menu_layout
            }
        })
    }

}