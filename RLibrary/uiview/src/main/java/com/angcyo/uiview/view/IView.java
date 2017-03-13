package com.angcyo.uiview.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.angcyo.uiview.base.UILayoutActivity;
import com.angcyo.uiview.container.ILayout;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.widget.viewpager.UIViewPager;

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
     * 生命周期顺序: 1
     * 请在此方法中, 进行xml的inflate操作, 如果使用了ButterKnife, 请在loadContentView方法之后初始化view的相应操作.
     */
    View inflateContentView(UILayoutActivity activity, ILayout iLayout, FrameLayout container, LayoutInflater inflater);

    /**
     * 生命周期顺序: 2
     * 当loadContentView完成之后会调用
     */
    void onViewCreate(View rootView);

    /**
     * 生命周期顺序: 3
     * 此方法会在inflateContentView之后, 紧接着执行
     */
    void loadContentView(View rootView);


    /**
     * 生命周期顺序: 4
     */
    void onViewLoad();

    /**
     * 生命周期顺序: 5
     */
    @Deprecated
    void onViewShow();

    /**
     * 生命周期顺序: 5
     */
    void onViewShow(final Bundle bundle);//2016-12-15

    /**
     * {@link com.angcyo.uiview.container.UIParam#start_mode} 是 {@link com.angcyo.uiview.container.UIParam#SINGLE_TOP}
     * 时,会调用此方法
     */
    void onViewReShow(final Bundle bundle);//2017-1-7

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
     * 获取对话框变暗动画的View, 默认是对话框root layout
     */
    View getDialogDimView();//星期二 2017-1-10

    /**
     * 获取动画作用的View
     */
    View getAnimView();//星期二 2017-1-10

    /**
     * 获取对应的ILayout接口
     */
    ILayout getILayout();

    void onShowInPager(UIViewPager viewPager);//2016-11-26

    void onHideInPager(UIViewPager viewPager);//2016-11-26

    void onActivityResult(int requestCode, int resultCode, Intent data);//2016-12-13

    /**
     * 获取对话框 变暗时的颜色
     */
    int getDimColor();//2016-12-15

    /**
     * 请求是否可以退出.
     */
    boolean onBackPressed();//2016-12-16

    /**
     * 滑动返回的时候,请求是否可以结束.
     */
    boolean canSwipeBackPressed();//星期三 2017-2-22

    /**
     * 在导航上, 左边的按钮界面, 调到右边的按钮界面, 或者 右边的按钮界面, 跳到左边的按钮界面.
     * 用来决定动画是否需要反向执行
     */
    IView setIsRightJumpLeft(boolean isRightJumpLeft);//2016-12-16

    /**
     * 会在start iVew 之后, 最先执行
     */
    void onAttachedToILayout(ILayout iLayout);

    /**
     * 是否可以滑动关闭
     */
    boolean canTryCaptureView();//星期二 2017-2-14
}
