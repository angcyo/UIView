package com.angcyo.uidemo.layout.demo.qq

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/03/07 10:32
 * 修改人员：Robi
 * 修改时间：2018/03/07 10:32
 * 修改备注：
 * Version: 1.0.0
 */
class SweepView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val angle: Float = (tag as String?)?.toFloat() ?: 180f

        val rectF = RectF()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeWidth = 5f
            color = Color.GREEN
            style = Paint.Style.STROKE
        }
        rectF.set(10f, 10f, (measuredWidth - 10).toFloat(), (measuredHeight - 10).toFloat())
//        paint.shader = LinearGradient(measuredWidth.toFloat(), (measuredHeight / 2).toFloat(), (measuredWidth / 2).toFloat(), measuredHeight.toFloat(),
//                intArrayOf(Color.BLUE, Color.RED /*Color.parseColor("#40000000")*/),
//                null, Shader.TileMode.CLAMP)

//        paint.shader = LinearGradient(0f, 0f, 0f, measuredHeight.toFloat(),
//                intArrayOf(Color.BLUE, Color.RED /*Color.parseColor("#40000000")*/),
//                null, Shader.TileMode.CLAMP)

        val s = Color.parseColor("#FDC42D")
        val e = Color.parseColor("#E96858")

        paint.shader = SweepGradient(measuredWidth / 2f, measuredHeight / 2f,
                intArrayOf(s, e, Color.TRANSPARENT),
                floatArrayOf(0f, angle / 360f, 1f))

        canvas.drawArc(rectF, 0f, angle, false, paint)
    }

}