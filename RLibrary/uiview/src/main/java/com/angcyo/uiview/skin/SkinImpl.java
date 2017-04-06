package com.angcyo.uiview.skin;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;

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
        return "";
    }

    @Override
    public int getThemeColor() {
        return 0;
    }

    @Override
    public int getThemeSubColor() {
        return 0;
    }

    @Override
    public int getThemeDarkColor() {
        return 0;
    }

    @Override
    public Drawable getThemeBackgroundSelector() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ResUtil.generateRippleDrawable(getThemeColor());
        } else {
            return ResUtil.generateBgDrawable(getThemeColor(), Color.TRANSPARENT);
        }
    }

    @Override
    public Drawable getThemeMaskBackgroundSelector() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ResUtil.generateRippleMaskDrawable(getThemeColor());
        } else {
            return ResUtil.generateBgDrawable(getThemeColor(), Color.TRANSPARENT);
        }
    }
}
