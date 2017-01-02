package com.angcyo.uidemo.layout.demo;

import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.angcyo.uidemo.R;
import com.angcyo.uidemo.layout.demo.view.CenterButton;
import com.angcyo.uiview.base.UIContentView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/30 15:46
 * 修改人员：Robi
 * 修改时间：2016/12/30 15:46
 * 修改备注：
 * Version: 1.0.0
 */
public class CenterRadioButtonUIView extends UIContentView {
    @BindView(R.id.center_button)
    CenterButton mCenterButton;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_center_button_layout);
    }


    @OnClick(R.id.setChecked)
    public void onSetChecked() {
        mCenterButton.setChecked(!mCenterButton.isChecked());
    }

    @OnClick(R.id.setEnabled)
    public void onSetEnabled() {
        mCenterButton.setEnabled(!mCenterButton.isEnabled());
    }
}
