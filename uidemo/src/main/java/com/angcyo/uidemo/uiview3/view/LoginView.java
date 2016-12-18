package com.angcyo.uidemo.uiview3.view;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.uidemo.R;
import com.angcyo.uidemo.RApp;
import com.angcyo.uidemo.uiview3.login.Login;
import com.angcyo.uidemo.uiview3.login.LoginPresenterImpl;
import com.angcyo.library.utils.L;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.container.IWindowInsetsListener;
import com.angcyo.uiview.container.UILayoutImpl;
import com.angcyo.uiview.utils.T;

import butterknife.BindView;
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
public class LoginView extends UIContentView implements Login.ILoginView {

    @BindView(R.id.phone_view)
    TextView mPhoneView;
    @BindView(R.id.password_view)
    TextView mPasswordView;
    private IWindowInsetsListener mWindowInsetsListener = new IWindowInsetsListener() {
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
    private Login.ILoginPresenter mLoginPresenter = new LoginPresenterImpl();

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
        mLoginPresenter.bindView(this);
    }

    @OnClick(R.id.login_button)
    public void onLoginButton() {
        T.show(mActivity, "登录");
        mLoginPresenter.startLogin(mPhoneView.getText().toString(), mPasswordView.getText().toString(), "phone", RApp.getIMEI());
    }

    @OnClick(R.id.dialog_login_button)
    public void onDialogLoginButton() {
        getILayout().startIView(new DialogLoginView());
    }

    @Override
    public void onViewShow() {
        super.onViewShow();
        mBaseRootLayout.addOnWindowInsetsListener(mWindowInsetsListener);
//        mUILayout.setLockHeight(true);
    }

    @Override
    public void onViewHide() {
        super.onViewHide();
        mBaseRootLayout.removeOnWindowInsetsListener(mWindowInsetsListener);
//        mUILayout.setLockHeight(false);

    }

    @Override
    public void onStartLoad() {
        L.w("");
        mUITitleBarContainer.showLoadView();
    }

    @Override
    public void onFinishLoad() {
        L.w("");
        mUITitleBarContainer.hideLoadView();
    }

    @Override
    public void onSuccess() {
        L.w("");
        T.show(mActivity, "登录成功");
        mUITitleBarContainer.hideLoadView();
    }

    @Override
    public void onError(int code, @NonNull String msg) {
        L.w("");
        T.show(mActivity, "登录失败:" + msg);
        mUITitleBarContainer.hideLoadView();
    }

    @Override
    public void onViewUnload() {
        super.onViewUnload();
        mLoginPresenter.onUnload();
    }
}
