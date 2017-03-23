package com.angcyo.uiview.design;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/03/17 13:47
 * 修改人员：Robi
 * 修改时间：2017/03/17 13:47
 * 修改备注：
 * Version: 1.0.0
 */
public class BaseLinearLayoutManager extends RLinearLayoutManager {

    public BaseLinearLayoutManager(Context context) {
        super(context);
    }

    public BaseLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        onPreLayout(recycler, state);
        try {
            super.onLayoutChildren(recycler, state);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            onPostLayout(recycler, state, Integer.MAX_VALUE);
        }
    }

    @Override
    protected void layoutChunk(View view, RecyclerView.LayoutParams params, RecyclerView.Recycler recycler,
                               RecyclerView.State state, LayoutState layoutState, LayoutChunkResult result) {
        super.layoutChunk(view, params, recycler, state, layoutState, result);
    }

    @Override
    protected int scrollByInner(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        onPreLayout(recycler, state);
        int scrollBy = 0;
        try {
            scrollBy = super.scrollByInner(dy, recycler, state);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            onPostLayout(recycler, state, scrollBy);
        }
        return scrollBy;
    }

    protected void onPreLayout(RecyclerView.Recycler recycler, RecyclerView.State state) {

    }

    protected void onPostLayout(RecyclerView.Recycler recycler, RecyclerView.State state, int scrolled) {

    }
}
