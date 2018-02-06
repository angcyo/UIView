package com.angcyo.uidemo.layout.demo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader.TileMode
import android.util.AttributeSet
import android.view.View
import com.angcyo.uiview.kotlin.debugPaint
import com.angcyo.uiview.kotlin.density
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
class CanvasView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        debugPaint.textSize = 30 * density
        debugPaint.style = Paint.Style.FILL_AND_STROKE
        //debugPaint.setColorFilter()
        debugPaint.setShader(LinearGradient(0f, 0f,
                0f, debugPaint.textHeight(),
                Color.RED, Color.WHITE,
                TileMode.CLAMP))
        canvas.drawText("孬孬孬孬孬孬孬孬", 10f, 40 * density, debugPaint)
    }

}