package com.angcyo.uiview.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/02/21 10:24
 * 修改人员：Robi
 * 修改时间：2017/02/21 10:24
 * 修改备注：
 * Version: 1.0.0
 */
public class UI {
    public static void setViewHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
        if (view.getParent() != null) {
            view.getParent().requestLayout();
        }
    }

    public static void setViewWidth(View view, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        view.setLayoutParams(layoutParams);
        if (view.getParent() != null) {
            view.getParent().requestLayout();
        }
    }

    public static void setView(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        layoutParams.width = width;
        view.setLayoutParams(layoutParams);
        if (view.getParent() != null) {
            view.getParent().requestLayout();
        }
    }
}
