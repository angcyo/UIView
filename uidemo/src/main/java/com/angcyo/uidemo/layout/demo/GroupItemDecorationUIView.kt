package com.angcyo.uidemo.layout.demo

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RExGroupItemDecoration
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RBaseAdapter

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/06/07 14:31
 * 修改人员：Robi
 * 修改时间：2017/06/07 14:31
 * 修改备注：
 * Version: 1.0.0
 */
class GroupItemDecorationUIView : BaseContentUIView() {
    override fun inflateContentLayout(baseContentLayout: FrameLayout?, inflater: LayoutInflater?) {
        inflate(R.layout.view_group_item_decoration_layout)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        val recyclerView: RRecyclerView = mViewHolder.v(R.id.recycler_view)
        val recyclerView2: RRecyclerView = mViewHolder.v(R.id.recycler_view2)

        val adapter = object : RBaseAdapter<String>(mActivity) {
            override fun getItemLayoutId(viewType: Int): Int {
                return R.layout.item_single_image
            }

            override fun onBindView(holder: RBaseViewHolder, position: Int, bean: String) {
                val padd: Int = (density() * 4).toInt()

                holder.itemView.setPadding(padd, padd, padd, padd)

                holder.tv(R.id.text_view).text = bean
            }
        }


        recyclerView.adapter = adapter
        recyclerView2.adapter = adapter

        adapter.resetData(MutableList(30) {
            "___" + it
        })

        recyclerView.addItemDecoration(createItemDecoration())
        recyclerView2.addItemDecoration(createItemDecoration())
    }

    fun createItemDecoration(): RecyclerView.ItemDecoration {
        return object : RExGroupItemDecoration(object : GroupCallBack() {

            var paint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

            init {
                paint.textSize = 12 * scaledDensity()
                paint.color = Color.RED
            }

            override fun getGroupTextSize(position: Int, layoutOrientation: Int): Float {
                if (layoutOrientation == LinearLayoutManager.HORIZONTAL) {
                    return paint.measureText(getGroupText(position))
                } else {
                    return paint.descent() - paint.ascent()
                }
            }

            override fun getGroupText(position: Int): String {
                return when {
                    position < 3 -> {
                        ""
                    }
                    position < 6 -> {
                        "测试1 >"
                    }
                    position < 10 -> {
                        "测试测试 >"
                    }
                    position < 15 -> {
                        "测试15555 >"
                    }
                    else -> {
                        "剩余 >"
                    }
                }
            }

            override fun onGetItemOffsets(outRect: Rect, groupInfo: GroupInfo) {
                if (groupInfo.adapterPosition in groupInfo.groupStartPosition..groupInfo.groupEndPosition) {
                    outRect.top = 100/* (paint.descent() - paint.ascent()).toInt()*/
                }
            }

            override fun onGroupDraw(canvas: Canvas, view: View, groupInfo: GroupInfo) {
                paint.color = Color.BLUE

                if (groupInfo.isHorizontal) {
                    canvas.drawText(getGroupText(groupInfo.adapterPosition),
                            view.left.toFloat(), view.top - paint.descent(), paint)
                } else {
                    canvas.drawText(getGroupText(groupInfo.adapterPosition),
                            0F, view.top - paint.descent(), paint)
                }
            }

            override fun onGroupOverDraw(canvas: Canvas, view: View, groupInfo: GroupInfo) {
                paint.color = Color.RED

                if (groupInfo.isHorizontal) {
                    canvas.drawText(getGroupText(groupInfo.adapterPosition),
                            groupInfo.groupStartOffset.toFloat(), view.top - paint.descent(), paint)
                } else {
//                    canvas.drawText(getGroupText(groupInfo.adapterPosition),
//                            view.left.toFloat(), groupInfo.groupStartOffset.toFloat() - paint.ascent(), paint)
                    canvas.drawText(getGroupText(groupInfo.adapterPosition),
                            view.left.toFloat(), groupInfo.groupStartOffset.toFloat() - paint.descent(), paint)

                    L.e("top:${groupInfo.groupStartOffset.toFloat() - paint.descent()} position:${groupInfo.adapterPosition}")
                }
            }
        }) {

        }
    }
}