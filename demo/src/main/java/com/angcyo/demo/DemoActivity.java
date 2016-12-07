package com.angcyo.demo;

import com.angcyo.demo.uiview.EditTextViewDemo;
import com.angcyo.uiview.base.UILayoutActivity;

public class DemoActivity extends UILayoutActivity {
    @Override
    protected void onLoadView() {
        mLayout.startIView(new EditTextViewDemo());
    }
}
