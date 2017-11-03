package com.angcyo.uidemo.activity

import android.content.Intent
import com.angcyo.uidemo.uiview.TestDemo
import com.angcyo.uiview.base.UILayoutActivity

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/11/03 10:59
 * 修改人员：Robi
 * 修改时间：2017/11/03 10:59
 * 修改备注：
 * Version: 1.0.0
 */
class MemoryTestActivity : UILayoutActivity() {
    override fun onLoadView(intent: Intent?) {
        startIView(TestDemo())
    }
}