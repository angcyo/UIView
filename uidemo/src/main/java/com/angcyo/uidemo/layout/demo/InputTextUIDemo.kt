package com.angcyo.uidemo.layout.demo

import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.base.UIInputView
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.widget.ItemInfoLayout

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/11/01 15:02
 * 修改人员：Robi
 * 修改时间：2017/11/01 15:02
 * 修改备注：
 * Version: 1.0.0
 */

class InputTextUIDemo : BaseItemUIView() {
    override fun createItems(items: MutableList<SingleItem>?) {
        items?.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val infoLayout1: ItemInfoLayout = holder.v(R.id.item_layout1)
                infoLayout1.setItemText("点击输入单行文本")

                val infoLayout2: ItemInfoLayout = holder.v(R.id.item_layout2)
                infoLayout2.setItemText("点击输入多行文本")

                holder.click(R.id.item_layout1) {
                    startIView(object : UIInputView() {
                        override fun getTitleString(): String {
                            return "请输入文本"
                        }

                        override fun getTipInputString(): String {
                            return "这里是输入提示:"
                        }

                        override fun initEditView() {
                            super.initEditView()
                            editView.setInputText("默认文本")
                        }

                        override fun getMaxInputLength(): Int {
                            return 10
                        }

                        override fun onInputTextResult(text: String) {
                            super.onInputTextResult(text)
                            holder.tv(R.id.text_view).text = text
                        }
                    })
                }

                holder.click(R.id.item_layout2) {
                    startIView(object : UIInputView() {
                        override fun getTitleString(): String {
                            return "请输入文本"
                        }

                        override fun getTipInputString(): String {
                            return "这里是输入提示:"
                        }

                        override fun initEditView() {
                            super.initEditView()
                            editView.hint = "输入提示."
                        }

                        override fun getInputViewHeight(): Int {
                            return (100 * density()).toInt()
                        }

                        override fun getMaxInputLength(): Int {
                            return 100
                        }

                        override fun onInputTextResult(text: String) {
                            super.onInputTextResult(text)
                            holder.tv(R.id.text_view).text = text
                        }
                    })
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.view_input_text
            }

        })
    }

}