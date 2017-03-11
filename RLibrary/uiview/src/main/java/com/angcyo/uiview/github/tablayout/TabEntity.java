package com.angcyo.uiview.github.tablayout;


import com.angcyo.uiview.github.tablayout.listener.CustomTabEntity;

public class TabEntity implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;
    /**
     * 显示下拉弹窗箭头
     */
    public boolean showArrow = false;

    public boolean isHomeNavigation = false;

    public boolean isShowBackground = true;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    public TabEntity(String title, int selectedIcon, int unSelectedIcon, boolean showArrow) {
        this.selectedIcon = selectedIcon;
        this.title = title;
        this.unSelectedIcon = unSelectedIcon;
        this.showArrow = showArrow;
    }

    public TabEntity(boolean isHomeNavigation, String title, int selectedIcon, int unSelectedIcon) {
        this.selectedIcon = selectedIcon;
        this.title = title;
        this.unSelectedIcon = unSelectedIcon;
        this.isHomeNavigation = isHomeNavigation;
    }

    public TabEntity(String title, int selectedIcon, int unSelectedIcon,
                     boolean showArrow, boolean isHomeNavigation, boolean isShowBackground) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
        this.showArrow = showArrow;
        this.isHomeNavigation = isHomeNavigation;
        this.isShowBackground = isShowBackground;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }

    @Override
    public boolean isShowArrow() {
        return showArrow;
    }

    @Override
    public boolean isHomeNavigation() {
        return isHomeNavigation;
    }

    @Override
    public boolean isShowBackground() {
        return isShowBackground;
    }
}
