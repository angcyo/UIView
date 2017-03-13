package com.angcyo.uiview.github.tablayout;

import android.support.v4.view.ViewPager;

import com.angcyo.uiview.github.tablayout.listener.CustomTabEntity;
import com.angcyo.uiview.github.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

/**
 * Created by angcyo on 2017-03-12.
 */

public class TabLayoutUtil {
    public static void initCommonTab(CommonTabLayout tabLayout, ArrayList<CustomTabEntity> entities, OnTabSelectListener listener) {
        tabLayout.setTabData(entities);
        tabLayout.setOnTabSelectListener(listener);
    }

    public static void initSegmentTab(SegmentTabLayout tabLayout, String[] titles, OnTabSelectListener listener) {
        tabLayout.setTabData(titles);
        tabLayout.setOnTabSelectListener(listener);
    }

    public static void initSlidingTab(SlidingTabLayout tabLayout, ViewPager viewPager, OnTabSelectListener listener) {
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnTabSelectListener(listener);
    }
}
