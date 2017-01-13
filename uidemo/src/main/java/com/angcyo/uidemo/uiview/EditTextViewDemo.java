package com.angcyo.uidemo.uiview;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.angcyo.uiview.base.UIBaseView;
import com.angcyo.uiview.widget.ExEditText;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/01 17:47
 * 修改人员：Robi
 * 修改时间：2016/12/01 17:47
 * 修改备注：
 * Version: 1.0.0
 */
public class EditTextViewDemo extends UIBaseView {

    ExEditText mExEditText;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        mExEditText = new ExEditText(mActivity);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -2);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        baseContentLayout.addView(mExEditText, params);
    }

    @Override
    protected void initOnShowContentLayout() {
        mExEditText.setHint("我就是一个测试的");
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);
        showContentLayout();
    }
}
