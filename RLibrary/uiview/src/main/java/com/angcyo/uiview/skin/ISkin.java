package com.angcyo.uiview.skin;

import android.graphics.drawable.Drawable;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：皮肤接口
 * 创建人员：Robi
 * 创建时间：2017/04/01 15:14
 * 修改人员：Robi
 * 修改时间：2017/04/01 15:14
 * 修改备注：
 * Version: 1.0.0
 */
public interface ISkin {

    /**
     * 皮肤的名称
     */
    String skinName();

    int getThemeColor();

    int getThemeSubColor();

    int getThemeDarkColor();

    /**
     * 波纹无限制, 默认是个圆
     */
    Drawable getThemeBackgroundSelector();

    /**
     * 波纹有限制
     */
    Drawable getThemeMaskBackgroundSelector();
}
