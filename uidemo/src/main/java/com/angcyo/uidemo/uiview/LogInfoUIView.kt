package com.angcyo.uidemo.uiview

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.container.UIParam
import com.angcyo.uiview.kotlin.nowTime
import com.angcyo.uiview.kotlin.toFullDate
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.widget.viewpager.UIViewPager

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/26 17:17
 * 修改人员：Robi
 * 修改时间：2018/06/26 17:17
 * 修改备注：
 * Version: 1.0.0
 */
class LogInfoUIView : BaseItemUIView() {

    private var logBuild = StringBuilder(this.javaClass.simpleName)

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                holder.tv(R.id.text_view).apply {
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, 12 * density())
                    text = logBuild.toString()
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_text_view
            }

        })
    }

    override fun onViewShow(bundle: Bundle?, fromClz: Class<*>?) {
        super.onViewShow(bundle, fromClz)
        append("onViewShow:$viewShowCount $bundle ${bundle?.isEmpty} $fromClz")
    }

    override fun onViewCreate(rootView: View, param: UIParam) {
        super.onViewCreate(rootView, param)
        append("onViewCreate $rootView \n$param")
    }

    override fun onViewLoad() {
        super.onViewLoad()
        append("onViewLoad")
    }

    override fun onViewUnload(uiParam: UIParam) {
        super.onViewUnload(uiParam)
        append("onViewUnload")
    }

    override fun onViewHide() {
        super.onViewHide()
        append("onViewHide")
    }

    override fun onViewReShow(bundle: Bundle?) {
        super.onViewReShow(bundle)
        append("onViewReShow:$viewShowCount $bundle ${bundle?.isEmpty}")
    }

    override fun onShowInPager(viewPager: UIViewPager) {
        super.onShowInPager(viewPager)
        append("onShowInPager:$showInPagerCount")
    }

    override fun onHideInPager(viewPager: UIViewPager) {
        super.onHideInPager(viewPager)
        append("onHideInPager")
    }

    override fun onViewHideFromDialog() {
        super.onViewHideFromDialog()
        append("onViewHideFromDialog")
    }

    fun append(text: String) {
        logBuild.append("\n")
        logBuild.append(nowTime().toFullDate())
        logBuild.append("\n")
        logBuild.append(text)
        logBuild.append("\n")
        notifyItemChanged(0)
    }
}