package com.angcyo.uidemo.layout.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/06/01 09:13
 * 修改人员：Robi
 * 修改时间：2017/06/01 09:13
 * 修改备注：
 * Version: 1.0.0
 */
public class MyLrcView extends AppCompatTextView {
    Paint mPaint;

    public MyLrcView(Context context) {
        super(context);
        initView();
    }

    public MyLrcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyLrcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.save();
//        mPaint.setTextSize(getTextSize());
//        mPaint.setColor(getCurrentTextColor());
//
//        float textDrawY = getMeasuredHeight() - mPaint.descent();
//        canvas.drawText(getText(), 0, getText().length(), getPaddingLeft(), textDrawY, mPaint);
//
//        Rect rect = new Rect();
//        rect.set(0, 0, getMeasuredWidth() / 2, getMeasuredHeight());
//        mPaint.setColor(Color.RED);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
//
//        canvas.drawRect(rect, mPaint);
//        canvas.restore();
        mPaint.setTextSize(getTextSize());
        canvas.save();
        Rect leftRect = new Rect(0, 0, getMeasuredWidth() / 2, getMeasuredHeight());
        canvas.clipRect(leftRect);
        mPaint.setColor(Color.RED);
        float textDrawY = getMeasuredHeight() - mPaint.descent();
        canvas.drawText(getText(), 0, getText().length(), getPaddingLeft(), textDrawY, mPaint);
        canvas.restore();

        canvas.save();
        Rect rightRect = new Rect(leftRect.right, 0, getMeasuredWidth(), getMeasuredHeight());
        mPaint.setColor(getCurrentTextColor());
        canvas.clipRect(rightRect);
        canvas.drawText(getText(), 0, getText().length(), getPaddingLeft(), textDrawY, mPaint);
        canvas.restore();
    }
}
