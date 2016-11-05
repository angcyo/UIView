package com.angcyo.uiview.container;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import com.angcyo.library.utils.L;

/**
 * Created by angcyo on 2016-11-05.
 */

public class UIContainer_back extends FrameLayout {
    private static final String TAG = "UIContainer";

    protected static int mBackgroundColor = Color.WHITE;

    public UIContainer_back(Context context) {
        super(context);
        L.i(TAG, "UIContainer: ");
        post(new Runnable() {
            @Override
            public void run() {
                L.e("on post run in UIContainer");
            }
        });
        initContainer(context);
    }

    public UIContainer_back(Context context, AttributeSet attrs) {
        super(context, attrs);
        L.i(TAG, "UIContainer: ");
    }

    public UIContainer_back(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        L.i(TAG, "UIContainer: ");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UIContainer_back(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        L.i(TAG, "UIContainer: ");
    }

    private void initContainer(Context context) {
        L.i(TAG, "initContainer: ");
        setWillNotDraw(false);
        setBackgroundColor(mBackgroundColor);

        post(new Runnable() {
            @Override
            public void run() {
                L.e("on post run in initContainer");
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        L.i(TAG, "onMeasure: " + widthMeasureSpec + " " + heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        L.i(TAG, "onLayout: " + changed + " " + left + " " + top + " " + right + " " + bottom);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void onViewAdded(View child) {
        L.i(TAG, "onViewAdded: " + child);
        super.onViewAdded(child);
    }

    @Override
    public void onViewRemoved(View child) {
        L.i(TAG, "onViewRemoved: " + child);
        super.onViewRemoved(child);
    }

    @Override
    protected void onAttachedToWindow() {
        L.i(TAG, "onAttachedToWindow: ");
        super.onAttachedToWindow();

        post(new Runnable() {
            @Override
            public void run() {
                L.e("on post run");
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        L.i(TAG, "onDetachedFromWindow: ");
        super.onDetachedFromWindow();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        L.i(TAG, "onCreateDrawableState: " + extraSpace);
        return super.onCreateDrawableState(extraSpace);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        L.i(TAG, "onFocusChanged: " + gainFocus + " " + direction);
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        L.i(TAG, "onApplyWindowInsets: " + insets);
        return super.onApplyWindowInsets(insets);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        L.i(TAG, "onWindowFocusChanged: " + hasWindowFocus);
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        L.i(TAG, "onVisibilityChanged: " + changedView + " " + visibility);
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    protected void onDisplayHint(int hint) {
        L.i(TAG, "onDisplayHint: " + hint);
        super.onDisplayHint(hint);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        L.i(TAG, "onWindowVisibilityChanged: " + visibility);
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        L.i(TAG, "onVisibilityAggregated: " + isVisible);
        super.onVisibilityAggregated(isVisible);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        L.i(TAG, "onConfigurationChanged: " + newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        L.i(TAG, "onScrollChanged: " + l + " " + t + " " + oldl + " " + oldt);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        L.i(TAG, "onSizeChanged: " + w + " " + h + " " + oldw + " " + oldh);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        L.i(TAG, "onDraw: ");
        super.onDraw(canvas);
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        L.i(TAG, "onScreenStateChanged: " + screenState);
        super.onScreenStateChanged(screenState);
    }

    @Override
    public void onCancelPendingInputEvents() {
        L.i(TAG, "onCancelPendingInputEvents: ");
        super.onCancelPendingInputEvents();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        L.i(TAG, "onSaveInstanceState: ");
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        L.i(TAG, "onRestoreInstanceState: ");
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onFinishInflate() {
        L.i(TAG, "onFinishInflate: ");
        super.onFinishInflate();
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        L.i(TAG, "onDrawForeground: ");
        super.onDrawForeground(canvas);
    }

    @Override
    protected void onAnimationStart() {
        L.i(TAG, "onAnimationStart: ");
        super.onAnimationStart();
    }

    @Override
    protected void onAnimationEnd() {
        L.i(TAG, "onAnimationEnd: ");
        super.onAnimationEnd();
    }

    @Override
    protected boolean onSetAlpha(int alpha) {
        L.i(TAG, "onSetAlpha: " + alpha);
        return super.onSetAlpha(alpha);
    }

    @Override
    public void onWindowSystemUiVisibilityChanged(int visible) {
        L.i(TAG, "onWindowSystemUiVisibilityChanged: " + visible);
        super.onWindowSystemUiVisibilityChanged(visible);
    }
}
