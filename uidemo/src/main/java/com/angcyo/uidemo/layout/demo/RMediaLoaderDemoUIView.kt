package com.angcyo.uidemo.layout.demo

import com.angcyo.picker.media.uiview.RMediaLoaderUIView
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.base.UIItemUIView
import com.angcyo.uiview.recycler.RBaseViewHolder

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/15 16:05
 * 修改人员：Robi
 * 修改时间：2018/06/15 16:05
 * 修改备注：
 * Version: 1.0.0
 */
class RMediaLoaderDemoUIView : BaseItemUIView() {
    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                UIItemUIView.baseInitItem(holder, "打开图片/视频/音频 选择器") {
                    startIView(RMediaLoaderUIView())
                }
            }
        })
    }

}