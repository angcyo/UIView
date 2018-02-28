package com.angcyo.uidemo.layout.demo

import android.support.v7.widget.RecyclerView
import android.view.View
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseRecyclerUIView
import com.angcyo.uidemo.layout.demo.view.QQTestLayout
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/17 09:46
 * 修改人员：Robi
 * 修改时间：2017/04/17 09:46
 * 修改备注：
 * Version: 1.0.0
 */
class RRecyclerViewDemoUIView3 : BaseRecyclerUIView<String>() {

    override fun getDefaultLayoutState(): LayoutState {
        return LayoutState.CONTENT
    }

    companion object {
        fun loadData(): List<String> {
            val datas = (0..29).map { it.toString() }
            return datas
        }
    }

    override fun getDefaultBackgroundColor(): Int {
        return getColor(R.color.base_chat_bg_color)
    }

    override fun initRecyclerView(recyclerView: RRecyclerView?, baseContentLayout: ContentLayout?) {
        super.initRecyclerView(recyclerView, baseContentLayout)
        recyclerView?.let {
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    L.e("call: onScrolled -> ")
                    it.localRefresh { viewHolder, _ ->
                        viewHolder.v<QQTestLayout>(R.id.qq_test_layout)?.let {
                            it.postInvalidateOnAnimation()
                        }
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    L.e("call: onScrollStateChanged -> ")
                }
            })
        }
    }

    override fun createAdapter(): RExBaseAdapter<String, String, String> {
        return object : RExBaseAdapter<String, String, String>(mActivity, loadData()) {

            override fun getItemLayoutId(viewType: Int): Int {
                return R.layout.item_qq_card_test_layout
            }

//            override fun getDataItemType(posInData: Int): Int {
//                return posInData
//            }

            override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: String?) {
                super.onBindDataView(holder, posInData, dataBean)
//                holder.tv(R.id.text_view).text = "测试位置提示 $posInData"
//                val adapter = holder.reV(R.id.recycler_view).adapter
//                if (adapter == null) {
//                    holder.reV(R.id.recycler_view).adapter = createSingleAdapter(mContext)
//                }
            }

            override fun onChildViewDetachedFromWindow(view: View?, adapterPosition: Int, layoutPosition: Int) {
                super.onChildViewDetachedFromWindow(view, adapterPosition, layoutPosition)
            }
        }
    }
}
