package com.angcyo.demo.uiview3.view;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.angcyo.demo.R;
import com.angcyo.uiview.base.UIBaseView;
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
    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_login_layout);
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
}
