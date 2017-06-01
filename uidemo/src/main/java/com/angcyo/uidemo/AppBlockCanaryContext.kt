package com.hn.d.valley

import com.angcyo.uiview.receiver.NetworkStateReceiver
import com.github.moduth.blockcanary.BlockCanaryContext

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/06/01 16:12
 * 修改人员：Robi
 * 修改时间：2017/06/01 16:12
 * 修改备注：
 * Version: 1.0.0
 */
class AppBlockCanaryContext : BlockCanaryContext() {

    override fun provideNetworkType(): String {
        return NetworkStateReceiver.getNetType().toString()
    }

    override fun displayNotification(): Boolean {
        return true
    }
}