package com.angcyo.uiview.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.angcyo.uiview.recycler.RBaseAdapter;
import com.angcyo.uiview.recycler.RExBaseAdapter;
import com.angcyo.uiview.recycler.RRecyclerView;
import com.angcyo.uiview.rsen.RefreshLayout;

/**
 * Created by angcyo on 2017-03-11.
 */

public class UIRecyclerUIView<H, T, F> extends UIContentView implements RefreshLayout.OnRefreshListener, RBaseAdapter.OnAdapterLoadMoreListener {

    /**
     * 刷新控件
     */
    protected RefreshLayout mRefreshLayout;
    /**
     * 列表
     */
    protected RRecyclerView mRecyclerView;
    protected RExBaseAdapter<H, T, F> mExBaseAdapter;

    @Override
    final protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        beforeInflateView(baseContentLayout);
        mRefreshLayout = createRefreshLayout(baseContentLayout, inflater);
        mRecyclerView = createRecyclerView(baseContentLayout, inflater);
        afterInflateView(baseContentLayout);
    }

    /**
     * 填充试图之前调用
     */
    protected void beforeInflateView(RelativeLayout baseContentLayout) {

    }

    /**
     * 内容试图填充之后调用
     */
    protected void afterInflateView(RelativeLayout baseContentLayout) {

    }

    /**
     * 创建列表并添加到父视图
     */
    protected RRecyclerView createRecyclerView(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        RRecyclerView recyclerView = new RRecyclerView(mActivity);
        final RecyclerView.ItemDecoration itemDecoration = createItemDecoration();
        if (itemDecoration != null) {
            recyclerView.addItemDecoration(itemDecoration);
        }
        mExBaseAdapter = createAdapter();
        recyclerView.setAdapter(mExBaseAdapter);

        if (mExBaseAdapter != null) {
            mExBaseAdapter.setLoadMoreListener(this);
        }

        if (mRefreshLayout == null) {
            baseContentLayout.addView(recyclerView, new ViewGroup.LayoutParams(-1, -1));
        } else {
            mRefreshLayout.addView(recyclerView, new ViewGroup.LayoutParams(-1, -1));
        }

        return recyclerView;
    }

    protected RExBaseAdapter<H, T, F> createAdapter() {
        return null;
    }

    protected RecyclerView.ItemDecoration createItemDecoration() {
        return null;
    }

    /**
     * 创建刷新控件, 并添加试图
     */
    protected RefreshLayout createRefreshLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        RefreshLayout refreshLayout = new RefreshLayout(mActivity);
        refreshLayout.setRefreshDirection(RefreshLayout.TOP);
        refreshLayout.addRefreshListener(this);
        baseContentLayout.addView(refreshLayout, new ViewGroup.LayoutParams(-1, -1));
        return refreshLayout;
    }

    /**
     * 刷新控件, 刷新事件回调
     */
    @Override
    public void onRefresh(@RefreshLayout.Direction int direction) {
        if (direction == RefreshLayout.TOP) {
            //刷新事件
        } else if (direction == RefreshLayout.BOTTOM) {
            //加载更多事件
        }
    }

    /**
     * adapter加载更多回调
     */
    @Override
    public void onAdapterLodeMore(RBaseAdapter baseAdapter) {

    }
}
