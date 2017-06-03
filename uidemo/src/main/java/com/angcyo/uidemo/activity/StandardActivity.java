package com.angcyo.uidemo.activity;

public class StandardActivity extends BaseLaunchModeActivity {

    @Override
    public void onFabClick() {
        StandardActivity.launcher(StandardActivity.this, StandardActivity.class);
    }

}
