package com.angcyo.uiview.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.container.ILayout;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.view.IView;

import butterknife.ButterKnife;

/**
 * Created by angcyo on 2016-11-12.
 */

public class UIBaseView implements IView {

    public static final int DEFAULT_ANIM_TIME = 300;

    protected ILayout mILayout;
    protected Context mContext;

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
        return null;
    }

    @Override
    public void loadContentView(View rootView) {
        L.i(this.getClass().getSimpleName(), "loadContentView: ");
        ButterKnife.bind(this, rootView);
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
        return null;
    }

    @Override
    public Animation loadHideAnimation() {
        L.i(this.getClass().getSimpleName(), "loadHideAnimation: ");
        return null;
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

    private void setDefaultConfig(Animation animation) {
        animation.setDuration(DEFAULT_ANIM_TIME);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setFillAfter(true);
    }
}
