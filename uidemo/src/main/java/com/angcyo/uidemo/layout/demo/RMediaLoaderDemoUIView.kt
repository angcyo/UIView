package com.angcyo.uidemo.layout.demo

import com.angcyo.picker.media.OnMediaSelectorObserver
import com.angcyo.picker.media.bean.MediaItem
import com.angcyo.picker.media.bean.MediaLoaderConfig
import com.angcyo.picker.media.uiview.RMediaLoaderUIView
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.base.UIItemUIView
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.RUtils

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/15 16:05
 * 修改人员：Robi
 * 修改时间：2018/06/15 16:05
 * 修改备注：
 * Version: 1.0.0
 */
class RMediaLoaderDemoUIView : BaseItemUIView() {

    var mediaItemList: MutableList<MediaItem>? = null

    override fun createItems(items: MutableList<SingleItem>) {
        val observer = object : OnMediaSelectorObserver {
            override fun onMediaSelector(mediaItemList: MutableList<MediaItem>) {
                this@RMediaLoaderDemoUIView.mediaItemList = mediaItemList
                notifyItemChangedByTag("result")
            }
        }

        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                UIItemUIView.baseInitItem(holder, "打开图片/视频/音频 选择器") {
                    startIView(RMediaLoaderUIView().apply {
                        mediaLoaderConfig = MediaLoaderConfig().apply {
                            mediaLoaderType = MediaLoaderConfig.LOADER_TYPE_ALL
                            limitFileSizeModel = MediaLoaderConfig.SIZE_MODEL_MEDIA
//                            limitFileMinSize = 200f
//                            limitFileMaxSize = 400f
                        }

                        onMediaSelectorObserver = observer
                    })
                }
            }
        })

        items.add(object : SingleItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                UIItemUIView.baseInitItem(holder, "打开图片/视频 选择器") {
                    startIView(RMediaLoaderUIView().apply {
                        mediaLoaderConfig = MediaLoaderConfig().apply {
                            mediaLoaderType = MediaLoaderConfig.LOADER_TYPE_IMAGE_VIDEO
                            limitFileSizeModel = MediaLoaderConfig.SIZE_MODEL_SELECTOR
//                            limitFileMinSize = 200f
//                            limitFileMaxSize = 400f
                        }

                        onMediaSelectorObserver = observer
                    })
                }
            }
        })

        items.add(object : SingleItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                UIItemUIView.baseInitItem(holder, "打开图片 选择器") {
                    startIView(RMediaLoaderUIView().apply {
                        mediaLoaderConfig = MediaLoaderConfig().apply {
                            mediaLoaderType = MediaLoaderConfig.LOADER_TYPE_IMAGE
                            limitFileSizeModel = MediaLoaderConfig.SIZE_MODEL_SELECTOR
                            limitFileMaxSize = 4000f
                            limitFileMinSize = 200f
                        }

                        onMediaSelectorObserver = observer
                    })
                }
            }
        })

        items.add(object : SingleItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                UIItemUIView.baseInitItem(holder, "打开视频 选择器") {
                    startIView(RMediaLoaderUIView().apply {
                        mediaLoaderConfig = MediaLoaderConfig().apply {
                            mediaLoaderType = MediaLoaderConfig.LOADER_TYPE_VIDEO

                            limitFileSizeModel = MediaLoaderConfig.SIZE_MODEL_SELECTOR
                            limitFileMinSize = 3 * 1024f
                        }

                        onMediaSelectorObserver = observer
                    })
                }
            }
        })

        items.add(object : SingleItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                UIItemUIView.baseInitItem(holder, "打开音频 选择器") {
                    startIView(RMediaLoaderUIView().apply {
                        mediaLoaderConfig = MediaLoaderConfig().apply {
                            mediaLoaderType = MediaLoaderConfig.LOADER_TYPE_AUDIO
                        }

                        onMediaSelectorObserver = observer
                    })
                }
            }
        })

        items.add(object : SingleItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                UIItemUIView.baseInitItem(holder, "打开图片/视频 选择器 (不混合)") {
                    startIView(RMediaLoaderUIView().apply {
                        mediaLoaderConfig = MediaLoaderConfig().apply {
                            mediaLoaderType = MediaLoaderConfig.LOADER_TYPE_IMAGE_VIDEO
                            limitFileSizeModel = MediaLoaderConfig.SIZE_MODEL_SELECTOR
                            mixSelectorModel = MediaLoaderConfig.LOADER_TYPE_IMAGE
                            maxSelectorVideoLimit = 2
//                            limitFileMinSize = 200f
//                            limitFileMaxSize = 400f
                        }

                        onMediaSelectorObserver = observer
                    })
                }
            }
        })

        items.add(object : SingleItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                UIItemUIView.baseInitItem(holder, "打开图片/视频/音频 选择器 (单选)") {
                    startIView(RMediaLoaderUIView().apply {
                        mediaLoaderConfig = MediaLoaderConfig().apply {
                            mediaLoaderType = MediaLoaderConfig.LOADER_TYPE_ALL
                            selectorModel = MediaLoaderConfig.SELECTOR_MODEL_SINGLE
                        }

                        onMediaSelectorObserver = observer
                    })
                }
            }
        })

        items.add(object : SingleItem(Type.TOP, "result") {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                holder.tv(R.id.text_view).textSize = 12f
                if (RUtils.isListEmpty(mediaItemList)) {
                    holder.tv(R.id.text_view).text = "nothing to show"
                } else {
                    val builder = StringBuilder()
                    for (item in mediaItemList!!) {
                        builder.append("\n")
                        builder.append(item.mimeType)
                        builder.append("\n")
                        builder.append(item.displayName)
                        builder.append("\n")
                        builder.append(item.path)
                        builder.append("\n")
                    }

                    holder.tv(R.id.text_view).text = builder.toString()
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_text_view
            }
        })
    }

}