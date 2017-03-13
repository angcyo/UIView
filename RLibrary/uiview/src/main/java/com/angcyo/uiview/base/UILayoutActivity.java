package com.angcyo.uiview.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.container.ILayout;
import com.angcyo.uiview.container.UILayoutImpl;
import com.angcyo.uiview.container.UIParam;
import com.angcyo.uiview.utils.T_;
import com.angcyo.uiview.view.IView;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by angcyo on 2016-11-12.
 */

public abstract class UILayoutActivity extends StyleActivity {

    protected ILayout mLayout;
    protected RxPermissions mRxPermissions;
    private boolean destroyed = false;

    @Override
    protected void onCreateView() {
        mLayout = new UILayoutImpl(this);
        setContentView(mLayout.getLayout());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

        mRxPermissions = new RxPermissions(this);
        mRxPermissions.requestEach(needPermissions())
                .map(new Func1<Permission, String>() {
                    @Override
                    public String call(Permission permission) {
                        if (permission.granted) {
                            return permission.name + "1";
                        }
                        return permission.name + "0";
                    }
                })
                .scan(new Func2<String, String, String>() {
                    @Override
                    public String call(String s, String s2) {
                        return s + "\n" + s2;
                    }
                })
                .last()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        L.e("\n" + UILayoutActivity.this.getClass().getSimpleName() + " 权限状态-->\n"
                                + s.replaceAll("1", " 允许").replaceAll("0", " 拒绝"));
                        if (s.contains("0")) {
                            finishSelf();
                            notifyAppDetailView();
                            T_.show("必要的权限被拒绝!");
                        } else {
                            onLoadView();
                        }
                    }
                });
//                .subscribe(new Action1<Permission>() {
//                    @Override
//                    public void call(Permission permission) {
//                        if (permission.granted) {
//                            T.show(UILayoutActivity.this, "权限允许");
//                        } else {
//                            notifyAppDetailView();
//                            T.show(UILayoutActivity.this, "权限被拒绝");
//                        }
//                    }
//                });

    }

    protected String[] needPermissions() {
        return new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS
        };
    }

    public void finishSelf() {
        finish();
        //overridePendingTransition(R.anim.base_tran_to_top, R.anim.base_tran_to_bottom);
    }

    protected abstract void onLoadView();

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

    public boolean isDestroyedCompatible() {
        if (Build.VERSION.SDK_INT >= 17) {
            return isDestroyedCompatible17();
        } else {
            return destroyed || super.isFinishing();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyed = true;
    }

    @TargetApi(17)
    private boolean isDestroyedCompatible17() {
        return super.isDestroyed();
    }

    protected void showKeyboard(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            if (getCurrentFocus() == null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                imm.showSoftInput(getCurrentFocus(), 0);
            }
        } else {
            if (getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
