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
import android.view.inputmethod.InputMethodManager;

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

    long downTime = 0;//按下的时间

    /**
     * 是否当键盘弹出的时候, touch down事件隐藏键盘
     */
    boolean autoHideSoftInput = false;

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
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
    }

    @Override
    public void setTag(Object tag) {
        super.setTag(tag);
        initView();
    }

    private void initView() {
        Object tag = getTag();
        if (tag != null) {
            String tagString = String.valueOf(tag);
            if (tagString.contains("emoji")) {
                //激活emoji表情过滤
                addFilter(new EmojiFilter());
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
        getClearDrawable();
    }

    private Drawable getClearDrawable() {
        if (showClear && clearDrawable == null) {
            clearDrawable = ResourcesCompat.getDrawable(
                    getResources(),
                    R.drawable.base_edit_delete_selector,
                    getContext().getTheme());
        }
        return clearDrawable;
    }

    private void addFilter(InputFilter filter) {
        final InputFilter[] filters = getFilters();
        final InputFilter[] newFilters = new InputFilter[filters.length + 1];
        System.arraycopy(filters, 0, newFilters, 0, filters.length);
        newFilters[filters.length] = filter;
        setFilters(newFilters);
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
            clearRect.set(w - getPaddingRight() - getClearDrawable().getIntrinsicWidth(),
                    getPaddingTop(), w - getPaddingRight(), Math.min(w, h) - getPaddingBottom());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (showClear && isFocused()) {
            if (action == MotionEvent.ACTION_DOWN) {
                isDownIn = checkClear(event.getX(), event.getY());
                updateState(isDownIn);
            } else if (action == MotionEvent.ACTION_MOVE) {
                updateState(checkClear(event.getX(), event.getY()));
            } else if (action == MotionEvent.ACTION_UP) {
                updateState(false);
                if (isDownIn && checkClear(event.getX(), event.getY())) {
                    if (!TextUtils.isEmpty(getText())) {
                        setText("");
                        return true;
                    }
                }
                isDownIn = false;

                if (autoHideSoftInput && isSoftKeyboardShow()) {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            hideSoftInput();
                        }
                    });
                }
            } else if (action == MotionEvent.ACTION_CANCEL) {
                updateState(false);
                isDownIn = false;
            }
        }

//        if (isPassword) {
//            if (action == MotionEvent.ACTION_DOWN) {
//                downTime = System.currentTimeMillis();
//            } else if (action == MotionEvent.ACTION_MOVE) {
//                if ((System.currentTimeMillis() - downTime) > 100) {
//                    if (isDownIn) {
//                        hidePassword();
//                    } else {
//                        showPassword();
//                    }
//                }
//            } else if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
//                hidePassword();
//            }
//        }
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

    public void checkEdit(boolean focused) {
        if (showClear) {
            final Drawable[] compoundDrawables = getCompoundDrawables();
            if (TextUtils.isEmpty(getText()) || !focused) {
                setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0], compoundDrawables[1],
                        null, compoundDrawables[3]);
            } else {
                setError(null);
                setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0], compoundDrawables[1],
                        getClearDrawable(), compoundDrawables[3]);
            }
        }
    }

    private boolean checkClear(float x, float y) {
        return clearRect.contains(((int) x), (int) y);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        checkEdit(isFocused());
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        checkEdit(isFocused());
    }

    private boolean hasPasswordTransformation() {
        return this.getTransformationMethod() instanceof PasswordTransformationMethod;
    }

    public void showPassword() {
        final int selection = getSelectionEnd();
        setTransformationMethod(null);
        setSelection(selection);
    }

    public void hidePassword() {
        final int selection = getSelectionEnd();
        setTransformationMethod(PasswordTransformationMethod.getInstance());
        setSelection(selection);
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

    /**
     * 判断是否是手机号码
     */
    public boolean isPhone() {
        final String phone = string().trim();
        return !TextUtils.isEmpty(phone) && phone.matches("^1[3-8]\\d{9}$");
    }

    /**
     * 判断是否是有效
     */
    public boolean isPassword() {
        final String string = string().trim();
        return !TextUtils.isEmpty(string) && string.matches("^[a-zA-Z0-9_-]{6,12}$");
    }

    /**
     * 判断键盘是否显示
     */
    public boolean isSoftKeyboardShow() {
        int screenHeight = getScreenHeightPixels();
        int keyboardHeight = getSoftKeyboardHeight();
        return screenHeight != keyboardHeight && keyboardHeight > 100;
    }

    public void hideSoftInput() {
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(getWindowToken(), 0);
    }

    public void setAutoHideSoftInput(boolean autoHideSoftInput) {
        this.autoHideSoftInput = autoHideSoftInput;
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

    /**
     * 长度
     */
    public int length() {
        return string().length();
    }

    public String string() {
        return getText().toString().trim();
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(string());
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
}
