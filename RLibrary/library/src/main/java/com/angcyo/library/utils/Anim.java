package com.angcyo.library.utils;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/13 8:46
 * 修改人员：Robi
 * 修改时间：2016/12/13 8:46
 * 修改备注：
 * Version: 1.0.0
 */
public class Anim {
    /**
     * 上下抖动
     */
    public static void bounce(View target) {
        YoYo.with(Techniques.Bounce).interpolate(new DecelerateInterpolator()).duration(300).playOn(target);
    }

    /**
     * 左右跳动
     */
    public static void band(View target) {
        YoYo.with(Techniques.RubberBand).interpolate(new DecelerateInterpolator()).duration(300).playOn(target);
    }

    public static ViewPropertyAnimatorCompat anim(final View target) {
        return ViewCompat.animate(target).setDuration(300).setInterpolator(new DecelerateInterpolator());
    }
}
