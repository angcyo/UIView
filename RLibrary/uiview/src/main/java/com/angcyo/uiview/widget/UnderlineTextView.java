package com.angcyo.uiview.widget;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/13 9:09
 * 修改人员：Robi
 * 修改时间：2016/12/13 9:09
 * 修改备注：
 * Version: 1.0.0
 */
public class UnderlineTextView extends AppCompatTextView {
    public UnderlineTextView(Context context) {
        super(context);
    }

    public UnderlineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnderlineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        getPaint().setAntiAlias(true);
    }
}
