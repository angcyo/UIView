package com.angcyo.uiview.rsen;

import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/01/16 17:47
 * 修改人员：Robi
 * 修改时间：2017/01/16 17:47
 * 修改备注：
 * Version: 1.0.0
 */
public class RGestureDetector {
    /**
     * 双击
     */
    public static void onDoubleTap(@NonNull View targetView, @NonNull final OnDoubleTapListener onDoubleTapListener) {
        final GestureDetectorCompat gestureDetectorCompat = new GestureDetectorCompat(targetView.getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        onDoubleTapListener.onDoubleTap();
                        return super.onDoubleTap(e);
                    }
                });
        targetView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorCompat.onTouchEvent(event);
                return false;
            }
        });
    }

    public interface OnDoubleTapListener {
        void onDoubleTap();
    }
}
