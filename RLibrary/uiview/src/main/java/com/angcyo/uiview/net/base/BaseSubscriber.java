package com.angcyo.uiview.net.base;

import com.angcyo.library.utils.L;

import rx.Subscriber;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/01 14:54
 * 修改人员：Robi
 * 修改时间：2016/12/01 14:54
 * 修改备注：
 * Version: 1.0.0
 */
public class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onStart() {
        super.onStart();
        L.e("");
    }

    @Override
    public void onCompleted() {
        L.e("");
    }

    @Override
    public void onError(Throwable e) {
        onResult(false, null, e.getMessage());
    }

    @Override
    public void onNext(T t) {
        onResult(true, t, "请求成功");
    }

    public void onResult(boolean success, T bean, String msg) {

    }
}
