package com.angcyo.uidemo.layout.demo.game

import com.angcyo.uidemo.R
import com.angcyo.uiview.game.layer.BaseTouchLayer
import com.angcyo.uiview.game.layer.TouchSpiritBean
import com.angcyo.uiview.utils.ScreenUtil

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：金币Layer
 * 创建人员：Robi
 * 创建时间：2017/12/16 14:52
 * 修改人员：Robi
 * 修改时间：2017/12/16 14:52
 * 修改备注：
 * Version: 1.0.0
 */
class GoldCoinLayer : BaseTouchLayer() {

    init {
        addSpiritNum = 5
        maxSpiritNum = Int.MAX_VALUE
        checkTouchEvent = false
        spiritAddInterval = 600L //控制金币添加的速度
        //drawIntervalTime = spiritAddInterval //控制检查金币是否需要添加的时间
    }

    override fun randomY(h: Int): Int {
        return -((random.nextFloat() * (ScreenUtil.screenHeight / 2)).toInt())
    }

    override fun onAddNewSpirit(): TouchSpiritBean = TouchSpiritBean(arrayOf(
            getDrawable(R.drawable.jinbi_00000),
            getDrawable(R.drawable.jinbi_00001),
            getDrawable(R.drawable.jinbi_00002),
            getDrawable(R.drawable.jinbi_00003),
            getDrawable(R.drawable.jinbi_00004),
            getDrawable(R.drawable.jinbi_00005))).apply {
        useBezier = true
        stepY = 10 + random.nextInt(30)
        initSpirit(this)
    }
}