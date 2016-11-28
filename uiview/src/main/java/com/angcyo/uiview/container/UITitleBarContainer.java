package com.angcyo.uiview.container;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.angcyo.uiview.R;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.resources.ResUtil;

import java.util.ArrayList;

/**
 * 标题栏的封装
 * Created by angcyo on 2016-11-05.
 */

public class UITitleBarContainer extends FrameLayout {

    protected ILayout mILayout;
    protected ViewGroup mTitleBarLayout;
    protected LinearLayout mLeftControlLayout;
    protected ViewGroup mCenterControlLayout;
    protected LinearLayout mRightControlLayout;
    protected ImageView mBackImageView;
    protected TextView mTitleView;

    protected TitleBarPattern mTitleBarPattern;
    protected boolean isAttachedToWindow;

    protected ArrayList<View> mLeftViews = new ArrayList<>();
    protected ArrayList<View> mRightViews = new ArrayList<>();


    public UITitleBarContainer(Context context) {
        super(context);
        initTitleBar(context);
    }

    public UITitleBarContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTitleBar(context);
    }

    private void initTitleBar(Context context) {
//        if (context instanceof Activity) {
//            if (ResUtil.isLayoutFullscreen((Activity) context)) {
//                setPadding(getPaddingLeft(),
//                        getPaddingTop() + getResources().getDimensionPixelSize(R.dimen.status_bar_height),
//                        getPaddingRight(), getPaddingBottom());
//            }
//        }

        final View root = LayoutInflater.from(context).inflate(R.layout.base_title_layout, this);
        mTitleBarLayout = (ViewGroup) root.findViewById(R.id.base_title_bar_layout);
        mLeftControlLayout = (LinearLayout) root.findViewById(R.id.base_left_control_layout);
        mCenterControlLayout = (ViewGroup) root.findViewById(R.id.base_center_control_layout);
        mRightControlLayout = (LinearLayout) root.findViewById(R.id.base_right_control_layout);
        mBackImageView = (ImageView) root.findViewById(R.id.base_back_image_view);
        mTitleView = (TextView) root.findViewById(R.id.base_title_view);

        if (context instanceof Activity) {
            if (ResUtil.isLayoutFullscreen((Activity) context)) {
                int statusBarHeight = getResources().getDimensionPixelSize(R.dimen.status_bar_height);
                mTitleBarLayout.setClipToPadding(false);
                mTitleBarLayout.setClipChildren(false);
                mTitleBarLayout.setPadding(getPaddingLeft(),
                        getPaddingTop() + statusBarHeight,
                        getPaddingRight(), getPaddingBottom());
                ViewGroup.LayoutParams layoutParams = mTitleBarLayout.getLayoutParams();
                layoutParams.height += statusBarHeight;
                mTitleBarLayout.setLayoutParams(layoutParams);
            }
        }
    }

    public void onAttachToLayout(ILayout container) {
        mILayout = container;
    }

    /**
     * 标题栏的信息
     */
    public void setTitleBarPattern(TitleBarPattern titleBarPattern) {
        mTitleBarPattern = titleBarPattern;
        if (isAttachedToWindow) {
            loadTitleBar();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(new Runnable() {
            @Override
            public void run() {
                isAttachedToWindow = true;
                loadTitleBar();
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isAttachedToWindow = false;
    }

    private void loadTitleBar() {
        if (mTitleBarPattern == null) {
            mLeftControlLayout.removeAllViews();
            mRightControlLayout.removeAllViews();
//            mCenterControlLayout.removeAllViews();
            setVisibility(GONE);
            return;
        }

        setVisibility(VISIBLE);

        int itemSize = getResources().getDimensionPixelSize(R.dimen.base_title_bar_item_size);
        int animTime = 300;

        setBackgroundColor(mTitleBarPattern.mTitleBarBGColor);

        /*返回按钮*/
        if (mTitleBarPattern.isShowBackImageView) {
            mBackImageView.setVisibility(VISIBLE);
            mBackImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mILayout != null) {
                        mILayout.requestBackPressed();
                    }
                }
            });
            ViewCompat.animate(mBackImageView).rotation(360)
                    .setInterpolator(new DecelerateInterpolator())
                    .setDuration(animTime).start();
        } else {
            mBackImageView.setVisibility(GONE);
        }
        /*标题*/
        mTitleView.setText(mTitleBarPattern.mTitleString);
        if (!TextUtils.isEmpty(mTitleBarPattern.mTitleString)) {
            ViewCompat.setTranslationY(mTitleView, -itemSize);
            ViewCompat.animate(mTitleView).translationY(0)
                    .setInterpolator(new DecelerateInterpolator())
                    .setDuration(animTime).start();
        }

        clearViews(mLeftControlLayout, mLeftViews);
        clearViews(mRightControlLayout, mRightViews);

        /*左边控制按钮*/
        fillViews(mLeftControlLayout, mTitleBarPattern.mLeftItems, mLeftViews);
        /*右边控制按钮*/
        fillViews(mRightControlLayout, mTitleBarPattern.mRightItems, mRightViews);

        /*控制按钮的动画*/
        animViews(mLeftViews, true);
        animViews(mRightViews, false);
    }

    private void animViews(ArrayList<View> views, boolean left) {
        int itemSize = getResources().getDimensionPixelSize(R.dimen.base_title_bar_item_size);
        int animTime = 300;
        int delayTime = 100;
        final int size = views.size();
        if (left) {
            for (int j = 0; j < size; j++) {
                final View view = views.get(j);
                ViewCompat.setTranslationX(view, itemSize);
                ViewCompat.animate(view).translationX(0)
                        .setInterpolator(new DecelerateInterpolator())
                        .setDuration(animTime).start();
            }
        } else {
            for (int i = 0; i < size; i++) {
                final View view = views.get(i);
                ViewCompat.setTranslationX(view, -itemSize);
                ViewCompat.animate(view).setStartDelay(delayTime * (size - i - 1)).translationX(0)
                        .setInterpolator(new DecelerateInterpolator())
                        .setDuration(animTime).start();
            }
        }
    }

    /**
     * 清空布局
     */
    private void clearViews(ViewGroup leftControlLayout, ArrayList<View> leftViews) {
        for (View view : leftViews) {
            leftControlLayout.removeView(view);
        }
    }

    /**
     * 填充左右控制按钮
     */
    private void fillViews(LinearLayout layout, ArrayList<TitleBarPattern.TitleBarItem> items, ArrayList<View> views) {
        if (items == null || items.isEmpty()) {
            return;
        }

        int itemSize = getResources().getDimensionPixelSize(R.dimen.base_title_bar_item_size);

        for (TitleBarPattern.TitleBarItem item : items) {
            View view;
            if (item.res == -1) {
                //不是图片, 就创建文本按钮
                view = createTextItem(item.text, item.listener);
            } else {
                //创建图片按钮
                view = createImageItem(item.res, item.listener);
            }
            layout.addView(view, new LinearLayout.LayoutParams(itemSize, -1));
            views.add(view);
        }
    }

    private ImageView createImageItem(@DrawableRes int res, View.OnClickListener listener) {
        ImageView item = new ImageView(getContext());
        item.setImageResource(res);
        item.setScaleType(ImageView.ScaleType.CENTER);
        item.setBackgroundResource(R.drawable.base_bg_selector);
        item.setOnClickListener(listener);
        if (listener == null) {
            item.setClickable(false);
        }
        return item;
    }


    private TextView createTextItem(String text, View.OnClickListener listener) {
        TextView item = new TextView(getContext());
        item.setText(text);
        item.setTextColor(Color.WHITE);
        item.setGravity(Gravity.CENTER);
        item.setBackgroundResource(R.drawable.base_bg_selector);
        item.setOnClickListener(listener);
        if (listener == null) {
            item.setClickable(false);
        }
        return item;
    }
}
