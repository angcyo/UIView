package com.angcyo.uiview.container;

import android.os.Bundle;
import android.view.View;

import com.angcyo.uiview.view.IView;

/**
 * 可以添加IView, 必须实现的接口
 * Created by angcyo on 2016-11-12.
 */

public interface ILayout<T extends UIParam> {

    /**
     * 开始一个IView, 返回创建的View, 执行动画
     */
    void startIView(IView iView, T param);//2016-12-19

    void startIView(IView iView);

    /**
     * 移除View
     */
    void finishIView(View view, boolean needAnim);

    void finishIView(View view);

    void finishIView(IView iview, boolean needAnim);//2016-12-14

    void finishIView(IView iview, final UIParam param);//2016-12-29

    void finishIView(IView iview);//2016-12-14

    void finishIView(IView iview, boolean needAnim, boolean quiet);//2016-12-15

    void finishIView(Class<?> clz);//2017-3-15 根据类型, 关闭页面

    /**
     * 显示一个View
     */
    void showIView(View view, boolean needAnim);

    void showIView(View view);

    void showIView(final View view, final boolean needAnim, final Bundle bundle);//2016-12-15

    void showIView(IView iview, boolean needAnim);//2016-12-16

    void showIView(IView iview);//2016-12-16

    void showIView(final IView iview, final boolean needAnim, final Bundle bundle);//2016-12-16

    /**
     * 替换一个View
     */
    void replaceIView(IView iView, final UIParam param);//2016-12-19

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

    boolean requestBackPressed(final UIParam param);//2017-1-3

    /**
     * 结束所有的IView, 不会有动画执行, 最上层的IVew 也不会有 生命周期的回调
     * {@link ILayout#finishIView(IView, boolean, boolean)} 类似此方法quiet=true 的情况
     */
    void finishAll();//2016-12-16

    /**
     * 结束所有的IView,
     * 参考
     * {@link ILayout#finishAll()}
     *
     * @param keepLast true 会保留最上层的IView
     */
    void finishAll(boolean keepLast);//2016-12-16

    /**
     * 强制退出
     */
    void finish();//2016-12-16
}
