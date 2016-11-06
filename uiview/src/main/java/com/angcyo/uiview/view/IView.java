package com.angcyo.uiview.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.angcyo.uiview.container.UIContainer;
import com.angcyo.uiview.model.TitleBarPattern;

/**
 * Created by angcyo on 2016-11-05.
 */

public interface IView {
    /**
     * 不需要标题栏,请返回null, 请在实现的时候, 做好缓存
     */
    TitleBarPattern loadTitleBar(Context context);

    /**
     * 显示内容,
     * 注意:如果使用 inflate 方式填充试图, 第三个参数 请设置为 false, 否则返回的view == container.
     */
    View loadContentView(Context context, UIContainer uiContainer, FrameLayout container, LayoutInflater inflater);

    void onViewCreate();

    void onViewLoad();

    void onViewShow();

    void onViewHide();

    void onViewUnload();
}
