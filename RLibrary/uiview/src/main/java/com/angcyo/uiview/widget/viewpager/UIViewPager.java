package com.angcyo.uiview.widget.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.design.StickLayout;
import com.angcyo.uiview.utils.Reflect;
import com.angcyo.uiview.utils.UI;

import java.util.ArrayList;

/**
 * Created by angcyo on 2016-11-26.
 */

public class UIViewPager extends ViewPager implements Runnable, StickLayout.CanScrollUpCallBack {

    /**
     * 是否要拦截事件
     */
    public static boolean interceptTouch = true;

    /**
     * 迟钝处理touch事件
     */
    private boolean slowTouch = false;

    private int lastItem = -1;

    private int defaultShowItem = 0;
    private float downY, downX;

    public UIViewPager(Context context) {
        super(context);
        init();
    }

    public UIViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //Reflect.setMember(getSuperclass(), this, "mCurItem", -1);
        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                L.w("-->" + position);
                post(UIViewPager.this);
            }
        });
        //setCurrentItem(defaultShowItem);
    }

    private Class<?> getSuperclass() {
        return getClass().getSuperclass();
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        post(new Runnable() {
            @Override
            public void run() {
                checkPageChanged();
            }
        });
    }

    @Override
    public void setCurrentItem(int item) {
        //lastItem = getCurrentItem();
        super.setCurrentItem(item);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        //lastItem = getCurrentItem();
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float density = getResources().getDisplayMetrics().density;
        if (!interceptTouch) {
            if (ev.getX() < 80 * density) {
                return super.onInterceptTouchEvent(ev);
            }
            return false;
        }
        if (slowTouch) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downY = ev.getY();
                    downX = ev.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float moveY = ev.getY();
                    float moveX = ev.getX();
                    float offsetY = downY - moveY;
                    float offsetX = downX - moveX;
                    if (Math.abs(offsetY) > Math.abs(offsetX) || Math.abs(offsetX) < 40 * density) {
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    downY = 0;
                    downX = 0;
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
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

        int currentItem = getCurrentItem();
        for (Object obj : mItems) {
            //对应的是ViewPager 的 ItemInfo结构体
            int position = (int) getPosition(obj);//position成员
            final Object available = getObject(obj);//object成员
            if (currentItem == lastItem && lastItem == position) {

            } else if (lastItem == position) {
                if (obj != null && available instanceof OnPagerShowListener) {
                    ((OnPagerShowListener) available).onHideInPager(this);
                }
            } else if (currentItem == position) {
                if (obj != null && available instanceof OnPagerShowListener) {
                    ((OnPagerShowListener) available).onShowInPager(this);
                }
            }
        }

        lastItem = currentItem;
    }

    public void setSlowTouch(boolean slowTouch) {
        this.slowTouch = slowTouch;
    }

    private Object getObject(Object object) {
        return Reflect.getMember(object, "object");
    }

    private Object getPosition(Object object) {
        return Reflect.getMember(object, "position");
    }

    @Override
    public boolean canChildScrollUp() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (getScrollX() == childAt.getLeft()) {
                return UI.canChildScrollUp(childAt);
            }
        }
        return false;
    }

    @Override
    public RecyclerView getRecyclerView() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (getScrollX() == childAt.getLeft() && childAt instanceof ViewGroup) {
                return UI.getRecyclerView((ViewGroup) childAt);
            }
        }
        return null;
    }

    public interface OnPagerShowListener {
        void onShowInPager(UIViewPager viewPager);

        void onHideInPager(UIViewPager viewPager);
    }
}
