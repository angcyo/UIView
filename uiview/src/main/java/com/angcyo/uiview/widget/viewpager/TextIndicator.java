package com.angcyo.uiview.widget.viewpager;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 类的描述：1/6 这样的ViewPager 指示器
 * 创建人员：Robi
 * 创建时间：2016/12/17 10:58 ~
 */
public class TextIndicator extends TextView implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;

    public TextIndicator(Context context) {
        super(context);
    }

    public TextIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
}
