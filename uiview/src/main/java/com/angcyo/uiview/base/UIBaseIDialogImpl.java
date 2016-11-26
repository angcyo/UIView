package com.angcyo.uiview.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.angcyo.uiview.view.UIBaseIViewImpl;

/**
 * Created by angcyo on 2016-11-15.
 */

public abstract class UIBaseIDialogImpl extends UIBaseIViewImpl {

    protected RelativeLayout mDialogRootLayout;

    @Override
    protected View inflateBaseView(FrameLayout container, LayoutInflater inflater) {
        mDialogRootLayout = new RelativeLayout(mContext);
        container.addView(mDialogRootLayout, new ViewGroup.LayoutParams(-1, -1));
        return inflateDialogView(mDialogRootLayout, inflater);
    }

    protected abstract View inflateDialogView(RelativeLayout dialogRootLayout, LayoutInflater inflater);

    @Override
    public boolean isDialog() {
        return true;
    }

    @Override
    public Animation loadStartAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        setDefaultConfig(translateAnimation);
        setDefaultConfig(alphaAnimation);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        return animationSet;
    }

    @Override
    public Animation loadFinishAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        setDefaultConfig(translateAnimation);
        setDefaultConfig(alphaAnimation);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        return animationSet;
    }

    @Override
    public Animation loadLayoutAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 1f, Animation.RELATIVE_TO_PARENT, 0f);
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }
}
