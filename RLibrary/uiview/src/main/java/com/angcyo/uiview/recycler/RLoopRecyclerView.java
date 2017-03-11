package com.angcyo.uiview.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.List;


/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：无限循环的RecyclerView
 * 创建人员：Robi
 * 创建时间：2017/03/01 11:58
 * 修改人员：Robi
 * 修改时间：2017/03/01 11:58
 * 修改备注：
 * Version: 1.0.0
 */
public class RLoopRecyclerView extends RRecyclerView {

    private OnPageListener mOnPageListener;
    private RPagerSnapHelper mPagerSnapHelper;

    public RLoopRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RLoopRecyclerView(Context context) {
        super(context);
    }

    public RLoopRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
    }

    @Override
    public LoopAdapter getAdapter() {
        return (LoopAdapter) super.getAdapter();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (!(adapter instanceof LoopAdapter)) {
            throw new IllegalArgumentException("adapter must  instanceof LoopAdapter!");
        }
        super.setAdapter(adapter);
        scrollToPosition(getAdapter().getItemRawCount() * 10000);//开始时的偏移量
    }

    private void initView() {
        if (mPagerSnapHelper == null) {
            mPagerSnapHelper = new RPagerSnapHelper();
            mPagerSnapHelper.setOnPageListener(new RPagerSnapHelper.OnPageListener() {
                @Override
                public void onPageSelector(int position) {
                    int index = position % getAdapter().getItemRawCount();
                    if (mOnPageListener != null) {
                        mOnPageListener.onPageSelector(index);
                    }
                }
            }).attachToRecyclerView(this);
        }
    }

    public void setOnPageListener(OnPageListener onPageListener) {
        mOnPageListener = onPageListener;
    }

    public interface OnPageListener {
        void onPageSelector(int position);
    }

    /**
     * 循环数据源适配器
     */
    public static abstract class LoopAdapter<T> extends RBaseAdapter<T> {

        public LoopAdapter(Context context) {
            super(context);
        }

        public LoopAdapter(Context context, List<T> datas) {
            super(context, datas);
        }

        /**
         * 真实数据的大小
         */
        public int getItemRawCount() {
            return mAllDatas == null ? 0 : mAllDatas.size();
        }

        @Override
        final public int getItemType(int position) {
            return getLoopItemViewType(position % getItemRawCount());
        }

        protected int getLoopItemViewType(int position) {
            return 0;
        }

        @Override
        final protected void onBindView(RBaseViewHolder holder, int position, T bean) {
            int index = position % getItemRawCount();
            onBindLoopViewHolder(holder, index, mAllDatas.size() > index ? mAllDatas.get(index) : null);
        }

        public abstract void onBindLoopViewHolder(RBaseViewHolder holder, int position, T bean);

        @Override
        final public int getItemCount() {
            int rawCount = getItemRawCount();
            if (rawCount > 0) {
                return Integer.MAX_VALUE;
            }
            return 0;
        }
    }
}