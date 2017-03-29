package com.angcyo.uidemo.layout.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.angcyo.uiview.widget.RImageView;

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

    OverScroller mOverScroller;
    GestureDetectorCompat mGestureDetectorCompat;
    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            if (mOverScroller.isFinished()) {
//                mOverScroller.startScroll(0, mOverScroller.getCurrY(), 0, (int) distanceY);
//                postInvalidate();
//            }
            scrollBy(0, (int) distanceY);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mOverScroller.fling(0, getScrollY(), 0, -(int) velocityY, 0, 0, -Integer.MAX_VALUE, 0);
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

//        ImageView image1 = new ImageView(getContext());
//        ImageView image2 = new ImageView(getContext());
//        ImageView image3 = new ImageView(getContext());
//
//        image1.setImageResource(R.drawable.game_bg1);
//        image2.setImageResource(R.drawable.game_bg2);
//        image3.setImageResource(R.drawable.game_bg3);
//
//        image1.setScaleType(ImageView.ScaleType.FIT_XY);
//        image2.setScaleType(ImageView.ScaleType.FIT_XY);
//        image3.setScaleType(ImageView.ScaleType.FIT_XY);
//
//        addView(image1);
//        addView(image2);
//        addView(image3);

        for (int i = 0; i < 1; i++) {

            RImageView image1 = new RImageView(getContext());
            RImageView image2 = new RImageView(getContext());
            RImageView image3 = new RImageView(getContext());

            image1.setImageResource(R.drawable.game_bg1);
            image2.setImageResource(R.drawable.game_bg2);
            image3.setImageResource(R.drawable.game_bg3);

            image1.setScaleType(ImageView.ScaleType.FIT_XY);
            image2.setScaleType(ImageView.ScaleType.FIT_XY);
            image3.setScaleType(ImageView.ScaleType.FIT_XY);

            image1.setClickable(true);
            image2.setClickable(true);
            image3.setClickable(true);

            image1.setTag(String.valueOf(i * 3));
            image2.setTag(String.valueOf(1 + i * 3));
            image3.setTag(String.valueOf(2 + i * 3));

            addView(image1, 0);
            addView(image2, 0);
            addView(image3, 0);
        }

        //setPadding(10, 20, 30, 40);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(height / 3, MeasureSpec.EXACTLY));
        }

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        radius = getMeasuredHeight() * 2 / 3;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        int offsetTop = 0;
//        for (int i = 0; i < getChildCount(); i++) {
//            View childAt = getChildAt(i);
//            int measuredHeight = childAt.getMeasuredHeight();
//            int top = b - offsetTop - measuredHeight;
////            if (i % 2 == 0) {
////                childAt.layout(l, top, (int) (3 / 2f * r), (int) (top + 3 / 2f * measuredHeight));
////            } else {
////                childAt.layout(l, top, r, top + measuredHeight);
////            }
//
//            top = calcLayoutTop(childAt, i * angdegStep);
//
//            childAt.layout(l, top, r, top + measuredHeight);
//
//            offsetTop += measuredHeight;
//        }
        postLayout();
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
        super.scrollTo(x, Math.min(0, y));
        postLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetectorCompat.onTouchEvent(event);
        return true;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mGestureDetectorCompat.onTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawColor(Color.RED);
        //L.e("call: onDraw([canvas])-> ");

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        for (int i = 0; i < 360; i++) {
            double sin = Math.sin(Math.toRadians(i));
            canvas.drawPoint(i, (float) sin * 200 - getScrollY(), paint);
        }
    }

    private void postLayout() {
//        int measuredHeight = getMeasuredHeight();
//        for (int i = 0; i < getChildCount(); i++) {
//            View childAt = getChildAt(i);
//            int childAtMeasuredHeight = childAt.getMeasuredHeight();
//            int viewBottomOffset = getViewBottomOffset(childAt);
//            if (viewBottomOffset > 0 && viewBottomOffset < (measuredHeight + childAtMeasuredHeight)) {
//                L.e("call: postLayout([])-> i:" + i + " " + viewBottomOffset + " " + getScrollY());
//                childAt.setAlpha(1);
//                //childAt.offsetTopAndBottom(childAtMeasuredHeight/2);
//            } else {
//                childAt.setAlpha(0);
//            }
//        }
//        getChildAt(0).layout(0, getScrollY(), getMeasuredWidth(), getScrollY() + getChildAt(0).getMeasuredHeight());

        int childHeight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int height = childAt.getMeasuredHeight();
            int scrollY = Math.abs(getScrollY());
            childHeight += height;
            if (scrollY > childHeight) {
                childAt.setVisibility(GONE);
                //childAt.setAlpha(0);
            } else {
                //childAt.setAlpha(1);
                childAt.setVisibility(VISIBLE);

//                float angdeg = (childCount - i - 1) * angdegStep + calcOffsetAngdeg();
                float angdeg = i * angdegStep + calcOffsetAngdeg();

//                if (angdeg % 180 > 180f) {
//                    childAt.setVisibility(GONE);
//                } else {

                double radians = Math.toRadians(angdeg);
                double sin = Math.sin(radians);
                double top = sin * radius;

                int t = (int) (getMeasuredHeight() - top - childAt.getMeasuredHeight() / 2 + getScrollY());
                childAt.layout(0, t, getMeasuredWidth(), t + childAt.getMeasuredHeight());
//                }
            }
        }
//
//        for (int i = 0; i < getChildCount(); i++) {
//
//        }
    }

    /**
     * 根据滚动距离, 计算角度偏移
     */
    private float calcOffsetAngdeg() {
        int scrollY = Math.abs(getScrollY());

        return scrollY / 45f;
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
}
