package com.angcyo.uiview.skin;

import android.content.Context;

import com.angcyo.uiview.container.ILayout;

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
public class SkinHelper {

    static ISkin mSkin;

    public static void init(Context context) {
        if (mSkin == null) {
            createDefaultSkin(context);
        }
    }

    private static void createDefaultSkin(Context context) {
        mSkin = new BaseSkin(context);
    }

    /**
     * 获取当前的皮肤
     */
    public static ISkin getSkin() {
        if (mSkin == null) {
            throw new NullPointerException("please call SkinHelper#init method.");
        }
        return mSkin;
    }

    public static void setSkin(ISkin iSkin) {
        SkinHelper.mSkin = iSkin;
    }

    /**
     * 改变皮肤
     */
    public static void changeSkin(ISkin skin, ILayout layout) {
        SkinHelper.mSkin = skin;
        if (layout != null) {
            layout.onSkinChanged(skin);
        }
    }
}
