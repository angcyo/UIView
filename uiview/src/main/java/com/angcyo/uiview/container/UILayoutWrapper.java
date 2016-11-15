package com.angcyo.uiview.container;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.angcyo.uiview.model.ViewPattern;
import com.angcyo.uiview.view.IView;

import java.util.Stack;

/**
 * 可以用来显示IView的布局, 每一层的管理
 * Created by angcyo on 2016-11-12.
 */

public class UILayoutWrapper extends FrameLayout implements ILayout {

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


    public UILayoutWrapper(Context context) {
        super(context);
    }

    public UILayoutWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UILayoutWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UILayoutWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

        View rawView = loadViewInternal(iView);

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

        final View view = iView.inflateContentView(context, this, this, LayoutInflater.from(context));
        iView.onViewCreate();

        View rawView;

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
        viewPattern.mIView.onViewHide();
        if (needAnim) {
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
                view.setVisibility(VISIBLE);
                viewPattern.mIView.onViewShow();
            }
        });
    }

    @Override
    public void showIView(View view) {
        showIView(view, true);
    }

    @Override
    public void hideIView(final View view, boolean needAnim) {

    }

    @Override
    public void hideIView(View view) {
        hideIView(view, true);
    }

    @Override
    public View getView() {
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
    }
}
