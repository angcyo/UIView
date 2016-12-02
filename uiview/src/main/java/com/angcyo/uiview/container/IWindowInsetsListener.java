package com.angcyo.uiview.container;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：当窗口需要插入装饰空间时,回调. 比如显示键盘,显示状态栏的时候.
 * 创建人员：Robi
 * 创建时间：2016/12/02 15:40
 * 修改人员：Robi
 * 修改时间：2016/12/02 15:40
 * 修改备注：
 * Version: 1.0.0
 */

/**
 */
public interface IWindowInsetsListener {
    void onWindowInsets(int insetLeft, int insetTop, int insetRight, int insetBottom);
}
