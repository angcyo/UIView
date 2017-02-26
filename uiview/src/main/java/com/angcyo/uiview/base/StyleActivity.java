package com.angcyo.uiview.base;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.angcyo.uiview.R;

/**
 * Created by angcyo on 2016-11-12.
 */

public abstract class StyleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//TBS X5

        loadActivityStyle();
        initWindow();
        initUIActivity();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        onLoadView();//在权限通过后调用
        onCreateView();
    }

    protected abstract void onCreateView();

    protected void initUIActivity() {
    }

    /**
     * 基本样式
     */
    protected void loadActivityStyle() {
        final Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
    }

    /**
     * 窗口样式
     */
    protected void initWindow() {
        enableLayoutFullScreen();

        if (enableWindowAnim()) {
            //默认窗口动画
            getWindow().setWindowAnimations(R.style.WindowTranAnim);
        }
    }

    /**
     * 激活布局全屏, View 可以布局在 StatusBar 下面
     */
    protected void enableLayoutFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    protected boolean enableWindowAnim() {
        return false;
    }
}
