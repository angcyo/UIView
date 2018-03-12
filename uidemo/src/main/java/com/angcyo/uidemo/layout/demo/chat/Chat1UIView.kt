package com.angcyo.uidemo.layout.demo.chat

import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseRecyclerUIView
import com.angcyo.uidemo.layout.demo.BehaviorStickDemoUIView
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.utils.Reflect

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/03/07 13:26
 * 修改人员：Robi
 * 修改时间：2018/03/07 13:26
 * 修改备注：
 * Version: 1.0.0
 */
class Chat1UIView : BaseRecyclerUIView<String>() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setShowTitleBarBottomShadow(true)
    }

    override fun createAdapter(): RExBaseAdapter<String, String, String> {
        return object : RExBaseAdapter<String, String, String>(mActivity, BehaviorStickDemoUIView.createItems()) {
            override fun getItemLayoutId(viewType: Int): Int {
                return if (viewType == 0) {
                    R.layout.item_stick_layout_top
                } else R.layout.item_stick_layout_normal
            }

            override fun getDataItemType(posInData: Int): Int {
                return if (posInData == 0) {
                    posInData
                } else super.getDataItemType(posInData)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RBaseViewHolder {
                L.w("call: onCreateViewHolder([parent, viewType])-> $viewType")
                return super.onCreateViewHolder(parent, viewType)
            }

            override fun onBindViewHolder(holder: RBaseViewHolder, position: Int) {
                L.w("call: onBindViewHolder([Holder, position])-> $position")
                super.onBindViewHolder(holder, position)
                holder.tv(R.id.text_view).text = "测试文本: $position"

                holder.itemView.setOnClickListener {
                    val mTopGlow = Reflect.getMember(RecyclerView::class.java, mRecyclerView, "mTopGlow")
                    val mEdgeEffect = Reflect.getMember(mTopGlow, "mEdgeEffect")
                    val mPaint = Reflect.getMember(mEdgeEffect, "mPaint")
                    if (mPaint is Paint) {
                        mPaint.color = Color.RED
                    }
                }
            }
        }
    }

    override fun onUILoadData(page: Int, extend: String?) {
        super.onUILoadData(page, extend)
        if (isShowInViewPager() && showInPagerCount < 2) {
            return
        }
        postDelayed(1000) {
            resetUI()
            if (isShowInViewPager()) {
                showContentLayout()
            }
        }
    }

    override fun onViewShow(viewShowCount: Long) {
        super.onViewShow(viewShowCount)
        if (viewShowCount == 2L && !isShowInViewPager()) {
            resetUI()
            showContentLayout()
        }
    }

}
