package com.angcyo.uiview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.text.util.LinkifyCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.AttributeSet;

import com.angcyo.uiview.R;
import com.angcyo.uiview.skin.SkinHelper;

import java.util.Locale;

/**
 * 通过Tag属性, 得到text格式化模板, 然后重写setText实现格式化
 * Created by angcyo on 2017-01-08.
 */

public class RTextView extends AppCompatTextView {

    /**
     * 左边垂直矩形的颜色
     */
    Rect leftColorRect;
    Paint colorPaint;
    int leftWidth = 0;
    @ColorInt
    int leftColor;
    int leftMargin = 0;

    boolean hasUnderline = false;

    boolean isAttached = false;

    public RTextView(Context context) {
        this(context, null);
    }

    public RTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RTextView);
        leftColor = typedArray.getColor(R.styleable.RTextView_r_left_color, SkinHelper.getSkin().getThemeColor());
        leftWidth = typedArray.getDimensionPixelOffset(R.styleable.RTextView_r_left_width, 0);
        hasUnderline = typedArray.getBoolean(R.styleable.RTextView_r_has_underline, false);

        typedArray.recycle();

        initLeftRes();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initLeftRes();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (hasUnderline) {
            getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            getPaint().setAntiAlias(true);
        }
        super.onDraw(canvas);
        if (leftWidth > 0) {
            canvas.drawRect(leftColorRect, colorPaint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isAttached = false;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isAttached = true;
        if (getContentDescription() != null) {
            LinkifyCompat.addLinks(this, Linkify.ALL);
        }
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

    public void addFilter(InputFilter filter) {
        final InputFilter[] filters = getFilters();
        final InputFilter[] newFilters = new InputFilter[filters.length + 1];
        System.arraycopy(filters, 0, newFilters, 0, filters.length);
        newFilters[filters.length] = filter;
        setFilters(newFilters);
    }

    public void setMaxLength(int length) {
        InputFilter[] filters = getFilters();
        boolean have = false;
        InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(length);
        for (int i = 0; i < filters.length; i++) {
            InputFilter filter = filters[i];
            if (filter instanceof InputFilter.LengthFilter) {
                have = true;
                filters[i] = lengthFilter;
                setFilters(filters);
                break;
            }
        }
        if (!have) {
            addFilter(lengthFilter);
        }
    }

    public void setText(Object... args) {
        if (getTag() != null && args != null && args.length > 0) {
            try {
                final String format = String.format(Locale.CHINA, getTag().toString(), args);
                super.setText(format);
            } catch (Exception e) {
                super.setText("");
            }
        } else {
            super.setText("");
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

    private void initLeftRes() {
        if (leftWidth <= 0) {
            return;
        }

        int viewHeight = getMeasuredHeight();
        if (leftColorRect == null) {
            leftColorRect = new Rect();
        }
        if (colorPaint == null) {
            colorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }
        colorPaint.setColor(leftColor);
        leftColorRect.set(leftMargin, getPaddingTop(),
                leftMargin + leftWidth, viewHeight - getPaddingBottom());
    }

    public RTextView setLeftWidth(int leftWidth) {
        this.leftWidth = leftWidth;
        initLeftRes();
        return this;
    }

    public RTextView setLeftColor(int leftColor) {
        this.leftColor = leftColor;
        initLeftRes();
        return this;
    }

    public RTextView setLeftColor(int leftColor, int leftWidth) {
        this.leftColor = leftColor;
        this.leftWidth = leftWidth;
        initLeftRes();
        return this;
    }

    public void setDefaultSKin(int resId) {
        setDefaultSKin(getResources().getString(resId));
    }

    public void setDefaultSKin(String text) {
        setPadding(getResources().getDimensionPixelOffset(R.dimen.base_hdpi), getPaddingTop(),
                getPaddingRight(), getPaddingBottom());
        this.leftColor = SkinHelper.getSkin().getThemeSubColor();
        this.leftWidth = getResources().getDimensionPixelOffset(R.dimen.base_mdpi);
        setText(text);
//        if (isAttached) {
//            postInvalidate();
//        }
    }
}
