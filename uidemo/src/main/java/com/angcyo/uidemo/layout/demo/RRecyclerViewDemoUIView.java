package com.angcyo.uidemo.layout.demo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.angcyo.uidemo.R;
import com.angcyo.uidemo.layout.base.BaseItemUIView;
import com.angcyo.uiview.base.Item;
import com.angcyo.uiview.base.SingleItem;
import com.angcyo.uiview.container.ContentLayout;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RExItemDecoration;
import com.angcyo.uiview.recycler.RRecyclerView;
import com.angcyo.uiview.utils.UI;

import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/17 09:46
 * 修改人员：Robi
 * 修改时间：2017/04/17 09:46
 * 修改备注：
 * Version: 1.0.0
 */
public class RRecyclerViewDemoUIView extends BaseItemUIView {

    TextView scrollToBottomTipView;
    private boolean mVertical;

    @Override
    protected void createRecyclerRootView(ContentLayout baseContentLayout, LayoutInflater inflater) {
        super.createRecyclerRootView(baseContentLayout, inflater);

        scrollToBottomTipView = new TextView(mActivity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = Gravity.END | Gravity.BOTTOM;
        params.setMarginEnd((int) (20 * density()));
        params.bottomMargin = (int) (20 * density());
        scrollToBottomTipView.setVisibility(View.INVISIBLE);
        scrollToBottomTipView.setText("回到底部");
        scrollToBottomTipView.setBackgroundResource(R.drawable.base_dark_solid_round_shape);
        scrollToBottomTipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.scrollToLastBottom(false);
            }
        });
        baseContentLayout.addView(scrollToBottomTipView, params);
    }

    @Override
    protected void afterInflateView(ContentLayout baseContentLayout) {
        super.afterInflateView(baseContentLayout);
        int vertical = LinearLayoutManager.VERTICAL;
        mVertical = (vertical == LinearLayoutManager.VERTICAL);

//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, vertical));
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 3, vertical, false));
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_image_view;
    }

    @Override
    public void onScrollStateEnd(RRecyclerView rRecyclerView, boolean firstItemVisible, boolean lastItemVisible, boolean topCanScroll, boolean bottomCanScroll) {
        super.onScrollStateEnd(rRecyclerView, firstItemVisible, lastItemVisible, topCanScroll, bottomCanScroll);
        if (lastItemVisible) {
            scrollToBottomTipView.setVisibility(View.INVISIBLE);
        } else {
            if (bottomCanScroll) {
                scrollToBottomTipView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void createItems(List<SingleItem> items) {
        for (int i = 0; i < 30; i++) {
            final int finalI = i;
            items.add(new SingleItem() {
                @Override
                public void setItemOffsets2(Rect rect, int edge) {
                    if (mVertical) {
                        rect.bottom = mBaseOffsetSize;
                        if (!RExItemDecoration.isLeftEdge(edge)) {
                            rect.left = mBaseOffsetSize / 2;
                        }
                        if (!RExItemDecoration.isRightEdge(edge)) {
                            rect.right = mBaseOffsetSize / 2;
                        }
                    } else {
                        rect.right = mBaseOffsetSize;

                        if (!RExItemDecoration.isTopEdge(edge)) {
                            rect.top = mBaseOffsetSize / 2;
                        }
                        if (!RExItemDecoration.isBottomEdge(edge)) {
                            rect.bottom = mBaseOffsetSize / 2;
                        }
                    }
                }

                @Override
                public void draw(Canvas canvas, TextPaint paint, View itemView, Rect offsetRect, int itemCount, int position) {

                    paint.setColor(Color.RED);
                    int left = itemView.getLeft();
                    int top = itemView.getTop();
                    int right = itemView.getRight();
                    int bottom = itemView.getBottom();

                    //绘制顶部分割线
                    mDrawRect.set(left - offsetRect.left, top - offsetRect.top, right + offsetRect.right, top);
                    canvas.drawRect(mDrawRect, paint);

                    //绘制底部分割线
                    mDrawRect.set(left - offsetRect.left, bottom, right + offsetRect.right, bottom + offsetRect.bottom);
                    canvas.drawRect(mDrawRect, paint);

                    //绘制左边分割线
                    mDrawRect.set(left - offsetRect.left, top, left, bottom);
                    canvas.drawRect(mDrawRect, paint);

                    //绘制右边分割线
                    mDrawRect.set(left, top, right + offsetRect.right, bottom);
                    canvas.drawRect(mDrawRect, paint);
                }

                @Override
                public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                    if (finalI % 2 == 0) {
                        UI.setView(holder.itemView, -1, (int) (300 * density()));
                        holder.imgV(R.id.image_view).setImageResource(R.drawable.test_image);
                    } else {
                        UI.setView(holder.itemView, -1, -2);
                        holder.imgV(R.id.image_view).setImageResource(R.drawable.image_demo);
                    }
                    holder.tv(R.id.text_view).setText(posInData + "");
                }
            });
        }
    }
}
