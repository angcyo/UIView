package com.angcyo.uidemo.layout.demo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout
import com.angcyo.library.utils.L
import com.angcyo.uiview.kotlin.*
import com.angcyo.uiview.utils.ScreenUtil

/**
 * Created by angcyo on 2018-02-28.
 */
class QQTestLayout(context: Context, attributeSet: AttributeSet? = null) : FrameLayout(context, attributeSet) {
    init {
        setWillNotDraw(false)
        debugPaint.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        getGlobalVisibleRect().apply {
            val fl = 1 - (ScreenUtil.screenHeight - top) * 1f / ScreenUtil.screenHeight //(1 - 0)
            L.e("call: onDraw ->$fl $this ${ScreenUtil.screenHeight}")

            val size = viewSize
            val w2 = viewDrawWith / 2
            val h2 = viewDrawHeight / 2

            val cy = measuredHeight - size / 2

            canvas.drawCircle(w2 * fl + 20 * density,
                    cy * fl,
                    size / 2 * fl, debugPaint)
        }
    }

}
