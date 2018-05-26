package com.angcyo.uidemo.layout.demo.chat

import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.demo.holder.*
import com.angcyo.uidemo.layout.demo.type.*
import com.angcyo.uiview.base.UIClassItemUIView
import com.angcyo.uiview.recycler.adapter.RExItem

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
class ChatClassMessageUIView : UIClassItemUIView<String>() {
    override fun registerItems(allRegItems: ArrayList<RExItem<Class<*>, String>>) {
        allRegItems.add(RExItem(StringType::class.java, R.layout.ex_item_text_layout, ItemTextHolder::class.java))
        allRegItems.add(RExItem(ImageType::class.java, R.layout.ex_item_text_layout, ItemImageHolder::class.java))
        allRegItems.add(RExItem(VideoType::class.java, R.layout.ex_item_text_layout, ItemVideoHolder::class.java))
        allRegItems.add(RExItem(MusicType::class.java, R.layout.ex_item_text_layout, ItemMusicHolder::class.java))
        allRegItems.add(RExItem(OtherType::class.java, R.layout.ex_item_text_layout, ItemOtherHolder::class.java))
        allRegItems.add(RExItem(EmptyType::class.java, R.layout.ex_item_empty_layout, ItemEmptyHolder::class.java))
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

    override fun getItemTypeFromData(data: String?, position: Int): Class<*>? {
        return when (data) {
            "文本" -> {
                StringType::class.java
            }
            "图片" -> {
                ImageType::class.java
            }
            "视频" -> {
                VideoType::class.java
            }
            "音乐" -> {
                MusicType::class.java
            }
            "其他" -> {
                OtherType::class.java
            }
            "空" -> {
                EmptyType::class.java
            }
            else -> {
                StringType::class.java
            }
        }
    }

}