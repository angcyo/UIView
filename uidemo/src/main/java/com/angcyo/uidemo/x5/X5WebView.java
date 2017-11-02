package com.angcyo.uidemo.x5;

import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.WebView;

public class X5WebView extends WebView {

    public X5WebView(Context arg0) {
        this(arg0, null);
    }

    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        setBackgroundColor(85621);
        this.getView().setClickable(true);
        X5Utils.initWebSetting(this);
    }
}
