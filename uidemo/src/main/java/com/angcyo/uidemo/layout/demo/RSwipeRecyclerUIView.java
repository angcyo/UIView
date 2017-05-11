package com.angcyo.uidemo.layout.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIRecyclerUIView;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RRecyclerView;
import com.angcyo.uiview.recycler.RSwipeRecycleView;
import com.angcyo.uiview.recycler.adapter.RBaseSwipeAdapter;
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter;
import com.angcyo.uiview.rsen.RefreshLayout;
import com.angcyo.uiview.utils.T_;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/05/10 18:42
 * 修改人员：Robi
 * 修改时间：2017/05/10 18:42
 * 修改备注：
 * Version: 1.0.0
 */
public class RSwipeRecyclerUIView extends UIRecyclerUIView<String, String, String> {
    @Override
    protected TitleBarPattern getTitleBar() {
        return super.getTitleBar().setTitleString("侧滑删除").setShowBackImageView(true);
    }

    @Override
    protected RRecyclerView createRecyclerView(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        return initRecyclerView(new RSwipeRecycleView(mActivity), baseContentLayout);
    }

    @Override
    protected void afterInflateView(RelativeLayout baseContentLayout) {
        super.afterInflateView(baseContentLayout);
        mRefreshLayout.setRefreshDirection(RefreshLayout.BOTH);
    }

    @Override
    protected RExBaseAdapter<String, String, String> createAdapter() {
        return new RBaseSwipeAdapter<String, String, String>(mActivity, createData()) {
            @Override
            protected int getItemLayoutId(int viewType) {
                if (viewType == 0) {
                    return R.layout.item_text_image;
                }
                return R.layout.item_text_view;
            }

            @Override
            protected int getDataItemType(int posInData) {
                return posInData % 2;
            }

            @Override
            protected void onBindDataView(RBaseViewHolder holder, int posInData, String dataBean) {
                super.onBindDataView(holder, posInData, dataBean);
                holder.tv(R.id.text_view).setText(dataBean + " position:" + posInData);
            }

            @Override
            protected void onBindMenuView(RBaseViewHolder holder, final int position) {
                super.onBindMenuView(holder, position);
                holder.v(R.id.menu1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T_.show("position:" + position + "  menu 1");
                    }
                });
                holder.v(R.id.menu2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T_.show("position:" + position + "  menu 2");
                    }
                });

                if (holder.getItemViewType() != 0) {
                    holder.v(R.id.menu3).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            T_.show("position:" + position + "  menu 3");
                        }
                    });
                }
            }

            @Override
            protected View onCreateMenuView(ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    return LayoutInflater.from(mContext).inflate(R.layout.item_base_menu, parent, false);
                } else {
                    return LayoutInflater.from(mContext).inflate(R.layout.item_base_menu_3item, parent, false);
                }
            }
        };
    }

    private List<String> createData() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add("Data: " + i);
        }
        return datas;
    }
}
