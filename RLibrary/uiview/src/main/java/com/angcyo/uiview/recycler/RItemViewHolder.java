package com.angcyo.uiview.recycler;

import android.view.View;
import android.view.ViewGroup;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：用来创建普通Item界面的ViewHolder, 比如设置界面, 一条一条显示的界面, 等类似界面
 * 创建人员：Robi
 * 创建时间：2017/02/15 10:55
 * 修改人员：Robi
 * 修改时间：2017/02/15 10:55
 * 修改备注：
 * Version: 1.0.0
 */
@Deprecated
public abstract class RItemViewHolder extends RBaseViewHolder {
    public RItemViewHolder(ViewGroup parent, int viewType) {
        super(parent, viewType);
    }

    protected abstract View getItemView(ViewGroup parent, int viewType);
}
