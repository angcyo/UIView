package com.angcyo.uiview.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

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
            mItemTouchHelper = new ItemTouchHelper(new RDragCallback(new RDragCallback.SingleDragCallback() {
                @Override
                public boolean canDragDirs(RecyclerView recyclerView, ViewHolder viewHolder) {
                    return mDragCallback.canDragDirs(recyclerView, viewHolder);
                }
            }));
            mItemTouchHelper.attachToRecyclerView(this);
        }
    }

    public void setDragCallback(OnDragCallback dragCallback) {
        mDragCallback = dragCallback;
    }

    public interface OnDragCallback {
        boolean canDragDirs(RecyclerView recyclerView, ViewHolder viewHolder);
    }
}
