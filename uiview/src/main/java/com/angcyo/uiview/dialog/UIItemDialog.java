package com.angcyo.uiview.dialog;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.uiview.R;
import com.angcyo.uiview.base.UIIDialogImpl;
import com.angcyo.uiview.recycler.RBaseViewHolder;

import java.util.ArrayList;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：提供Item选择样式的对话框
 * 创建人员：Robi
 * 创建时间：2016/12/13 17:20
 * 修改人员：Robi
 * 修改时间：2016/12/13 17:20
 * 修改备注：
 * Version: 1.0.0
 */
public class UIItemDialog extends UIIDialogImpl {

    protected LinearLayout mItemContentLayout;
    protected TextView mCancelView;
    protected RBaseViewHolder mViewHolder;
    protected ArrayList<ItemInfo> mItemInfos = new ArrayList<>();

    private UIItemDialog() {
    }

    public static UIItemDialog build() {
        return new UIItemDialog();
    }

    public UIItemDialog addItem(String text, View.OnClickListener clickListener) {
        mItemInfos.add(new ItemInfo(text, clickListener));
        return this;
    }

    @Override
    protected View inflateDialogView(RelativeLayout dialogRootLayout, LayoutInflater inflater) {
        return inflate(R.layout.base_dialog_item_layout);
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);
        mViewHolder = new RBaseViewHolder(rootView);
        mItemContentLayout = mViewHolder.v(R.id.item_content_layout);
        mCancelView = mViewHolder.v(R.id.cancel_view);
        mCancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDialog();
            }
        });

        inflateItem();
    }

    /**
     * 填充item信息
     */
    protected void inflateItem() {
        int size = mItemInfos.size();
        for (int i = 0; i < size; i++) {
            ItemInfo info = mItemInfos.get(i);
            TextView textView = getItem(info);
            if (size == 1) {
                textView.setBackgroundResource(R.drawable.base_round_bg_selector);
            } else {
                if (i == 0) {
                    textView.setBackgroundResource(R.drawable.base_top_round_bg_selector);
                } else if (i == size - 1) {
                    textView.setBackgroundResource(R.drawable.base_bottom_round_bg_selector);
                } else {
                    textView.setBackgroundResource(R.drawable.base_bg_selector);
                }
            }

            mItemContentLayout.addView(textView,
                    new ViewGroup.LayoutParams(-1,
                            mActivity.getResources().getDimensionPixelSize(R.dimen.default_button_height)));
        }
    }

    protected TextView getItem(final ItemInfo info) {
        TextView textView = new TextView(mActivity);
        textView.setText(info.mItemText);
        textView.setTextColor(mActivity.getResources().getColor(R.color.theme_color_accent));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                mActivity.getResources().getDimensionPixelSize(R.dimen.default_text_size));
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.mClickListener.onClick(v);
                finishDialog();
            }
        });

        return textView;
    }

    static class ItemInfo {
        public String mItemText;
        public View.OnClickListener mClickListener;

        public ItemInfo(String itemText, View.OnClickListener clickListener) {
            mItemText = itemText;
            mClickListener = clickListener;
        }
    }
}
