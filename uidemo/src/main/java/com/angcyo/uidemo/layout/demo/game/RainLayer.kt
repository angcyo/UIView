package com.angcyo.uidemo.layout.demo.game

import android.graphics.Canvas
import android.graphics.PointF
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import com.angcyo.library.utils.L
import com.angcyo.uiview.game.GameRenderView
import com.angcyo.uiview.game.layer.BaseLayer
import com.angcyo.uiview.helper.BezierHelper
import com.angcyo.uiview.widget.RainBean
import com.angcyo.uiview.widget.helper.RainHelper

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/12/15 11:19
 * 修改人员：Robi
 * 修改时间：2017/12/15 11:19
 * 修改备注：
 * Version: 1.0.0
 */
@Deprecated("")
class RainLayer(val rainResId: Int) : BaseLayer() {
    private val rainList = mutableListOf<RainBean>()

    var onClickRainListener: OnClickRainListener? = null

    companion object {
        val TAG = "rainAnim"

        /**每次新增数量*/
        var addNum = 5

        /**每次新增 时间间隔*/
        var interval = 700L

        /**最大数量*/
        var maxNum = 100
    }

    /*已经添加了多少个*/
    private var rainAddNum = 0

    /**点中了多少个Rain*/
    var touchUpNum = 0

    /**使用贝塞尔曲线*/
    var useBezier = true

    var rainStepY = 2 //px

    /**每个Rain下降的Step是否随机*/
    var randomStep = true

    private var isRainEnd = false

    init {
        drawIntervalTime = interval
    }

    private fun checkListener(x: Int, y: Int): Boolean {
        var isIn = false
        for (i in rainList.size - 1 downTo 0) {
            val rainBean = rainList[i]
            //L.w("check", "${rainBean.getRect()} $x $y ${rainBean.isIn(x, y)}")
            if (rainBean.isIn(x, y)) {
                touchUpNum++
                onClickRainListener?.onClickRain(this, rainBean)
                isIn = true
                break
            }
        }
        return isIn
    }

    override fun onTouchEvent(event: MotionEvent, point: PointF): Boolean {
        if (isRainEnd) {
            return super.onTouchEvent(event, point)
        }
        if (!rainList?.isEmpty()) {
            return checkListener(point.x.toInt(), point.y.toInt())
        }
        return super.onTouchEvent(event, point)
    }

    override fun draw(canvas: Canvas, gameStartTime: Long /*最开始渲染的时间*/, lastRenderTime: Long, nowRenderTime: Long /*现在渲染的时候*/) {
        if (isRainEnd) {
            return
        }

        super.draw(canvas, gameStartTime, lastRenderTime, nowRenderTime)

        updateRainList()
        rainList?.let {
            for (bean in it) {
                bean.draw(canvas)
            }
        }
    }

    override fun onDraw(canvas: Canvas, gameStartTime: Long, lastRenderTime: Long, nowRenderTime: Long) {
        super.onDraw(canvas, gameStartTime, lastRenderTime, nowRenderTime)
        addNewRain()
    }

    lateinit var gameRenderView: GameRenderView
    override fun onRenderStart(gameRenderView: GameRenderView) {
        super.onRenderStart(gameRenderView)
        this.gameRenderView = gameRenderView
    }

    fun removeRain(bean: RainBean) {
        rainList.remove(bean)
    }

    private fun addNewRain() {
        if (rainAddNum >= maxNum) {
            //所有Rain 添加完毕
            if (rainList.isEmpty()) {
                //所有Rain, 移除了屏幕
                endRain()
            }
        } else if (rainAddNum + addNum > maxNum) {
            //达到最大
            addNewRainInner(maxNum - rainAddNum)
        } else {
            addNewRainInner(addNum)
        }
    }

    //添加Rain
    private fun addNewRainInner(num: Int) {
        //L.e(TAG, "call: addNewRainInner -> $num")
        for (i in 0 until num) {
            rainList.add(RainBean().apply {
                if (rainResId != -1) {
                    val randomStepY = rainStepY + random.nextInt(5)
                    if (randomStep) {
                        stepY = randomStepY
                    } else {
                        stepY = rainStepY
                    }

                    rainDrawable = ContextCompat.getDrawable(gameRenderView.context, rainResId)
                    val intrinsicWidth = rainDrawable!!.intrinsicWidth
                    val intrinsicHeight = rainDrawable!!.intrinsicHeight

                    val w2 = gameRenderView.measuredWidth / 2
                    val w4 = w2 / 2

                    val randomX = if (useBezier) randomX(gameRenderView, w4 - intrinsicWidth) else randomX(gameRenderView, intrinsicWidth)
                    val randomY = randomY(-intrinsicHeight)
                    setRect(randomX, randomY, intrinsicWidth, intrinsicHeight)

                    val x = (randomX + intrinsicWidth).toFloat()
                    val left = randomStepY % 2 == 0

                    val cp1: Float = if (left) (x + w4) else (x - w4)
                    val cp2: Float = if (left) (x - w4) else (x + w4)

                    bezierHelper = BezierHelper(x, x, cp1, cp2)
                }
            })
            rainAddNum++
        }
    }

    /*随机产生x轴*/
    private fun randomX(gameRenderView: GameRenderView, w: Int): Int {
        return (random.nextFloat() * (gameRenderView.measuredWidth - w)).toInt()
    }

    /*随机产生y轴*/
    private fun randomY(h: Int): Int {
        return (random.nextFloat() * h).toInt()
    }

    /**结束绘制*/
    fun endRain() {
        isRainEnd = true
        val showNum = rainList.size
        val addNum = rainAddNum
        val maxNum = RainHelper.maxNum
        L.e(RainHelper.TAG, "call: endRain -> showNum:$showNum addNum:$addNum maxNum:$maxNum touchUpNum:$touchUpNum")
        //listener?.onRainEnd(addNum, showNum, maxNum, touchUpNum)
    }

    /**重置参数*/
    fun reset() {
        rainList.clear()
        rainAddNum = 0
        touchUpNum = 0
        isRainEnd = false
    }

    private fun updateRainList() {
        val removeList = mutableListOf<RainBean>()
        for (bean in rainList) {
            bean.offset(0, (bean.stepY /*ScreenUtil.density*/).toInt())

            if (useBezier) {
                val maxY = gameRenderView.measuredHeight / 5 /*分成5份, 循环5次曲线*/
                if (maxY > 0) {
                    val fl = Math.abs(bean.getRect().top % maxY / maxY.toFloat())

                    val dx = (bean.bezierHelper!!.evaluate(fl) - bean.getRect().left).toInt()
                    bean.offset(dx, 0)
                }

                //L.e("call: updateRainList -> fl:$fl dx:$dx")
            }

            if (bean.getTop() > gameRenderView.measuredHeight) {
                //移动到屏幕外了,需要移除
                removeList.add(bean)
            }
        }
        rainList.removeAll(removeList)
    }
}

interface OnClickRainListener {
    fun onClickRain(rainLayout: RainLayer, bean: RainBean)
}