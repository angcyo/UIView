package com.angcyo.uidemo.layout.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.angcyo.library.utils.L;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：电池View
 * 创建人员：Robi
 * 创建时间：2017/04/27 15:11
 * 修改人员：Robi
 * 修改时间：2017/04/27 15:11
 * 修改备注：
 * Version: 1.0.0
 */
public class BatteryView extends View {

    Paint mPaint;
    private float mDensity;
    /**
     * 边框的宽度
     */
    private float mBorderWidth;

    /**
     * 电池头部的高度, 宽度默认是View宽度的1/2, 边框一样大
     */
    private int mLittleHeight;//

    /**
     * 0-100的取值范围
     */
    private int batteryProgress = 0;

    public BatteryView(Context context) {
        super(context);
        initView();
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mDensity = getResources().getDisplayMetrics().density;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderWidth = 2 * mDensity;
        mLittleHeight = (int) (mBorderWidth * 2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) (60 * mDensity);
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) (100 * mDensity);
        }

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    public void draw(Canvas canvas) {
        L.e("子View call: draw([canvas])-> ");
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        L.e("子View call: dispatchDraw([canvas])-> ");
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        L.e("子View call: onDraw([canvas])-> ");
        canvas.drawColor(Color.BLACK);

        int width = getMeasuredWidth();

        //绘制边框
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(Color.GREEN);

        //电池容量的顶部和底部的距离
        float top = 0 + mBorderWidth / 2 + mLittleHeight + mBorderWidth;
        float bottom = getMeasuredHeight() - mBorderWidth / 2;

        //绘制电池主体
        canvas.drawRect(0 + mBorderWidth / 2, top,
                width - mBorderWidth / 2, bottom,
                mPaint);
        //绘制电池头部
        canvas.drawRect(width / 2 - width / 4, 0 + mBorderWidth / 2,
                width / 2 + width / 4, 0 + mBorderWidth / 2 + mLittleHeight + mBorderWidth,
                mPaint);

        //绘制电池容量
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        float value = (bottom - top) * (1 - batteryProgress * 1f / 100);
        //L.e("call: onDraw([canvas])-> " + top + value);
        canvas.drawRect(0 + mBorderWidth / 2, top + value,
                width - mBorderWidth / 2, bottom,
                mPaint);

        //60帧重绘
        postDelayed(new Runnable() {
            @Override
            public void run() {
                batteryProgress++;
                if (batteryProgress > 100) {
                    batteryProgress = 0;
                }
                postInvalidate();
            }
        }, 16);
    }
}
