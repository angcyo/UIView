package com.angcyo.uidemo.layout.demo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader.TileMode
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import com.angcyo.uiview.kotlin.debugPaint
import com.angcyo.uiview.kotlin.textHeight


/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/11/10 09:29
 * 修改人员：Robi
 * 修改时间：2017/11/10 09:29
 * 修改备注：
 * Version: 1.0.0
 */
class CanvasEditTextView(context: Context, attributeSet: AttributeSet? = null) : AppCompatEditText(context, attributeSet) {

    override fun onDraw(canvas: Canvas) {
        paint.setShader(LinearGradient(0f, 0f,
                0f, debugPaint.textHeight(),
                Color.RED, Color.WHITE,
                TileMode.CLAMP))
        super.onDraw(canvas)
    }

}