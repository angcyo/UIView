package com.angcyo.uiview.recycler;

import android.support.annotation.CallSuper;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.angcyo.uiview.utils.ScreenUtil;

import java.util.List;

/**
 * 根据Item的数量, 自动设置RecyclerView的大小和Item的大小
 * 规则:
 * 1个item , 宽度和高度相等
 * 2个item, 宽度平分, 高度和宽度相等
 * 3个item, 宽度平分, 高度和宽度相等
 * 4个item, 2倍平分宽度, 高度=宽度, 另起一行一致
 * 5个之后的item, 采用3平分的方法
 */
public abstract class ResizeAdapter<T> extends RBaseAdapter<T> {

    private static int MAX_COUNT = 3;//最多多少列

    private boolean isDeleteModel = false;
    private RRecyclerView mRecyclerView;
    /**
     * 如果RecyclerView设置了分割线, 需要设置这个属性
     */
    private int dividerHeight = 0;

    public ResizeAdapter(RRecyclerView recyclerView) {
        super(recyclerView.getContext());
        mRecyclerView = recyclerView;
        if (!(recyclerView.getLayoutManager() instanceof GridLayoutManager)) {
            throw new IllegalArgumentException("RecyclerView仅支持GridLayoutManager");
        }
    }

    private static void setHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
        }
    }

    /**
     * 根据数量, 返回多少列
     */
    private static int getColumnCount(int size) {
        int count;
        if (size == 4) {
            count = 2;
        } else if (size >= MAX_COUNT) {
            count = MAX_COUNT;
        } else {
            count = size % MAX_COUNT;
        }
        return count;
    }

    /**
     * 根据数量, 返回多少行
     */
    private static int getLineCount(int size) {
        int count;
        if (size == 4) {
            count = 2;
        } else {
            count = (int) Math.ceil(size * 1.f / MAX_COUNT);
        }
        return count;
    }

    /**
     * 通过数量, 计算出对应的高度
     */
    private static int getHeight(int count) {
        int height;
        int screenWidth = ScreenUtil.screenWidth;
        height = screenWidth / getColumnCount(count);
        return height;
    }

    @Override
    public RBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RBaseViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        viewHolder.itemView.setLayoutParams(new ViewGroup.LayoutParams(-1, getItemHeight()));
        return viewHolder;
    }

    @CallSuper
    @Override
    protected void onBindView(RBaseViewHolder holder, int position, T bean) {
        setHeight(holder.itemView, getItemHeight());
    }

    protected int getItemHeight() {
        return getHeight(getItemCount());
    }

    /**
     * 数据改变之后, 都需要调用此方法, 达到重置宽度高度的效果
     */
    @Override
    public void resetData(List<T> datas) {
        super.resetData(datas);
        resetRecyclerViewHeight();
    }

    protected void resetRecyclerViewHeight() {
        int size = getItemCount();
        double line = getLineCount(size);
        setHeight(mRecyclerView, (int) ((int) (line * getHeight(size)) +
                Math.max(0, line - 1) * dividerHeight));
        ((GridLayoutManager) mRecyclerView.getLayoutManager())
                .setSpanCount(getColumnCount(size));
    }

    public void setDividerHeight(int dividerHeight) {
        this.dividerHeight = dividerHeight;
    }
}
