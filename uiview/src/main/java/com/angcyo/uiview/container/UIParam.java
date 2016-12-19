package com.angcyo.uiview.container;

import android.os.Bundle;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：启动时候的布局参数
 * 创建人员：Robi
 * 创建时间：2016/12/19 10:17
 * 修改人员：Robi
 * 修改时间：2016/12/19 10:17
 * 修改备注：
 * Version: 1.0.0
 */
public class UIParam {
    public boolean mAnim = true;
    public Bundle mBundle;
    public boolean mAsync = true;

    public UIParam(boolean anim, boolean async, Bundle bundle) {
        mAnim = anim;
        mBundle = bundle;
        mAsync = async;
    }

    public UIParam(boolean anim, boolean async) {
        this(anim, async, null);
    }

    public UIParam(boolean anim) {
        mAnim = anim;
    }

    public UIParam() {
    }
}
