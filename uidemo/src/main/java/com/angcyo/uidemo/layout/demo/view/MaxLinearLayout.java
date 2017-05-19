package com.angcyo.uidemo.layout.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.angcyo.library.utils.L;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/26 14:55
 * 修改人员：Robi
 * 修改时间：2017/04/26 14:55
 * 修改备注：
 * Version: 1.0.0
 */
public class MaxLinearLayout extends LinearLayout {

    private boolean drawMask;
    private Paint mPaint;
    private float mDensity;

    public MaxLinearLayout(Context context) {
        super(context);
    }

    public MaxLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View secondView = getChildAt(1);
        View firstView = getChildAt(0);
        measureChild(secondView,
                MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.AT_MOST));

        int secondWidth = secondView.getMeasuredWidth();//第二个布局需要的宽度
        int secondHeight = secondView.getMeasuredHeight();//第二个布局需要的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (firstView.getMeasuredWidth() > getMeasuredWidth() - secondWidth) {
            measureChild(firstView,
                    MeasureSpec.makeMeasureSpec(getMeasuredWidth() - secondWidth, MeasureSpec.EXACTLY),
                    heightMeasureSpec);

            measureChild(secondView,
                    MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.AT_MOST));
        }

        setMeasuredDimension(getMeasuredWidth(), Math.max(firstView.getMeasuredHeight(), secondHeight)
                + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        e("call: onLayout([changed, l, t, r, b])-> ");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //setWillNotDraw(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        e("父View call: onDraw([canvas])-> ");
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        e("父View call: dispatchDraw([canvas])-> ");
        super.dispatchDraw(canvas);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        e("父View call: drawChild([canvas, child, drawingTime])-> ");
        return super.drawChild(canvas, child, drawingTime);
    }

    @Override
    public void draw(Canvas canvas) {
        e("父View call: draw([canvas])-> ");
        super.draw(canvas);

        if (drawMask) {
            int height = getMeasuredHeight();
            LinearGradient linearGradient = new LinearGradient(0, height, 0,
                    height - 74 * mDensity,
                    new int[]{Color.BLACK, Color.TRANSPARENT /*Color.parseColor("#40000000")*/},
                    null, Shader.TileMode.CLAMP);
            mPaint.setShader(linearGradient);
            canvas.drawPaint(mPaint);
        }
    }

    /**
     * 是否绘制蒙层
     */
    public void setDrawMask(boolean drawMask) {
        this.drawMask = drawMask;
        setWillNotDraw(drawMask);
        if (drawMask) {
            ensurePaint();
            postInvalidate();
        }
    }

    private void ensurePaint() {
        if (mPaint == null) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setFlags(Paint.FILTER_BITMAP_FLAG);
            mDensity = getResources().getDisplayMetrics().density;
        }
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        e("父View call: onDrawForeground([canvas])-> ");
        super.onDrawForeground(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            setDrawMask(true);
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void e(String log) {
        if (!isInEditMode()) {
            L.e(log);
        }
    }
}
