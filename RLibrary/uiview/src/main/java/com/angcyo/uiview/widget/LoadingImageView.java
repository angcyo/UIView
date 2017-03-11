package com.angcyo.uiview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.angcyo.uiview.R;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/01/06 17:31
 * 修改人员：Robi
 * 修改时间：2017/01/06 17:31
 * 修改备注：
 * Version: 1.0.0
 */
public class LoadingImageView extends AppCompatImageView {

    float degrees = 0;

    public LoadingImageView(Context context) {
        this(context, null);
    }

    public LoadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setImageResource(R.drawable.loading);
        setScaleType(ScaleType.CENTER_INSIDE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final Drawable drawable = getDrawable();
        if (drawable != null) {
            canvas.save();
            canvas.translate(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
            canvas.rotate(degrees);
            final int intrinsicWidth = drawable.getIntrinsicWidth();
            final int intrinsicHeight = drawable.getIntrinsicHeight();
            drawable.setBounds(-intrinsicWidth / 2, -intrinsicHeight / 2, intrinsicWidth / 2, intrinsicHeight / 2);
            drawable.draw(canvas);
            canvas.restore();
        }

        degrees += 10;

        if (getVisibility() == VISIBLE) {
            postInvalidate();
        }
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            postInvalidate();
        }
    }
}
