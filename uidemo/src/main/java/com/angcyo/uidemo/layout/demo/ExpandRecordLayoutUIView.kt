package com.angcyo.uidemo.layout.demo

import android.view.LayoutInflater
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.viewgroup.ExpandRecordLayout

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/06/09 11:31
 * 修改人员：Robi
 * 修改时间：2017/06/09 11:31
 * 修改备注：
 * Version: 1.0.0
 */
class ExpandRecordLayoutUIView : BaseContentUIView() {
    override fun inflateContentLayout(baseContentLayout: ContentLayout, inflater: LayoutInflater) {
        inflate(R.layout.view_expand_record_layout)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()

        val expandLayout: ExpandRecordLayout = v(R.id.expand_record_layout)
        click(R.id.button1) {
            expandLayout.expandLayout()
        }
    }
}