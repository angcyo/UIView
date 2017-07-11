package com.angcyo.uidemo.layout.demo

import android.view.LayoutInflater
import android.widget.FrameLayout
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.widget.TouchMoveGroupLayout
import com.angcyo.uiview.widget.TouchMoveView

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

    override fun inflateContentLayout(baseContentLayout: FrameLayout?, inflater: LayoutInflater) {
        inflate(R.layout.view_qq_navigation)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        val groupLayout: TouchMoveGroupLayout = mViewHolder.v(R.id.touch_move_group_layout)
        groupLayout.listener = object : TouchMoveGroupLayout.OnSelectorPositionListener {
            override fun onSelectorPosition(targetView: TouchMoveView, position: Int) {
                mViewHolder.tv(R.id.text_view).text = "Position:$position"
                L.e("onSelectorPosition:$position")

            }

            override fun onRepeatSelectorPosition(targetView: TouchMoveView, position: Int) {
                mViewHolder.tv(R.id.text_view).text = "RepeatPosition:$position"
                L.e("onRepeatSelectorPosition:$position")

                if (targetView.noReadNum > 0) {
                    targetView.noReadNum = -1
                } else {
                    when (position) {
                        0 -> targetView.noReadNum = 100
                        1 -> targetView.noReadNum = 11
                        2 -> targetView.noReadNum = 0
                    }
                }
            }
        }
    }
}
