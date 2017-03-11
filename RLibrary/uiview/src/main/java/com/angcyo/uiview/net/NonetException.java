package com.angcyo.uiview.net;

import com.angcyo.uiview.R;
import com.angcyo.uiview.RApplication;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/15 10:42
 * 修改人员：Robi
 * 修改时间：2016/12/15 10:42
 * 修改备注：
 * Version: 1.0.0
 */
public class NonetException extends RuntimeException {
    @Override
    public String getMessage() {
        return RApplication.getApp().getString(R.string.no_network);
    }
}
