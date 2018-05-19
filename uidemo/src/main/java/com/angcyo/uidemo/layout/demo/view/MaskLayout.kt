package com.angcyo.uidemo.layout.demo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Created by angcyo on 2018/05/19 10:26
 */
class MaskLayout : FrameLayout {

    var maskDrawable: Drawable? = null
    val maskPaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            isFilterBitmap = true
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        }
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun draw(canvas: Canvas) {
        if (maskDrawable == null) {
            super.draw(canvas)
        } else {
            canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
            maskDrawable!!.setBounds(0, 0, width, height)
            maskDrawable!!.draw(canvas)
            canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), maskPaint, Canvas.ALL_SAVE_FLAG)
            super.draw(canvas)
            canvas.restore()
            canvas.restore()
        }
    }
}