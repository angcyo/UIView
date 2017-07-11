package com.angcyo.uidemo.layout.demo;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.angcyo.uidemo.layout.demo.view.GameCircleLayout;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.model.TitleBarPattern;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/03/29 11:11
 * 修改人员：Robi
 * 修改时间：2017/03/29 11:11
 * 修改备注：
 * Version: 1.0.0
 */
public class GameCircleLayoutUIView extends UIContentView {

    @Override
    protected TitleBarPattern getTitleBar() {
        return super.getTitleBar().setShowBackImageView(true);
    }

    @Override
    protected String getTitleString() {
        return "游戏圆形滚动布局";
    }

    @Override
    protected void inflateContentLayout(FrameLayout baseContentLayout, LayoutInflater inflater) {
        baseContentLayout.addView(new GameCircleLayout(mActivity), new ViewGroup.LayoutParams(-1, -1));
    }
}
