package com.angcyo.uidemo.layout

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.View
import com.angcyo.library.utils.L
import com.angcyo.uidemo.NavUIView
import com.angcyo.uidemo.R
import com.angcyo.uidemo.kotlin.cls.SingleClass2
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uidemo.layout.demo.*
import com.angcyo.uidemo.layout.qq.QQGuideAnimationUIDemo
import com.angcyo.uidemo.refresh.RefreshLayoutDemo
import com.angcyo.uidemo.uiview.ScrollerIView
import com.angcyo.uidemo.uiview.TestDemo
import com.angcyo.uiview.Root
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.base.UIBaseView
import com.angcyo.uiview.container.UIParam
import com.angcyo.uiview.dialog.UIProgressDialog
import com.angcyo.uiview.dynamicload.DynamicLoadUIView
import com.angcyo.uiview.kotlin.v
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.net.Rx
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.RUtils
import com.angcyo.uiview.utils.T_
import com.angcyo.uiview.utils.Tip
import com.angcyo.uiview.view.IView
import com.angcyo.uiview.view.IViewAnimationType
import com.angcyo.uiview.view.OnUIViewListener
import com.angcyo.uiview.widget.ItemInfoLayout
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import java.util.concurrent.TimeUnit

/**
 * Created by angcyo on 2017-03-13.
 */
class DemoListUIView2 : BaseItemUIView() {

    internal var progress = 0
    internal var clickCount = 0
    private var mSubscribe: Subscription? = null
    private var mProgressDialog: UIProgressDialog? = null

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar()
                .setShowBackImageView(false)
                .setShowTitleBarBottomShadow(true)
    }

    //    @Override
    //    public int getDefaultBackgroundColor() {
    //        return Color.GREEN;
    //    }

    override fun getTitleString(): String {
        return mActivity.title.toString()
    }

    override fun enableTouchBack(): Boolean {
        return false
    }

    override fun onViewShowFirst(bundle: Bundle?) {
        super.onViewShowFirst(bundle)
        //mRootView.setEnabled(false);
        //showLoadView();

        //魅族自动识别状态栏颜色的BUG
        lightStatusBar(false)
    }

    override fun onViewUnload(uiParam: UIParam) {
        super.onViewUnload(uiParam)
        injectCancelPluginPackage()
    }

    override fun getItemLayoutId(viewType: Int): Int {
        injectPluginPackage()
        return if (isLast(viewType)) {
            R.layout.item_version_layout
        } else R.layout.item_demo_list_layout
    }

    //    @Override
    //    protected View createItemView(ViewGroup parent, int position) {
    //        if (isInPlugin()) {
    //            if (isLast(position)) {
    //                return mPluginPackage.inflate(mActivity, R.layout.item_version_layout, parent, false);
    //            } else {
    //                return mPluginPackage.inflate(mActivity, R.layout.item_demo_list_layout, parent, false);
    //            }
    //        }
    //        return super.createItemView(parent, position);
    //    }

    internal fun initItem(holder: RBaseViewHolder, itemText: String, onClickListener: View.OnClickListener) {
        initItem(holder, itemText, false, onClickListener)
    }

    internal fun initItem(holder: RBaseViewHolder, itemText: String, isDeprecated: Boolean, onClickListener: View.OnClickListener) {
        val infoLayout = holder.v<ItemInfoLayout>(R.id.item_info_layout)
        infoLayout.setItemText(itemText)

        val textView = infoLayout.textView
        textView.setDeleteLine(isDeprecated)
        holder.click(infoLayout, onClickListener)
    }

    internal fun onKotlinDemoClick() {
        lightStatusBar(false)
        if (Build.MODEL.contains("OPPO") || Build.MODEL.contains("ONEPLUS")) {
            T_.info("友情提示\n请关闭 '三指截屏'")
            Tip.tip("友情提示\n请关闭 '三指截屏'")
        }
//
//        for (a in 0..9) {
//            L.e((a shl 3).toString() + " -> " + a * 8)
//        }
//
//        val s1 = "我"
//        val s2 = String("我")
//        L.e("in java-> " + ("我" === "我") + (s1 === s2) + (s1 === "我") + (s2 === "我"))


        //onSynchronizedDemo(true, "")

        var str: String? = null
        var log = str?.length?.let {
            it + 1
        } ?: "is empty"

        L.e("call: onKotlinDemoClick1 -> $log")

        str = "angcyo"
        log = str?.length?.let {
            it + 1
        } ?: "is empty"

        L.e("call: onKotlinDemoClick2 -> $log")
    }

    internal fun onSynchronizedDemo(runThread: Boolean, tag: String) {
        L.e("call: onSynchronizedDemo([])-> synchronized start $tag")

        if (runThread) {
            Thread(Runnable {
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                onSynchronizedDemo(false, " thread")
            }).start()
        }

        synchronized(this) {
            try {
                Thread.sleep(200)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }

        L.e("call: onSynchronizedDemo([])-> synchronized end $tag")
    }

    override fun getItemsInitialSize(): Int {
        return 100
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem(SingleItem.Type.TOP) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Scroller IView",
                        View.OnClickListener { startIView(ScrollerIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Refresh Layout Demo",
                        View.OnClickListener { startIView(RefreshLayoutDemo()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Test Demo",
                        View.OnClickListener { startIView(TestDemo()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".RTL Demo",
                        View.OnClickListener { startIView(RTLUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Emoji Layout Demo (Android5.+)",
                        true, View.OnClickListener { startIView(EmojiUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".ExEmoji Layout Demo",
                        View.OnClickListener { startIView(ExEmojiUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Swipe RecyclerUIView Demo",
                        View.OnClickListener { startIView(SwipeRecyclerViewUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Link Url And LaunchMode Demo",
                        View.OnClickListener { startIView(NavUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Center Button Demo + Video",
                        View.OnClickListener { startIView(CenterRadioButtonUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Animator Demo + Math",
                        View.OnClickListener { startIView(AnimatorDemoUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Loop RecyclerUIView Demo",
                        View.OnClickListener { startIView(LoopRecyclerViewUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".@ Mention Demo",
                        View.OnClickListener { startIView(MentionDemoUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Stick Layout Demo (Beta)",
                        true, View.OnClickListener { startIView(StickLayoutDemoUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Stick Layout Demo2",
                        View.OnClickListener { startIView(StickLayoutDemo2UIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Behavior Stick Demo",
                        true, View.OnClickListener { startIView(BehaviorStickDemoUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Behavior Stick Demo2 (Deprecated)",
                        true, View.OnClickListener { startIView(BehaviorStickDemoUIView2()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Stick LayoutManager Demo",
                        View.OnClickListener { startIView(StickLayoutManagerUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".GitHub Project Demo",
                        View.OnClickListener { startIView(GithubDemoUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Game CircleLayout Demo (Beta)",
                        View.OnClickListener { startIView(GameCircleLayoutUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Touch Event Demo",
                        View.OnClickListener { startIView(TouchEventDemoUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Ripple Drawable Demo",
                        View.OnClickListener { startIView(RippleDrawableDemoUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".RRecyclerView Demo",
                        View.OnClickListener {
                            val value = clickCount % 3
                            if (value == 0) {
                                startIView(RRecyclerViewDemoUIView3())
                            } else if (value == 1) {
                                startIView(RRecyclerViewDemoUIView())
                            } else if (value == 2) {
                                startIView(RRecyclerViewDemoUIView2())
                            }
                            clickCount++
                        })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Math Path And Paint Demo", View.OnClickListener { startIView(MathPathUIView()) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Span Demo",
                        View.OnClickListener { v -> startIView(SpanUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Custom View Demo",
                        View.OnClickListener { v -> startIView(CustomViewUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Custom View Demo2",
                        View.OnClickListener { v -> startIView(CustomViewUIView2().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".RSwipeRecycler View Demo",
                        View.OnClickListener { v -> startIView(RSwipeRecyclerUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Kotlin 1.1 Demo",
                        View.OnClickListener {
                            onKotlinDemoClick()

                            KtDemo.main()

                            shakeView()

                            //Tip.show("测试文本", R.drawable.hot_package_1);

                            //new ktclass().test();
                            //KtinterfaceKt.ktinterface_fun();
                            //KtfileKt.getN();
                            //KtinterfaceKt.fun_test(DemoListUIView2.this);

                            //                        new ktclass.ktsubclass();

                            //                        int num = SingleClass.Holder.getInstance().getNum();
                            //                        SingleClass.Holder.getInstance().setNum(2);
                            //                        num = SingleClass.Holder.getInstance().getNum();
                            //                        num = SingleClass.Holder.getInstance().getNum();
                            //
                            //                        SingleClass.Holder.getIns().getNum();

                            //int num = SingleClass.Companion.getInstance().getNum();
                            //SingleClass2.INSTANCE.getInstance()

                            //                        String num = SingleClass2.INSTANCE.getInstance().getNum();
                            //                        SingleClass2.INSTANCE.getInstance().setNum("1");
                            //                        num = SingleClass2.INSTANCE.getInstance().getNum();
                            //
                            //                        SingleClass2.INSTANCE.getInstance().setNum("122222");
                            //                        num = SingleClass2.INSTANCE.getInstance().getNum();

                            var num = SingleClass2.num
                            L.e("call: onClick([v])-> $num")
                            SingleClass2.num = 2
                            num = SingleClass2.num
                            L.e("call: onClick([v])-> $num")

                            val test = "http://www.baidu.com/1.jpg?param=a?http://www.jd.com/ui.mp4"
                            try {
                                val substring = test.substring(test.lastIndexOf('?'), test.length)
                                val substring1 = test.substring(0, test.lastIndexOf('?'))

                                L.e("call: onClick([v])-> $substring $substring1")
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                            var numTest = 12347980981235L
                            L.e(numTest.toString() + " : 缩短后-> " + RUtils.getShortString(numTest))
                            numTest = 78983L
                            L.e(numTest.toString() + " : 缩短后-> " + RUtils.getShortString(numTest))
                            numTest = 87654321L
                            L.e(numTest.toString() + " : 缩短后-> " + RUtils.getShortString(numTest))
                            numTest = 4300L
                            L.e(numTest.toString() + " : 缩短后-> " + RUtils.getShortString(numTest))

                            val view = View(mActivity)
                            val v1 = view.v<View>(R.id.text_view)

                            L.e("call: onClick([v])-> " + v1)

                            //                        ResUtil.setGreyscale(getILayout(), true);
                            //                        postDelayed(2000, new Runnable() {
                            //                            @Override
                            //                            public void run() {
                            //                                ResUtil.setGreyscale(getILayout(), false);
                            //                            }
                            //                        });
                            //
                            //                        //xingqiuqp://null/null?GameID=null&RoomID=860101
                            //                        //String url = "xingqiuqp://null/null?GameID=null&RoomID=860101";
                            //                        //RUtils.openAppFromUrl(mActivity, url);
                            //
                            //                        AppUtils.installApp(RApplication.getApp(), new File("/sdcard/DValley/apk/2.2.3003.apk"));
                            //
                            //                        L.e("call: onClick([v])-> 1::" + RUtils.decimal(2f / 3f, 2, false));
                            //                        L.e("call: onClick([v])-> 2::" + RUtils.decimal(2f / 3f, 2, true));
                        })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Notify Demo",
                        View.OnClickListener { v -> startIView(NotifyDemoUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Finger Printer Demo",
                        View.OnClickListener { v -> startIView(FingerPrinterUIDemo().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".QQ  Navigation Demo",
                        View.OnClickListener { v -> startIView(QQNavigationUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Group ItemDecoration Demo",
                        View.OnClickListener { v -> startIView(GroupItemDecorationUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".ExpandRecordLayout Demo",
                        View.OnClickListener { v -> startIView(ExpandRecordLayoutUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".DYRecordView Demo",
                        View.OnClickListener { v -> startIView(DYRecordLayoutUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".CursorLoader Demo",
                        View.OnClickListener { v -> startIView(CursorLoaderUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".R Media Loader Demo",
                        View.OnClickListener { v -> startIView(RMediaLoaderDemoUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".PopupWindow Demo",
                        View.OnClickListener { v -> startIView(PopupWindowUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".PasswordInput Demo",
                        View.OnClickListener { v -> startIView(PasswordInputUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".ProgressDialog Demo",
                        View.OnClickListener {
                            mProgressDialog = UIProgressDialog.build()
                            mProgressDialog!!.setDimBehind(false).addDismissListener {
                                progress = 0
                                mSubscribe!!.unsubscribe()
                                mProgressDialog = null
                            }.showDialog(mParentILayout)

                            mSubscribe = Rx.interval(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                                    .subscribe { aLong ->
                                        L.e("call: call([aLong])-> " + aLong!!)
                                        mProgressDialog!!.setProgress(progress)

                                        progress++
                                        if (progress > 100) {
                                            mSubscribe!!.unsubscribe()
                                        } else if (progress > 80) {
                                            mProgressDialog!!.setTipText("")
                                        } else if (progress > 50) {
                                            mProgressDialog!!.setTipText("视频处理中")
                                        }
                                    }
                        })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".ContentState Demo",
                        View.OnClickListener { v -> startIView(ContentStateUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Dice Card Demo",
                        View.OnClickListener { v -> startIView(DiceCardUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".ConstraintLayout Demo", View.OnClickListener { v ->
                    startIView(ConstraintLayoutUIView()
                            .setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)
                            .setOnUIViewListener(object : OnUIViewListener() {
                                override fun onViewUnload(uiview: IView) {
                                    super.onViewUnload(uiview)
                                    L.e("call: onViewUnload([uiview])-> ConstraintLayoutUIView...")
                                }
                            }))
                })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".StickTopLayout Demo", View.OnClickListener { v ->
                    startIView(StickTopLayoutUIDemo()
                            .setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)
                            .setOnUIViewListener(object : OnUIViewListener() {
                                override fun onViewUnload(uiview: IView) {
                                    super.onViewUnload(uiview)
                                    L.e("call: onViewUnload([uiview])-> ConstraintLayoutUIView...")
                                }
                            }))
                })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".SliderLayout Demo",
                        View.OnClickListener { v -> startIView(SliderLayoutUIDemo().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".TouchStickLayout_d Demo",
                        true, View.OnClickListener { v -> startIView(TouchLayoutUIDemo().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".TouchBackLayout Demo",
                        false, View.OnClickListener { v -> startIView(TouchBackUIDemo().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".RippleView Demo",
                        false, View.OnClickListener { v -> startIView(RippleViewUIDemo().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Mp3Play Demo",
                        false, View.OnClickListener { v -> startIView(Mp3PlayUIDemo().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".X5WebView Demo",
                        false, View.OnClickListener { v -> startIView(X5WebViewUIDemo().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".InputText Demo",
                        false, View.OnClickListener { v -> startIView(InputTextUIDemo().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Regular Test Demo",
                        false, View.OnClickListener { v -> startIView(RegularTestUIDemo().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".RGroupAdapter Demo",
                        false, View.OnClickListener { v -> startIView(RGroupAdapterUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".Guide Layout Demo",
                        false, View.OnClickListener { v -> startIView(GuideLayoutUIView(v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".WebSocket Demo",
                        false, View.OnClickListener { v -> startIView(WebsocketUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".FileObserver Demo",
                        false, View.OnClickListener { v -> startIView(FileObserverUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".GalleryLayout Demo",
                        false, View.OnClickListener { v -> startIView(GalleryLayoutUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".App Info Demo",
                        false, View.OnClickListener { v -> startIView(AppInfoDemoUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".RV LayoutManager Demo",
                        false, View.OnClickListener { v -> startIView(RVLayoutManagerUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })

        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".ZxingZbar Scan Demo",
                        false, View.OnClickListener { startIView(MyScanUIView2(Action1 { s -> startIView(ScanResultUIView(s)) })/*.setEnableClipMode(ClipMode.CLIP_BOTH, v)*/) })
            }
        })

        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".UIChat Demo",
                        false, View.OnClickListener { startIView(UIChatDemo().setAnimationType(IViewAnimationType.ALPHA)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".UIChat With ViewPager Demo",
                        false, View.OnClickListener { startIView(UIChatViewPagerDemo().setAnimationType(IViewAnimationType.TRANSLATE_VERTICAL)) })
            }
        })

        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".UIChat TabLayout Demo",
                        false, View.OnClickListener { startIView(UIChatTabLayoutDemo().setAnimationType(IViewAnimationType.NONE)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".UIChat TabLayout With ViewPager Demo",
                        false, View.OnClickListener { startIView(UIChatTabPagerLayoutDemo().setAnimationType(IViewAnimationType.NONE)) })
            }
        })

        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".QQ Guide Animation Demo",
                        false, View.OnClickListener { v -> startIView(QQGuideAnimationUIDemo().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, v)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".File Download Demo",
                        false, View.OnClickListener { startIView(FileDownLoadUIView.get().setAnimationType(IViewAnimationType.SCALE_TO_MAX)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                initItem(holder, (posInData + 1).toString() + ".DynamicLoad Demo",
                        false, View.OnClickListener { startIView(DynamicLoadUIView().setAnimationType(IViewAnimationType.SCALE_TO_MAX_AND_END)) })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                initItem(holder, (posInData + 1).toString() + ".Slider Menu Demo",
                        false, View.OnClickListener {
                    startIView(SliderMenuUIView().setAnimationType(IViewAnimationType.ALPHA))
                })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                initItem(holder, (posInData + 1).toString() + ".Item Decoration Demo",
                        false, View.OnClickListener {
                    startIView(ItemDecorationUIDemo().setAnimationType(IViewAnimationType.NONE))
                })
            }
        })
//        items.add(object : SingleItem(SingleItem.Type.LINE) {
//            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
//                initItem(holder, (posInData + 1).toString() + ".RViewGroup Demo",
//                        false, View.OnClickListener {
//                    startIView(RViewGroupUIDemo().setAnimationType(IViewAnimationType.TRANSLATE_VERTICAL))
//                })
//            }
//        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                initItem(holder, (posInData + 1).toString() + ".IP Mac Network Demo",
                        false, View.OnClickListener {
                    startIView(IpMacNetworkUIDemo().setAnimationType(IViewAnimationType.ALPHA))
                })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                initItem(holder, (posInData + 1).toString() + ".CircleImageView Demo",
                        false, View.OnClickListener {
                    startIView(CircleImageViewUIDemo().setAnimationType(IViewAnimationType.TRANSLATE_VERTICAL))
                })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                initItem(holder, (posInData + 1).toString() + ".RTabLayout Demo",
                        false, View.OnClickListener {
                    startIView(RTabLayoutUIDemo().setEnableClipMode(ClipMode.CLIP_BOTH, it))
                })
            }
        })
        items.add(object : SingleItem(SingleItem.Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                initItem(holder, (posInData + 1).toString() + ".TelephonyManager Demo",
                        false, View.OnClickListener {
                    startIView(TelephonyManagerUIDemo().setEnableClipMode(ClipMode.CLIP_BOTH, it))
                })
            }
        })

        //版本 编译时间
        items.add(object : SingleItem(SingleItem.Type.LINE) {

            override fun setItemOffsets(rect: Rect) {
                super.setItemOffsets(rect)
                rect.bottom = getDimensionPixelOffset(R.dimen.base_xhdpi)
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                holder.tv(R.id.text_view).text = Root.device_info(mActivity)

                holder.itemView.setOnClickListener {
                    startIView(MyScanUIView(Action1 { s ->
                        //T_.info("扫码结果:" + s);
                        startIView(ScanResultUIView(s))
                    }))
                }

                holder.itemView.setOnLongClickListener {
                    Tip.tip("正在模拟Crash")
                    val i = 1 / 0
                    false
                }
            }
        })

        //Rx.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
        //        .map(new Func1<Long, String>() {
        //            @Override
        //            public String call(Long s) {
        //                L.e("call: call([s])-> " + s);
        //                return "";
        //            }
        //        })
        //        .subscribe(new RSubscriber<String>() {
        //            @Overrides
        //            public void onSucceed(String bean) {
        //                super.onSucceed(bean);
        //            }
        //        });
    }
}
