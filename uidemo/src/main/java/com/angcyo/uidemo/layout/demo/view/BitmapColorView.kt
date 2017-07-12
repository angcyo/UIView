package com.angcyo.uidemo.layout.demo.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.view.View
import com.angcyo.uidemo.R
import com.angcyo.uiview.kotlin.density
import com.angcyo.uiview.resources.ResUtil


/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/07/12 14:54
 * 修改人员：Robi
 * 修改时间：2017/07/12 14:54
 * 修改备注：
 * Version: 1.0.0
 */
class BitmapColorView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {


    private var leftBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_arrow_left)
    private var rightBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_arrow_right)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLeftArrow(canvas)
        drawRightArrow(canvas)

        val drawable = BitmapDrawable(resources, leftBitmap)
        ResUtil.colorFilter(drawable, Color.RED) //tintDrawable(BitmapDrawable(resources, leftBitmap), Color.RED)
        drawable.setBounds(measuredWidth / 2, 0, measuredWidth / 2 + mArrowWidth, measuredHeight)
        drawable.draw(canvas)


        val drawable2 = ResUtil.tintDrawable(BitmapDrawable(resources, rightBitmap), Color.RED)
        drawable2.setBounds(measuredWidth / 2 + mArrowWidth, 0, measuredWidth / 2 + 2 * mArrowWidth, measuredHeight)
        drawable2.draw(canvas)

        leftBitmap = drawable.bitmap
        drawLeftArrow(canvas)
    }

    private val mArrowWidth: Int = (10 * density).toInt()//箭头图标宽度

    private fun drawLeftArrow(canvas: Canvas) {
        val src = Rect()
        src.left = 0
        src.top = 0
        src.right = leftBitmap.width
        src.bottom = leftBitmap.height

        val des = Rect()
        des.left = 0
        des.top = 0
        des.right = mArrowWidth
        des.bottom = measuredHeight

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
        canvas.drawBitmap(leftBitmap, src, des, paint)
    }

    private fun drawRightArrow(canvas: Canvas) {
        val src = Rect()
        src.left = 0
        src.top = 0
        src.right = rightBitmap.width
        src.bottom = rightBitmap.height

        val des = Rect()
        des.left = measuredWidth - mArrowWidth
        des.top = 0
        des.right = measuredWidth
        des.bottom = measuredHeight

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.isDither = true

        paint.colorFilter = LightingColorFilter(0xFF000000.toInt(), 0xFFFF0000.toInt())
        canvas.drawBitmap(rightBitmap, src, des, paint)
    }

    fun tintDrawable(drawable: Drawable, color: Int): Drawable {
        val wrappedDrawable = DrawableCompat.wrap(drawable).mutate()
        DrawableCompat.setTint(wrappedDrawable, color)
        return wrappedDrawable
    }
}