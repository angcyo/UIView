package com.angcyo.uiview.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.angcyo.uiview.R;

/**
 * 用来控制状态栏的padding
 * Created by angcyo on 2016-11-05.
 */

public class TitleBarLayout extends FrameLayout {

    public TitleBarLayout(Context context) {
        super(context);
    }

    public TitleBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (getChildCount() > 1) {
            throw new IllegalArgumentException("Need Only One Child View.");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int statusBarHeight = getResources().getDimensionPixelSize(R.dimen.status_bar_height);

        if (!isInEditMode() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getChildCount() > 0) {
            setPadding(getPaddingLeft(), statusBarHeight, getPaddingRight(), getPaddingBottom());
            View childAt = getChildAt(0);

            if (heightMode == MeasureSpec.EXACTLY) {
                childAt.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY));
            } else {
                childAt.measure(widthMeasureSpec, heightMeasureSpec);
                heightSize = childAt.getMeasuredHeight();
            }
            setMeasuredDimension(getMeasuredWidth(), heightSize + statusBarHeight + getPaddingBottom());
        }
    }
}