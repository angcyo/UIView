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

    View startIView(IView iView);

    /**
     * 移除View
     */
    void finishIView(View view, boolean needAnim);

    void finishIView(View view);

    void finishIView(IView iview, boolean needAnim);//2016-12-14

    void finishIView(IView iview);//2016-12-14

    /**
     * 显示一个View
     */
    void showIView(View view, boolean needAnim);

    void showIView(View view);

    /**
     * 替换一个View
     */
    void replaceIView(IView iView, boolean needAnim);

    void replaceIView(IView iView);

    /**
     * 隐藏一个View
     */
    void hideIView(View view, boolean needAnim);

    void hideIView(View view);

    /**
     * 返回Layout
     */
    View getLayout();

    /**
     * 请求返回
     */
    boolean requestBackPressed();
}
