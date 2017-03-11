package com.angcyo.uiview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述： 实现了 checked 状态的 组合布局
 * 创建人员：Robi
 * 创建时间：2017/02/13 16:03
 * 修改人员：Robi
 * 修改时间：2017/02/13 16:03
 * 修改备注：
 * Version: 1.0.0
 */
public class RLayoutCheckView extends RelativeLayout implements RCheckGroup.ICheckView {

    boolean mChecked = false;
    View checkView;//选中状态显示的View, 非选中隐藏

    public RLayoutCheckView(Context context) {
        this(context, null);
    }

    public RLayoutCheckView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        for (int i = 0; i < getChildCount(); i++) {
            final View view = getChildAt(i);
            final Object tag = view.getTag();
            if (tag != null && "check".equalsIgnoreCase(tag.toString())) {
                checkView = view;
                break;
            }
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        boolean old = mChecked;
        mChecked = checked;
        if (checkView != null && mChecked != old) {
            checkView.setVisibility(mChecked ? VISIBLE : GONE);
        }
    }
}
