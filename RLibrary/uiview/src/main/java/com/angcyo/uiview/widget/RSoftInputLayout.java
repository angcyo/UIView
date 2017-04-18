package com.angcyo.uiview.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：支持透明状态栏, 支持Dialog, 支持动画
 * 创建人员：Robi
 * 创建时间：2016/12/21 9:01
 * 修改人员：Robi
 * 修改时间：2016/12/21 9:01
 * 修改备注：
 * Version: 1.0.0
 */
public class RSoftInputLayout extends FrameLayout {

    private static final String TAG = "Robi";
    View contentLayout;
    View emojiLayout;
    /**
     * 缺省的键盘高度
     */
    int keyboardHeight = 0;

    /**
     * 需要显示的键盘高度,可以指定显示多高
     */
    int showEmojiHeight = 0;

    boolean isEmojiShow = false;

    HashSet<OnEmojiLayoutChangeListener> mEmojiLayoutChangeListeners = new HashSet<>();

    /**
     * 键盘是否显示
     */
    private boolean isKeyboardShow = false;
    private boolean mClipToPadding;
    private Runnable mCheckSizeChanged = new Runnable() {
        @Override
        public void run() {
            onSizeChanged(0, 0, 0, 0);
        }
    };
    private ValueAnimator mValueAnimator;

    /**
     * 使用动画的形式展开表情布局
     */
    private boolean isAnimToShow = true;

    public RSoftInputLayout(Context context) {
        super(context);
    }

    public RSoftInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RSoftInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RSoftInputLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setAnimToShow(boolean animToShow) {
        isAnimToShow = animToShow;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (!isInEditMode() && isEnabled()) {
            setFitsSystemWindows(true);
            setClipToPadding(false);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setFitsSystemWindows(enabled);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        int childCount = getChildCount();
        /*请按顺序布局*/
        if (childCount > 0) {
            contentLayout = getChildAt(0);
        }
        if (childCount > 1) {
            emojiLayout = getChildAt(1);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (contentLayout == null || !isEnabled()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        int maxHeight = heightSize - getPaddingBottom() - getPaddingTop();
        int maxHeight = heightSize - getPaddingTop();
        int paddingBottom = getPaddingBottom();

//        L.w("height:" + heightSize + " paddBottom:" + paddingBottom);

        if (isInEditMode()) {
            contentLayout.measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY));
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
            if (emojiLayout != null) {
                emojiLayout.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY));
            }
            setMeasuredDimension(widthSize, heightSize);
            return;
        }

        isKeyboardShow = isSoftKeyboardShow();
        if (isKeyboardShow) {
            keyboardHeight = getSoftKeyboardHeight();
            //isEmojiShow = false;
        }

        int contentHeight;
        int emojiHeight;

        if (isEmojiShow) {
            if (getShowEmojiHeight() == 0) {
                emojiHeight = keyboardHeight;
            } else {
                emojiHeight = getShowEmojiHeight();
            }
        } else {
            emojiHeight = 0;
        }

        if (isKeyboardShow) {
            if (maxHeight + keyboardHeight > getScreenHeightPixels()) {
                contentHeight = maxHeight - keyboardHeight;
            } else {
                contentHeight = maxHeight;
            }
        } else {
            contentHeight = maxHeight - emojiHeight;
        }

        contentLayout.measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(contentHeight, MeasureSpec.EXACTLY));
        if (emojiLayout != null) {
            emojiLayout.measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(emojiHeight, MeasureSpec.EXACTLY));
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (contentLayout == null || !isEnabled()) {
            super.onLayout(changed, l, t, r, b);
            return;
        }

        if (isInEditMode()) {
            contentLayout.layout(l, t, r, b);
            if (emojiLayout != null) {
                emojiLayout.layout(l, b, r, b);
            }
            return;
        }

        int paddingTop = getPaddingTop();
        t += paddingTop;
        contentLayout.layout(l, t, r, contentLayout.getMeasuredHeight() + paddingTop);
        if (emojiLayout != null) {
            emojiLayout.layout(l, contentLayout.getMeasuredHeight() + paddingTop, r, getMeasuredHeight());
        }
    }

    @Override
    public void setClipToPadding(boolean clipToPadding) {
        super.setClipToPadding(clipToPadding);
        mClipToPadding = clipToPadding;
    }

    @Override
    public int getPaddingTop() {
        if (!mClipToPadding) {
            return 0;
        }
        return super.getPaddingTop();
    }

    /**
     * 当在5.0+ 手机上, 并且设置了 状态栏透明, 则onSizeChange方法不会执行,
     * 因为此时系统设置了PaddingBottom来腾出键盘的位置, 而不是通过改变高度.
     * 这个时候需要通过下面的方法处理
     */
    @Override
    protected boolean fitSystemWindows(Rect insets) {
        boolean result = super.fitSystemWindows(insets);
        post(mCheckSizeChanged);
        return result;
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        return super.onApplyWindowInsets(insets);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (!isEnabled()) {
            return;
        }

        removeCallbacks(mCheckSizeChanged);
        if (isSoftKeyboardShow()) {
            isEmojiShow = false;
        }
        notifyEmojiLayoutChangeListener(isEmojiShow, isKeyboardShow,
                isKeyboardShow ? getSoftKeyboardHeight() : showEmojiHeight);
    }

    /**
     * 判断键盘是否显示
     */
    public boolean isSoftKeyboardShow() {
        int screenHeight = getScreenHeightPixels();
        int keyboardHeight = getSoftKeyboardHeight();
        return screenHeight != keyboardHeight && keyboardHeight > 100;
    }

    /**
     * 获取键盘的高度
     */
    public int getSoftKeyboardHeight() {
        int screenHeight = getScreenHeightPixels();
        Rect rect = new Rect();
        getWindowVisibleDisplayFrame(rect);
        int visibleBottom = rect.bottom;
        return screenHeight - visibleBottom;
    }

    /**
     * 屏幕高度(不包含虚拟导航键盘的高度)
     */
    private int getScreenHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    private void showEmojiLayoutInner(int height) {
        if (isEmojiShow && height == showEmojiHeight) {
            return;
        }

        boolean keyboardShow = isKeyboardShow();
        int oldHeight = showEmojiHeight;

        isEmojiShow = height > 0;
        this.showEmojiHeight = height;
        if (keyboardShow) {
            hideSoftInput();
        } else {
            requestLayout();
            if (isAnimToShow) {
                animToShow(height, oldHeight);
            } else {
                post(mCheckSizeChanged);
            }
        }
    }

    private void animToShow(int height, int oldHeight) {
        if (mValueAnimator != null) {
            return;
        }

        mValueAnimator = ObjectAnimator.ofInt(oldHeight, height);
        mValueAnimator.setDuration(300);
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                showEmojiLayoutInner((Integer) animation.getAnimatedValue());
            }
        });
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mValueAnimator = null;
                post(mCheckSizeChanged);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mValueAnimator.start();
    }

    /**
     * 显示表情布局
     *
     * @param height 指定表情需要显示的高度
     */
    public void showEmojiLayout(final int height) {
        showEmojiLayoutInner(height);
    }

    /**
     * 采用默认的键盘高度显示表情, 如果键盘从未弹出过, 则使用一个缺省的高度
     */
    public void showEmojiLayout() {
        if (keyboardHeight == 0) {
            keyboardHeight = getDefaultKeyboardHeight();
        }
        showEmojiLayoutInner(keyboardHeight);
    }

    private int getDefaultKeyboardHeight() {
        return (int) (getResources().getDisplayMetrics().density * 200);
    }

    /**
     * 采用默认的键盘高度显示表情, 如果键盘从未弹出过, 则使用一个缺省的高度
     */
    public void hideEmojiLayout() {
        if (isKeyboardShow || isEmojiShow) {
            showEmojiLayoutInner(0);
        }
    }

    public void addOnEmojiLayoutChangeListener(OnEmojiLayoutChangeListener listener) {
        mEmojiLayoutChangeListeners.add(listener);
    }

    public void removeOnEmojiLayoutChangeListener(OnEmojiLayoutChangeListener listener) {
        mEmojiLayoutChangeListeners.remove(listener);
    }

    private void notifyEmojiLayoutChangeListener(boolean isEmojiShow, boolean isKeyboardShow, int height) {
        Iterator<OnEmojiLayoutChangeListener> iterator = mEmojiLayoutChangeListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onEmojiLayoutChange(isEmojiShow, isKeyboardShow, height);
        }
    }

    public void hideSoftInput() {
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(getWindowToken(), 0);
    }

    public void showSoftInput(View view) {
        view.requestFocus();
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(view, 0);
    }

    /**
     * 当键盘显示时, 可以通过此方法返回键盘的高度
     */
    public int getKeyboardHeight() {
        if (keyboardHeight == 0) {
            keyboardHeight = getDefaultKeyboardHeight();
        }
        return keyboardHeight;
    }

    public void setKeyboardHeight(int keyboardHeight) {
        this.keyboardHeight = keyboardHeight;
    }

    public boolean isEmojiShow() {
        return isEmojiShow;
    }

    public boolean isKeyboardShow() {
        return isKeyboardShow;
    }

    /**
     * 当表情显示时, 可以通过此方法返回表情的高度
     */
    public int getShowEmojiHeight() {
        return showEmojiHeight;
    }

    /**
     * 返回true时, 可以退出. 否则会隐藏键盘或者表情.
     */
    public boolean requestBackPressed() {
        if (isKeyboardShow || isEmojiShow) {
            hideEmojiLayout();
            return false;
        }
        return true;
    }

    public interface OnEmojiLayoutChangeListener {
        /**
         * @param height         EmojiLayout弹出的高度 或者 键盘弹出的高度
         * @param isEmojiShow    表情布局是否显示了
         * @param isKeyboardShow 键盘是否显示了
         */
        void onEmojiLayoutChange(boolean isEmojiShow, boolean isKeyboardShow, int height);
    }
}
