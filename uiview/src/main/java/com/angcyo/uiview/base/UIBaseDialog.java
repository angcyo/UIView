package com.angcyo.uiview.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

/**
 * Created by angcyo on 2016-11-15.
 */

public abstract class UIBaseDialog extends UIBaseView {

    protected FrameLayout mDialogRootLayout;

    @Override
    protected View inflateBaseView(FrameLayout container, LayoutInflater inflater) {
        mDialogRootLayout = new FrameLayout(mContext);
        container.addView(mDialogRootLayout, new ViewGroup.LayoutParams(-1, -1));
        return inflateDialogView(mDialogRootLayout, inflater);
    }

    protected abstract View inflateDialogView(FrameLayout dialogRootLayout, LayoutInflater inflater);

    @Override
    public boolean isDialog() {
        return true;
    }

    @Override
    public Animation loadStartAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f);
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }

    @Override
    public Animation loadFinishAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }
}
