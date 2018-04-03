package com.angcyo.uidemo.layout

import android.graphics.Color
import android.view.LayoutInflater
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uidemo.layout.demo.NotifyDemoUIView
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.container.UIParam

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/04/03 13:44
 * 修改人员：Robi
 * 修改时间：2018/04/03 13:44
 * 修改备注：
 * Version: 1.0.0
 */
class PluginTestUIView : BaseContentUIView() {
    override fun inflateContentLayout(baseContentLayout: ContentLayout, inflater: LayoutInflater) {
        injectPluginPackage(mPluginPackage)
        inflate(R.layout.view_plugin_text_layout)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()

        injectPluginPackage(mPluginPackage)
        click(R.id.text_view) {
            it.setBackgroundColor(Color.RED)
            mViewHolder.tv(R.id.text_view).text = "动态设置文本"
        }

        click(R.id.button_view) {
            startIView(NotifyDemoUIView())
        }
    }

    override fun onViewUnload(uiParam: UIParam?) {
        super.onViewUnload(uiParam)
        injectPluginPackage(null)
    }
}