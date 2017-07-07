package com.angcyo.uidemo.layout.demo.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/07/07 10:57
 * 修改人员：Robi
 * 修改时间：2017/07/07 10:57
 * 修改备注：
 * Version: 1.0.0
 */
class DemoViewGroup(context: Context, attributeSet: AttributeSet? = null) : FrameLayout(context, attributeSet) {
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        getChildAt(0).layout(0, 0, measuredWidth, measuredHeight)
    }
}