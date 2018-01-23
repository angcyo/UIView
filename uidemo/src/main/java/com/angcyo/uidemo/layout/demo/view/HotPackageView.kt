package com.angcyo.uidemo.layout.demo.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.angcyo.uidemo.R
import com.angcyo.uiview.kotlin.density
import com.angcyo.uiview.resources.RAnimListener

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：红包提示View
 * 创建人员：Robi
 * 创建时间：2017/07/26 11:06
 * 修改人员：Robi
 * 修改时间：2017/07/26 11:06
 * 修改备注：
 * Version: 1.0.0
 */
class HotPackageView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    private val hot1: Drawable by lazy {
        ContextCompat.getDrawable(context, R.drawable.hot_package_1)!!
    }
    private val hot2: Drawable by lazy {
        ContextCompat.getDrawable(context, R.drawable.hot_package_2)!!
    }

    private var showHot2 = true

    private var rotate = 0F

    private var hotAnim = false //hot 动画阶段

    private var animator: ValueAnimator? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (widthMode != MeasureSpec.EXACTLY) {
            widthSize = hot2.intrinsicWidth
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            heightSize = hot1.intrinsicHeight
        }

        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        hot1.setBounds(0, 0, w, h)
        hot2.setBounds(0, 0, w, h)
    }

    override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas)
        canvas.save()
        canvas.rotate(rotate, (measuredWidth / 2).toFloat(), measuredHeight - measuredHeight / 4 - 4 * density)
        hot1.draw(canvas)
        if (showHot2) {
            hot2.draw(canvas)
        }
        canvas.restore()
    }

    private var isAttached = false

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isAttached = true

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            startAnim()
//        }
        if (visibility == VISIBLE) {
            startAnim()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        isAttached = false
        stopAnim()
    }

    override fun onVisibilityChanged(changedView: View?, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (isAttached && visibility == VISIBLE) {
            startAnim()
        } else {
            stopAnim()
        }
    }

    private fun startAnim() {
        if (animator == null) {
            val anim = ObjectAnimator.ofFloat(0f, 10f, -10F, 10f, 0f)
            anim.duration = 400
            anim.interpolator = LinearInterpolator()
            anim.addUpdateListener { animation ->
                val value: Float = animation.animatedValue as Float
                if (hotAnim) {
                    rotate = 0f
                    showHot2 = false
                } else {
                    showHot2 = true
                    rotate = value
                }
                postInvalidateOnAnimation()
            }
            anim.addListener(object : RAnimListener() {
                override fun onAnimationRepeat(animation: Animator?) {
                    super.onAnimationRepeat(animation)
                    hotAnim = !hotAnim
                }
            })
            anim.repeatCount = ObjectAnimator.INFINITE
            anim.repeatMode = ObjectAnimator.RESTART
            animator = anim
        }

        animator?.let {
            if (!it.isStarted) {
                it.start()
            }
        }
    }

    private fun stopAnim() {
        animator?.let {
            it.cancel()
        }
    }
}