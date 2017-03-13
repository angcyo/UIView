package com.angcyo.uiview.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.View;

import com.angcyo.uiview.R;
import com.angcyo.uiview.RApplication;

/**
 * 可以设置top分割线, 和中间分割线样式的{@link Item}
 * Created by angcyo on 2017-03-12.
 */

public abstract class SingleItem implements Item {

    /**
     * 左边绘制距离
     */
    int leftOffset = 0;
    /**
     * 上边留出距离
     */
    int topOffset = 0;

    public SingleItem() {

    }

    public SingleItem(Type type) {
        switch (type) {
            case TOP:
                this.topOffset = RApplication.getApp().getResources().getDimensionPixelSize(R.dimen.base_xhdpi);
                break;
            case LINE:
            case TOP_LINE:
                this.leftOffset = RApplication.getApp().getResources().getDimensionPixelSize(R.dimen.base_xhdpi);
                this.topOffset = RApplication.getApp().getResources().getDimensionPixelSize(R.dimen.base_line);
                break;
        }
    }

    public SingleItem(Context context) {
        this.leftOffset = context.getResources().getDimensionPixelSize(R.dimen.base_xhdpi);
        this.topOffset = context.getResources().getDimensionPixelSize(R.dimen.base_line);
    }

    public SingleItem(int topOffset) {
        this.topOffset = topOffset;
    }

    public SingleItem(int leftOffset, int topOffset) {
        this.leftOffset = leftOffset;
        this.topOffset = topOffset;
    }

    @Override
    public void setItemOffsets(Rect rect) {
        rect.top = topOffset;
        //rect.left = leftOffset;
    }

    @Override
    public void draw(Canvas canvas, TextPaint paint, View itemView, Rect offsetRect, int itemCount, int position) {
        paint.setColor(Color.WHITE);
        offsetRect.set(itemView.getLeft(), itemView.getTop() - offsetRect.top,
                itemView.getLeft() + leftOffset, itemView.getTop());
        canvas.drawRect(offsetRect, paint);
    }

    public enum Type {
        TOP, LINE, TOP_LINE
    }
}
