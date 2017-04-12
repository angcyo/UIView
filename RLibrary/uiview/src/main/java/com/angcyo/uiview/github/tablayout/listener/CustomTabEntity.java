package com.angcyo.uiview.github.tablayout.listener;

import android.support.annotation.DrawableRes;

public interface CustomTabEntity {
    String getTabTitle();

    @DrawableRes
    int getTabSelectedIcon();

    CustomTabEntity setTabSelectedIcon(@DrawableRes int res);

    @DrawableRes
    int getTabUnselectedIcon();

    CustomTabEntity setTabUnselectedIcon(@DrawableRes int res);

    boolean isShowArrow();

    boolean isHomeNavigation();

    boolean isShowBackground();
}
