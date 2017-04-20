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

    protected final Rect mDrawRect = new Rect();
    /**
     * 左边绘制距离
     */
    int leftOffset = 0;
    /**
     * 上边留出距离
     */
    int topOffset = 0;
    int lineColor = -1;
    Type mType;

    public SingleItem() {

    }

    public SingleItem(Type type) {
        mType = type;
        switch (mType) {
            case TOP:
                this.topOffset = RApplication.getApp().getResources().getDimensionPixelSize(R.dimen.base_xhdpi);
                break;
            case LINE:
                this.topOffset = RApplication.getApp().getResources().getDimensionPixelSize(R.dimen.base_line);
                break;
            case TOP_LINE:
                this.leftOffset = RApplication.getApp().getResources().getDimensionPixelSize(R.dimen.base_xhdpi);
                this.topOffset = RApplication.getApp().getResources().getDimensionPixelSize(R.dimen.base_line);
                break;
        }
    }

    public SingleItem(Type type, int lineColor) {
        this(type);
        this.lineColor = lineColor;
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
    public void setItemOffsets2(Rect rect, int edge) {
        setItemOffsets(rect);
    }

    @Override
    public void draw(Canvas canvas, TextPaint paint, View itemView, Rect offsetRect, int itemCount, int position) {
        if (mType == Type.LINE && lineColor != -1) {
            paint.setColor(lineColor);
            mDrawRect.set(itemView.getLeft(), itemView.getTop() - offsetRect.top, itemView.getRight(), itemView.getTop());
            canvas.drawRect(mDrawRect, paint);
        } else {
            paint.setColor(Color.WHITE);
            mDrawRect.set(itemView.getLeft(), itemView.getTop() - offsetRect.top,
                    itemView.getLeft() + leftOffset, itemView.getTop());
            canvas.drawRect(mDrawRect, paint);
        }
    }

    public enum Type {
        /**
         * 距离很大的Line
         */
        TOP,
        /**
         * Line
         */
        LINE,
        /**
         * 左边偏移TOP距离的Line
         */
        TOP_LINE
    }
}
