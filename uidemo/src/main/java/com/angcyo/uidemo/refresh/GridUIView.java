package com.angcyo.uidemo.refresh;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.container.ContentLayout;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.recycler.RBaseItemDecoration;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RRecyclerView;
import com.angcyo.uiview.recycler.adapter.RBaseAdapter;
import com.angcyo.uiview.resources.ResUtil;
import com.angcyo.uiview.rsen.BasePointRefreshView;
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
public class GridUIView extends UIContentView {

    private RefreshLayout mRefreshLayout;
    private RRecyclerView mRecyclerView;

    @Override
    protected void inflateContentLayout(ContentLayout baseContentLayout, LayoutInflater inflater) {
        mRefreshLayout = new RefreshLayout(mActivity);
        mRefreshLayout.setTag("refresh");
        mRecyclerView = new RRecyclerView(mActivity);
        mRecyclerView.setTag("gv4");
        mRefreshLayout.addView(mRecyclerView);

        mRefreshLayout.setTopView(new BasePointRefreshView(mActivity));
        mRefreshLayout.setBottomView(new BasePointRefreshView(mActivity));
        baseContentLayout.addView(mRefreshLayout);
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);
        showContentLayout();
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();
        initRecyclerView();
    }

    private void initRecyclerView() {
        RBaseItemDecoration itemDecoration = new RBaseItemDecoration((int) (2 * density()), Color.RED);
        itemDecoration.setDrawLastLine(true);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(new RBaseAdapter<String>(mActivity) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return 0;
            }

            @Override
            public int getItemCount() {
                return 40;
            }

            @Override
            protected View createItemView(ViewGroup parent, int viewType) {
                TextView textView = new TextView(mActivity);
                textView.setLayoutParams(new ViewGroup.LayoutParams(-1, (int) ResUtil.dpToPx(mActivity.getResources(), 50)));
                textView.setTextColor(Color.BLUE);
                return textView;
            }

            @Override
            protected void onBindView(RBaseViewHolder holder, int position, String bean) {
                ((TextView) holder.itemView).setText(this.getClass().getSuperclass().getSimpleName() + " " + position);
            }
        });
    }


    @Override
    protected TitleBarPattern getTitleBar() {
        return null;
    }
}
