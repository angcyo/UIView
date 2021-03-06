package com.angcyo.uidemo.layout.demo.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uiview.helper.BezierHelper
import com.angcyo.uiview.helper.BezierPointHelper
import com.angcyo.uiview.kotlin.c
import com.angcyo.uiview.kotlin.density
import com.angcyo.uiview.kotlin.rotateTo

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
        fun calculate(vararg points: Array<Float> /*a b c 三点的坐标*/): Array<Float> {
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
    lateinit var bezierHelper: BezierPointHelper
    val bezierPath: Path by lazy {
        Path()
    }
    val bezierPath2: Path by lazy {
        Path()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        L.i("call: onSizeChanged -> w:$w h:$h")
        path.reset()
        bezierPath.reset()
        bezierPath2.reset()
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
        val h = measuredHeight.toFloat()
        val w = measuredWidth.toFloat()

        val g = calculate(arrayOf(0f, h), arrayOf(w / 2, 0f), arrayOf(w, h))

        path.moveTo(0f, h)
        for (x in 0..measuredWidth) {
            path.lineTo(x.toFloat(), g[0] * x * x + g[1] * x + g[2])
        }

        val w2 = measuredWidth / 2f
        bezierHelper = BezierPointHelper(PointF(w2, 0f), PointF(w2, h), PointF(w2 / 2, 0f), PointF(w2 * 3 / 2, 0f))
        bezierPath.moveTo(w2, 0f)
        bezierPath2.moveTo(w2, 0f)
//        (0..100)
//                .map { bezierHelper.evaluate(it / 100f) }
//                .forEach { bezierPath.lineTo(it.x, it.y) }

        val bezier = BezierHelper(500f, 400f, 910f, 390f)

        val circleCount = 5 * 100
        for (i in 0..circleCount) {
            val fl = i % 101 / 100f
            val evaluate = bezierHelper.evaluate(fl)
            bezierPath.lineTo(evaluate.x, i / circleCount.toFloat() * h)
            //bezierPath2.lineTo(evaluate.x, evaluate.y)
            //L.e("call: onSizeChanged -> ${evaluate.y}")
        }
        for (i in 0..100) {
            val fl = i % 101 / 100f
            val evaluate = bezier.evaluate(fl)
            val x = evaluate
            val y = fl * (h + 100) - 100
            bezierPath2.lineTo(x, y)
            L.e("call: onSizeChanged ->$fl $x $y")
        }
    }

    private val drawable: Drawable by lazy {
        ContextCompat.getDrawable(getContext(), R.drawable.hongbao)!!
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //抛物线 绘制
        canvas.save()
        //canvas.translate(0f, (-measuredHeight).toFloat())
        //canvas.drawColor(Color.GRAY)
        canvas.drawRect(10f, 10f, 20f, 20f, paint)
        paint.color = Color.RED
        canvas.drawPath(path, paint)
        canvas.restore()

        //贝塞尔曲线 绘制
        canvas.save()
        paint.color = Color.BLUE
        canvas.drawPath(bezierPath, paint)
        canvas.restore()

        //贝塞尔曲线 绘制
        canvas.save()
        paint.color = Color.GREEN
        canvas.drawPath(bezierPath2, paint)
        canvas.restore()

        val point = Point(400, 400)

        //
//        drawable.let {
//            it.bounds = it.getBoundsWith(point, canvas.clipBounds)
//            it.onDraw(canvas)
//        }

        canvas.save()
        canvas.translate(point.x.toFloat(), point.y.toFloat())
        canvas.rotate(30f)
        canvas.scale(2f, 2f)
        drawable.let {
            it.setBounds(-it.intrinsicWidth / 2, -it.intrinsicHeight / 2, it.intrinsicWidth / 2, it.intrinsicHeight / 2)
            it.draw(canvas)
        }
//        canvas.translate(-point.x.toFloat(), -point.y.toFloat())
        canvas.restore()

        paint.strokeWidth = 4 * density
        canvas.save()
        val l = 600
        val t = 1000
        val w = 400
        val h = 700//(800 * Math.cos( Math.toRadians(30.0))).toInt()
        testRect1.set(l, t, l + w, t + h)
        testRect1.rotateTo(testRect2, rotateDegrees)
        paint.color = Color.YELLOW
        canvas.drawRect(testRect1, paint)
        paint.color = Color.RED
        canvas.drawCircle(testRect1.centerX().toFloat(), testRect1.centerY().toFloat(), (testRect1.c() / 2).toFloat(), paint)
        canvas.restore()

        canvas.save()
        canvas.translate(testRect1.centerX().toFloat(), testRect1.centerY().toFloat())
        canvas.rotate(rotateDegrees)
        paint.strokeWidth = 2 * density
        paint.color = Color.BLUE
        canvas.drawRect((-testRect1.width() / 2).toFloat(), (-testRect1.height() / 2).toFloat(),
                (testRect1.width() / 2).toFloat(), (testRect1.height() / 2).toFloat(), paint)
        canvas.restore()

        canvas.save()
        paint.color = Color.GREEN
        paint.strokeWidth = 1 * density
        canvas.drawRect(testRect2, paint)
        canvas.restore()

    }

    var rotateDegrees = 0f

    val testRect1 = Rect()
    val testRect2 = Rect()

}
