package com.angcyo.uidemo.layout.demo;

import com.angcyo.uidemo.R;
import com.angcyo.uidemo.layout.base.BaseItemUIView;
import com.angcyo.uiview.base.Item;
import com.angcyo.uiview.base.SingleItem;
import com.angcyo.uiview.recycler.RBaseViewHolder;

import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/24 16:20
 * 修改人员：Robi
 * 修改时间：2017/04/24 16:20
 * 修改备注：
 * Version: 1.0.0
 */
public class SpanUIView extends BaseItemUIView {
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.view_span_demo_layout;
    }

    @Override
    protected void createItems(List<SingleItem> items) {
        items.add(new SingleItem() {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                String text = "https://www.baidu.com http://www.baidu.com www.baidu.com https://baidu.com";
                holder.tv(R.id.text1).setText(text);
            }
        });
    }
}