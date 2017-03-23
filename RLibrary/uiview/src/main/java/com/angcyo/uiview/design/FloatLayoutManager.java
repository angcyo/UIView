package com.angcyo.uiview.design;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：支持固定位置的LayoutManager
 * 创建人员：Robi
 * 创建时间：2017/03/16 16:37
 * 修改人员：Robi
 * 修改时间：2017/03/16 16:37
 * 修改备注：
 * Version: 1.0.0
 */
public class FloatLayoutManager extends BaseLinearLayoutManager {

    private static final String TRACE_LAYOUT = "FLM onLayoutChildren";
    View mFloatView;

    /**
     * 需要固定View的位置
     */
    int floatPosition = RecyclerView.NO_POSITION;

    /**
     * 固定的left, top坐标
     */
    int floatLeft, floatTop;

    public FloatLayoutManager(Context context) {
        super(context);
    }

    public FloatLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public FloatLayoutManager setFloatPosition(int floatPosition) {
        this.floatPosition = floatPosition;
        return this;
    }


    public FloatLayoutManager setFloatLeft(int floatLeft) {
        this.floatLeft = floatLeft;
        return this;
    }

    public FloatLayoutManager setFloatTop(int floatTop) {
        this.floatTop = floatTop;
        return this;
    }


    @Override
    protected void layoutChunk(View view, RecyclerView.LayoutParams params, RecyclerView.Recycler recycler,
                               RecyclerView.State state, LayoutState layoutState, LayoutChunkResult result) {
        super.layoutChunk(view, params, recycler, state, layoutState, result);
        if (params.getViewAdapterPosition() == floatPosition) {
            result.mConsumed = 0;
        }
    }

    @Override
    protected void onPostLayout(RecyclerView.Recycler recycler, RecyclerView.State state, int scrolled) {
        if (floatPosition < 0) {
            return;
        }

        if (mFloatView != null) {
            addFloatView(mFloatView);
            layoutFloatView();
        } else {
            mFloatView = recycler.getViewForPosition(floatPosition);
            measureChildWithMargins(mFloatView, 0, 0);
            layoutFloatView();
            addFloatView(mFloatView);
        }
    }

    /**
     * 用来控制固定的位置
     */
    protected void layoutFloatView() {
        int right = mOrientationHelper.getDecoratedMeasurementInOther(mFloatView);
        int height = mOrientationHelper.getDecoratedMeasurement(mFloatView);

        layoutDecorated(mFloatView, floatLeft, floatTop, floatLeft + right, floatTop + height);
    }
}
