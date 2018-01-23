package com.angcyo.uidemo.layout.demo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.angcyo.uiview.kotlin.density
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

    private val cardsResArray = IntArray(54)

    /**每张牌的大小*/
    var cardSize: Int = (60 * density).toInt()

    private var cardWidth: Int = 0
    private var cardHeight: Int = 0

    /**每张卡牌之间的偏移距离*/
    var cardOffset: Int = (15 * density).toInt()

    /**最终的纸牌*/
    private var targetDrawables = arrayListOf<Drawable>()

    init {
        for (i in 0..53) {
            cardsResArray[i] = ResUtil.getThemeIdentifier(context, "pocker_0x${if (i >= 10) i else ("0" + i)}", "drawable")
        }
    }

    companion object {
        /**缓存的骰子Drawable, 用到的时候才会加载进来*/
        private val drawablesMap = mutableMapOf<Int, Drawable>()

        /**释放缓存*/
        fun clear() {
            drawablesMap.clear()
        }

        fun getCardDrawable(context: Context, card: Int): Drawable {
            val drawable = drawablesMap[card]
            if (drawable == null) {
                drawablesMap[card] = ContextCompat.getDrawable(context, card)!!
                return drawablesMap[card]!!
            } else {
            }
            return drawable
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (widthMode != MeasureSpec.EXACTLY) {
            widthSize = cardSize + (targetDrawables.size - 1) * cardOffset
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            heightSize = cardSize
        }
        cardWidth = widthSize - (targetDrawables.size - 1) * cardOffset
        cardHeight = heightSize
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        targetDrawables.mapIndexed { index, drawable ->
            drawable.setBounds(index * cardOffset, 0, cardWidth + index * cardOffset, cardHeight)
            drawable.draw(canvas)
        }
    }

    /**设置显示的纸牌*/
    fun setTargetCards(targetCards: IntArray) {
        val oldSize = targetDrawables.size

        targetDrawables.clear()
        targetCards.mapIndexed { index, _ ->
            targetDrawables.add(getCardDrawable(context, cardsResArray[ensureCard(targetCards[index])]))
        }

        if (oldSize != targetDrawables.size) {
            requestLayout()
        } else {
            postInvalidate()
        }
    }

    private fun ensureCard(card: Int): Int {
        return Math.max(Math.min(53, card), 0)
    }

}