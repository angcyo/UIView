package com.angcyo.demo.rsen;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：刷新控件
 * 创建人员：Robi
 * 创建时间：2016/12/05 17:30
 * 修改人员：Robi
 * 修改时间：2016/12/05 17:30
 * 修改备注：
 * Version: 1.0.0
 */
public class RefreshLayout extends ViewGroup {
    public static final int TOP = 1;
    public static final int BOTTOM = 2;
    public static final int BOTH = 3;
    public static final int MOVE = 4;
    public static final int NORMAL = 0;

    private View mTopView, mBottomView, mTargetView;

    private ViewDragHelper mDragHelper;

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mTargetView;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 1;
        }
    };

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (mTargetView != null) {
            mTargetView.measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY));
        }
        if (mTopView != null) {
            mTopView.measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST));
        }
        if (mBottomView != null) {
            mBottomView.measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mTargetView != null) {
            mTargetView.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getChildCount() != 1) {
            throw new IllegalArgumentException("必须包含一个子View");
        }
        mTargetView = getChildAt(0);
        mDragHelper = ViewDragHelper.create(this, 1.f, mCallback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            mDragHelper.processTouchEvent(event);
        } catch (Exception e) {
        }
        return true;
    }

    /**
     * Child是否可以滚动
     */
    private boolean innerCanChildScrollVertically(View view, int direction) {
        if (view instanceof ViewGroup) {
            final ViewGroup vGroup = (ViewGroup) view;
            View child;
            boolean result;
            for (int i = 0; i < vGroup.getChildCount(); i++) {
                child = vGroup.getChildAt(i);
                if (child instanceof View) {
                    result = ViewCompat.canScrollVertically(child, direction);
                } else {
                    result = innerCanChildScrollVertically(child, direction);
                }

                if (result) {
                    return true;
                }
            }
        }

        return ViewCompat.canScrollVertically(view, direction);
    }

    /**
     * 支持的刷新方向
     */
    @IntDef({TOP, BOTTOM, BOTH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
    }

    /**
     * 当前的刷新状态
     */
    @IntDef({TOP, BOTTOM, NORMAL, MOVE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    interface OnTopViewMoveListener {
        /**
         * @param top       距离父View顶部的距离
         * @param maxHeight view的高度
         */
        void onTopMoveTo(View view, int top, int maxHeight);
    }

    interface OnBottomViewMoveListener {
        /**
         * @param bottom    距离父View底部的距离
         * @param maxHeight view的高度
         */
        void onBottomMoveTo(View view, int bottom, int maxHeight);
    }


}
