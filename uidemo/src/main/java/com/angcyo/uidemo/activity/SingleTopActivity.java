package com.angcyo.uidemo.activity;

public class SingleTopActivity extends BaseLaunchModeActivity {

    @Override
    public void onFabClick() {
        SingleTopActivity.launcher(SingleTopActivity.this, SingleTopActivity.class);
    }
}
