package com.angcyo.uiview.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.base.UILayoutActivity;
import com.angcyo.uiview.container.ILayout;
import com.angcyo.uiview.container.UILayoutImpl;
import com.angcyo.uiview.container.UIParam;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RRecyclerView;
import com.angcyo.uiview.resources.AnimUtil;
import com.angcyo.uiview.resources.ResUtil;
import com.angcyo.uiview.skin.ISkin;
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
    public static final int DEFAULT_FINISH_ANIM_TIME = 300;
    public static final int DEFAULT_DIALOG_FINISH_ANIM_TIME = 150;
    public static final int STATE_NORMAL = 1;
    public static final int STATE_VIEW_SHOW = 3;
    public static final int STATE_VIEW_HIDE = 4;
    public static final int STATE_VIEW_LOAD = 2;
    public static final int STATE_VIEW_UNLOAD = 5;

    protected ILayout mILayout;
    protected ILayout mOtherILayout;
    protected UILayoutActivity mActivity;
    /**
     * 根布局
     */
    protected View mRootView;

    /**
     * 用来管理rootView
     */
    protected RBaseViewHolder mViewHolder;
    protected int mIViewStatus = STATE_NORMAL;
    /**
     * 最后一次显示的时间
     */
    protected long mLastShowTime = 0;
    /**
     * 当{@link #onViewShow(Bundle)}被调用一次, 计数器就会累加
     */
    private long viewShowCount = 0;
    private boolean mIsRightJumpLeft = false;

    public static void setDefaultConfig(Animation animation, boolean isFinish) {
        if (isFinish) {
            animation.setDuration(DEFAULT_FINISH_ANIM_TIME);
            animation.setInterpolator(new AccelerateInterpolator());
        } else {
            animation.setDuration(DEFAULT_ANIM_TIME);
            animation.setInterpolator(new DecelerateInterpolator());
        }
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
        if (mOtherILayout == null) {
            mOtherILayout = iLayout;
        }
    }

    @Override
    public View inflateContentView(UILayoutActivity activity, ILayout iLayout, FrameLayout container, LayoutInflater inflater) {
        L.d(this.getClass().getSimpleName(), "inflateContentView: ");
        mActivity = activity;
        return inflateBaseView(container, inflater);
    }

    protected abstract View inflateBaseView(FrameLayout container, LayoutInflater inflater);

    @CallSuper
    @Override
    public void loadContentView(View rootView) {
        L.d(this.getClass().getSimpleName(), "loadContentView: ");
        try {
            ButterKnife.bind(this, rootView);
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

    @CallSuper
    @Override
    @Deprecated
    public void onViewCreate(View rootView) {
        L.d(this.getClass().getSimpleName(), "onViewCreate: ");
        mRootView = rootView;
        mViewHolder = new RBaseViewHolder(mRootView);
    }

    @Override
    public void onViewCreate(View rootView, UIParam param) {
        L.d(this.getClass().getSimpleName(), "onViewCreate 2: ");
    }

    @CallSuper
    @Override
    public void onViewLoad() {
        mIViewStatus = STATE_VIEW_LOAD;
        L.d(this.getClass().getSimpleName(), "onViewLoad: ");
    }

    @Deprecated
    @Override
    @CallSuper
    public void onViewShow() {
        onViewShow(null);
    }

    @CallSuper
    @Override
    public void onViewShow(Bundle bundle) {
        L.d(this.getClass().getSimpleName(), "onViewShow: ");
        long lastShowTime = mLastShowTime;
        viewShowCount++;
        mIViewStatus = STATE_VIEW_SHOW;
        mLastShowTime = System.currentTimeMillis();
        if (lastShowTime == 0) {
            onViewShowFirst(bundle);
        }
        onViewShow(viewShowCount);
    }

    public void onViewShowFirst(Bundle bundle) {
        L.d(this.getClass().getSimpleName(), "onViewShowFirst: ");
    }

    //星期五 2017-2-17
    public void onViewShow(long viewShowCount) {
        L.d(this.getClass().getSimpleName(), "onViewShowCount " + viewShowCount);
    }

    @CallSuper
    @Override
    public void onViewReShow(Bundle bundle) {
        L.d(this.getClass().getSimpleName(), "onViewReShow: ");
    }

    @CallSuper
    @Override
    public void onViewHide() {
        mIViewStatus = STATE_VIEW_HIDE;
        L.d(this.getClass().getSimpleName(), "onViewHide: ");
    }

    @CallSuper
    @Override
    public void onViewUnload() {
        mIViewStatus = STATE_VIEW_UNLOAD;
        L.d(this.getClass().getSimpleName(), "onViewUnload: ");
    }

    @Override
    public Animation loadStartAnimation() {
        L.v(this.getClass().getSimpleName(), "loadStartAnimation: ");
        TranslateAnimation translateAnimation;
        if (mIsRightJumpLeft) {
            translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        } else {
            translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        }
        setDefaultConfig(translateAnimation, false);
        return translateAnimation;
    }

    @Override
    public Animation loadFinishAnimation() {
        L.v(this.getClass().getSimpleName(), "loadFinishAnimation: ");
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        setDefaultConfig(translateAnimation, true);
        return translateAnimation;
    }

    @Override
    public Animation loadShowAnimation() {
        L.v(this.getClass().getSimpleName(), "loadShowAnimation: ");
        return loadStartAnimation();
    }

    @Override
    public Animation loadHideAnimation() {
        L.v(this.getClass().getSimpleName(), "loadHideAnimation: ");
        return loadFinishAnimation();
    }

    @Override
    public Animation loadOtherExitAnimation() {
        L.v(this.getClass().getSimpleName(), "loadOtherExitAnimation: ");
        TranslateAnimation translateAnimation;
        if (mIsRightJumpLeft) {
            translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        } else {
            translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        }
        setDefaultConfig(translateAnimation, false);
        return translateAnimation;
    }

    @Override
    public Animation loadOtherEnterAnimation() {
        L.v(this.getClass().getSimpleName(), "loadOtherEnterAnimation: ");
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        setDefaultConfig(translateAnimation, false);
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
        L.v(this.getClass().getSimpleName(), "loadLayoutAnimation: ");
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

    @Override
    public View getDialogDimView() {
        //请在对话框中实现
        return null;
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

    public void finishIView() {
        finishIView(this);
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

    public void removeCallbacks(Runnable action) {
        if (mRootView != null) {
            mRootView.removeCallbacks(action);
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
    public boolean canSwipeBackPressed() {
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public IView setIsRightJumpLeft(boolean isRightJumpLeft) {
        mIsRightJumpLeft = isRightJumpLeft;
        return this;
    }

    @Override
    public int getDimColor() {
        return Color.parseColor("#60000000");
    }

    @Override
    public View getAnimView() {
        return mRootView;
    }

    public IView bindOtherILayout(ILayout otherILayout) {
        mOtherILayout = otherILayout;
        return this;
    }

    @Override
    public boolean canTryCaptureView() {
        return true;
    }

    public Resources getResources() {
        return mActivity.getResources();
    }

    //星期二 2017-2-28
    public String getString(@StringRes int id) {
        return getResources().getString(id);
    }

    //星期二 2017-2-28
    public String getString(@StringRes int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    /**
     * 是否全屏
     *
     * @param enable 是
     */
    //星期三 2017-3-1
    public void fullscreen(boolean enable) {
        fullscreen(enable, false);
    }

    public void fullscreen(final boolean enable, boolean checkSdk) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final View decorView = mActivity.getWindow().getDecorView();
                if (enable) {
                    decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_FULLSCREEN);
                } else {
                    decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_FULLSCREEN);
                }
            }
        };

        if (checkSdk) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                runnable.run();
            }
        } else {
            runnable.run();
        }
    }

    //星期一 2017-3-13
    @ColorInt
    public int getColor(@ColorRes int id) {
        return ContextCompat.getColor(mActivity, id);
    }

    public Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(mActivity, id);//.mutate();
    }

    public int getDimensionPixelOffset(@DimenRes int id) {
        return getResources().getDimensionPixelOffset(id);
    }

    public float density() {
        return getResources().getDisplayMetrics().density;
    }

    public int widthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    public int heightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 冻结界面, 拦截所有Touch事件
     */
    public void setLayoutFrozen(boolean frozen) {
        if (mRootView != null) {
            mRootView.setEnabled(frozen);
        }
    }

    @Override
    public void onSkinChanged(ISkin skin) {
        L.v(this.getClass().getSimpleName(), "onSkinChanged: " + skin.skinName());
        notifySkinChanged(mRootView, skin);
    }

    protected void notifySkinChanged(View view, ISkin skin) {
        if (view != null) {
            if (view instanceof UILayoutImpl) {
                ((UILayoutImpl) view).onSkinChanged(skin);
            }
            if (view instanceof RecyclerView) {
                RRecyclerView.ensureGlow(((RecyclerView) view), skin.getThemeSubColor());
            }
            if (view instanceof ViewPager) {
                UIViewPager.ensureGlow((ViewPager) view, skin.getThemeSubColor());
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < (viewGroup).getChildCount(); i++) {
                    notifySkinChanged(viewGroup.getChildAt(i), skin);
                }
            }
        }
    }

    public float getTitleBarHeight() {
        float density = getResources().getDisplayMetrics().density;
        if (ResUtil.isLayoutFullscreen(mActivity)) {
            return density * 65f;
        } else {
            return density * 40f;
        }
    }
}
