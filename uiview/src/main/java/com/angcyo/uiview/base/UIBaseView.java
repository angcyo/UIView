package com.angcyo.uiview.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.angcyo.uiview.R;
import com.angcyo.uiview.container.UILayoutImpl;
import com.angcyo.uiview.container.UITitleBarContainer;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.resources.ResUtil;
import com.angcyo.uiview.view.UIIViewImpl;
import com.angcyo.uiview.widget.EmptyView;
import com.angcyo.uiview.widget.SoftRelativeLayout;

import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * 实现了 空布局, 无网络布局, 数据加载布局, 内容布局之间的切换
 * <p>
 * 内容布局会在 {@link #showContentLayout()} 之后才显示
 * <p>
 * Created by angcyo on 2016-11-27.
 */

public abstract class UIBaseView extends UIIViewImpl {

    public static int mUITitleBarId = View.NO_ID;
    public static int mBaseRootId = View.NO_ID;
    public static int mBaseContentRootId = View.NO_ID;
    /**
     * 根布局,和父类中的 {@link #mRootView} 相同, 包含标题栏
     */
    protected SoftRelativeLayout mBaseRootLayout;
    /**
     * 所有内容的根布局, 不包含标题栏
     */
    protected FrameLayout mBaseContentRootLayout;
    /**
     * 空布局
     */
    protected View mBaseEmptyLayout;
    /**
     * 无网络布局
     */
    protected View mBaseNonetLayout;
    /**
     * 加载数据的布局
     */
    protected View mBaseLoadLayout;
    /**
     * 内容布局
     */
    protected RelativeLayout mBaseContentLayout;
    /**
     * 标题
     */
    protected UITitleBarContainer mUITitleBarContainer;
    protected LayoutState mLayoutState = LayoutState.NORMAL;
    protected View.OnClickListener mNonetSettingClickListener, mNonetRefreshClickListener;
    private Animation mLoadingAnimation;

    @Override
    protected View inflateBaseView(FrameLayout container, LayoutInflater inflater) {
        //包含标题栏的根布局
        mBaseRootLayout = new SoftRelativeLayout(mActivity);
        mBaseRootId = R.id.base_root_layout_id;//View.generateViewId();
        mBaseRootLayout.setId(mBaseRootId);

        mBaseRootLayout.setBackgroundColor(getDefaultBackgroundColor());

        TitleBarPattern titleBarPattern = getTitleBar();
        if (titleBarPattern != null) {
            mUITitleBarContainer = new UITitleBarContainer(mActivity);
            mUITitleBarId = R.id.base_root_title_id;//View.generateViewId();
            mUITitleBarContainer.setId(mUITitleBarId);
            mUITitleBarContainer.setTitleBarPattern(titleBarPattern);
            mUITitleBarContainer.onAttachToLayout(mILayout);//note
        }

        //内容根布局, 包含空布局,加载布局等
        mBaseContentRootLayout = new FrameLayout(mActivity);
        mBaseContentRootId = R.id.base_root_content_id;//View.generateViewId();
        mBaseContentRootLayout.setId(mBaseContentRootId);

        //内容包裹布局
        mBaseContentLayout = new RelativeLayout(mActivity);
// 2016-12-18 使用懒加载的方式 加载.
        mBaseContentRootLayout.addView(mBaseContentLayout, new ViewGroup.LayoutParams(-1, -1));
//        mBaseEmptyLayout = UILayoutImpl.safeAssignView(mBaseContentRootLayout,
//                inflateEmptyLayout(mBaseContentRootLayout, inflater));//填充空布局
//        mBaseNonetLayout = UILayoutImpl.safeAssignView(mBaseContentRootLayout,
//                inflateNonetLayout(mBaseContentRootLayout, inflater));//填充无网络布局
//        mBaseLoadLayout = UILayoutImpl.safeAssignView(mBaseContentRootLayout,
//                inflateLoadLayout(mBaseContentRootLayout, inflater));//填充加载布局
//
//        safeSetView(mBaseContentLayout);
//        safeSetView(mBaseEmptyLayout);
//        safeSetView(mBaseNonetLayout);
//        safeSetView(mBaseLoadLayout);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -1);
        mBaseRootLayout.addView(mBaseContentRootLayout, params);

        if (titleBarPattern != null) {
            mBaseRootLayout.addView(mUITitleBarContainer, new ViewGroup.LayoutParams(-1, -2));

            if (titleBarPattern.isFloating) {
                if (titleBarPattern.isFixContentHeight) {
                    mBaseContentRootLayout.setPadding(mBaseContentRootLayout.getPaddingLeft(),
                            mBaseContentRootLayout.getPaddingTop() + mActivity.getResources().getDimensionPixelOffset(R.dimen.title_bar_height),
                            mBaseContentRootLayout.getPaddingRight(), mBaseContentRootLayout.getPaddingBottom());
                }
            } else {
                params.addRule(RelativeLayout.BELOW, mUITitleBarId);
            }
        }

        container.addView(mBaseRootLayout, new ViewGroup.LayoutParams(-1, -1));
        return mBaseRootLayout;
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);
        LayoutState state = getDefaultLayoutState();
        if (state == LayoutState.CONTENT) {
            showContentLayout();
            startLayoutAnim(mBaseContentLayout);
        } else if (state == LayoutState.LOAD) {
            showLoadLayout();
        } else if (state == LayoutState.EMPTY) {
            showEmptyLayout();
        } else if (state == LayoutState.NONET) {
            showNonetLayout();
        }
    }

    @NonNull
    protected LayoutState getDefaultLayoutState() {
        return LayoutState.LOAD;
    }

    /**
     * 请不要在此方法中初始化内容, 因为ButterKnife.bind(this, mBaseContentLayout);还么有执行
     */
    protected abstract void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater);

    /**
     * 初始化内容, 当你的 默认布局状态不等于 {@link LayoutState#CONTENT} 时,请使用以下方法初始化内容
     */
    protected void initContentLayout() {

    }

    protected void inflate(@LayoutRes int layoutId) {
        LayoutInflater.from(mActivity).inflate(layoutId, mBaseContentLayout);
    }

    protected View inflateLoadLayout(FrameLayout baseRootLayout, LayoutInflater inflater) {
//        return inflater.inflate(R.layout.base_load_layout, baseRootLayout);
        EmptyView emptyView = new EmptyView(mActivity);
        int padding = (int) ResUtil.dpToPx(mActivity, 20);
        emptyView.setPadding(padding, padding, padding, padding);
        baseRootLayout.addView(emptyView, new ViewGroup.LayoutParams(-1, -1));
        return emptyView;
    }

    protected View inflateNonetLayout(FrameLayout baseRootLayout, LayoutInflater inflater) {
        return inflater.inflate(R.layout.base_nonet_layout, baseRootLayout);
    }

    protected View inflateEmptyLayout(FrameLayout baseRootLayout, LayoutInflater inflater) {
        return inflater.inflate(R.layout.base_empty_layout, baseRootLayout);
    }

    protected TitleBarPattern getTitleBar() {
        return TitleBarPattern.build(getTitle()).setTitleBarBGColor(mActivity.getResources().getColor(R.color.theme_color_primary));
    }

    protected String getTitle() {
        return mActivity.getTitle().toString();
    }

    /**
     * 当布局的显示状态发生了改变
     */
    protected void onLayoutStateChanged(LayoutState fromState, LayoutState toState) {
        if (fromState == LayoutState.LOAD && mBaseLoadLayout != null) {
        }
        if (toState == LayoutState.LOAD && mBaseLoadLayout != null) {

        } else if (toState == LayoutState.NONET) {
            mBaseNonetLayout.findViewById(R.id.base_setting_view).setOnClickListener(mNonetSettingClickListener);
            mBaseNonetLayout.findViewById(R.id.base_refresh_view).setOnClickListener(mNonetRefreshClickListener);
        }
    }

    /**
     * 显示装载布局
     */
    public void showLoadLayout() {
        if (mBaseLoadLayout == null) {
            mBaseLoadLayout = UILayoutImpl.safeAssignView(mBaseContentRootLayout,
                    inflateLoadLayout(mBaseContentRootLayout, LayoutInflater.from(mActivity)));//填充加载布局
        }
        changeState(mLayoutState, LayoutState.LOAD);
    }

    /**
     * 显示空布局
     */
    public void showEmptyLayout() {
        if (mBaseEmptyLayout == null) {
            mBaseEmptyLayout = UILayoutImpl.safeAssignView(mBaseContentRootLayout,
                    inflateEmptyLayout(mBaseContentRootLayout, LayoutInflater.from(mActivity)));//填充空布局
        }
        changeState(mLayoutState, LayoutState.EMPTY);
    }

    /**
     * 显示无网络布局
     */
    public void showNonetLayout() {
        if (mBaseNonetLayout == null) {
            mBaseNonetLayout = UILayoutImpl.safeAssignView(mBaseContentRootLayout,
                    inflateNonetLayout(mBaseContentRootLayout, LayoutInflater.from(mActivity)));//填充无网络布局
        }
        showNonetLayout(null, null);
    }

    public void showNonetLayout(View.OnClickListener settingListener, View.OnClickListener refreshListener) {
        mNonetSettingClickListener = settingListener;
        mNonetRefreshClickListener = refreshListener;
        changeState(mLayoutState, LayoutState.NONET);
    }

    /**
     * 显示内容布局
     */
    public void showContentLayout() {
        if (mBaseContentLayout.getChildCount() == 0) {
            inflateContentLayout(mBaseContentLayout, LayoutInflater.from(mActivity));
            ButterKnife.bind(this, mBaseContentLayout);
            initContentLayout();
        }
        changeState(mLayoutState, LayoutState.CONTENT);
    }

    protected Animation loadLoadingAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        setDefaultConfig(rotateAnimation);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(1000);
        return rotateAnimation;
    }

    protected void fixInsersTop() {
        mBaseRootLayout.fixInsertsTop();
    }


    //-----------------以下私有方法------------------//

    private void safeSetView(View view) {
        if (view != null) {
            view.setVisibility(GONE);
            //mBaseRootLayout.addView(view, new ViewGroup.LayoutParams(-1, -1));
        }
    }

    /**
     * 改变布局的状态
     */
    private void changeState(LayoutState from, LayoutState to) {
        if (from == to) {
            return;
        }
        mLayoutState = to;
        updateLayoutState();
        onLayoutStateChanged(from, to);
    }

    private void updateLayoutState() {
        if (mLayoutState == LayoutState.LOAD) {
            safeSetVisibility(mBaseContentLayout, View.GONE);
            safeSetVisibility(mBaseEmptyLayout, View.GONE);
            safeSetVisibility(mBaseNonetLayout, View.GONE);
            safeUpdateLayoutState(mBaseLoadLayout, View.VISIBLE);
        } else if (mLayoutState == LayoutState.EMPTY) {
            safeSetVisibility(mBaseContentLayout, View.GONE);
            safeSetVisibility(mBaseLoadLayout, View.GONE);
            safeSetVisibility(mBaseNonetLayout, View.GONE);
            safeUpdateLayoutState(mBaseEmptyLayout, View.VISIBLE);
        } else if (mLayoutState == LayoutState.NONET) {
            safeSetVisibility(mBaseContentLayout, View.GONE);
            safeSetVisibility(mBaseEmptyLayout, View.GONE);
            safeSetVisibility(mBaseLoadLayout, View.GONE);
            safeUpdateLayoutState(mBaseNonetLayout, View.VISIBLE);
        } else if (mLayoutState == LayoutState.CONTENT) {
            safeSetVisibility(mBaseLoadLayout, View.GONE);
            safeSetVisibility(mBaseEmptyLayout, View.GONE);
            safeSetVisibility(mBaseNonetLayout, View.GONE);
            safeUpdateLayoutState(mBaseContentLayout, View.VISIBLE);
        }
    }

    private void safeUpdateLayoutState(View view, int visibility) {
        if (view == null) {
            showContentLayout();
            return;
        }
        view.setVisibility(visibility);
    }

    private void safeSetVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    public UITitleBarContainer getUITitleBarContainer() {
        return mUITitleBarContainer;
    }

    public UIBaseView showLoadView() {
        if (mUITitleBarContainer != null) {
            mUITitleBarContainer.showLoadView();
        }
        return this;
    }

    public UIBaseView hideLoadView() {
        if (mUITitleBarContainer != null) {
            mUITitleBarContainer.hideLoadView();
        }
        return this;
    }

    /**
     * 设置标题文本
     */
    public void setTitleString(String title) {
        if (mUITitleBarContainer != null) {
            mUITitleBarContainer.getTitleView().setText(title);
        }
    }

    /**
     * 显示键盘
     */
    public void showSoftInput() {
        if (isSoftKeyboardShow()) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInputFromInputMethod(mActivity.getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 隐藏键盘
     */
    public void hideSoftInput() {
        if (isSoftKeyboardShow()) {
            InputMethodManager manager = (InputMethodManager) mActivity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(mActivity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 判断键盘是否显示
     */
    public boolean isSoftKeyboardShow() {
        int screenHeight = mActivity.getResources().getDisplayMetrics().heightPixels;
        int keyboardHeight = getSoftKeyboardHeight();
        return screenHeight != keyboardHeight && keyboardHeight > 100;
    }

    /**
     * 获取键盘的高度
     */
    public int getSoftKeyboardHeight() {
        int screenHeight = mActivity.getResources().getDisplayMetrics().heightPixels;
        Rect rect = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int visibleBottom = rect.bottom;
        return screenHeight - visibleBottom;
    }


    @ColorInt
    public int getDefaultBackgroundColor() {
        return Color.TRANSPARENT;
    }

    /**
     * 指示当前布局的显示状态, 当前那个布局在显示
     */
    public enum LayoutState {
        NORMAL,//正常
        EMPTY,//空布局
        LOAD,//装载布局
        NONET,//无网络
        CONTENT //内容
    }
}
