package com.angcyo.uidemo.layout.demo

import android.view.LayoutInflater
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uiview.container.ContentLayout

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/09/14 15:15
 * 修改人员：Robi
 * 修改时间：2017/09/14 15:15
 * 修改备注：
 * Version: 1.0.0
 */
class TouchLayoutUIDemo : BaseContentUIView() {
    override fun inflateContentLayout(baseContentLayout: ContentLayout?, inflater: LayoutInflater?) {
        inflate(R.layout.view_touch_stick_layout)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        BehaviorStickDemoUIView.initLayout(mViewHolder.v(R.id.tab_layout), mViewHolder.v(R.id.view_pager))
    }
}