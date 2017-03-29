package com.angcyo.uiview.rsen;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.angcyo.uiview.resources.ResUtil;

/**
 * 只有宽度和高度的空视图
 */
public class PlaceholderView extends View implements RefreshLayout.OnTopViewMoveListener,
        RefreshLayout.OnBottomViewMoveListener {

    private int height;//px
    private int width;//px

    public PlaceholderView(Context context) {
        this(context, null);
    }

    public PlaceholderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    protected void initView() {
        height = (int) ResUtil.dpToPx(getResources(), 30);
        width = (int) ResUtil.dpToPx(getResources(), 1);
        setWillNotDraw(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    public PlaceholderView setHeight(int height) {
        this.height = height;
        return this;
    }

    public PlaceholderView setWidth(int width) {
        this.width = width;
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
    }

    @Override
    public void onTopMoveTo(View view, int top, int maxHeight, @RefreshLayout.State int state) {

    }

    @Override
    public void onBottomMoveTo(View view, int bottom, int maxHeight, @RefreshLayout.State int state) {

    }
}