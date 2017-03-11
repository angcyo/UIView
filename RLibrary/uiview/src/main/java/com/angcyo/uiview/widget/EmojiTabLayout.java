package com.angcyo.uiview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.angcyo.uiview.R;


/**
 * Created by angcyo on 2016-12-31 00:11.
 */
public class EmojiTabLayout extends LinearLayout implements View.OnClickListener {

    //item的背景资源
    int mItemBackgroundRes = 0;
    //默认选中的位置
    int mCheckedIndex = -1;

    int mFirstCheckedIndex = 0;

    OnTabSelectorListener mOnTabSelectorListener;

    public EmojiTabLayout(Context context) {
        super(context);
    }

    public EmojiTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmojiTabLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setOrientation(HORIZONTAL);
        post(new Runnable() {
            @Override
            public void run() {
                setCheckedIndex(mFirstCheckedIndex, true);
            }
        });
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (!(child instanceof EmojiTabItemView)) {
            throw new IllegalArgumentException("need add EmojiTabItemView");
        }
        super.addView(child, index, params);
    }

    public void setCheckedIndex(int checkedIndex, boolean notify) {
        if (checkedIndex == mCheckedIndex) {
            if (notify && mOnTabSelectorListener != null) {
                mOnTabSelectorListener.onTabReselect(checkedIndex);
            }
        } else {
            mCheckedIndex = checkedIndex;
            for (int i = 0; i < getChildCount(); i++) {
                final EmojiTabItemView child = (EmojiTabItemView) getChildAt(i);
                child.setChecked(i == checkedIndex);
            }

            if (notify && mOnTabSelectorListener != null) {
                mOnTabSelectorListener.onTabSelect(checkedIndex);
            }
        }
    }

    public void addItem(@DrawableRes int ico) {
        EmojiTabItemView itemView = new EmojiTabItemView(getContext());
        itemView.setImageResource(ico);
        if (mItemBackgroundRes == 0) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_checked},
                    new ColorDrawable(getResources().getColor(R.color.default_base_bg_press)));
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed},
                    new ColorDrawable(getResources().getColor(R.color.default_base_bg_press)));
            stateListDrawable.addState(new int[]{}, new ColorDrawable(Color.TRANSPARENT));
            itemView.setBackground(stateListDrawable);
        } else {
            itemView.setBackgroundResource(mItemBackgroundRes);
        }
        addView(itemView, -1, -1);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        setCheckedIndex(indexOfChild(v), true);
    }

    public void setOnTabSelectorListener(OnTabSelectorListener onTabSelectorListener) {
        mOnTabSelectorListener = onTabSelectorListener;
    }

    public interface OnTabSelectorListener {
        void onTabSelect(int index);

        void onTabReselect(int index);
    }

    public static class EmojiTabItemView extends ImageView implements OnClickListener {

        boolean mChecked = false;

        private static final int[] CHECKED_STATE_SET = {
                android.R.attr.state_checked
        };

        View.OnClickListener mOnClickListener;
        OnCheckedChangeListener mOnCheckedChangeListener;

        public EmojiTabItemView(Context context) {
            super(context);
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            super.setOnClickListener(this);
            setScaleType(ScaleType.CENTER_INSIDE);
        }

        public void setChecked(boolean checked) {
            if (mChecked == checked) {
                return;
            }

            mChecked = checked;

            refreshDrawableState();

            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
            }
        }

        public boolean isChecked() {
            return mChecked;
        }

        @Override
        public int[] onCreateDrawableState(int extraSpace) {
            final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
            if (isChecked()) {
                mergeDrawableStates(drawableState, CHECKED_STATE_SET);
            }
            return drawableState;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            int size;

            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);

            if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
                size = Math.min(width, height);
            } else if (widthMode == MeasureSpec.EXACTLY) {
                size = width;
            } else if (heightMode == MeasureSpec.EXACTLY) {
                size = height;
            } else {
                size = (int) (getResources().getDisplayMetrics().density * 40);
            }

            setMeasuredDimension(size, size);
        }

        @Override
        public void setEnabled(boolean enabled) {
            super.setEnabled(enabled);
        }

        @Override
        public void setOnClickListener(OnClickListener l) {
            mOnClickListener = l;
        }

        public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
            mOnCheckedChangeListener = onCheckedChangeListener;
        }

        @Override
        public void onClick(View v) {
//            setChecked(!isChecked());
            if (mOnClickListener != null) {
                mOnClickListener.onClick(v);
            }
        }

        public interface OnCheckedChangeListener {
            void onCheckedChanged(EmojiTabItemView buttonView, boolean isChecked);
        }
    }
}
