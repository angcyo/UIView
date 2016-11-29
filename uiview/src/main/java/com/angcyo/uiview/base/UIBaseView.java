package com.angcyo.uiview.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.angcyo.uiview.R;
import com.angcyo.uiview.container.UILayoutImpl;
import com.angcyo.uiview.view.UIIViewImpl;

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

    /**
     * 根布局,和父类中的 {@link #mRootView} 相同
     */
    protected FrameLayout mBaseRootLayout;

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

    protected LayoutState mLayoutState = LayoutState.NORMAL;

    protected View.OnClickListener mNonetSettingClickListener, mNonetRefreshClickListener;

    @Override
    protected View inflateBaseView(FrameLayout container, LayoutInflater inflater) {
        mBaseRootLayout = new FrameLayout(mContext);
        mBaseContentLayout = new RelativeLayout(mContext);
        mBaseContentLayout.setId(View.generateViewId());

        mBaseRootLayout.addView(mBaseContentLayout, new ViewGroup.LayoutParams(-1, -1));
        mBaseEmptyLayout = UILayoutImpl.safeAssignView(mBaseRootLayout,
                inflateEmptyLayout(mBaseRootLayout, inflater));
        mBaseNonetLayout = UILayoutImpl.safeAssignView(mBaseRootLayout,
                inflateNonetLayout(mBaseRootLayout, inflater));
        mBaseLoadLayout = UILayoutImpl.safeAssignView(mBaseRootLayout,
                inflateLoadLayout(mBaseRootLayout, inflater));

        safeSetView(mBaseContentLayout);
        safeSetView(mBaseEmptyLayout);
        safeSetView(mBaseNonetLayout);
        safeSetView(mBaseLoadLayout);

        container.addView(mBaseRootLayout, new ViewGroup.LayoutParams(-1, -1));
        return mBaseRootLayout;
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);
        changeState(mLayoutState, LayoutState.LOAD);
    }

    protected abstract void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater);

    protected View inflateLoadLayout(FrameLayout baseRootLayout, LayoutInflater inflater) {
        return inflater.inflate(R.layout.base_load_layout, baseRootLayout);
    }

    protected View inflateNonetLayout(FrameLayout baseRootLayout, LayoutInflater inflater) {
        return inflater.inflate(R.layout.base_nonet_layout, baseRootLayout);
    }

    protected View inflateEmptyLayout(FrameLayout baseRootLayout, LayoutInflater inflater) {
        return inflater.inflate(R.layout.base_empty_layout, baseRootLayout);
    }

    /**
     * 当布局的显示状态发生了改变
     */
    protected void onLayoutStateChanged(LayoutState fromState, LayoutState toState) {
        if (fromState == LayoutState.LOAD) {
            mBaseLoadLayout.findViewById(R.id.base_load_image_view).clearAnimation();
        }
        if (toState == LayoutState.LOAD) {
            mBaseLoadLayout.findViewById(R.id.base_load_image_view).startAnimation(loadLoadingAnimation());
        } else if (toState == LayoutState.NONET) {
            mBaseNonetLayout.findViewById(R.id.base_setting_view).setOnClickListener(mNonetSettingClickListener);
            mBaseNonetLayout.findViewById(R.id.base_refresh_view).setOnClickListener(mNonetRefreshClickListener);
        }

    }

    /**
     * 显示装载布局
     */
    public void showLoadLayout() {
        changeState(mLayoutState, LayoutState.LOAD);
    }

    /**
     * 显示空布局
     */
    public void showEmptyLayout() {
        changeState(mLayoutState, LayoutState.EMPTY);
    }

    /**
     * 显示无网络布局
     */
    public void showNonetLayout() {
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
            inflateContentLayout(mBaseContentLayout, LayoutInflater.from(mContext));
            ButterKnife.bind(this, mBaseContentLayout);
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

    /**
     * 指示当前布局的显示状态, 当前那个布局在显示
     */
    enum LayoutState {
        NORMAL,//正常
        EMPTY,//空布局
        LOAD,//装载布局
        NONET,//无网络
        CONTENT //内容
    }
}
