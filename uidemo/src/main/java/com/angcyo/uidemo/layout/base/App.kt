package com.angcyo.rwxtools.base

import com.angcyo.uiview.RApplication
import com.angcyo.uiview.Root

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/01/24 11:58
 * 修改人员：Robi
 * 修改时间：2018/01/24 11:58
 * 修改备注：
 * Version: 1.0.0
 */
class App : RApplication() {
    override fun onInit() {
        super.onInit()
        Root.APP_FOLDER = "RWxTools"
    }
}