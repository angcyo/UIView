package com.angcyo.uidemo.layout.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.angcyo.library.utils.L;
import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.resources.ResUtil;
import com.angcyo.uiview.widget.ExSoftInputLayout;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/21 8:51
 * 修改人员：Robi
 * 修改时间：2016/12/21 8:51
 * 修改备注：
 * Version: 1.0.0
 */
public class Emoji2UIView extends UIContentView {
    ExSoftInputLayout mSoftInputLayout;

    @Override
    public Animation loadLayoutAnimation() {
        return null;
    }

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_emoji_layout);
        mBaseRootLayout.setFitsSystemWindows(false);
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();
        mSoftInputLayout = mViewHolder.v(R.id.soft_input_layout);
        mSoftInputLayout.addOnEmojiLayoutChangeListener(new ExSoftInputLayout.OnEmojiLayoutChangeListener() {
            @Override
            public void onEmojiLayoutChange(boolean isEmojiShow, boolean isKeyboardShow, int oldHeight, int height) {
                L.w("表情显示:" + mSoftInputLayout.isEmojiShow() + " 键盘显示:" + mSoftInputLayout.isKeyboardShow()
                        + " 表情高度:" + mSoftInputLayout.getShowKeyboardHeight() + " 键盘高度:" + mSoftInputLayout.getKeyboardHeight());
                L.e("表情显示:" + isEmojiShow + " 键盘显示:" + isKeyboardShow + " 高度:" + height + " 旧高度:" + oldHeight);
            }
        });

        mViewHolder.click(R.id.set_padding200_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetPadding200Click();
            }
        });
        mViewHolder.click(R.id.set_padding400_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetPadding400Click();
            }
        });
        mViewHolder.click(R.id.show_layout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Emoji2UIView.this.onClick(v);
            }
        });
        mViewHolder.click(R.id.hide_layout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Emoji2UIView.this.onClick(v);
            }
        });
    }

    public void onSetPadding200Click() {
        mSoftInputLayout.showEmojiLayout((int) ResUtil.dpToPx(mActivity, 200));
    }

    public void onSetPadding400Click() {
        mSoftInputLayout.showEmojiLayout((int) ResUtil.dpToPx(mActivity, 400));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_layout:
                mSoftInputLayout.showEmojiLayout();
                break;
            case R.id.hide_layout:
                mSoftInputLayout.hideEmojiLayout();
                break;
        }
    }
}
