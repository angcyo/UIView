package com.angcyo.uidemo.layout.demo;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.Item;
import com.angcyo.uiview.base.SingleItem;
import com.angcyo.uiview.base.UIItemUIView;
import com.angcyo.uiview.recycler.RBaseViewHolder;

import java.util.List;

/**
 * Created by angcyo on 2017-03-15.
 */

public class StickLayoutDemoUIView extends UIItemUIView<SingleItem> {
    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == 0) {
            return R.layout.item_stick_layout_top;
        }
        return R.layout.item_stick_layout_normal;
    }

    @Override
    protected String getTitleString() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void createItems(List<SingleItem> items) {
        items.add(new SingleItem() {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {

            }
        });
        items.add(new SingleItem(SingleItem.Type.TOP) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {

            }
        });
        items.add(new SingleItem(SingleItem.Type.TOP) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {

            }
        });
        items.add(new SingleItem(SingleItem.Type.TOP) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {

            }
        });
        items.add(new SingleItem(SingleItem.Type.TOP) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {

            }
        });
        items.add(new SingleItem(SingleItem.Type.TOP) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {

            }
        });
        items.add(new SingleItem(SingleItem.Type.TOP) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {

            }
        });
    }
}
