package com.angcyo.uidemo.layout.demo

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.widget.FrameLayout
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uiview.base.UIIDialogImpl
import com.angcyo.uiview.model.AnimParam
import com.angcyo.uiview.widget.group.GuideFrameLayout

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/12/08 18:31
 * 修改人员：Robi
 * 修改时间：2017/12/08 18:31
 * 修改备注：
 * Version: 1.0.0
 */
class GuideLayoutUIView(anchorView: View /*锚点View*/) : UIIDialogImpl() {


    private val anchorRect = Rect()

    init {
        anchorView.getGlobalVisibleRect(anchorRect)
        L.e("call: GuideLayoutUIView init -> $anchorRect")
    }

    override fun inflateDialogView(dialogRootLayout: FrameLayout, inflater: LayoutInflater): View {
        return inflate(R.layout.dialog_guide_layout)
    }

    override fun initDialogContentView() {
        super.initDialogContentView()
        //view(R.id.view).x = anchorRect.left.toFloat()
        //view(R.id.view).y = anchorRect.top.toFloat()
        //UI.setView(view(R.id.view), anchorRect.width(), anchorRect.height())

        val rootLayout: GuideFrameLayout = v(R.id.root_layout)
        rootLayout.addAnchor(anchorRect)
        rootLayout.setBackgroundColor(getColor(R.color.transparent_dark20))
        click(rootLayout) {
            finishIView(this, false)
        }

        L.e("call: initDialogContentView -> ")
    }

    override fun isDimBehind(): Boolean {
        return false
    }

    override fun loadStartAnimation(animParam: AnimParam): Animation? {
        return null
    }

    override fun loadFinishAnimation(animParam: AnimParam): Animation? {
        return null
    }

    override fun loadOtherEnterAnimation(animParam: AnimParam): Animation? {
        return null
    }

    override fun loadOtherExitAnimation(animParam: AnimParam): Animation? {
        return null
    }

}
