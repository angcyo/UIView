package com.angcyo.uidemo.layout.demo;

import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.angcyo.library.utils.L;
import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/11 16:47
 * 修改人员：Robi
 * 修改时间：2017/04/11 16:47
 * 修改备注：
 * Version: 1.0.0
 */
//public class TouchEventDemoUIView extends UIItemUIView<SingleItem> {
//    @Override
//    protected int getItemLayoutId(int viewType) {
//        return R.layout.view_touch_event_layout;
//    }
//
//    @Override
//    protected void createItems(List<SingleItem> items) {
//        items.add(new SingleItem() {
//            @Override
//            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
//
//            }
//        });
//    }
//
//    @Override
//    protected void afterInflateView(RelativeLayout baseContentLayout) {
//        super.afterInflateView(baseContentLayout);
//        mRefreshLayout.setEnabled(false);
//    }
//}

public class TouchEventDemoUIView extends UIContentView {

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_touch_event_layout);
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();
        mViewHolder.v(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View touchView = mViewHolder.v(R.id.touch_view);
                log(touchView);
                ViewCompat.offsetTopAndBottom(touchView, 100);
                log(touchView);
            }
        });
    }

    private void log(View touchView) {
        L.e("call: log([touchView])-> " +
                " l:" + touchView.getLeft() +
                " t:" + touchView.getTop() +
                " r:" + touchView.getRight() +
                " b:" + touchView.getBottom());
    }
}

