package com.angcyo.uiview.rsen;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.OverScroller;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：刷新控件
 * 创建人员：Robi
 * 创建时间：2016/12/05 17:30
 * 修改人员：Robi
 * 修改时间：2016/12/05 17:30
 * 修改备注：
 * Version: 1.0.0
 */
public class RefreshLayout extends ViewGroup {
    /**
     * 不支持刷新和上拉
     */
    public static final int NONE = -1;
    /**
     * 支持刷新,或者刷新中
     */
    public static final int TOP = 1;
    /**
     * 支持上拉,或者上拉中
     */
    public static final int BOTTOM = 2;
    /**
     * 支持刷新和上拉
     */
    public static final int BOTH = 3;
    /**
     * 刷新,上拉控件 正在移动中
     */
    public static final int MOVE = 4;
    /**
     * 刷新,上拉 完成
     */
    public static final int FINISH = 5;
    /**
     * 正常
     */
    public static final int NORMAL = 0;
    float downY, lastY;
    private View mTopView, mBottomView, mTargetView;
    private OverScroller mScroller;
    private int mTouchSlop;

    /**
     * 支持的滚动方向
     */
    @Direction
    private int mDirection = BOTH;

    /**
     * 当前刷新的状态
     */
    @State
    private int mCurState = NORMAL;

    /**
     * 手指未离屏
     */
    private boolean isTouchDown = false;

    private ArrayList<OnTopViewMoveListener> mTopViewMoveListeners = new ArrayList<>();
    private ArrayList<OnBottomViewMoveListener> mBottomViewMoveListeners = new ArrayList<>();
    private ArrayList<OnRefreshListener> mRefreshListeners = new ArrayList<>();

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (mTargetView != null) {
            mTargetView.measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY));
        }
        if (mTopView != null) {
            mTopView.measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST));
        }
        if (mBottomView != null) {
            mBottomView.measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mTargetView != null) {
            mTargetView.layout(0, 0, r, getMeasuredHeight());
        }
        if (mTopView != null) {
            mTopView.layout(0, -mTopView.getMeasuredHeight(), r, 0);
        }
        if (mBottomView != null) {
            mBottomView.layout(0, b, r, b + mBottomView.getMeasuredHeight());
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getChildCount() != 1) {
            throw new IllegalArgumentException("必须包含一个子View");
        }
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mTargetView = getChildAt(0);
        mScroller = new OverScroller(getContext(), new DecelerateInterpolator());
        initView();
    }

    protected void initView() {
        mTopView = new Button(getContext());
        mBottomView = new Button(getContext());

        ((TextView) mTopView).setText("下拉刷新");
        ((TextView) mBottomView).setText("上拉加载");

        addView(mTopView);
        addView(mBottomView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int currY = mScroller.getCurrY();
            if (currY == 0 && mCurState == FINISH) {
                mCurState = NORMAL;
            }
            scrollTo(mScroller.getCurrX(), currY);
            postInvalidate();

        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            downY = event.getY();
            lastY = downY;
        } else if (action == MotionEvent.ACTION_MOVE) {
            float y = event.getY();
            float dy = y - downY;
            if (Math.abs(dy) > mTouchSlop) {
                int scrollY = getScrollY();
                if (mCurState == TOP && dy < 0 && scrollY < 0) {
                    //如果已经处理加载状态, 通过滚动, View 隐藏, 使得内容全屏显示
                    scrollTo(0, (int) Math.min(0, (scrollY - dy)));
                    downY = event.getY();
                    return super.onInterceptTouchEvent(event);
                } else if (mCurState == BOTTOM && dy > 0 && scrollY > 0) {
                    scrollTo(0, (int) Math.max(0, scrollY - dy));
                    downY = event.getY();
                    return super.onInterceptTouchEvent(event);
                } else {
                    if (dy > 0 && canScrollDown() &&
                            !innerCanChildScrollVertically(mTargetView, -1)) {
                        return true;
                    } else if (dy < 0 && handleScrollUp() &&
                            !innerCanChildScrollVertically(mTargetView, 1)) {
                        return true;
                    }
                }
            }
        }

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            int count = event.getPointerCount();
            if (count == 1) {
                isTouchDown = false;
            }
            handleTouchUp();
        } else if (action == MotionEvent.ACTION_MOVE) {
            float y = event.getY();
            float dy = lastY - y;
            isTouchDown = true;
            if (Math.abs(dy) > mTouchSlop) {
                scrollBy(0, (int) (dy * (1 - 0.4 - Math.abs(getScrollY()) * 1.f / getMeasuredHeight())));
                lastY = y;
                if (mCurState == NORMAL) {
                    mCurState = MOVE;
                }
            }
        } else if (action == MotionEvent.ACTION_POINTER_DOWN) {
            //多个手指按下
            lastY = event.getY();
        }
        return true;
    }

    /**
     * 释放手指之后的处理
     */
    private void handleTouchUp() {
        int scrollY = getScrollY();
        int rawY = Math.abs(scrollY);

        if (scrollY < 0) {
            //处理刷新
            if (mTopView == null || mCurState == FINISH) {
                resetScroll();
                return;
            }

            int height = mTopView.getMeasuredHeight();
            if (rawY > height) {
                refreshTop();
            } else {
                resetScroll();
            }
        } else if (scrollY > 0) {
            //处理加载
            if (mBottomView == null || mCurState == FINISH) {
                resetScroll();
                return;
            }

            int height = mBottomView.getMeasuredHeight();
            if (rawY > height) {
                refreshBottom();
            } else {
                resetScroll();
            }
        }

    }

    /**
     * 设置支持刷新的方向
     */
    public void setRefreshDirection(@Direction int direction) {
        mDirection = direction;
    }

    /**
     * 设置当前刷新的状态
     */
    public void setRefreshState(@State int state) {
        if (mCurState == NORMAL) {
            if (state == TOP) {
                if (mDirection == TOP || mDirection == BOTH) {
                    refreshTop();
                }
            } else if (state == BOTTOM) {
                if (mDirection == BOTTOM || mDirection == BOTH) {
                    refreshBottom();
                }
            }
        } else {
            return;
        }
    }

    /**
     * 结束刷新
     */
    public void setRefreshEnd() {
        mCurState = FINISH;
        if (isTouchDown) {
            scrollTo(getScrollX(), getScrollY());
        } else {
            startScroll(0);
        }
    }

    private void refreshTop() {
        if (mTopView != null) {
            //设置正在刷新
            mCurState = TOP;
            startScroll(-mTopView.getMeasuredHeight());

            for (OnRefreshListener listener : mRefreshListeners) {
                listener.onRefresh(TOP);
            }
        }
    }

    private void refreshBottom() {
        if (mBottomView != null) {
            //设置正在上拉
            mCurState = BOTTOM;
            startScroll(mBottomView.getMeasuredHeight());

            for (OnRefreshListener listener : mRefreshListeners) {
                listener.onRefresh(BOTTOM);
            }
        }
    }

    /**
     * 恢复到默认的滚动状态
     */
    private void resetScroll() {
        if (mCurState != TOP && mCurState != BOTTOM) {
            mCurState = NORMAL;
        }
        startScroll(0);
    }

    private void startScroll(int to) {
        int scrollY = getScrollY();
        mScroller.startScroll(0, scrollY, 0, to - scrollY);
        postInvalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (mCurState == TOP) {
            y = Math.min(y, 0);
        } else if (mCurState == BOTTOM) {
            y = Math.max(y, 0);
        }

        super.scrollTo(x, y);

        int scrollY = getScrollY();
        int rawY = Math.abs(scrollY);

        if (scrollY < 0) {
            //刷新
            if (mTopView != null /*&& mCurState != TOP*/) {
                if (mTopView instanceof OnTopViewMoveListener
                        && !mTopViewMoveListeners.contains(mTopView)) {
                    ((OnTopViewMoveListener) mTopView).onTopMoveTo(this, rawY, mTopView.getMeasuredHeight(), mCurState);
                }
                for (OnTopViewMoveListener listener : mTopViewMoveListeners) {
                    listener.onTopMoveTo(mTopView, rawY, mTopView.getMeasuredHeight(), mCurState);
                }
            }
        } else if (scrollY > 0) {
            //加载
            if (mBottomView != null /*&& mCurState != BOTTOM*/) {
                if (mBottomView instanceof OnBottomViewMoveListener
                        && !mBottomViewMoveListeners.contains(mBottomView)) {
                    ((OnBottomViewMoveListener) mBottomView).onBottomMoveTo(this, rawY, mBottomView.getMeasuredHeight(), mCurState);
                }
                for (OnBottomViewMoveListener listener : mBottomViewMoveListeners) {
                    listener.onBottomMoveTo(mBottomView, rawY, mBottomView.getMeasuredHeight(), mCurState);
                }
            }
        }
    }

    public RefreshLayout addTopViewMoveListener(OnTopViewMoveListener listener) {
        mTopViewMoveListeners.add(listener);
        return this;
    }

    public RefreshLayout addBottomViewMoveListener(OnBottomViewMoveListener listener) {
        mBottomViewMoveListeners.add(listener);
        return this;
    }

    public RefreshLayout addRefreshListener(OnRefreshListener listener) {
        mRefreshListeners.add(listener);
        return this;
    }


    public RefreshLayout removeTopViewMoveListener(OnTopViewMoveListener listener) {
        mTopViewMoveListeners.remove(listener);
        return this;
    }

    public RefreshLayout removeBottomViewMoveListener(OnBottomViewMoveListener listener) {
        mBottomViewMoveListeners.remove(listener);
        return this;
    }

    public RefreshLayout removeRefreshListener(OnRefreshListener listener) {
        mRefreshListeners.remove(listener);
        return this;
    }

    /**
     * 是否拦截向下滚动, 影响下拉刷新的功能**
     *
     * @return true 激活下拉刷新功能
     */
    private boolean canScrollDown() {
        if (isEnabled() && mTopView != null &&
                (mDirection == TOP || mDirection == BOTH)) {
            if (mCurState == BOTTOM) {
                //如果当前正在上拉加载,则禁止刷新功能, 当然~~~你可以取消此限制
                return false;
            }
            return true;
        }

        return false;
    }

    /**
     * 是否拦截向上滚动, 影响上拉加载的功能
     *
     * @return true 激活上拉加载功能
     */
    private boolean handleScrollUp() {
        if (isEnabled() && mBottomView != null &&
                (mDirection == BOTTOM || mDirection == BOTH)) {
            if (mCurState == TOP) {
                //如果当前正在下拉刷新,则禁止上拉功能, 当然~~~你可以取消此限制
                return false;
            }
            return true;
        }

        return false;
    }

    /**
     * Child是否可以滚动
     *
     * @param direction 如果是大于0, 表示视图底部没有数据了, 即不能向上滚动了, 反之...
     */
    private boolean innerCanChildScrollVertically(View view, int direction) {
        if (view instanceof ViewGroup) {
            final ViewGroup vGroup = (ViewGroup) view;
            View child;
            boolean result;
            for (int i = 0; i < vGroup.getChildCount(); i++) {
                child = vGroup.getChildAt(i);
                if (child instanceof View) {
                    result = ViewCompat.canScrollVertically(child, direction);
                } else {
                    result = innerCanChildScrollVertically(child, direction);
                }

                if (result) {
                    return true;
                }
            }
        }

        return ViewCompat.canScrollVertically(view, direction);
    }

    /**
     * 支持的刷新方向
     */
    @IntDef({TOP, BOTTOM, BOTH, NONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
    }

    /**
     * 当前的刷新状态
     */
    @IntDef({TOP, BOTTOM, NORMAL, MOVE, FINISH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public interface OnTopViewMoveListener {
        /**
         * @param top       距离父View顶部的距离
         * @param maxHeight view的高度
         */
        void onTopMoveTo(View view, int top, int maxHeight, @State int state);
    }

    public interface OnBottomViewMoveListener {
        /**
         * @param bottom    距离父View底部的距离
         * @param maxHeight view的高度
         */
        void onBottomMoveTo(View view, int bottom, int maxHeight, @State int state);
    }

    /**
     * 刷新,上拉回调
     */
    public interface OnRefreshListener {
        void onRefresh(@Direction int direction);
    }

}
