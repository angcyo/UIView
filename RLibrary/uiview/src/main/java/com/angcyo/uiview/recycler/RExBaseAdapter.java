package com.angcyo.uiview.recycler;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：支持头部, 支持尾部数据的Adapter
 * 创建人员：Robi
 * 创建时间：2017/01/05 14:43
 * 修改人员：Robi
 * 修改时间：2017/01/05 14:43
 * 修改备注：
 * Version: 1.0.0
 */
public abstract class RExBaseAdapter<H, T, F> extends RModelAdapter<T> {

    public static final int TYPE_HEADER = 10;
    public static final int TYPE_FOOTER = 12;
    public static final int TYPE_DATA = 11;

    /**
     * 头部数据集合
     */
    protected List<H> mAllHeaderDatas;
    /**
     * 尾部数据集合
     */
    protected List<F> mAllFooterDatas;


    public RExBaseAdapter(Context context) {
        super(context);
    }

    public RExBaseAdapter(Context context, List<T> datas) {
        super(context, datas);
    }

    @Override
    final public int getItemType(int position) {
        if (isInHeader(position)) {
            return getHeaderItemType(position);
        } else if (isInData(position)) {
            return getDataItemType(position - getHeaderCount());
        } else if (isInFooter(position)) {
            return getFooterItemType(position - getHeaderCount() - getDataCount());
        }
        return super.getItemType(position);
    }

    protected int getHeaderItemType(int posInHeader) {
        return TYPE_HEADER;
    }

    protected int getDataItemType(int posInData) {
        return TYPE_DATA;
    }

    protected int getFooterItemType(int posInFooter) {
        return TYPE_FOOTER;
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getDataCount() + getFooterCount() + (isEnableLoadMore() ? 1 : 0);
    }

    /**
     * 判断当前的位置,是否在头部范围之内
     */
    public boolean isInHeader(int position) {
        return position < getHeaderCount();
    }

    public boolean isInFooter(int position) {
        return position < getHeaderCount() + getDataCount() + getFooterCount();
    }

    public boolean isInData(int position) {
        return position < getHeaderCount() + getDataCount();
    }

    /**
     * 获取头部数据数量
     */
    public int getHeaderCount() {
        return mAllHeaderDatas == null ? 0 : mAllHeaderDatas.size();
    }

    /**
     * 获取尾部数据数量
     */
    public int getFooterCount() {
        return mAllFooterDatas == null ? 0 : mAllFooterDatas.size();
    }

    /**
     * 获取中间数据数量
     */
    public int getDataCount() {
        return mAllDatas == null ? 0 : mAllDatas.size();
    }

    public int getRawItemCount() {
        return getHeaderCount() + getDataCount() + getFooterCount();
    }

    @Override
    final protected void onBindCommonView(RBaseViewHolder holder, int position, T bean) {
        if (isInHeader(position)) {
            onBindHeaderView(holder, position, mAllHeaderDatas.size() > position ? mAllHeaderDatas.get(position) : null);
        } else if (isInData(position)) {
            position -= getHeaderCount();
            onBindDataView(holder, position, mAllDatas.size() > position ? mAllDatas.get(position) : null);
        } else if (isInFooter(position)) {
            position -= getHeaderCount() - getDataCount();
            onBindFooterView(holder, position, mAllFooterDatas.size() > position ? mAllFooterDatas.get(position) : null);
        }
    }

    @Override
    protected void onBindModelView(int model, boolean isSelector, RBaseViewHolder holder, int position, T bean) {

    }

    @Override
    protected void onBindNormalView(RBaseViewHolder holder, int position, T bean) {

    }

    /**
     * 尾部布局数据绑定
     */
    protected void onBindFooterView(RBaseViewHolder holder, int posInFooter, F footerBean) {

    }

    /**
     * 头部布局数据绑定
     */
    protected void onBindHeaderView(RBaseViewHolder holder, int posInHeader, H headerBean) {

    }

    /**
     * 中间布局数据绑定
     */
    protected void onBindDataView(RBaseViewHolder holder, int posInData, T dataBean) {

    }


    //------------------------------设置--------------------------------//


    public RExBaseAdapter<H, T, F> setAllFooterDatas(List<F> allFooterDatas) {
        mAllFooterDatas = allFooterDatas;
        if (mAllFooterDatas == null) {
            mAllFooterDatas = new ArrayList<>();
        }
        return this;
    }

    public RExBaseAdapter<H, T, F> setAllHeaderDatas(List<H> allHeaderDatas) {
        mAllHeaderDatas = allHeaderDatas;
        if (mAllHeaderDatas == null) {
            mAllHeaderDatas = new ArrayList<>();
        }
        return this;
    }

    public RExBaseAdapter<H, T, F> setFooterData(F footerData) {
        if (mAllFooterDatas == null) {
            mAllFooterDatas = new ArrayList<>();
        }
        mAllFooterDatas.add(footerData);
        return this;
    }

    public RExBaseAdapter<H, T, F> setHeaderData(H headerData) {
        if (mAllHeaderDatas == null) {
            mAllHeaderDatas = new ArrayList<>();
        }
        mAllHeaderDatas.add(headerData);
        return this;
    }

    public RExBaseAdapter<H, T, F> setAllDatas(List<T> allDatas) {
        mAllDatas = allDatas;
        if (mAllDatas == null) {
            mAllDatas = new ArrayList<>();
        }
        return this;
    }

    /**
     * 重置头部数据
     */
    public void resetHeaderData(List<H> headerDatas) {
        int oldSize = getListSize(mAllHeaderDatas);
        int newSize = getListSize(headerDatas);
        if (headerDatas == null) {
            this.mAllHeaderDatas = new ArrayList<>();
        } else {
            this.mAllHeaderDatas = headerDatas;
        }

        if (oldSize == newSize) {
            notifyItemRangeChanged(0, oldSize);
        } else {
            notifyDataSetChanged();
        }
    }

    /**
     * 追加头部数据
     */
    public void appendHeaderData(List<H> headerDatas) {
        if (headerDatas == null || headerDatas.isEmpty()) {
            return;
        }
        if (this.mAllHeaderDatas == null) {
            this.mAllHeaderDatas = new ArrayList<>();
        }
        int startPosition = getHeaderCount();
        this.mAllHeaderDatas.addAll(headerDatas);
        notifyItemRangeInserted(startPosition, headerDatas.size());
        notifyItemRangeChanged(startPosition, getItemCount());
    }

    /**
     * 重置尾部数据
     */
    public void resetFooterData(List<F> footerDatas) {
        int oldSize = getListSize(mAllFooterDatas);
        int newSize = getListSize(footerDatas);

        if (footerDatas == null) {
            this.mAllFooterDatas = new ArrayList<>();
        } else {
            this.mAllFooterDatas = footerDatas;
        }

        if (oldSize == newSize) {
            notifyItemRangeChanged(getHeaderCount() + getDataCount(), oldSize);
        } else {
            notifyDataSetChanged();
        }
    }

    /**
     * 追加尾部数据
     */
    public void appendFooterData(List<F> footerDatas) {
        if (footerDatas == null || footerDatas.isEmpty()) {
            return;
        }
        if (this.mAllFooterDatas == null) {
            this.mAllFooterDatas = new ArrayList<>();
        }
        int startPosition = getHeaderCount() + getDataCount() + mAllFooterDatas.size();
        this.mAllFooterDatas.addAll(footerDatas);
        notifyItemRangeInserted(startPosition, footerDatas.size());
        notifyItemRangeChanged(startPosition, getItemCount());
    }

    /**
     * 重置中间标准数据
     */
    public void resetAllData(List<T> allDatas) {
        resetData(allDatas);
    }

    /**
     * 追加中间标准数据
     */
    public void appendAllData(List<T> allDatas) {
        appendData(allDatas);
    }


    //-------------------------------操作--------------------------------//

    /**
     * 在最后的位置插入数据
     */
    @Override
    public void addLastItem(T bean) {
        int startPosition = getHeaderCount() + getDataCount();
        mAllDatas.add(bean);
        notifyItemInserted(startPosition);
        notifyItemRangeChanged(startPosition, getItemCount());
    }

    /**
     * 解决九宫格添加图片后,添加按钮消失的bug
     */
    @Override
    public void addLastItemSafe(T bean) {
        int startPosition = getHeaderCount() + getDataCount();
        mAllDatas.add(bean);
        int itemCount = getItemCount();
        if (itemCount > startPosition + 1) {
            notifyItemInserted(startPosition);
            notifyItemRangeChanged(startPosition, getItemCount());
        } else {
            notifyItemChanged(itemCount - 1);//
        }
    }

    @Override
    public void addFirstItem(T bean) {
        if (mAllDatas == null) {
            mAllDatas = new ArrayList<>();
        }
        mAllDatas.add(0, bean);
        notifyItemInserted(getHeaderCount());
        notifyItemRangeChanged(getHeaderCount(), getItemCount());
    }

    /**
     * delete item with object
     */
    @Override
    public void deleteItem(T bean) {
        if (mAllDatas != null) {
            int size = mAllDatas.size();
            int indexOf = mAllDatas.indexOf(bean) + getHeaderCount();
            if (indexOf > -1) {
                if (onDeleteItem(bean)) {
                    mAllDatas.remove(bean);
                    notifyItemRemoved(indexOf);
                    notifyItemRangeChanged(indexOf, size - indexOf);
                }
            }
        }
    }

    @Override
    public void deleteItem(int position) {
        position += getHeaderCount();
        if (mAllDatas != null) {
            int size = mAllDatas.size();
            if (size > position) {
                mAllDatas.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, size - position);
            }
        }
    }

    @Override
    public void removeFirstItem() {
        mAllDatas.remove(0);
        notifyItemRemoved(getHeaderCount());
        notifyItemRangeChanged(getHeaderCount(), getItemCount());
    }

    @Override
    public void removeLastItem() {
        int last = mAllDatas.size() - 1;
        mAllDatas.remove(last);
        notifyItemRemoved(last + getHeaderCount());
        notifyItemRangeChanged(last, getItemCount());
    }

    /**
     * 重置数据
     */
    @Override
    public void resetData(List<T> datas) {
        int oldSize = getListSize(mAllDatas);
        int newSize = getListSize(datas);
        if (datas == null) {
            this.mAllDatas = new ArrayList<>();
        } else {
            this.mAllDatas = datas;
        }
        if (oldSize == newSize) {
            notifyItemRangeChanged(getHeaderCount(), oldSize);
        } else {
            notifyDataSetChanged();
        }
    }

    /**
     * 追加数据
     */
    @Override
    public void appendData(List<T> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        if (this.mAllDatas == null) {
            this.mAllDatas = new ArrayList<>();
        }
        int startPosition = this.mAllDatas.size() + getHeaderCount();
        this.mAllDatas.addAll(datas);
        notifyItemRangeInserted(startPosition, datas.size());
        notifyItemRangeChanged(startPosition, getItemCount());
    }

    public interface ObjectEmpty {
        /**
         * 如果想要添加一个空数据的item, 实现此接口返回true
         */
        boolean isDataEmpty();
    }
}
