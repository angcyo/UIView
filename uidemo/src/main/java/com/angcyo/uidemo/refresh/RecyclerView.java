package com.angcyo.uidemo.refresh;

import android.graphics.Color;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.uidemo.R;
import com.angcyo.uidemo.T_;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RModelAdapter;
import com.angcyo.uiview.recycler.RRecyclerView;
import com.angcyo.uiview.resources.ResUtil;
import com.angcyo.uiview.rsen.RefreshLayout;

import static android.R.id.list;

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
public class RecyclerView extends UIContentView {

    private RefreshLayout mRefreshLayout;
    private RRecyclerView mRecyclerView;

    private RModelAdapter<String> mAdapter;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        mRefreshLayout = new RefreshLayout(mActivity);
        mRefreshLayout.setTag("refresh");
        mRecyclerView = new RRecyclerView(mActivity);
        mRefreshLayout.addView(mRecyclerView);
//        TextView textView = new TextView(mActivity);
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
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new RModelAdapter<String>(mActivity) {
            @Override
            public int getItemCount() {
                return 30;
            }

            @Override
            protected View createContentView(ViewGroup parent, int viewType) {
                LinearLayout root = new LinearLayout(mActivity);
                root.setOrientation(LinearLayout.HORIZONTAL);
                root.setVerticalGravity(Gravity.CENTER_VERTICAL);
                root.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
                root.setPadding(1, 1, 1, 1);
                root.setBackgroundResource(R.drawable.base_main_color_bg_selector);

                AppCompatCheckBox checkBox = new AppCompatCheckBox(mActivity);
                checkBox.setTag("check_box");
                checkBox.setVisibility(View.VISIBLE);

                TextView textView = new TextView(mActivity);
                textView.setLayoutParams(new ViewGroup.LayoutParams(-1, (int) ResUtil.dpToPx(mActivity.getResources(), 50)));
                textView.setTextColor(Color.BLUE);
                textView.setTag("text");
                textView.setGravity(Gravity.CENTER_VERTICAL);
                //textView.setBackgroundColor(Color.GRAY);

                root.addView(checkBox, new ViewGroup.LayoutParams(-2, -2));
                root.addView(textView);

                return root;
            }

            @Override
            protected void onUnSelectorPosition(RBaseViewHolder viewHolder, int position) {
                unselector(list, mRecyclerView, "check_box");
            }

            @Override
            protected void onBindCommonView(RBaseViewHolder holder, int position, String bean) {
                ((TextView) holder.tag("text")).setText(this.getClass().getSuperclass().getSimpleName() + " " + position);
            }

            @Override
            protected void onBindModelView(int model, boolean isSelector, RBaseViewHolder holder, final int position, String bean) {
                final CheckBox check_box = holder.tag("check_box");
                check_box.setVisibility(View.VISIBLE);
                check_box.setChecked(isSelector);

                check_box.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setSelectorPosition(position, check_box);
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setSelectorPosition(position, check_box);
                    }
                });
            }

            @Override
            protected void onBindNormalView(RBaseViewHolder holder, int position, String bean) {
                holder.tag("check_box").setVisibility(View.GONE);

                holder.itemView.setOnClickListener(null);
            }

            @Override
            protected void onLoadMore() {
                T_.show("加载更多..." + System.currentTimeMillis());
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setLoadMoreEnd();
                        T_.show("结束加载更多..." + System.currentTimeMillis() + "  !进入多选模式! ");

                        mAdapter.setModel(MODEL_MULTI);

                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.setNoMore();

                                postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapter.setLoadError();

                                        postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mAdapter.setNoMore();
                                                T_.show(" !进入单选模式!  上次选中:" + mAdapter.getAllSelector().size() + "个");

                                                mAdapter.setModel(MODEL_SINGLE);
                                            }
                                        }, 5000);
                                    }
                                }, 1000);
                            }
                        }, 1000);
                    }
                }, 1000);
            }
        };
        mAdapter.setEnableLoadMore(true);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected TitleBarPattern getTitleBar() {
        return null;
    }
}
