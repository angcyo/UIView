package com.angcyo.uidemo.layout.demo.qq

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.angcyo.library.utils.L
import com.angcyo.uiview.kotlin.density

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/06 11:24
 * 修改人员：Robi
 * 修改时间：2018/06/06 11:24
 * 修改备注：
 * Version: 1.0.0
 */
class SlideView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {
    val paint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 20 * density
        }
    }

    var offsetX = 0
        set(value) {
            field = if (value > measuredWidth - 2 * radius) {
                (measuredWidth - 2 * radius).toInt()
            } else if (value < 0) {
                0
            } else {
                value
            }
        }

    val radius: Float
        get() {
            return measuredHeight.toFloat() / 2
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //背景
        paint.color = Color.BLUE
        canvas.drawRoundRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), 45 * density, 45 * density, paint)

        val cx = radius + offsetX

        //右边文字
        paint.color = Color.WHITE
        var text = "滑动滑动滑动滑动"
        var measureText = paint.measureText(text)
        var textX = (measuredWidth - cx - radius - measureText) / 2 + cx + radius
        if (textX > measuredWidth - measureText) {
            textX = measuredWidth - measureText
        }
        canvas.drawText(text, textX, measuredHeight / 2 + paint.descent(), paint)

        //左边圆圈
        paint.color = Color.RED
        canvas.drawCircle(cx, radius, radius, paint)
        paint.color = Color.WHITE

        //圆圈上的文字
        text = "滑动"
        measureText = paint.measureText(text)
        canvas.drawText(text, cx - measureText / 2, measuredHeight / 2 + paint.descent(), paint)
    }

    var downMotionEvent: MotionEvent? = null
    var firstMotionEvent: MotionEvent? = null
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)

                downMotionEvent = MotionEvent.obtain(event)
                //firstMotionEvent = MotionEvent.obtain(event)
            }
            MotionEvent.ACTION_MOVE -> {
                offsetX = (event.x - downMotionEvent!!.x).toInt()
                L.e("call: onTouchEvent -> $offsetX")
                //firstMotionEvent = MotionEvent.obtain(event)
                postInvalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                downMotionEvent = null
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return true
    }
}