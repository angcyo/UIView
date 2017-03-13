package com.angcyo.uiview.recycler.widget;

/**
 * Created by angcyo on 2016-12-18.
 */

public interface ILoadMore {
    /**
     * 正常
     */
    int NORMAL = 0;
    /**
     * 没有更多
     */
    int NO_MORE = 1;
    /**
     * 正在装载更多
     */
    int LOAD_MORE = 2;
    /**
     * 装载出错
     */
    int LOAD_ERROR = 3;

    int getLoadState();

    void setLoadState(int state);
}
