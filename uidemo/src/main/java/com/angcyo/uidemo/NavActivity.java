package com.angcyo.uidemo;

import android.content.Intent;

import com.angcyo.uiview.base.UILayoutActivity;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/30 10:34
 * 修改人员：Robi
 * 修改时间：2016/12/30 10:34
 * 修改备注：
 * Version: 1.0.0
 */
public class NavActivity extends UILayoutActivity {
    @Override
    protected void onLoadView(Intent intent) {

        startIView(new NavUIView());

//        Uri uri = getIntent().getData();
//        if (uri != null) {
//            String test1 = uri.getQueryParameter("arg0");
//            String test2 = uri.getQueryParameter("arg1");
//            T_.show(test1 + "--" + test2);
//        }
    }
}
