package com.angcyo.uiview.utils;

import com.angcyo.uiview.RApplication;

/**
 * Created by angcyo on 2016-12-23.
 */

public class T_ {
    public static void show(final CharSequence text) {
        //T2.show(RApplication.getApp(), text, T2.TYPE_NONE);
        info(text);
    }

    public static void info(final CharSequence text) {
        T2.show(RApplication.getApp(), text, T2.TYPE_INFO);
    }

    public static void ok(final CharSequence text) {
        T2.show(RApplication.getApp(), text, T2.TYPE_OK);
    }

    public static void error(final CharSequence text) {
        T2.show(RApplication.getApp(), text, T2.TYPE_ERROR);
    }
}
