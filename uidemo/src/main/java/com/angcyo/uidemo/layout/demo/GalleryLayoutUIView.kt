package com.angcyo.uidemo.layout.demo

import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uidemo.layout.demo.game.MusicNoteLayer
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.game.GameRenderView
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.viewgroup.OnGalleryChangeListener
import com.angcyo.uiview.viewgroup.RGalleryLayout

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/01/04 10:31
 * 修改人员：Robi
 * 修改时间：2018/01/04 10:31
 * 修改备注：
 * Version: 1.0.0
 */
class GalleryLayoutUIView : BaseItemUIView() {
    override fun createItems(items: MutableList<SingleItem>) {
        items?.let {
            it.add(object : SingleItem() {
                override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                    val galleryLayout: RGalleryLayout = holder.v(R.id.gallery_layout)
                    galleryLayout.apply {
                        onGalleryChangeListener = object : OnGalleryChangeListener() {
                            override fun onChangeTo(from: Int, to: Int) {
                                holder.tv(R.id.text_view).text = "from:$from to:$to"
                            }
                        }
                    }
                    holder.click(R.id.text_view) {
                        galleryLayout.scrollToIndex(0)
                    }

                    val gameRenderView: GameRenderView = holder.v(R.id.game_render_view)
                    gameRenderView.addLayer(MusicNoteLayer())
                }

                override fun getItemLayoutId(): Int {
                    return R.layout.item_gallery_layout
                }

            })
        }
    }

}