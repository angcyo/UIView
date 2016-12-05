package com.angcyo.demo.uiview3.view;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.demo.R;
import com.angcyo.demo.RApp;
import com.angcyo.demo.uiview3.login.Login;
import com.angcyo.demo.uiview3.login.LoginPresenterImpl;
import com.angcyo.library.utils.L;
import com.angcyo.uiview.base.UIIDialogImpl;
import com.angcyo.uiview.utils.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/02 11:05
 * 修改人员：Robi
 * 修改时间：2016/12/02 11:05
 * 修改备注：
 * Version: 1.0.0
 */
public class DialogLoginView extends UIIDialogImpl implements Login.ILoginView {

    @BindView(R.id.phone_view)
    TextView mPhoneView;
    @BindView(R.id.password_view)
    TextView mPasswordView;
    private Login.ILoginPresenter mLoginPresenter;

    @Override
    protected View inflateDialogView(RelativeLayout dialogRootLayout, LayoutInflater inflater) {
        return inflate(R.layout.view_dialog_login_layout);
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);
        mLoginPresenter = new LoginPresenterImpl();
        mLoginPresenter.bindView(this);
    }

    @OnClick(R.id.login_button)
    public void onLoginButton() {
        T.show(mContext, "登录");
        mLoginPresenter.startLogin(mPhoneView.getText().toString(), mPasswordView.getText().toString(), "phone", RApp.getIMEI());
    }

    @Override
    public void onStartLoad() {

    }

    @Override
    public void onFinishLoad() {

    }

    @Override
    public void onSuccess() {
        L.w("");
        T.show(mContext, "登录成功");
    }

    @Override
    public void onError(int code, @NonNull String msg) {
        L.w("");
        T.show(mContext, "登录失败:" + msg);
    }

}
