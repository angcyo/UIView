package com.angcyo.uiview.design;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.angcyo.library.utils.L;

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
public class StickLayoutManager extends LinearLayoutManager {
    public StickLayoutManager(Context context) {
        super(context);
    }

    public StickLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public StickLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setRecycleChildrenOnDetach(boolean recycleChildrenOnDetach) {
        L.e("call: setRecycleChildrenOnDetach([recycleChildrenOnDetach])-> ");
        super.setRecycleChildrenOnDetach(recycleChildrenOnDetach);
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        L.e("call: onDetachedFromWindow([view, recycler])-> ");
        super.onDetachedFromWindow(view, recycler);
    }

    @Override
    public boolean canScrollHorizontally() {
        //L.e("call: canScrollHorizontally([])-> ");
        return super.canScrollHorizontally();
    }

    @Override
    public boolean canScrollVertically() {
        //L.e("call: canScrollVertically([])-> ");
        return super.canScrollVertically();
    }

    @Override
    public void setStackFromEnd(boolean stackFromEnd) {
        L.e("call: setStackFromEnd([stackFromEnd])-> ");
        super.setStackFromEnd(stackFromEnd);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        L.e("call: smoothScrollToPosition([recyclerView, state, position])-> ");
        super.smoothScrollToPosition(recyclerView, state, position);
    }

    @Override
    public PointF computeScrollVectorForPosition(int targetPosition) {
        L.e("call: computeScrollVectorForPosition([targetPosition])-> ");
        return super.computeScrollVectorForPosition(targetPosition);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        L.e("call: onLayoutChildren([recycler, state])-> ");
        super.onLayoutChildren(recycler, state);
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        L.e("call: onLayoutCompleted([state])-> ");
        super.onLayoutCompleted(state);
    }
}
