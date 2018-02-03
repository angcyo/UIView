package com.angcyo.uidemo.layout.demo.game

import android.graphics.Canvas
import com.angcyo.uidemo.R
import com.angcyo.uiview.game.layer.BaseTouchLayer
import com.angcyo.uiview.game.spirit.TouchSpiritBean
import com.angcyo.uiview.helper.BezierHelper
import com.angcyo.uiview.kotlin.maxValue

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：抖音音乐播放动画
 * 创建人员：Robi
 * 创建时间：2018/01/10 17:07
 * 修改人员：Robi
 * 修改时间：2018/01/10 17:07
 * 修改备注：
 * Version: 1.0.0
 */
class MusicNoteLayer : BaseTouchLayer() {


    val drawables = arrayOf(
            R.drawable.yinfu_dong_1,
            R.drawable.yinfu_dong_2,
            R.drawable.yinfu_dong_3,
            R.drawable.yinfu_dong_4)

    private var index = 0
        get() {
            if (field >= drawables.size) {
                field = 0
            }
            return field
        }

    init {
        checkTouchEvent = false //不需要touch事件
        maxSpiritNum = -1 //无限循环
        addSpiritNum = 1 //每次添加1个
        pauseDrawFrame = false //开始绘制
        spiritAddInterval = 1500 //300毫秒添加一个
    }

    override fun createBezierHelper(spirit: TouchSpiritBean, randomX: Int, randomY: Int, intrinsicWidth: Int, intrinsicHeight: Int): BezierHelper {
        val random = 30 + random.nextInt(30)

        return BezierHelper(randomX.toFloat(), randomX - random * density,
                randomX - 50 * density, randomX - 50 * density)
    }

    override fun onAddNewSpirit(): TouchSpiritBean = object : TouchSpiritBean(arrayOf(
            getDrawable(drawables[index++]))) {

        val degreesStep = if (random.nextBoolean()) {
            2
        } else {
            -2
        }

        init {
            useBezier = true
            randomStep = false
            stepY = -1
            scaleX = 0.1f
            scaleY = 0.1f
            bezierPart = 1f
            initSpirit(this)

            //frameDrawIntervalTime = 300
        }

//        override fun onUpdateSpiritList(): Boolean {
//            offset(0, (stepY /*ScreenUtil.density*/).toInt())
//            if (useBezier) {
//                val maxY = (gameRenderView.measuredHeight - startY) / bezierPart /*分成5份, 循环5次曲线*/
//                if (maxY > 0) {
//                    val fl = Math.abs((getSpiritDrawRect().top - startY) % (maxY + 1) / maxY.toFloat())
//
//                    val dx = (bezierHelper!!.evaluate(fl) - getSpiritDrawRect().left).toInt()
//                    if (dx < getSpiritDrawRect().width()) {
//                        //控制位移的幅度, 防止漂移现象
//                        offset(dx, 0)
//                    }
//                    //L.e("call: updateRainList -> fl:$fl dx:$dx ${bean.getRect()} $maxY")
//                }
//            }
//            return true
//        }

        override fun onFrameDrawInterval(canvas: Canvas, gameStartTime: Long, lastRenderTime: Long, nowRenderTime: Long) {
            super.onFrameDrawInterval(canvas, gameStartTime, lastRenderTime, nowRenderTime)
            scaleX += 0.1f
            scaleX = scaleX.maxValue(0.5f)

            scaleY += 0.1f
            scaleY = scaleY.maxValue(0.5f)

            rotateDegrees += degreesStep
        }

//        override fun getDrawDrawableBounds(drawable: Drawable): Rect {
//            return Rect(0, 0,
//                    drawable.intrinsicWidth * ScreenUtil.density().toInt(), drawable.intrinsicHeight * ScreenUtil.density().toInt())
//        }
    }

    override fun getSpiritStartX(spiritBean: TouchSpiritBean, sw: Int): Int {
        return layerRect.centerX()
    }

    override fun getSpiritStartY(spiritBean: TouchSpiritBean): Int {
        return layerRect.bottom
    }
}