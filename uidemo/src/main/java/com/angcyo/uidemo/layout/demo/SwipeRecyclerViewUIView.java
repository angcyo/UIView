package com.angcyo.uidemo.layout.demo;

import android.graphics.Color;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.container.ContentLayout;
import com.angcyo.uiview.github.swipe.RBaseMenuAdapter;
import com.angcyo.uiview.github.swipe.recyclerview.Closeable;
import com.angcyo.uiview.github.swipe.recyclerview.OnSwipeMenuItemClickListener;
import com.angcyo.uiview.github.swipe.recyclerview.SwipeMenu;
import com.angcyo.uiview.github.swipe.recyclerview.SwipeMenuCreator;
import com.angcyo.uiview.github.swipe.recyclerview.SwipeMenuItem;
import com.angcyo.uiview.github.swipe.recyclerview.SwipeMenuRecyclerView;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.resources.ResUtil;
import com.angcyo.uiview.utils.T_;

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
    protected void inflateContentLayout(ContentLayout baseContentLayout, LayoutInflater inflater) {
        SwipeMenuRecyclerView swipeMenuRecyclerView = new SwipeMenuRecyclerView(mActivity);
        swipeMenuRecyclerView.setSwipeMenuCreator(new MenuCreator());
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(new OnSwipeMenuItemClickListener() {
            @Override
            public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition,
                                    @SwipeMenuRecyclerView.DirectionMode int direction, final SwipeMenuItem menuItem) {
                closeable.smoothCloseMenu();// 关闭被点击的菜单。
                if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                    T_.show("list第" + adapterPosition + "; 右侧菜单第" + menuPosition + " " + menuItem.getText());
                } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                    T_.show("list第" + adapterPosition + "; 左侧菜单第" + menuPosition + " " + menuItem.getText());
                }
            }
        });

        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        swipeMenuRecyclerView.setAdapter(new RBaseMenuAdapter(mActivity) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return 0;
            }

            @Override
            protected View createContentView(ViewGroup parent, int viewType) {
                LinearLayout root = new LinearLayout(mActivity);
                root.setOrientation(LinearLayout.HORIZONTAL);
                root.setVerticalGravity(Gravity.CENTER_VERTICAL);
                root.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
                root.setPadding(1, 1, 1, 1);
                root.setBackgroundResource(R.drawable.base_main_color_bg_selector);

                AppCompatCheckBox checkBox = new AppCompatCheckBox(mActivity);
                checkBox.setTag("check_box");
                checkBox.setVisibility(View.INVISIBLE);

                TextView textView = new TextView(mActivity);
                textView.setLayoutParams(new ViewGroup.LayoutParams(-1, (int) ResUtil.dpToPx(mActivity.getResources(), 50)));
                textView.setTextColor(Color.BLUE);
                textView.setTag("text");
                textView.setGravity(Gravity.CENTER_VERTICAL);
                //textView.setBackgroundColor(Color.GRAY);

                root.addView(checkBox, new ViewGroup.LayoutParams(-2, -2));
                root.addView(textView);

                return root;
            }

            @Override
            protected void onBindView(RBaseViewHolder holder, int position, Object bean) {
                ((TextView) holder.tag("text")).setText("左右滑动试一试咯!!! ->" + this.getClass().getSuperclass().getSimpleName() + " " + position);
            }

            @Override
            public int getItemCount() {
                return 100;
            }
        });
//        swipeMenuRecyclerView.setAdapter(new RImageAdapter<String>(mActivity) {
//            @Override
//            public int getItemCount() {
//                return 30;
//            }
//
//            @Override
//            public void onBindImageView(@Nullable GlideImageView imageView, @NotNull RBaseViewHolder holder, int position, @Nullable String bean) {
//                super.onBindImageView(imageView, holder, position, bean);
//                imageView.setImageResource(R.drawable.image_demo);
//            }
//        });
        baseContentLayout.addView(swipeMenuRecyclerView, new ViewGroup.LayoutParams(-1, -1));
    }

    private class MenuCreator implements SwipeMenuCreator {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int padd = (int) ResUtil.dpToPx(mActivity, 10);
            SwipeMenuItem left1 = new SwipeMenuItem(mActivity)
                    .setBackgroundDrawable(R.drawable.base_bg2_selector)// 点击的背景。
                    .setImage(R.mipmap.ic_launcher) // 图标。
                    .setText("左1") // 文字，还可以设置文字颜色，大小等。。
                    .setTextColor(Color.BLUE)
                    .setPaddLeft(padd)
                    .setPaddRight(padd)
                    .setWidth(-2) // 宽度。
                    .setHeight(-1); // 高度。
            SwipeMenuItem left2 = new SwipeMenuItem(mActivity)
                    .setBackgroundDrawable(R.drawable.base_bg2_selector)// 点击的背景。
                    .setImage(R.mipmap.ic_launcher) // 图标。
                    .setText("左2") // 文字，还可以设置文字颜色，大小等。。
                    .setTextColor(Color.BLUE)
                    .setPaddLeft(padd)
                    .setPaddRight(padd)
                    .setWidth(-2) // 宽度。
                    .setHeight(-1); // 高度。
            swipeLeftMenu.addMenuItem(left1);
            swipeLeftMenu.addMenuItem(left2);

            SwipeMenuItem right1 = new SwipeMenuItem(mActivity)
                    .setBackgroundDrawable(R.drawable.base_bg2_selector)// 点击的背景。
                    .setText("右1") // 文字，还可以设置文字颜色，大小等。。
                    .setTextColor(Color.BLUE)
                    .setPaddLeft(padd)
                    .setPaddRight(padd)
                    .setBackgroundColor(Color.GREEN)
                    .setWidth(-2) // 宽度。
                    .setHeight(-1); // 高度。
            SwipeMenuItem right2 = new SwipeMenuItem(mActivity)
                    .setBackgroundColor(Color.GRAY)
                    .setBackgroundDrawable(R.drawable.base_bg2_selector)// 点击的背景。
                    .setText("右2") // 文字，还可以设置文字颜色，大小等。。
                    .setTextColor(Color.BLUE)
                    .setBackgroundColor(Color.YELLOW)
                    .setPaddLeft(padd)
                    .setPaddRight(padd)
                    .setWidth(-2) // 宽度。
                    .setHeight(-1); // 高度。
            swipeRightMenu.addMenuItem(right1);
            swipeRightMenu.addMenuItem(right2);
        }
    }

}
