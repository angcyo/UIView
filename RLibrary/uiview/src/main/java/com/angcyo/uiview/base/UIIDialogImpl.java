package com.angcyo.uiview.base;

import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.angcyo.uiview.container.ILayout;
import com.angcyo.uiview.container.UILayoutImpl;
import com.angcyo.uiview.view.UIIViewImpl;
import com.angcyo.uiview.widget.SoftRelativeLayout;

import java.util.ArrayList;

/**
 * 自定义对话框的基类
 * <p>
 * Created by angcyo on 2016-11-15.
 */

public abstract class UIIDialogImpl extends UIIViewImpl {

    protected SoftRelativeLayout mDialogRootLayout;

    /**
     * 对话框显示的重力
     */
    protected int gravity = Gravity.BOTTOM;

    /**
     * 是否激活布局动画
     */
    protected boolean layoutAnim = false;

    /**
     * 是否可以取消对话框
     */
    protected boolean canCancel = true;

    /**
     * 窗口外是否可点击
     */
    protected boolean canTouchOnOutside = true;

    /**
     * 点击窗口外,是否可以取消对话框, 需要 {@link #canTouchOnOutside} 为true
     */
    protected boolean canCanceledOnOutside = true;

    /**
     * 对话框外, 是否变暗
     */
    protected boolean isDimBehind = true;

    /**
     * 设置布局动画
     */
    protected Animation layoutAnimation = null;

    protected ArrayList<OnDismissListener> mOnDismissListeners = new ArrayList<>();

    @Override
    protected View inflateBaseView(FrameLayout container, LayoutInflater inflater) {
        mDialogRootLayout = new SoftRelativeLayout(mActivity);
        container.addView(mDialogRootLayout, new ViewGroup.LayoutParams(-1, -1));
        View xmlRootView = UILayoutImpl.safeAssignView(mDialogRootLayout,
                inflateDialogView(mDialogRootLayout, inflater));
        xmlRootView.setClickable(true);
        mDialogRootLayout.setGravity(getGravity());
        return mDialogRootLayout;
    }

    protected View inflate(@LayoutRes int layoutId) {
        return LayoutInflater.from(mActivity).inflate(layoutId, mDialogRootLayout);
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);
        startLayoutAnim(mDialogRootLayout);
        initDialogContentView();
    }

    /**
     * 初始化内容
     */
    protected void initDialogContentView() {

    }

    /**
     * 对话框的背景需要用来执行变暗的动画
     */
    @Override
    public View getDialogDimView() {
        return mDialogRootLayout;
    }

    /**
     * 需要实现的方法
     */
    protected abstract View inflateDialogView(RelativeLayout dialogRootLayout, LayoutInflater inflater);

    @Override
    public boolean isDialog() {
        return true;
    }

    @Override
    public boolean canTryCaptureView() {
        return false;
    }

    public int getGravity() {
        return gravity;
    }

    public UIIDialogImpl setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public UIIDialogImpl setLayoutAnim(boolean layoutAnim) {
        this.layoutAnim = layoutAnim;
        return this;
    }

    public UIIDialogImpl setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
        return this;
    }

    public UIIDialogImpl setCanTouchOnOutside(boolean canTouchOnOutside) {
        this.canTouchOnOutside = canTouchOnOutside;
        return this;
    }

    public UIIDialogImpl setCanCanceledOnOutside(boolean canCanceledOnOutside) {
        this.canCanceledOnOutside = canCanceledOnOutside;
        return this;
    }

    public UIIDialogImpl setLayoutAnimation(Animation layoutAnimation) {
        this.layoutAnimation = layoutAnimation;
        return this;
    }

    /**
     * 结束对话框
     */
    protected void finishDialog() {
        mILayout.finishIView(this);
    }

    @Override
    public boolean canCancel() {
        return canCancel;
    }

    @Override
    public boolean canTouchOnOutside() {
        return canTouchOnOutside;
    }

    @Override
    public boolean canCanceledOnOutside() {
        return canCanceledOnOutside;
    }

    @Override
    public boolean isDimBehind() {
        return isDimBehind;
    }

    public UIIDialogImpl setDimBehind(boolean dimBehind) {
        isDimBehind = dimBehind;
        return this;
    }

    /**
     * 对话框启动时的动画
     */
    @Override
    public Animation loadStartAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        setDefaultConfig(translateAnimation, false);
        setDefaultConfig(alphaAnimation, false);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        return animationSet;
    }

    /**
     * 对话框结束时的动画
     */
    @Override
    public Animation loadFinishAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        setDefaultConfig(translateAnimation, true);
        setDefaultConfig(alphaAnimation, true);

        translateAnimation.setDuration(DEFAULT_DIALOG_FINISH_ANIM_TIME);
        alphaAnimation.setDuration(DEFAULT_DIALOG_FINISH_ANIM_TIME);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        return animationSet;
    }

    /**
     * 对话框的布局动画
     */
    @Override
    public Animation loadLayoutAnimation() {
        if (layoutAnim) {
            if (layoutAnimation == null) {
                TranslateAnimation translateAnimation = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f,
                        Animation.RELATIVE_TO_PARENT, 1f, Animation.RELATIVE_TO_PARENT, 0f);
                setDefaultConfig(translateAnimation, false);
                translateAnimation.setDuration(160);
                return translateAnimation;
            } else {
                return layoutAnimation;
            }
        }
        return null;
    }

    @Override
    public void onViewUnload() {
        super.onViewUnload();
        for (OnDismissListener listener : mOnDismissListeners) {
            listener.onDismiss();
        }
    }

    /**
     * 背景执行了变暗动画, 所以真正的动画需要在子View上执行
     */
    @Override
    public View getAnimView() {
        return mDialogRootLayout.getChildAt(0);
    }

    public UIIDialogImpl addDismissListener(OnDismissListener dismissListener) {
        mOnDismissListeners.add(dismissListener);
        return this;
    }

    public UIIDialogImpl removeDismissListener(OnDismissListener dismissListener) {
        mOnDismissListeners.remove(dismissListener);
        return this;
    }

    public UIIDialogImpl showDialog(UIIViewImpl iView) {
        iView.startIView(this);
        return this;
    }

    public UIIDialogImpl showDialog(ILayout iLayout) {
        iLayout.startIView(this);
        return this;
    }

    public interface OnDismissListener {
        void onDismiss();
    }
}
