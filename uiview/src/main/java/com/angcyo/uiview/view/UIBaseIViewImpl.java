package com.angcyo.uiview.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.container.ILayout;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.resources.AnimUtil;

import butterknife.ButterKnife;

/**
 * Created by angcyo on 2016-11-12.
 */

public abstract class UIBaseIViewImpl implements IView {

    public static final int DEFAULT_ANIM_TIME = 300;

    protected ILayout mILayout;
    protected Context mContext;
    protected View mRootView;

    @Override
    public TitleBarPattern loadTitleBar(Context context) {
        L.i(this.getClass().getSimpleName(), "loadTitleBar: ");
        return null;
    }

    @Override
    public View inflateContentView(Context context, ILayout iLayout, FrameLayout container, LayoutInflater inflater) {
        L.i(this.getClass().getSimpleName(), "inflateContentView: ");
        mContext = context;
        mILayout = iLayout;
        return inflateBaseView(container, inflater);
    }

    protected abstract View inflateBaseView(FrameLayout container, LayoutInflater inflater);

    @Override
    public void loadContentView(View rootView) {
        L.i(this.getClass().getSimpleName(), "loadContentView: ");
        mRootView = rootView;
        if (mRootView instanceof ViewGroup) {
            final int childCount = ((ViewGroup) mRootView).getChildCount();
            if (childCount == 1) {
                final View firstView = ((ViewGroup) mRootView).getChildAt(0);
                if (firstView instanceof ViewGroup) {
                    AnimUtil.applyLayoutAnimation((ViewGroup) firstView, loadLayoutAnimation());
                }
            }
        }
        ButterKnife.bind(this, mRootView);
    }

    @Override
    public void onViewCreate() {
        L.i(this.getClass().getSimpleName(), "onViewCreate: ");

    }

    @Override
    public void onViewLoad() {
        L.i(this.getClass().getSimpleName(), "onViewLoad: ");

    }

    @Override
    public void onViewShow() {
        L.i(this.getClass().getSimpleName(), "onViewShow: ");

    }

    @Override
    public void onViewHide() {
        L.i(this.getClass().getSimpleName(), "onViewHide: ");

    }

    @Override
    public void onViewUnload() {
        L.i(this.getClass().getSimpleName(), "onViewUnload: ");
    }

    @Override
    public Animation loadStartAnimation() {
        L.i(this.getClass().getSimpleName(), "loadStartAnimation: ");
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }

    @Override
    public Animation loadFinishAnimation() {
        L.i(this.getClass().getSimpleName(), "loadFinishAnimation: ");
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }

    @Override
    public Animation loadShowAnimation() {
        L.i(this.getClass().getSimpleName(), "loadShowAnimation: ");
        return loadStartAnimation();
    }

    @Override
    public Animation loadHideAnimation() {
        L.i(this.getClass().getSimpleName(), "loadHideAnimation: ");
        return loadFinishAnimation();
    }

    @Override
    public Animation loadOtherStartExitAnimation() {
        L.i(this.getClass().getSimpleName(), "loadOtherStartExitAnimation: ");
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }

    @Override
    public Animation loadOtherFinishEnterAnimation() {
        L.i(this.getClass().getSimpleName(), "loadOtherFinishEnterAnimation: ");
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }

    @Override
    public Animation loadLayoutAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f,
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f);
        setDefaultConfig(translateAnimation);
        return translateAnimation;
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

    protected void setDefaultConfig(Animation animation) {
        animation.setDuration(DEFAULT_ANIM_TIME);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setFillAfter(true);
    }

    @Override
    public View getView() {
        return mRootView;
    }
}
