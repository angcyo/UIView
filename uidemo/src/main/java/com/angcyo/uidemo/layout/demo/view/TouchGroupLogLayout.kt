package com.angcyo.uidemo.layout.demo.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.angcyo.library.utils.L

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/09/14 14:52
 * 修改人员：Robi
 * 修改时间：2017/09/14 14:52
 * 修改备注：
 * Version: 1.0.0
 */
class TouchGroupLogLayout(context: Context, attributeSet: AttributeSet? = null) : FrameLayout(context, attributeSet) {
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        L.e("call:TouchGroupLogLayout dispatchTouchEvent -> ${ev?.actionMasked}")

//        if (ev?.actionMasked == MotionEvent.ACTION_MOVE) {
//            parent.requestDisallowInterceptTouchEvent(false)
//        }

        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        L.e("call:TouchGroupLogLayout onInterceptTouchEvent -> ${ev?.actionMasked}")
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        L.e("call:TouchGroupLogLayout onTouchEvent -> ${event?.actionMasked}")
        return super.onTouchEvent(event)
    }
}