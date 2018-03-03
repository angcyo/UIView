package com.angcyo.uidemo.layout.demo.test

import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uidemo.layout.demo.ContentStateUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.container.UIParam
import com.angcyo.uiview.dialog.UIDialog
import com.angcyo.uiview.dialog.UILoading
import com.angcyo.uiview.kotlin.clickIt
import com.angcyo.uiview.model.TitleBarItem
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.resources.ResUtil

/**
 * Created by angcyo on 2018-03-03.
 */
abstract class BaseTestUIView : BaseItemUIView() {
    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar()
                .setBackImageString("返回")
                .setTitleBarBGDrawable(ResUtil.createGradientDrawable(getColor(R.color.theme_color_primary),
                        getColor(R.color.theme_color_primary_dark), 0f))
//                .addLeftItem(TitleBarItem("返回", R.drawable.base_back) {
//
//                })
                .addRightItem(TitleBarItem("Hide") {
                    hideIView(mRootView)
                })
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.item(R.id.base_item_info_layout).apply {
                    setItemText("SINGLE_TOP 启动 ContentStateUIView")

                    clickIt {
                        startIView(ContentStateUIView(), UIParam().setLaunchMode(UIParam.SINGLE_TOP))
                    }
                }
            }
        })
        items.add(object : SingleItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.item(R.id.base_item_info_layout).apply {
                    setItemText("SINGLE_TOP 启动 ContentStateUIView, Dialog")

                    clickIt {
                        showDialog()
                        startIView(ContentStateUIView(), UIParam().setLaunchMode(UIParam.SINGLE_TOP))
                    }
                }
            }
        })
        items.add(object : SingleItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.item(R.id.base_item_info_layout).apply {
                    setItemText("替换 ReplaceUIView")

                    clickIt {
                        replaceIView(ReplaceUIView(), UIParam())
                    }
                }
            }
        })
        items.add(object : SingleItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.item(R.id.base_item_info_layout).apply {
                    setItemText("SINGLE_TOP 替换 ReplaceUIView")

                    clickIt {
                        replaceIView(ReplaceUIView(), UIParam().setLaunchMode(UIParam.SINGLE_TOP))
                    }
                }
            }
        })
        items.add(object : SingleItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.item(R.id.base_item_info_layout).apply {
                    setItemText("SINGLE_TOP 替换 ContentStateUIView")

                    clickIt {
                        startIView(ContentStateUIView(), UIParam().setLaunchMode(UIParam.SINGLE_TOP))
                    }
                }
            }
        })
        items.add(object : SingleItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.item(R.id.base_item_info_layout).apply {
                    setItemText("SINGLE_TOP 替换 ContentStateUIView, Dialog")

                    clickIt {
                        showDialog()
                        startIView(ContentStateUIView(), UIParam().setLaunchMode(UIParam.SINGLE_TOP))
                    }
                }
            }
        })
    }

    protected fun showDialog() {
        UIDialog.build()
                .setDialogContent("恶心的对话框出现了.")
                .setOkListener {
                    UILoading.progress(mParentILayout)
                }
                .showDialog(mParentILayout)
    }
}
