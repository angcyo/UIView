package com.angcyo.uiview.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.angcyo.uiview.container.ILayout;
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
    View loadContentView(Context context, ILayout iLayout, FrameLayout container, LayoutInflater inflater);

    /**
     * 当loadContentView完成之后会调用
     */
    void onViewCreate();

    void onViewLoad();

    void onViewShow();

    void onViewHide();

    void onViewUnload();

    /**
     * 开始动画
     */
    Animation loadStartAnimation();

    /**
     * 结束动画
     */
    Animation loadFinishAnimation();

    /**
     * 显示动画
     */
    Animation loadShowAnimation();

    /**
     * 结隐藏动画
     */
    Animation loadHideAnimation();

    /**
     * 其他View开始开始, 退出的动画
     */
    Animation loadOtherStartExitAnimation();

    /**
     * 其他View结束, 进入的动画
     */
    Animation loadOtherFinishEnterAnimation();


}
