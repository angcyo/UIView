package com.angcyo.demo;

import com.angcyo.demo.uiview.DemoIView;
import com.angcyo.uiview.base.UIActivity;

public class DemoActivity extends UIActivity {
    @Override
    protected void onLoadView() {
        mUIContainer.startView(new DemoIView());
    }
}
