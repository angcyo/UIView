package com.angcyo.library.adapter;

import android.support.annotation.DrawableRes;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.angcyo.library.facebook.DraweeViewUtil;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/17 11:07
 * 修改人员：Robi
 * 修改时间：2016/12/17 11:07
 * 修改备注：
 * Version: 1.0.0
 */
public abstract class SingleFrescoImageAdapter extends PagerAdapter {

    private int width = -1, height = -1;

    @DrawableRes
    private int placeholderImageRes;

    public SingleFrescoImageAdapter(int placeholderImageRes) {
        this.placeholderImageRes = placeholderImageRes;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        final SimpleDraweeView draweeView = new SimpleDraweeView(container.getContext());
        GenericDraweeHierarchy hierarchy = draweeView.getHierarchy();
        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
        hierarchy.setPlaceholderImage(placeholderImageRes, ScalingUtils.ScaleType.CENTER_CROP);

        container.addView(draweeView, new ViewGroup.LayoutParams(width, height));
        DraweeViewUtil.setDraweeViewHttp(draweeView, getImageUrl(position));
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v, position);
            }
        });
        return draweeView;
    }

    protected void onItemClick(View view, int position) {

    }

    protected abstract String getImageUrl(int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

