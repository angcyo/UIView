package com.angcyo.uidemo.layout.demo.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.angcyo.library.utils.L
import com.angcyo.uiview.kotlin.density
import com.angcyo.uiview.resources.RAnimListener
import com.angcyo.uiview.resources.ResUtil
import java.util.*

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：骰子
 * 创建人员：Robi
 * 创建时间：2017/08/10 15:20
 * 修改人员：Robi
 * 修改时间：2017/08/10 15:20
 * 修改备注：
 * Version: 1.0.0
 */
class DiceView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    /**所有骰子的资源id集合*/
    private val diceResArray = IntArray(15)

    /**缓存的骰子Drawable, 用到的时候才会加载进来*/
    private val drawablesMap = mutableMapOf<Int, Drawable>()

    /**执行动画时, 需要绘制的骰子*/
    private var drawDrawables = arrayListOf<Drawable>()

    /**最终的骰子*/
    private var targetDrawables = arrayListOf<Drawable>()


    /**每个骰子的大小*/
    var diceSize: Int = (60 * density).toInt()

    init {
        for (i in 0..14) {
            diceResArray[i] = ResUtil.getThemeIdentifier(context, "shaizi1_00${if (i >= 10) i else ("0" + i)}", "drawable")
        }
    }

    private fun getDiceDrawable(dice: Int): Drawable {
        L.e("call: getDiceDrawable -> $dice")
        val drawable = drawablesMap[dice]
        if (drawable == null) {
            L.e("call: getDiceDrawable 创建-> $dice")
            drawablesMap[dice] = ContextCompat.getDrawable(context, dice)
            return drawablesMap[dice]!!
        } else {
            L.e("call: getDiceDrawable 缓存-> $dice")
        }
        return drawable
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (widthMode != MeasureSpec.EXACTLY) {
            widthSize = diceSize * targetDrawables.size
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            heightSize = diceSize
        }
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (drawDrawables.isEmpty()) {
            targetDrawables.mapIndexed { index, drawable ->
                drawable.setBounds(index * measuredHeight, 0, (index + 1) * measuredHeight, measuredHeight)
                drawable.draw(canvas)
            }
        } else {
            drawDrawables.mapIndexed { index, drawable ->
                drawable.setBounds(index * measuredHeight, 0, (index + 1) * measuredHeight, measuredHeight)
                drawable.draw(canvas)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        L.e("call: onDetachedFromWindow -> ")
        if (tag != null && !TextUtils.isEmpty(uuid) &&
                TextUtils.isEmpty(tag.toString()) &&
                TextUtils.equals(uuid, tag.toString())) {

            if (valueAnim.isStarted) {
                valueAnim.cancel()
            }
        }
    }

    private var lastRollAnimValue: Int = 0
    private val random: Random by lazy {
        Random(System.nanoTime())
    }

    /**防止相同骰子, 本次随机和上次随机到相同的点数*/
    private val lastRandomMap = mutableMapOf<Int, Int>()

    private val valueAnim: ValueAnimator by lazy {
        val valueAnimator = ObjectAnimator.ofInt(0, 100)
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Int
            if (value == 0 || value - lastRollAnimValue >= 10) {
                lastRollAnimValue = value
                //setInputText(String.format(Locale.CHINA, "%.2f", minValue + (maxValue - minValue) * random.nextFloat()))
                targetDrawables.mapIndexed { index, _ ->
                    var nextInt = random.nextInt(14)
                    val get = lastRandomMap[index]
                    while (get == nextInt) {
                        nextInt = random.nextInt(14)
                    }
                    lastRandomMap[index] = nextInt

                    val drawable = getDiceDrawable(diceResArray[nextInt])
                    if (drawDrawables.size < targetDrawables.size) {
                        drawDrawables.add(drawable)
                    } else {
                        drawDrawables[index] = drawable
                    }
                }
                postInvalidateOnAnimation()
            }
        }
        valueAnimator.addListener(object : RAnimListener() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                lastRollAnimValue = 0
                drawDrawables.clear()
                lastRandomMap.clear()
                postInvalidateOnAnimation()
            }
        })
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = 1000
        valueAnimator
    }

    private var uuid: String? = null

    /**开始随机滚动*/
    fun startRoll(uuid: String, targetDices: IntArray) {
        this.uuid = uuid
        val oldSize = targetDrawables.size

        targetDrawables.clear()
        drawDrawables.clear()

        targetDices.mapIndexed { index, _ ->
            L.e("call: startRoll -> size:${targetDices.size} index:$index")
            targetDrawables.add(getDiceDrawable(diceResArray[ensureDice(targetDices[index])]))
        }

        if (oldSize != targetDrawables.size) {
            requestLayout()
        }

        if (!valueAnim.isStarted) {
            valueAnim.start()
        }
    }

    /**设置最终的骰子, 并重绘*/
    fun setTargetDice(targetDices: IntArray) {
        val oldSize = targetDrawables.size

        targetDrawables.clear()
        targetDices.mapIndexed { index, _ ->
            targetDrawables.add(getDiceDrawable(diceResArray[ensureDice(targetDices[index])]))
        }

        if (oldSize != targetDrawables.size) {
            requestLayout()
        } else {
            postInvalidate()
        }
    }

    private fun ensureDice(dice: Int): Int {
        return Math.max(Math.min(14, dice), 0)
    }

}