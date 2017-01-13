package com.angcyo.uiview.net;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.utils.T_;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

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
public abstract class RSubscriber<T> extends Subscriber<T> {

    @Override
    public void onStart() {
        super.onStart();
        L.d("开始订阅->" + this.getClass().getSimpleName());
    }

    @Override
    public void onCompleted() {
        L.d("订阅完成->" + this.getClass().getSimpleName());
        onEnd();
    }

    @Override
    public void onNext(T bean) {
        L.d("订阅onNext->" + this.getClass().getSimpleName());
    }

    @Override
    final public void onError(Throwable e) {
        L.e("----------------------------------------异常处理----------------------------------------");
        int errorCode;
        String errorMsg;

        if (e instanceof UnknownHostException ||
                e instanceof SocketTimeoutException ||
                e instanceof SocketException ||
                e instanceof NonetException) {
            L.e(e.getMessage());
            errorMsg = "请检查网络连接!";
            errorCode = 40000;
        } else if (e instanceof JsonParseException || e instanceof JsonMappingException) {
            errorMsg = "恐龙君打了个盹，请稍后再试!"; //   "数据解析错误:" + e.getMessage();
            errorCode = -40001;
        } else if (e instanceof RException) {
            errorMsg = e.getMessage();
            errorCode = ((RException) e).getCode();
        } else {
            errorMsg = "未知错误:" + e.getMessage();
            errorCode = -40000;
        }

        e.printStackTrace();

        onError(errorCode, errorMsg);
        if (L.LOG_DEBUG) {
            T_.show("[" + errorCode + "]" + errorMsg);
        }
        L.e("-----------------------------------------End-------------------------------------------");
    }

    /**
     * 统一错误处理
     */
    public void onError(int code, String msg) {
        L.d("订阅异常->" + this.getClass().getSimpleName() + " " + msg);
        onEnd();
    }

    /**
     * 不管是成功订阅,还是异常,都会执行的方法
     */
    public void onEnd() {

    }
}
