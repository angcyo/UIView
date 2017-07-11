package com.angcyo.uidemo.layout.demo;

import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.widget.viewpager.UIViewPager;

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
public class BehaviorStickDemoUIView2 extends UIContentView {

    @Override
    protected TitleBarPattern getTitleBar() {
        return super.getTitleBar()
                .setShowBackImageView(true)
                .setTitleString(this.getClass().getSimpleName());
    }

    @Override
    protected void inflateContentLayout(FrameLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_behavior_layout2);
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();

        UIViewPager viewPager = mViewHolder.v(R.id.view_pager);
        TabLayout tabLayout = mViewHolder.v(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(new BehaviorStickDemoUIView.PagerAdapter());

        mViewHolder.v(R.id.image_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ViewCompat.offsetTopAndBottom(mViewHolder.v(R.id.test_layout), -100);
            }
        });
    }

}
