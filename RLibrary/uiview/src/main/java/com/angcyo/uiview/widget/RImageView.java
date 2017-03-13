package com.angcyo.uiview.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：自带点击效果的ImageView
 * 创建人员：Robi
 * 创建时间：2017/03/10 11:45
 * 修改人员：Robi
 * 修改时间：2017/03/10 11:45
 * 修改备注：
 * Version: 1.0.0
 */
public class RImageView extends AppCompatImageView {
    public RImageView(Context context) {
        super(context);
    }

    public RImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Drawable drawable = getDrawable();
                if (drawable != null) {
                    drawable.mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                clearColor();
                break;
        }

        return super.onTouchEvent(event);
    }

    private void clearColor() {
        Drawable drawableUp = getDrawable();
        if (drawableUp != null) {
            drawableUp.mutate().clearColorFilter();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearColor();
        //setImageDrawable(null);
    }
}
