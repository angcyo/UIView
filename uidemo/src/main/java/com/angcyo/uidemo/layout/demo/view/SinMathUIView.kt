package com.angcyo.uidemo.layout.demo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.angcyo.uiview.kotlin.density

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/12/07 17:03
 * 修改人员：Robi
 * 修改时间：2017/12/07 17:03
 * 修改备注：
 * Version: 1.0.0
 */
class SinMathUIView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    companion object {
        /**抛物线方程 y = ax² + bx + c */
        fun calculate(vararg points: Array<Float>): Array<Float> {
            val x1 = points[0][0]
            val y1 = points[0][1]
            val x2 = points[1][0]
            val y2 = points[1][1]
            val x3 = points[2][0]
            val y3 = points[2][1]

            val a = (y1 * (x2 - x3) + y2 * (x3 - x1) + y3 * (x1 - x2)) / (x1 * x1 * (x2 - x3) + x2 * x2 * (x3 - x1) + x3 * x3 * (x1 - x2))
            val b = (y1 - y2) / (x1 - x2) - a * (x1 + x2)
            val c = y1 - x1 * x1 * a - x1 * b

            //println("-a->$a b->$b c->$c")
            return arrayOf(a, b, c)
        }

        fun y(x: Float, calculate: Array<Float>): Float {
            return calculate[0] * x * x + calculate[1] * x + calculate[2]
        }
    }

    fun getInterpolation(cycles: Float, input: Float): Float {
        return Math.sin(2.0 * cycles.toDouble() * Math.PI * input.toDouble()).toFloat()
    }

    init {
        setOnClickListener {

        }
    }

    val paint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 4 * density
        }
    }

    val path: Path by lazy {
        Path()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
//        val startX = 100f * density
//        val startY = measuredHeight / 2f
//        path.moveTo(startX, startY)
//        for (i in 0..60) {
//            val f = i / 100f
//            val w = 100 * density * f
//            val x = startX + w
//            val y = startY - (Math.tan(Math.PI / 2f * f) * w).toFloat()
//            path.lineTo(x, y)
//            L.e("call: onSizeChanged -> i:$i  x:$x  y:$y")
//        }
        val g = calculate(arrayOf(0f, measuredHeight.toFloat()), arrayOf(measuredWidth.toFloat() / 2, 0f), arrayOf(measuredWidth.toFloat(), measuredHeight.toFloat()))

        path.moveTo(0f, measuredHeight.toFloat())
        for (x in 0..measuredWidth) {
            path.lineTo(x.toFloat(), g[0] * x * x + g[1] * x + g[2])
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        //canvas.translate(0f, (-measuredHeight).toFloat())
        //canvas.drawColor(Color.GRAY)
        canvas.drawRect(10f, 10f, 20f, 20f, paint)
        canvas.drawPath(path, paint)
        canvas.restore()
    }
}