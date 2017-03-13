package com.angcyo.uiview.recycler.widget;

/**
 * 显示状态
 * Created by angcyo on 2017-03-11.
 */

public interface IShowState {
    /**
     * 显示数据
     */
    int NORMAL = 0;
    /**
     * 显示数据加载中
     */
    int LOADING = 1;

    /**
     * 显示数据为空
     */
    int EMPTY = 2;

    /**
     * 显示数据异常
     */
    int ERROR = 3;

    /**
     * 显示无网络
     */
    int NONET = 4;

    int getShowState();

    void setShowState(int showState);
}
