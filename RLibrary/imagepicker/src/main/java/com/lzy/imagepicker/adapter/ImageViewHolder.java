package com.lzy.imagepicker.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/02/21 15:07
 * 修改人员：Robi
 * 修改时间：2017/02/21 15:07
 * 修改备注：
 * Version: 1.0.0
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {
    public ImageViewHolder(View itemView) {
        super(itemView);
    }

    public <T extends View> T v(@IdRes int resId) {
        return (T) itemView.findViewById(resId);
    }
}
