package com.angcyo.uidemo.layout.demo.view

import android.content.Context
import android.graphics.Canvas
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.VideoView
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/12/08 16:09
 * 修改人员：Robi
 * 修改时间：2017/12/08 16:09
 * 修改备注：
 * Version: 1.0.0
 */
class TestVideoView(context: Context, attributeSet: AttributeSet? = null) : VideoView(context, attributeSet) {
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        L.e("call: onDetachedFromWindow -> ")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        L.e("call: onAttachedToWindow -> ")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(ContextCompat.getColor(context, R.color.transparent_dark20))
    }
}