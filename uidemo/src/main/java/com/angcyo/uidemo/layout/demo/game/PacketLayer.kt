package com.angcyo.uidemo.layout.demo.game

import com.angcyo.uidemo.R
import com.angcyo.uiview.game.layer.BaseTouchLayer
import com.angcyo.uiview.game.layer.TouchSpiritBean
import com.angcyo.uiview.utils.ScreenUtil

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：红包Layer
 * 创建人员：Robi
 * 创建时间：2017/12/16 19:27
 * 修改人员：Robi
 * 修改时间：2017/12/16 19:27
 * 修改备注：
 * Version: 1.0.0
 */
class PacketLayer : BaseTouchLayer() {

    var useBezier = true
    var randomStep = true

    init {
        addSpiritNum = 3
        maxSpiritNum = 10000
        checkTouchEvent = true
        spiritAddInterval = 1000L //控制红包添加的速度
    }

    override fun randomY(h: Int): Int {
        return -((random.nextFloat() * (ScreenUtil.screenHeight / 2)).toInt())
    }

//    override fun randomX(sw: Int, w: Int): Int {
//        return (random.nextFloat() * (sw - w / 2)).toInt()
//    }

    override fun getExcludeWidth(spiritBean: TouchSpiritBean, intrinsicWidth: Int): Int {
        val excludeWidth = super.getExcludeWidth(spiritBean, intrinsicWidth)
        return if (spiritBean.useBezier) excludeWidth / 2 else excludeWidth
    }

    override fun onAddNewSpirit(): TouchSpiritBean = TouchSpiritBean(arrayOf(getDrawable(R.drawable.hongbao))).apply {
        useBezier = this@PacketLayer.useBezier
        randomStep = this@PacketLayer.randomStep

        initSpirit(this)

        if (randomStep) {
            stepY = 10 + random.nextInt(30) //控制红包下降速度
        }
        rotateDegrees = when (random.nextInt(4)) {
            0 -> 45f
            1 -> -45f
            2 -> -75f
            3 -> -15f
            else -> 0f
        }

        //红包缩放
        scaleX = 0.2f + random.nextInt(6) / 10f
        scaleY = scaleX
    }
}