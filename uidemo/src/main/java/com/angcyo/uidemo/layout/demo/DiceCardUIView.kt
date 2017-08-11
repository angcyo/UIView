package com.angcyo.uidemo.layout.demo

import android.view.LayoutInflater
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseRecyclerUIView
import com.angcyo.uidemo.layout.bean.DiceCardBean
import com.angcyo.uidemo.layout.item.CardDataItem
import com.angcyo.uidemo.layout.item.DiceDataItem
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.kotlin.v
import com.angcyo.uiview.recycler.adapter.RBaseDataItem
import com.angcyo.uiview.recycler.adapter.RDataAdapter
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.rsen.RefreshLayout
import com.angcyo.uiview.widget.RSoftInputLayout
import java.util.*

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：骰子, 纸牌 demo
 * 创建人员：Robi
 * 创建时间：2017/08/10 14:21
 * 修改人员：Robi
 * 修改时间：2017/08/10 14:21
 * 修改备注：
 * Version: 1.0.0
 */
class DiceCardUIView : BaseRecyclerUIView<RBaseDataItem<*>>() {

    override fun getTitleString(): String {
        return "骰子/纸牌"
    }

    override fun getDefaultLayoutState(): LayoutState {
        return LayoutState.CONTENT
    }

    override fun createAdapter(): RExBaseAdapter<String, RBaseDataItem<*>, String> {
        return RDataAdapter(mActivity)
    }

    override fun initRefreshLayout(refreshLayout: RefreshLayout?, baseContentLayout: ContentLayout?) {
        super.initRefreshLayout(refreshLayout, baseContentLayout)
        refreshLayout?.setNoNotifyPlaceholder()
    }

    override fun createRecyclerRootView(baseContentLayout: ContentLayout?, inflater: LayoutInflater?) {
        //super.createRecyclerRootView(baseContentLayout, inflater)
        val inflate = inflate(R.layout.view_dice_card_layout)

        mRefreshLayout = inflate.v(R.id.refresh_view)
        initRefreshLayout(mRefreshLayout, baseContentLayout)
        mRecyclerView = inflate.v(R.id.recycler_view)
        initRecyclerView(mRecyclerView, baseContentLayout)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        val softLayout: RSoftInputLayout = mViewHolder.v(R.id.soft_input_layout)
        softLayout.addOnEmojiLayoutChangeListener { isEmojiShow, isKeyboardShow, height ->
            L.e("call: initOnShowContentLayout -> 表情:$isEmojiShow 键盘:$isKeyboardShow 高度:$height")
        }

        //显示表情
        mViewHolder.click(R.id.show_emoji) {
            if (it.tag == null) {
                it.tag = ""
                softLayout.showEmojiLayout()
            } else {
                it.tag = null
                softLayout.hideEmojiLayout()
            }
        }

        val random = Random(System.nanoTime())

        //发送骰子
        mViewHolder.click(R.id.send_dice, 0) {
            val intList = mutableListOf<Int>()
            val nextInt = random.nextInt(6)
            for (i in 0..nextInt) {
                intList.add(random.nextInt(6))
            }
            mExBaseAdapter.addLastItem(DiceDataItem(DiceCardBean(intList.toIntArray())))
            mRecyclerView.scrollToLastBottom(true)
        }

        //发送纸牌
        mViewHolder.click(R.id.send_card, 0) {
            val intList = mutableListOf<Int>()
            val nextInt = random.nextInt(10)
            for (i in 0..nextInt) {
                intList.add(random.nextInt(54))
            }
            mExBaseAdapter.addLastItem(CardDataItem(DiceCardBean(intList.toIntArray())))
            mRecyclerView.scrollToLastBottom(true)
        }
    }

}