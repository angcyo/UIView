package com.angcyo.uidemo.layout.demo

import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.demo.chat.Chat1UIView
import com.angcyo.uiview.base.PageBean
import com.angcyo.uiview.base.UINavigationView

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/03/07 12:35
 * 修改人员：Robi
 * 修改时间：2018/03/07 12:35
 * 修改备注：
 * Version: 1.0.0
 */
class UIChatDemo : UINavigationView() {
    override fun createPages(pages: ArrayList<PageBean>) {
        pages.add(PageBean(Chat1UIView(), "", "",
                null, null,
                R.drawable.skin_tab_icon_conversation_normal, R.drawable.skin_tab_icon_conversation_selected))

        pages.add(PageBean(Chat1UIView(), "正常", "选中"))

        pages.add(PageBean(Chat1UIView(), "", "",
                null, null,
                R.drawable.skin_tab_icon_contact_normal, R.drawable.skin_tab_icon_contact_selected))

        pages.add(PageBean(Chat1UIView(), "我的", "我的",
                null, null,
                R.drawable.skin_tab_icon_plugin_normal, R.drawable.skin_tab_icon_plugin_selected))
    }

}