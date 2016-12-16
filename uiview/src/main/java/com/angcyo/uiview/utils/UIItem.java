package com.angcyo.uiview.utils;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：简单封装的 一条一条的 Item 布局工具类. 可以用于 用户界面,Item点击跳转等
 * 创建人员：Robi
 * 创建时间：2016/12/16 14:06
 * 修改人员：Robi
 * 修改时间：2016/12/16 14:06
 * 修改备注：
 * Version: 1.0.0
 */
public class UIItem {

    public static void fill(LinearLayout linearLayout, ArrayList<ItemInfo> itemInfos) {
        for (ItemInfo info : itemInfos) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, info.itemHeight);
            linearLayout.addView(makeItem(linearLayout.getContext(), info), params);
        }
    }

    private static TextView makeItem(Context context, ItemInfo info) {
        TextView textView = new TextView(context);
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                context.getResources().getDrawable(info.leftIcoResId), null,
                context.getResources().getDrawable(info.rightIcoResId), null);
        textView.setText(info.itemText);
        textView.setTextColor(info.textColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, info.textSize);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setCompoundDrawablePadding((int) dpToPx(context, 8));
        textView.setPadding(0, 0, (int) dpToPx(context, 8), 0);
        textView.setOnClickListener(info.clickListener);
        textView.setBackgroundResource(info.backgroundResId);
        return textView;
    }

    public static float dpToPx(Context context, float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
        return px;
    }


    public static class ItemInfo {
        @DrawableRes
        public int leftIcoResId;

        @DrawableRes
        public int rightIcoResId;

        @DrawableRes
        public int backgroundResId;

        @ColorInt
        public int textColor;

        /**
         * 字体大小, px单位
         */
        public int textSize;

        /**
         * 高度, px单位
         */
        public int itemHeight;

        public String itemText;

        public View.OnClickListener clickListener;


        private ItemInfo() {
        }

        public ItemInfo(@DrawableRes int leftIcoResId, @DrawableRes int rightIcoResId, @DrawableRes int backgroundResId,
                        int textColor, int textSize, int itemHeight,
                        String itemText, View.OnClickListener clickListener) {
            this.leftIcoResId = leftIcoResId;
            this.rightIcoResId = rightIcoResId;
            this.textColor = textColor;
            this.textSize = textSize;
            this.itemHeight = itemHeight;
            this.itemText = itemText;
            this.clickListener = clickListener;
            this.backgroundResId = backgroundResId;
        }

        public ItemInfo setLeftIcoResId(int leftIcoResId) {
            this.leftIcoResId = leftIcoResId;
            return this;
        }

        public ItemInfo setRightIcoResId(int rightIcoResId) {
            this.rightIcoResId = rightIcoResId;
            return this;
        }

        public ItemInfo setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public ItemInfo setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public ItemInfo setItemHeight(int itemHeight) {
            this.itemHeight = itemHeight;
            return this;
        }

        public ItemInfo setItemText(String itemText) {
            this.itemText = itemText;
            return this;
        }

        public ItemInfo setClickListener(View.OnClickListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public ItemInfo setBackgroundResId(int backgroundResId) {
            this.backgroundResId = backgroundResId;
            return this;
        }

        public ItemInfo copy() {
            ItemInfo itemInfo = new ItemInfo();
            itemInfo.setLeftIcoResId(leftIcoResId)
                    .setRightIcoResId(rightIcoResId)
                    .setTextColor(textColor)
                    .setTextSize(textSize)
                    .setItemHeight(itemHeight)
                    .setItemText(itemText)
                    .setClickListener(clickListener)
                    .setBackgroundResId(backgroundResId);
            return itemInfo;
        }
    }

}
