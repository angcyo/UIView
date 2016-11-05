package com.angcyo.uiview.container;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;

import com.angcyo.uiview.R;
import com.angcyo.uiview.resources.AnimUtil;
import com.angcyo.uiview.resources.ResUtil;

/**
 * Created by angcyo on 2016-11-05.
 */

public class UIContainer extends ContainerWrapper {
    private static final String TAG = "UIContainer";

    public UIContainer(Context context) {
        super(context);
    }

    public UIContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UIContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UIContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void loadOnAttachedToWindow() {

    }


//    //------------------------------私有方法--------------------------//
//
//    /**
//     * 在这个方法里面, 可以通过 {@link #getMeasuredWidth()} {@link #getMeasuredHeight()} 获取布局的宽高
//     */
//    private void loadOnAttachedToWindow(Context context) {
//        loadTitleBar(context);
//    }

    protected void loadTitleBar(Context context) {
        if (loadTitleBar) {
            final Resources resources = getResources();
            final int titleBarBGColor = resources.getColor(R.color.theme_color_primary);

            int height;
            if (ResUtil.isLayoutFullscreen((Activity) context)) {
                height = resources.getDimensionPixelSize(R.dimen.title_bar_height);
            } else {
                height = resources.getDimensionPixelSize(R.dimen.action_bar_height);
            }
            mTitleBarLayout.setMinimumHeight(height);

            mUITitleBarContainer = new UITitleBarContainer(context);
            mUITitleBarContainer.onAttachToContainer(this);

            mTitleBarLayout.addView(mUITitleBarContainer, new LayoutParams(-1, -2));

//            mTitleBarLayout.setBackgroundColor(resources.getColor(R.color.theme_color_primary));
            AnimUtil.startArgb(mTitleBarLayout, mBackgroundColor, titleBarBGColor);
        }
    }

}
