package com.angcyo.uidemo.layout.demo.view;

import android.content.Context;
import android.support.annotation.Px;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.OverScroller;

import com.angcyo.uidemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/03/29 11:18
 * 修改人员：Robi
 * 修改时间：2017/03/29 11:18
 * 修改备注：
 * Version: 1.0.0
 */
public class GameCircleLayout extends ViewGroup {

    /**
     * 决定不同item之间的间隙, 值越大间隙越大
     */
    float radius;
    float angdegStep = 30, angdegOffset;

    //最大的高度,所有子view的高度总和
    int maxHeight;
    OverScroller mOverScroller;
    GestureDetectorCompat mGestureDetectorCompat;
    /**
     * 决定滚动时, item移动的速度, 值越小滚动越快
     */
    private float mScrollFactor = 45f;
    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            scrollBy(0, convert(distanceY));
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mOverScroller.fling(0, getScrollY(), 0, -(int) velocityY, 0, 0, getMaxScrollHeight(), 0);
            postInvalidate();
            return true;
        }
    };

    public GameCircleLayout(Context context) {
        super(context);
        initLayout();
    }

    public GameCircleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    private void initLayout() {
        setWillNotDraw(false);

        mOverScroller = new OverScroller(getContext());
        mGestureDetectorCompat = new GestureDetectorCompat(getContext(), mSimpleOnGestureListener);

        List<View> childs = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            ImageView image1 = new ImageView(getContext());
            ImageView image2 = new ImageView(getContext());
            ImageView image3 = new ImageView(getContext());

            image1.setImageResource(R.drawable.game_bg1);
            image2.setImageResource(R.drawable.game_bg2);
            image3.setImageResource(R.drawable.game_bg3);

            image1.setScaleType(ImageView.ScaleType.FIT_XY);
            image2.setScaleType(ImageView.ScaleType.FIT_XY);
            image3.setScaleType(ImageView.ScaleType.FIT_XY);

//            image1.setClickable(true);
//            image2.setClickable(true);
//            image3.setClickable(true);

            image1.setTag(String.valueOf(i * 3));
            image2.setTag(String.valueOf(1 + i * 3));
            image3.setTag(String.valueOf(2 + i * 3));

            childs.add(image1);
            childs.add(image2);
            childs.add(image3);
        }

        for (int i = childs.size() - 1; i >= 0; i--) {
            addView(childs.get(i));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getScrollY() != 0) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
            return;
        }

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        maxHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(height / 3, MeasureSpec.EXACTLY));

            maxHeight += getChildAt(i).getMeasuredHeight();
        }

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        radius = getMeasuredHeight() * 1 / 2;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            postLayout();
        }
    }

    @Override
    public void computeScroll() {
        if (mOverScroller.computeScrollOffset()) {
            int currY = mOverScroller.getCurrY();
            scrollTo(0, currY);
            postInvalidate();
        }
    }

    @Override
    public void scrollTo(@Px int x, @Px int y) {
        super.scrollTo(x, Math.min(0, Math.max(getMaxScrollHeight(), y)));
        postLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetectorCompat.onTouchEvent(event);
        return true;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //mGestureDetectorCompat.onTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    private void postLayout() {
        int height = 0;
        int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View childAt = getChildAt(i);
            int index = childCount - 1 - i;
            int childHeight = childAt.getMeasuredHeight();
            int scrollY = Math.abs(getScrollY());

            height += childHeight;

            float angdeg = index * angdegStep - calcOffsetAngdeg();

            double radians = Math.toRadians(angdeg);
            double sin = Math.sin(radians);
            double top = sin * radius;

            if (angdeg > -angdegStep && angdeg < 90 + angdegStep) {
                childAt.setVisibility(VISIBLE);
            } else {
                childAt.setVisibility(GONE);
            }

            int t = (int) (getMeasuredHeight() - top - childHeight + getScrollY());
            childAt.layout(0, t, getMeasuredWidth(), t + childAt.getMeasuredHeight());

            //L.e("call: postLayout2([])-> angdeg: " + angdeg + " radians:" + radians + " sin:" + sin);
        }
    }

    /**
     * 根据滚动距离, 计算角度偏移
     */
    private float calcOffsetAngdeg() {
        int scrollY = Math.abs(getScrollY());

        return scrollY / mScrollFactor;
    }

    /**
     * @param angdeg 角度
     */
    private int calcLayoutTop(View child, double angdeg) {
        double a = Math.sin(Math.toRadians(angdeg)) * radius;

        return (int) (getMeasuredHeight() - a - child.getMeasuredHeight() / 2);
    }

    /**
     * child 距离底部的距离
     */
    private int getViewBottomOffset(View child) {
        int scrollY = getScrollY();
        int childTop = child.getTop();
        int top = getMeasuredHeight() - childTop;
        return top + scrollY;
    }

    /**
     * 将原本滚动的距离, 经过角度计算后, 得到新的滚动距离
     */
    private int convert(float distanceY) {
        return (int) (distanceY * 1.4f);
    }

    /**
     * 允许滚动的最大距离
     */
    private int getMaxScrollHeight() {
//        if (getChildCount() > 0) {
//            return maxHeight - getChildAt(getChildCount() - 1).getMeasuredHeight();
//        }
        //return -maxHeight;

        float v = (getChildCount() - 1) * angdegStep;//最大需要滚动的角度

        return -(int) (v * mScrollFactor);//最大可以滚动的距离
    }
}
