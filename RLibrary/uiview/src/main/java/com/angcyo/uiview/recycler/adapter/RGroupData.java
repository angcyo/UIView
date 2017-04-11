package com.angcyo.uiview.recycler.adapter;

import com.angcyo.uiview.recycler.RBaseViewHolder;

import java.util.List;

import static com.angcyo.uiview.recycler.adapter.RGroupAdapter.TYPE_GROUP_DATA;
import static com.angcyo.uiview.recycler.adapter.RGroupAdapter.TYPE_GROUP_HEAD;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：分组数据
 * 创建人员：Robi
 * 创建时间：2017/04/07 15:16
 * 修改人员：Robi
 * 修改时间：2017/04/07 15:16
 * 修改备注：
 * Version: 1.0.0
 */
public class RGroupData<T> {


    /**
     * 分组下面的数据集合
     */
    List<T> mAllDatas;

    /**
     * 分组是否展开
     */
    boolean isExpand = true;

    public RGroupData(List<T> allDatas) {
        mAllDatas = allDatas;
    }

    /**
     * 返回数据的数量
     */
    public int getDataCount() {
        return isExpand ? (mAllDatas == null ? 0 : mAllDatas.size()) : 0;
    }

    public List<T> getAllDatas() {
        return mAllDatas;
    }

    /**
     * 分组的数量, 返回0表示没有分组信息
     */
    public int getGroupCount() {
        return 1;
    }

//    /**
//     * @param indexInGroup 当有多个分组时, index表示从0开始的索引
//     */
//    public abstract int getGroupLayoutId(int indexInGroup);
//
//    public abstract int getDataLayoutId(int indexInData);

    public int getGroupItemType(int indexInGroup) {
        return TYPE_GROUP_HEAD;
    }

    public int getDataItemType(int indexInData) {
        return TYPE_GROUP_DATA;
    }

    /**
     * @param indexInGroup 在头部数据中, 从0开始的索引
     */
    protected void onBindGroupView(RBaseViewHolder holder, int position, int indexInGroup) {

    }

    /**
     * @param indexInData 在数据中, 从0开始的索引
     */
    protected void onBindDataView(RBaseViewHolder holder, int position, int indexInData) {

    }
}
