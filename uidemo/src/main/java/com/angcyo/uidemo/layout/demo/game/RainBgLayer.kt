package com.angcyo.uidemo.layout.demo.game

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.angcyo.uiview.game.layer.BaseLayer

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
class RainBgLayer(val bgDrawable: Drawable) : BaseLayer() {

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bgDrawable.bounds = layerRect
    }

    override fun draw(canvas: Canvas, gameStartTime: Long, lastRenderTime: Long, nowRenderTime: Long) {
        super.draw(canvas, gameStartTime, lastRenderTime, nowRenderTime)
        bgDrawable.draw(canvas)
    }
}