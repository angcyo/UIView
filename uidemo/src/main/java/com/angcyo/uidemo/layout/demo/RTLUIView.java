package com.angcyo.uidemo.layout.demo;

import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.container.ContentLayout;
import com.angcyo.uiview.model.TitleBarPattern;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/20 18:20
 * 修改人员：Robi
 * 修改时间：2016/12/20 18:20
 * 修改备注：
 * Version: 1.0.0
 */
public class RTLUIView extends UIContentView {
    NestedScrollView mScrollView;

    @Override
    protected void inflateContentLayout(ContentLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_rtl_layout);
    }

    @Override
    protected TitleBarPattern getTitleBar() {
        return super.getTitleBar().setFloating(true).setFixContentHeight(false).setTitleBarBGColor(Color.TRANSPARENT);
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();
        mScrollView = mViewHolder.v(R.id.scroll_view);
        mUITitleBarContainer.getTitleView().setVisibility(View.GONE);
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY,
                                       int oldScrollX, int oldScrollY) {
                mUITitleBarContainer.evaluateBackgroundColor(scrollY);
                mUITitleBarContainer.getTitleView()
                        .setVisibility(scrollY > mUITitleBarContainer.getMeasuredHeight() ?
                                View.VISIBLE : View.GONE);
            }
        });
    }
}
