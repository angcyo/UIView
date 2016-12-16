package com.angcyo.uiview.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.angcyo.uiview.R;
import com.angcyo.uiview.resources.ResUtil;

/**
 * 用来控制状态栏的padding
 * Created by angcyo on 2016-11-05.
 */

public class TitleBarLayout extends RelativeLayout {

    public TitleBarLayout(Context context) {
        super(context);
    }

    public TitleBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Context context = getContext();
        if (context instanceof Activity) {
            if (ResUtil.isLayoutFullscreen((Activity) context)) {
                int statusBarHeight = getResources().getDimensionPixelSize(R.dimen.status_bar_height);
                setClipToPadding(false);
                setClipChildren(false);
                setPadding(getPaddingLeft(),
                        getPaddingTop() + statusBarHeight,
                        getPaddingRight(), getPaddingBottom());
            }
        }
    }
}
