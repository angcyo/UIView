package com.angcyo.uidemo.activity;

public class SingleTaskActivity extends BaseLaunchModeActivity {

    @Override
    public void onFabClick() {
        SingleTaskActivity.launcher(SingleTaskActivity.this, SingleTaskActivity.class);
    }

}
