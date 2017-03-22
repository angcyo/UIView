package com.angcyo.uiview.design;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.angcyo.library.utils.L;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：支持悬停的 Behavior
 * 创建人员：Robi
 * 创建时间：2017/03/16 10:19
 * 修改人员：Robi
 * 修改时间：2017/03/16 10:19
 * 修改备注：
 * Version: 1.0.0
 */
public class StickBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    View mTargetView;

    /**
     * 悬停区域的高度 px
     */
    int stickHeight = 0;

    /**
     * 为了保存已经偏移的距离
     */
    int lastTop = 0;

    public StickBehavior() {
    }

    public StickBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 当CoordinatorLayout在layout的时候, 会回调此方法, 告诉你,
     * 是否需要得到dependency View的位置大小改变的监听,如果你需要, 那么必须返回true
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency) {
        if (mTargetView == null) {
            lastTop = 0;
        }
        if (dependency instanceof TestLayout) {
            //StickLayout只是用来判断是否是目标的标识, 没有任何不同支持,
            //你也可以通过getId判断id是否相等, 来处理
            mTargetView = dependency;
            stickHeight = (int) (40 * child.getContext().getResources().getDisplayMetrics().density);
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    /**
     * 只有当layoutDependsOn返回true时, 才会回调.
     * 当dependency对应的View, 发生了改变, 就会回调此方法.
     * 你可以在此更新child的位置
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, V child, View dependency) {
        lastTop = dependency.getTop();
        child.setTop(dependency.getBottom());
        return true;
    }


    /**
     * 测量child的大小, 和自定义view的套路一样
     */
    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, V child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        child.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(parent.getMeasuredHeight() - stickHeight, View.MeasureSpec.EXACTLY));
        return true;
    }

    /**
     * 布局child的位置, 和自定义view的套路一样
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        if (mTargetView != null) {
            ViewCompat.offsetTopAndBottom(mTargetView, lastTop);
            int top = mTargetView.getBottom();// - Math.abs(lastTop);
            child.layout(0, top, child.getMeasuredWidth(), top + child.getMeasuredHeight());
            return true;
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    /**
     * 必须返回true, 否则你收不到子View滚动的事件
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    /**
     * 当子View开始滚动之前, 你可以通过此方法消耗掉滚动事件, 并偏移child的位置
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed) {
        if (dy == 0 || (dy < 0 && ViewCompat.canScrollVertically(target, -1))) {
            return;
        }

        int offsetMax = mTargetView.getMeasuredHeight() - stickHeight;

        int top = mTargetView.getTop();
        int offset = 0;
        if (dy > 0) {
            //上滑
            offset = Math.min(dy, offsetMax + top);
        } else if (dy < 0) {
            //下滑
            offset = Math.max(dy, top);
        }
        ViewCompat.offsetTopAndBottom(mTargetView, -offset);
        consumed[1] = offset;
    }

    /**
     * 用来处理快速滑动
     */
    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY) {
        L.e("call: onNestedPreFling([coordinatorLayout, child, target, velocityX, velocityY])-> ");

        if (velocityY < 0 && ViewCompat.canScrollVertically(target, -1)) {
            return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
        }

        int offsetMax = mTargetView.getMeasuredHeight() - stickHeight;

        if (velocityY > offsetMax) {
            mTargetView.setTop(-offsetMax);
            //mTargetView.setBottom(mTargetView.getMeasuredHeight() - offsetMax);
            animToBottom(mTargetView.getMeasuredHeight() - offsetMax);
        } else if (velocityY < -offsetMax) {
            mTargetView.setTop(0);
            //mTargetView.setBottom(mTargetView.getMeasuredHeight());
            animToBottom(mTargetView.getMeasuredHeight());
        }

        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    void animToBottom(int bottom) {
        ValueAnimator animator = ObjectAnimator.ofInt(mTargetView.getBottom(), bottom);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTargetView.setBottom((Integer) animation.getAnimatedValue());
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(100);
        animator.start();
    }
}
