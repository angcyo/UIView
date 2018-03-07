package com.angcyo.uidemo.layout.demo

import android.view.LayoutInflater
import android.widget.RadioGroup
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RExLoopRecyclerView
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RBaseAdapter
import com.angcyo.uiview.utils.UI
import com.leochuan.*

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/02/09 16:52
 * 修改人员：Robi
 * 修改时间：2018/02/09 16:52
 * 修改备注：
 * Version: 1.0.0
 */
class RVLayoutManagerUIView : BaseContentUIView() {
    override fun inflateContentLayout(baseContentLayout: ContentLayout?, inflater: LayoutInflater?) {
        inflate(R.layout.view_rv_layout_manager)
    }

    private lateinit var recyclerView: RRecyclerView
    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        recyclerView = v(R.id.recycler_view)
        val radio_group: RadioGroup = v(R.id.radio_group)
        radio_group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.circle -> recyclerView.layoutManager = CircleLayoutManager(mActivity)
                R.id.circle_scale -> recyclerView.layoutManager = CircleScaleLayoutManager(mActivity)
                R.id.scale -> recyclerView.layoutManager = ScaleLayoutManager(mActivity, (100 * density()).toInt())
                R.id.carousel -> recyclerView.layoutManager = CarouselLayoutManager(mActivity, (100 * density()).toInt())
                R.id.rotate -> recyclerView.layoutManager = RotateLayoutManager(mActivity, (100 * density()).toInt())
                R.id.gallery -> recyclerView.layoutManager = GalleryLayoutManager(mActivity, (10 * density()).toInt())
                R.id.loop -> recyclerView.layoutManager = RExLoopRecyclerView.LoopLayoutManager(mActivity).apply {
                    setItemInterval(recyclerView.measuredWidth.toFloat())
                }
            }
        }

        recyclerView.layoutManager = CircleLayoutManager(mActivity)
        recyclerView.adapter = DataAdapter()
    }


    inner class DataAdapter : RBaseAdapter<String>(mActivity) {

        override fun getItemCount(): Int {
            return 20
        }

        override fun getItemLayoutId(viewType: Int): Int {
            return R.layout.item_single_card_image
        }

        override fun onBindView(holder: RBaseViewHolder, position: Int, bean: String?) {
            UI.setViewWidth(holder.itemView, (200 * density()).toInt())
        }

    }

}
