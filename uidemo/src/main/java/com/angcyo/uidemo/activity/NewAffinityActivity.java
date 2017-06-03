package com.angcyo.uidemo.activity;

public class NewAffinityActivity extends BaseLaunchModeActivity {

    @Override
    public void onFabClick() {
        NewAffinityActivity.launcher(NewAffinityActivity.this, NewAffinityActivity.class);
    }
}
