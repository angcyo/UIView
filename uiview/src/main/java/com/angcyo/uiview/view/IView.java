package com.angcyo.uiview.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.angcyo.uiview.container.ILayout;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.widget.UIViewPager;

/**
 * Created by angcyo on 2016-11-05.
 */

public interface IView {

    /**
     * 不需要标题栏,请返回null, 请在实现的时候, 做好缓存
     */
    @Deprecated
    TitleBarPattern loadTitleBar(Context context);

    /**
     * 显示内容,
     */
    View inflateContentView(AppCompatActivity activity, ILayout iLayout, FrameLayout container, LayoutInflater inflater);

    void loadContentView(View rootView);

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
    Animation loadOtherExitAnimation();

    /**
     * 其他View结束, 进入的动画
     */
    Animation loadOtherEnterAnimation();

    Animation loadOtherHideAnimation();

    Animation loadOtherShowAnimation();

    /**
     * 布局动画
     */
    Animation loadLayoutAnimation();

    /**
     * 是否是对话框, 对话框显示在对话框层
     */
    boolean isDialog();

    /**
     * 对话框背景是否变暗
     */
    boolean isDimBehind();

    /**
     * 点击变暗处,是否关闭对话框
     */
    boolean canCanceledOnOutside();

    /**
     * 窗口外是否可点击, 当此方法返回false时, 点击窗口外将不能关闭对话框
     */
    boolean canTouchOnOutside();

    /**
     * 是否可以关闭对话框
     */
    boolean canCancel();

    /**
     * 获取对应的RootView
     */
    View getView();

    /**
     * 获取对应的ILayout接口
     */
    ILayout getILayout();

    void onShowInPager(UIViewPager viewPager);//2016-11-26

    void onHideInPager(UIViewPager viewPager);//2016-11-26

    void onActivityResult(int requestCode, int resultCode, Intent data);//2016-12-13
}
