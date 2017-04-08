package com.angcyo.uiview.recycler.adapter;

import android.content.Context;

import com.angcyo.uiview.recycler.RBaseViewHolder;

import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：支持分组控制的Adapter
 * 创建人员：Robi
 * 创建时间：2017/04/07 15:14
 * 修改人员：Robi
 * 修改时间：2017/04/07 15:14
 * 修改备注：
 * Version: 1.0.0
 */
public class RGroupAdapter<H, T, F> extends RExBaseAdapter<H, RGroupData<T>, F> {

    public static final int TYPE_GROUP_HEAD = 0x11000;
    public static final int TYPE_GROUP_DATA = 0x22000;

    public RGroupAdapter(Context context) {
        super(context);
    }

    public RGroupAdapter(Context context, List<RGroupData<T>> datas) {
        super(context, datas);
    }

    @Override
    public int getDataCount() {
        if (mAllDatas == null) {
            return 0;
        }
        int count = 0;
        for (RGroupData group : mAllDatas) {
            count += group.getGroupCount();
            count += group.getDataCount();
        }
        return count;
    }

    /**
     * @param position 判断位置是否在分组信息中
     */
    public boolean isInGroup(int position) {
        int groupIndex = getGroupIndex(position - getHeaderCount());
        return groupIndex >= 0;
    }

    private int getGroupIndex(int posInData) {
        if (mAllDatas == null) {
            return -1;
        }

        int count = 0;
        int index = -1;
        for (int i = 0; i < mAllDatas.size(); i++) {
            RGroupData<T> groupData = mAllDatas.get(i);
            int groupCount = groupData.getGroupCount();

            if (posInData >= count && posInData < count + groupCount) {
                index = posInData - count;
                break;
            }

            count += (groupCount + groupData.getDataCount());
        }
        return index;
    }

    private int getDataIndex(int posInData) {
        if (mAllDatas == null) {
            return -1;
        }

        int count = 0;
        int index = -1;
        for (int i = 0; i < mAllDatas.size(); i++) {
            RGroupData<T> groupData = mAllDatas.get(i);
            int groupCount = groupData.getGroupCount();
            int dataCount = groupData.getDataCount();

            if (posInData >= count + groupCount && posInData < count + groupCount + dataCount) {
                index = posInData - count - groupCount;
                break;
            }

            count += (groupCount + dataCount);
        }
        return index;
    }

    public RGroupData<T> getGroupDataFromPosition(int posInData) {
        if (mAllDatas == null) {
            return null;
        }

        int count = 0;
        for (int i = 0; i < mAllDatas.size(); i++) {
            RGroupData<T> groupData = mAllDatas.get(i);
            int groupCount = groupData.getGroupCount();
            int dataCount = groupData.getDataCount();

            if (posInData >= count && posInData < count + groupCount + dataCount) {
                return groupData;
            }

            count += (groupCount + dataCount);
        }

        return null;
    }

    @Override
    protected int getDataItemType(int posInData) {
        int groupIndex = getGroupIndex(posInData);
        if (groupIndex >= 0) {
            //有分组
            return getGroupDataFromPosition(posInData).getGroupItemType(groupIndex);
        }
        int dataIndex = getDataIndex(posInData);
        if (dataIndex >= 0) {
            return getGroupDataFromPosition(posInData).getDataItemType(dataIndex);
        }

        return super.getDataItemType(posInData);
    }

    @Override
    protected void onBindDataView(RBaseViewHolder holder, int posInData, RGroupData<T> dataBean) {
        super.onBindDataView(holder, posInData, dataBean);
        int groupIndex = getGroupIndex(posInData);
        if (groupIndex >= 0) {
            //有分组
            getGroupDataFromPosition(posInData).onBindGroupView(holder, posInData + getHeaderCount(), groupIndex);
        }
        int dataIndex = getDataIndex(posInData);
        if (dataIndex >= 0) {
            getGroupDataFromPosition(posInData).onBindDataView(holder, posInData + getHeaderCount(), dataIndex);
        }
    }
}
