package com.angcyo.uidemo.kotlin.cls

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/07/16 09:56
 * 修改人员：Robi
 * 修改时间：2018/07/16 09:56
 * 修改备注：
 * Version: 1.0.0
 */
class TestView : View {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}