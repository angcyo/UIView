package com.angcyo.uidemo.layout.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/20 16:18
 * 修改人员：Robi
 * 修改时间：2017/04/20 16:18
 * 修改备注：
 * Version: 1.0.0
 */
public class QQPathCircleView extends View {
    public QQPathCircleView(Context context) {
        super(context);
    }

    public QQPathCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.LTGRAY);

        float density = getResources().getDisplayMetrics().density;
        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();

        int cx = viewWidth / 2;
        int cy = viewHeight / 2;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(1 * density);
        paint.setStyle(Paint.Style.STROKE);

        //绘制矩形
        canvas.save();
        int width = (int) (viewHeight - 10 * density);
        int r = width / 2;
        Rect rect = new Rect(cx - r, cy - r, cx + r, cy + r);
        canvas.drawRect(rect, paint);
        canvas.restore();

        //绘制内圆
        canvas.save();
        canvas.drawCircle(cx, cy, r, paint);
        canvas.restore();

        //绘制弧形
        canvas.save();
        canvas.drawArc(rect.left, rect.top, rect.left + 2 * rect.width(), rect.top + 2 * rect.height(), -180, 90, false, paint);
        canvas.drawArc(rect.left - rect.width(), rect.top - rect.height(), rect.right, rect.bottom, 0, 90, false, paint);
        canvas.restore();

//        //偏移内圆
//        canvas.save();
//        paint.setColor(Color.CYAN);
//        canvas.drawCircle(cx, cy, r / 2, paint);
//        //canvas.drawCircle(cx - rect.width() / 4, cy - rect.width() / 4, r, paint);
//        canvas.restore();

        int layer = canvas.saveLayer(rect.left, rect.top, rect.right, rect.bottom, paint, Canvas.ALL_SAVE_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(cx, cy, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawCircle(rect.right, rect.bottom, rect.width(), paint);
        canvas.restoreToCount(layer);

        paint.setXfermode(null);
        layer = canvas.saveLayer(rect.left, rect.top, rect.right, rect.bottom, paint, Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(cx, cy, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawCircle(rect.left, rect.top, rect.width(), paint);
        canvas.restoreToCount(layer);

    }
}
