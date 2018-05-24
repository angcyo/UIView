package com.angcyo.uidemo.layout.demo.holder

import com.angcyo.uidemo.R
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RExItemHolder
import com.angcyo.uiview.utils.ScreenUtil
import com.angcyo.uiview.utils.UI

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/03/21 11:58
 * 修改人员：Robi
 * 修改时间：2018/03/21 11:58
 * 修改备注：
 * Version: 1.0.0
 */
class ItemOtherHolder : RExItemHolder<String>() {
    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: String?) {
        //holder.itemView.setBackgroundColor(Color.GRAY)
        UI.setViewHeight(holder.itemView, (350 * ScreenUtil.density()).toInt())
        holder.tv(R.id.text_view).text = "数据:$dataBean"
    }
}