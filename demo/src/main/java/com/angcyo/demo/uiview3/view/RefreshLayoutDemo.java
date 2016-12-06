package com.angcyo.demo.uiview3.view;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.demo.rsen.RefreshLayout;
import com.angcyo.library.utils.L;
import com.angcyo.uiview.base.UIBaseDataView;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.recycler.RBaseAdapter;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RRecyclerView;
import com.angcyo.uiview.resources.ResUtil;

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
public class RefreshLayoutDemo extends UIBaseDataView {

    private RefreshLayout mRefreshLayout;
    private RRecyclerView mRecyclerView;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        mRefreshLayout = new RefreshLayout(mContext);
        mRecyclerView = new RRecyclerView(mContext);
        mRefreshLayout.addView(mRecyclerView);
//        TextView textView = new TextView(mContext);
//        textView.setText("我就是内容.....");
//        mRefreshLayout.addView(textView);
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
        mRefreshLayout.addTopViewMoveListener(new RefreshLayout.OnTopViewMoveListener() {
            @Override
            public void onTopMoveTo(View view, int top, int maxHeight) {
                if (top > maxHeight) {
                    ((TextView) view).setText("释放刷新");
                } else {
                    ((TextView) view).setText("下拉刷新...");
                }

                L.w("刷新:::-->" + top + "         :" + maxHeight);
            }
        });

        mRefreshLayout.addBottomViewMoveListener(new RefreshLayout.OnBottomViewMoveListener() {
            @Override
            public void onBottomMoveTo(View view, int bottom, int maxHeight) {
                if (bottom > maxHeight) {
                    ((TextView) view).setText("释放加载更多");
                } else {
                    ((TextView) view).setText("上拉加载...");
                }

                L.w("加载:::-->" + bottom + "         :" + maxHeight);
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setAdapter(new RBaseAdapter<String>(mContext) {
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
                TextView textView = new TextView(mContext);
                textView.setLayoutParams(new ViewGroup.LayoutParams(-1, (int) ResUtil.dpToPx(mContext.getResources(), 50)));
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
    protected TitleBarPattern initTitleBar() {
        return super.initTitleBar().setTitleString("刷新控件测试");
    }
}
