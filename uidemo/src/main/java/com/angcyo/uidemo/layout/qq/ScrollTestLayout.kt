package com.angcyo.uidemo.layout.qq

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uiview.kotlin.abs
import com.angcyo.uiview.kotlin.density
import com.angcyo.uiview.kotlin.exactlyMeasure
import com.angcyo.uiview.kotlin.maxValue

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/28 18:30
 * 修改人员：Robi
 * 修改时间：2018/06/28 18:30
 * 修改备注：
 * Version: 1.0.0
 */
class ScrollTestLayout(context: Context, attributeSet: AttributeSet? = null) : ViewGroup(context, attributeSet) {

    private var isFirstLayout = true
    //间隙大小
    val spaceSize = 10 * density
    var minHeight = 0

    private var curHeight = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val count = 4
        minHeight = ((widthSize - spaceSize * (count + 1)) / count).toInt()

        for (i in 0 until childCount) {
            //measureChild(getChildAt(i), exactlyMeasure(minHeight), exactlyMeasure(minHeight))
            getChildAt(i).measure(exactlyMeasure(minHeight), exactlyMeasure(minHeight))
        }

        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (isFirstLayout && measuredWidth > 0 && measuredHeight > 0 && curHeight == 0) {

            for (i in 0 until childCount) {
                val child = getChildAt(i)
                val lp: LayoutParams = child.layoutParams as LayoutParams

                lp.endLeft = (spaceSize * (i + 1) + child.measuredWidth / 2 + child.measuredWidth * i).toInt()
                lp.endTop = (spaceSize + child.measuredHeight / 2).toInt()

                //可以在这里强制重写属性, 测试代码可以注释
                when (i) {
                    0 -> {
                        lp.startLeft = (40 * density + child.measuredWidth / 2).toInt()
                        lp.startTop = measuredHeight / 2

                        lp.maxScale = 1.2f
                    }
                    1 -> {
                        lp.startLeft = (measuredWidth / 2)
                        lp.startTop = (measuredHeight - 40 * density - child.measuredHeight / 2).toInt()

                        lp.tranTop = measuredHeight / 2
                        lp.maxScale = 1.2f
                    }
                    2 -> {
                        lp.startLeft = (measuredWidth - 40 * density - child.measuredWidth / 2).toInt()
                        lp.startTop = measuredHeight / 2
                        lp.maxScale = 1.2f
                    }
                    3 -> {
                        lp.startLeft = measuredWidth / 2
                        lp.startTop = (40 * density + child.measuredHeight / 2).toInt()
                        lp.maxScale = 1.4f
                    }
                }

                L.e("onLayout -> s:${lp.startLeft} ${lp.startTop}  e:${lp.endLeft} ${lp.endTop}")
            }
            isFirstLayout = false
        }
        refreshLayout()
    }

    fun refreshLayout(height: Int) {
        curHeight = height
        refreshLayout()
    }

    fun refreshLayout() {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val lp: LayoutParams = child.layoutParams as LayoutParams

            val ints = lp.calc(this, child)
            child.layout(ints[0] - child.measuredWidth / 2, ints[1] - child.measuredHeight / 2,
                    ints[0] + child.measuredWidth / 2, ints[1] + child.measuredHeight / 2)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): ViewGroup.LayoutParams {
        return LayoutParams(context, attrs)
    }

    class LayoutParams : ViewGroup.LayoutParams {
        /*在childs中的索引*/
        var indexOfChild = 0

        /*位置都是 中心点坐标*/

        /*最开始的位置*/
        var startLeft = 0
        var endLeft = 0

        /*最终的位置*/
        var startTop = 0
        var endTop = 0

        /*过渡的位置*/
        var tranLeft = 0
        var tranTop = 0

        var maxScale = 1f

        /**第一阶段的阈值*/
        var firstStepRatio = 0.3f

        constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {
            val typedArray = c.obtainStyledAttributes(attrs, R.styleable.ScrollTestLayout_Layout)

            startLeft = typedArray.getDimensionPixelOffset(R.styleable.ScrollTestLayout_Layout_r_layout_start_left, startLeft)
            startTop = typedArray.getDimensionPixelOffset(R.styleable.ScrollTestLayout_Layout_r_layout_start_top, startTop)

            tranTop = typedArray.getDimensionPixelOffset(R.styleable.ScrollTestLayout_Layout_r_layout_tran_top, tranTop)

            maxScale = typedArray.getFloat(R.styleable.ScrollTestLayout_Layout_r_layout_max_scale, maxScale)
            firstStepRatio = typedArray.getFloat(R.styleable.ScrollTestLayout_Layout_r_layout_first_step_ratio, firstStepRatio)

            typedArray.recycle()
        }

        fun calc(layout: ScrollTestLayout, childView: View): IntArray {
            var f = (layout.curHeight.abs() * 1f / (layout.measuredHeight - layout.minHeight)).maxValue(1f)

            var left = startLeft
            var top = startTop

            if (f < firstStepRatio) {
                val scale = 1f + (maxScale - 1f) * ((0.3f - f) / 0.3f)
                childView.scaleX = scale
                childView.scaleY = scale

                f /= (firstStepRatio)

                if (tranTop > 0) {
                    top = (startTop + (tranTop - startTop) * f).toInt()
                }
                if (tranLeft > 0) {
                    left = (startLeft + (tranLeft - startLeft) * f).toInt()
                }

                return intArrayOf(left, top)
            }

            val sL = if (tranLeft > 0) tranLeft else startLeft
            val sT = if (tranTop > 0) tranTop else startTop


            f = (f - firstStepRatio) / (1 - firstStepRatio)

            left = (sL + (endLeft - sL) * f).toInt()
            top = (sT + (endTop - sT) * f).toInt()

            L.e("call: calc -> s:$sL $sT  e:$endLeft $endTop  :$left $top  $f")
            return intArrayOf(left, top)
        }
    }
}