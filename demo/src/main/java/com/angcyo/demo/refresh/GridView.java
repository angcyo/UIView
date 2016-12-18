package com.angcyo.demo.refresh;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.recycler.RBaseAdapter;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RRecyclerView;
import com.angcyo.uiview.resources.ResUtil;
import com.angcyo.uiview.rsen.RefreshLayout;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/05 17:54
 * 修改人员：Robi
 * 修改时间：2016/12/05 17:54
 * 修改备注：
 * Version: 1.0.0
 */
public class GridView extends UIContentView {

    private RefreshLayout mRefreshLayout;
    private RRecyclerView mRecyclerView;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        mRefreshLayout = new RefreshLayout(mActivity);
        mRefreshLayout.setTag("refresh");
        mRecyclerView = new RRecyclerView(mActivity);
        mRecyclerView.setTag("gv4");
        mRefreshLayout.addView(mRecyclerView);
        baseContentLayout.addView(mRefreshLayout);
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);
        showContentLayout();
    }

    @Override
    protected void initContentLayout() {
        super.initContentLayout();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setAdapter(new RBaseAdapter<String>(mActivity) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return 0;
            }

            @Override
            public int getItemCount() {
                return 30;
            }

            @Override
            protected View createContentView(ViewGroup parent, int viewType) {
                TextView textView = new TextView(mActivity);
                textView.setLayoutParams(new ViewGroup.LayoutParams(-1, (int) ResUtil.dpToPx(mActivity.getResources(), 50)));
                textView.setTextColor(Color.BLUE);
                return textView;
            }

            @Override
            public void onBindViewHolder(RBaseViewHolder holder, int position) {
                ((TextView) holder.itemView).setText(this.getClass().getSimpleName() + " " + position);

            }

            @Override
            protected void onBindView(RBaseViewHolder holder, int position, String bean) {

            }
        });
    }


    @Override
    protected TitleBarPattern getTitleBar() {
        return null;
    }
}
