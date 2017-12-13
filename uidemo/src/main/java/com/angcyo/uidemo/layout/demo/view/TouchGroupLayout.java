package com.angcyo.uidemo.layout.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.utils.RTextPaint;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/11 16:49
 * 修改人员：Robi
 * 修改时间：2017/04/11 16:49
 * 修改备注：
 * Version: 1.0.0
 */
public class TouchGroupLayout extends RelativeLayout {

    static String TAG = "touchGroup";

    RTextPaint mRTextPaint;
    String string1, string2, string3;
    private float mDownX;
    private float mDownY;
    private float mMoveX;
    private float mMoveY;

    public TouchGroupLayout(Context context) {
        this(context, null);
    }

    public TouchGroupLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRTextPaint = new RTextPaint();
        mRTextPaint.setTextColor(Color.RED);
        setWillNotDraw(false);

//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        final int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (i == 0 && child.getVisibility() != GONE) {
                RelativeLayout.LayoutParams st =
                        (RelativeLayout.LayoutParams) child.getLayoutParams();
                child.layout(child.getLeft(), child.getTop(),
                        child.getLeft() + child.getMeasuredWidth(), child.getTop() + child.getMeasuredHeight());
            }
        }

        L.e("call: onLayout([changed, l, t, r, b])-> " +
                " l:" + l +
                " t:" + t +
                " r:" + r +
                " b:" + b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        L.e("call: dispatchTouchEvent([ev])-> " + event.getAction() + " x:" + event.getX() + " y:" + event.getY());
        string1 = "dispatchTouchEvent-> " + event.getAction() + " x:" + event.getX() + " y:" + event.getY();
        postInvalidate();

//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            event.offsetLocation(-300, -300);
//        }
        float x = event.getX();
        float y = event.getY();

//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            mDownX = x;
//            mDownY = y;
////            if (y < 600) {
////                event.offsetLocation(100 - mDownX, 100 - mDownY);
////                //return false;
////                //mDownX = event.getX();
////                //mDownY = event.getY();
////            }
//        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
//            mMoveX = x;
//            mMoveY = y;
//            if (y < 600) {
//                //event.offsetLocation(mDownX - mMoveX, mDownY - mMoveY);
//                //return false;
//                //mDownX = event.getX();
//                //mDownY = event.getY();
//            }
//        }
        boolean touchEvent = super.dispatchTouchEvent(event);
        return touchEvent;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        L.e("call: onInterceptTouchEvent([ev])-> " + event.getAction() + " x:" + x + " y:" + y);
        string2 = "onInterceptTouchEvent-> " + event.getAction() + " x:" + x + " y:" + y;
        postInvalidate();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mDownX = x;
            mDownY = y;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mMoveX = x;
            mMoveY = y;
            if (y < 600) {
                event.offsetLocation(mDownX - mMoveX, mDownY - mMoveY);
                //return false;
            }
        }
        boolean touchEvent = super.onInterceptTouchEvent(event);

        return touchEvent;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean touchEvent = super.onTouchEvent(event);

        L.e("call: onTouchEvent([event])-> " + event.getAction() + " x:" + event.getX() + " y:" + event.getY());
        string3 = "onTouchEvent-> " + event.getAction() + " x:" + event.getX() + " y:" + event.getY();
        int actionMasked = event.getActionMasked();
        int pointerCount = event.getPointerCount();
        int index = pointerCount - 1;

        int actionIndex = event.getActionIndex();
        int id = event.getPointerId(actionIndex);

//        for (int i = 0; i < pointerCount; i++) {
//            L.i(TAG, event.getPointerId(i));
//        }


        /**
         * 不管多少个点, 按下去
         * event.getX() event.getY()
         * 获取到的,永远都是第一个点的坐标.
         *
         * 其他点的坐标要通过event.getX(index) event.getY(index) 获取
         * */
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                L.i(TAG, "call__ " + event.getActionMasked() + " " + event.getAction() + " " + actionIndex);

                L.e(TAG, "call: onTouchEvent([event])-> down " + pointerCount + " id:" + id + " x:" + event.getX() + " y:" + event.getY());
                L.i(TAG, "call: onTouchEvent([event])-> down " + pointerCount + " id:" + id + " x:" + event.getX(actionIndex) + " y:" + event.getY(actionIndex));
                break;
            case MotionEvent.ACTION_UP:
                L.e(TAG, "call: onTouchEvent([event])-> up " + pointerCount + " id:" + id + " x:" + event.getX() + " y:" + event.getY());
                for (int i = 0; i < pointerCount; i++) {
                    int pointerId = event.getPointerId(i);
                    L.i(TAG, "getPointerId:" + pointerId + "  findPointerIndex:" + event.findPointerIndex(pointerId));
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                L.i(TAG, "__call " + event.getActionMasked() + " " + event.getAction() + " " + actionIndex);

                //L.e(TAG, "call: onTouchEvent([event])-> pointer down " + " x:" + event.getX() + " y:" + event.getY());
                L.e(TAG, "call: onTouchEvent([event])-> pointer down " + pointerCount + " id:" + id + " x:" + event.getX(index) + " y:" + event.getY(index));
                L.i(TAG, "call: onTouchEvent([event])-> pointer down " + pointerCount + " id:" + id + " x:" + event.getX(actionIndex) + " y:" + event.getY(actionIndex));

//                for (int i = 0; i < pointerCount; i++) {
//                    L.i(TAG, "x:" + event.getX(i) + " y:" + event.getY(i));
//                }

                break;
            case MotionEvent.ACTION_POINTER_UP:
                L.i(TAG, "__call " + event.getActionMasked() + " " + event.getAction() + " " + actionIndex);

                L.e(TAG, "call: onTouchEvent([event])-> pointer up " + pointerCount + " id:" + id + " x:" + event.getX(index) + " y:" + event.getY(index));
//                for (int i = 0; i < pointerCount; i++) {
//                    int pointerId = event.getPointerId(i);
//                    L.i(TAG, "i:" + i + " getPointerId:" + pointerId + "  findPointerIndex:" + event.findPointerIndex(pointerId));
//                }
                break;

        }

        postInvalidate();
        requestLayout();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRTextPaint.drawText(canvas, string1, 0, getMeasuredHeight() - 300);
        mRTextPaint.drawText(canvas, string2, 0, getMeasuredHeight() - 200);
        mRTextPaint.drawText(canvas, string3, 0, getMeasuredHeight() - 100);
    }
}
