package com.angcyo.uidemo.layout.demo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.angcyo.uidemo.R;
import com.angcyo.uidemo.T_;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.github.swipe.recyclerview.Closeable;
import com.angcyo.uiview.github.swipe.recyclerview.OnSwipeMenuItemClickListener;
import com.angcyo.uiview.github.swipe.recyclerview.SwipeMenu;
import com.angcyo.uiview.github.swipe.recyclerview.SwipeMenuCreator;
import com.angcyo.uiview.github.swipe.recyclerview.SwipeMenuItem;
import com.angcyo.uiview.github.swipe.recyclerview.SwipeMenuRecyclerView;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/23 14:44
 * 修改人员：Robi
 * 修改时间：2016/12/23 14:44
 * 修改备注：
 * Version: 1.0.0
 */
public class SwipeRecyclerViewUIView extends UIContentView {
    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        SwipeMenuRecyclerView swipeMenuRecyclerView = new SwipeMenuRecyclerView(mActivity);
        swipeMenuRecyclerView.setSwipeMenuCreator(new MenuCreator());
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(new OnSwipeMenuItemClickListener() {
            @Override
            public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, @SwipeMenuRecyclerView.DirectionMode int direction) {
                closeable.smoothCloseMenu();// 关闭被点击的菜单。
                if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                    T_.show("list第" + adapterPosition + "; 右侧菜单第" + menuPosition);
                } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                    T_.show("list第" + adapterPosition + "; 左侧菜单第" + menuPosition);
                }
            }
        });
    }

    private class MenuCreator implements SwipeMenuCreator {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            SwipeMenuItem left1 = new SwipeMenuItem(mActivity)
                    .setBackgroundDrawable(R.drawable.base_bg2_selector)// 点击的背景。
                    .setImage(R.mipmap.ic_launcher) // 图标。
                    .setText("左1") // 文字，还可以设置文字颜色，大小等。。
                    .setTextColor(Color.WHITE)
                    .setWidth(-2) // 宽度。
                    .setHeight(-1); // 高度。
            SwipeMenuItem left2 = new SwipeMenuItem(mActivity)
                    .setBackgroundDrawable(R.drawable.base_bg2_selector)// 点击的背景。
                    .setImage(R.mipmap.ic_launcher) // 图标。
                    .setText("左2") // 文字，还可以设置文字颜色，大小等。。
                    .setTextColor(Color.WHITE)
                    .setWidth(-2) // 宽度。
                    .setHeight(-1); // 高度。
            swipeLeftMenu.addMenuItem(left1);
            swipeLeftMenu.addMenuItem(left2);

            SwipeMenuItem right1 = new SwipeMenuItem(mActivity)
                    .setBackgroundDrawable(R.drawable.base_bg2_selector)// 点击的背景。
                    .setText("右1") // 文字，还可以设置文字颜色，大小等。。
                    .setTextColor(Color.WHITE)
                    .setWidth(-2) // 宽度。
                    .setHeight(-1); // 高度。
            SwipeMenuItem right2 = new SwipeMenuItem(mActivity)
                    .setBackgroundDrawable(R.drawable.base_bg2_selector)// 点击的背景。
                    .setText("右2") // 文字，还可以设置文字颜色，大小等。。
                    .setTextColor(Color.WHITE)
                    .setWidth(-2) // 宽度。
                    .setHeight(-1); // 高度。
            swipeRightMenu.addMenuItem(right1);
            swipeRightMenu.addMenuItem(right2);
        }
    }

}
