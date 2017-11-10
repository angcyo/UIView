package com.angcyo.uidemo.layout.demo.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.angcyo.uiview.kotlin.getDrawCenterCy
import com.angcyo.uiview.kotlin.getDrawCenterR

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
class CanvasTestView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.YELLOW
        }
    }
    private val clipBoundsRect = Rect()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val drawCenterR = getDrawCenterR()
        canvas.getClipBounds(clipBoundsRect)
        clipBoundsRect.inset(-drawCenterR.toInt() / 2, -drawCenterR.toInt() / 2)

        val cy = getDrawCenterCy()

        val cxOffset: Float = (measuredWidth / 7).toFloat()

        //无法突破
        canvas.save()
        canvas.clipRect(clipBoundsRect, Region.Op.REPLACE)
        paint.color = Color.RED
        canvas.drawCircle(cy, cy, drawCenterR + drawCenterR / 2, paint)
        canvas.restore()

        //看不见了
        canvas.save()
        canvas.clipRect(clipBoundsRect, Region.Op.DIFFERENCE)
        paint.color = Color.BLUE
        canvas.drawCircle(cxOffset * 2, cy, drawCenterR + drawCenterR / 2, paint)
        canvas.restore()

        //可以突破,需要 android:clipChildren="false" 的支持, 父ViewGroup和父父ViewGroup都设置此属性
        canvas.save()
        canvas.clipRect(clipBoundsRect, Region.Op.INTERSECT)
        paint.color = Color.GREEN
        canvas.drawCircle(cxOffset * 3, cy, drawCenterR + drawCenterR / 2, paint)
        canvas.restore()

        //可以突破, 动态设置的话, 也需要 android:clipChildren="false" 的支持
        canvas.save()
        canvas.clipRect(clipBoundsRect, Region.Op.REPLACE)
        paint.color = Color.MAGENTA
        canvas.drawCircle(cxOffset * 4, cy, drawCenterR + drawCenterR / 2, paint)
        canvas.restore()
        canvas.save()

        //看不见
        canvas.clipRect(clipBoundsRect, Region.Op.REVERSE_DIFFERENCE)
        paint.color = Color.CYAN
        canvas.drawCircle(cxOffset * 5, cy, drawCenterR + drawCenterR / 2, paint)
        canvas.restore()
        canvas.save()

        //看不见
        canvas.clipRect(clipBoundsRect, Region.Op.XOR)
        paint.color = Color.BLACK
        canvas.drawCircle(cxOffset * 6, cy, drawCenterR + drawCenterR / 2, paint)
        canvas.restore()

        //标准的绘制
        paint.color = Color.YELLOW
        canvas.drawCircle(cxOffset * 7 - measuredHeight, cy, drawCenterR, paint)
    }

}