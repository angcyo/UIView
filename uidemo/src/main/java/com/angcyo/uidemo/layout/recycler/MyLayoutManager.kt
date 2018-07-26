package com.angcyo.uidemo.layout.recycler

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.angcyo.library.utils.L

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/07/26 10:10
 * 修改人员：Robi
 * 修改时间：2018/07/26 10:10
 * 修改备注：
 * Version: 1.0.0
 */
class MyLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        //super.onLayoutChildren(recycler, state)
        log("onLayoutChildren ${state.isPreLayout} ${state.itemCount}")

        var top = paddingTop
        var left = paddingLeft
        for (i in 0 until state.itemCount) {
            //拿到view
            val view = recycler.getViewForPosition(i)
            val lp: RecyclerView.LayoutParams = view.layoutParams as RecyclerView.LayoutParams
            addView(view)

            measureChildWithMargins(view, 0, 0)

            top += lp.topMargin
            view.layout(left + lp.leftMargin, top, width - lp.rightMargin - paddingRight, top + view.measuredHeight)

//            layoutDecoratedWithMargins(view, left, top,
//                    width - paddingRight,
//                    top + getDecoratedMeasuredHeight(view) + getTopDecorationHeight(view) + lp.topMargin)

            top = getDecoratedBottom(view) + lp.bottomMargin
        }
    }

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        log("onLayoutCompleted")
    }

    override fun canScrollVertically(): Boolean {
        log("canScrollVertically")
        return true
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        log("scrollVerticallyBy: $dy")
        offsetChildrenVertical(-dy)
        return dy
    }

    private fun log(l: String) {
        L.e("MyLayoutManager", l)
    }
}