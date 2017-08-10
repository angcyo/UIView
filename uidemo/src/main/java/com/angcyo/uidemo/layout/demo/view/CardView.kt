package com.angcyo.uidemo.layout.demo.view

import android.content.Context
import android.graphics.Canvas
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.angcyo.uiview.resources.ResUtil

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：纸牌
 * 创建人员：Robi
 * 创建时间：2017/08/10 15:21
 * 修改人员：Robi
 * 修改时间：2017/08/10 15:21
 * 修改备注：
 * Version: 1.0.0
 */
class CardView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    private val cards = IntArray(54)

    init {
        for (i in 0..53) {
            cards[i] = ResUtil.getThemeIdentifier(context, "pocker_0x${if (i >= 10) i else ("0" + i)}", "drawable")
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        ContextCompat.getDrawable(context, cards[0]).apply {
            setBounds(0, 0, measuredWidth, measuredHeight)
            draw(canvas)
        }
    }
}