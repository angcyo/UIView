package com.angcyo.uidemo.layout.recycler

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uiview.container.ContentLayout

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/07/26 10:08
 * 修改人员：Robi
 * 修改时间：2018/07/26 10:08
 * 修改备注：
 * Version: 1.0.0
 */
class CustomLayoutManagerUIDemo : BaseContentUIView() {
    override fun inflateContentLayout(baseContentLayout: ContentLayout, inflater: LayoutInflater) {
        inflate(R.layout.view_custom_layout_manager)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        val recyclerView: RecyclerView = v(R.id.recycler_view)
        recyclerView.apply {
            layoutManager = MyLayoutManager()
            adapter = MyAdapter()
        }

        val recyclerView2: RecyclerView = v(R.id.recycler_view2)
        recyclerView2.apply {
            layoutManager = LinearLayoutManager(mActivity)
            adapter = MyAdapter()
        }
    }

}