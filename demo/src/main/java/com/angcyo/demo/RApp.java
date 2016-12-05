package com.angcyo.demo;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.angcyo.uiview.RApplication;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/05 15:00
 * 修改人员：Robi
 * 修改时间：2016/12/05 15:00
 * 修改备注：
 * Version: 1.0.0
 */
public class RApp extends RApplication {

    private static RApp app;

    public static RApp getApp() {
        if (app == null) {
            throw new IllegalArgumentException("RApp 初始化了吗?");
        }
        return app;
    }

    public static String getIMEI() {
        return ((TelephonyManager) getApp().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
