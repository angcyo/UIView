package com.angcyo.uiview.base;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextPaint;
import android.view.View;
import android.widget.RelativeLayout;

import com.angcyo.uiview.R;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RExBaseAdapter;
import com.angcyo.uiview.recycler.RExItemDecoration;
import com.angcyo.uiview.rsen.PlaceholderView;
import com.angcyo.uiview.rsen.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angcyo on 2017-03-12.
 */

public abstract class UIItemUIView<T extends Item> extends UIRecyclerUIView<String, T, String> {

    protected List<T> mItems = new ArrayList<>();

    @Override
    public int getDefaultBackgroundColor() {
        return getColor(R.color.base_chat_bg_color);
    }

    @Override
    protected RExBaseAdapter<String, T, String> createAdapter() {
        refreshLayout();
        return new RExBaseAdapter<String, T, String>(mActivity, mItems) {
            @Override
            protected void onBindDataView(RBaseViewHolder holder, int posInData, T dataBean) {
                dataBean.onBindView(holder, posInData, dataBean);
            }

            @Override
            protected int getDataItemType(int posInData) {
                return posInData;
            }

            @Override
            protected int getItemLayoutId(int viewType) {
                return UIItemUIView.this.getItemLayoutId(viewType);
            }
        };
    }

    /**
     * 更新布局
     */
    public void refreshLayout() {
        mItems.clear();
        createItems(mItems);
        if (mExBaseAdapter != null) {
            mExBaseAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 返回布局
     */
    protected abstract int getItemLayoutId(int viewType);

    /**
     * 创建Item
     */
    protected abstract void createItems(List<T> items);

    @Override
    protected void afterInflateView(RelativeLayout baseContentLayout) {
        mRefreshLayout.setRefreshDirection(RefreshLayout.BOTH);
        mRefreshLayout.setTopView(new PlaceholderView(mActivity));
        mRefreshLayout.setBottomView(new PlaceholderView(mActivity));
        mRefreshLayout.setNotifyListener(false);

        mExBaseAdapter.setEnableLoadMore(false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.addItemDecoration(new RExItemDecoration(new RExItemDecoration.ItemDecorationCallback() {
            @Override
            public Rect getItemOffsets(LinearLayoutManager layoutManager, int position) {
                Rect rect = new Rect(0, 0, 0, 0);
                T t = mItems.get(position);
                t.setItemOffsets(rect);
                return rect;
            }

            @Override
            public void draw(Canvas canvas, TextPaint paint, View itemView, Rect offsetRect, int itemCount, int position) {
                T t = mItems.get(position);
                t.draw(canvas, paint, itemView, offsetRect, itemCount, position);
            }
        }));
    }
}
