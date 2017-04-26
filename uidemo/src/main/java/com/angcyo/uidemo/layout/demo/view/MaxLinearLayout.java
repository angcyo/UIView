package com.angcyo.uidemo.layout.demo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/26 14:55
 * 修改人员：Robi
 * 修改时间：2017/04/26 14:55
 * 修改备注：
 * Version: 1.0.0
 */
public class MaxLinearLayout extends LinearLayout {
    public MaxLinearLayout(Context context) {
        super(context);
    }

    public MaxLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View secondView = getChildAt(1);
        View firstView = getChildAt(0);
        measureChild(secondView,
                MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.AT_MOST));

        int secondWidth = secondView.getMeasuredWidth();//第二个布局需要的宽度
        int secondHeight = secondView.getMeasuredHeight();//第二个布局需要的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (firstView.getMeasuredWidth() > getMeasuredWidth() - secondWidth) {
            measureChild(firstView,
                    MeasureSpec.makeMeasureSpec(getMeasuredWidth() - secondWidth, MeasureSpec.EXACTLY),
                    heightMeasureSpec);

            measureChild(secondView,
                    MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.AT_MOST));
        }

        setMeasuredDimension(getMeasuredWidth(), Math.max(firstView.getMeasuredHeight(), secondHeight));
    }
}
