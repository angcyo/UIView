package com.angcyo.uidemo.activity;

public class SingleInstanceActivity extends BaseLaunchModeActivity {

    @Override
    public void onFabClick() {
        SingleInstanceActivity.launcher(SingleInstanceActivity.this, SingleInstanceActivity.class);
    }
}
