package com.angcyo.demo.uiview;

import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.angcyo.demo.R;
import com.angcyo.uiview.base.UIContentView;

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
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.test_layout);
    }
}
