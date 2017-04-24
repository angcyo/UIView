package com.angcyo.uidemo.layout.demo;

import android.graphics.Color;

import com.angcyo.uidemo.R;
import com.angcyo.uidemo.layout.base.BaseItemUIView;
import com.angcyo.uiview.base.Item;
import com.angcyo.uiview.base.SingleItem;
import com.angcyo.uiview.github.textview.RevealTextView;
import com.angcyo.uiview.github.textview.shimmer.Shimmer;
import com.angcyo.uiview.github.textview.shimmer.ShimmerButton;
import com.angcyo.uiview.github.textview.shimmer.ShimmerTextView;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.recycler.RBaseViewHolder;

import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/03/21 15:28
 * 修改人员：Robi
 * 修改时间：2017/03/21 15:28
 * 修改备注：
 * Version: 1.0.0
 */
public class GithubDemoUIView extends BaseItemUIView {

    boolean isInSubUIView = false;
    private Shimmer mShimmer;
    private Shimmer mShimmer1;

    @Override
    protected TitleBarPattern getTitleBar() {
        if (!isInSubUIView) {
            return super.getTitleBar();
        } else {
            return null;
        }
    }

    public GithubDemoUIView setInSubUIView(boolean inSubUIView) {
        isInSubUIView = inSubUIView;
        return this;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == 0) {
            return R.layout.item_reveal_textview;
        }
        if (viewType == 1) {
            return R.layout.item_shimmer_textview;
        }
        return 0;
    }

    @Override
    public void onViewUnload() {
        super.onViewUnload();
        mShimmer.cancel();
        mShimmer1.cancel();
    }

    @Override
    protected void createItems(List<SingleItem> items) {
        items.add(new SingleItem(SingleItem.Type.LINE, Color.GREEN) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                RevealTextView revealTextView = holder.v(R.id.reveal_text_view);
                revealTextView.setAnimationDuration(2000);
                revealTextView.setLoop(true);
                revealTextView.setAnimatedText("用来测试的文本");
            }
        });

        items.add(new SingleItem(SingleItem.Type.LINE, Color.GREEN) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                ShimmerTextView shimmerTextView = holder.v(R.id.shimmer_text_view);
                shimmerTextView.setText("用来测试的文本");
                mShimmer = new Shimmer();
                mShimmer.start(shimmerTextView);

                ShimmerButton shimmerButton = holder.v(R.id.shimmer_button_view);
                shimmerButton.setText("用来测试的文本");
                mShimmer1 = new Shimmer();
                mShimmer1.start(shimmerButton);
            }
        });
    }
}
