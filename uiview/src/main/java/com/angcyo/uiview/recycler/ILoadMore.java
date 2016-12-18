package com.angcyo.uiview.recycler;

/**
 * Created by angcyo on 2016-12-18.
 */

public interface ILoadMore {
    int NORMAL = 0;
    int NO_MORE = 1;
    int LOAD_MORE = 2;
    int LOAD_ERROR = 3;

    int getLoadState();

    void setLoadState(int state);
}
