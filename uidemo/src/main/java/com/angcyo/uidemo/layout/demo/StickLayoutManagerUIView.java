package com.angcyo.uidemo.layout.demo;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.angcyo.library.utils.L;
import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIRecyclerUIView;
import com.angcyo.uiview.design.StickLayoutManager;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter;
import com.angcyo.uiview.utils.Reflect;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/03/16 16:40
 * 修改人员：Robi
 * 修改时间：2017/03/16 16:40
 * 修改备注：
 * Version: 1.0.0
 */
public class StickLayoutManagerUIView extends UIRecyclerUIView<String, String, String> {
    @Override
    protected TitleBarPattern getTitleBar() {
        return super.getTitleBar()
                .setShowBackImageView(true)
                .setTitleString(this.getClass().getSimpleName());
    }

    @Override
    protected void afterInflateView(RelativeLayout baseContentLayout) {
        super.afterInflateView(baseContentLayout);
//        mRecyclerView.setLayoutManager(new FloatLayoutManager(mActivity).setFloatPosition(4));
        mRecyclerView.setLayoutManager(new StickLayoutManager(mActivity).setStickPosition(8).setStickTop(200));
//        mRecyclerView.setLayoutManager(new TestLayoutManager());
//        mRecyclerView.setLayoutManager(new BaseLinearLayoutManager(mActivity));
//        mRecyclerView.getRecycledViewPool().setMaxRecycledViews(11, 2);
//        mRecyclerView.setItemViewCacheSize(4);
//        mRecyclerView.setVerticalScrollBarEnabled(true);
//        mRecyclerView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        mRecyclerView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        //mRecyclerView.setOverScrollMode();
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();
        //showLoadView();
        //getUITitleBarContainer().getCenterControlLayout().setBackgroundColor(Color.RED);
    }

    @Override
    protected RExBaseAdapter<String, String, String> createAdapter() {
        return new RExBaseAdapter<String, String, String>(mActivity, BehaviorStickDemoUIView.createItems()) {
            @Override
            protected int getItemLayoutId(int viewType) {
                if (viewType == 0) {
                    return R.layout.item_stick_layout_top;
                }
                return R.layout.item_stick_layout_normal;
            }

            @Override
            protected int getDataItemType(int posInData) {
                if (posInData == 0) {
                    return posInData;
                }
                return super.getDataItemType(posInData);
            }

            @Override
            public RBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                L.w("call: onCreateViewHolder([parent, viewType])-> " + viewType);
                return super.onCreateViewHolder(parent, viewType);
            }

            @Override
            public void onBindViewHolder(RBaseViewHolder holder, int position) {
                L.w("call: onBindViewHolder([holder, position])-> " + position);
                super.onBindViewHolder(holder, position);
                holder.tv(R.id.text_view).setText("测试文本: " + position);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Object mTopGlow = Reflect.getMember(RecyclerView.class, mRecyclerView, "mTopGlow");
                        Object mEdgeEffect = Reflect.getMember(mTopGlow, "mEdgeEffect");
                        Object mPaint = Reflect.getMember(mEdgeEffect, "mPaint");
                        if (mPaint instanceof Paint) {
                            ((Paint) mPaint).setColor(Color.RED);
                        }
//                        if (mEdgeEffect instanceof EdgeEffect) {
//                            ((EdgeEffect) mEdgeEffect).setColor(Color.RED);
//                        }
                    }
                });
            }
        };
    }
}
