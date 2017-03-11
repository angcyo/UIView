package com.angcyo.uiview.base;

import android.support.annotation.NonNull;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/11/29 18:00
 * 修改人员：Robi
 * 修改时间：2016/11/29 18:00
 * 修改备注：
 * Version: 1.0.0
 */
public abstract class UIBaseDataView extends UIBaseView {
    /**
     * 最后一次加载数据的时间
     */
    protected long mLastLoadTime = 0;

    @NonNull
    @Override
    protected LayoutState getDefaultLayoutState() {
        return LayoutState.LOAD;
    }
}
