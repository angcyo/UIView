package com.angcyo.uiview.design;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.angcyo.library.utils.L;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/03/16 17:03
 * 修改人员：Robi
 * 修改时间：2017/03/16 17:03
 * 修改备注：
 * Version: 1.0.0
 */
public class TestLayoutManager extends RecyclerView.LayoutManager {

    public TestLayoutManager() {
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        L.e("call: generateDefaultLayoutParams([])-> ");
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        L.e("call: onLayoutChildren([recycler, state])-> ");
    }
}
