package com.angcyo.library.glide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.angcyo.library.R;
import com.angcyo.library.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：根据图片数量, 自动排列图片位置
 * 创建人员：Robi
 * 创建时间：2017/02/10 10:52
 * 修改人员：Robi
 * 修改时间：2017/02/10 10:52
 * 修改备注：
 * Version: 1.0.0
 */
@Deprecated
public class NineImageLayout extends FrameLayout implements View.OnClickListener {

    /**
     * 需要加载的图片列表
     */
    List<String> mImagesList = new ArrayList<>();
    /**
     * 用来显示图片的ImageView
     */
    List<ImageView> mImageViews = new ArrayList<>();

    NineImageConfig mNineImageConfig;

    int space = 6;//dp, 间隙

    boolean canItemClick = true;

    public NineImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        space *= getResources().getDisplayMetrics().density;
    }

    public NineImageLayout(Context context) {
        this(context, null);
    }

    private int getSize(int width) {
        return MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
    }

    private int getSize2(int width) {
        return MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (mImagesList.isEmpty()) {
            L.w("don't have image list , skip measure.");
            setMeasuredDimension(0, 0);//没有图片, 设置0大小
        } else if (mNineImageConfig == null) {
            L.w("need set nine image config.");
            mImageViews.get(0).measure(getSize(measureWidth), getSize(measureWidth));
            setMeasuredDimension(measureWidth, measureWidth);
        } else {
            final int size = mImagesList.size();
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height;//需要计算布局的高度
            if (size == 1) {
                //一张图片
                final int[] widthHeight = mNineImageConfig.getWidthHeight(1);
                if (widthHeight[0] == -1) {
                    mImageViews.get(0).measure(getSize(width), getSize(width));
                    setMeasuredDimension(width, width);
                } else if (widthHeight[0] == 0) {
                    int defaultSize = (int) (getResources().getDisplayMetrics().density * 150);
                    mImageViews.get(0).measure(getSize(defaultSize), getSize(defaultSize));
                    setMeasuredDimension(defaultSize, defaultSize);
                } else {
                    mImageViews.get(0).measure(getSize(widthHeight[0]), getSize(widthHeight[1]));
                    setMeasuredDimension(widthHeight[0], widthHeight[1]);

                    //L.e("width:" + widthHeight[0] + " height:" + widthHeight[1]);
                }
            } else {
                final int columns = getColumns(size);
                final int rows = getRows(size);
                //每张图片的宽度
                int itemWidth = (width - space * Math.max(0, columns - 1)) / columns;
                height = rows * itemWidth + Math.max(0, columns - 1) * space;

                for (View view : mImageViews) {
                    view.measure(getSize(itemWidth), getSize(itemWidth));
                }
                setMeasuredDimension(width, height);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //super.onLayout(changed, left, top, right, bottom);
        if (mImageViews.isEmpty()) {
            L.w("don't have image list , skip layout.");
        } else if (mNineImageConfig == null) {
            L.w("need set nine image config.");
            final ImageView firstView = mImageViews.get(0);
            firstView.layout(left, top, right, bottom);

//            if (mNineImageConfig != null) {
//                mNineImageConfig.displayImage(firstView, mImagesList.get(0), getMeasuredWidth(), getMeasuredHeight());
//            }
        } else {
            final int size = mImagesList.size();
            if (size == 1) {
                //一张图片
                final ImageView firstView = mImageViews.get(0);
                firstView.layout(left, top, right, bottom);

//                if (mNineImageConfig != null) {
//                    mNineImageConfig.displayImage(firstView, mImagesList.get(0), getMeasuredWidth(), getMeasuredHeight());
//                }
            } else {
                final int columns = getColumns(size);
                final int rows = getRows(size);

                int l;
                int t;
                for (int i = 0; i < rows; i++) {
                    //行数
                    for (int j = 0; j < columns; j++) {
                        final int index = i * columns + j;
                        if (index >= size) {
                            break;
                        }
                        //列数
                        final ImageView imageView = mImageViews.get(index);
                        final int width = imageView.getMeasuredWidth();
                        final int height = imageView.getMeasuredHeight();
                        l = left + j * width + j * space;
                        t = top + i * height + i * space;
                        imageView.layout(l, t, l + width, t + height);

//                        if (mNineImageConfig != null) {
//                            mNineImageConfig.displayImage(imageView, mImagesList.get(index), imageView.getMeasuredWidth(), imageView.getMeasuredHeight());
//                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (mNineImageConfig != null) {
            for (int i = 0; i < mImageViews.size(); i++) {
                ImageView imageView = mImageViews.get(i);
                mNineImageConfig.displayImage(imageView, mImagesList.get(i),
                        imageView.getMeasuredWidth(), imageView.getMeasuredHeight());
            }
        }
    }

    /**
     * 调用此方法, 开始显示图片,
     * 请先调用 {@link #setNineImageConfig(NineImageConfig)}
     */
    public void setImagesList(List<String> imagesList) {
        mImagesList.clear();
        if (imagesList != null) {
            mImagesList.addAll(imagesList);
        }
        notifyDataChanged();
    }

    public void setImage(String image) {
        List<String> imagesList = new ArrayList<>();
        imagesList.add(image);
        setImagesList(imagesList);
    }

    /**
     * 设置图片加载方式, 和第一张图片的大小
     */
    public void setNineImageConfig(NineImageConfig nineImageConfig) {
        mNineImageConfig = nineImageConfig;
    }

    private void notifyDataChanged() {
        int oldSize = mImageViews.size();
        int newSize = mImagesList.size();
        if (newSize > oldSize) {
            for (int i = oldSize; i < newSize; i++) {
                createImageView(i);
            }
        } else if (newSize < oldSize) {
            for (int i = oldSize - 1; i >= newSize; i--) {
                removeView(mImageViews.remove(i));
            }
        }
        requestLayout();
        onSizeChanged(0, 0, 0, 0);

//        mImageViews.clear();
//        removeAllViews();
//        for (int i = 0; i < newSize; i++) {
//            createImageView(i);
//        }
//        requestLayout();
    }

    private void createImageView(int i) {
        ImageView imageView = new ImageView(getContext());
//            imageView.setBackgroundColor(Color.BLUE);
        imageView.setTag(R.id.tag_position, i);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addViewInLayout(imageView, i, new LayoutParams(-2, -2));
        mImageViews.add(imageView);
        if (canItemClick) {
            imageView.setOnClickListener(this);
        }
    }

    /**
     * 根据图片数量, 返回行数
     */
    private int getRows(int size) {
        if (size <= 3) {
            return 1;
        }
        if (size <= 6) {
            return 2;
        }
        if (size <= 9) {
            return 3;
        }
        return 0;
    }

    /**
     * 根据图片数量, 返回列数
     */
    private int getColumns(int size) {
        if (size == 4) {
            return 2;
        }
        return Math.min(3, size);
    }

    @Override
    public void onClick(View v) {
        if (mNineImageConfig != null) {
            final int position = (int) v.getTag(R.id.tag_position);
            mNineImageConfig.onImageItemClick((ImageView) v, mImagesList, mImageViews, position);
        }
    }

    /**
     * 设置间隙大小
     */
    public void setSpace(int space) {
        this.space = space;
    }

    /**
     * 设置Item是否可点击
     */
    public void setCanItemClick(boolean canItemClick) {
        this.canItemClick = canItemClick;
    }

    public interface NineImageConfig {
        /**
         * 通过图片数量, 返回对应的宽度和高度, 目前只适用于1张图片的时候, 其他数量的会自动计算
         */
        int[] getWidthHeight(int imageSize);

        void displayImage(ImageView imageView, String url, int width, int height);

        void onImageItemClick(ImageView imageView, List<String> urlList, List<ImageView> imageList, int index);
    }
}
