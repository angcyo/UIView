package com.angcyo.demo;

import com.angcyo.demo.uiview.DemoIViewImpl;
import com.angcyo.uiview.base.UIActivity;

public class DemoActivity extends UIActivity {
    @Override
    protected void onLoadView() {
        mUIContainer.startView(new DemoIViewImpl());
    }
}
