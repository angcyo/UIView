package com.angcyo.uidemo.layout.demo.chat

import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseRecyclerUIView
import com.angcyo.uidemo.layout.demo.holder.*
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.recycler.adapter.RExItem
import com.angcyo.uiview.recycler.adapter.RExItemAdapter
import com.angcyo.uiview.recycler.adapter.RExItemFactory

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
        return RExItemAdapter(mActivity, createItemFactory())
    }

    override fun getDefaultLayoutState(): LayoutState {
        return LayoutState.CONTENT
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        mExBaseAdapter.resetDataData("空")
    }

    override fun onUILoadData(page: Int, extend: String?) {
        super.onUILoadData(page, extend)
        resetUI()
        mExBaseAdapter.resetData(listOf("文本", "图片", "视频", "音乐", "其他", "空", "文本", "图片", "视频", "音乐", "其他", "空", "文本", "图片", "视频", "音乐", "其他", "空"))
    }

    private fun createItemFactory(): RExItemFactory<String, String> {
        return object : RExItemFactory<String, String>() {
            override fun registerItems(allRegItems: ArrayList<RExItem<String, String>>) {
                allRegItems.add(RExItem("文本", R.layout.ex_item_text_layout, ItemTextHolder::class.java))
                allRegItems.add(RExItem("图片", R.layout.ex_item_text_layout, ItemImageHolder::class.java))
                allRegItems.add(RExItem("视频", R.layout.ex_item_text_layout, ItemVideoHolder::class.java))
                allRegItems.add(RExItem("音乐", R.layout.ex_item_text_layout, ItemMusicHolder::class.java))
                allRegItems.add(RExItem("其他", R.layout.ex_item_text_layout, ItemOtherHolder::class.java))
                allRegItems.add(RExItem("空", R.layout.ex_item_empty_layout, ItemEmptyHolder::class.java))
            }

            override fun getItemTypeFromData(data: String): String {
                return data
            }
        }
    }

}