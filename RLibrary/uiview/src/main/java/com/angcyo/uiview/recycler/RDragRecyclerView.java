package com.angcyo.uiview.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

import com.angcyo.uiview.recycler.adapter.RBaseAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：支持拖拽的RecycleView
 * 创建人员：Robi
 * 创建时间：2017/02/17 17:59
 * 修改人员：Robi
 * 修改时间：2017/02/17 17:59
 * 修改备注：
 * Version: 1.0.0
 */
public class RDragRecyclerView extends RRecyclerView {

    ItemTouchHelper mItemTouchHelper;
    OnDragCallback mDragCallback;

    public RDragRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RDragRecyclerView(Context context) {
        super(context);
    }

    public RDragRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView(Context context) {
        super.initView(context);
        if (mItemTouchHelper == null) {
            mItemTouchHelper = new ItemTouchHelper(new DragCallback());
            mItemTouchHelper.attachToRecyclerView(this);
        }
    }

    public void setDragCallback(OnDragCallback dragCallback) {
        mDragCallback = dragCallback;
    }

    public interface OnDragCallback {
        boolean canDragDirs(RecyclerView recyclerView, ViewHolder viewHolder);
    }

    private class DragCallback extends ItemTouchHelper.SimpleCallback {

        public DragCallback() {
            //第一个参数表示,什么方向支持拖拽
            super(ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0);
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public int getDragDirs(RecyclerView recyclerView, ViewHolder viewHolder) {
            if (mDragCallback != null) {
                if (mDragCallback.canDragDirs(recyclerView, viewHolder)) {
                    return ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                } else {
                    return 0;
                }
            }
            return super.getDragDirs(recyclerView, viewHolder);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
            Adapter adapter = getAdapter();

            if (mDragCallback != null) {
                if (!mDragCallback.canDragDirs(recyclerView, target)) {
                    return false;
                }
            }

            if (adapter instanceof RBaseAdapter) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                List allDatas = ((RBaseAdapter) adapter).getAllDatas();

                int size = allDatas.size();
                if (size > from && size > to) {
                    Collections.swap(allDatas, from, to);
                }
                adapter.notifyItemMoved(from, to);
            }
            return true;
        }

        @Override
        public void onSwiped(ViewHolder viewHolder, int direction) {

        }
    }
}
