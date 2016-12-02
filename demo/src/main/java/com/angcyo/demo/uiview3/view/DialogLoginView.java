package com.angcyo.demo.uiview3.view;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.angcyo.demo.R;
import com.angcyo.uiview.base.UIIDialogImpl;

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
public class DialogLoginView extends UIIDialogImpl {
    @Override
    protected View inflateDialogView(RelativeLayout dialogRootLayout, LayoutInflater inflater) {
        return inflate(R.layout.view_dialog_login_layout);
    }
}
