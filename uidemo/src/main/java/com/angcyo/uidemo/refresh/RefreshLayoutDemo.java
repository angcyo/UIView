package com.angcyo.uidemo.refresh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.container.UILayoutImpl;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.model.ViewPattern;
import com.angcyo.uiview.rsen.RefreshLayout;

import butterknife.BindView;
import butterknife.OnClick;

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


    @BindView(R.id.content_layout)
    UILayoutImpl mUILayout;
    private NormalView mNormalView;
    private WebviewView mWebviewView;
    private RecyclerView mRecyclerView;
    private GridView mGridView;
    private StaggerView mStaggerView;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_refresh_layout);
    }

    @Override
    public void onViewShow(Bundle bundle) {
        super.onViewShow(bundle);
        ((UILayoutImpl) mILayout).lock();
    }

    @Override
    protected void initOnShowContentLayout() {
        mNormalView = new NormalView();
        mWebviewView = new WebviewView();
        mRecyclerView = new RecyclerView();
        mGridView = new GridView();
        mStaggerView = new StaggerView();
        mUILayout.setEnableSwipeBack(false);
        mUILayout.startIView(mNormalView);
        mUILayout.startIView(mWebviewView);
        mUILayout.startIView(mRecyclerView);
        mUILayout.startIView(mGridView);
        mUILayout.startIView(mStaggerView);
        mUILayout.showIView(mNormalView);
    }

    @Override
    protected TitleBarPattern getTitleBar() {
        return super.getTitleBar().setTitleString("刷新控件测试");
    }

    @OnClick({R.id.in_normal_view, R.id.in_web_view, R.id.in_recycler_view, R.id.in_grid_view, R.id.in_stagger_view,
            R.id.refresh_view, R.id.pull_view, R.id.finish_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.in_normal_view:
                mUILayout.showIView(mNormalView.getView());
                break;
            case R.id.in_web_view:
                mUILayout.showIView(mWebviewView.getView());
                break;
            case R.id.in_recycler_view:
                mUILayout.showIView(mRecyclerView.getView());
                break;
            case R.id.in_grid_view:
                mUILayout.showIView(mGridView.getView());
                break;
            case R.id.in_stagger_view:
                mUILayout.showIView(mStaggerView.getView());
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
