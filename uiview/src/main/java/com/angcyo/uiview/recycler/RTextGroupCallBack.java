package com.angcyo.uiview.recycler;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

/**
 * Created by angcyo on 2017-01-15.
 */

public abstract class RTextGroupCallBack implements RGroupItemDecoration.GroupCallBack {

    RecyclerView mRecyclerView;
    TextPaint paint;
    /**
     * 背景绘制范围
     */
    private RectF mRectF;
    /**
     * 文本测量宽高
     */
    private Rect mRect;
    private int mBackgroundColor;

    public RTextGroupCallBack(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(mRecyclerView.getResources().getDisplayMetrics().scaledDensity * 20);
        mRectF = new RectF();
        mRect = new Rect();
        mBackgroundColor = Color.parseColor("#969696");
    }

    private boolean isHorizontal() {
        return ((LinearLayoutManager) mRecyclerView.getLayoutManager()).getOrientation() == LinearLayoutManager.HORIZONTAL;
    }

    private int dp2px(int dp) {
        return (int) (mRecyclerView.getResources().getDisplayMetrics().density) * dp;
    }


    @Override
    public void onGroupDraw(Canvas canvas, View view, int position) {
        paint.setColor(mBackgroundColor);

        if (isHorizontal()) {
            mRectF.set(view.getLeft() - getGroupHeight(), view.getTop(), view.getLeft(), view.getBottom());
        } else {
            mRectF.set(view.getLeft(), view.getTop() - getGroupHeight(), view.getRight(), view.getTop());
        }

        canvas.drawRoundRect(mRectF, dp2px(2), dp2px(2), paint);
        paint.setColor(Color.WHITE);

        final String letter = getGroupText(position);
        paint.getTextBounds(letter, 0, letter.length(), mRect);

        if (isHorizontal()) {
            canvas.drawText(letter, view.getLeft() - getGroupHeight() / 2 - mRect.width() / 2, view.getBottom() - dp2px(10), paint);
        } else {
            canvas.drawText(letter, view.getLeft() + dp2px(10), view.getTop() - (getGroupHeight() - mRect.height()) / 2, paint);
        }
    }

    @Override
    public void onGroupOverDraw(Canvas canvas, View view, int position, int offset) {
        paint.setColor(mBackgroundColor);

        if (isHorizontal()) {
            mRectF.set(-offset, view.getTop(), getGroupHeight() - offset, view.getBottom());
        } else {
            mRectF.set(view.getLeft(), -offset, view.getRight(), getGroupHeight() - offset);
        }

        canvas.drawRoundRect(mRectF, dp2px(2), dp2px(2), paint);
        paint.setColor(Color.WHITE);

        final String letter = getGroupText(position);
        paint.getTextBounds(letter, 0, letter.length(), mRect);

        if (isHorizontal()) {
            canvas.drawText(letter, (getGroupHeight() - mRect.width()) / 2 - offset, view.getBottom() - dp2px(10), paint);
        } else {
            canvas.drawText(letter, view.getLeft() + dp2px(10), (getGroupHeight() + mRect.height()) / 2 - offset, paint);
        }
    }
}
