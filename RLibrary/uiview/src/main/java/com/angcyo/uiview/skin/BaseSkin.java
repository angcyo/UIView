package com.angcyo.uiview.skin;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.angcyo.uiview.R;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/01 15:37
 * 修改人员：Robi
 * 修改时间：2017/04/01 15:37
 * 修改备注：
 * Version: 1.0.0
 */
public class BaseSkin extends SkinImpl {

    protected Context mContext;

    public BaseSkin(Context context) {
        mContext = context;
    }

    @Override
    public int getThemeColor() {
        return ContextCompat.getColor(mContext, R.color.theme_color_primary);
    }

    @Override
    public int getThemeSubColor() {
        return ContextCompat.getColor(mContext, R.color.theme_color_primary_dark);
    }

    @Override
    public int getThemeDarkColor() {
        return ContextCompat.getColor(mContext, R.color.theme_color_primary_dark_tran);
    }
}
