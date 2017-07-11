package com.angcyo.uidemo.uiview;

import android.view.LayoutInflater;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.container.ContentLayout;
import com.angcyo.uiview.model.TitleBarPattern;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/11/18 14:54
 * 修改人员：Robi
 * 修改时间：2016/11/18 14:54
 * 修改备注：
 * Version: 1.0.0
 */
public class TestDemo extends UIContentView {

    @Override
    protected void inflateContentLayout(ContentLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.test_layout);
    }

    @Override
    protected TitleBarPattern getTitleBar() {
        return null;
    }
}
