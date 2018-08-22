package com.angcyo.uidemo.layout.qq.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by angcyo on 2018-08-21.
 * Email:angcyo@126.com
 */
class QQPBar : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var r = 80f

    var progress = 0

    var rectF = RectF()
    var cPath = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rectF.set(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
        cPath.reset()
        cPath.addRoundRect(rectF, r, r, Path.Direction.CW)

        canvas.save()
        canvas.clipPath(cPath)

        paint.shader = null
        paint.color = Color.GRAY
        canvas.drawRoundRect(rectF, r, r, paint)
        //canvas.drawColor(Color.RED)
        val width = measuredWidth.toFloat() * progress / 100f
        paint.shader = LinearGradient(0f, 0f, measuredWidth.toFloat(), 0f, Color.YELLOW, Color.RED, Shader.TileMode.CLAMP)

        var delay = 16L
        if (progress < 10) {
            delay = 700L
            canvas.drawRoundRect(-r, 0f, width, measuredHeight.toFloat(), r, r, paint)
        } else {
            canvas.drawRoundRect(0f, 0f, width, measuredHeight.toFloat(), r, r, paint)
        }

        postDelayed({
            progress++
            if (progress > 100) {
                progress = 0
            }
            invalidate()
        }, delay)
        canvas.restore()
    }
}