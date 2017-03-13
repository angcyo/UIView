package com.angcyo.uiview.recycler.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.angcyo.uiview.view.UIIViewImpl;

/**
 * Created by angcyo on 2016-12-18.
 */

public class ItemShowStateLayout extends FrameLayout implements IShowState {

    int mShowState = IShowState.NORMAL;
    private View loadingView, emptyView, errorView, nonetView;

    public ItemShowStateLayout(Context context) {
        this(context, null);
    }

    public ItemShowStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setShowState(mShowState);
    }

    private void initView() {
        for (int i = 0; i < getChildCount(); i++) {
            final View view = getChildAt(i);
            if (TextUtils.equals("error", (CharSequence) view.getTag())) {
                errorView = view;
            } else if (TextUtils.equals("empty", (CharSequence) view.getTag())) {
                emptyView = view;
            } else if (TextUtils.equals("loading", (CharSequence) view.getTag())) {
                loadingView = view;
            } else if (TextUtils.equals("nonet", (CharSequence) view.getTag())) {
                nonetView = view;
            }
        }
    }

    private void showView(View view) {
        if (view != null) {
//            errorView.setVisibility(errorView == view ? View.VISIBLE : View.INVISIBLE);
//            emptyView.setVisibility(emptyView == view ? View.VISIBLE : View.INVISIBLE);
//            loadingView.setVisibility(loadingView == view ? View.VISIBLE : View.INVISIBLE);
//            nonetView.setVisibility(nonetView == view ? View.VISIBLE : View.INVISIBLE);

            safeSetVisibility(errorView, errorView == view ? View.VISIBLE : View.INVISIBLE);
            safeSetVisibility(emptyView, emptyView == view ? View.VISIBLE : View.INVISIBLE);
            safeSetVisibility(loadingView, loadingView == view ? View.VISIBLE : View.INVISIBLE);
            safeSetVisibility(nonetView, nonetView == view ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private void safeSetVisibility(final View view, final int visibility) {
        if (view != null) {
            if (view.getVisibility() == View.VISIBLE) {
                ViewCompat.animate(view).scaleX(1.2f).scaleY(1.2f).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(visibility);
                    }
                }).setInterpolator(new DecelerateInterpolator()).setDuration(UIIViewImpl.DEFAULT_ANIM_TIME).start();
            } else {
                view.setVisibility(visibility);
            }
        }
    }

    public void animToHide(final Runnable endRunnable) {
        ViewCompat.animate(this).scaleX(1.2f).scaleY(1.2f).alpha(0).withEndAction(new Runnable() {
            @Override
            public void run() {
                endRunnable.run();
                setVisibility(GONE);
            }
        }).setInterpolator(new DecelerateInterpolator()).setDuration(UIIViewImpl.DEFAULT_ANIM_TIME).start();
    }

    @Override
    public int getShowState() {
        return mShowState;
    }

    @Override
    public void setShowState(int showState) {
        initView();
        if (showState != mShowState) {
            mShowState = showState;
            if (showState == IShowState.LOADING) {
                showView(loadingView);
            } else if (showState == IShowState.ERROR) {
                showView(errorView);
            } else if (showState == IShowState.EMPTY) {
                showView(emptyView);
            } else if (showState == IShowState.NONET) {
                showView(nonetView);
            } else {
                showView(this);
            }
        }
    }
}
