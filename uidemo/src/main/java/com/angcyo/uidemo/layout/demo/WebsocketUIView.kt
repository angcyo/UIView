package com.angcyo.uidemo.layout.demo

import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseRecyclerUIView
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.game.GameRenderView
import com.angcyo.uiview.game.helper.GameRenderHelper
import com.angcyo.uiview.game.layer.BaseFrameLayer
import com.angcyo.uiview.game.layer.FrameBgBean
import com.angcyo.uiview.game.layer.MoveBean
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.utils.ScreenUtil
import com.angcyo.uiview.viewgroup.RLinearLayout
import com.angcyo.uiview.widget.ExEditText
import com.angcyo.uiview.widget.helper.RainHelper
import java.util.*

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

    private val random: Random by lazy {
        Random(System.nanoTime())
    }

    private lateinit var editText: ExEditText
    private lateinit var rainHelper: RainHelper

    private lateinit var gameRenderHelper: GameRenderHelper
    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        loadData()

        rainHelper = RainHelper(v(R.id.rain_anim_view)).apply {
            rainResId = R.drawable.hot_package
        }

        val gameRenderView: GameRenderView = v(R.id.game_render_view)
        gameRenderHelper = GameRenderHelper(gameRenderView)

        editText = v(R.id.edit_text)
        editText.setOnTextEmptyListener { isEmpty ->
            view(R.id.send_button).isEnabled = !isEmpty
        }
        editText.setText("show2")

        click(R.id.send_button) {
            if (TextUtils.equals(editText.string(), "show")) {
                hideSoftInput()
                rainHelper.randomStep = mViewHolder.cV(R.id.step_box).isChecked
                rainHelper.useBezier = mViewHolder.cV(R.id.bezier_box).isChecked
                rainHelper.startRain()
                return@click
            }
            if (TextUtils.equals(editText.string(), "show2")) {
                hideSoftInput()

                val baseFrameLayer = BaseFrameLayer()
//                val baseMoveLayer = BaseMoveLayer()
//                val frameArray = arrayOf(getDrawable(R.drawable.home_48_color),
//                        getDrawable(R.drawable.me_48_color),
//                        getDrawable(R.drawable.live_48_color),
//                        getDrawable(R.drawable.message_48_color),
//                        getDrawable(R.drawable.shop_48_color))
//
//                gameRenderHelper.addLayer(RainBgLayer(getDrawable(R.drawable.hongbaoyu_bg)))
//                gameRenderHelper.addLayer(RainLayer(R.drawable.hot_package).apply {
//                    randomStep = mViewHolder.cV(R.id.step_box).isChecked
//                    useBezier = mViewHolder.cV(R.id.bezier_box).isChecked
//                    onClickRainListener = object : OnClickRainListener {
//                        override fun onClickRain(rainLayout: RainLayer, bean: RainBean) {
//                            rainLayout.removeRain(bean)
//
//                            baseFrameLayer.addFrameBean(FrameBean(frameArray, Point(bean.getRect().centerX(), bean.getRect().centerY())).apply {
//                                loop = random.nextBoolean()
//                            })
//
//                            baseMoveLayer.addFrameBean(MoveBean(
//                                    frameArray,
//                                    Point(bean.getRect().centerX(), bean.getRect().centerY()),
//                                    Point(ScreenUtil.screenWidth, 0)
//                            ).apply {
//                                frameDrawIntervalTime = (16 + random.nextInt(10) * 10).toLong()
//                                maxMoveTime = (1 + random.nextInt(4)) * 1000
//                            })
//                        }
//
//                    }
//                })
//                gameRenderHelper.addLayer(baseMoveLayer)
                addBgLayer(baseFrameLayer)
                addLineLayer(baseFrameLayer)
                addMarkLayer(baseFrameLayer)

                gameRenderHelper.addLayer(baseFrameLayer)

                view(R.id.game_render_view).visibility = View.VISIBLE
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


    /**背景*/
    private fun addBgLayer(hotRainLayer: BaseFrameLayer) {
        hotRainLayer.addFrameBean(FrameBgBean(getDrawable(R.drawable.rain_game_bg)))
    }

    /**流星*/
    private fun addLineLayer(hotRainLayer: BaseFrameLayer) {
        val dp20: Int = (20 * density()).toInt()
        val dp10: Int = (10 * density()).toInt()
        val sw = ScreenUtil.screenWidth
        val sh = ScreenUtil.screenHeight

        /*根据开始点和角度, 求出结束点*/
        fun endPoint(startPoint: Point, degrees: Float): Point {
            val endPoint = Point().apply {
                set(((ScreenUtil.screenHeight - startPoint.y) * Math.tan(Math.toRadians(degrees.toDouble()))).toInt(), ScreenUtil.screenHeight)
            }
            return endPoint
        }

        fun createMBean(id: Int, startPoint: Point, degrees: Float): MoveBean {
            return MoveBean(
                    arrayOf(getDrawable(id)),
                    startPoint,
                    endPoint(startPoint, degrees)).apply {
                maxMoveTime = (5 + random.nextInt(5)) * 1000
                rotateDegrees = -degrees
                isLoopMove = true
            }
        }

        val mb1 = createMBean(R.drawable.liuxing01, Point(0, dp20 * 2), 25f)
        val mb2s = Point(0, dp20 * 6)
        val mb2 = object : MoveBean(
                arrayOf(getDrawable(R.drawable.liuxing02)),
                mb2s,
                endPoint(mb2s, 25f)) {
            init {
                maxMoveTime = (5 + random.nextInt(7)) * 1000
                rotateDegrees = -25f
                constantSpeed = false
                isLoopMove = true
            }

            private var isSet = false
            override fun getDrawDrawableBounds(drawable: Drawable): Rect {
                return super.getDrawDrawableBounds(drawable).apply {
                    if (!isSet) {
                        inset(-dp10, -dp10)
                        isSet = true
                    }
                }
            }
        }
        val mb3 = createMBean(R.drawable.liuxing01, Point(dp20, dp20 * 2), 25f)
//                object : MoveBean(
//                arrayOf(getDrawable(R.drawable.liuxing01)),
//                Point(dp20, dp20),
//                Point(sw, sh - dp20)) {
//            init {
//                maxMoveTime = 5000
//                rotateDegrees = -30f
//                isLoopMove = true
//            }
//        }

        val mb4s = Point(0, dp20 * 8)
        val mb4 = object : MoveBean(
                arrayOf(getDrawable(R.drawable.liuxing02)),
                mb4s,
                endPoint(mb4s, 25f)) {
            init {
                maxMoveTime = (5 + random.nextInt(7)) * 1000
                rotateDegrees = -25f
                constantSpeed = false
                isLoopMove = true
            }

            private var isSet = false
            override fun getDrawDrawableBounds(drawable: Drawable): Rect {
                return super.getDrawDrawableBounds(drawable).apply {
                    if (!isSet) {
                        inset(-dp10, -dp10)
                        isSet = true
                    }
                }
            }
        }

        val mb5 = createMBean(R.drawable.liuxing01, Point(0, 0), 25f)
//                object : MoveBean(
//                arrayOf(getDrawable(R.drawable.liuxing01)),
//                Point(0, dp20 * 3),
//                Point(sw - dp20 * 3, sh)) {
//            init {
//                maxMoveTime = 5000
//                rotateDegrees = -30f
//                isLoopMove = true
//            }
//        }

        val mb6s = Point(-dp20 * 4, dp20 * 10)
        val mb6 = object : MoveBean(
                arrayOf(getDrawable(R.drawable.liuxing02)),
                mb6s,
                endPoint(mb6s, 25f)) {
            init {
                maxMoveTime = (5 + random.nextInt(7)) * 1000
                rotateDegrees = -25f
                constantSpeed = false
                isLoopMove = true
            }

            private var isSet = false
            override fun getDrawDrawableBounds(drawable: Drawable): Rect {
                return super.getDrawDrawableBounds(drawable).apply {
                    if (!isSet) {
                        inset(-dp10, -dp10)
                        isSet = true
                    }
                }
            }
        }

        hotRainLayer.addFrameBean(mb1)
//        hotRainLayer.addFrameBean(mb2)
//        hotRainLayer.addFrameBean(mb3)
//        hotRainLayer.addFrameBean(mb4)
//        hotRainLayer.addFrameBean(mb5)
//        hotRainLayer.addFrameBean(mb6)

    }

    /**山*/
    private fun addMarkLayer(hotRainLayer: BaseFrameLayer) {
        hotRainLayer.addFrameBean(object : FrameBgBean(getDrawable(R.drawable.shan_bg)) {
            override fun draw(canvas: Canvas, gameStartTime: Long, lastRenderTime: Long, nowRenderTime: Long, onDrawEnd: () -> Unit) {
                bgDrawable.let {
                    it.bounds.set(parentRect.left, parentRect.bottom - bgDrawable.intrinsicHeight, parentRect.right, parentRect.bottom)
                    it.draw(canvas)
                }
            }
        })
    }

}