package com.angcyo.uiview.container;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.angcyo.uiview.model.ViewPattern;
import com.angcyo.uiview.resources.AnimUtil;
import com.angcyo.uiview.view.IView;

import java.util.Stack;

import static com.angcyo.uiview.view.UIBaseIViewImpl.DEFAULT_ANIM_TIME;

/**
 * 可以用来显示IView的布局, 每一层的管理
 * Created by angcyo on 2016-11-12.
 */

public class UILayoutImpl extends FrameLayout implements ILayout {

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

    public UILayoutImpl(Context context) {
        super(context);
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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
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

    /**
     * 加载IView
     */
    protected void loadViewInternal() {
        ViewPattern lastViewPattern = null;
        for (ViewPattern viewPattern : mAttachViews) {
            if (lastViewPattern != null) {
                lastViewPattern.mIView.onViewHide();
            }
            lastViewPattern = viewPattern;
            lastViewPattern.mIView.onViewLoad();
        }

        if (lastViewPattern != null) {
            lastViewPattern.mIView.onViewShow();
        }

        mLastShowViewPattern = lastViewPattern;
    }

    @Override
    public View startIView(final IView iView, boolean needAnim) {

        //1:inflateContentView, 会返回对应IView的RootLayout
        View rawView = loadViewInternal(iView);
        //2:loadContentView
        iView.loadContentView(rawView);

        final ViewPattern lastViewPattern = getLastViewPattern();
        final ViewPattern newViewPattern = new ViewPattern(iView, rawView);
        mAttachViews.push(newViewPattern);

        startIViewAnim(lastViewPattern, newViewPattern, needAnim);

        return rawView;
    }

    @Override
    public View startIView(IView iView) {
        return startIView(iView, true);
    }

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

        viewPattern.mIView.onViewHide();
        if (needAnim) {
            viewPattern.isAnimToEnd = true;
            startViewPatternAnim(viewPattern, lastViewPattern, true, true);
            startViewPatternAnim(viewPattern, lastViewPattern, false, false);
        } else {
            if (lastViewPattern != null) {
                lastViewPattern.mIView.onViewShow();
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
    public void showIView(final View view, boolean needAnim) {
        final ViewPattern viewPattern = findViewPatternByView(view);
        viewPattern.mView.setVisibility(VISIBLE);

        if (viewPattern.mIView.isDialog()) {
            startDialogAnim(viewPattern, needAnim ? viewPattern.mIView.loadShowAnimation() : null);
        } else {
            if (mLastShowViewPattern != null) {
                safeStartAnim(mLastShowViewPattern.mView, needAnim ? mLastShowViewPattern.mIView.loadHideAnimation() : null, new Runnable() {
                    @Override
                    public void run() {
                        mLastShowViewPattern.mView.setVisibility(GONE);
                        mLastShowViewPattern.mIView.onViewHide();
                    }
                });
            }
            safeStartAnim(view, needAnim ? viewPattern.mIView.loadShowAnimation() : null, new Runnable() {
                @Override
                public void run() {
                    viewPattern.mIView.onViewShow();
                    mLastShowViewPattern = viewPattern;
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
                    viewPattern.mView.setVisibility(GONE);
                    viewPattern.mIView.onViewHide();
                }
            });
        } else {
            //隐藏的时候, 会错乱!!! 找不到之前显示的View, 慎用隐藏...
            safeStartAnim(view, needAnim ? viewPattern.mIView.loadHideAnimation() : null, new Runnable() {
                @Override
                public void run() {
                    viewPattern.mView.setVisibility(GONE);
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
            }
        });
    }

    @Override
    public void replaceIView(IView iView) {
        replaceIView(iView, true);
    }

    protected ViewPattern getLastViewPattern() {
        if (mAttachViews.isEmpty()) {
            return null;
        }
        return mAttachViews.lastElement();
    }

    private void startIViewAnim(final ViewPattern lastViewPattern, final ViewPattern newViewPattern, boolean needAnim) {
        if (isAttachedToWindow) {
            mLastShowViewPattern = newViewPattern;
            if (newViewPattern != null) {
                newViewPattern.mIView.onViewLoad();
            }

            if (needAnim) {
                startViewPatternAnim(newViewPattern, lastViewPattern, false, true);
                startViewPatternAnim(newViewPattern, lastViewPattern, true, false);
            } else {
                if (lastViewPattern != null) {
                    lastViewPattern.mIView.onViewHide();
                }
                if (newViewPattern != null) {
                    newViewPattern.mIView.onViewShow();
                }
            }
        }
    }

    /**
     * 启动之前, 开始View的动画
     * 新界面, 播放启动动画;
     * 旧界面, 播放伴随退出动画
     */
    private void startViewPatternAnim(final ViewPattern newViewPattern, final ViewPattern lastViewPattern, boolean isStart, boolean withOther) {
        if (newViewPattern == null) {
            return;
        }

        if (withOther) {
            if (newViewPattern.mIView.isDialog()) {
                //对话框不产生伴随动画.
            } else {
                //伴随, 需要产生的动画
                if (isStart) {
                    //上层的View退出, 下层的View进入的动画
                    final Animation animation = newViewPattern.mIView.loadOtherFinishEnterAnimation();

                    safeStartAnim(lastViewPattern.mView, animation, new Runnable() {
                        @Override
                        public void run() {
                            lastViewPattern.mIView.onViewShow();
                        }
                    });
                } else if (lastViewPattern != null) {
                    //上层的View进入, 下层的View退出的动画
                    final Animation animation = newViewPattern.mIView.loadOtherStartExitAnimation();
                    safeStartAnim(lastViewPattern.mView, animation, new Runnable() {
                        @Override
                        public void run() {
                            lastViewPattern.mIView.onViewHide();
                        }
                    });
                }
            }
        } else {
            if (newViewPattern.mIView.isDialog()) {
                if (isStart) {
                    //对话框的启动动画,作用在第一个子View上
                    startDialogAnim(newViewPattern, newViewPattern.mIView.loadStartAnimation());
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
    private void startDialogAnim(final ViewPattern dialogPattern, final Animation animation) {
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

        safeStartAnim(((ViewGroup) dialogPattern.mView).getChildAt(0), animation, new Runnable() {
            @Override
            public void run() {
                dialogPattern.mIView.onViewShow();
            }
        });
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
                dialogPattern.mView.setVisibility(GONE);
                end.run();
            }
        };

        safeStartAnim(animView, animation, endRunnable);
    }

    /**
     * 安全的启动一个动画
     */
    private void safeStartAnim(final View view, final Animation animation, final Runnable endRunnable) {
        if (view == null) {
            return;
        }

        if (animation == null) {
            if (endRunnable != null) {
                endRunnable.run();
            }
            return;
        }

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (endRunnable != null) {
                    endRunnable.run();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(animation);
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
}
