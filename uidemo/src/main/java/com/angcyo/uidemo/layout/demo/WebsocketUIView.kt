package com.angcyo.uidemo.layout.demo

import android.graphics.Canvas
import android.graphics.Point
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseRecyclerUIView
import com.angcyo.uidemo.layout.demo.game.GoldCoinLayer
import com.angcyo.uidemo.layout.demo.game.PacketLayer
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.game.GameRenderView
import com.angcyo.uiview.game.helper.GameRenderHelper
import com.angcyo.uiview.game.layer.BaseFrameLayer
import com.angcyo.uiview.game.layer.BaseTouchLayer
import com.angcyo.uiview.game.layer.OnClickSpiritListener
import com.angcyo.uiview.game.layer.TouchSpiritBean
import com.angcyo.uiview.game.spirit.FrameBean
import com.angcyo.uiview.game.spirit.FrameBgBean
import com.angcyo.uiview.game.spirit.MoveBean
import com.angcyo.uiview.game.spirit.ScaleRandomPointBean
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

    override fun onBackPressed(): Boolean {
        if (view(R.id.game_control_view).visibility == View.VISIBLE) {
            view(R.id.game_control_view).visibility = View.GONE
            return false
        }
        return super.onBackPressed()
    }

    private lateinit var editText: ExEditText
    private lateinit var rainHelper: RainHelper

    private lateinit var gameRenderHelper: GameRenderHelper
    private val packetLayer = PacketLayer()
    private val effectLayer = BaseFrameLayer()
    private val scoreLayer = BaseFrameLayer()
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

                view(R.id.game_control_view).visibility = View.VISIBLE

                packetLayer.apply {
                    reset()
                    randomStep = mViewHolder.cV(R.id.step_box).isChecked
                    useBezier = mViewHolder.cV(R.id.bezier_box).isChecked
                    onClickSpiritListener = object : OnClickSpiritListener {
                        override fun onClickSpirit(baseTouchLayer: BaseTouchLayer, spiritBean: TouchSpiritBean, x: Int, y: Int) {
                            effectLayer.addFrameBean(FrameBean(arrayOf(
                                    getDrawable(R.drawable.hongbao_g_00000),
                                    getDrawable(R.drawable.hongbao_g_00001),
                                    getDrawable(R.drawable.hongbao_g_00002),
                                    getDrawable(R.drawable.hongbao_g_00003),
                                    getDrawable(R.drawable.hongbao_g_00004),
                                    getDrawable(R.drawable.hongbao_g_00005),
                                    getDrawable(R.drawable.hongbao_g_00006),
                                    getDrawable(R.drawable.hongbao_g_00007),
                                    getDrawable(R.drawable.hongbao_g_00008),
                                    getDrawable(R.drawable.hongbao_g_00009),
                                    getDrawable(R.drawable.score_1)
                            ), Point(spiritBean.getRect().centerX(), spiritBean.getRect().centerY())).apply {
                                frameDrawIntervalTime = 60L
                                loopDrawFrame = false
                                onDrawEndFun = { drawPoint ->
                                    scoreLayer.addFrameBean(
                                            MoveBean(arrayOf(getDrawable(R.drawable.score_1)),
                                                    drawPoint,
                                                    Point(gameRenderView.measuredWidth, 0)))
                                }
                            })
                        }
                    }
                }

                if (gameRenderView.layerList.isNotEmpty()) {
                    return@click
                }

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
//                                loopDrawFrame = random.nextBoolean()
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
                addXingLayer(baseFrameLayer)

                gameRenderHelper.addLayer(baseFrameLayer)
                gameRenderHelper.addLayer(packetLayer)//红包
                gameRenderHelper.addLayer(GoldCoinLayer())//金币
                gameRenderHelper.addLayer(effectLayer)//红包炸开效果
                gameRenderHelper.addLayer(scoreLayer)//分数漂移

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

        /*流星的角度*/
        val degrees = 40f

        /*根据开始点和角度, 求出结束点*/
        fun endPoint(startPoint: Point, degrees: Float): Point {
            val endPoint = Point().apply {
                set(((ScreenUtil.screenHeight - startPoint.y - 300) * Math.tan(Math.toRadians(degrees.toDouble()))).toInt(), ScreenUtil.screenHeight - 300)
            }
            return endPoint
        }

        fun delayDrawTime(f: Int = 6) = (random.nextInt(f) * 1000).toLong()
        fun maxMoveTime(f: Int = 3) = (5 + random.nextInt(f)) * 1000

        fun createMBean(id: Int, startPoint: Point, degrees: Float): MoveBean {
            val drawable = getDrawable(id)
            return MoveBean(
                    arrayOf(drawable),
                    startPoint,
                    endPoint(startPoint, degrees)).apply {
                delayDrawTime = delayDrawTime()
                maxMoveTime = maxMoveTime()
                rotateDegrees = -degrees
                isLoopMove = true
                constantSpeed = random.nextBoolean()
            }
        }

        val mb1 = createMBean(R.drawable.liuxing01, Point(0, sh * 1 / 3), degrees)
        val mb2s = Point(0, dp20 * 6)
        val mb2 = object : MoveBean(
                arrayOf(getDrawable(R.drawable.liuxing02)),
                mb2s,
                endPoint(mb2s, degrees)) {
            init {
                maxMoveTime = maxMoveTime(7)
                rotateDegrees = -degrees
                constantSpeed = false
                isLoopMove = true
                scaleX = 0.6f
                scaleY = 0.6f
            }
        }
        val mb3 = createMBean(R.drawable.liuxing01, Point(0, sh * 1 / 7), degrees)
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
                endPoint(mb4s, degrees)) {
            init {
                maxMoveTime = maxMoveTime(8)
                rotateDegrees = -degrees
                constantSpeed = false
                isLoopMove = true
                scaleX = 0.4f
                scaleY = 0.4f
            }
        }

        val mb5 = createMBean(R.drawable.liuxing01, Point(sw / 3, 0), degrees)
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
                endPoint(mb6s, degrees)) {
            init {
                maxMoveTime = maxMoveTime(9)
                rotateDegrees = -degrees
                constantSpeed = random.nextBoolean()
                isLoopMove = true
            }
        }

        val mb7s = Point(sw / 2, 0)
        val mb7 = object : MoveBean(
                arrayOf(getDrawable(R.drawable.liuxing02)),
                mb7s,
                endPoint(mb7s, degrees)) {
            init {
                delayDrawTime = delayDrawTime()
                maxMoveTime = maxMoveTime(7)
                rotateDegrees = -degrees
                constantSpeed = false
                isLoopMove = true
            }
        }

        val mb8s = Point(sw / 3, 0)
        val mb8 = object : MoveBean(
                arrayOf(getDrawable(R.drawable.liuxing02)),
                mb8s,
                endPoint(mb8s, degrees)) {
            init {
                delayDrawTime = delayDrawTime()
                maxMoveTime = maxMoveTime(7)
                rotateDegrees = -degrees
                constantSpeed = random.nextBoolean()
                isLoopMove = true
                scaleX = 0.6f
                scaleY = 0.6f
            }
        }

        hotRainLayer.addFrameBean(mb1)
        hotRainLayer.addFrameBean(mb2)
        hotRainLayer.addFrameBean(mb3)
        hotRainLayer.addFrameBean(mb4)
        hotRainLayer.addFrameBean(mb5)
        hotRainLayer.addFrameBean(mb6)
        hotRainLayer.addFrameBean(mb7)
        hotRainLayer.addFrameBean(mb8)

    }

    /**山*/
    private fun addMarkLayer(hotRainLayer: BaseFrameLayer) {
        hotRainLayer.addFrameBean(object : FrameBgBean(getDrawable(R.drawable.shan_bg)) {
            override fun draw(canvas: Canvas, gameStartTime: Long, lastRenderTime: Long, nowRenderTime: Long, onDrawEnd: (() -> Unit)?) {
                bgDrawable.let {
                    it.bounds.set(parentRect.left, parentRect.bottom - bgDrawable.intrinsicHeight, parentRect.right, parentRect.bottom)
                    it.draw(canvas)
                }
            }
        })
    }

    /**星星*/
    private fun addXingLayer(hotRainLayer: BaseFrameLayer) {
        val scale1 = arrayOf(0.2f, 0.4f, 0.6f, 0.8f, 1f, 1.2f, 1f, 0.8f, 0.6f, 0.4f, 0.2f, 0f)
        val scale2 = arrayOf(0.2f, 0.4f, 0.6f, 0.8f, 0.8f, 0.6f, 0.4f, 0.2f, 0f)
        val scale3 = arrayOf(0.2f, 0.4f, 0.6f, 0.8f, 1f, 1.2f, 1f, 1f, 1.2f, 1f, 0.8f, 0.6f, 0.4f, 0.2f, 0f)
        val scale4 = arrayOf(0.2f, 0.4f, 0.6f, 0.8f, 1f, 1.2f, 1.4f, 1.4f, 1.2f, 1f, 0.8f, 0.6f, 0.4f, 0.2f, 0f)
        val scale5 = arrayOf(0.2f, 0.6f, 1.2f, 1.8f, 2.5f, 3f, 2.5f, 1.8f, 1.2f, 0.6f, 0.2f)
        val scale6 = arrayOf(0.2f, 0.6f, 1.2f, 1.6f, 2f, 2.5f, 2f, 1.6f, 1.2f, 0.6f, 0.2f)

        hotRainLayer.addFrameBean(ScaleRandomPointBean(scale1, getDrawable(R.drawable.xing01)))
        hotRainLayer.addFrameBean(ScaleRandomPointBean(scale2, getDrawable(R.drawable.xing02)))

        hotRainLayer.addFrameBean(ScaleRandomPointBean(scale3, getDrawable(R.drawable.xing01)))
        hotRainLayer.addFrameBean(ScaleRandomPointBean(scale1, getDrawable(R.drawable.xing02)))

        hotRainLayer.addFrameBean(ScaleRandomPointBean(scale4, getDrawable(R.drawable.xing01)))
        hotRainLayer.addFrameBean(ScaleRandomPointBean(scale3, getDrawable(R.drawable.xing02)))

        hotRainLayer.addFrameBean(ScaleRandomPointBean(scale4, getDrawable(R.drawable.xing01)))
        hotRainLayer.addFrameBean(ScaleRandomPointBean(scale2, getDrawable(R.drawable.xing02)))

        hotRainLayer.addFrameBean(ScaleRandomPointBean(scale5, getDrawable(R.drawable.xing01)))
        hotRainLayer.addFrameBean(ScaleRandomPointBean(scale5, getDrawable(R.drawable.xing01)))

        hotRainLayer.addFrameBean(ScaleRandomPointBean(scale6, getDrawable(R.drawable.xing02)))
        hotRainLayer.addFrameBean(ScaleRandomPointBean(scale6, getDrawable(R.drawable.xing02)))
    }

}
