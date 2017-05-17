package com.angcyo.uidemo.layout.demo;

import com.angcyo.uidemo.R;
import com.angcyo.uidemo.layout.base.BaseItemUIView;
import com.angcyo.uiview.base.Item;
import com.angcyo.uiview.base.SingleItem;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.widget.RSeekBar;

import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/26 14:53
 * 修改人员：Robi
 * 修改时间：2017/04/26 14:53
 * 修改备注：
 * Version: 1.0.0
 */
public class CustomViewUIView extends BaseItemUIView {
    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == 0) {
            return R.layout.item_custom_view1;
        }
        return R.layout.item_custom_view2;
    }

    @Override
    protected void createItems(List<SingleItem> items) {
        items.add(new SingleItem() {
            @Override
            public void onBindView(final RBaseViewHolder holder, final int posInData, Item dataBean) {
                RSeekBar seekBar = holder.v(R.id.seek_bar);
                seekBar.addOnProgressChangeListener(new RSeekBar.OnProgressChangeListener() {
                    @Override
                    public void onProgress(int progress) {
                        holder.tv(R.id.progress_view).setText(progress + "");
                    }
                });
            }
        });
        items.add(new SingleItem() {
            @Override
            public void onBindView(final RBaseViewHolder holder, final int posInData, Item dataBean) {
            }
        });
    }
}
