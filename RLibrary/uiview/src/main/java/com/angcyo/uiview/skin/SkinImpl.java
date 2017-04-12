package com.angcyo.uiview.skin;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.angcyo.uiview.R;
import com.angcyo.uiview.RApplication;
import com.angcyo.uiview.resources.ResUtil;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/01 15:14
 * 修改人员：Robi
 * 修改时间：2017/04/01 15:14
 * 修改备注：
 * Version: 1.0.0
 */
public class SkinImpl implements ISkin {
    @Override
    public String skinName() {
        return "Default";
    }

    @Override
    public int getThemeColor() {
        return Color.BLACK;
    }

    @Override
    public int getThemeSubColor() {
        return Color.GRAY;
    }

    @Override
    public int getThemeDarkColor() {
        return Color.DKGRAY;
    }

    @Override
    public Drawable getThemeTranBackgroundSelector() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ResUtil.generateRippleDrawable(getThemeSubColor());
        } else {
            return ResUtil.generateBgDrawable(getThemeSubColor(), Color.TRANSPARENT);
        }
    }

    /**
     * @Inherited
     */
    @Override
    public Drawable getThemeTranMaskBackgroundSelector() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ResUtil.generateRippleMaskDrawable(getThemeSubColor());
        } else {
            return ResUtil.generateBgDrawable(getThemeSubColor(), Color.TRANSPARENT);
        }
    }

    @Override
    public Drawable getThemeMaskBackgroundRoundSelector() {
        return ResUtil.generateRoundRippleMaskDrawable(RApplication.getApp()
                        .getResources()
                        .getDimensionPixelOffset(R.dimen.base_round_little_radius),
                Color.WHITE, getThemeDarkColor(), getThemeSubColor()
        );
    }

    @Override
    public Drawable getThemeBackgroundSelector() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ResUtil.generateRippleDrawable(getThemeSubColor());
        } else {
            return ResUtil.generateBgDrawable(getThemeDarkColor(), getThemeSubColor());
        }
    }

    @Override
    public Drawable getThemeBackgroundSelector(int pressColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ResUtil.generateRippleDrawable(getThemeColor());
        } else {
            return ResUtil.generateBgDrawable(pressColor, getThemeColor());
        }
    }

    @Override
    public Drawable getThemeMaskBackgroundSelector() {
        return null;
    }

    @Override
    public Drawable getThemeMaskBackgroundSelector(int pressColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ResUtil.generateRippleMaskDrawable(getThemeSubColor());
        } else {
            return ResUtil.generateBgDrawable(pressColor, getThemeSubColor());
        }
    }


    @Override
    public Drawable getThemeMaskBackgroundRoundSelector(int pressColor) {
        return ResUtil.generateRoundRippleMaskDrawable(RApplication.getApp()
                        .getResources()
                        .getDimensionPixelOffset(R.dimen.base_round_little_radius),
                Color.WHITE, pressColor, getThemeSubColor()
        );
    }
}
