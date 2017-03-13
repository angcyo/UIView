package com.angcyo.uiview.github.swipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.angcyo.uiview.R;
import com.angcyo.uiview.github.swipe.recyclerview.SwipeMenuAdapter;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.widget.ILoadMore;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/23 15:03
 * 修改人员：Robi
 * 修改时间：2016/12/23 15:03
 * 修改备注：
 * Version: 1.0.0
 */
public abstract class RBaseMenuAdapter<T> extends SwipeMenuAdapter<RBaseViewHolder> {

    protected List<T> mAllDatas;
    protected Context mContext;
    /**
     * 是否激活加载更多
     */
    protected boolean mEnableLoadMore = false;
    protected ILoadMore mLoadMore;

    public RBaseMenuAdapter(Context context) {
        mAllDatas = new ArrayList<>();
        this.mContext = context;
    }

    public RBaseMenuAdapter(Context context, List<T> datas) {
        this.mAllDatas = datas == null ? new ArrayList<T>() : datas;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (mEnableLoadMore && isLast(position)) {
            return 666;
        }
        return getItemType(position);
    }

    /**
     * 根据position返回Item的类型.
     */
    public int getItemType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        int size = mAllDatas == null ? 0 : mAllDatas.size();
        if (mEnableLoadMore) {
            size += 1;
        }
        return size;
    }

    /**
     * 重写此方法, 实现加载更多功能
     */
    protected void onLoadMore() {

    }

    /**
     * 结束加载更多的标识, 方便下一次回调
     */
    public void setLoadMoreEnd() {
        mLoadMore.setLoadState(ILoadMore.NORMAL);
    }

    public void setLoadError() {
        mLoadMore.setLoadState(ILoadMore.LOAD_ERROR);
    }

    public void setNoMore() {
        mLoadMore.setLoadState(ILoadMore.NO_MORE);
    }

    private boolean isLast(int position) {
        return position == getItemCount() - 1;
    }

    /**
     * 启用加载更多功能
     */
    public void setEnableLoadMore(boolean enableLoadMore) {
        mEnableLoadMore = enableLoadMore;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        View item;
        if (mEnableLoadMore && viewType == 666) {
            item = LayoutInflater.from(mContext).inflate(R.layout.base_item_load_more_layout, parent, false);
            mLoadMore = (ILoadMore) item;
        } else {
            int itemLayoutId = getItemLayoutId(viewType);
            if (itemLayoutId == 0) {
                item = createContentView(parent, viewType);
            } else {
                item = LayoutInflater.from(mContext).inflate(itemLayoutId, parent, false);
            }
        }
        return item;
    }

    @Override
    public RBaseViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new RBaseViewHolder(realContentView, viewType);
    }

    @Override
    public void onBindViewHolder(RBaseViewHolder holder, int position) {
        if (mEnableLoadMore && isLast(position)) {
            onBindLoadMore();
        } else {
            try {
                onBindView(holder, position, mAllDatas.size() > position ? mAllDatas.get(position) : null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void onBindLoadMore() {
        if (mLoadMore.getLoadState() != ILoadMore.LOAD_MORE &&
                mLoadMore.getLoadState() != ILoadMore.NO_MORE) {
            mLoadMore.setLoadState(ILoadMore.LOAD_MORE);
            onLoadMore();
        }
    }

    //--------------需要实现的方法------------//

    /**
     * 当 {@link #getItemLayoutId(int)} 返回0的时候, 会调用此方法
     */
    protected View createContentView(ViewGroup parent, int viewType) {
        return null;
    }

    protected abstract int getItemLayoutId(int viewType);

    protected abstract void onBindView(RBaseViewHolder holder, int position, T bean);

    //---------------滚动事件的处理--------------------//

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    }

    //----------------Item 数据的操作-----------------//

    /**
     * 在最后的位置插入数据
     */
    public void addLastItem(T bean) {
        int startPosition = mAllDatas.size();
        mAllDatas.add(bean);
        notifyItemInserted(startPosition);
    }

    /**
     * 解决九宫格添加图片后,添加按钮消失的bug
     */
    public void addLastItemSafe(T bean) {
        int startPosition = mAllDatas.size();
        mAllDatas.add(bean);
        int itemCount = getItemCount();
        if (itemCount > startPosition + 1) {
            notifyItemInserted(startPosition);
        } else {
            notifyItemChanged(itemCount - 1);//
        }
    }

    public void addFirstItem(T bean) {
        List<T> tempBeans = new ArrayList<>();
        tempBeans.add(bean);
        tempBeans.addAll(mAllDatas);
        mAllDatas.clear();
        mAllDatas = tempBeans;
        notifyItemInserted(0);
    }

    /**
     * delete item with object
     */
    public void deleteItem(T bean) {
        if (mAllDatas != null) {
            int size = mAllDatas.size();
            int indexOf = mAllDatas.indexOf(bean);
            if (indexOf > -1) {
                if (onDeleteItem(bean)) {
                    mAllDatas.remove(bean);
                    notifyItemRemoved(indexOf);
                    notifyItemRangeChanged(indexOf, size - indexOf);
                }
            }
        }
    }

    public void deleteItem(int position) {
        if (mAllDatas != null) {
            int size = mAllDatas.size();
            if (size > position) {
                mAllDatas.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, size - position);
            }
        }
    }

    protected boolean onDeleteItem(T bean) {
        return true;
    }

    public void removeFirstItem() {
        mAllDatas.remove(0);
        notifyItemRemoved(0);
    }

    public void removeLastItem() {
        int last = mAllDatas.size() - 1;
        mAllDatas.remove(last);
        notifyItemRemoved(last);
    }

    /**
     * 重置数据
     */
    public void resetData(List<T> datas) {
        if (datas == null) {
            this.mAllDatas = new ArrayList<>();
        } else {
            this.mAllDatas = datas;
        }
        notifyDataSetChanged();
    }

    /**
     * 追加数据
     */
    public void appendData(List<T> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        if (this.mAllDatas == null) {
            this.mAllDatas = new ArrayList<>();
        }
        int startPosition = this.mAllDatas.size();
        this.mAllDatas.addAll(datas);
        notifyItemRangeInserted(startPosition, datas.size());
    }

    public List<T> getAllDatas() {
        return mAllDatas;
    }
}
