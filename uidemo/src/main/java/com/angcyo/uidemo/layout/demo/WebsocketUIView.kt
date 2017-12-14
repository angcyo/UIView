package com.angcyo.uidemo.layout.demo

import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.LinearLayout
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseRecyclerUIView
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.viewgroup.RLinearLayout
import com.angcyo.uiview.widget.ExEditText
import com.angcyo.uiview.widget.helper.RainHelper

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/12/12 09:42
 * 修改人员：Robi
 * 修改时间：2017/12/12 09:42
 * 修改备注：
 * Version: 1.0.0
 */
class WebsocketUIView : BaseRecyclerUIView<String>() {

    companion object {
        val ITEM_TYPE_LEFT = 1
        val ITEM_TYPE_RIGHT = 2
    }

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("WebSocket聊天室")
    }

//    override fun getDefaultBackgroundColor(): Int {
//        return Color.RED
//    }


    override fun getDefaultLayoutState(): LayoutState {
        return LayoutState.CONTENT
    }

    override fun createRecyclerRootView(baseContentLayout: ContentLayout?, inflater: LayoutInflater?) {
        inflate(R.layout.view_websocket_layout).apply {
            mRecyclerView = findViewById(R.id.recycler_view)
            mRefreshLayout = findViewById(R.id.refresh_view)

            initRefreshLayout(mRefreshLayout, baseContentLayout)
            initRecyclerView(mRecyclerView, baseContentLayout)

            mRecyclerView.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false).apply {
                stackFromEnd = true
            }
        }

    }

    override fun createAdapter(): RExBaseAdapter<String, String, String> = object : RExBaseAdapter<String, String, String>(mActivity) {

        private fun isLeft(position: Int): Boolean = position % 2 != 0

        var isScrollFromBottom = false
        var needShowSoftInput = false

        override fun onScrollStateChanged(recyclerView: RRecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RRecyclerView.SCROLL_STATE_IDLE) {
                isScrollFromBottom = false
                if (needShowSoftInput) {
                    showSoftInput(editText)
                    postDelayed(60) {
                        recyclerView?.scrollToLastBottom(true)
                    }
                }
                needShowSoftInput = false
            } else if (newState == RRecyclerView.SCROLL_STATE_DRAGGING) {
                isScrollFromBottom = recyclerView?.isBottomEnd == true
            }
        }

        override fun onScrolledInTouch(recyclerView: RRecyclerView?, e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float) {
            super.onScrolledInTouch(recyclerView, e1, e2, distanceX, distanceY)
            if (distanceY < 0) {
                hideSoftInput()
                needShowSoftInput = false
            } else if (distanceY > 0 && isScrollFromBottom) {
                needShowSoftInput = true
            }
        }

        override fun onScrolled(recyclerView: RRecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

        }


        override fun getDataItemType(posInData: Int): Int {
            return if (isLeft(posInData)) {
                ITEM_TYPE_RIGHT
            } else {
                ITEM_TYPE_LEFT
            }
        }

        override fun getItemLayoutId(viewType: Int): Int {
//            return if (viewType == ITEM_TYPE_RIGHT) {
//                R.layout.item_websocket_chat_layout
//            } else {
//                R.layout.item_websocket_chat_layout
//            }
            return R.layout.item_websocket_chat_layout
        }

        override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: String) {
            super.onBindDataView(holder, posInData, dataBean)

            val nameText = StringBuilder()
            for (i in 0..posInData % 2) {
                nameText.append("用户昵称$dataBean")
            }
            val contentText = StringBuilder()
            for (i in 0..posInData % 6) {
                contentText.append("测试文本$dataBean")
            }

            holder.tv(R.id.user_name_text_view).text = nameText
            holder.tv(R.id.content_text_view).text = contentText

            val itemRootLayout = holder.itemView as RLinearLayout
            val contentControlLayout = holder.v<LinearLayout>(R.id.content_control_layout)

            if (isLeft(posInData)) {
//                itemRootLayout.layoutDirection = LAYOUT_DIRECTION_LTR
//                itemRootLayout.isInChatLayout = false
                itemRootLayout.isReverseLayout = false
                holder.tv(R.id.content_text_view).setBackgroundResource(R.drawable.bubble_box_left_n)
            } else {
//                itemRootLayout.layoutDirection = LAYOUT_DIRECTION_RTL
//                itemRootLayout.isInChatLayout = false
                itemRootLayout.isReverseLayout = true
                holder.tv(R.id.content_text_view).setBackgroundResource(R.drawable.bubble_box_right_black_s)
            }
        }
    }

    private lateinit var editText: ExEditText
    private lateinit var rainHelper: RainHelper
    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        loadData()

        rainHelper = RainHelper(v(R.id.rain_anim_view)).apply {
            rainResId = R.drawable.hot_package
        }

        editText = v(R.id.edit_text)
        editText.setOnTextEmptyListener { isEmpty ->
            view(R.id.send_button).isEnabled = !isEmpty
        }

        click(R.id.send_button) {
            if (TextUtils.equals(editText.string(), "show")) {
                hideSoftInput()
                rainHelper.randomStep = mViewHolder.cV(R.id.step_box).isChecked
                rainHelper.useBezier = mViewHolder.cV(R.id.bezier_box).isChecked
                rainHelper.startRain()
                return@click
            }

            mExBaseAdapter.addLastItem(editText.string())
            v<ExEditText>(R.id.edit_text).setText("")
            post {
                mRecyclerView.scrollToLastBottom(true)
            }
        }
    }

    override fun onBaseLoadData() {
        super.onBaseLoadData()
        loadData()
    }

    override fun onBaseLoadMore() {
        super.onBaseLoadMore()
        val datas = (0..100).map { it.toString() }

        mExBaseAdapter.appendAllData(datas)

        postDelayed(1000) {
            resetUI()
        }
    }

    private fun loadData() {
        val datas = (0..100).map { it.toString() }

        mExBaseAdapter.resetAllData(datas)
        postDelayed(1000) {
            resetUI()
        }
    }

}