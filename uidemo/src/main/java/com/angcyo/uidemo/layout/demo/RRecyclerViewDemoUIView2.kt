package com.angcyo.uidemo.layout.demo

import android.content.Context
import android.view.View
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseRecyclerUIView
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RBaseAdapter
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.widget.CircleImageView
import com.angcyo.uiview.widget.RImageView

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
class RRecyclerViewDemoUIView2 : BaseRecyclerUIView<String>() {

    override fun getDefaultLayoutState(): LayoutState {
        return LayoutState.CONTENT
    }

    companion object {
        fun loadData(): List<String> {
            val datas = (0..29).map { it.toString() }
            return datas
        }

        fun createSingleAdapter(context: Context): RBaseAdapter<String> {
            return object : RBaseAdapter<String>(context, loadData()) {
                override fun getItemLayoutId(viewType: Int): Int {
                    return R.layout.item_image_text_view
//                    return R.layout.item_image_view
                }

                override fun onBindView(holder: RBaseViewHolder, position: Int, bean: String?) {
                    holder.tv(R.id.text_view).text = "测试位置提示 $position"

                    val imageView: RImageView = holder.v(R.id.image_view)
                    if (position % 2 == 0) {
                        imageView.showType = CircleImageView.ROUND_RECT
                    } else {
                        imageView.showType = CircleImageView.CIRCLE
                    }
                    holder.click(imageView) {
                        if (imageView.showType == CircleImageView.ROUND_RECT) {
                            imageView.showType = CircleImageView.CIRCLE
                        } else {
                            imageView.showType = CircleImageView.ROUND_RECT
                        }
                    }
                }
            }
        }
    }

    override fun createAdapter(): RExBaseAdapter<String, String, String> {
        return object : RExBaseAdapter<String, String, String>(mActivity, loadData()) {

            override fun getItemLayoutId(viewType: Int): Int {
                return R.layout.item_recycler_view
            }

//            override fun getDataItemType(posInData: Int): Int {
//                return posInData
//            }

            override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: String?) {
                super.onBindDataView(holder, posInData, dataBean)
                holder.tv(R.id.text_view).text = "测试位置提示 $posInData"
                val adapter = holder.reV(R.id.recycler_view).adapter
                if (adapter == null) {
                    holder.reV(R.id.recycler_view).adapter = createSingleAdapter(mContext)
                }
            }

            override fun onChildViewDetachedFromWindow(view: View?, adapterPosition: Int, layoutPosition: Int) {
                super.onChildViewDetachedFromWindow(view, adapterPosition, layoutPosition)
            }
        }
    }
}
