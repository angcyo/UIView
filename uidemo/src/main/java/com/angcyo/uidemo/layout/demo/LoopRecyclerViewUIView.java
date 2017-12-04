package com.angcyo.uidemo.layout.demo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.container.ContentLayout;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RLoopRecyclerView;
import com.angcyo.uiview.utils.T_;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/03/01 14:41
 * 修改人员：Robi
 * 修改时间：2017/03/01 14:41
 * 修改备注：
 * Version: 1.0.0
 */
public class LoopRecyclerViewUIView extends UIContentView {

    private RLoopRecyclerView.LoopAdapter<String> mLoopAdapter;

    @Override
    protected String getTitleString() {
        return "无限循环";
    }

    @Override
    protected void inflateContentLayout(ContentLayout baseContentLayout, LayoutInflater inflater) {
        RLoopRecyclerView loopRecyclerView = new RLoopRecyclerView(mActivity);
        baseContentLayout.addView(loopRecyclerView, new ViewGroup.LayoutParams(-1, -1));

        loopRecyclerView.setTag("h");
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            datas.add("数据位置测试:" + i);
        }

        loopRecyclerView.setOnPageListener(new RLoopRecyclerView.OnPageListener() {
            @Override
            public void onPageSelector(int position) {
                T_.ok("position:" + position);
            }
        });

        mLoopAdapter = new RLoopRecyclerView.LoopAdapter<String>(mActivity, datas) {
            @Override
            public void onBindLoopViewHolder(RBaseViewHolder holder, int position, String bean) {
                holder.tv(R.id.text_view).setText(bean);
            }

            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.item_loop_text_view;
            }
        };
        loopRecyclerView.setAdapter(mLoopAdapter);
        loopRecyclerView.resetScrollPosition();
    }
}
