package com.angcyo.uiview.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Locale;

/**
 * 通过Tag属性, 得到text格式化模板, 然后重写setText实现格式化
 * Created by angcyo on 2017-01-08.
 */

public class RTextView extends TextView {
    public RTextView(Context context) {
        this(context, null);
    }

    public RTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (getTag() != null && !TextUtils.isEmpty(text)) {
            try {
                final String format = String.format(Locale.CHINA, getTag().toString(), text);
                super.setText(format, type);
            } catch (Exception e) {
                super.setText(text, type);
            }
        } else {
            super.setText(text, type);
        }
    }

    public void setLeftIco(@DrawableRes int leftIco) {
        final Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(leftIco),
                compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
    }

    public void setRightIco(@DrawableRes int rightIco) {
        final Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0],
                compoundDrawables[1], getResources().getDrawable(rightIco), compoundDrawables[3]);
    }

    public void setTopIco(@DrawableRes int topIco) {
        final Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0], getResources().getDrawable(topIco),
                compoundDrawables[2], compoundDrawables[3]);
    }

    public void setBottomIco(@DrawableRes int bottomIco) {
        final Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawablesWithIntrinsicBounds(
                compoundDrawables[0], compoundDrawables[1],
                compoundDrawables[2], getResources().getDrawable(bottomIco));
    }

}
