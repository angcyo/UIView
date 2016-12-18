package com.angcyo.uidemo;

import com.angcyo.uidemo.uiview.EditTextViewDemo;
import com.angcyo.uiview.base.UILayoutActivity;

public class DemoActivity extends UILayoutActivity {
    @Override
    protected void onLoadView() {
        mLayout.startIView(new EditTextViewDemo());
    }
}
