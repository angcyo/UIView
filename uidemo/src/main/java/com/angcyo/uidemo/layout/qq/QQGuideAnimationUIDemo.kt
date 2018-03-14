package com.angcyo.uidemo.layout.qq

import android.view.LayoutInflater
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uidemo.layout.view.QQGuideAnimLayout
import com.angcyo.uiview.container.ContentLayout

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/03/14 11:29
 * 修改人员：Robi
 * 修改时间：2018/03/14 11:29
 * 修改备注：
 * Version: 1.0.0
 */
class QQGuideAnimationUIDemo : BaseContentUIView() {
    override fun inflateContentLayout(baseContentLayout: ContentLayout?, inflater: LayoutInflater?) {
        inflate(R.layout.view_qq_guid_anim_layout)
    }

    override fun getDefaultBackgroundColor(): Int {
        return getColor(R.color.base_chat_bg_color)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        val qqGuideAnimLayout: QQGuideAnimLayout = v(R.id.qq_guide_anim_layout)

        fun run(index: Int) {
            if (qqGuideAnimLayout.currentIndex > index) {
                qqGuideAnimLayout.animToPrev()
            } else {
                qqGuideAnimLayout.animToNext()
            }
        }

        click(R.id.next_1) {
            run(0)
        }
        click(R.id.next_2) {
            run(1)
        }
        click(R.id.next_3) {
            run(2)
        }
        click(R.id.next_4) {
            run(3)
        }
    }
}