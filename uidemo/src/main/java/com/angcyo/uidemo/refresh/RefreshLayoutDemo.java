package com.angcyo.uidemo.refresh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.container.ContentLayout;
import com.angcyo.uiview.container.UILayoutImpl;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.model.ViewPattern;
import com.angcyo.uiview.rsen.RefreshLayout;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/05 17:54
 * 修改人员：Robi
 * 修改时间：2016/12/05 17:54
 * 修改备注：
 * Version: 1.0.0
 */
public class RefreshLayoutDemo extends UIContentView {


    UILayoutImpl mUILayout;
    private NormalUIView mNormalUIView;
    private WebviewUIView mWebviewUIView;
    private RecyclerUIView mRecyclerUIView;
    private GridUIView mGridUIView;
    private StaggerUIView mStaggerUIView;

    @Override
    protected void inflateContentLayout(ContentLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_refresh_layout);
    }

    @Override
    public void onViewShow(Bundle bundle) {
        super.onViewShow(bundle);
        mUILayout.lock();
    }

    @Override
    public void onViewUnload() {
        super.onViewUnload();
    }

    @Override
    protected void initOnShowContentLayout() {
        mUILayout = v(R.id.content_layout);
        mNormalUIView = new NormalUIView();
        mWebviewUIView = new WebviewUIView();
        mRecyclerUIView = new RecyclerUIView();
        mGridUIView = new GridUIView();
        mStaggerUIView = new StaggerUIView();
        mUILayout.setEnableSwipeBack(false);
        mUILayout.startIView(mNormalUIView);
        mUILayout.startIView(mWebviewUIView);
        mUILayout.startIView(mRecyclerUIView);
        mUILayout.startIView(mGridUIView);
        mUILayout.startIView(mStaggerUIView);
        mUILayout.showIView(mNormalUIView);

        click(R.id.in_normal_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshLayoutDemo.this.onClick(v);
            }
        });
        click(R.id.in_web_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshLayoutDemo.this.onClick(v);
            }
        });
        click(R.id.in_recycler_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshLayoutDemo.this.onClick(v);
            }
        });
        click(R.id.in_grid_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshLayoutDemo.this.onClick(v);
            }
        });
        click(R.id.in_stagger_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshLayoutDemo.this.onClick(v);
            }
        });
        click(R.id.refresh_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshLayoutDemo.this.onClick(v);
            }
        });
        click(R.id.pull_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshLayoutDemo.this.onClick(v);
            }
        });
        click(R.id.finish_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshLayoutDemo.this.onClick(v);
            }
        });
    }

    @Override
    protected TitleBarPattern getTitleBar() {
        return super.getTitleBar().setTitleString("刷新控件测试");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.in_normal_view:
                mUILayout.showIView(mNormalUIView.getView());
                break;
            case R.id.in_web_view:
                mUILayout.showIView(mWebviewUIView.getView());
                break;
            case R.id.in_recycler_view:
                mUILayout.showIView(mRecyclerUIView.getView());
                break;
            case R.id.in_grid_view:
                mUILayout.showIView(mGridUIView.getView());
                break;
            case R.id.in_stagger_view:
                mUILayout.showIView(mStaggerUIView.getView());
                break;
            case R.id.refresh_view:
                safeRefresh();
                break;
            case R.id.pull_view:
                safePull();
                break;
            case R.id.finish_view:
                safeFinish();
                break;
        }
    }

    private RefreshLayout getRefreshLayout() {
        ViewPattern lastShowViewPattern = mUILayout.getLastViewPattern();
        if (lastShowViewPattern != null) {
            RefreshLayout refreshLayout = (RefreshLayout) lastShowViewPattern.mView.findViewWithTag("refresh");
            return refreshLayout;
        }
        return null;
    }

    private void safeRefresh() {
        RefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.setRefreshState(RefreshLayout.TOP);
        }
    }

    private void safePull() {
        RefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.setRefreshState(RefreshLayout.BOTTOM);
        }
    }

    private void safeFinish() {
        RefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.setRefreshEnd();
        }
    }
}
