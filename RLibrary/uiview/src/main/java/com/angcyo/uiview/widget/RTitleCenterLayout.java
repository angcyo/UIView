package com.angcyo.uiview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：一定让标题居中的layout
 * 创建人员：Robi
 * 创建时间：2017/01/13 11:25
 * 修改人员：Robi
 * 修改时间：2017/01/13 11:25
 * 修改备注：
 * Version: 1.0.0
 */
public class RTitleCenterLayout extends RelativeLayout {

    protected View mTitleView;
    protected View mLoadingView;

    public RTitleCenterLayout(Context context) {
        super(context);
    }

    public RTitleCenterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RTitleCenterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RTitleCenterLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        if (mLoadingView != null) {
            int loadViewWidth = mLoadingView.getMeasuredWidth();
            if (mTitleView != null) {
                mTitleView.measure(MeasureSpec.makeMeasureSpec(width - 2 * loadViewWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
            }
        } else {
            if (mTitleView != null) {
                mTitleView.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), heightMeasureSpec);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (mTitleView != null && mLoadingView != null) {
            if (mTitleView.getVisibility() == VISIBLE) {
                layoutCenter(mTitleView);
                if (mLoadingView.getVisibility() == VISIBLE) {
                    float offset = getResources().getDisplayMetrics().density * 4;
                    mLoadingView.layout((int) (mTitleView.getLeft() - mLoadingView.getMeasuredWidth() - offset),
                            (height - mLoadingView.getMeasuredHeight()) / 2,
                            (int) (mTitleView.getLeft() - offset), height / 2 + mLoadingView.getMeasuredHeight() / 2);
                }
                //mTitleView.setBackgroundColor(Color.BLUE);
            } else {
                if (mLoadingView.getVisibility() == VISIBLE) {
                    layoutCenter(mLoadingView);
                }
            }
        } else {
            super.onLayout(changed, l, t, r, b);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            Object tag = view.getTag();
            if (tag != null && "title".equalsIgnoreCase(tag.toString())) {
                mTitleView = view;
            }
            if (view instanceof LoadingImageView) {
                mLoadingView = view;
            }
        }
    }

    private void layoutCenter(View view) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        view.layout((width - view.getMeasuredWidth()) / 2, (height - view.getMeasuredHeight()) / 2,
                width / 2 + view.getMeasuredWidth() / 2, height / 2 + view.getMeasuredHeight() / 2);
    }
}
