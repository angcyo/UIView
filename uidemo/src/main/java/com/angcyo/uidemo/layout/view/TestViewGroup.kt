package com.angcyo.uidemo.layout.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/26 14:12
 * 修改人员：Robi
 * 修改时间：2018/06/26 14:12
 * 修改备注：
 * Version: 1.0.0
 */
class TestViewGroup(context: Context, attributeSet: AttributeSet? = null) : FrameLayout(context, attributeSet) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        //super.onLayout(changed, left, top, right, bottom)
        for (i in 0 until childCount) {
            val v = measuredWidth / childCount

            getChildAt(i).layout(v * i, 0, v * (i + 1), measuredHeight)
        }

        getChildAt(0)?.let {
            it.layout(measuredWidth - 200, 0, measuredWidth, measuredHeight)
        }
    }
}