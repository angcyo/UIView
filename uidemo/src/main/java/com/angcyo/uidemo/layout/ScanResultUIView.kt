package com.angcyo.uidemo.layout

import com.angcyo.github.utilcode.utils.ClipboardUtils
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.T_

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/10/31 08:29
 * 修改人员：Robi
 * 修改时间：2017/10/31 08:29
 * 修改备注：
 * Version: 1.0.0
 */
class ScanResultUIView(var result: String = "") : BaseItemUIView() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("扫码结果")
    }

    override fun getItemLayoutId(viewType: Int): Int {
        return R.layout.view_scan_result
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.tv(R.id.text_view).text = result

                holder.click(R.id.copy_view) {
                    ClipboardUtils.copyText(result)
                    T_.ok("内容已复制")
                }
            }
        })
    }

}