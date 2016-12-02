package com.angcyo.demo.uiview3.view;

import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.angcyo.demo.R;
import com.angcyo.uiview.base.UIBaseView;
import com.angcyo.uiview.container.UILayoutImpl;
import com.angcyo.uiview.utils.T;

import butterknife.OnClick;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/01 16:03
 * 修改人员：Robi
 * 修改时间：2016/12/01 16:03
 * 修改备注：
 * Version: 1.0.0
 */
public class LoginView extends UIBaseView {

    private UILayoutImpl.OnWindowInsetsListener mWindowInsetsListener = new UILayoutImpl.OnWindowInsetsListener() {
        @Override
        public void onWindowInsets(int insetLeft, int insetTop, int insetRight, int insetBottom) {
            View targetView = mBaseContentLayout.getChildAt(0);
            if (insetBottom > 0) {
                ViewCompat.animate(targetView)
                        .translationY(-targetView.getTop())
                        .setDuration(300)
                        .setInterpolator(new DecelerateInterpolator())
                        .start();
            } else {
                ViewCompat.animate(targetView)
                        .translationY(-0)
                        .setDuration(300)
                        .setInterpolator(new DecelerateInterpolator())
                        .start();
            }
        }
    };
    private UILayoutImpl mUILayout;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_login_layout);
    }

    @Override
    protected void initContentLayout() {
        mUILayout = ((UILayoutImpl) getILayout().getLayout());
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);
        showContentLayout();
    }

    @OnClick(R.id.login_button)
    public void onLoginButton() {
        T.show(mContext, "登录");
    }

    @OnClick(R.id.dialog_login_button)
    public void onDialogLoginButton() {
        getILayout().startIView(new DialogLoginView());
    }

    @Override
    public void onViewShow() {
        super.onViewShow();
//        mUILayout.addOnWindowInsetsListener(mWindowInsetsListener);
//        mUILayout.setLockHeight(true);
    }

    @Override
    public void onViewHide() {
        super.onViewHide();
//        mUILayout.removeOnWindowInsetsListener(mWindowInsetsListener);
//        mUILayout.setLockHeight(false);

    }
}
