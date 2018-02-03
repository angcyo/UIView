package com.angcyo.uidemo.layout.demo.game

import com.angcyo.uidemo.R
import com.angcyo.uiview.game.layer.BaseTouchLayer
import com.angcyo.uiview.game.spirit.TouchSpiritBean

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：炸弹层
 * 创建人员：Robi
 * 创建时间：2018/01/17 11:16
 * 修改人员：Robi
 * 修改时间：2018/01/17 11:16
 * 修改备注：
 * Version: 1.0.0
 */
class BombLayer : BaseTouchLayer() {
    init {
        checkTouchEvent = true
        addSpiritNum = 3
        maxSpiritNum = 100
        spiritAddInterval = 1000L //控制添加的速度
    }

    override fun onAddNewSpirit(): TouchSpiritBean = TouchSpiritBean(arrayOf(
            getDrawable(R.drawable.zhadan001),
            getDrawable(R.drawable.zhadan002)
    )).apply {
        scaleX = 0.5f
        scaleY = 0.5f
        useBezier = false
        stepY = 10 + random.nextInt(5)
        initSpirit(this)
    }
}