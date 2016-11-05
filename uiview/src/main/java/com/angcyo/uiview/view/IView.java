package com.angcyo.uiview.view;

import android.view.LayoutInflater;
import android.view.View;

import com.angcyo.uiview.container.UIContainer;
import com.angcyo.uiview.model.TitleBarPattern;

/**
 * Created by angcyo on 2016-11-05.
 */

public interface IView {
    /**
     * 不需要标题栏,请返回null
     */
    TitleBarPattern loadTitleBar();

    /**
     * 显示内容
     */
    View loadContentView(UIContainer container, LayoutInflater inflater);

    void onViewCreate();

    void onViewLoad();

    void onViewShow();

    void onViewHide();

    void onViewUnload();
}
