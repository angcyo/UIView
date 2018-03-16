package com.angcyo.uidemo.layout.demo.chat

import com.angcyo.uidemo.layout.base.BaseRecyclerUIView
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/03/16 13:53
 * 修改人员：Robi
 * 修改时间：2018/03/16 13:53
 * 修改备注：
 * Version: 1.0.0
 */
class ChatMessageUIView : BaseRecyclerUIView<String>() {
    override fun createAdapter(): RExBaseAdapter<String, String, String> {
        return object : RExBaseAdapter<String, String, String>(mActivity) {

        }
    }

}