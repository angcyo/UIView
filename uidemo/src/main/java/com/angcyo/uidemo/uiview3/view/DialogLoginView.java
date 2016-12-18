package com.angcyo.uidemo.uiview3.view;

import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.library.utils.L;
import com.angcyo.uidemo.R;
import com.angcyo.uidemo.RApp;
import com.angcyo.uidemo.uiview3.login.Login;
import com.angcyo.uidemo.uiview3.login.LoginPresenterImpl;
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

    private static long index = 0;
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
        T.show(mActivity, "登录");
        mLoginPresenter.startLogin(mPhoneView.getText().toString(), mPasswordView.getText().toString(), "phone", RApp.getIMEI());
    }

    @Override
    public void onStartLoad() {

    }

    @Override
    public void onFinishLoad() {

    }

    @Override
    public int getGravity() {
        int i = (int) (index % 3);
        index++;
        if (i == 0) {
            return Gravity.TOP;
        }
        if (i == 1) {
            return Gravity.CENTER;
        }
        return Gravity.BOTTOM;
    }

    @Override
    public void onSuccess() {
        L.w("");
        T.show(mActivity, "登录成功");
    }

    @Override
    public void onError(int code, @NonNull String msg) {
        L.w("");
        T.show(mActivity, "登录失败:" + msg);
    }

}
