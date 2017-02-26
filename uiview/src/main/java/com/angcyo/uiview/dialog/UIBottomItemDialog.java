package com.angcyo.uiview.dialog;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.uiview.R;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/02/24 12:11
 * 修改人员：Robi
 * 修改时间：2017/02/24 12:11
 * 修改备注：
 * Version: 1.0.0
 */
public class UIBottomItemDialog extends UIItemDialog {

    //是否显示取消按钮
    boolean showCancelButton = true;
    /*是否显示分割线*/
    boolean showDivider = true;
    /*标题文本, 没有则隐藏标题*/
    String titleString;

    public UIBottomItemDialog() {
    }

    public static UIBottomItemDialog build() {
        return new UIBottomItemDialog();
    }

    public UIBottomItemDialog setShowCancelButton(boolean showCancelButton) {
        this.showCancelButton = showCancelButton;
        return this;
    }

    public UIBottomItemDialog setShowDivider(boolean showDivider) {
        this.showDivider = showDivider;
        return this;
    }

    public UIBottomItemDialog setTitleString(String titleString) {
        this.titleString = titleString;
        return this;
    }

    @Override
    protected View inflateDialogView(RelativeLayout dialogRootLayout, LayoutInflater inflater) {
        return inflate(R.layout.base_dialog_bottom_layout);
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);
        if (!showCancelButton) {
            mViewHolder.v(R.id.cancel_control_layout).setVisibility(View.GONE);
        }

        TextView titleView = mViewHolder.v(R.id.base_title_view);
        if (TextUtils.isEmpty(titleString)) {
            titleView.setVisibility(View.GONE);
        } else {
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(titleString);
        }

        if (showDivider) {
            mItemContentLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE | LinearLayout.SHOW_DIVIDER_BEGINNING);
        } else {
            mItemContentLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        }
    }

    @Override
    protected void inflateItem() {
        int size = mItemInfos.size();
        for (int i = 0; i < size; i++) {
            ItemInfo info = mItemInfos.get(i);
            TextView textView = getItem(info);
            textView.setTextColor(mActivity.getResources().getColor(R.color.base_text_color));
            textView.setBackgroundResource(R.drawable.base_bg_selector);
            mItemContentLayout.addView(textView,
                    new ViewGroup.LayoutParams(-1,
                            mActivity.getResources().getDimensionPixelSize(R.dimen.base_item_size)));
        }
    }
}
