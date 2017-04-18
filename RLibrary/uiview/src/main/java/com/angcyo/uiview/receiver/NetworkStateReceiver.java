package com.angcyo.uiview.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.RApplication;
import com.angcyo.uiview.github.utilcode.utils.NetworkUtils;
import com.hwangjr.rxbus.RxBus;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：网络状态监听广播
 * 创建人员：Robi
 * 创建时间：2017/03/10 10:58
 * 修改人员：Robi
 * 修改时间：2017/03/10 10:58
 * 修改备注：
 * Version: 1.0.0
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    private static NetworkUtils.NetworkType netType = NetworkUtils.NetworkType.NETWORK_NO;

    /**
     * 获取网络类型
     */
    public static NetworkUtils.NetworkType getNetType() {
        if (netType == NetworkUtils.NetworkType.NETWORK_NO) {
            netType = NetworkUtils.getNetworkType(RApplication.getApp());
        }
        return netType;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        netType = NetworkUtils.getNetworkType(activeNetworkInfo);

        if (netType == NetworkUtils.NetworkType.NETWORK_WIFI) {
            RxBus.get().post("update", "Wifi");
        }
        L.w("网络变化至:" + netType + " " + (netType.value() > 0 ? "有网" : "没网"));
    }
}
