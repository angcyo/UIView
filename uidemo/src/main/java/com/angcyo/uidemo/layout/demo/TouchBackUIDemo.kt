package com.angcyo.uidemo.layout.demo

import android.view.LayoutInflater
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uidemo.layout.demo.BehaviorStickDemoUIView.createItems
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.container.UILayoutImpl
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RBaseAdapter
import com.angcyo.uiview.utils.T_
import com.angcyo.uiview.viewgroup.TouchBackLayout
import java.util.*

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/10/16 17:12
 * 修改人员：Robi
 * 修改时间：2017/10/16 17:12
 * 修改备注：
 * Version: 1.0.0
 */
class TouchBackUIDemo : BaseContentUIView() {

    override fun getTitleBar(): TitleBarPattern? = null

    override fun getDefaultBackgroundColor(): Int = getColor(R.color.transparent_dark80)//Color.TRANSPARENT

    override fun inflateContentLayout(baseContentLayout: ContentLayout?, inflater: LayoutInflater?) {
        inflate(R.layout.view_touch_back_layout)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        mViewHolder.v<RRecyclerView>(R.id.recycler_view).apply {
            adapter = object : RBaseAdapter<String>(mActivity, createItems()) {
                override fun getItemLayoutId(viewType: Int): Int {
                    return if (viewType == 0) {
                        R.layout.item_stick_layout_top
                    } else R.layout.item_stick_layout_normal
                }

                override fun getItemType(position: Int): Int {
                    return position
                }

                override fun onBindView(holder: RBaseViewHolder, position: Int, bean: String) {
                    holder.tv(R.id.text_view).text = "测试文本::" + position
                    holder.tv(R.id.text_view).setOnClickListener { T_.show("测试文本::" + position) }
                }
            }
        }

        val random = Random(System.nanoTime())
        mViewHolder.v<TouchBackLayout>(R.id.touch_back_layout).apply {

            offsetScrollTop = if (random.nextInt(10) > 5) {
                (300 * density()).toInt()
            } else {
                0
            }

            onTouchBackListener = object : TouchBackLayout.OnTouchBackListener {
                override fun onTouchBackListener(layout: TouchBackLayout, scrollY: Int, maxScrollY: Int) {
                    L.e("call: onTouchBackListener -> $scrollY $maxScrollY")
                    if (scrollY >= maxScrollY) {
                        (mILayout as UILayoutImpl).swipeBackIView(this@TouchBackUIDemo)
                    }
                }
            }
        }

        click(R.id.button1) {
            T_.show("Click On Button1")
        }
        click(R.id.button2) {
            T_.show("Click On Button2")
        }
    }
}