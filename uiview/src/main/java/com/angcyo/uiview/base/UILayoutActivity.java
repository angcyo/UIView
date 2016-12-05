package com.angcyo.uiview.base;

import android.Manifest;

import com.angcyo.uiview.container.ILayout;
import com.angcyo.uiview.container.UILayoutImpl;
import com.angcyo.uiview.utils.T;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Created by angcyo on 2016-11-12.
 */

public abstract class UILayoutActivity extends StyleActivity {

    protected ILayout mLayout;
    protected RxPermissions mRxPermissions;

    @Override
    protected void onCreateView() {
        mLayout = new UILayoutImpl(this);
        //mLayout.getLayout().setFitsSystemWindows(true);
        setContentView(mLayout.getLayout());

        mRxPermissions = new RxPermissions(this);
        mRxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        )
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.granted) {
                            //T.show(UILayoutActivity.this, "权限允许");
                        } else {
                            T.show(UILayoutActivity.this, "权限拒绝");
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        if (mLayout.requestBackPressed()) {
            super.onBackPressed();
        }
    }
}
