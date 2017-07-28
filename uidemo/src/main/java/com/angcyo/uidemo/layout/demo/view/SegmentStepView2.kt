package com.angcyo.uidemo.layout.demo.view

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.angcyo.uiview.kotlin.density
import com.angcyo.uiview.kotlin.scaledDensity

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/06/06 14:44
 * 修改人员：Robi
 * 修改时间：2017/06/06 14:44
 * 修改备注：
 * Version: 1.0.0
 */
class SegmentStepView2(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    init {

    }

    /**各个级别对应的文本, size决定了多少个段*/
    var steps = listOf<String>()
        get() {
            if (isInEditMode) {
                return listOf("较差", "中等", "良好", "优秀", "极好")
            }
            return field
        }
        set(value) {
            field = value
            postInvalidate()
        }

    var stepColors = listOf<Int>()
        get() {
            if (isInEditMode) {
                return listOf(Color.BLUE, Color.CYAN, Color.RED, Color.GREEN, Color.YELLOW)
            }
            return field
        }
        set(value) {
            field = value
            postInvalidate()
        }
    /**文本的颜色, 如果为null,则使用stepColors对应的颜色*/
    var textColor: Int? = Color.GRAY

    /**级别的宽度, 请自行确保宽度可以容纳文本*/
    var stepWidth: Float = 60 * density
    var stepHeight: Float = 6 * density

    /**文本和分段之间的间隙*/
    var textSpace: Float = 4 * density

    /**测量文本的高度*/
    val textHeight: Float
        get() {
            if (steps.isEmpty()) {
                return 0f
            }
            return paint.descent() - paint.ascent()
        }

    val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        get() {
            field.textSize = textSize
            return field
        }

    var textSize: Float = 12 * scaledDensity

    /**当前平分进度*/
    var curProgress = 0
        get() {
            if (isInEditMode) {
                return 30
            }
            return field
        }
        set(value) {
            if (value > 100 || value < 0) {
                throw IllegalArgumentException("progress must 0 .. 100")
            }
            field = value
            postInvalidate()
        }

    /**三角形的边长*/
    var triangleSize = 4 * density

    /**三角形*/
    val mTrianglePath = Path()
        get() {
            field.reset()
            val progress = curProgress / 100f
            field.moveTo(measuredWidth * progress, textHeight + textSpace)
            field.lineTo(measuredWidth * progress - triangleSize / 2, textHeight + textSpace - triangleSize)
            field.lineTo(measuredWidth * progress + triangleSize / 2, textHeight + textSpace - triangleSize)
            field.close()
            return field
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var newWidthSpec = widthMeasureSpec
        var newHeightSpec = heightMeasureSpec

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = (steps.size * stepWidth).toInt()
            newWidthSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY)
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = ((textHeight + textSpace) * 2 + stepHeight).toInt()
            newHeightSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY)
        }

        super.onMeasure(newWidthSpec, newHeightSpec)
    }


    /**圆角的大小*/
    var radiusSize = 4 * density

    /**剪切圆角的path*/
    val drawClipPath = Path()
        get() {
            val radius = floatArrayOf(radiusSize, radiusSize, radiusSize, radiusSize, radiusSize, radiusSize, radiusSize, radiusSize)
            field.addRoundRect(RectF(0f, textHeight + textSpace, measuredWidth.toFloat(), textHeight + textSpace + stepHeight), radius, Path.Direction.CW)
            return field
        }

    override fun onDraw(canvas: Canvas) {

        //绘制分段颜色
        steps.mapIndexed { index, text ->
            paint.color = stepColors[index]

            val stepLeft = index * stepWidth
            canvas.save()
            canvas.clipPath(drawClipPath)
            canvas.drawRect(stepLeft, textHeight + textSpace, (index + 1) * stepWidth, textHeight + textSpace + stepHeight, paint)
            canvas.restore()

            //绘制文本
            val textWidth = paint.measureText(text)
            if (textColor != null) {
                paint.color = textColor as Int
            }
            canvas.drawText(text, stepLeft + stepWidth / 2 - textWidth / 2, textHeight - paint.descent(), paint)
        }

        //绘制底部分段刻度
        (0..100 step 100 / steps.size).mapIndexed { index, i ->
            val text = i.toString()
            val textWidth = paint.measureText(text)
            val offset = when (i) {
                0 -> 0f
                100 -> textWidth
                else -> textWidth / 2
            }
            if (textColor != null) {
                paint.color = textColor as Int
            }
            canvas.drawText(text, stepWidth * index - offset, (textHeight + textSpace) * 2 + stepHeight - paint.descent(), paint)

            if (curProgress >= i) {
                paint.color = stepColors[Math.min(index, stepColors.size - 1)]
                paint.style = Paint.Style.FILL_AND_STROKE
                canvas.drawPath(mTrianglePath, paint)
            }
        }
    }
}