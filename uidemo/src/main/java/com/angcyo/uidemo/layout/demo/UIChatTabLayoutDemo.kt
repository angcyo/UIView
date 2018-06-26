package com.angcyo.uidemo.layout.demo

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.angcyo.uidemo.R
import com.angcyo.uidemo.uiview.LogInfoUIView
import com.angcyo.uiview.base.UIBaseTabView
import com.angcyo.uiview.container.UIParam
import com.angcyo.uiview.skin.SkinHelper
import com.angcyo.uiview.view.IView
import com.angcyo.uiview.viewgroup.RTabLayout
import com.angcyo.uiview.widget.TouchMoveView

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
class UIChatTabLayoutDemo : UIBaseTabView() {
    //    override fun createPages(pages: ArrayList<PageBean>) {
//        pages.add(PageBean(ChatMessageUIView(), "", "",
//                null, null,
//                R.drawable.skin_tab_icon_conversation_normal, R.drawable.skin_tab_icon_conversation_selected))
//
//        pages.add(PageBean(Chat2UIView(), "正常", "选中",
//                null, null,
//                null, R.drawable.skin_tab_icon_plugin_selected,
//                null, null))
//
//        pages.add(PageBean(ChatClassMessageUIView(), "", "",
//                null, null,
//                R.drawable.skin_tab_icon_contact_normal, R.drawable.skin_tab_icon_contact_selected))
//
//        pages.add(PageBean(Chat1UIView(), "我的", "我的",
//                null, null,
//                R.drawable.skin_tab_icon_plugin_normal, R.drawable.skin_tab_icon_plugin_selected))
//    }

    override fun onViewCreate(rootView: View, param: UIParam) {
        super.onViewCreate(rootView, param)
        rootView.setBackgroundColor(Color.WHITE)
    }

    override fun getBaseLayoutId(): Int {
        return R.layout.view_chat_tab_layout
    }

    override fun initTabLayout(tabLayout: RTabLayout, iViews: MutableList<IView>) {
        //tablayout.addView()
        for (i in 0..2) {
            iViews.add(LogInfoUIView())
        }
        super.initTabLayout(tabLayout, iViews)
    }

    override fun onUnSelectorItemView(tabLayout: RTabLayout, itemView: View, index: Int) {
        super.onUnSelectorItemView(tabLayout, itemView, index)
        when (index) {
            0 -> (itemView as ImageView).setImageResource(R.drawable.skin_tab_icon_conversation_normal)
            1 -> {
                (itemView as TextView).apply {
                    text = "居中Item文本"
                    setTextColor(getColor(R.color.base_text_color))
                }
            }
            2 -> {
                if (itemView is ImageView) {
                    itemView.setImageResource(R.drawable.skin_tab_icon_contact_normal)
                } else if (itemView is TouchMoveView) {
                    itemView.mSelected = false
                }
            }
        }
    }

    override fun onSelectorItemView(tabLayout: RTabLayout, itemView: View, index: Int) {
        super.onSelectorItemView(tabLayout, itemView, index)
        when (index) {
            0 -> (itemView as ImageView).setImageResource(R.drawable.skin_tab_icon_conversation_selected)
            1 -> {
                (itemView as TextView).apply {
                    text = "居中Item文本(选中)"
                    setTextColor(SkinHelper.getSkin().themeSubColor)
                }
            }
            2 -> {
                if (itemView is ImageView) {
                    itemView.setImageResource(R.drawable.skin_tab_icon_contact_selected)
                } else if (itemView is TouchMoveView) {
                    itemView.mSelected = true
                }
            }
        }
    }

    override fun onTabReSelector(tabLayout: RTabLayout, itemView: View, index: Int) {
        super.onTabReSelector(tabLayout, itemView, index)

        val iView = iViews[index]
        if (iView is LogInfoUIView) {
            iView.append("onTabReSelector: $index \n$itemView ")
        }
    }
}
