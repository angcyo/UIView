package com.angcyo.uiview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.R;
import com.angcyo.uiview.RApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    /**
     * 保存需要@的成员昵称
     */
    List<String> mAllMention = new ArrayList<>(5);
    private List<Range> mRangeArrayList = new ArrayList<>(5);
    private OnMentionInputListener mOnMentionInputListener;
    private boolean mIsSelected = false;
    private Range mLastSelectedRange;
    /**
     * 是否激活@功能, 当调用{@link #setOnMentionInputListener(OnMentionInputListener)}后, 自动激活
     */
    private boolean enableMention = false;

    public ExEditText(Context context) {
        super(context);
    }

    public ExEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 为TextView设置显示@效果
     */
    public static void checkMentionSpannable(TextView textView, String content, List<String> allMention) {
        if (textView == null) {
            return;
        }
        if (allMention.isEmpty() || TextUtils.isEmpty(content)) {
            textView.setText(content);
            textView.setMovementMethod(ArrowKeyMovementMethod.getInstance());
            return;
        }

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        int lastMentionIndex = -1;

        for (String mention : allMention) {
            Matcher matcher = Pattern.compile("@" + mention).matcher(content);

            while (matcher.find()) {
                String mentionText = matcher.group();
                int start;
                if (lastMentionIndex != -1) {
                    start = content.indexOf(mentionText, lastMentionIndex);
                } else {
                    start = content.indexOf(mentionText);
                }
                int end = start + mentionText.length();
                spannableStringBuilder.setSpan(new MentionSpan(mentionText), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        textView.setText(spannableStringBuilder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
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

    public void setInputText(String text) {
        setText(text);
        setSelection(TextUtils.isEmpty(text) ? 0 : text.length());
        //checkEdit(true);
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

        //L.e("call: onTouchEvent([event])-> canVerticalScroll:" + canVerticalScroll(this));

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
        if (enableMention) {
            checkMentionString();
        }
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        if (!enableMention) {
            return;
        }

        //avoid infinite recursion after calling setSelection()
        if (mLastSelectedRange != null && mLastSelectedRange.isEqual(selStart, selEnd)) {
            return;
        }

        //if user cancel a selection of mention string, reset the state of 'mIsSelected'
        Range closestRange = getRangeOfClosestMentionString(selStart, selEnd);
        if (closestRange != null && closestRange.to == selEnd) {
            mIsSelected = false;
        }

        Range nearbyRange = getRangeOfNearbyMentionString(selStart, selEnd);
        //if there is no mention string nearby the cursor, just skip
        if (nearbyRange == null) {
            return;
        }

        //forbid cursor located in the mention string.
        if (selStart == selEnd) {
            setSelection(nearbyRange.getAnchorPosition(selStart));
        } else {
            if (selEnd < nearbyRange.to) {
                setSelection(selStart, nearbyRange.to);
            }
            if (selStart > nearbyRange.from) {
                setSelection(nearbyRange.from, selEnd);
            }
        }
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

    //------------------------------------@功能支持-----------------------------------------//

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

    /**
     * 监听键盘按键
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new HackInputConnection(super.onCreateInputConnection(outAttrs), true, this);
    }

    /**
     * 添加需要@的人
     */
    public void addMention(String mention) {
        addMention(mention, true);
    }

    /**
     * @param auto 如果为true时, 自动上屏和去重
     */
    public void addMention(String mention, boolean auto) {
        if (auto) {
            if (isContains(mention)) {
                deleteLast();
            } else {
                mAllMention.add(mention);
                insert(mention + ' ');
            }
        } else {
            mAllMention.add(mention);
        }
    }

    /**
     * 在指定的位置插入text
     */
    public void insert(int where, CharSequence text) {
        if (text == null) {
            return;
        }
        int start = getSelectionStart();
        int end = getSelectionEnd();
        int length = text.length();

        if (start == end) {
            getText().insert(where, text);
        } else {
            getText().replace(start, end, text, 0, length);
            setSelection(start + length, start + length);
        }
    }

    public void insert(CharSequence text) {
        insert(getSelectionStart(), text);
    }

    /**
     * 是否已经添加了
     */
    public boolean isContains(String mention) {
        return mAllMention.contains(mention);
    }

    public void removeMention(String mention) {
        mAllMention.remove(mention);
    }

    /**
     * 删除最后一个字符,用来当@的人, 已经存在时,调用
     */
    public void deleteLast() {
        Editable text = getText();
        if (text != null && text.length() > 0) {
            text.delete(text.length() - 1, text.length());
        }
    }

    public List<String> getAllMention() {
        return mAllMention;
    }

    public void setOnMentionInputListener(OnMentionInputListener onMentionInputListener) {
        enableMention = true;
        if (mOnMentionInputListener == null) {
            //监听@字符输入
            addTextChangedListener(new MentionTextWatcher());
            //为@span添加点击事件支持
            //setMovementMethod(LinkMovementMethod.getInstance());
        }
        mOnMentionInputListener = onMentionInputListener;
    }

    private Range getRangeOfClosestMentionString(int selStart, int selEnd) {
        if (mRangeArrayList == null) {
            return null;
        }
        for (Range range : mRangeArrayList) {
            if (range.contains(selStart, selEnd)) {
                return range;
            }
        }
        return null;
    }

    private Range getRangeOfNearbyMentionString(int selStart, int selEnd) {
        if (mRangeArrayList == null) {
            return null;
        }
        for (Range range : mRangeArrayList) {
            if (range.isWrappedBy(selStart, selEnd)) {
                return range;
            }
        }
        return null;
    }

    //检查@span
    private void checkMentionString() {
        //reset state
        mIsSelected = false;
        if (mRangeArrayList != null) {
            mRangeArrayList.clear();
        }

        Editable spannableText = getText();
        if (mAllMention.isEmpty() || spannableText == null || TextUtils.isEmpty(spannableText.toString())) {
            mAllMention.clear();
            return;
        }

        //remove previous spans
        MentionSpan[] oldSpans = spannableText.getSpans(0, spannableText.length(), MentionSpan.class);
        for (MentionSpan oldSpan : oldSpans) {
            spannableText.removeSpan(oldSpan);
        }

        //find mention string and color it
        int lastMentionIndex = -1;
        String text = spannableText.toString();

        //筛选一下, 防止被删除了, 缺还在List中
        List<String> mentions = new ArrayList<>();
        for (String mention : mAllMention) {
            Matcher matcher = Pattern.compile("@" + mention).matcher(text);

            boolean isFind = false;
            while (matcher.find()) {
                isFind = true;
                String mentionText = matcher.group();
                int start;
                if (lastMentionIndex != -1) {
                    start = text.indexOf(mentionText, lastMentionIndex);
                } else {
                    start = text.indexOf(mentionText);
                }
                int end = start + mentionText.length();
                spannableText.setSpan(new MentionSpan(text.substring(start, end)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                lastMentionIndex = end;
                //record all mention-string's position
                mRangeArrayList.add(new Range(start, end));
            }
            if (isFind) {
                mentions.add(mention);
            }
        }
        mAllMention.clear();
        mAllMention.addAll(mentions);
    }

    /**
     * Listener for '@' character
     */
    public interface OnMentionInputListener {
        /**
         * call when '@' character is inserted into EditText, 当输入@字符之后, 会回调
         */
        void onMentionCharacterInput();
    }

    /**
     * {@code @}文本样式Span
     */
    public static class MentionSpan extends ClickableSpan {

        String mention;

        public MentionSpan(String mention) {
            this.mention = mention;
        }

        @Override
        public void onClick(View widget) {
            L.i("onClick @: " + mention);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            //设置背景色
            ds.bgColor = RApplication.getApp().getResources().getColor(R.color.theme_color_primary_dark_tran3);
            //设置前景色
            //ds.setColor(getResources().getColor(R.color.theme_color_accent));
        }
    }

    /**
     * 用来监听@字符输入
     */
    private class MentionTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int index, int i1, int count) {
            if (count == 1 && !TextUtils.isEmpty(charSequence)) {
                char mentionChar = charSequence.toString().charAt(index);
                if ('@' == mentionChar && mOnMentionInputListener != null) {
                    mOnMentionInputListener.onMentionCharacterInput();
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //文本内容为空时, 解决光标不显示的BUG
            if (editable.length() > 0) {
                setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                setMovementMethod(getDefaultMovementMethod());
            }
        }
    }

    /**
     * 用来处理按下删除键的时候,删除整个@文本内容
     */
    private class HackInputConnection extends InputConnectionWrapper {
        private ExEditText editText;

        public HackInputConnection(InputConnection target, boolean mutable, ExEditText editText) {
            super(target, mutable);
            this.editText = editText;
        }

        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {
            return super.commitText(text, newCursorPosition);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                int selectionStart = editText.getSelectionStart();
                int selectionEnd = editText.getSelectionEnd();
                Range closestRange = getRangeOfClosestMentionString(selectionStart, selectionEnd);
                if (closestRange == null) {
                    mIsSelected = false;
                    return super.sendKeyEvent(event);
                }
                //if mention string has been selected or the cursor is at the beginning of mention string, just use default action(delete)
                if (mIsSelected || selectionStart == closestRange.from) {
                    mIsSelected = false;
                    return super.sendKeyEvent(event);
                } else {
                    //select the mention string
                    mIsSelected = true;
                    mLastSelectedRange = closestRange;
                    setSelection(closestRange.to, closestRange.from);
                    return super.sendKeyEvent(event);//调用此方法, 删除整个@文本内容
                }
                //return true;
            }
            return super.sendKeyEvent(event);
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            if (beforeLength == 1 && afterLength == 0) {
                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                        && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
            }
            return super.deleteSurroundingText(beforeLength, afterLength);
        }
    }

    /**
     * {@code @}文本的开始位置和结束位置
     */
    private class Range {
        int from;
        int to;

        public Range(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public boolean isWrappedBy(int start, int end) {
            return (start > from && start < to) || (end > from && end < to);
        }

        public boolean contains(int start, int end) {
            return from <= start && to >= end;
        }

        public boolean isEqual(int start, int end) {
            return (from == start && to == end) || (from == end && to == start);
        }

        public int getAnchorPosition(int value) {
            if ((value - from) - (to - value) > 0) {
                return to;
            } else {
                return from;
            }
        }
    }
}
