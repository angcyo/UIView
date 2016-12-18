package com.angcyo.uiview.recycler;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.angcyo.uiview.R;
import com.angcyo.uiview.github.load.AVLoadingIndicatorView;

/**
 * Created by angcyo on 2016-12-18._
 */

public class ItemLoadMoreLayout extends RelativeLayout implements ILoadMore {

    int loadState = ILoadMore.NORMAL;
    AVLoadingIndicatorView mLoadingIndicatorView;
    private View loadView, noMoreView, errorView;

    public ItemLoadMoreLayout(Context context) {
        super(context);
    }

    public ItemLoadMoreLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemLoadMoreLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemLoadMoreLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        for (int i = 0; i < getChildCount(); i++) {
            final View view = getChildAt(i);
            if (TextUtils.equals("error_view", (CharSequence) view.getTag())) {
                errorView = view;
            } else if (TextUtils.equals("no_more_view", (CharSequence) view.getTag())) {
                noMoreView = view;
            } else if (TextUtils.equals("load_view", (CharSequence) view.getTag())) {
                loadView = view;
                mLoadingIndicatorView = (AVLoadingIndicatorView) loadView.findViewById(R.id.load_view);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public int getLoadState() {
        return loadState;
    }

    @Override
    public void setLoadState(int state) {
        if (loadState != state) {
            loadState = state;
            if (loadState == ILoadMore.NORMAL) {
                showView(loadView);
            } else if (loadState == ILoadMore.LOAD_ERROR) {
                showView(errorView);
            } else if (loadState == ILoadMore.LOAD_MORE) {
                showView(loadView);
            } else if (loadState == ILoadMore.NO_MORE) {
                showView(noMoreView);
            }
        }
    }

    private void showView(View view) {
        if (view != null) {
            errorView.setVisibility(errorView == view ? View.VISIBLE : View.INVISIBLE);
            noMoreView.setVisibility(noMoreView == view ? View.VISIBLE : View.INVISIBLE);
            if (loadView == view) {
                loadView.setVisibility(View.VISIBLE);
//                mLoadingIndicatorView.show();
            } else {
                loadView.setVisibility(View.INVISIBLE);
//                mLoadingIndicatorView.hide();
            }
        }
    }
}
