package com.angcyo.uidemo.layout.demo.game

import com.angcyo.uidemo.R
import com.angcyo.uiview.game.layer.BaseTouchLayer
import com.angcyo.uiview.game.spirit.TouchSpiritBean

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

    init {
        checkTouchEvent = false //不需要touch事件
        maxSpiritNum = -1 //无限循环
        addSpiritNum = 1 //每次添加1个
        pauseDrawFrame = false //开始绘制
        spiritAddInterval = 300 //300毫秒添加一个
    }

    override fun onAddNewSpirit(): TouchSpiritBean = TouchSpiritBean(arrayOf(
            getDrawable(R.drawable.yinfu_dong_1))).apply {
        useBezier = false
        randomStep = false
        stepY = -4
        initSpirit(this)
    }

    override fun isSpiritRemoveFromWindow(spirit: TouchSpiritBean): Boolean {
        val bottom = spirit.getBottom()
        return bottom <= 0
    }

    override fun getSpiritStartX(spiritBean: TouchSpiritBean, sw: Int): Int {
        return layerRect.centerX()
    }

    override fun getSpiritStartY(spiritBean: TouchSpiritBean): Int {
        return layerRect.bottom
    }
}