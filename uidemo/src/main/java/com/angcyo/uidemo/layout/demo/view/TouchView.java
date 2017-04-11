package com.angcyo.uidemo.layout.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.angcyo.library.utils.L;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/11 16:52
 * 修改人员：Robi
 * 修改时间：2017/04/11 16:52
 * 修改备注：
 * Version: 1.0.0
 */
public class TouchView extends View {

    RTextPaint mRTextPaint;
    String string1, string2;

    public TouchView(Context context) {
        this(context, null);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mRTextPaint = new RTextPaint();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        L.e("call: dispatchTouchEvent([event])-> " + " x:" + event.getX() + " y:" + event.getY());
        string1 = "dispatchTouchEvent-> " + event.getAction() + " x:" + event.getX() + " y:" + event.getY();
        boolean touchEvent = super.dispatchTouchEvent(event);
        postInvalidate();
        return touchEvent;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.e("call: onTouchEvent([event])-> " + " x:" + event.getX() + " y:" + event.getY());
        string2 = "onTouchEvent-> " + event.getAction() + " x:" + event.getX() + " y:" + event.getY();
        boolean touchEvent = super.onTouchEvent(event);
        postInvalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRTextPaint.drawText(canvas, string1, 0, 0);
        mRTextPaint.drawText(canvas, string2, 0, 100);
    }
}
