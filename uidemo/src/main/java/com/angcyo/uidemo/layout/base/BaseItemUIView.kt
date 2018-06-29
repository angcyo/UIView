package com.angcyo.uidemo.layout.base

import android.view.View
import com.angcyo.uidemo.R
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.base.UIItemUIView
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.widget.ItemInfoLayout

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/24 16:21
 * 修改人员：Robi
 * 修改时间：2017/04/24 16:21
 * 修改备注：
 * Version: 1.0.0
 */
abstract class BaseItemUIView : UIItemUIView<SingleItem>() {

    override fun createTitleBarPattern(): TitleBarPattern {
        return super.createTitleBarPattern().setTitleStringLength(25)
    }

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleStringLength(30)
    }

    override fun getDefaultBackgroundColor(): Int {
        return super.getDefaultBackgroundColor()
    }

    internal fun initItem(holder: RBaseViewHolder, itemText: String, onClickListener: View.OnClickListener) {
        initItem(holder, itemText, false, onClickListener)
    }

    internal fun initItem(holder: RBaseViewHolder, itemText: String, isDeprecated: Boolean, onClickListener: View.OnClickListener) {
        val infoLayout = holder.v<ItemInfoLayout>(R.id.base_item_info_layout)
        infoLayout.setItemText(itemText)

        val textView = infoLayout.textView
        textView.setDeleteLine(isDeprecated)
        holder.click(infoLayout, onClickListener)
    }

}
