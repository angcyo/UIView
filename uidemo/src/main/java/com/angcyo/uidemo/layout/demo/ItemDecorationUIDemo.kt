package com.angcyo.uidemo.layout.demo

import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseRecyclerUIView
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.recycler.RBaseItemDecoration
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.rsen.RefreshLayout

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/04/04 11:22
 * 修改人员：Robi
 * 修改时间：2018/04/04 11:22
 * 修改备注：
 * Version: 1.0.0
 */
class ItemDecorationUIDemo : BaseRecyclerUIView<String>() {
    override fun createAdapter(): RExBaseAdapter<String, String, String> {
        return object : RExBaseAdapter<String, String, String>(mActivity) {

            override fun getItemCount(): Int {
                return 38
            }

            override fun isInData(position: Int): Boolean {
                return true
            }

            override fun createItemView(parent: ViewGroup, viewType: Int): View? {
                val textView = TextView(mActivity)
                if (isGrid && orientation == LinearLayoutManager.HORIZONTAL) {
                    textView.layoutParams = ViewGroup.LayoutParams(-2, -1)
                } else {
                    textView.layoutParams = ViewGroup.LayoutParams(-2,
                            -2)
                }

                textView.setPadding(20, 20, 20, 20)
                textView.gravity = Gravity.CENTER_VERTICAL
                textView.setTextColor(Color.BLUE)
                return textView
            }

            override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: String?) {
                super.onBindDataView(holder, posInData, dataBean)
                (holder.itemView as TextView).text = this.javaClass.superclass.simpleName + " " + posInData
            }
        }
    }

    override fun getDefaultLayoutState(): LayoutState {
        return LayoutState.CONTENT
    }

    override fun getRecyclerRootLayoutId(): Int {
        return R.layout.view_item_decoration_layout
    }

    override fun initRefreshLayout(refreshLayout: RefreshLayout?, baseContentLayout: ContentLayout?) {
        super.initRefreshLayout(refreshLayout, baseContentLayout)
        refreshLayout?.setNoNotifyPlaceholder()
    }

    private lateinit var itemDecoration: RBaseItemDecoration
    private var orientation = LinearLayoutManager.VERTICAL
    private var isGrid = false

    override fun initRecyclerView(recyclerView: RRecyclerView?, baseContentLayout: ContentLayout?) {
        super.initRecyclerView(recyclerView, baseContentLayout)
        itemDecoration = RBaseItemDecoration((2 * density()).toInt(), Color.RED)
        recyclerView?.addItemDecoration(itemDecoration)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        click(R.id.grid_lm) {
            isGrid = true
            refresh()
        }
        click(R.id.linear_lm) {
            isGrid = false
            refresh()
        }
        click(R.id.v) {
            orientation = LinearLayoutManager.VERTICAL
            refresh()
        }
        click(R.id.h) {
            orientation = LinearLayoutManager.HORIZONTAL
            refresh()
        }
        click(R.id.first_line) {
            itemDecoration.setDrawFirstLine((it as CompoundButton).isChecked)
            refresh()
        }
        click(R.id.last_line) {
            itemDecoration.setDrawLastLine((it as CompoundButton).isChecked)
            refresh()
        }
    }

    private fun refresh() {
        val lm: RecyclerView.LayoutManager
        if (isGrid) {
            lm = GridLayoutManager(mActivity, 4, orientation, false)
        } else {
            lm = LinearLayoutManager(mActivity, orientation, false)
        }
        mRecyclerView.layoutManager = lm
    }
}