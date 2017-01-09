package com.angcyo.uiview.github.all.base.adapter.listener;

import android.view.View;
import android.view.ViewGroup;

/**
 * 介绍：ViewGroup里 点击事件监听器
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 16/12/10.
 */

public interface OnItemClickListener {
    void onItemClick(ViewGroup parent, View itemView, int position);
}
