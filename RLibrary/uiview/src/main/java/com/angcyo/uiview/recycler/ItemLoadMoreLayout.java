package com.angcyo.uiview.recycler;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.angcyo.uiview.R;

/**
 * Created by angcyo on 2016-12-18.
 */

public class ItemLoadMoreLayout extends RelativeLayout implements ILoadMore {

    int loadState = ILoadMore.NORMAL;
    View mLoadingIndicatorView;
    private View loadView, noMoreView, errorView;

    public ItemLoadMoreLayout(Context context) {
        this(context, null);
    }

    public ItemLoadMoreLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLoadState(loadState);
    }

    private void initView() {
        for (int i = 0; i < getChildCount(); i++) {
            final View view = getChildAt(i);
            if (TextUtils.equals("error_view", (CharSequence) view.getTag())) {
                errorView = view;
            } else if (TextUtils.equals("no_more_view", (CharSequence) view.getTag())) {
                noMoreView = view;
            } else if (TextUtils.equals("load_view", (CharSequence) view.getTag())) {
                loadView = view;
                mLoadingIndicatorView = loadView.findViewById(R.id.load_view);
            }
        }
    }

//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        setLoadState(loadState);
//    }

    @Override
    public int getLoadState() {
        return loadState;
    }

    @Override
    public void setLoadState(int state) {
        initView();
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
            } else {
                loadView.setVisibility(View.INVISIBLE);
            }
        }
    }
}
