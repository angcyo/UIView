package com.angcyo.uidemo;

import android.content.Intent;

import com.angcyo.uidemo.uiview.EditTextViewDemo;
import com.angcyo.uiview.base.UILayoutActivity;

public class DemoActivity extends UILayoutActivity {
    @Override
    protected void onLoadView(Intent intent) {
        mLayout.startIView(new EditTextViewDemo());
    }
}
