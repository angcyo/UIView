package com.angcyo.uiview.widget.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.angcyo.uiview.utils.Reflect;

import java.util.ArrayList;

/**
 * Created by angcyo on 2016-11-26.
 */

public class UIViewPager extends ViewPager implements Runnable {

    private int lastItem = -1;

    public UIViewPager(Context context) {
        super(context);
        init();
    }

    public UIViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Reflect.setMember(getSuperclass(), this, "mCurItem", -1);
        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                post(UIViewPager.this);
            }
        });
    }

    private Class<?> getSuperclass() {
        return getClass().getSuperclass();
    }

    @Override
    public void setCurrentItem(int item) {
        lastItem = getCurrentItem();
        super.setCurrentItem(item);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        lastItem = getCurrentItem();
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void run() {
        checkPageChanged();
    }

    @SuppressWarnings("Unchecked")
    private void checkPageChanged() {
        final ArrayList<Object> mItems = (ArrayList<Object>) Reflect.getMember(
                getSuperclass(),
                this,
                "mItems");
        if (mItems == null || mItems.size() == 0) {
            return;
        }

        for (Object obj : mItems) {
            //对应的是ViewPager 的 ItemInfo结构体
            int position = (int) getPosition(obj);//position成员
            final Object available = getObject(obj);//object成员
            if (lastItem == position) {
                if (obj != null && available instanceof OnPagerShowListener) {
                    ((OnPagerShowListener) available).onHideInPager(this);
                }
            } else if (getCurrentItem() == position) {
                if (obj != null && available instanceof OnPagerShowListener) {
                    ((OnPagerShowListener) available).onShowInPager(this);
                }
            }
        }

        lastItem = getCurrentItem();
    }

    private Object getObject(Object object) {
        return Reflect.getMember(object, "object");
    }

    private Object getPosition(Object object) {
        return Reflect.getMember(object, "position");
    }

    public interface OnPagerShowListener {
        void onShowInPager(UIViewPager viewPager);

        void onHideInPager(UIViewPager viewPager);
    }
}
