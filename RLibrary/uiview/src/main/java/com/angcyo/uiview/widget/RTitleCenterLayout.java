package com.angcyo.uiview.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getResources().getDisplayMetrics().widthPixels;//MeasureSpec.getSize(widthMeasureSpec);// - getPaddingLeft() - getPaddingRight();
        int maxWidth = (int) (width * 3f / 4);

        if (mLoadingView != null) {
            int loadViewWidth = mLoadingView.getMeasuredWidth();
            if (mTitleView != null) {
                mTitleView.measure(
                        MeasureSpec.makeMeasureSpec(Math.min(width - 2 * loadViewWidth, mTitleView.getMeasuredWidth()), MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(mTitleView.getMeasuredHeight(), MeasureSpec.EXACTLY)
                );
            }
        } else {
            if (mTitleView != null) {
                mTitleView.measure(
                        MeasureSpec.makeMeasureSpec(Math.min(width, mTitleView.getMeasuredWidth()), MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(mTitleView.getMeasuredHeight(), MeasureSpec.EXACTLY)
                );
            }
        }

        setMeasuredDimension(maxWidth, MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //super.onLayout(changed, l, t, r, b);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        float offset = getResources().getDisplayMetrics().density * 4;
        int loadViewRight = -1;
        //有标题view的情况
        if (mTitleView != null && mTitleView.getVisibility() == VISIBLE) {
            if (mTitleView instanceof TextView) {
                if (!TextUtils.isEmpty(((TextView) mTitleView).getText())) {
                    loadViewRight = (int) (layoutCenter(mTitleView, width, height) - offset);
                }
            } else {
                loadViewRight = (int) (layoutCenter(mTitleView, width, height) - offset);
            }
        }

        if (mLoadingView != null && mLoadingView.getVisibility() == VISIBLE) {
            int top = (height - mLoadingView.getMeasuredHeight()) / 2;

            if (loadViewRight == -1) {
                layoutCenter(mLoadingView, width, height);
            } else {
                mLoadingView.layout(loadViewRight - mLoadingView.getMeasuredWidth(), top,
                        loadViewRight, top + mLoadingView.getMeasuredHeight());
            }
        }

    }

    private int layoutCenter(View view, int width, int height) {
        int left = (width - view.getMeasuredWidth()) / 2;
        int top = (height - view.getMeasuredHeight()) / 2;
        view.layout(left, top, left + view.getMeasuredWidth(), top + view.getMeasuredHeight());
        return left;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
//            Object tag = view.getTag();
//            if (mTitleView == null && (tag != null && "title".equalsIgnoreCase(tag.toString()))) {
//                mTitleView = view;
//            }
            if (view instanceof LoadingImageView) {
                mLoadingView = view;
                break;
            }
        }

        if (mTitleView == null) {
            mTitleView = findViewWithTag("title");
        }
    }

    /**
     * Title View 会自动居中显示, 并且loading View始终会在TitleView 的左边
     */
    public void setTitleView(View titleView) {
        mTitleView = titleView;
    }
}
