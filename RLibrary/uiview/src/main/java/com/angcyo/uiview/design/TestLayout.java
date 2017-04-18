package com.angcyo.uiview.design;

import android.content.Context;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by angcyo on 2017-03-15.
 */

public class TestLayout extends LinearLayout {
    private NestedScrollingChildHelper mScrollingChildHelper;

    public TestLayout(Context context) {
        super(context);
    }

    public TestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        L.e("onMeasure() -> ");
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
//        L.e("onLayout() -> " + changed + " l:" + l + " t:" + t + " r:" + r + " b:" + b);
//    }
//
//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        L.e("onAttachedToWindow() -> ");
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        L.e("onDetachedFromWindow() -> ");
//    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (MotionEvent.ACTION_DOWN == MotionEventCompat.getActionMasked(ev)) {
//            ViewCompat.offsetTopAndBottom(this, -100);
//            getScrollingChildHelper().startNestedScroll(ViewCompat.SCROLL_AXIS_NONE);
//        }
        return super.onInterceptTouchEvent(ev);
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (mScrollingChildHelper == null) {
            mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return mScrollingChildHelper;
    }
}
