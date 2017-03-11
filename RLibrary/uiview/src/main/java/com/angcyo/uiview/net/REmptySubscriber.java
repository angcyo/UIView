package com.angcyo.uiview.net;

import com.angcyo.library.utils.L;

import rx.Subscriber;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/15 11:53
 * 修改人员：Robi
 * 修改时间：2016/12/15 11:53
 * 修改备注：
 * Version: 1.0.0
 */
public class REmptySubscriber<T> extends Subscriber<T> {

    public static <T> REmptySubscriber<T> build() {
        return new REmptySubscriber<T>();
    }

    @Override
    public void onStart() {
        L.d("onStart: ");
    }

    @Override
    public void onCompleted() {
        L.d("Completed:");
    }

    @Override
    public void onError(Throwable e) {
        L.d("onError: " + e.getMessage());
        e.printStackTrace();
    }

    @Override
    public void onNext(T bean) {
        L.d("onNext: ");
    }

}
