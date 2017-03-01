package com.angcyo.uiview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.angcyo.uiview.R;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：实现 {@link android.widget.RadioGroup}的功能
 * 创建人员：Robi
 * 创建时间：2017/01/11 13:45
 * 修改人员：Robi
 * 修改时间：2017/01/11 13:45
 * 修改备注：
 * Version: 1.0.0
 */
public class RCheckGroup extends LinearLayout implements View.OnClickListener {

    /**
     * 当前选中的view
     */
    private ICheckView mCheckView;

    /**
     * 选中view的id
     */
    @IdRes
    private int checkId = -10000;

    private View checkView;

    private OnCheckChangedListener mCheckChangedListener;

    public RCheckGroup(Context context) {
        this(context, null);
    }

    public RCheckGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RCheckGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RCheckGroup);
        checkId = typedArray.getResourceId(R.styleable.RCheckGroup_checked_id, checkId);
        typedArray.recycle();
        initGroup();
    }

    private void initGroup() {

    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        //动态添加的view, 设置了默认的选中item, 不会有事件通知.
        if (child instanceof ICheckView) {
            child.setOnClickListener(this);
            ((ICheckView) child).setChecked(child.getId() == checkId);
            if (child.getId() == checkId) {
                checkView = child;
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        View oldView = checkView;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof ICheckView) {
                if (view.getId() == checkId) {
                    checkView = view;
                }
            }
        }

        if (checkView != null) {
            notifyListener(oldView, checkView);
        }
    }

    private void notifyListener(View from, View to) {
        if (mCheckChangedListener != null) {
            if (from == to) {
                mCheckChangedListener.onReChecked(to);
            } else {
                mCheckChangedListener.onChecked(from, to);
            }
        }
    }

    public View getCheckView() {
        return checkView;
    }

    public void setCheckView(View checkView) {
        View oldView = this.checkView;

        this.checkView = checkView;
        this.checkId = checkView.getId();

        notifyListener(oldView, checkView);
    }

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        View oldView = this.checkView;

        this.checkId = checkId;
        this.checkView = findViewById(checkId);

        notifyListener(oldView, checkView);
    }

    public void setOnCheckChangedListener(OnCheckChangedListener checkChangedListener) {
        mCheckChangedListener = checkChangedListener;
    }

    @Override
    public void onClick(View v) {
        View oldView = this.checkView;

        if (v instanceof ICheckView) {
            checkView = v;
            ICheckView iCheckView = (ICheckView) checkView;

            if (oldView != null) {
                ((ICheckView) oldView).setChecked(false);
            }

            if (iCheckView.isChecked()) {
                //已经是选中状态
            } else {
                //非选中状态
                iCheckView.setChecked(true);
            }

            notifyListener(oldView, v);
        }
    }

    public interface OnCheckChangedListener {
        /**
         * 其他按钮选中
         */
        void onChecked(View fromm, View to);

        /**
         * 相同按钮重复选中
         */
        void onReChecked(View view);
    }

    public interface ICheckView {
        /**
         * 是否选中
         */
        boolean isChecked();

        /**
         * 设置状态
         */
        void setChecked(boolean checked);
    }
}
