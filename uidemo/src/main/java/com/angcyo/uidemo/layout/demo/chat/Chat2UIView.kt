package com.angcyo.uidemo.layout.demo.chat

import android.app.Activity
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseRecyclerUIView
import com.angcyo.uidemo.layout.demo.BehaviorStickDemoUIView
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RExLoopRecyclerView
import com.angcyo.uiview.recycler.RPagerSnapHelper
import com.angcyo.uiview.recycler.adapter.RBaseAdapter
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
class Chat2UIView : BaseRecyclerUIView<String>() {

    companion object {
        fun createLoopAdapter(activity: Activity): RBaseAdapter<String> {
            return object : RBaseAdapter<String>(activity, BehaviorStickDemoUIView.createItems(10)) {
                override fun getItemLayoutId(viewType: Int): Int {
                    return R.layout.item_single_test_image
                }

                override fun onBindView(holder: RBaseViewHolder, position: Int, bean: String?) {
                    holder.tv(R.id.text_view).text = "测试1_pos:$position"
                    holder.tv(R.id.text_view2).text = "测试2_pos:$position"
                }

            }
        }
    }

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setShowTitleBarBottomShadow(true)
    }

    override fun createAdapter(): RExBaseAdapter<String, String, String> {
        return object : RExBaseAdapter<String, String, String>(mActivity, BehaviorStickDemoUIView.createItems()) {
            override fun getItemLayoutId(viewType: Int): Int {
                if (isHeaderItemType(viewType)) {
                    return R.layout.item_chat_head_layout
                }
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

            override fun onBindHeaderView(holder: RBaseViewHolder, posInHeader: Int, headerBean: String?) {
                super.onBindHeaderView(holder, posInHeader, headerBean)
                val loopRecyclerView: RExLoopRecyclerView = holder.v(R.id.recycler_view)
                loopRecyclerView.adapter = createLoopAdapter(mActivity)
                holder.click(R.id.pre_button) { loopRecyclerView.scrollToPrev() }
                holder.click(R.id.next_button) { loopRecyclerView.scrollToNext() }
                holder.cV(R.id.infinite_cb).setOnCheckedChangeListener { _, isChecked ->
                    loopRecyclerView.setInfinite(isChecked)
                }
                holder.cV(R.id.vertical_scroll).setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        loopRecyclerView.setOrientation(LinearLayoutManager.VERTICAL)
                    } else {
                        loopRecyclerView.setOrientation(LinearLayoutManager.HORIZONTAL)
                    }
                }

                holder.cV(R.id.start_scroll).setOnCheckedChangeListener { _, isChecked ->
                    loopRecyclerView.setEnableScroll(isChecked)
                    loopRecyclerView.startAutoScroll()
                }

                loopRecyclerView.setOnPageListener(object : RPagerSnapHelper.OnPageListener() {
                    override fun onPageSelector(fromPosition: Int, toPosition: Int) {
                        super.onPageSelector(fromPosition, toPosition)
                        holder.tv(R.id.tip_view).text = "from:$fromPosition  to:$toPosition"
                    }
                })
            }

            override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: String?) {
                super.onBindDataView(holder, posInData, dataBean)

                holder.tv(R.id.text_view).text = "测试文本: $posInData"

                holder.itemView.setOnClickListener {
                    val mTopGlow = Reflect.getMember(RecyclerView::class.java, mRecyclerView, "mTopGlow")
                    val mEdgeEffect = Reflect.getMember(mTopGlow, "mEdgeEffect")
                    val mPaint = Reflect.getMember(mEdgeEffect, "mPaint")
                    if (mPaint is Paint) {
                        mPaint.color = Color.RED
                    }
                }
            }

            override fun onBindViewHolder(holder: RBaseViewHolder, position: Int) {
                L.w("call: onBindViewHolder([Holder, position])-> $position")
                super.onBindViewHolder(holder, position)
            }
        }
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        mExBaseAdapter.resetHeaderData("")
    }

    override fun onUILoadData(page: Int, extend: String?) {
        super.onUILoadData(page, extend)
        postDelayed(300) {
            resetUI()
            showContentLayout()
        }
    }

    override fun onViewShow(viewShowCount: Long) {
        super.onViewShow(viewShowCount)
        if (viewShowCount == 2L) {
            resetUI()
            showContentLayout()
        }
    }

}
