package com.angcyo.uidemo.layout.demo

import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.angcyo.uidemo.R

import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uiview.model.TitleBarPattern

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/06/05 13:58
 * 修改人员：Robi
 * 修改时间：2017/06/05 13:58
 * 修改备注：
 * Version: 1.0.0
 */
class QQNavigationUIView : BaseContentUIView() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setShowBackImageView(true)
    }

    override fun inflateContentLayout(baseContentLayout: RelativeLayout, inflater: LayoutInflater) {
        inflate(R.layout.view_qq_navigation)
    }
}
