package com.angcyo.uidemo.layout.demo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.design.StickLayout2;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.widget.viewpager.UIViewPager;

import butterknife.BindView;

/**
 * Created by angcyo on 2017-03-15.
 */
@Deprecated
public class StickLayoutDemo2UIView extends UIContentView {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    UIViewPager mViewPager;
    @BindView(R.id.root_layout)
    StickLayout2 mRootLayout;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_stick_layout2);
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
        initPager();
    }

    @Override
    public void onViewShow(Bundle bundle) {
        super.onViewShow(bundle);
    }

    void initPager() {
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new BehaviorStickDemoUIView.PagerAdapter());
        //mViewPager.setSlowTouch(true);
    }
}
