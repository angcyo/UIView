package com.angcyo.uiview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：1/6 这样的ViewPager 指示器
 * 创建人员：Robi
 * 创建时间：2016/12/17 10:58
 * 修改人员：Robi
 * 修改时间：2016/12/17 10:58
 * 修改备注：
 * Version: 1.0.0
 */
public class SingleTextIndicator extends TextView implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;

    public SingleTextIndicator(Context context) {
        super(context);
    }

    public SingleTextIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleTextIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SingleTextIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setupViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        initView();
    }

    private void initView() {
        PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter == null) {
            setVisibility(INVISIBLE);
        } else {
            setVisibility(VISIBLE);
            setText((mViewPager.getCurrentItem() + 1) + "/" + adapter.getCount());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        initView();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 移除 缩放, 进入 方法, 平移透明
     */
    public static class SingleTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left. 将要从左边离开屏幕
                view.setAlpha(0);
            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }
                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.  将要从右边离开屏幕
                view.setAlpha(0);
            }
        }
    }

    /**
     * 上层平移, 底部缩放
     */
    public static class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);
            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);
            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);
                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);
                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
