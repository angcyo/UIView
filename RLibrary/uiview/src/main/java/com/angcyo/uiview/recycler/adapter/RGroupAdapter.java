package com.angcyo.uiview.recycler.adapter;

import android.content.Context;

import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.utils.T;

import java.util.ArrayList;
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
public class RGroupAdapter<H, G extends RGroupData, F> extends RExBaseAdapter<H, G, F> {

    public static final int TYPE_GROUP_HEAD = 0x11000;
    public static final int TYPE_GROUP_DATA = 0x22000;

    public RGroupAdapter(Context context) {
        super(context);
    }

    public RGroupAdapter(Context context, List<G> datas) {
        super(context, datas);
    }

    @Override
    public int getDataCount() {
        return getCountFromGroup(mAllDatas);
    }

    /**
     * 从分组信息中返回真实item的数量
     */
    public int getCountFromGroup(List<G> groups) {
        if (groups == null) {
            return 0;
        }
        int count = 0;
        for (RGroupData group : groups) {
            count += group.getCount();
        }
        return count;
    }

    @Override
    public void resetData(List<G> datas) {
        int oldSize = getCountFromGroup(mAllDatas);
        int newSize = getCountFromGroup(datas);
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

    @Override
    public void appendData(List<G> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        if (this.mAllDatas == null) {
            this.mAllDatas = new ArrayList<>();
        }

        int startPosition = getDataCount() + getHeaderCount();

        this.mAllDatas.addAll(datas);
        notifyItemRangeInserted(startPosition, getCountFromGroup(datas));
        notifyItemRangeChanged(startPosition, getItemCount());
    }

    /**
     * @param position 判断位置是否在分组信息中
     */
    public boolean isInGroup(int position) {
        int groupIndex = getGroupIndex(position - getHeaderCount());
        return groupIndex >= 0;
    }

    /**
     * 通过adapter中的位置, 返回在Group头部中的索引
     */
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

    /**
     * 通过adapter中的位置, 返回在Group数据部分中的索引
     */
    private int getDataIndex(int posInData) {
        if (mAllDatas == null) {
            return -1;
        }

        int count = 0;
        int index = -1;
        for (int i = 0; i < mAllDatas.size(); i++) {
            RGroupData groupData = mAllDatas.get(i);
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

    /**
     * 通过adapter中的位置, 返回对应的{@link RGroupData}对象
     */
    public RGroupData getGroupDataFromPosition(int posInData) {
        if (mAllDatas == null) {
            return null;
        }

        int count = 0;
        for (int i = 0; i < mAllDatas.size(); i++) {
            RGroupData groupData = mAllDatas.get(i);
            int groupCount = groupData.getGroupCount();
            int dataCount = groupData.getDataCount();

            if (posInData >= count && posInData < count + groupCount + dataCount) {
                return groupData;
            }

            count += (groupCount + dataCount);
        }

        return null;
    }

    /**
     * 返回当前{@link RGroupData}在Adapter中开始的position
     */
    public int getPositionFromGroup(RGroupData groupData) {
        if (groupData == null) {
            return -1;
        }
        int position = getHeaderCount();
        if (mAllDatas == null) {
            return position;
        }

        for (int i = 0; i < mAllDatas.size(); i++) {
            RGroupData g = mAllDatas.get(i);
            if (g == groupData) {
                return position;
            }
            position += g.getCount();
        }
        return position;
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
    protected void onBindDataView(RBaseViewHolder holder, int posInData, G dataBean) {
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
