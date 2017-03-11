package com.angcyo.uiview.mvp.presenter;


import com.angcyo.uiview.mvp.view.IBaseView;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/05 10:56
 * 修改人员：Robi
 * 修改时间：2016/12/05 10:56
 * 修改备注：
 * Version: 1.0.0
 */
public interface IBasePresenter<T extends IBaseView> {
    void onLoadData();

    void onLoadMoreData(int page);

    void onUnload();

    void onLoad();

    void bindView(T baseView);

    /**
     * 取消订阅后
     */
    void onCancel();
}
