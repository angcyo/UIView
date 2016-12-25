/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.angcyo.uiview.github.swipe.recyclerview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by Yan Zhenjie on 2016/7/26.
 */
public class SwipeMenuItem {

    private Context mContext;
    private Drawable background;
    private Drawable icon;
    private String title;
    private ColorStateList titleColor = ColorStateList.valueOf(Color.WHITE);
    private int titleSize;
    private Typeface textTypeface;
    private int textAppearance;
    private int width = -2;
    private int height = -2;
    private int weight = 0;
    private int paddLeft = 0;
    private int paddRight = 0;
    private Object tag;

    public SwipeMenuItem(Context context) {
        mContext = context;
    }

    public Object getTag() {
        return tag;
    }

    public SwipeMenuItem setTag(Object tag) {
        this.tag = tag;
        return this;
    }

    public SwipeMenuItem setBackgroundDrawable(Drawable background) {
        this.background = background;
        return this;
    }

    public SwipeMenuItem setBackgroundDrawable(int resId) {
        this.background = ResCompat.getDrawable(mContext, resId);
        return this;
    }

    public SwipeMenuItem setBackgroundColor(int color) {
        this.background = new ColorDrawable(color);
        return this;
    }

    public Drawable getBackground() {
        return background;
    }

    public SwipeMenuItem setText(String title) {
        this.title = title;
        return this;
    }

    public SwipeMenuItem setImage(Drawable icon) {
        this.icon = icon;
        return this;
    }

    public int getPaddLeft() {
        return paddLeft;
    }

    public SwipeMenuItem setPaddLeft(int paddLeft) {
        this.paddLeft = paddLeft;
        return this;
    }

    public int getPaddRight() {
        return paddRight;
    }

    public SwipeMenuItem setPaddRight(int paddRight) {
        this.paddRight = paddRight;
        return this;
    }

    public Drawable getImage() {
        return icon;
    }

    public SwipeMenuItem setImage(int resId) {
        return setImage(ResCompat.getDrawable(mContext, resId));
    }

    public SwipeMenuItem setTextColor(int titleColor) {
        this.titleColor = ColorStateList.valueOf(titleColor);
        return this;
    }

    public ColorStateList getTitleColor() {
        return titleColor;
    }

    public int getTextSize() {
        return titleSize;
    }

    public SwipeMenuItem setTextSize(int titleSize) {
        this.titleSize = titleSize;
        return this;
    }

    public String getText() {
        return title;
    }

    public SwipeMenuItem setText(int resId) {
        setText(mContext.getString(resId));
        return this;
    }

    public int getTextAppearance() {
        return textAppearance;
    }

    public SwipeMenuItem setTextAppearance(int textAppearance) {
        this.textAppearance = textAppearance;
        return this;
    }

    public Typeface getTextTypeface() {
        return textTypeface;
    }

    public SwipeMenuItem setTextTypeface(Typeface textTypeface) {
        this.textTypeface = textTypeface;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public SwipeMenuItem setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public SwipeMenuItem setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public SwipeMenuItem setWeight(int weight) {
        this.weight = weight;
        return this;
    }
}
