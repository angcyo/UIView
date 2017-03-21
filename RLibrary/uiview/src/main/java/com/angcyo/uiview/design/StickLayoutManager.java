package com.angcyo.uiview.design;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：支持悬停的LayoutManager
 * 创建人员：Robi
 * 创建时间：2017/03/16 16:37
 * 修改人员：Robi
 * 修改时间：2017/03/16 16:37
 * 修改备注：
 * Version: 1.0.0
 */
public class StickLayoutManager extends BaseLinearLayoutManager {

    private static final String TRACE_LAYOUT = "SLM onLayoutChildren";
    /**
     * 已经悬浮的view
     */
    View mStickView;

    /**
     * 需要悬浮的位置
     */
    int stickPosition = RecyclerView.NO_POSITION;
    /**
     * 悬浮距离top的值
     */
    int stickTop = 0;

    boolean isFloat = false;

    int stickViewHeight;

    public StickLayoutManager(Context context) {
        super(context);
    }

    public StickLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public StickLayoutManager setStickPosition(int stickPosition) {
        this.stickPosition = stickPosition;
        return this;
    }

    @Override
    protected void layoutChunk(View view, RecyclerView.LayoutParams params, RecyclerView.Recycler recycler,
                               RecyclerView.State state, LayoutState layoutState, LayoutChunkResult result) {
        super.layoutChunk(view, params, recycler, state, layoutState, result);
        if (checkPosition(params)) {
            stickViewHeight = result.mConsumed;
        }
    }

    private boolean checkPosition(RecyclerView.LayoutParams params) {
        return params.getViewAdapterPosition() == stickPosition;
    }

    /**
     * 浮动View
     */
    private void floatView() {
        addFloatView(mStickView);
        layoutDecoratedWithMargins(mStickView, mStickView.getLeft(), stickTop,
                mOrientationHelper.getDecoratedMeasurementInOther(mStickView), stickTop + mOrientationHelper.getDecoratedMeasurement(mStickView));
    }

    @Override
    protected void onPostLayout(RecyclerView.Recycler recycler, RecyclerView.State state, int scrolled) {
        if (checkFloat()) {
            //L.e("浮动:是");
            isFloat = true;
            ensureStickView(recycler);
            floatView();
        } else {
            //L.e("浮动:否");
            if (isFloat) {
                isFloat = false;
                removeStickView();
                if (mStickView != null) {
                    if (((RecyclerView.LayoutParams) mStickView.getLayoutParams()).isItemRemoved()
                            || mStickView.getParent() == null) {
                        int index = -1;
                        for (int i = 0; i < getChildCount(); i++) {
                            View childAt = getChildAt(i);
                            int adapterPosition = getAdapterPosition(childAt);
                            if (adapterPosition == stickPosition - 1) {
                                index = i + 1;
                            }
                        }
                        addView(mStickView, index);
                        //L.e("addView...");
                    }
                    int stickTop = getStickTop();
                    layoutDecorated(mStickView, 0, stickTop,
                            mOrientationHelper.getDecoratedMeasurementInOther(mStickView),
                            stickTop + mOrientationHelper.getDecoratedMeasurement(mStickView));
                }
            }
        }
        //L.e("call: onPostLayout([recycler, state, scrolled])-> count:" + getChildCount() + "   preLayout:" + state.isPreLayout());
    }

    /**
     * 检查是否需要浮动
     */
    private boolean checkFloat() {
        int firstVisibleItemPosition = findFirstVisibleItemPosition();
        View firstView = findViewByPosition(firstVisibleItemPosition);

        if (firstVisibleItemPosition == stickPosition + 1) {
            if (firstView.getTop() < stickTop + stickViewHeight) {
                return true;
            } else {
                return false;
            }
        } else if (firstVisibleItemPosition == stickPosition - 1) {
            if (firstView.getBottom() < stickTop) {
                return true;
            } else {
                return false;
            }
        } else {
            if (firstVisibleItemPosition > stickPosition) {
                return true;
            }
        }

        View viewByPosition = findViewByPosition(stickPosition);
        if (viewByPosition == null) {
            return false;
        }

        if (viewByPosition.getTop() < stickTop) {
            return true;
        }
        return false;
    }

    private void ensureStickView(RecyclerView.Recycler recycler) {
        if (mStickView == null) {
            View viewByPosition = findViewByPosition(stickPosition);
            if (viewByPosition == null) {
                mStickView = recycler.getViewForPosition(stickPosition);
                measureChildWithMargins(mStickView, 0, 0);
            } else {
                mStickView = viewByPosition;
            }
        }
    }

    private void removeStickView() {
        if (mStickView != null) {
            clearView(mStickView);
            removeView(mStickView);

            if (findLastVisibleItemPosition() < stickPosition) {
                mStickView = null;
            }
        }
    }

    private int getStickTop() {
        View view = findViewByPosition(stickPosition - 1);
        if (view == null) {
            view = findViewByPosition(stickPosition + 1);
            if (view == null) {
                return 0;
            } else {
                return view.getTop() - stickViewHeight;
            }
        } else {
            return view.getBottom();
        }
    }

    public StickLayoutManager setStickTop(int stickTop) {
        this.stickTop = stickTop;
        return this;
    }
}
