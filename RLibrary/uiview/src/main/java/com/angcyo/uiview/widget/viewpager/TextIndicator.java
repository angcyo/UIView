package com.angcyo.uiview.widget.viewpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.angcyo.uiview.github.utilcode.utils.SpannableStringUtils;

/**
 * 类的描述：1/6 这样的ViewPager 指示器
 * 创建人员：Robi
 * 创建时间：2016/12/17 10:58 ~
 */
public class TextIndicator extends AppCompatTextView implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private int maxCount, currentCount;

    public TextIndicator(Context context) {
        super(context);
    }

    public TextIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setupViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        initView();
    }

    public TextIndicator setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
        updateText();
        return this;
    }

    private void updateText() {
        if (currentCount > maxCount) {
            setText(SpannableStringUtils.getBuilder(currentCount + "")
                    .setForegroundColor(Color.RED)
                    .append("/" + maxCount).create());
        } else {
            setText(currentCount + "/" + maxCount);
        }
    }

    public TextIndicator setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        updateText();
        return this;
    }

    public TextIndicator initIndicator(int currentCount, int maxCount) {
        this.maxCount = maxCount;
        this.currentCount = currentCount;
        updateText();
        return this;
    }

    public TextIndicator initIndicator(int maxCount, final EditText editText) {
        initIndicator(TextUtils.isEmpty(editText.getText()) ? 0 : editText.length(), maxCount);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setCurrentCount(s.length());
            }
        });
        return this;
    }

    private void initView() {
        PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter == null) {
            setVisibility(INVISIBLE);
        } else {
            setVisibility(VISIBLE);
            setText((mViewPager.getCurrentItem() + 1) + "/" + adapter.getCount());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        initView();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
