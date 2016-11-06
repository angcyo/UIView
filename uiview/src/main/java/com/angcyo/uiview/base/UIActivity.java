package com.angcyo.uiview.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.angcyo.uiview.container.UIContainer;

/**
 * Created by angcyo on 2016-11-05.
 */

public abstract class UIActivity extends AppCompatActivity {

    protected UIContainer mUIContainer;

    protected abstract void onLoadView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadActivityStyle();
        initWindow();
        mUIContainer = new UIContainer(this);
        mUIContainer.attachToActivity(this);
        setContentView(mUIContainer, new ViewGroup.LayoutParams(-1, -1));
        initUIActivity();
        if (savedInstanceState == null) {

        } else {

        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        onLoadView();
    }


    @Override
    public void onBackPressed() {
        if (mUIContainer.canBack()) {
            super.onBackPressed();
        }
    }

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
}
