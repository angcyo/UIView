package com.angcyo.uiview.design;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.angcyo.library.utils.L;

/**
 * Created by angcyo on 2017-03-15.
 */

public class StickLayout extends LinearLayout {
    public StickLayout(Context context) {
        super(context);
    }

    public StickLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        L.e("onMeasure() -> ");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        L.e("onLayout() -> " + changed + " l:" + l + " t:" + t + " r:" + r + " b:" + b);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        L.e("onAttachedToWindow() -> ");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        L.e("onDetachedFromWindow() -> ");
    }
}
