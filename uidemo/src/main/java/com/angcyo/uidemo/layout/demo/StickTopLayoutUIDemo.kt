package com.angcyo.uidemo.layout.demo

import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.design.StickTopLayout
import com.angcyo.uiview.kotlin.vh
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.widget.viewpager.UIViewPager

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/09/04 18:14
 * 修改人员：Robi
 * 修改时间：2017/09/04 18:14
 * 修改备注：
 * Version: 1.0.0
 */
class StickTopLayoutUIDemo : BaseContentUIView() {

    var mTabLayout: TabLayout? = null
    var mViewPager: UIViewPager? = null
    val stickTopLayout: StickTopLayout by vh(R.id.stick_top_layout)

    override fun getTitleBar(): TitleBarPattern? {
        return super.getTitleBar()
                ?.setFloating(true)
                ?.addRightItem(TitleBarPattern.TitleBarItem(R.drawable.base_next) {
                    stickTopLayout.openTop()
                })
    }

    override fun inflateContentLayout(baseContentLayout: ContentLayout?, inflater: LayoutInflater?) {
        inflate(R.layout.view_stick_top_layout)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        mTabLayout = v(R.id.tab_layout)
        mViewPager = v(R.id.view_pager)
        initPager()

        stickTopLayout.onTopScrollListener = object : StickTopLayout.OnTopScrollListener {
            override fun onTopScroll(isStickTop: Boolean, y: Int, max: Int) {
                uiTitleBarContainer.alpha = (max - y) * 1f / uiTitleBarContainer.measuredHeight
            }
        }
    }

    fun initPager() {
        mTabLayout?.setupWithViewPager(mViewPager)
        mViewPager?.setAdapter(BehaviorStickDemoUIView.PagerAdapter())
    }
}