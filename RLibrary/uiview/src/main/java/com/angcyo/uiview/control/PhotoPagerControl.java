package com.angcyo.uiview.control;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.angcyo.library.adapter.SingleFrescoImageAdapter;
import com.angcyo.uiview.R;
import com.angcyo.uiview.widget.viewpager.DepthPageTransformer;
import com.angcyo.uiview.widget.viewpager.TextIndicator;

import java.util.ArrayList;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/01/05 14:29
 * 修改人员：Robi
 * 修改时间：2017/01/05 14:29
 * 修改备注：
 * Version: 1.0.0
 */
public class PhotoPagerControl {

    TextIndicator mTextIndicatorView;
    ViewPager mViewPager;
    ArrayList<String> photos;
    OnItemClickListener mItemClickListener;
    private SingleFrescoImageAdapter mFrescoImageAdapter;
    private int mPlaceholderImageRes;

    public PhotoPagerControl(TextIndicator textIndicatorView, ViewPager viewPager,
                             ArrayList<String> photos) {
        this(textIndicatorView, viewPager, photos, R.drawable.base_placeholder);
    }

    public PhotoPagerControl(TextIndicator textIndicatorView, ViewPager viewPager,
                             ArrayList<String> photos, int placeholderImageRes) {
        mTextIndicatorView = textIndicatorView;
        mViewPager = viewPager;
        this.photos = photos;
        mPlaceholderImageRes = placeholderImageRes;
        initViewPager();
    }

    public PhotoPagerControl setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        return this;
    }

    private void initViewPager() {
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mFrescoImageAdapter = new SingleFrescoImageAdapter(mPlaceholderImageRes) {
            @Override
            protected String getImageUrl(int position) {
                return photos.get(position);
            }

            @Override
            public int getCount() {
                if (photos == null) {
                    return 0;
                }
                return photos.size();
            }

            @Override
            protected void onItemClick(View view, int position) {
                super.onItemClick(view, position);
                if (mItemClickListener == null) {

                } else {
                    mItemClickListener.onItemClick(view, position, getImageUrl(position));
                }
            }
        };
        mViewPager.setAdapter(mFrescoImageAdapter);
        mTextIndicatorView.setupViewPager(mViewPager);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String url);
    }
}
