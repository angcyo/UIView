package com.angcyo.uiview.design;

import android.content.Context;
import android.support.annotation.Px;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;
import android.widget.RelativeLayout;

import com.angcyo.uiview.recycler.RRecyclerView;
import com.angcyo.uiview.utils.Debug;

/**
 * Created by angcyo on 2017-03-15.
 */

public class StickLayout2 extends RelativeLayout {

    View mFloatView;
    int floatTopOffset = 0;
    int floatTop = 0;//
    float downY, downX, lastMoveX, lastMoveY, lastScrollY;
    StickLayout.CanScrollUpCallBack mScrollTarget;
    boolean inTopTouch = false, needHandle = true;
    boolean isFirst = true;
    boolean wantVertical = true;
    StickLayout.OnScrollListener mOnScrollListener;
    boolean lastOffset = false;//之前是否偏移了
    private OverScroller mOverScroller;
    private GestureDetectorCompat mGestureDetectorCompat;
    private int mTouchSlop, mTouchCheckSlop;
    private int maxScrollY, topHeight;
    private RRecyclerView.OnScrollEndListener mOnScrollEndListener;
    private boolean mIntercept;
    private float mInterceptDownY;
    private float mInterceptDownX;
    private boolean handleTouch = true;

    public StickLayout2(Context context) {
        this(context, null);
    }

    public StickLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        mOverScroller = new OverScroller(getContext());
        mGestureDetectorCompat = new GestureDetectorCompat(getContext(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, final float velocityY) {
                        //L.e("call: onFling([e1, e2, velocityX, velocityY])-> " + velocityX + "  " + velocityY);
                        if (Math.abs(velocityX) > Math.abs(velocityY)) {
                            return false;
                        }

                        if (isFloat() && velocityY > 0) {
                            //L.e("call: onFling return");
                            return false;
                        }
                        fling(velocityY);
                        final RecyclerView recyclerView = mScrollTarget.getRecyclerView();
                        final int velocityDecay = getChildAt(0).getMeasuredHeight() * 3;//速度衰减值
                        if (velocityY < -velocityDecay && recyclerView != null) {
                            final int fling = (int) -velocityY - velocityDecay;
                            //L.e("recyclerView fling..............." + fling);
                            recyclerView.post(new Runnable() {
                                @Override
                                public void run() {
                                    //L.e("call: run([])-> " + mOverScroller.getCurrVelocity());
                                    recyclerView.fling(0, fling);
                                }
                            });
                        }
                        return true;
                    }
                });
        mTouchSlop = 0;
        mTouchCheckSlop = 10;//ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    private void fling(float velocityY) {
        mOverScroller.fling(0, getScrollY(), 0, (int) -velocityY, 0, 0, 0, maxScrollY);
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        //L.e("call: scrollTo([x, y])-> " + mOverScroller.getCurrVelocity() + "       :" + mOverScroller.getCurrY());
        if (mOverScroller.computeScrollOffset()) {
            int currY = mOverScroller.getCurrY();
            scrollTo(0, currY);
            postInvalidate();
        }
    }

    @Override
    public void scrollTo(@Px int x, @Px int y) {
        int offset = Math.min(maxScrollY, Math.max(0, y));
        boolean layout = false;
        if (getScrollY() != offset) {
            layout = true;
        }
        super.scrollTo(0, offset);
        if (layout) {
            requestLayout();
        }
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollTo(offset);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        View topView = getChildAt(0);
        View scrollView = getChildAt(1);
        if (scrollView instanceof StickLayout.CanScrollUpCallBack) {
            mScrollTarget = (StickLayout.CanScrollUpCallBack) scrollView;
        } else {
            if (mScrollTarget == null) {
                mScrollTarget = new StickLayout.CanScrollUpCallBack() {
                    @Override
                    public boolean canChildScrollUp() {
                        return false;
                    }

                    @Override
                    public RecyclerView getRecyclerView() {
                        return null;
                    }
                };
            }
        }
        mFloatView = getChildAt(2);

        measureChild(topView, widthMeasureSpec, heightMeasureSpec);
        measureChild(mFloatView, widthMeasureSpec, heightMeasureSpec);
        measureChild(scrollView, widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(heightSize - mFloatView.getMeasuredHeight() - floatTopOffset, MeasureSpec.EXACTLY));

        floatTop = topView.getMeasuredHeight();
        maxScrollY = floatTop - floatTopOffset;
        topHeight = floatTop + mFloatView.getMeasuredHeight();

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //super.onLayout(changed, l, t, r, b);
        //L.w("onLayout() -> " + changed + " l:" + l + " t:" + t + " r:" + r + " b:" + b);
        View firstView = getChildAt(0);
        firstView.layout(0, 0, r, firstView.getMeasuredHeight());

        View lastView = getChildAt(1);
        lastView.layout(0, firstView.getMeasuredHeight() + mFloatView.getMeasuredHeight(), r,
                firstView.getMeasuredHeight() + mFloatView.getMeasuredHeight() + lastView.getMeasuredHeight());

        if (mFloatView != null) {
            int scrollY = getScrollY();
            if (isFloat()) {
                mFloatView.layout(mFloatView.getLeft(), scrollY + floatTopOffset, r,
                        scrollY + floatTopOffset + mFloatView.getMeasuredHeight());
            } else {
                mFloatView.layout(mFloatView.getLeft(), firstView.getMeasuredHeight(), r,
                        firstView.getMeasuredHeight() + mFloatView.getMeasuredHeight());
            }
        }

        initScrollTarget();
    }

    private void initScrollTarget() {
        if (mScrollTarget != null && mScrollTarget.getRecyclerView() instanceof RRecyclerView) {
            if (mOnScrollEndListener == null) {
                mOnScrollEndListener = new RRecyclerView.OnScrollEndListener() {
                    @Override
                    public void onScrollTopEnd(float currVelocity) {
//                        if (!(currVelocity > 0)) {
//                            //向下滑动产生的fling操作, 才处理
//                            fling(currVelocity);
//                        }
                        fling(currVelocity);
                    }
                };
            }
            ((RRecyclerView) mScrollTarget.getRecyclerView()).setOnScrollEndListener(mOnScrollEndListener);
        }
    }

    private boolean isFloat() {
        return getScrollY() >= (floatTop - floatTopOffset);
    }

    public void setFloatTopOffset(int floatTopOffset) {
        this.floatTopOffset = floatTopOffset;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        boolean intercept = this.mIntercept;
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                onTouchDown(ev);
//                mInterceptDownY = event.getY();
//                mInterceptDownX = event.getX();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (intercept) {
//                    float moveY = event.getY();
//                    float moveX = event.getX();
//                    float offsetY = mInterceptDownY - moveY;
//                    float offsetX = mInterceptDownX - moveX;
//
//                    event.offsetLocation(offsetX, 0);
//                }
//                break;
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                onTouchUp();
//                if (intercept) {
//                    float moveY = event.getY();
//                    float moveX = event.getX();
//                    float offsetY = mInterceptDownY - moveY;
//                    float offsetX = mInterceptDownX - moveX;
//
//                    event.offsetLocation(offsetX, offsetY);
//                }
//                break;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }


//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        handleTouchEvent(ev);
//        return super.onInterceptTouchEvent(ev);
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        boolean isDispatch = true;

        mOverScroller.abortAnimation();
        mGestureDetectorCompat.onTouchEvent(ev);

        if (handleTouch) {
            if (handleTouchEvent(ev)) {
                isDispatch = false;
            }
        }
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                onTouchUp();
                break;
        }
        if (isDispatch) {
            return super.dispatchTouchEvent(ev);
        }
        return true;
    }

    private boolean handleTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onTouchDown(event);

                Debug.log("downat:", event.getY() + "");
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                float moveX = event.getX();
                float offsetY = lastMoveY - moveY;
                float offsetX = lastMoveX - moveX;

                boolean isFirst = this.isFirst;
                this.isFirst = false;
//
//                //downY = moveY;
//                //downX = moveX;
                lastMoveX = moveX;
                lastMoveY = moveY;
//
//                boolean isCancel = false;

                float scrollOffset = getScrollY() - lastScrollY;

                if (isFirst) {
                    if (Math.abs(offsetX) > Math.abs(offsetY)) {
                        //第一次滑动的时候, 意图是横向滚动. 那么之后都不处理touch事件
                        handleTouch = false;
                        break;
                    }
                } else {
                    if (Math.abs(offsetX) > Math.abs(offsetY)) {
                        //event.setAction(MotionEvent.ACTION_DOWN);
                        //L.e("call: handleTouchEvent([event])-> ----------------------");
                        //event.setLocation(downX, moveY - scrollOffset);
                        //isCancel = true;
                        //break;
                    }
                }
//
//                if (offsetY != 0) {
                boolean offsetTo = isOffsetTo(offsetY);
//                    if (offsetTo) {
//                        L.e("call: handleTouchEvent([event])-> " + "Cancel");
//                        event.setAction(MotionEvent.ACTION_CANCEL);
//                        lastOffset = true;
//                    } else {
//                        if (lastOffset) {
//                            L.e("call: handleTouchEvent([event])-> " + "Down_Move");
//                            //event.setAction(MotionEvent.ACTION_CANCEL);
//                            //super.dispatchTouchEvent(event);
//                            isCancel = true;
//                            lastOffset = false;
//                        }
//                    }
//                }
//
//                if (isCancel) {
//                    event.setAction(MotionEvent.ACTION_DOWN);
//                    super.dispatchTouchEvent(event);
//                    event.setAction(MotionEvent.ACTION_MOVE);
//                }

                Debug.log("offset:", scrollOffset + "");
                if (offsetTo) {
                    // event.setAction(MotionEvent.ACTION_DOWN);
                    // event.setLocation(downX, downY);
//                    return true;
                    event.setLocation(downX, downY - scrollOffset);
                } else {
                    Debug.log("moveto:", moveY + " -> " + (moveY + scrollOffset));
                    event.setLocation(downX, moveY + scrollOffset);
                }


                break;
        }
        return false;
    }

    private void consumeTouchEvent(MotionEvent event) {
        //event.setAction(MotionEvent.ACTION_DOWN);
        event.setLocation(downX, downY);
    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
////        if (!inTopTouch) {
////            return super.dispatchTouchEvent(ev);
////        }
//        mOverScroller.abortAnimation();
//        mGestureDetectorCompat.onTouchEvent(ev);
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                onTouchDown(ev);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float moveY = event.getY();
//                float moveX = event.getX();
//                float offsetY = downY - moveY;
//                float offsetX = downX - moveX;
//
//                if (isFirst &&
//                        (Math.abs(offsetX) < mTouchCheckSlop || Math.abs(offsetY) < mTouchCheckSlop)) {
//                    return super.dispatchTouchEvent(ev);
//                }
//
//                downY = moveY;
//                downX = moveX;
//
//                boolean wantV;
//                if (Math.abs(offsetX) > Math.abs(offsetY)) {
//                    wantV = false;
//                } else {
//                    wantV = true;
//                }
//
//                boolean first = isFirst;
//                isFirst = false;
//
//                if (first) {
//                    wantVertical = wantV;
//
//                    if (inTopTouch) {
//                        scrollBy(0, (int) (offsetY));
//                    } else {
//                        if (wantVertical) {
//                            mIntercept = !isOffsetTo(offsetY);
//                        } else {
//                            mIntercept = false;
//                        }
//                    }
//
//                } else {
//                    if (inTopTouch) {
//                        scrollBy(0, (int) (offsetY));
//                    } else {
//                        if (wantVertical == wantV) {
//                            if (wantV) {
//                                mIntercept = !isOffsetTo(offsetY);
//                            } else {
//                                mIntercept = false;
//                            }
//                        } else {
//                            mIntercept = false;
//                            if (wantV) {
//                                return false;
//                            }
//                        }
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                onTouchUp();
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    private void onTouchUp() {
        downY = 0;
        downX = 0;
        needHandle = true;
        isFirst = true;
        wantVertical = true;
        mIntercept = false;
        handleTouch = true;
        lastOffset = false;
    }

    /**
     * 修正可以滚动的距离offsetY
     */
    private float fixOffset(float offsetY) {
        int scrollY = getScrollY();
        if (offsetY < 0) {
            if (scrollY + offsetY >= 0) {
                return offsetY;
            } else {
                return -scrollY;
            }
        } else {
            if (scrollY + offsetY <= maxScrollY) {
                return offsetY;
            } else {
                return maxScrollY - scrollY;
            }
        }
    }

    /**
     * 返回是否偏移
     */
    private boolean isOffsetTo(float offsetY) {
        if (Math.abs(offsetY) > mTouchSlop) {
            if (offsetY < 0) {
                //手指下滑
                if (getScrollY() + offsetY <= 0) {
                    //没有位置可以向下滚动了
                    return false;
                }

                boolean scrollVertically = mScrollTarget.canChildScrollUp();
                if (!scrollVertically) {
                    scrollBy(0, (int) (offsetY));
                    return true;
                }
            } else {
                if (isFloat()) {
                } else {
                    scrollBy(0, (int) (offsetY));
                    return true;
                }
            }
        }
        return false;
    }

    private void onTouchDown(MotionEvent event) {
        onTouchUp();

        downX = event.getX();
        downY = event.getY();

        lastMoveX = downX;
        lastMoveY = downY;

        lastScrollY = getScrollY();

        if (isFloat()) {
            if (mFloatView.getMeasuredHeight() + floatTopOffset > downY) {
                inTopTouch = true;
                needHandle = true;
            } else {
                inTopTouch = false;
            }
        } else {
            if (topHeight - lastScrollY > downY) {
                inTopTouch = true;
                needHandle = true;
            } else {
                inTopTouch = false;
            }
        }
        isFirst = true;
        wantVertical = true;

        initScrollTarget();
    }

//    @Override
//    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
//        return true;
//    }
//
//    @Override
//    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//        super.onNestedPreScroll(target, dx, dy, consumed);
//        consumed[0] = dx;
//        consumed[1] = dy;
//    }

    public void setOnScrollListener(StickLayout.OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

}
