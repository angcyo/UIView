package com.angcyo.uidemo.layout;

import android.Manifest;
import android.content.Intent;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.RCrashHandler;
import com.angcyo.uiview.base.UIBaseView;
import com.angcyo.uiview.base.UILayoutActivity;

public class LayoutDemoActivity extends UILayoutActivity {
    @Override
    protected void onLoadView(Intent intent) {
//        mLayout.getLayout().setBackgroundColor(Color.RED);

        mLayout.getLayout().post(new Runnable() {
            @Override
            public void run() {
                final View view = mLayout.getLayout();
                final DisplayMetrics metrics = getResources().getDisplayMetrics();
                Rect outRect = new Rect();
                view.getHitRect(outRect);

                Rect outRect2 = new Rect();
                view.getWindowVisibleDisplayFrame(outRect2);
                Rect outRect3 = new Rect();
                view.getGlobalVisibleRect(outRect3);
                Rect outRect4 = new Rect();
                view.getLocalVisibleRect(outRect4);
                Rect outRect5 = new Rect();
                view.getDrawingRect(outRect5);

                L.i(view.getMeasuredWidth() + " " + view.getMeasuredHeight());
            }
        });

        mLayout.startIView(new DemoListUIView2()
                .setEnableClipMode(UIBaseView.ClipMode.CLIP_START));

        RCrashHandler.checkCrash(mLayout);

        checkPermissions();
    }

    @Override
    protected String[] needPermissions() {
        return new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
        };
    }
}
