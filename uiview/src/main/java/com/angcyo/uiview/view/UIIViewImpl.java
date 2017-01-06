package com.angcyo.uiview.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.container.ILayout;
import com.angcyo.uiview.container.UIParam;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.resources.AnimUtil;
import com.angcyo.uiview.widget.viewpager.UIViewPager;

import butterknife.ButterKnife;

/**
 * 接口的实现, 仅处理了一些动画, 其他实现都为空
 * 对对话框做了区分处理
 * <p>
 * Created by angcyo on 2016-11-12.
 */

public abstract class UIIViewImpl implements IView {

    public static final int DEFAULT_ANIM_TIME = 300;

    protected ILayout mILayout;
    protected ILayout mOtherILayout;
    protected AppCompatActivity mActivity;
    /**
     * 根布局
     */
    protected View mRootView;

    /**
     * 用来管理rootview
     */
    protected RBaseViewHolder mViewHolder;
    private boolean mIsRightJumpLeft = false;

    public static void setDefaultConfig(Animation animation) {
        animation.setDuration(DEFAULT_ANIM_TIME);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setFillAfter(false);
    }

    @Override
    public TitleBarPattern loadTitleBar(Context context) {
        L.d(this.getClass().getSimpleName(), "loadTitleBar: ");
        return null;
    }

    @Override
    public void onAttachedToILayout(ILayout iLayout) {
        mILayout = iLayout;
    }

    @Override
    public View inflateContentView(AppCompatActivity activity, ILayout iLayout, FrameLayout container, LayoutInflater inflater) {
        L.d(this.getClass().getSimpleName(), "inflateContentView: ");
        mActivity = activity;
        return inflateBaseView(container, inflater);
    }

    protected abstract View inflateBaseView(FrameLayout container, LayoutInflater inflater);

    @Override
    public void loadContentView(View rootView) {
        L.d(this.getClass().getSimpleName(), "loadContentView: ");
        mRootView = rootView;
        mViewHolder = new RBaseViewHolder(mRootView);
        try {
            ButterKnife.bind(this, mRootView);
        } catch (Exception e) {
        }
    }

    /**
     * 开始布局动画
     */
    public void startLayoutAnim(View parent) {
        final Animation layoutAnimation = loadLayoutAnimation();
        if (layoutAnimation != null && parent instanceof ViewGroup) {
            AnimUtil.applyLayoutAnimation(findChildViewGroup((ViewGroup) parent), layoutAnimation);
        }
    }

    /**
     * 查找具有多个子View的ViewGroup, 用来播放布局动画
     */
    private ViewGroup findChildViewGroup(ViewGroup parent) {
        final int childCount = parent.getChildCount();
        if (childCount == 1) {
            View childAt = parent.getChildAt(0);
            if (childAt instanceof ViewGroup) {
                return findChildViewGroup((ViewGroup) childAt);
            } else {
                return parent;
            }
        } else {
            return parent;
        }
    }

    @Override
    public void onViewCreate() {
        L.d(this.getClass().getSimpleName(), "onViewCreate: ");
    }

    @Override
    public void onViewLoad() {
        L.d(this.getClass().getSimpleName(), "onViewLoad: ");
    }

    @Deprecated
    @Override
    public void onViewShow() {
        onViewShow(null);
    }

    @Override
    public void onViewShow(Bundle bundle) {
        L.d(this.getClass().getSimpleName(), "onViewShow: ");
    }

    @Override
    public void onViewHide() {
        L.d(this.getClass().getSimpleName(), "onViewHide: ");
    }

    @Override
    public void onViewUnload() {
        L.d(this.getClass().getSimpleName(), "onViewUnload: ");
    }

    @Override
    public Animation loadStartAnimation() {
        L.d(this.getClass().getSimpleName(), "loadStartAnimation: ");
        TranslateAnimation translateAnimation;
        if (mIsRightJumpLeft) {
            translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        } else {
            translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        }
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }

    @Override
    public Animation loadFinishAnimation() {
        L.d(this.getClass().getSimpleName(), "loadFinishAnimation: ");
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }

    @Override
    public Animation loadShowAnimation() {
        L.d(this.getClass().getSimpleName(), "loadShowAnimation: ");
        return loadStartAnimation();
    }

    @Override
    public Animation loadHideAnimation() {
        L.d(this.getClass().getSimpleName(), "loadHideAnimation: ");
        return loadFinishAnimation();
    }

    @Override
    public Animation loadOtherExitAnimation() {
        L.d(this.getClass().getSimpleName(), "loadOtherExitAnimation: ");
        TranslateAnimation translateAnimation;
        if (mIsRightJumpLeft) {
            translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        } else {
            translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        }
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }

    @Override
    public Animation loadOtherEnterAnimation() {
        L.d(this.getClass().getSimpleName(), "loadOtherEnterAnimation: ");
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }

    @Override
    public Animation loadOtherHideAnimation() {
        return loadOtherExitAnimation();
    }

    @Override
    public Animation loadOtherShowAnimation() {
        return loadOtherEnterAnimation();
    }

    @Override
    public Animation loadLayoutAnimation() {
        L.d(this.getClass().getSimpleName(), "loadLayoutAnimation: ");
//        if (mIsRightJumpLeft) {
//
//        } else {
//
//        }
//
//        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f,
//                Animation.RELATIVE_TO_PARENT, 0f,
//                Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f);
//        setDefaultConfig(translateAnimation);
//        return translateAnimation;
        return null;
    }

    @Override
    public boolean isDialog() {
        return false;
    }

    @Override
    public boolean isDimBehind() {
        return true;
    }

    @Override
    public boolean canCanceledOnOutside() {
        return true;
    }

    @Override
    public boolean canTouchOnOutside() {
        return true;
    }

    @Override
    public boolean canCancel() {
        return true;
    }

    @Override
    public View getView() {
        return mRootView;
    }

    /**
     * 在inflateContentView之前调用, 返回的都是null
     */
    @Override
    public ILayout getILayout() {
        return mILayout;
    }

    /**
     * 此方法只在UIVIewPager中会调用, 当前IView显示时
     */
    @Override
    public void onShowInPager(UIViewPager viewPager) {
        L.i(this.getClass().getSimpleName(), "onShowInPager: ");
    }

    /**
     * 此方法只在UIVIewPager中会调, 当前IView隐藏时
     */
    @Override
    public void onHideInPager(UIViewPager viewPager) {
        L.i(this.getClass().getSimpleName(), "onHideInPager: ");
    }

    public void startIView(final IView iView) {
        startIView(iView, true);
    }

    public void startIView(final IView iView, boolean anim) {
        startIView(iView, new UIParam(anim));
    }

    public void startIView(final IView iView, final UIParam param) {
        if (iView == null) {
            return;
        }
        if (mILayout == null) {
            throw new IllegalArgumentException("ILayout 还未初始化");
        }
        mILayout.startIView(iView, param);
    }

    public void finishIView(final IView iView) {
        finishIView(iView, true);
    }

    public void finishIView(final IView iView, final UIParam param) {
        if (iView == null) {
            return;
        }
        if (mILayout == null) {
            throw new IllegalArgumentException("ILayout 还未初始化");
        }
        mILayout.finishIView(iView, param);
    }

    public void finishIView(final IView iView, boolean anim) {
        finishIView(iView, anim, false);
    }

    public void finishIView(final IView iView, boolean anim, boolean quiet) {
        if (iView == null) {
            return;
        }
        if (mILayout == null) {
            throw new IllegalArgumentException("ILayout 还未初始化");
        }
        mILayout.finishIView(iView, anim, quiet);
    }

    public void showIView(final View view) {
        showIView(view, true);
    }

    public void showIView(final View view, final boolean needAnim) {
        showIView(view, needAnim, null);
    }

    public void showIView(final View view, final boolean needAnim, final Bundle bundle) {
        if (view == null) {
            return;
        }
        if (mILayout == null) {
            throw new IllegalArgumentException("ILayout 还未初始化");
        }
        mILayout.showIView(view, needAnim, bundle);
    }

    public void showIView(IView iview, boolean needAnim) {
        showIView(iview, needAnim, null);
    }

    public void showIView(IView iview) {
        showIView(iview, true);
    }

    public void showIView(IView iview, boolean needAnim, Bundle bundle) {
        if (iview == null) {
            return;
        }
        if (mILayout == null) {
            throw new IllegalArgumentException("ILayout 还未初始化");
        }
        mILayout.showIView(iview, needAnim, bundle);
    }

    public void replaceIView(IView iView, boolean needAnim) {
        replaceIView(iView, new UIParam(needAnim));
    }

    public void replaceIView(IView iView) {
        replaceIView(iView, true);
    }

    public void replaceIView(IView iView, UIParam param) {
        if (iView == null) {
            return;
        }
        if (mILayout == null) {
            throw new IllegalArgumentException("ILayout 还未初始化");
        }
        mILayout.replaceIView(iView, param);
    }

    public void post(Runnable action) {
        if (mRootView != null) {
            mRootView.post(action);
        }
    }

    public void postDelayed(Runnable action, long delayMillis) {
        if (mRootView != null) {
            mRootView.postDelayed(action, delayMillis);
        }
    }

    /**
     * @return true 允许退出
     */
    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void setIsRightJumpLeft(boolean isRightJumpLeft) {
        mIsRightJumpLeft = isRightJumpLeft;
    }

    @Override
    public int getDimColor() {
        return Color.parseColor("#60000000");
    }

    public void bindOtherILayout(ILayout otherILayout) {
        mOtherILayout = otherILayout;
    }
}
