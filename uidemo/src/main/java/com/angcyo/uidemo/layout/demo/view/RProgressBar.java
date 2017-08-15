package com.angcyo.uidemo.layout.demo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.angcyo.uiview.kotlin.ViewExKt;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/08/09 15:28
 * 修改人员：Robi
 * 修改时间：2017/08/09 15:28
 * 修改备注：
 * Version: 1.0.0
 */
public class RProgressBar extends View {

    /**
     * 当前的进度 (0-100)
     */
    int progress = 80;

    /**
     * 进度矩形
     */
    RectF progressRectF = new RectF();
    TextPaint mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    ValueAnimator mAnimator;
    private int progressBgColor = Color.parseColor("#00D8DD");
    private int progressColor = Color.parseColor("#00ADB1");

    public RProgressBar(Context context) {
        super(context);
    }

    public RProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 偷懒, 这里就不写了.  xml里面估计写死吧
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 进度圆角半径, 当超过一定值时, 就是一个圆.....
     */
    private float getRoundRadius() {
        return 25 * ViewExKt.getDensity(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(progressBgColor);
        progressRectF.set(0 - getRoundRadius(), 0, getMeasuredWidth() * (progress / 100f), getMeasuredHeight());
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(progressColor);
        canvas.drawRoundRect(progressRectF, getRoundRadius(), getRoundRadius(), mPaint);

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(20 * getResources().getDisplayMetrics().scaledDensity);
        //绘制左文本
        String leftString = "A: " + getProgressText(progress);
        canvas.drawText(leftString, 10 * getResources().getDisplayMetrics().density, getDrawCenterTextCy(), mPaint);

        //绘制右文本
        String rightString = getProgressText(100 - progress) + " :B";
        canvas.drawText(rightString, getMeasuredWidth() - mPaint.measureText(rightString) - 10 * getResources().getDisplayMetrics().density, getDrawCenterTextCy(), mPaint);
    }

    /**
     * 返回进度对应的文本信息
     */
    private String getProgressText(int progress) {
        return progress + "%";
    }

    private float getDrawCenterTextCy() {
        int rawHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        //return getPaddingTop() + rawHeight / 2 - mPaint.descent() / 2 - mPaint.ascent() / 2;

        return getPaddingTop() + rawHeight / 2 + (mPaint.descent() - mPaint.ascent()) / 2 - mPaint.descent();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void animToProgress(final int progress) {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
        RProgressBar.this.progress = 0;
        postInvalidateOnAnimation();
        mAnimator = ValueAnimator.ofInt(0, progress);
        mAnimator.setDuration(1000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                RProgressBar.this.progress = (int) animation.getAnimatedValue();
                postInvalidateOnAnimation();
            }
        });
        mAnimator.start();
    }
}
