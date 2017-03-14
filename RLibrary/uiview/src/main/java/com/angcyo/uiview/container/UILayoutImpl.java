package com.angcyo.uiview.container;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.base.UILayoutActivity;
import com.angcyo.uiview.model.ViewPattern;
import com.angcyo.uiview.resources.AnimUtil;
import com.angcyo.uiview.view.ILifecycle;
import com.angcyo.uiview.view.IView;
import com.angcyo.uiview.view.UIIViewImpl;
import com.angcyo.uiview.widget.viewpager.UIViewPager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import static com.angcyo.uiview.view.UIIViewImpl.DEFAULT_ANIM_TIME;

/**
 * 可以用来显示IView的布局, 每一层的管理
 * Created by angcyo on 2016-11-12.
 */

public class UILayoutImpl extends SwipeBackLayout implements ILayout<UIParam>, UIViewPager.OnPagerShowListener {

    private static final String TAG = "UILayoutWrapper";
    /**
     * 已经追加到内容层的View
     */
    protected Stack<ViewPattern> mAttachViews = new Stack<>();
    /**
     * 最前显示的View
     */
    protected ViewPattern mLastShowViewPattern;
    protected boolean isAttachedToWindow = false;
    protected UILayoutActivity mLayoutActivity;
    Application.ActivityLifecycleCallbacks mCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (activity == mLayoutActivity) {
                if (mLastShowViewPattern != null
                        && mLastShowViewPattern.mView.getVisibility() != VISIBLE) {
                    viewShow(mLastShowViewPattern, null);
                }
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
//            if (activity == mLayoutActivity) {
//                viewShow(mLastShowViewPattern, null);
//            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
//            if (activity == mLayoutActivity) {
//                viewHide(mLastShowViewPattern);
//            }
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (activity == mLayoutActivity) {
                if (mLastShowViewPattern != null
                        && mLastShowViewPattern.mView.getVisibility() == VISIBLE) {
                    viewHide(mLastShowViewPattern);
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };
    /**
     * 是否正在退出
     */
    private boolean isFinishing = false;
    /**
     * 按下返回键
     */
    private boolean isBackPress = false;
    private ArrayList<IWindowInsetsListener> mIWindowInsetsListeners;
    private ArrayList<OnIViewChangedListener> mOnIViewChangedListeners = new ArrayList<>();
    private int[] mInsets = new int[4];
    /**
     * 锁定高度, 当键盘弹出的时候, 可以不改变size
     */
    private boolean lockHeight = false;
    private float mTranslationOffsetX;
    /**
     * 记录有多少个Runnable任务在执行
     */
    private int runnableCount = 0;
    /**
     * 如果只剩下最后一个View, 是否激活滑动删除
     */
    private boolean enableRootSwipe = false;
    /**
     * 是否正在拖拽返回.
     */
    private boolean isSwipeDrag = false;

    /**
     * 是否需要滑动返回, 如果正在滑动返回,则阻止onLayout的进行
     */
    private boolean isWantSwipeBack = false;
    /**
     * 需要中断IView启动的的列表
     */
    private Set<IView> interruptSet;

    public UILayoutImpl(Context context) {
        super(context);
        initLayout();
    }

    public UILayoutImpl(Context context, IView iView) {
        super(context);
        initLayout();
        startIView(iView, new UIParam(false));
    }

    public UILayoutImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public UILayoutImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UILayoutImpl(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initLayout();
    }

    /**
     * inflate之后, 有时会返回 父布局, 这个时候需要处理一下, 才能拿到真实的RootView.
     */
    public static View safeAssignView(final View parentView, final View childView) {
        if (parentView == childView) {
            if (parentView instanceof ViewGroup) {
                final ViewGroup viewGroup = (ViewGroup) parentView;
                return viewGroup.getChildAt(viewGroup.getChildCount() - 1);
            }
            return childView;
        } else {
            return childView;
        }
    }

    /**
     * 滑动返回处理
     */
    @Override
    protected boolean canTryCaptureView(View child) {
        if (mLastShowViewPattern == null || mLastShowViewPattern.isAnimToStart) {
            return false;
        }

        if (mAttachViews.size() > 1) {
            if (!mLastShowViewPattern.mIView.isDialog()//最前的不是对话框
                    && mLastShowViewPattern.mIView.canTryCaptureView()//激活滑动关闭
                    && mLastShowViewPattern.mView == child) {
                hideSoftInput();
                return true;
            } else {
                return false;
            }
        } else if (enableRootSwipe) {
            hideSoftInput();
            return true;
        }
        return false;
    }

    public void setEnableRootSwipe(boolean enableRootSwipe) {
        this.enableRootSwipe = enableRootSwipe;
    }

    private void initLayout() {
        mLayoutActivity = (UILayoutActivity) getContext();
        interruptSet = new HashSet<>();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mLayoutActivity.getApplication().registerActivityLifecycleCallbacks(mCallbacks);
        setFocusable(true);
        setFocusableInTouchMode(true);
        post(new Runnable() {
            @Override
            public void run() {
                isAttachedToWindow = true;
                loadViewInternal();
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mLayoutActivity.getApplication().unregisterActivityLifecycleCallbacks(mCallbacks);
        isAttachedToWindow = false;
        unloadViewInternal();
    }

    /**
     * 卸载IView
     */
    protected void unloadViewInternal() {
        for (; !mAttachViews.isEmpty(); ) {
            final ViewPattern viewPattern = mAttachViews.pop();
            viewHide(viewPattern);
            viewPattern.mIView.onViewUnload();
            try {
                removeView(viewPattern.mView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void startIView(final IView iView, final UIParam param) {
        L.d("请求启动:" + iView.getClass().getSimpleName());
        iView.onAttachedToILayout(this);

        /**已经被中断启动了*/
        if (interruptSet.contains(iView)) {
            interruptSet.remove(iView);
            L.d("请求启动:" + iView.getClass().getSimpleName() + " --启动被中断!");
            return;
        }

        runnableCount++;
        final Runnable endRunnable = new Runnable() {
            @Override
            public void run() {
                startInner(iView, param);
                runnableCount--;
            }
        };
        if (isFinishing || (mLastShowViewPattern != null && mLastShowViewPattern.mIView.isDialog())) {
            //如果在对话框上,启动一个IView的时候
            runnableCount--;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    isFinishing = false;
                    startIView(iView, param);
                }
            }, UIIViewImpl.DEFAULT_ANIM_TIME);
        } else {
            if (param.mAsync) {
                post(endRunnable);
            } else {
                endRunnable.run();
            }
        }
    }

    private void startInner(final IView iView, final UIParam param) {
        final ViewPattern oldViewPattern = getLastViewPattern();

        if (param.start_mode == UIParam.SINGLE_TOP) {
            if (oldViewPattern != null && oldViewPattern.mIView == iView) {
                //如果已经是最前显示, 调用onViewShow方法
                oldViewPattern.mIView.onViewShow(param.mBundle);
            } else {
                ViewPattern viewPatternByIView = findViewPatternByClass(iView.getClass());
                if (viewPatternByIView == null) {
                    //这个IView 还不存在
                    final ViewPattern newViewPattern = startIViewInternal(iView, param);
//                    startIViewAnim(oldViewPattern, newViewPattern, param);
                    viewPatternByIView = newViewPattern;
                    startIViewAnim(oldViewPattern, viewPatternByIView, param, false);

                } else {
                    //这个IView 存在, 但是不在最前显示
//                    bottomViewFinish(oldViewPattern, viewPatternByIView, param);
//                    topViewStart(viewPatternByIView, param);

                    mAttachViews.remove(viewPatternByIView);
                    mAttachViews.push(viewPatternByIView);
                    startIViewAnim(oldViewPattern, viewPatternByIView, param, true);
                }
            }
        } else {
            //正常的启动模式
            final ViewPattern newViewPattern = startIViewInternal(iView, param);
            startIViewAnim(oldViewPattern, newViewPattern, param, false);
        }
    }

    @Override
    public void startIView(IView iView) {
        startIView(iView, new UIParam());
    }

    private ViewPattern startIViewInternal(final IView iView, UIParam param) {
        hideSoftInput();

        iView.onAttachedToILayout(this);

        //1:inflateContentView, 会返回对应IView的RootLayout
        View rawView = loadViewInternal(iView, param);
        //2:loadContentView
        iView.loadContentView(rawView);

        final ViewPattern newViewPattern = new ViewPattern(iView, rawView);
        mAttachViews.push(newViewPattern);

        for (OnIViewChangedListener listener : mOnIViewChangedListeners) {
            listener.onIViewAdd(this, newViewPattern);
        }

        return newViewPattern;
    }

    /**
     * [add] 星期三 2017-1-11
     */
    private ViewPattern startIViewInternal(final ViewPattern viewPattern) {
        hideSoftInput();

        IView iView = viewPattern.mIView;

        iView.onAttachedToILayout(this);

        //1:inflateContentView, 会返回对应IView的RootLayout
        View rawView = loadViewInternal(iView, null);
        //2:loadContentView
        iView.loadContentView(rawView);

        viewPattern.setView(rawView);
        mAttachViews.push(viewPattern);

        for (OnIViewChangedListener listener : mOnIViewChangedListeners) {
            listener.onIViewAdd(this, viewPattern);
        }

        return viewPattern;
    }

    /**
     * 加载所有添加的IView
     */
    protected void loadViewInternal() {
        ViewPattern lastViewPattern = null;
        for (ViewPattern viewPattern : mAttachViews) {
            if (lastViewPattern != null) {
                viewHide(lastViewPattern);
            }
            lastViewPattern = viewPattern;
            lastViewPattern.mIView.onViewLoad();//1:
        }

        if (lastViewPattern != null) {
            viewShow(lastViewPattern, null);
        }
        mLastShowViewPattern = lastViewPattern;
    }

    /**
     * 加载IView
     */
    private View loadViewInternal(IView iView, UIParam uiParam) {

        //首先调用IView接口的inflateContentView方法,(inflateContentView请不要初始化View)
        //其次会调用loadContentView方法,用来初始化View.(此方法调用之后, 就支持ButterKnife了)
        //1:
        final View view = iView.inflateContentView(mLayoutActivity, this, this, LayoutInflater.from(mLayoutActivity));

        View rawView;

        //返回真实的RootView, 防止连续追加2个相同的IView之后, id重叠的情况
        if (this == view) {
            rawView = getChildAt(getChildCount() - 1);
        } else {
            rawView = view;
        }

        //2:
        iView.onViewCreate(view);
        iView.onViewCreate(view, uiParam);

        return rawView;
    }

    @Override
    public void finishIView(final View view, final boolean needAnim) {
        if (view == null) {
            return;
        }
        final ViewPattern viewPatternByView = findViewPatternByView(view);
        if (viewPatternByView == null) {
//            post(new Runnable() {
//                @Override
//                public void run() {
//                    finishIView(view, needAnim);
//                }
//            });
            return;
        }
        finishIView(viewPatternByView.mIView, new UIParam(needAnim, false, false));
    }

    /**
     * @param param isQuiet 如果为true, 上层的视图,将取消生命周期 {@link IView#onViewShow()}  的回调
     */
    private void finishIViewInner(final ViewPattern viewPattern, final UIParam param) {
        if (viewPattern == null || viewPattern.isAnimToEnd) {
            isFinishing = false;
            return;
        }

        L.d("请求关闭2:" + viewPattern.toString() + " isFinishing:" + isFinishing);

        ViewPattern lastViewPattern = findLastShowViewPattern(viewPattern);

        if (!viewPattern.interrupt) {
            if (viewPattern.isAnimToStart || isFinishing) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        finishIViewInner(viewPattern, param);
                    }
                });
                return;
            }
        }

        if (param.isSwipeBack) {

        } else {
            /*对话框的处理*/
            if (viewPattern.mIView.isDialog() &&
                    !viewPattern.mIView.canCancel()) {
                isFinishing = false;
                return;
            }
        }


//        if (!param.isSwipeBack && !viewPattern.mIView.onBackPressed()) {
//            //如果不是滑动返回, 并且不能退出
//            isFinishing = false;
//            return;
//        }

        topViewFinish(lastViewPattern, viewPattern, param.mAnim);
        bottomViewStart(lastViewPattern, viewPattern, param.mAnim, param.isQuiet);

//        if (param.mAnim) {
//            viewPattern.isAnimToEnd = true;
//            //startViewPatternAnim(viewPattern, lastViewPattern, true, true);
//            //startViewPatternAnim(viewPattern, lastViewPattern, false, false);
//            topViewFinish(viewPattern, true);
//            bottomViewStart(lastViewPattern, viewPattern, param.mAnim, param.isQuiet);
//        } else {
//            if (lastViewPattern != null) {
//
//                if (!param.isQuiet) {
//                    viewShow(lastViewPattern, null);
//                }
//
//                if (lastViewPattern.mView instanceof ILifecycle) {
//                    ((ILifecycle) lastViewPattern.mView).onLifeViewShow();
//                }
//            }
//            removeViewPattern(viewPattern);
//        }

        //mLastShowViewPattern = lastViewPattern;//在top view 完全remove 之后, 再赋值
    }

    @Override
    public void finishIView(View view) {
        finishIView(view, true);
    }

    @Override
    public void finishIView(IView iview) {
        finishIView(iview, true);
    }

    @Override
    public void finishIView(IView iview, boolean needAnim) {
        finishIView(iview, needAnim, false);
    }

    @Override
    public void finishIView(IView iview, boolean needAnim, boolean quiet) {
        finishIView(iview, new UIParam(needAnim, false, quiet));
    }

    @Override
    public void finishIView(final IView iview, final UIParam param) {
        if (iview == null) {
            isBackPress = false;
            return;
        }
        L.d("请求关闭/中断:" + iview.getClass().getSimpleName());
        interruptSet.add(iview);

        final ViewPattern viewPattern = findViewPatternByIView(iview);
        if (viewPattern != null) {
            viewPattern.interrupt = true;//中断启动
        }

        if (mLastShowViewPattern != null && mLastShowViewPattern != viewPattern && mLastShowViewPattern.mIView.isDialog()) {
            L.d("等待对话框:" + mLastShowViewPattern.mIView.getClass().getSimpleName() + " 的关闭");
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    finishIView(iview, param);
                }
            }, DEFAULT_ANIM_TIME);
            return;
        }

        final Runnable endRunnable = new Runnable() {
            @Override
            public void run() {
                finishIViewInner(viewPattern, param);
            }
        };

        if (param.mAsync) {
            post(endRunnable);
            return;
        }
        endRunnable.run();
    }

    @Override
    public void showIView(View view) {
        showIView(view, true);
    }

    @Override
    public void showIView(final View view, final boolean needAnim) {
        showIView(view, needAnim, null);
    }

    @Override
    public void showIView(final View view, final boolean needAnim, final Bundle bundle) {
        if (view == null) {
            return;
        }

        post(new Runnable() {
            @Override
            public void run() {
                final ViewPattern viewPattern = findViewPatternByView(view);
                showIViewInternal(viewPattern, needAnim, bundle);
            }
        });
    }

    @Override
    public void showIView(IView iview, boolean needAnim) {
        showIView(iview, needAnim, null);
    }

    @Override
    public void showIView(IView iview) {
        showIView(iview, true);
    }

    @Override
    public void showIView(final IView iview, final boolean needAnim, final Bundle bundle) {
        post(new Runnable() {
            @Override
            public void run() {
                final ViewPattern viewPattern = findViewPatternByIView(iview);
                showIViewInternal(viewPattern, needAnim, bundle);
            }
        });
    }

    private void showIViewInternal(final ViewPattern viewPattern, final boolean needAnim, final Bundle bundle) {
        if (viewPattern == null) {
            return;
        }

        if (isFinishing || !isAttachedToWindow) {
            post(new Runnable() {
                @Override
                public void run() {
                    showIViewInternal(viewPattern, needAnim, bundle);
                }
            });
            return;
        }

        viewPattern.mView.bringToFront();

        if (viewPattern == mLastShowViewPattern) {
            if (bundle != null) {
                viewShow(viewPattern, bundle);
            }
            return;
        }

        final ViewPattern lastShowViewPattern = mLastShowViewPattern;
        mLastShowViewPattern = viewPattern;

        mAttachViews.remove(viewPattern);
        mAttachViews.push(viewPattern);

        mLastShowViewPattern.mView.bringToFront();

        final Runnable endRunnable = new Runnable() {
            @Override
            public void run() {
                viewShow(mLastShowViewPattern, bundle);
            }
        };

        if (mLastShowViewPattern.mIView.isDialog()) {
            startDialogAnim(mLastShowViewPattern, needAnim ? mLastShowViewPattern.mIView.loadShowAnimation() : null, endRunnable);
        } else {
            if (lastShowViewPattern != null) {
                safeStartAnim(lastShowViewPattern.mView, needAnim ? mLastShowViewPattern.mIView.loadOtherHideAnimation() : null, new Runnable() {
                    @Override
                    public void run() {
                        viewHide(lastShowViewPattern);
                    }
                });
            }
            safeStartAnim(mLastShowViewPattern.mView, needAnim ? mLastShowViewPattern.mIView.loadShowAnimation() : null, endRunnable);
        }
    }

    @Override
    public void hideIView(final View view, final boolean needAnim) {
        if (isFinishing) {
            post(new Runnable() {
                @Override
                public void run() {
                    hideIView(view, needAnim);
                }
            });
            return;
        }

        post(new Runnable() {
            @Override
            public void run() {
                final ViewPattern viewPattern = findViewPatternByView(view);
                final Runnable endRunnable = new Runnable() {
                    @Override
                    public void run() {
                        viewHide(viewPattern);
                    }
                };
                if (viewPattern.mIView.isDialog()) {
                    finishDialogAnim(viewPattern, needAnim ? viewPattern.mIView.loadHideAnimation() : null, endRunnable);
                } else {
                    //隐藏的时候, 会错乱!!! 找不到之前显示的View, 慎用隐藏...
                    safeStartAnim(view, needAnim ? viewPattern.mIView.loadHideAnimation() : null, endRunnable);
                }
            }
        });
    }

    @Override
    public void hideIView(View view) {
        hideIView(view, true);
    }

    @Override
    public View getLayout() {
        return this;
    }

    @Override
    public boolean requestBackPressed() {
        return requestBackPressed(new UIParam());
    }

    @Override
    public boolean requestBackPressed(final UIParam param) {
        if (isBackPress) {
            return false;
        }
        if (mAttachViews.size() <= 0) {
            return true;
        }
        if (mAttachViews.size() == 1) {
            if (mLastShowViewPattern == null) {
                return true;
            } else {
                return mLastShowViewPattern.mIView.onBackPressed();
            }
        }

        if (param.isSwipeBack) {
            if (!mLastShowViewPattern.mIView.canSwipeBackPressed()) {
                //不能滑动返回
                return false;
            }
        } else {
            if (!mLastShowViewPattern.mIView.onBackPressed()) {
                //不能退出
                return false;
            }
        }

        isBackPress = true;
        finishIView(mLastShowViewPattern.mIView, param);
        return false;
    }

    @Override
    public void replaceIView(final IView iView, final UIParam param) {
        iView.onAttachedToILayout(this);

        if (isFinishing) {
            post(new Runnable() {
                @Override
                public void run() {
                    replaceIView(iView, param);
                }
            });
            return;
        }

        if (mLastShowViewPattern != null && mLastShowViewPattern.mIView.isDialog()) {
            L.d("等待对话框:" + mLastShowViewPattern.mIView.getClass().getSimpleName() + " 的关闭");
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    replaceIView(iView, param);
                }
            }, DEFAULT_ANIM_TIME);
            return;
        }

        runnableCount++;

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final ViewPattern oldViewPattern = getLastViewPattern();
                final ViewPattern newViewPattern = startIViewInternal(iView, param);

                //3:
                newViewPattern.mIView.onViewLoad();

                topViewStart(newViewPattern, param);

                final Runnable endRunnable = new Runnable() {
                    @Override
                    public void run() {
                        removeViewPattern(oldViewPattern);
                    }
                };
                oldViewPattern.mIView.onViewHide();
                bottomViewRemove(oldViewPattern, newViewPattern, endRunnable, param.mAnim);

                mLastShowViewPattern = newViewPattern;
                runnableCount--;
            }
        };

        if (param.mAsync) {
            post(runnable);
        } else {
            runnable.run();
        }
    }

    @Override
    public void replaceIView(IView iView) {
        replaceIView(iView, new UIParam());
    }

    /**
     * 获取最前显示的视图信息
     */
    public ViewPattern getLastViewPattern() {
//        if (mAttachViews.isEmpty()) {
//            return null;
//        }
//        return mAttachViews.lastElement();
        return mLastShowViewPattern;
    }

    private void startIViewAnim(final ViewPattern oldViewPattern, final ViewPattern newViewPattern,
                                final UIParam param, boolean reLoad) {
        if (isAttachedToWindow) {
            mLastShowViewPattern = newViewPattern;

            if (!reLoad) {
                newViewPattern.mIView.onViewLoad();
            }

            clearOldViewFocus(oldViewPattern);

            //startViewPatternAnim(newViewPattern, oldViewPattern, false, true);
            //startViewPatternAnim(newViewPattern, oldViewPattern, true, false);
            bottomViewFinish(oldViewPattern, newViewPattern, param);//先执行Bottom
            topViewStart(newViewPattern, param);//后执行Top
        } else {
//            for (ViewPattern viewPattern : mAttachViews) {
//                viewPattern.mView.setVisibility(INVISIBLE);
//            }
        }
    }

    /**
     * 清除焦点
     */
    private void clearOldViewFocus(ViewPattern oldViewPattern) {
        if (oldViewPattern != null) {
            oldViewPattern.mView.clearFocus();
            View focus = oldViewPattern.mView.findFocus();
            if (focus != null) {
                focus.clearFocus();
            }
        }
    }

    /**
     * 顶上视图进入的动画
     */

    private void topViewStart(final ViewPattern topViewPattern, final UIParam param) {
        final Animation animation = topViewPattern.mIView.loadStartAnimation();
        final Runnable endRunnable = new Runnable() {
            @Override
            public void run() {
                L.d(topViewPattern.mIView.getClass().getSimpleName() + " 启动完毕.");
                viewShow(topViewPattern, param.mBundle);
                topViewPattern.isAnimToStart = false;
                printLog();
            }
        };

        if (topViewPattern.mView instanceof ILifecycle) {
            ((ILifecycle) topViewPattern.mView).onLifeViewShow();
        }

        topViewPattern.mView.bringToFront();
        topViewPattern.isAnimToStart = true;
        topViewPattern.isAnimToEnd = false;

        if (!param.mAnim) {
            endRunnable.run();
            return;
        }

        if (topViewPattern.mIView.isDialog()) {
            //对话框的启动动画,作用在第一个子View上
            startDialogAnim(topViewPattern, animation, endRunnable);
        } else {
            safeStartAnim(topViewPattern.mIView.getAnimView(), animation, endRunnable);
        }
    }

    /**
     * 顶上视图退出的动画
     */
    private void topViewFinish(final ViewPattern bottomViewPattern, final ViewPattern topViewPattern, final boolean anim) {
        final Animation animation = topViewPattern.mIView.loadFinishAnimation();
        final Runnable endRunnable = new Runnable() {
            @Override
            public void run() {
                L.d(topViewPattern.mIView.getClass().getSimpleName() + " 关闭完成.");
                mLastShowViewPattern = bottomViewPattern;//星期一 2017-1-16

                topViewPattern.isAnimToEnd = false;
                isFinishing = false;
                isBackPress = false;
                viewHide(topViewPattern);
                removeViewPattern(topViewPattern);

                printLog();
            }
        };


        if (topViewPattern.mView instanceof ILifecycle) {
            ((ILifecycle) topViewPattern.mView).onLifeViewHide();
        }

        if (!anim) {
            endRunnable.run();
            return;
        }

        isFinishing = true;

        topViewPattern.isAnimToEnd = true;
        topViewPattern.isAnimToStart = false;

        if (topViewPattern.mIView.isDialog()) {
            //对话框的启动动画,作用在第一个子View上
            finishDialogAnim(topViewPattern, animation, endRunnable);
        } else {
            safeStartAnim(topViewPattern.mIView.getAnimView(), animation, endRunnable);
        }
    }

    /**
     * 为了确保任务都行执行完了, 延迟打印堆栈信息
     */
    private void printLog() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                logLayoutInfo();
            }
        }, 100);
    }

    /**
     * 底部视图进入动画
     */
    private void bottomViewStart(final ViewPattern bottomViewPattern, final ViewPattern topViewPattern,
                                 boolean anim, final boolean quiet) {
        if (bottomViewPattern == null) {
            return;
        }
        final Runnable endRunnable = new Runnable() {
            @Override
            public void run() {
                if (!quiet) {
                    viewShow(bottomViewPattern, null);
                }
                bottomViewPattern.mIView.onViewReShow(null);
            }
        };
        bottomViewPattern.mView.setVisibility(VISIBLE);

        if (bottomViewPattern.mView instanceof ILifecycle) {
            ((ILifecycle) bottomViewPattern.mView).onLifeViewShow();
        }


        if (topViewPattern.mIView.isDialog()) {
            //对话框结束时, 不执行生命周期
        } else {
            if (!anim || quiet) {
                endRunnable.run();
            } else {
                final Animation animation = topViewPattern.mIView.loadOtherEnterAnimation();
                safeStartAnim(bottomViewPattern.mIView.getAnimView(), animation, endRunnable);
            }
        }
    }

    /**
     * 底部视图退出动画
     */
    private void bottomViewFinish(final ViewPattern bottomViewPattern, final ViewPattern topViewPattern,
                                  final UIParam param) {
        final Runnable endRunnable = new Runnable() {
            @Override
            public void run() {
                viewHide(bottomViewPattern, param.hideLastIView);
            }
        };
        bottomViewRemove(bottomViewPattern, topViewPattern, endRunnable, param.mAnim);
    }

    /**
     * 底部视图 销毁
     */
    private void bottomViewRemove(final ViewPattern bottomViewPattern, final ViewPattern topViewPattern,
                                  final Runnable endRunnable,
                                  boolean anim) {
        if (bottomViewPattern == null) {
            return;
        }

        bottomViewPattern.isAnimToStart = false;

        if (bottomViewPattern.mView instanceof ILifecycle) {
            ((ILifecycle) bottomViewPattern.mView).onLifeViewHide();
        }

        if (topViewPattern.mIView.isDialog()) {
            //对话框弹出的时候, 底部IView 不执行周期
        } else {
            if (anim) {
                final Animation animation = topViewPattern.mIView.loadOtherExitAnimation();
                safeStartAnim(bottomViewPattern.mIView.getAnimView(), animation, endRunnable);
            } else {
                endRunnable.run();
            }
        }
    }


    private void viewHide(final ViewPattern viewPattern, boolean hide) {
        if (viewPattern == null) {
            return;
        }
        viewPattern.mIView.onViewHide();
        if (hide) {
            viewPattern.mView.setVisibility(GONE);
        }
    }

    private void viewHide(final ViewPattern viewPattern) {
        viewHide(viewPattern, true);
    }

    private void viewShow(final ViewPattern viewPattern, final Bundle bundle) {
        if (viewPattern == null) {
            return;
        }
        viewPattern.mView.setVisibility(VISIBLE);
//        viewPattern.mView.bringToFront();
        viewPattern.mIView.onViewShow(bundle);
    }

    /**
     * 启动之前, 开始View的动画
     * 新界面, 播放启动动画;
     * 旧界面, 播放伴随退出动画
     * 2016-12-2 太复杂了, 拆成4个方法
     */
    @Deprecated
    private void startViewPatternAnim(final ViewPattern newViewPattern, final ViewPattern lastViewPattern, boolean isStart, boolean withOther) {
        if (newViewPattern == null) {
            return;
        }

        if (withOther) {
            if (newViewPattern.mIView.isDialog()) {
                //对话框不产生伴随动画.
                if (isStart) {
                    if (lastViewPattern != null) {
                        lastViewPattern.mIView.onViewShow(null);
                    }
                } else {
                    if (lastViewPattern != null) {
                        lastViewPattern.mIView.onViewHide();
                    }
                }
            } else {
                //伴随, 需要产生的动画
                if (isStart) {
                    //上层的View退出, 下层的View进入的动画
                    final Animation animation = newViewPattern.mIView.loadOtherEnterAnimation();

                    safeStartAnim(lastViewPattern.mView, animation, new Runnable() {
                        @Override
                        public void run() {
                            lastViewPattern.mIView.onViewShow(null);

                            if (lastViewPattern.mView instanceof ILifecycle) {
                                ((ILifecycle) lastViewPattern.mView).onLifeViewShow();
                            }
                        }
                    });
                } else if (lastViewPattern != null) {
                    //上层的View进入, 下层的View退出的动画
                    final Animation animation = newViewPattern.mIView.loadOtherExitAnimation();
                    safeStartAnim(lastViewPattern.mView, animation, new Runnable() {
                        @Override
                        public void run() {
                            lastViewPattern.mIView.onViewHide();

                            if (lastViewPattern.mView instanceof ILifecycle) {
                                ((ILifecycle) lastViewPattern.mView).onLifeViewHide();
                            }
                        }
                    });
                }
            }
        } else {
            if (newViewPattern.mIView.isDialog()) {
                if (isStart) {
                    //对话框的启动动画,作用在第一个子View上
                    startDialogAnim(newViewPattern, newViewPattern.mIView.loadStartAnimation(), new Runnable() {
                        @Override
                        public void run() {
                            newViewPattern.mIView.onViewShow(null);
                        }
                    });
                } else {
                    //finish View的动画
                    finishDialogAnim(newViewPattern, newViewPattern.mIView.loadFinishAnimation(), new Runnable() {
                        @Override
                        public void run() {
                            removeViewPattern(newViewPattern);
                        }
                    });
                }
            } else {
                //自己的动画
                if (isStart) {
                    //启动View的动画
                    final Animation animation = newViewPattern.mIView.loadStartAnimation();
                    safeStartAnim(newViewPattern.mView, animation, new Runnable() {
                        @Override
                        public void run() {
                            newViewPattern.mIView.onViewShow(null);
                        }
                    });
                } else {
                    //finish View的动画
                    final Animation animation = newViewPattern.mIView.loadFinishAnimation();
                    safeStartAnim(newViewPattern.mView, animation, new Runnable() {
                        @Override
                        public void run() {
                            removeViewPattern(newViewPattern);
                        }
                    });
                }
            }
        }
    }

    /**
     * 对话框的启动动画
     */
    private void startDialogAnim(final ViewPattern dialogPattern, final Animation animation, final Runnable endRunnable) {
        //对话框的启动动画,作用在第一个子View上

        /*是否变暗*/
        if (dialogPattern.mIView.isDimBehind()) {
            AnimUtil.startArgb(dialogPattern.mIView.getDialogDimView(),
                    Color.TRANSPARENT, dialogPattern.mIView.getDimColor(), DEFAULT_ANIM_TIME);
        }

        if (dialogPattern.mIView.canTouchOnOutside()) {
            dialogPattern.mView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogPattern.mIView.canCanceledOnOutside()) {
                        finishIView(dialogPattern.mView);
                    }
                }
            });
        }

        safeStartAnim(dialogPattern.mIView.getAnimView(), animation, endRunnable);
    }

    /**
     * 销毁对话框的动画
     */
    private void finishDialogAnim(final ViewPattern dialogPattern, final Animation animation, final Runnable end) {
          /*是否变暗*/
        if (dialogPattern.mIView.isDimBehind()) {
            AnimUtil.startArgb(dialogPattern.mIView.getDialogDimView(),
                    dialogPattern.mIView.getDimColor(), Color.TRANSPARENT, DEFAULT_ANIM_TIME);
        }

        final View animView = dialogPattern.mIView.getAnimView();

        final Runnable endRunnable = new Runnable() {
            @Override
            public void run() {
                dialogPattern.mView.setAlpha(0);
                dialogPattern.mView.setVisibility(INVISIBLE);
                end.run();
            }
        };

        safeStartAnim(animView, animation, endRunnable);
    }

    /**
     * 安全的启动一个动画
     */
    private boolean safeStartAnim(final View view, final Animation animation, final Runnable endRunnable) {
        if (view == null) {
            if (endRunnable != null) {
                endRunnable.run();
            }
            return false;
        }

        if (animation == null) {
            if (endRunnable != null) {
                endRunnable.run();
            }
            return false;
        }

        animation.setAnimationListener(new AnimRunnable(endRunnable));

        view.startAnimation(animation);

        return true;
    }

    public ViewPattern findViewPatternByView(View view) {
        for (ViewPattern viewPattern : mAttachViews) {
            if (viewPattern.mView == view) {
                return viewPattern;
            }
        }
        return null;
    }

    public ViewPattern findViewPatternByIView(IView iview) {
        for (ViewPattern viewPattern : mAttachViews) {
//            if (TextUtils.equals(viewPattern.mIView.getClass().getSimpleName(), iview.getClass().getSimpleName())) {
//                return viewPattern;
//            }

            if (viewPattern.mIView == iview) {
                return viewPattern;
            }
        }
        return null;
    }

    public ViewPattern findViewPatternByClass(Class<?> clz) {
        for (ViewPattern viewPattern : mAttachViews) {
            if (TextUtils.equals(viewPattern.mIView.getClass().getSimpleName(), clz.getSimpleName())) {
                return viewPattern;
            }
        }
        return null;
    }

    public ViewPattern findLastShowViewPattern() {
        return findViewPattern(mAttachViews.size() - 2);
    }

    public ViewPattern findLastShowViewPattern(final ViewPattern anchor) {
        if (anchor == mLastShowViewPattern) {
            return findViewPattern(mAttachViews.size() - 2);
        } else {
            return mLastShowViewPattern;
        }
    }

    public ViewPattern findLastViewPattern() {
        return findViewPattern(mAttachViews.size() - 1);
    }

    public ViewPattern findViewPattern(int position) {
        if (mAttachViews.size() > position && position >= 0) {
            return mAttachViews.get(position);
        }
        return null;
    }

    public void removeViewPattern(ViewPattern viewPattern) {
        hideSoftInput();
        interruptSet.remove(viewPattern.mIView);
        viewPattern.mIView.onViewUnload();
        final View view = viewPattern.mView;
        ViewCompat.setAlpha(view, 0);
        view.setVisibility(GONE);
        post(new Runnable() {
            @Override
            public void run() {
                try {
                    removeView(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mAttachViews.remove(viewPattern);
        isFinishing = false;
        isBackPress = false;

        for (OnIViewChangedListener listener : mOnIViewChangedListeners) {
            listener.onIViewRemove(this, viewPattern);
        }
    }

    @Override
    public void onShowInPager(final UIViewPager viewPager) {
        if (runnableCount > 0) {
            post(new Runnable() {
                @Override
                public void run() {
                    onShowInPager(viewPager);
                }
            });
            return;
        }
        mLastShowViewPattern.mIView.onShowInPager(viewPager);
    }

    @Override
    public void onHideInPager(final UIViewPager viewPager) {
        if (runnableCount > 0) {
            post(new Runnable() {
                @Override
                public void run() {
                    onShowInPager(viewPager);
                }
            });
            return;
        }
        mLastShowViewPattern.mIView.onHideInPager(viewPager);
    }

    private boolean needDelay() {
        if (!isAttachedToWindow) {
            return true;
        }
        if (mAttachViews.size() > 0 && mLastShowViewPattern == null) {
            return true;
        }
        return false;
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mInsets[0] = insets.getSystemWindowInsetLeft();
            mInsets[1] = insets.getSystemWindowInsetTop();
            mInsets[2] = insets.getSystemWindowInsetRight();
            mInsets[3] = insets.getSystemWindowInsetBottom();

            post(new Runnable() {
                @Override
                public void run() {
                    notifyListener();
                }
            });
            return super.onApplyWindowInsets(insets.replaceSystemWindowInsets(insets.getSystemWindowInsetLeft(), 0,
                    insets.getSystemWindowInsetRight(), lockHeight ? 0 : insets.getSystemWindowInsetBottom()));
        } else {
            return super.onApplyWindowInsets(insets);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (isWantSwipeBack) {
            return;
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void notifyListener() {
         /*键盘弹出监听事件*/
        if (mIWindowInsetsListeners != null) {
            for (IWindowInsetsListener listener : mIWindowInsetsListeners) {
                listener.onWindowInsets(mInsets[0], mInsets[1], mInsets[2], mInsets[3]);
            }
        }
    }

    public UILayoutImpl addIWindowInsetsListener(IWindowInsetsListener listener) {
        if (listener == null) {
            return this;
        }
        if (mIWindowInsetsListeners == null) {
            mIWindowInsetsListeners = new ArrayList<>();
        }
        this.mIWindowInsetsListeners.add(listener);
        return this;
    }

    public UILayoutImpl removeIWindowInsetsListener(IWindowInsetsListener listener) {
        if (listener == null || mIWindowInsetsListeners == null) {
            return this;
        }
        this.mIWindowInsetsListeners.remove(listener);
        return this;
    }

    public UILayoutImpl addOnIViewChangeListener(OnIViewChangedListener listener) {
        if (listener == null) {
            return this;
        }
        this.mOnIViewChangedListeners.add(listener);
        return this;
    }

    public UILayoutImpl removeOnIViewChangeListener(OnIViewChangedListener listener) {
        if (listener == null || mOnIViewChangedListeners == null) {
            return this;
        }
        this.mOnIViewChangedListeners.remove(listener);
        return this;
    }

    public void setLockHeight(boolean lockHeight) {
        this.lockHeight = lockHeight;
    }

    /**
     * 获取底部装饰物的高度 , 通常是键盘的高度
     */
    public int getInsersBottom() {
        return mInsets[3];
    }

    public void hideSoftInput() {
        if (isSoftKeyboardShow()) {
            InputMethodManager manager = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }

    /**
     * 判断键盘是否显示
     */
    private boolean isSoftKeyboardShow() {
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int keyboardHeight = getSoftKeyboardHeight();
        return screenHeight != keyboardHeight && keyboardHeight > 100;
    }

    /**
     * 获取键盘的高度
     */
    private int getSoftKeyboardHeight() {
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        Rect rect = new Rect();
        getWindowVisibleDisplayFrame(rect);
        int visibleBottom = rect.bottom;
        return screenHeight - visibleBottom;
    }

    public void showSoftInput() {
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInputFromInputMethod(getWindowToken(), 0);
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (mLastShowViewPattern != null) {
            if (mLastShowViewPattern.isAnimToEnd) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        onActivityResult(requestCode, resultCode, data);
                    }
                });
            } else {
                mLastShowViewPattern.mIView.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    /**
     * 根据位置, 返回IView
     */
    public ViewPattern getViewPattern(int position) {
        if (position < 0 || position >= mAttachViews.size()) {
            return null;
        }
        return mAttachViews.get(position);
    }

    /**
     * 通过类名, 返回最早添加的IView
     */
    public ViewPattern getViewPatternWithClass(Class<?> cls) {
        for (ViewPattern pattern : mAttachViews) {
            if (pattern.mIView.getClass().getSimpleName().equalsIgnoreCase(cls.getSimpleName())) {
                return pattern;
            }
        }
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }

    @Override
    public void finishAll() {
        finishAll(false);
    }

    @Override
    public void finishAll(boolean keepLast) {
        while (!mAttachViews.empty()) {
            ViewPattern pattern = mAttachViews.pop();
            if (keepLast && pattern == getLastViewPattern()) {
                return;
            } else {
                pattern.interrupt = true;
                finishIViewInner(pattern, new UIParam(false, false, true));
            }
        }
    }

    @Override
    public void finish() {
        finishAll();
        mLayoutActivity.onBackPressed();
    }

    @Override
    protected void onRequestClose() {
        super.onRequestClose();
        if (enableRootSwipe && getIViewSize() == 1) {
            mLayoutActivity.finish();
            mLayoutActivity.overridePendingTransition(0, 0);
        } else {
            finishIView(mLastShowViewPattern.mIView, new UIParam(false, true, false));
        }
        printLog();
    }

    @Override
    protected void onRequestOpened() {
        super.onRequestOpened();
        isSwipeDrag = false;
        translation(0);
        final ViewPattern viewPattern = findLastShowViewPattern(mLastShowViewPattern);
        if (viewPattern != null) {
            viewPattern.mView.setVisibility(GONE);
        }
        printLog();
    }

    @Override
    protected void onSlideChange(float percent) {
        super.onSlideChange(percent);
        isSwipeDrag = true;
        translation(percent);
    }

    @Override
    protected void onStateIdle() {
        super.onStateIdle();
        isWantSwipeBack = false;
    }

    @Override
    protected void onStateDragging() {
        super.onStateDragging();
        isWantSwipeBack = true;
        isSwipeDrag = true;

        //开始偏移时, 偏移的距离
        final ViewPattern viewPattern = findLastShowViewPattern(mLastShowViewPattern);
        if (viewPattern != null && !viewPattern.mIView.isDialog()) {
            mTranslationOffsetX = getMeasuredWidth() * 0.3f;
            viewPattern.mView.setVisibility(VISIBLE);
            viewPattern.mView.setTranslationX(-mTranslationOffsetX);
        }
    }

    private void translation(float percent) {
        final ViewPattern viewPattern = findLastShowViewPattern(mLastShowViewPattern);
        if (viewPattern != null && !viewPattern.mIView.isDialog()) {
            viewPattern.mView.setTranslationX(-mTranslationOffsetX * percent);
        }
    }

    /**
     * 移动最后一个可见视图
     */
    public void translationLastView(int x) {
        if (mLastShowViewPattern != null) {
            mLastShowViewPattern.mView.setTranslationX(x);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        try {
            super.dispatchDraw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取已经添加IView的数量
     */
    public int getIViewSize() {
        if (mAttachViews == null || mAttachViews.isEmpty()) {
            return 0;
        }
        return mAttachViews.size();
    }

    public boolean isSwipeDrag() {
        return isSwipeDrag;
    }

    /**
     * 打印堆栈信息
     */
    public String logLayoutInfo() {
        StringBuilder stringBuilder = new StringBuilder("\n");
        for (int i = 0; i < mAttachViews.size(); i++) {
            ViewPattern viewPattern = mAttachViews.get(i);
            stringBuilder.append(i);
            stringBuilder.append("-->");
            stringBuilder.append(viewPattern.mIView.getClass().getSimpleName());
            stringBuilder.append("");
            int visibility = viewPattern.mView.getVisibility();
            String vis;
            if (visibility == View.GONE) {
                vis = "GONE";
            } else if (visibility == View.VISIBLE) {
                vis = "VISIBLE";
            } else if (visibility == View.INVISIBLE) {
                vis = "INVISIBLE";
            } else {
                vis = "NONE";
            }
            stringBuilder.append(" visibility-->");
            stringBuilder.append(vis);
            stringBuilder.append("\n");
        }
        String string = stringBuilder.toString();
        L.e(string);
        return string;
    }

    /**
     * IView 添加,移除监听
     */
    public interface OnIViewChangedListener {
        void onIViewAdd(final UILayoutImpl uiLayout, final ViewPattern viewPattern);

        void onIViewRemove(final UILayoutImpl uiLayout, final ViewPattern viewPattern);
    }

    class AnimRunnable implements Animation.AnimationListener {

        private Runnable mRunnable;

        public AnimRunnable(Runnable runnable) {
            mRunnable = runnable;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (mRunnable != null) {
                mRunnable.run();
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
