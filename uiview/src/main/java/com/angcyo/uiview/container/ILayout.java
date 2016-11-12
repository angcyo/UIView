package com.angcyo.uiview.container;

import android.view.View;

import com.angcyo.uiview.view.IView;

/**
 * 可以添加IView, 必须实现的接口
 * Created by angcyo on 2016-11-12.
 */

public interface ILayout {

    /**
     * 开始一个IView, 返回创建的View, 执行动画
     */
    View startIView(IView iView, boolean needAnim);

    /**
     * 移除View
     */
    void finishIView(View view, boolean needAnim);

    /**
     * 显示一个View
     */
    void showIView(View view, boolean needAnim);

    /**
     * 替换一个View
     */
    void replaceIView(IView iView, boolean needAnim);

    /**
     * 隐藏一个View
     */
    void hideIView(View view, boolean needAnim);
}
