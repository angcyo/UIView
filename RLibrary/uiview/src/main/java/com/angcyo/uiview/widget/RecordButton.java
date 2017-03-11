package com.angcyo.uiview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：录制视频的按钮
 * 创建人员：Robi
 * 创建时间：2017/03/07 16:36
 * 修改人员：Robi
 * 修改时间：2017/03/07 16:36
 * 修改备注：
 * Version: 1.0.0
 */
public class RecordButton extends AppCompatTextView implements Runnable {
    private static final String TAG = "Robi";

    Paint mPaint;
    /**
     * 外圆和内圆的距离 dp
     */
    int mCircleSpace = 5;

    /**
     * 外圆的宽度 dp
     */
    int mOutCircleRadius = 10;
    /**
     * 放大多少dp
     */
    int mFactor = 10;
    OnRecordListener mOnRecordListener;
    boolean isStart = false;
    private RectF mOutRectF;
    private int maxProgress = 100, currentProgress = 0, currentAngle = 0;
    private ValueAnimator mAnimator;

    public RecordButton(Context context) {
        super(context);
        initView();
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);

        float density = getResources().getDisplayMetrics().density;

        mCircleSpace *= density;
        mOutCircleRadius *= density;
        mFactor *= density;

        mOutRectF = new RectF();
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return true;
        }

        int action = event.getAction();
        //Log.e(TAG, "onTouchEvent: " + action);
        super.onTouchEvent(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isStart) {
                    break;
                }
                onRecordStart();
                //postDelayed(this, 1000);
                if (mOnRecordListener != null) {
                    mOnRecordListener.onRecordStart();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //stepProgress();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!isStart) {
                    break;
                }
                onRecordEnd();
                break;

        }
        return true;
    }

    private void onRecordStart() {
        isStart = true;
        currentProgress = 0;
        invalidate();
        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, 360);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentAngle = (int) animation.getAnimatedValue();
                    invalidate();
                    currentProgress = currentAngle * maxProgress / 360;
                    if (currentProgress >= maxProgress) {
                        onRecordEnd();
                    }
                }
            });
            mAnimator.setDuration(maxProgress * 1000);
            mAnimator.setInterpolator(new LinearInterpolator());
        }
        mAnimator.start();
    }

    private void onRecordEnd() {
        isStart = false;
        invalidate();
        removeCallbacks(this);
        if (mAnimator != null) {
            mAnimator.cancel();
        }
        if (mOnRecordListener != null) {
            mOnRecordListener.onRecordEnd(currentProgress);
        }
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    /**
     * 设置事件监听
     */
    public void setOnRecordListener(OnRecordListener onRecordListener) {
        mOnRecordListener = onRecordListener;
    }

    /**
     * 增加进度
     */
    public void stepProgress() {
        currentProgress++;
        invalidate();
        if (currentProgress >= maxProgress) {
            onRecordEnd();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.max(getMeasuredWidth(), getMeasuredHeight());
        //保证是正方形
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isStart) {
            drawStart(canvas);
        } else {
            drawNormal(canvas);
            super.onDraw(canvas);
        }
    }


    /**
     * 开始录制
     */
    private void drawStart(Canvas canvas) {
        float center;
        center = getMeasuredWidth() / 2;

        //绘制内圆
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(getCurrentTextColor());
        canvas.drawCircle(center, center, center - mCircleSpace - mOutCircleRadius, mPaint);

        //绘制外圆
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(mOutCircleRadius);

        int outR = mOutCircleRadius / 2;
        float left, top, right, bottom;
        left = outR;
        top = outR;
        right = getMeasuredWidth() - outR;
        bottom = getMeasuredWidth() - outR;
        mOutRectF.set(left, top, right, bottom);
        canvas.drawArc(mOutRectF, 0, 360, false, mPaint);

        mPaint.setColor(getCurrentTextColor());
        canvas.drawArc(mOutRectF, -90, getSweepAngle(), false, mPaint);
    }

    private int getSweepAngle() {
        //return (int) (360 * Math.min(1, currentProgress * 1f / maxProgress));
        return currentAngle;
    }

    /**
     * 正常情况下绘制.
     */
    private void drawNormal(Canvas canvas) {
        float center;
        center = getMeasuredWidth() / 2;

        //绘制内圆
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(center, center, center - mCircleSpace * 2 / 3 - mOutCircleRadius - mFactor, mPaint);

        //绘制外圆
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mOutCircleRadius * 2 / 3);

        int outR = mOutCircleRadius / 2;
        float left, top, right, bottom;
        left = outR;
        top = outR;
        right = getMeasuredWidth() - outR;
        bottom = getMeasuredWidth() - outR;
        mOutRectF.set(left + mFactor, top + mFactor, right - mFactor, bottom - mFactor);
        canvas.drawArc(mOutRectF, 0, 360, false, mPaint);
    }

    @Override
    public void run() {
        stepProgress();
        postDelayed(this, 1000);
    }

    public interface OnRecordListener {
        /**
         * 点击的时候, 开始录制
         */
        void onRecordStart();

        /**
         * 达到最大的时间, 或者录制完成后
         */
        void onRecordEnd(int progress);
    }
}
