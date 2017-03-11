package com.angcyo.uiview.mvp.view;

import android.support.annotation.NonNull;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/05 10:53
 * 修改人员：Robi
 * 修改时间：2016/12/05 10:53
 * 修改备注：
 * Version: 1.0.0
 */
public interface IBaseView {
    /**
     * 开始加载请求
     */
    void onRequestStart();

    /**
     * 完成加载请求
     */
    void onRequestFinish();

    /**
     * 操作被取消
     */
    void onRequestCancel();

    /**
     * 请求错误
     */
    void onRequestError(int code, @NonNull String msg);
}
