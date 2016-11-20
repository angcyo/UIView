package com.angcyo.uiview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.angcyo.uiview.R;

/**
 * Created by angcyo on 2016-11-20.
 */

public class ExEditText extends AppCompatEditText {

    Rect clearRect = new Rect();

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
        if ("emoji".equalsIgnoreCase(String.valueOf(getTag()))) {
            final InputFilter[] filters = getFilters();
            final InputFilter[] newFilters = new InputFilter[filters.length + 1];
            System.arraycopy(filters, 0, newFilters, 0, filters.length);
            newFilters[filters.length] = new EmojiFilter();
            setFilters(newFilters);
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
        clearRect.set(w - getPaddingRight() - Math.min(w, h), getPaddingTop(), w - getPaddingRight(), Math.min(w, h) - getPaddingBottom());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && isFocused()) {
            if (checkClear(event.getX(), event.getY())) {
                if (!TextUtils.isEmpty(getText())) {
                    setText("");
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void checkEdit(boolean focused) {
        if (TextUtils.isEmpty(getText()) || !focused) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.base_edit_delete, 0);
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
}
