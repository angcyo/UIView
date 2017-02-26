package com.angcyo.uiview.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：最大高度的View
 * 创建人员：Robi
 * 创建时间：2017/02/24 11:55
 * 修改人员：Robi
 * 修改时间：2017/02/24 11:55
 * 修改备注：
 * Version: 1.0.0
 */
public class MaxHeightScrollView extends NestedScrollView {
    public MaxHeightScrollView(Context context) {
        super(context);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        int maxHeight = (int) (400 * getResources().getDisplayMetrics().density);

        if (measuredHeight >= maxHeight) {
            super.onMeasure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY));
        }
    }
}
