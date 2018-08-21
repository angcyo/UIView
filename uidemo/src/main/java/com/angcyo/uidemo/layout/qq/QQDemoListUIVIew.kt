package com.angcyo.uidemo.layout.qq

import android.view.View
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uidemo.layout.qq.demo.QQProgressBarUIDemo
import com.angcyo.uidemo.layout.qq.demo.QQScrollTestLayoutUIDemo
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.base.UIBaseView
import com.angcyo.uiview.recycler.RBaseViewHolder

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/29 09:30
 * 修改人员：Robi
 * 修改时间：2018/06/29 09:30
 * 修改备注：
 * Version: 1.0.0
 */
class QQDemoListUIVIew : BaseItemUIView() {
    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".QQ Guide Animation Demo",
                        false, View.OnClickListener { v -> startIView(QQGuideAnimationUIDemo().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".QQ Scroll Test Demo",
                        false, View.OnClickListener { v -> startIView(QQScrollTestLayoutUIDemo().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".QQ Progress Bar Demo",
                        false, View.OnClickListener { v -> startIView(QQProgressBarUIDemo().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
    }
}