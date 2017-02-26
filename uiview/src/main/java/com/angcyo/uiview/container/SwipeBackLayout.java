package com.angcyo.uiview.container;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 支持滑动退出的父布局
 * Created by angcyo on 2016-12-18.
 */

public abstract class SwipeBackLayout extends FrameLayout {

    private ViewDragHelper mDragHelper;
    private OnPanelSlideListener mListener;

    private int mScreenWidth;
    private int mScreenHeight;

    /**
     * 正在滑动的view
     */
    private View mTargetView;

    /**
     * 阴影的绘制区域
     */
    private Rect mDimRect;

    private int dimWidth;//阴影的宽度

    private Paint mPaint;

    private boolean enableSwipeBack = true;
    private boolean mIsLocked;
    private boolean mIsLeftEdge;
    private float mRawDownX;
    /**
     * The drag helper callback interface for the Left position
     */
    private ViewDragHelper.Callback mLeftCallback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (!enableSwipeBack) {
                return false;
            }
            mIsLeftEdge = mDragHelper.isEdgeTouched(ViewDragHelper.EDGE_LEFT, pointerId);
            mTargetView = child;
            if (mIsLeftEdge || isForceIntercept()) {
                return canTryCaptureView(child);
            }
            return false;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return clamp(left, 0, mScreenWidth);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mScreenWidth;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            int left = releasedChild.getLeft();
            int settleLeft = 0;
            int leftThreshold = (int) (getMeasuredWidth() * 0.2f);//当滑动的距离达到1/5时, 判断为滑动退出
            boolean isVerticalSwiping = Math.abs(yvel) > 5f;//垂直滑动的距离大于5

            if (xvel > 0) {
                //快速滑动的时候, 滑动的速度大于 5, 并且垂直滑动的速度小于 5, 也视为滑动删除
                if (Math.abs(xvel) > 5f && !isVerticalSwiping) {
                    settleLeft = mScreenWidth;
                } else if (left > leftThreshold) {
                    settleLeft = mScreenWidth;
                }

            } else if (xvel == 0) {
                if (left > leftThreshold) {
                    settleLeft = mScreenWidth;
                }
            }

            mDragHelper.settleCapturedViewAt(settleLeft, releasedChild.getTop());
            postInvalidateOnAnimation();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            float percent = 1f - ((float) left / (float) mScreenWidth);
            onSlideChange(percent);
            if (mListener != null) mListener.onSlideChange(percent);
            postInvalidateOnAnimation();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (mListener != null) mListener.onStateChanged(state);
            switch (state) {
                case ViewDragHelper.STATE_IDLE:
                    //滚动结束
                    onStateIdle();
                    if (mTargetView.getLeft() == 0) {
                        // State Open
                        onRequestOpened();
                        if (mListener != null) mListener.onRequestOpened();
                    } else {
                        // State Closed
                        onRequestClose();
                        if (mListener != null) mListener.onRequestClose();
                    }
                    mTargetView = null;
                    break;
                case ViewDragHelper.STATE_DRAGGING:
                    //开始滚动
                    onStateDragging();
                    break;
                case ViewDragHelper.STATE_SETTLING:
                    //滑行中...
                    break;
            }
        }

    };


    public SwipeBackLayout(Context context) {
        super(context);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 限制 value 在 min 和 max 之间
     */
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * 是否激活滑动删除
     */
    public void setEnableSwipeBack(boolean enableSwipeBack) {
        this.enableSwipeBack = enableSwipeBack;
    }

    /**
     * @return true 表示可以抓起 child
     */
    protected abstract boolean canTryCaptureView(View child);

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setWillNotDraw(false);
        mDragHelper = ViewDragHelper.create(this, 0.5f, mLeftCallback);
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);//设置只支持左边缘滑动返回.
        mDimRect = new Rect();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dimWidth = (int) (20 * getResources().getDisplayMetrics().density);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mTargetView != null && mTargetView.getLeft() != getMeasuredWidth()) {
            mDimRect.set(mTargetView.getLeft() - dimWidth, 0, mTargetView.getLeft(), getMeasuredHeight());
            mPaint.setAlpha((int) (255 * (1 - (mTargetView.getLeft() * 1f / getMeasuredWidth()))));
            mPaint.setShader(new LinearGradient(mDimRect.left, 0, mDimRect.right, 0,
                    new int[]{Color.TRANSPARENT, Color.parseColor("#40000000")}, null, Shader.TileMode.CLAMP));
            canvas.drawRect(mDimRect, mPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenWidth = w;
        mScreenHeight = h;
    }

    public void setOnPanelSlideListener(OnPanelSlideListener listener) {
        mListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean interceptForDrag;

        if (mIsLocked) {
            return false;
        }

        //canDragFromEdge(ev);

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mRawDownX = ev.getRawX();
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            mRawDownX = -1;
        }

        // Fix for pull request #13 and issue #12
        try {
            interceptForDrag = mDragHelper.shouldInterceptTouchEvent(ev);
        } catch (Exception e) {
            interceptForDrag = false;
        }

        return interceptForDrag && !mIsLocked;
    }

    boolean isForceIntercept() {
//        if (Build.USER.contains("nubia") && mRawDownX > 0 && mRawDownX <= 100f) {
//            return true;
//        }
        float density = getResources().getDisplayMetrics().density;
        if (mRawDownX > 0 && mRawDownX <= 50f * density) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsLocked) {
            return false;
        }

        try {
            mDragHelper.processTouchEvent(event);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    /**
     * Lock this sliding panel to ignore touch inputs.
     */
    public void lock() {
        mDragHelper.abort();
        mIsLocked = true;
    }

    /**
     * Unlock this sliding panel to listen to touch inputs.
     */
    public void unlock() {
        mDragHelper.abort();
        mIsLocked = false;
    }

    private boolean canDragFromEdge(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        return x < 0.18f * getWidth();
    }

    /**
     * 需要返回了
     */
    protected void onRequestClose() {

    }


    /**
     * 需要打开
     */
    protected void onRequestOpened() {

    }

    /**
     * @param percent 已经滑动的距离比例 (1-0取值)
     */
    protected void onSlideChange(float percent) {

    }

    /**
     * 开始拖拽的时候回调
     */
    protected void onStateDragging() {

    }

    /**
     * 结束拖拽的时候回调
     */
    protected void onStateIdle() {

    }

    public interface OnPanelSlideListener {
        void onStateChanged(int state);

        void onRequestClose();

        void onRequestOpened();

        void onSlideChange(float percent);
    }
}
