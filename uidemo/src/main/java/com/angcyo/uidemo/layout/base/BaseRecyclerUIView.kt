package com.angcyo.uidemo.layout.base

import com.angcyo.uiview.base.UIRecyclerUIView

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/08/10 14:21
 * 修改人员：Robi
 * 修改时间：2017/08/10 14:21
 * 修改备注：
 * Version: 1.0.0
 */
abstract class BaseRecyclerUIView<T> : UIRecyclerUIView<String, T, String>() {
    override fun isUIHaveLoadMore(datas: MutableList<T>?): Boolean {
        return false
    }
}