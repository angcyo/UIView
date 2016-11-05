package com.angcyo.uiview.container;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.angcyo.uiview.R;
import com.angcyo.uiview.resources.ResUtil;

/**
 * Created by angcyo on 2016-11-05.
 */

public class UITitleBarContainer extends FrameLayout {

    protected UIContainer mUIContainer;
    protected ViewGroup mTitleBarLayout;
    protected ViewGroup mLeftControlLayout;
    protected ViewGroup mCenterControlLayout;
    protected ViewGroup mRightControlLayout;
    protected ImageView mBackImageView;
    protected TextView mTitleView;

    public UITitleBarContainer(Context context) {
        super(context);
        initTitleBar(context);
    }

    public UITitleBarContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTitleBar(context);
    }

    private void initTitleBar(Context context) {
        if (context instanceof Activity) {
            if (ResUtil.isLayoutFullscreen((Activity) context)) {
                setPadding(getPaddingLeft(),
                        getPaddingTop() + getResources().getDimensionPixelSize(R.dimen.status_bar_height),
                        getPaddingRight(), getPaddingBottom());
            }
        }

        final View root = LayoutInflater.from(context).inflate(R.layout.base_title_layout, this);
        mTitleBarLayout = (ViewGroup) root.findViewById(R.id.base_title_bar_layout);
        mLeftControlLayout = (ViewGroup) root.findViewById(R.id.base_left_control_layout);
        mCenterControlLayout = (ViewGroup) root.findViewById(R.id.base_center_control_layout);
        mRightControlLayout = (ViewGroup) root.findViewById(R.id.base_right_control_layout);
        mBackImageView = (ImageView) root.findViewById(R.id.base_back_image_view);
        mTitleView = (TextView) root.findViewById(R.id.base_title_view);
    }

    public void onAttachToContainer(UIContainer container) {
        mUIContainer = container;
    }
}
