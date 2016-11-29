package com.angcyo.uiview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.angcyo.uiview.R;

/**
 * Created by angcyo on 2016-11-20.
 */

public class ExEditText extends AppCompatEditText {

    Rect clearRect = new Rect();//删除按钮区域
    boolean isDownIn = false;//是否在按钮区域按下
    Drawable clearDrawable;

    boolean showClear = true;//是否显示删除按钮

    boolean isPassword = false;//隐藏显示密码

    boolean handleTouch = false;

    public ExEditText(Context context) {
        super(context);
    }

    public ExEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Object tag = getTag();
        if (tag != null) {
            String tagString = String.valueOf(tag);
            if (tagString.contains("emoji")) {
                //激活emoji表情过滤
                final InputFilter[] filters = getFilters();
                final InputFilter[] newFilters = new InputFilter[filters.length + 1];
                System.arraycopy(filters, 0, newFilters, 0, filters.length);
                newFilters[filters.length] = new EmojiFilter();
                setFilters(newFilters);
            }

            if (tagString.contains("password")) {
                //隐藏显示密码
                isPassword = true;
            }

            if (tagString.contains("hide")) {
                //隐藏删除按钮
                showClear = false;
            } else if (tagString.contains("show")) {
                //显示删除按钮
                showClear = true;
            }
        }
        if (showClear) {
            clearDrawable = ResourcesCompat.getDrawable(
                    getResources(),
                    R.drawable.base_edit_delete_selector,
                    getContext().getTheme());
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        checkEdit(focused);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (showClear) {
            clearRect.set(w - getPaddingRight() - clearDrawable.getIntrinsicWidth(),
                    getPaddingTop(), w - getPaddingRight(), Math.min(w, h) - getPaddingBottom());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (showClear && isFocused()) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                isDownIn = checkClear(event.getX(), event.getY());
                updateState(isDownIn);
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                updateState(checkClear(event.getX(), event.getY()));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                updateState(false);
                if (isDownIn && checkClear(event.getX(), event.getY())) {
                    if (!TextUtils.isEmpty(getText())) {
                        setText("");
                        return true;
                    }
                }
                isDownIn = false;
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                updateState(false);
                isDownIn = false;
            }
        }

        if (isPassword) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                handleTouch = !checkClear(event.getX(), event.getY());
                if (handleTouch) {
                    passwordVisibilityToggleRequested();
                }
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
                if (handleTouch) {
                    passwordVisibilityToggleRequested();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void updateState(boolean isDownIn) {
        final Drawable clearDrawable = getCompoundDrawables()[2];
        if (clearDrawable == null) {
            return;
        }
        if (isDownIn) {
            clearDrawable.setState(new int[]{android.R.attr.state_checked});
        } else {
            clearDrawable.setState(new int[]{});
        }
    }

    private void checkEdit(boolean focused) {
        if (showClear) {
            final Drawable[] compoundDrawables = getCompoundDrawables();
            if (TextUtils.isEmpty(getText()) || !focused) {
                setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0], compoundDrawables[1],
                        null, compoundDrawables[3]);
            } else {
                setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0], compoundDrawables[1],
                        clearDrawable, compoundDrawables[3]);
            }
        }
    }

    private boolean checkClear(float x, float y) {
        return clearRect.contains(((int) x), (int) y);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        checkEdit(true);
    }

    private boolean hasPasswordTransformation() {
        return this.getTransformationMethod() instanceof PasswordTransformationMethod;
    }

    void passwordVisibilityToggleRequested() {
        final int selection = getSelectionEnd();

        if (hasPasswordTransformation()) {
            setTransformationMethod(null);
        } else {
            setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

        // And restore the cursor position
        setSelection(selection);
    }
}
