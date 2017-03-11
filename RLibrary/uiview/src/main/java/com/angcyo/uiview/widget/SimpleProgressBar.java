package com.angcyo.uiview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.angcyo.uiview.R;


/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/11/28 17:57
 * 修改人员：Robi
 * 修改时间：2016/11/28 17:57
 * 修改备注：
 * Version: 1.0.0
 */
public class SimpleProgressBar extends View {

    int mProgress = 0;//当前的进度

    int mProgressColor;

    Paint mPaint;
    Rect mRect;

    public SimpleProgressBar(Context context) {
        super(context);
        init();
    }

    public SimpleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mProgressColor = getResources().getColor(R.color.theme_color_accent);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mProgressColor);
        mPaint.setStyle(Paint.Style.FILL);
        mRect = new Rect();
    }

    public void setProgress(int progress) {
        if (progress >= 100 || progress <= 0) {
//            setVisibility(GONE);
            ViewCompat.animate(this).translationY(-getMeasuredHeight()).setDuration(300).start();
        } else {
            if (getTranslationY() == -getMeasuredHeight()) {
                ViewCompat.animate(this).translationY(0).setDuration(300).start();
            }
            mProgress = progress;
            postInvalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            height = (int) (getResources().getDisplayMetrics().density * 4);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRect.set(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, mRect.width() * (mProgress / 100f), mRect.height(), mPaint);
    }
}
