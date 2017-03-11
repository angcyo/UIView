package com.angcyo.uiview.container;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.R;
import com.angcyo.uiview.base.UIActivity;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.resources.AnimUtil;
import com.angcyo.uiview.view.IView;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 提供类似 内容管理
 * Created by angcyo on 2016-11-05.
 */
@Deprecated
public class UIContainer extends ContainerWrapper {
    private static final String TAG = "UIContainer";

    /**
     * 已经追加到内容层的View
     */
    protected Stack<View> mAttachViews = new Stack<>();

    /**
     * 需要追加到内容层的IView
     */
    protected LinkedList<IView> mNeedAddViews = new LinkedList<>();

    /**
     * 已经追加到内容层的IView
     */
    protected Stack<IView> mAddedIViews = new Stack<>();

    protected UIActivity mUIActivity;

    /**
     * 是否装载了标题, 需要为内容偏移标题栏的高度
     */
    protected boolean isLoadTitleBar = false;

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

    public void attachToActivity(UIActivity activity) {
        mUIActivity = activity;
    }

    /**
     * 开始显示试图,可以多次调用, 如果界面还没有添加到Window, 会保存起来, 只有一并添加
     */
    public void startView(IView view) {
        if (view == null) {
            throw new NullPointerException();
        }

        mNeedAddViews.addLast(view);

        if (isAttachedToWindow) {
            loadView();
        }
    }

    @Override
    protected void loadOnAttachedToWindow() {
        loadView();
    }

    /**
     * 退出
     */
    public void onBackPressed() {
        if (mAttachViews.size() <= 1) {
            mUIActivity.onBackPressed();
            return;
        }
        final View pop = mAttachViews.pop();
        mAddedIViews.pop();

        pop.animate().translationY(pop.getMeasuredHeight()).setDuration(1000).start();
        mContentLayout.removeView(pop);
        loadTitleBar(mContext, mAddedIViews.lastElement());

    }

    /**
     * 是否可以退出
     */
    public boolean canBack() {
        if (mAddedIViews.size() <= 1) {
            return true;
        } else {
            onBackPressed();
        }
        return false;
    }

    //------------------------------私有方法--------------------------//

    /**
     * 开始布局
     */
    private void loadView() {
        if (mNeedAddViews.isEmpty()) {
            L.w(TAG, "没有需要添加的视图");
            return;
        }

        final IView first = mNeedAddViews.removeFirst();
        loadTitleBar(mContext, first);
        loadContent(mContext, first);

        mAddedIViews.push(first);
    }

    /**
     * 加载内容
     */
    protected void loadContent(Context context, IView first) {
//        final View view = first.inflateContentView(context, this, mContentLayout, LayoutInflater.from(context));
//        if (mContentLayout == view) {
//            mAttachViews.push(mContentLayout.getChildAt(mContentLayout.getChildCount() - 1));
//        } else {
//            mAttachViews.push(view);
//        }
    }

    /**
     * 装载标题栏
     */
    protected void loadTitleBar(Context context, IView view) {
        isLoadTitleBar = false;
        if (loadTitleBar) {
            final TitleBarPattern titleBarPattern = view.loadTitleBar(context);
            if (titleBarPattern != null) {
                mTitleBarLayout.setVisibility(VISIBLE);

                int startColor = mTitleBarBGColor;

                int titleBarBGColor = titleBarPattern.mTitleBarBGColor;
                if (titleBarBGColor == -1) {
                    titleBarBGColor = context.getResources().getColor(R.color.theme_color_primary);
                }

                mTitleBarBGColor = titleBarBGColor;

                mUITitleBarContainer.onAttachToLayout(mContentLayout);
                mUITitleBarContainer.setTitleBarPattern(titleBarPattern);

                AnimUtil.startArgb(mTitleBarLayout, startColor, titleBarBGColor);

                isLoadTitleBar = true;
            } else {
                mTitleBarLayout.setVisibility(GONE);
            }

        } else {
            mTitleBarLayout.setVisibility(GONE);
        }

        //修复内容布局的高度区域
        int height = ContainerWrapper.getTitleBarHeight(mUIActivity);
        final int animTime = 300;
        if (isLoadTitleBar) {
            AnimUtil.startValue(mContentLayout.getPaddingTop(), height, animTime, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mContentLayout.setPadding(0, (Integer) animation.getAnimatedValue(), 0, 0);
                }
            });
        } else {
            AnimUtil.startValue(mContentLayout.getPaddingTop(), 0, animTime, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mContentLayout.setPadding(0, (Integer) animation.getAnimatedValue(), 0, 0);
                }
            });
        }
    }

}
