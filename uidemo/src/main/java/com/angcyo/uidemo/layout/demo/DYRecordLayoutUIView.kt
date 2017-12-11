package com.angcyo.uidemo.layout.demo

import android.view.LayoutInflater
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.viewgroup.DYRecordView

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/09/19 17:11
 * 修改人员：Robi
 * 修改时间：2017/09/19 17:11
 * 修改备注：
 * Version: 1.0.0
 */
class DYRecordLayoutUIView : BaseContentUIView() {
    override fun inflateContentLayout(baseContentLayout: ContentLayout?, inflater: LayoutInflater?) {
        inflate(R.layout.view_dy_record_layout)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        val recordLayout1 = v<DYRecordView>(R.id.record_view1)
        val recordLayout2 = v<DYRecordView>(R.id.record_view2)
        val recordLayout3 = v<DYRecordView>(R.id.record_view3)

        recordLayout3.switchRecordType()

        click(R.id.button) {
            recordLayout1.switchRecordType()
            recordLayout2.switchRecordType()
            recordLayout3.switchRecordType()
        }
    }

}