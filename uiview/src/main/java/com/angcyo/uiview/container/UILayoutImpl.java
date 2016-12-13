package com.angcyo.uiview.container;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.angcyo.uiview.model.ViewPattern;
import com.angcyo.uiview.resources.AnimUtil;
import com.angcyo.uiview.view.ILifecycle;
import com.angcyo.uiview.view.IView;
import com.angcyo.uiview.widget.UIViewPager;

import java.util.ArrayList;
import java.util.Stack;

import static com.angcyo.uiview.view.UIIViewImpl.DEFAULT_ANIM_TIME;

/**
 * 可以用来显示IView的布局, 每一层的管理
 * Created by angcyo on 2016-11-12.
 */

public class UILayoutImpl extends FrameLayout implements ILayout, UIViewPager.OnPagerShowListener {

    @ColorInt
    public static final int DimColor = Color.parseColor("#40000000");
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

    /**
     * 是否正在退出
     */
    private boolean isFinishing = false;
    private ArrayList<IWindowInsetsListener> mIWindowInsetsListeners;

    private int[] mInsets = new int[4];

    /**
     * 锁定高度, 当键盘弹出的时候, 可以不改变size
     */
    private boolean lockHeight = false;

    public UILayoutImpl(Context context) {
        super(context);
    }

    public UILayoutImpl(Context context, IView iView) {
        super(context);
        startIView(iView, false);
    }

    public UILayoutImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UILayoutImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UILayoutImpl(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * inflate之后, 有时会返回 父布局, 这个时候需要处理一下, 才能拿到真实的RootView
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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setFocusable(true);
        setFocusableInTouchMode(true);
        isAttachedToWindow = true;
        post(new Runnable() {
            @Override
            public void run() {
                loadViewInternal();
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isAttachedToWindow = false;
        unloadViewInternal();
    }

    /**
     * 卸载IView
     */
    protected void unloadViewInternal() {
        for (; !mAttachViews.isEmpty(); ) {
            final ViewPattern viewPattern = mAttachViews.pop();
            viewPattern.mIView.onViewUnload();
        }
    }

    @Override
    public View startIView(final IView iView, boolean needAnim) {

        hideSoftInput();

        //1:inflateContentView, 会返回对应IView的RootLayout
        View rawView = loadViewInternal(iView);
        //2:loadContentView
        iView.loadContentView(rawView);

        final ViewPattern oldViewPattern = getLastViewPattern();
        final ViewPattern newViewPattern = new ViewPattern(iView, rawView);
        mAttachViews.push(newViewPattern);

        startIViewAnim(oldViewPattern, newViewPattern, needAnim);

        return rawView;
    }

    @Override
    public View startIView(IView iView) {
        return startIView(iView, true);
    }

    /**
     * 加载所有添加的IView
     */
    protected void loadViewInternal() {
        ViewPattern lastViewPattern = null;
        for (ViewPattern viewPattern : mAttachViews) {
            if (lastViewPattern != null) {
                lastViewPattern.mIView.onViewHide();
            }
            lastViewPattern = viewPattern;
            lastViewPattern.mIView.onViewLoad();//1:
        }

        if (lastViewPattern != null) {
            //lastViewPattern.mView.setVisibility(VISIBLE);
            lastViewPattern.mView.bringToFront();
            lastViewPattern.mIView.onViewShow();//2:
        }
        mLastShowViewPattern = lastViewPattern;
    }

    /**
     * 加载IView
     */
    private View loadViewInternal(IView iView) {
        final Context context = getContext();

        //首先调用IView接口的inflateContentView方法,(inflateContentView请不要初始化View)
        //其次会调用loadContentView方法,用来初始化View.(此方法调用之后, 就支持ButterKnife了)
        final View view = iView.inflateContentView(context, this, this, LayoutInflater.from(context));
        iView.onViewCreate();

        View rawView;

        //返回真实的RootView, 防止连续追加2个相同的IView之后, id重叠的情况
        if (this == view) {
            rawView = getChildAt(getChildCount() - 1);
        } else {
            rawView = view;
        }

        return rawView;
    }

    @Override
    public void finishIView(final View view, boolean needAnim) {
        ViewPattern viewPattern = findViewPatternByView(view);
        ViewPattern lastViewPattern = findLastShowViewPattern();

        if (viewPattern.isAnimToEnd || isFinishing) {
            return;
        }

        isFinishing = true;

        /*对话框的处理*/
        if (viewPattern.mIView.isDialog() &&
                !viewPattern.mIView.canCancel()) {
            return;
        }

        if (needAnim) {
            viewPattern.isAnimToEnd = true;
            //startViewPatternAnim(viewPattern, lastViewPattern, true, true);
            //startViewPatternAnim(viewPattern, lastViewPattern, false, false);
            topViewFinish(viewPattern, needAnim);
            bottomViewStart(lastViewPattern, viewPattern, needAnim);
        } else {
            if (lastViewPattern != null) {
                lastViewPattern.mIView.onViewShow();

                if (lastViewPattern.mView instanceof ILifecycle) {
                    ((ILifecycle) lastViewPattern.mView).onViewShow();
                }
            }
            removeViewPattern(viewPattern);
        }
        mLastShowViewPattern = lastViewPattern;
    }

    @Override
    public void finishIView(View view) {
        finishIView(view, true);
    }

    @Override
    public void showIView(final View view, final boolean needAnim) {
        final ViewPattern viewPattern = findViewPatternByView(view);
        if (viewPattern == null) {
            return;
        }

        if (viewPattern == mLastShowViewPattern) {
            return;
        }

        if (!isAttachedToWindow) {
            post(new Runnable() {
                @Override
                public void run() {
                    showIView(view, needAnim);
                }
            });
            return;
        }

        //viewPattern.mView.setVisibility(VISIBLE);
        viewPattern.mView.bringToFront();

        if (viewPattern.mIView.isDialog()) {
            startDialogAnim(viewPattern, needAnim ? viewPattern.mIView.loadShowAnimation() : null, new Runnable() {
                @Override
                public void run() {
                    viewPattern.mIView.onViewShow();
                }
            });
        } else {
            if (mLastShowViewPattern != null) {
                final ViewPattern lastShowViewPattern = mLastShowViewPattern;
                safeStartAnim(lastShowViewPattern.mView, needAnim ? viewPattern.mIView.loadOtherHideAnimation() : null, new Runnable() {
                    @Override
                    public void run() {
                        //lastShowViewPattern.mView.setVisibility(INVISIBLE);
                        lastShowViewPattern.mIView.onViewHide();
                    }
                });
            }
            mLastShowViewPattern = viewPattern;
            safeStartAnim(viewPattern.mView, needAnim ? viewPattern.mIView.loadShowAnimation() : null, new Runnable() {
                @Override
                public void run() {
                    viewPattern.mIView.onViewShow();
                }
            });
        }
    }

    @Override
    public void showIView(View view) {
        showIView(view, true);
    }

    @Override
    public void hideIView(final View view, boolean needAnim) {
        final ViewPattern viewPattern = findViewPatternByView(view);
        if (viewPattern.mIView.isDialog()) {
            finishDialogAnim(viewPattern, needAnim ? viewPattern.mIView.loadHideAnimation() : null, new Runnable() {
                @Override
                public void run() {
                    //viewPattern.mView.setVisibility(INVISIBLE);
                    viewPattern.mIView.onViewHide();
                }
            });
        } else {
            //隐藏的时候, 会错乱!!! 找不到之前显示的View, 慎用隐藏...
            safeStartAnim(view, needAnim ? viewPattern.mIView.loadHideAnimation() : null, new Runnable() {
                @Override
                public void run() {
                    //viewPattern.mView.setVisibility(INVISIBLE);
                    viewPattern.mIView.onViewHide();
                }
            });
        }
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
        if (mAttachViews.size() < 2) {
            return true;
        }
        finishIView(mLastShowViewPattern.mView);
        return false;
    }

    @Override
    public void replaceIView(IView iView, boolean needAnim) {
        if (mLastShowViewPattern != null) {
            safeStartAnim(mLastShowViewPattern.mView, needAnim ? mLastShowViewPattern.mIView.loadFinishAnimation() : null, new Runnable() {
                @Override
                public void run() {
                    mLastShowViewPattern.mIView.onViewHide();
                    mLastShowViewPattern.mIView.onViewUnload();
                    removeView(mLastShowViewPattern.mView);
                    mAttachViews.remove(mLastShowViewPattern);
                }
            });
        }

        View rawView = loadViewInternal(iView);
        final ViewPattern newViewPattern = new ViewPattern(iView, rawView);
        mAttachViews.push(newViewPattern);
        mLastShowViewPattern = newViewPattern;

        mLastShowViewPattern.mIView.onViewLoad();
        safeStartAnim(mLastShowViewPattern.mView, needAnim ? mLastShowViewPattern.mIView.loadStartAnimation() : null, new Runnable() {
            @Override
            public void run() {
                mLastShowViewPattern.mIView.onViewShow();
                mLastShowViewPattern.mView.bringToFront();
            }
        });
    }

    @Override
    public void replaceIView(IView iView) {
        replaceIView(iView, true);
    }

    protected ViewPattern getLastViewPattern() {
//        if (mAttachViews.isEmpty()) {
//            return null;
//        }
//        return mAttachViews.lastElement();
        return mLastShowViewPattern;
    }

    private void startIViewAnim(final ViewPattern oldViewPattern, final ViewPattern newViewPattern, boolean needAnim) {
        if (isAttachedToWindow) {
            mLastShowViewPattern = newViewPattern;
            if (newViewPattern != null) {
                newViewPattern.mIView.onViewLoad();
            }

            if (oldViewPattern != null) {
                oldViewPattern.mView.clearFocus();
                View focus = oldViewPattern.mView.findFocus();
                if (focus != null) {
                    focus.clearFocus();
                }
            }

            //startViewPatternAnim(newViewPattern, oldViewPattern, false, true);
            //startViewPatternAnim(newViewPattern, oldViewPattern, true, false);
            topViewStart(newViewPattern, needAnim);
            bottomViewFinish(oldViewPattern, newViewPattern, needAnim);
        } else {
//            for (ViewPattern viewPattern : mAttachViews) {
//                viewPattern.mView.setVisibility(INVISIBLE);
//            }
        }
    }

    /**
     * 顶上视图进入的动画
     */

    private void topViewStart(final ViewPattern topViewPattern, boolean anim) {
        final Animation animation = topViewPattern.mIView.loadStartAnimation();
        final Runnable endRunnable = new Runnable() {
            @Override
            public void run() {
                topViewPattern.mIView.onViewShow();
            }
        };

        if (topViewPattern.mView instanceof ILifecycle) {
            ((ILifecycle) topViewPattern.mView).onViewShow();
        }

        if (!anim) {
            endRunnable.run();
            return;
        }

        if (topViewPattern.mIView.isDialog()) {
            //对话框的启动动画,作用在第一个子View上
            startDialogAnim(topViewPattern, animation, endRunnable);
        } else {
            safeStartAnim(topViewPattern.mView, animation, endRunnable);
        }
    }

    /**
     * 顶上视图退出的动画
     */
    private void topViewFinish(final ViewPattern topViewPattern, boolean anim) {
        final Animation animation = topViewPattern.mIView.loadFinishAnimation();
        final Runnable endRunnable = new Runnable() {
            @Override
            public void run() {
                removeViewPattern(topViewPattern);
            }
        };

        topViewPattern.mIView.onViewHide();

        if (topViewPattern.mView instanceof ILifecycle) {
            ((ILifecycle) topViewPattern.mView).onViewHide();
        }

        if (!anim) {
            endRunnable.run();
            return;
        }

        if (topViewPattern.mIView.isDialog()) {
            //对话框的启动动画,作用在第一个子View上
            finishDialogAnim(topViewPattern, animation, endRunnable);
        } else {
            safeStartAnim(topViewPattern.mView, animation, endRunnable);
        }
    }

    /**
     * 底部视图进入动画
     */
    private void bottomViewStart(final ViewPattern bottomViewPattern, final ViewPattern topViewPattern, boolean anim) {
        if (bottomViewPattern == null) {
            return;
        }
        final Runnable endRunnable = new Runnable() {
            @Override
            public void run() {
                bottomViewPattern.mIView.onViewShow();
            }
        };
        //bottomViewPattern.mView.setVisibility(VISIBLE);

        if (bottomViewPattern.mView instanceof ILifecycle) {
            ((ILifecycle) bottomViewPattern.mView).onViewShow();
        }

        if (!anim) {
            endRunnable.run();
            return;
        }

        if (topViewPattern.mIView.isDialog()) {
            endRunnable.run();
        } else {
            final Animation animation = topViewPattern.mIView.loadOtherEnterAnimation();
            safeStartAnim(bottomViewPattern.mView, animation, endRunnable);
        }
    }

    /**
     * 底部视图退出动画
     */
    private void bottomViewFinish(final ViewPattern bottomViewPattern, final ViewPattern topViewPattern, boolean anim) {
        if (bottomViewPattern == null) {
            return;
        }

        final Runnable endRunnable = new Runnable() {
            @Override
            public void run() {
                bottomViewPattern.mIView.onViewHide();
                //bottomViewPattern.mView.setVisibility(INVISIBLE);
            }
        };

        if (bottomViewPattern.mView instanceof ILifecycle) {
            ((ILifecycle) bottomViewPattern.mView).onViewHide();
        }

        if (!anim) {
            endRunnable.run();
            return;
        }

        if (topViewPattern.mIView.isDialog()) {
            endRunnable.run();
        } else {
            final Animation animation = topViewPattern.mIView.loadOtherExitAnimation();
            safeStartAnim(bottomViewPattern.mView, animation, endRunnable);
        }
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
                        lastViewPattern.mIView.onViewShow();
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
                            lastViewPattern.mIView.onViewShow();

                            if (lastViewPattern.mView instanceof ILifecycle) {
                                ((ILifecycle) lastViewPattern.mView).onViewShow();
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
                                ((ILifecycle) lastViewPattern.mView).onViewHide();
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
                            newViewPattern.mIView.onViewShow();
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
                            newViewPattern.mIView.onViewShow();
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
            AnimUtil.startArgb(dialogPattern.mView,
                    Color.TRANSPARENT, DimColor, DEFAULT_ANIM_TIME);
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

        safeStartAnim(((ViewGroup) dialogPattern.mView).getChildAt(0), animation, endRunnable);
    }

    /**
     * 销毁对话框的动画
     */
    private void finishDialogAnim(final ViewPattern dialogPattern, final Animation animation, final Runnable end) {
          /*是否变暗*/
        if (dialogPattern.mIView.isDimBehind()) {
            AnimUtil.startArgb(dialogPattern.mView,
                    DimColor, Color.TRANSPARENT, DEFAULT_ANIM_TIME);
        }

        final View animView;
        if (dialogPattern.mView instanceof ViewGroup) {
            animView = ((ViewGroup) dialogPattern.mView).getChildAt(0);
        } else {
            animView = dialogPattern.mView;
        }

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

    public ViewPattern findLastShowViewPattern() {
        return findViewPattern(mAttachViews.size() - 2);
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
        viewPattern.mIView.onViewUnload();
        removeView(viewPattern.mView);
        mAttachViews.remove(viewPattern);
        isFinishing = false;
    }

    @Override
    public void onShowInPager(final UIViewPager viewPager) {
        if (needDelay()) {
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
        if (needDelay()) {
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

//    @Override
//    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mInsets[0] = insets.getSystemWindowInsetLeft();
//            mInsets[1] = insets.getSystemWindowInsetTop();
//            mInsets[2] = insets.getSystemWindowInsetRight();
//            mInsets[3] = insets.getSystemWindowInsetBottom();
//
//            post(new Runnable() {
//                @Override
//                public void run() {
//                    notifyListener();
//                }
//            });
//            return super.onApplyWindowInsets(insets.replaceSystemWindowInsets(insets.getSystemWindowInsetLeft(), 0,
//                    insets.getSystemWindowInsetRight(), lockHeight ? 0 : insets.getSystemWindowInsetBottom()));
//        } else {
//            return super.onApplyWindowInsets(insets);
//        }
//    }


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
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(getWindowToken(), 0);
    }

    public void showSoftInput() {
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInputFromInputMethod(getWindowToken(), 0);
    }

    /**
     * 获取最前显示的视图信息
     */
    public ViewPattern getLastShowViewPattern() {
        return mLastShowViewPattern;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mLastShowViewPattern != null) {
            mLastShowViewPattern.mIView.onActivityResult(requestCode, resultCode, data);
        }
    }

    static class AnimRunnable implements Animation.AnimationListener {

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
