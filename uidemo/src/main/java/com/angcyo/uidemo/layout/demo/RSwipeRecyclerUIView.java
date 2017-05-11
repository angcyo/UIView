package com.angcyo.uidemo.layout.demo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIRecyclerUIView;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RRecyclerView;
import com.angcyo.uiview.recycler.RSwipeRecycleView;
import com.angcyo.uiview.recycler.adapter.RBaseSwipeAdapter;
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter;
import com.angcyo.uiview.recycler.widget.MenuBuilder;
import com.angcyo.uiview.rsen.RefreshLayout;
import com.angcyo.uiview.utils.T_;

import java.util.ArrayList;
import java.util.List;

import static com.angcyo.uiview.rsen.RefreshLayout.TOP;

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
        mRefreshLayout.addOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(@RefreshLayout.Direction int direction) {
                if (direction == TOP) {
                    mRefreshLayout.setShowTip("正在刷新...");
                } else {

                }
            }
        });
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
            protected void onBindMenuView(MenuBuilder menuBuilder, int viewType, final int position) {
                menuBuilder
                        .addMenu("菜单1", Color.GREEN, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                T_.show("position:" + position + "  menu 1");
                            }
                        })
                        .addMenu("菜单2", Color.YELLOW, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                T_.show("position:" + position + "  menu 2");
                            }
                        })
                ;
                if (viewType == 0) {
                    menuBuilder
                            .addMenu("菜单3", Color.BLUE, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    T_.show("position:" + position + "  menu 3");
                                }
                            });
                }
            }
        };
    }

    private List<String> createData() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("Data: " + i);
        }
        return datas;
    }
}
