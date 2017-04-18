package com.angcyo.uiview.dialog;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.uiview.R;
import com.angcyo.uiview.base.UIIDialogImpl;
import com.angcyo.uiview.container.ILayout;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：显示一个装载形式的对话框
 * 创建人员：Robi
 * 创建时间：2016/12/14 19:09
 * 修改人员：Robi
 * 修改时间：2016/12/14 19:09
 * 修改备注：
 * Version: 1.0.0
 */
public class UILoading extends UIIDialogImpl {

    static boolean isShowing = false;

    static UILoading mUILoading;

    protected ViewGroup mBaseLoadingRootLayout;

    protected View mBaseLoadingView;

    protected TextView mBaseLoadingTipView;

    String mLoadingTipText = "别怕, 马上就好...";

    private UILoading() {
    }


    @Deprecated
    public static UILoading build() {
        if (mUILoading == null) {
            mUILoading = new UILoading();
        }
        return mUILoading;
    }

    /**
     * 显示
     */
    @Deprecated
    public static void hide(ILayout layout) {
        if (isShowing) {
            layout.finishIView(mUILoading);
        }
    }

    public static void hide() {
        if (isShowing && mUILoading != null) {
            mUILoading.finishDialog();
        }
    }

    public static UILoading show2(ILayout layout) {
        if (isShowing) {
            mUILoading.initLoadingUI();
        } else {
            if (mUILoading == null) {
                mUILoading = new UILoading();
            }
            layout.startIView(mUILoading);
        }

        return mUILoading;
    }

    /**
     * 显示
     */
    @Deprecated
    public UILoading show(ILayout layout) {
        if (isShowing) {
            mUILoading.initLoadingUI();
        } else {
            layout.startIView(mUILoading);
        }
        return this;
    }

    /**
     * 设置提示语
     */
    public UILoading setLoadingTipText(String loadingTipText) {
        mLoadingTipText = loadingTipText;
        return this;
    }

    @Override
    protected View inflateDialogView(RelativeLayout dialogRootLayout, LayoutInflater inflater) {
        return inflate(R.layout.base_loading_layout);
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);
        mBaseLoadingRootLayout = mViewHolder.v(R.id.base_load_root_layout);
        mBaseLoadingView = mViewHolder.v(R.id.base_load_view);
        mBaseLoadingTipView = mViewHolder.v(R.id.base_load_tip_view);
        initLoadingUI();
    }

    private void initLoadingUI() {
        mBaseLoadingTipView.setText(mLoadingTipText);
    }

    @Override
    public void onViewLoad() {
        super.onViewLoad();
        isShowing = true;
    }

    @Override
    public void onViewUnload() {
        super.onViewUnload();
        isShowing = false;
    }

    @Override
    public int getGravity() {
        return Gravity.TOP;
    }

    @Override
    public boolean canCanceledOnOutside() {
        return false;
    }
}
