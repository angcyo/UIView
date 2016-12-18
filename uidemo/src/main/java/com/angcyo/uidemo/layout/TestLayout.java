package com.angcyo.uidemo.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/11/18 14:41
 * 修改人员：Robi
 * 修改时间：2016/11/18 14:41
 * 修改备注：
 * Version: 1.0.0
 */
public class TestLayout extends ViewGroup {

    private static final String TAG = "test1";
    View rightView;

    public TestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 2) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        messageRightView(widthSize, heightSize);
        int maxHeight = rightView.getMeasuredHeight();

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            if (!isRightView(child)) {
                child.measure(MeasureSpec.makeMeasureSpec(widthSize - rightView.getMeasuredWidth(), MeasureSpec.AT_MOST),
                        MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST));
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
            }
        }

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            if (!isRightView(child)) {
                child.measure(MeasureSpec.makeMeasureSpec(widthSize - rightView.getMeasuredWidth(), MeasureSpec.AT_MOST),
                        MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY));
            }
        }

        setMeasuredDimension(widthSize, maxHeight);
    }

    protected void messageRightView(int width, int height) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            if (isRightView(child)) {
                child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
                        MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
                rightView = child;
                break;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
            if (isRightView(child)) {
                child.layout(r - l - measuredWidth, 0, r - l, measuredHeight);
            } else {
                child.layout(0, 0, measuredWidth, measuredHeight);
            }
        }
    }

    private boolean isRightView(View child) {
        return child.getTag() != null && "r".equalsIgnoreCase(child.getTag().toString());
    }
}
