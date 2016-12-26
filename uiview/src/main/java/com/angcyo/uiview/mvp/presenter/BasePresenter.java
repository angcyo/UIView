package com.angcyo.uiview.mvp.presenter;


import android.support.annotation.NonNull;

import com.angcyo.uiview.mvp.view.IBaseView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/05 11:10
 * 修改人员：Robi
 * 修改时间：2016/12/05 11:10
 * 修改备注：
 * Version: 1.0.0
 */
public class BasePresenter<T extends IBaseView> implements IBasePresenter<T> {

    protected CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    protected T mBaseView;

    @Override
    public void onLoadData() {

    }

    @Override
    public void onLoadMoreData(int page) {

    }

    @Override
    public void onUnload() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void bindView(T baseView) {
        mBaseView = baseView;
    }

    @Override
    public void onCancel() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }

    protected void add(@NonNull final Subscription s) {
        mCompositeSubscription.add(s);
    }
}
