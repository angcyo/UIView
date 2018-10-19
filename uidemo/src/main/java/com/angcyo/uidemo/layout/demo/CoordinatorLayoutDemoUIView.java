package com.angcyo.uidemo.layout.demo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.container.ContentLayout;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.widget.viewpager.UIViewPager;

/**
 * Created by angcyo on 2018-10-19
 */

public class CoordinatorLayoutDemoUIView extends UIContentView {

    TabLayout mTabLayout;
    UIViewPager mViewPager;
    ViewGroup mRootLayout;

    @Override
    protected void inflateContentLayout(ContentLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_coordinator_layout);
    }

    @Override
    protected TitleBarPattern getTitleBar() {
        return super.getTitleBar().setShowBackImageView(true);
    }

    @Override
    protected String getTitleString() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();
        mTabLayout = v(R.id.tab_layout);
        mViewPager = v(R.id.view_pager);
        mRootLayout = v(R.id.root_layout);
        initPager();

        AppBarLayout appBarLayout = v(R.id.app_bar_layout);
        ViewGroup.LayoutParams params = appBarLayout.getLayoutParams();
        int width = params.width;
    }

    @Override
    public void onViewShow(Bundle bundle) {
        super.onViewShow(bundle);
    }

    void initPager() {
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new BehaviorStickDemoUIView.PagerAdapter(false));
        //mViewPager.setSlowTouch(true);
    }
}
