package com.angcyo.uidemo.layout.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.angcyo.library.utils.L;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：分段式 设置 view
 * 创建人员：Robi
 * 创建时间：2017/05/17 17:51
 * 修改人员：Robi
 * 修改时间：2017/05/17 17:51
 * 修改备注：
 * Version: 1.0.0
 */
public class SegmentStepView extends View {

    TextPaint mTextPaint;
    int mStrokeWidth;
    OnStepChangeListener mOnStepChangeListener;
    private float mDensity;
    /**
     * 圈圈的半径
     */
    private int mCircleRadius;
    /**
     * 圈圈之间线的宽度
     */
    private int mLineWidth;
    /**
     * 线的高度
     */
    private int mLineHeight;
    /**
     * Step的数量, 等价于 圈圈的数量
     */
    private int mStepCount;
    /**
     * 当前的step
     */
    private int mCurStepCount = 0;
    private int mCircleColor;
    private int mLineColor;
    private Rect mLineDrawRect = new Rect();

    public SegmentStepView(Context context) {
        this(context, null);
    }

    public SegmentStepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

        mDensity = getResources().getDisplayMetrics().density;
        mStrokeWidth = (int) (1 * mDensity);
        mTextPaint.setStrokeWidth(mStrokeWidth);
        mCircleRadius = (int) (mDensity * 10);
        mLineWidth = (int) (mDensity * 40);
        mLineHeight = (int) (mDensity * 6);

        mStepCount = 4;

        mCircleColor = Color.RED;
        mLineColor = Color.GREEN;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = mStepCount * 2 * mCircleRadius + (mStepCount - 1) * mLineWidth +
                    getPaddingLeft() + getPaddingRight() +
                    mStrokeWidth;
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = Math.max(2 * mCircleRadius, mLineHeight) +
                    getPaddingBottom() + getPaddingTop() +
                    mStrokeWidth;
        }

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.DKGRAY);

        int left = getPaddingLeft() + mStrokeWidth / 2;
        int top = getPaddingTop() + mStrokeWidth / 2;

        mTextPaint.setStyle(Paint.Style.STROKE);
        //先画线
        drawLine(canvas, mStepCount, left, top, mLineColor);
        //再画圆, 圆就会在线的上面
        drawCircle(canvas, mStepCount, left, top, mCircleColor);

        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //先画线
        drawLine(canvas, mCurStepCount, left, top, mLineColor);
        //再画圆, 圆就会在线的上面
        drawCircle(canvas, mCurStepCount, left, top, mCircleColor);

//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mCurStepCount++;
//                if (mCurStepCount > 4) {
//                    mCurStepCount = 0;
//                }
//                postInvalidate();
//            }
//        }, 1500);
    }

    private void drawCircle(Canvas canvas, int count, int left, int top, int color) {
        for (int i = 0; i < count; i++) {
            int circleLeft = left + i * (2 * mCircleRadius + mLineWidth);
            mTextPaint.setColor(color);
            canvas.drawCircle(circleLeft + mCircleRadius, top + mCircleRadius, mCircleRadius, mTextPaint);
        }
    }

    private void drawLine(Canvas canvas, int count, int left, int top, int color) {
        for (int i = 0; i < count; i++) {
            int lineLeft = left + i * (2 * mCircleRadius) + (i - 1) * (mLineWidth);
            mLineDrawRect.set(lineLeft, top + mCircleRadius - mLineHeight / 2, lineLeft + mLineWidth, top + mCircleRadius + mLineHeight / 2);
            mTextPaint.setColor(color);
            canvas.drawRect(mLineDrawRect, mTextPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = MotionEventCompat.getActionMasked(event);
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float eventX = event.getX();

                int left = getPaddingLeft() + mStrokeWidth / 2;
                int curStepCount = mCurStepCount;
                for (int i = 0; i < mStepCount; i++) {
                    int circleLeft = left + i * (2 * mCircleRadius + mLineWidth);
                    int circleRight = circleLeft + 2 * mCircleRadius;

                    if (eventX >= circleLeft && eventX <= circleRight) {
                        mCurStepCount = i + 1;
                        postInvalidate();
                        if (mOnStepChangeListener != null) {
                            mOnStepChangeListener.onStepChange(curStepCount, mCurStepCount);
                        }
                        L.e("call: onTouchEvent([event])-> F:" + curStepCount + " T:" + mCurStepCount);
                        break;
                    }
                }
                break;
        }
        return true;
    }

    public void setOnStepChangeListener(OnStepChangeListener onStepChangeListener) {
        mOnStepChangeListener = onStepChangeListener;
    }

    public interface OnStepChangeListener {
        void onStepChange(int from, int to);
    }

}
