package com.angcyo.uiview.base;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.angcyo.uiview.container.ILayout;
import com.angcyo.uiview.container.UILayoutImpl;
import com.angcyo.uiview.container.UIParam;
import com.angcyo.uiview.utils.T;
import com.angcyo.uiview.view.IView;
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
        setContentView(mLayout.getLayout());

        mRxPermissions = new RxPermissions(this);
        mRxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CAMERA)
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.granted) {
                            //T.show(UILayoutActivity.this, "权限允许");
                        } else {
                            notifyAppDetailView();
                            T.show(UILayoutActivity.this, "权限被拒绝");
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

    public void startIView(final IView iView, boolean needAnim) {
        mLayout.startIView(iView, new UIParam(needAnim));
    }

    public void startIView(IView iView) {
        startIView(iView, true);
    }

    public void notifyAppDetailView() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((UILayoutImpl) mLayout).onActivityResult(requestCode, resultCode, data);
    }
}
