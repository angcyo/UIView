package com.angcyo.uiview.widget;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.angcyo.uiview.container.IWindowInsetsListener;
import com.angcyo.uiview.utils.Reflect;
import com.angcyo.uiview.view.ILifecycle;

import java.util.ArrayList;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：针对键盘 弹出隐藏的问题, 进行修复
 * 创建人员：Robi
 * 创建时间：2016/12/02 11:30
 * 修改人员：Robi
 * 修改时间：2016/12/02 11:30
 * 修改备注：
 * Version: 1.0.0
 */
public class SoftInputLayout extends RelativeLayout implements ILifecycle {
    boolean isViewShow = false;
    private ArrayList<IWindowInsetsListener> mOnWindowInsetsListeners;
    private int[] mInsets = new int[4];
    /**
     * 锁定高度, 当键盘弹出的时候, 可以不改变size
     */
    private boolean lockHeight = false;

    public SoftInputLayout(Context context) {
        super(context);
    }

    public SoftInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setFitsSystemWindows(true);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                RelativeLayout.LayoutParams st =
                        (RelativeLayout.LayoutParams) child.getLayoutParams();
                int offset = mInsets[3];
                int left = (int) Reflect.getMember(st, "mLeft");
                int top = (int) Reflect.getMember(st, "mTop");
                int right = (int) Reflect.getMember(st, "mRight");
                int bottom = (int) Reflect.getMember(st, "mBottom");

                int height = getMeasuredHeight() - offset;

                /*修复对话框中,包含输入控件,键盘弹出时, 无法居中的BUG*/
                if (st.getRules()[RelativeLayout.CENTER_IN_PARENT] == RelativeLayout.TRUE ||
                        st.getRules()[RelativeLayout.CENTER_VERTICAL] == RelativeLayout.TRUE) {
                    //child.layout(left, , right, height / 2 + child.getMeasuredHeight() / 2);

                    ViewCompat.animate(child)
                            .translationY(((height - child.getMeasuredHeight()) / 2) - top)
                            .setInterpolator(new DecelerateInterpolator())
                            .setDuration(300)
                            .start();
                } else if (st.getRules()[RelativeLayout.ALIGN_PARENT_BOTTOM] == RelativeLayout.TRUE) {
                    ViewCompat.animate(child)
                            .translationY(-offset)
                            .setInterpolator(new DecelerateInterpolator())
                            .setDuration(300)
                            .start();
                }
            }
        }
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mInsets[0] = insets.getSystemWindowInsetLeft();
            mInsets[1] = insets.getSystemWindowInsetTop();
            mInsets[2] = insets.getSystemWindowInsetRight();
            mInsets[3] = insets.getSystemWindowInsetBottom();

            if (isViewShow) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        notifyListener();
                    }
                });
                return super.onApplyWindowInsets(insets.replaceSystemWindowInsets(insets.getSystemWindowInsetLeft(), 0,
                        insets.getSystemWindowInsetRight(), lockHeight ? 0 : insets.getSystemWindowInsetBottom()));
            } else {
                setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);
                return insets.replaceSystemWindowInsets(insets.getSystemWindowInsetLeft(), 0,
                        insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
            }

        } else {
            return super.onApplyWindowInsets(insets);
        }
    }

    private void notifyListener() {
         /*键盘弹出监听事件*/
        if (mOnWindowInsetsListeners != null) {
            for (IWindowInsetsListener listener : mOnWindowInsetsListeners) {
                listener.onWindowInsets(mInsets[0], mInsets[1], mInsets[2], mInsets[3]);
            }
        }
    }

    public SoftInputLayout addOnWindowInsetsListener(IWindowInsetsListener listener) {
        if (listener == null) {
            return this;
        }
        if (mOnWindowInsetsListeners == null) {
            mOnWindowInsetsListeners = new ArrayList<>();
        }
        this.mOnWindowInsetsListeners.add(listener);
        return this;
    }

    public SoftInputLayout removeOnWindowInsetsListener(IWindowInsetsListener listener) {
        if (listener == null || mOnWindowInsetsListeners == null) {
            return this;
        }
        this.mOnWindowInsetsListeners.remove(listener);
        return this;
    }

    public void setLockHeight(boolean lockHeight) {
        this.lockHeight = lockHeight;
    }

    /**
     * 获取底部装饰物的高度 , 通常是键盘的高度
     */
    public int getInsersBottom() {
        return mInsets[3];
    }

    public void hideSoftInput() {
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(getWindowToken(), 0);
    }

    public void showSoftInput() {
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInputFromInputMethod(getWindowToken(), 0);
    }

    @Override
    public void onViewShow() {
        isViewShow = true;
        lockHeight = false;
    }

    @Override
    public void onViewHide() {
        isViewShow = false;
        lockHeight = true;
    }
}
