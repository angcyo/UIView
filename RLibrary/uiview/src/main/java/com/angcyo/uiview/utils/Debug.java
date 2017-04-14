package com.angcyo.uiview.utils;

import android.text.TextUtils;

import com.angcyo.library.utils.L;

import java.util.Stack;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/03/31 09:57
 * 修改人员：Robi
 * 修改时间：2017/03/31 09:57
 * 修改备注：
 * Version: 1.0.0
 */
public class Debug {

    private static Stack<Long> sStartTime = new Stack<>();
    private static String lastTag;
    private static long lastTime;

    public static void logTimeStart(String log) {
        long time = System.currentTimeMillis();
        sStartTime.push(time);
        L.e(log + " --start:" + time);
    }

    public static void logTimeEnd(String log) {
        final long endTime = System.currentTimeMillis();
        Long pop = sStartTime.pop();
        L.e(log + " ----end:" + (endTime - pop) / 1000 + "秒" + (endTime - pop) % 1000);
    }

    public static void log(String tag, String log) {
        String[] contents = L.wrapperContent(6, tag, log);
        String t = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        if (TextUtils.equals(tag, lastTag)) {
            long time = System.currentTimeMillis();
            if (time - lastTime > 1000) {
                L.printDefault(6, t, headString + msg);
                lastTime = time;
            }
        } else {
            L.printDefault(6, t, headString + msg);
            lastTag = tag;
        }
    }
}
