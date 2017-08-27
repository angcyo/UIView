package com.angcyo.uidemo.layout.demo.view

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.View
import com.angcyo.uidemo.layout.demo.ex.show

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/08/26 16:13
 * 修改人员：Robi
 * 修改时间：2017/08/26 16:13
 * 修改备注：
 * Version: 1.0.0
 */
class AutoHideTextView(context: Context, attributeSet: AttributeSet? = null) : AppCompatTextView(context, attributeSet) {
    init {
        setOnClickListener {
            show()
            visibility = View.GONE
        }
    }
}
