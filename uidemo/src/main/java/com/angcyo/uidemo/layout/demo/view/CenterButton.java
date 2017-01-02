package com.angcyo.uidemo.layout.demo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by angcyo on 2016-12-30 22:58.
 */
public class CenterButton extends ImageView implements View.OnClickListener {

    boolean mChecked = false;

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    View.OnClickListener mOnClickListener;
    OnCheckedChangeListener mOnCheckedChangeListener;

    public CenterButton(Context context) {
        super(context);
    }

    public CenterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CenterButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        setChecked(!isChecked());
        if (mOnClickListener != null) {
            mOnClickListener.onClick(v);
        }
    }

    //    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        T_.show("touch");
//        super.onTouchEvent(event);
//        return true;
//    }


    public interface OnCheckedChangeListener {
        void onCheckedChanged(CenterButton buttonView, boolean isChecked);
    }
}
