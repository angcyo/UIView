package com.angcyo.uidemo.layout.demo;

import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.container.ContentLayout;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RRecyclerView;
import com.angcyo.uiview.recycler.adapter.RBaseAdapter;
import com.angcyo.uiview.utils.T_;
import com.angcyo.uiview.view.IView;
import com.angcyo.uiview.widget.viewpager.UIPagerAdapter;
import com.angcyo.uiview.widget.viewpager.UIViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/03/16 10:24
 * 修改人员：Robi
 * 修改时间：2017/03/16 10:24
 * 修改备注：
 * Version: 1.0.0
 */
public class BehaviorStickDemoUIView extends UIContentView {

    public static List<String> createItems() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            items.add("");
        }
        return items;
    }

    @Override
    protected TitleBarPattern getTitleBar() {
        return super.getTitleBar()
                .setShowBackImageView(true)
                .setTitleString(this.getClass().getSimpleName());
    }

    @Override
    protected void inflateContentLayout(ContentLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_behavior_layout);
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();

        UIViewPager viewPager = mViewHolder.v(R.id.view_pager);
        TabLayout tabLayout = mViewHolder.v(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(new PagerAdapter());
    }

    public static class BehaviorContentUIView extends UIContentView {

        private RRecyclerView mRecyclerView;

        @Override
        protected TitleBarPattern getTitleBar() {
            return null;
        }

        @Override
        protected void inflateContentLayout(ContentLayout baseContentLayout, LayoutInflater inflater) {
            mRecyclerView = new RRecyclerView(mActivity);
            baseContentLayout.addView(mRecyclerView, new ViewGroup.LayoutParams(-1, -1));
        }

        @Override
        protected void initOnShowContentLayout() {
            super.initOnShowContentLayout();
            mRecyclerView.setAdapter(new RBaseAdapter<String>(mActivity, createItems()) {
                @Override
                protected int getItemLayoutId(int viewType) {
                    if (viewType == 0) {
                        return R.layout.item_stick_layout_top;
                    }
                    return R.layout.item_stick_layout_normal;
                }

                @Override
                public int getItemType(int position) {
                    return position;
                }

                @Override
                protected void onBindView(RBaseViewHolder holder, final int position, String bean) {
                    holder.tv(R.id.text_view).setText("测试文本::" + position);
                    holder.tv(R.id.text_view).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            T_.show("测试文本::" + position);
                        }
                    });
                }
            });
        }
    }

    public static class PagerAdapter extends UIPagerAdapter {
        @Override
        protected IView getIView(int position) {
            if (position == 0) {
                return new GithubDemoUIView().setInSubUIView(true);
            }
            return new BehaviorContentUIView();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "标签1";
                case 1:
                    return "标签2";
                case 2:
                    return "标签3";
            }
            return "";
        }
    }

}
