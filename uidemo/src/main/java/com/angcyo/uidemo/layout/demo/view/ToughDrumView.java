package com.angcyo.uidemo.layout.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：油桶 View
 * 创建人员：Robi
 * 创建时间：2017/05/12 10:28
 * 修改人员：Robi
 * 修改时间：2017/05/12 10:28
 * 修改备注：
 * Version: 1.0.0
 */
public class ToughDrumView extends View {

    float toughHeight;//椭圆的高度
    private float mDensity;
    private Paint mPaint;
    private float mBorderWidth;

    private int curProgress = 0;

    public ToughDrumView(Context context) {
        super(context);
        initView();
    }

    public ToughDrumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ToughDrumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mDensity = getResources().getDisplayMetrics().density;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderWidth = 2 * mDensity;
        mPaint.setStrokeWidth(mBorderWidth);

        toughHeight = 20 * mDensity;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
            widthSize = (int) (60 * mDensity);
        }
        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            heightSize = (int) (100 * mDensity);
        }

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.DKGRAY);

        //绘制进度
        drawProgress(canvas);

        //绘制底部椭圆
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        canvas.drawOval(0 + mBorderWidth / 2, getMeasuredHeight() - toughHeight + mBorderWidth / 2,
                getMeasuredWidth() - mBorderWidth / 2, getMeasuredHeight() - mBorderWidth / 2, mPaint);
        canvas.restore();

        //绘制左右边框
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(0 + mBorderWidth / 2, toughHeight / 2 + mBorderWidth / 2,
                0 + mBorderWidth / 2, getMeasuredHeight() - toughHeight / 2 - mBorderWidth / 2, mPaint);

        canvas.drawLine(getMeasuredWidth() - mBorderWidth / 2, toughHeight / 2 + mBorderWidth / 2,
                getMeasuredWidth() - mBorderWidth / 2, getMeasuredHeight() - toughHeight / 2 - mBorderWidth / 2, mPaint);
        canvas.restore();

        //绘制顶部椭圆
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        canvas.drawOval(0 + mBorderWidth / 2, 0 + mBorderWidth / 2,
                getMeasuredWidth() - mBorderWidth / 2, toughHeight - mBorderWidth / 2, mPaint);
        canvas.restore();

        //60帧重绘
        postDelayed(new Runnable() {
            @Override
            public void run() {
                curProgress++;
                if (curProgress > 100) {
                    curProgress = 0;
                }
                postInvalidate();
            }
        }, 16);
    }

    private void drawProgress(Canvas canvas) {
        int maxProgressHeight = (int) (getMeasuredHeight() - toughHeight - mBorderWidth);

        int bottom = (int) (getMeasuredHeight() - mBorderWidth / 2);
        int startY = bottom;
        for (int i = 0; i < curProgress; i++) {
            canvas.save();
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.BLUE);
            canvas.drawOval(0 + mBorderWidth / 2, startY - toughHeight + mBorderWidth,
                    getMeasuredWidth() - mBorderWidth / 2, startY, mPaint);
            canvas.restore();

            startY = (int) (bottom - i / 100f * maxProgressHeight);
        }

        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        canvas.drawOval(0 + mBorderWidth / 2, startY - toughHeight + mBorderWidth,
                getMeasuredWidth() - mBorderWidth / 2, startY, mPaint);
        canvas.restore();
    }
}
