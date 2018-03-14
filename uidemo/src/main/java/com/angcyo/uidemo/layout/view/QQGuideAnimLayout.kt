package com.angcyo.uidemo.layout.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/03/14 11:32
 * 修改人员：Robi
 * 修改时间：2018/03/14 11:32
 * 修改备注：
 * Version: 1.0.0
 */
class QQGuideAnimLayout(context: Context, attributeSet: AttributeSet? = null) : FrameLayout(context, attributeSet) {

    /**需要显示哪一步*/
    var currentIndex = 0

    companion object {
        val ANIM_DURATION = 400L
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        //super.onLayout(changed, left, top, right, bottom)
        layoutChildren(left, top, right, bottom, true)
    }

    val logoView: View?
        get() {
            return findViewWithTag("logo")
        }


    private fun layoutChildren(left: Int, top: Int, right: Int, bottom: Int, layoutLogo: Boolean = false) {
        val count = childCount

        val parentLeft = paddingLeft
        val parentRight = paddingRight

        val parentTop = paddingTop
        val parentBottom = bottom - paddingBottom

        for (i in 0 until count) {
            val child = getChildAt(i)
            if (i == currentIndex || i == currentIndex - 1) {
                val lp = child.layoutParams as LayoutParams

                val width = child.measuredWidth
                val height = child.measuredHeight

                val childLeft: Int
                val childTop: Int

                var gravity = lp.gravity
                if (gravity == -1) {
                    gravity = Gravity.BOTTOM
                }

                val layoutDirection = layoutDirection
                val absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection)
                val verticalGravity = gravity and Gravity.VERTICAL_GRAVITY_MASK

                when (absoluteGravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
                    Gravity.CENTER_HORIZONTAL -> childLeft = parentLeft + (parentRight - parentLeft - width) / 2 +
                            lp.leftMargin - lp.rightMargin
                    Gravity.RIGHT -> {
                        childLeft = parentLeft + lp.leftMargin
                    }
                    Gravity.LEFT -> childLeft = parentLeft + lp.leftMargin
                    else -> childLeft = parentLeft + lp.leftMargin
                }

                when (verticalGravity) {
                    Gravity.TOP -> childTop = parentTop + lp.topMargin
                    Gravity.CENTER_VERTICAL -> childTop = parentTop + (parentBottom - parentTop - height) / 2 +
                            lp.topMargin - lp.bottomMargin
                    Gravity.BOTTOM -> childTop = parentBottom - height - lp.bottomMargin
                    else -> childTop = parentTop + lp.topMargin
                }

                child.layout(childLeft, childTop, childLeft + width, childTop + height)
            } else if (child == logoView) {
                if (layoutLogo) {
                    val view = getChildAt(currentIndex)
                    child.layout(0, view.top - child.measuredWidth, 0 + child.measuredWidth, view.top)
                }
            } else {
                child.layout(0, 0, 0, 0)
            }
        }
    }

    private fun refreshLayout() {
        layoutChildren(0, 0, measuredWidth, measuredHeight)
    }

    private fun translationY(view: View, value: Float) {
        view.animate()
                .translationY(value)
                .setInterpolator(LinearInterpolator())
                .setDuration(ANIM_DURATION)
                .start()
    }

    private fun logo(targetYValue: Float) {
        logoView?.let {
            it.animate()
                    .translationY(measuredHeight - targetYValue - it.bottom)
                    .setInterpolator(LinearInterpolator())
                    .setDuration(ANIM_DURATION + ANIM_DURATION / 2)
                    .start()
        }
    }

    private fun alpha(view: View, value: Float) {
        view.animate()
                .setStartDelay(ANIM_DURATION)
                .alpha(value)
                .setInterpolator(LinearInterpolator())
                .setDuration(ANIM_DURATION).withEndAction {
                    refreshLayout()
                }.start()
    }

    fun animToNext() {
        val view = getChildAt(currentIndex)
        val nextView = getChildAt(currentIndex + 1)
        if (nextView == null || nextView == logoView) {

        } else {
            currentIndex++

            nextView.alpha = 0f
            refreshLayout()

            translationY(view, (-nextView.measuredHeight).toFloat())
            alpha(nextView, 1f)
            logo(nextView.measuredHeight.toFloat())
        }
    }

    fun animToPrev() {
        if (currentIndex <= 0) {
            return
        }

        val view = getChildAt(currentIndex)
        val prevView = getChildAt(currentIndex - 1)
        if (prevView == null) {

        } else {
            currentIndex--

            alpha(view, 0f)
            translationY(prevView, 0f)
            logo(prevView.measuredHeight.toFloat())
        }
    }
}