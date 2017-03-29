package com.angcyo.uiview.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.angcyo.uiview.R;

/**
 * 用来控制状态栏的padding
 * Created by angcyo on 2016-11-05.
 */

public class TitleBarLayout extends ViewGroup {

    public TitleBarLayout(Context context) {
        super(context);
    }

    public TitleBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
//        Context context = getContext();
//        if (context instanceof Activity) {
//            if (ResUtil.isLayoutFullscreen((Activity) context)) {
//                int statusBarHeight = getResources().getDimensionPixelSize(R.dimen.status_bar_height);
//                setClipToPadding(false);
//                setClipChildren(false);
//                setPadding(getPaddingLeft(),
//                        statusBarHeight,
//                        getPaddingRight(), getPaddingBottom());
//                height = statusBarHeight + getResources().getDimensionPixelSize(R.dimen.action_bar_height);
//            }
//        }
            int statusBarHeight = getPaddingTop() + getPaddingBottom();
            if (!isInEditMode() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                statusBarHeight += getResources().getDimensionPixelSize(R.dimen.status_bar_height);
            }

            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            View childAt = getChildAt(0);
            childAt.measure(widthMeasureSpec, heightMeasureSpec);
            if (heightMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(widthMeasureSpec, childAt.getMeasuredHeight() + statusBarHeight);
            } else {
                setMeasuredDimension(widthMeasureSpec, height + statusBarHeight);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int statusBarHeight = 0;
        if (!isInEditMode() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            statusBarHeight = getResources().getDimensionPixelSize(R.dimen.status_bar_height);
        }

        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            childAt.layout(l, t + statusBarHeight, r, t + statusBarHeight + childAt.getMeasuredHeight());
        }
    }
}
