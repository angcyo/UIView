package com.angcyo.uiview.recycler.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.angcyo.library.facebook.DraweeViewUtil;
import com.angcyo.uiview.R;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.resources.ResUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：九宫格添加图片的适配器 推荐使用{@link RAddPhotoAdapter}
 * 创建人员：Robi
 * 创建时间：2016/10/18 10:51
 * 修改人员：Robi
 * 修改时间：2016/10/18 10:51
 * 修改备注：
 * Version: 1.0.0
 */
@Deprecated
public abstract class BaseAddPhotoAdapter<T> extends RBaseAdapter<T> {

    public static final int TYPE_ADD = 2;
    public static final int TYPE_NORMAL = 1;

    /**
     * 允许添加的图片最大数量
     */
    private int mMaxPhotoCount = 9;

    /**
     * 每一行中, Item的数量, 用来计算item的宽高
     */
    private int mItemCountLine = 4;

    public BaseAddPhotoAdapter(Context context) {
        super(context);
    }

    public BaseAddPhotoAdapter(Context context, List<T> datas) {
        super(context, datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return 0;
    }

    @Override
    protected void onBindView(RBaseViewHolder holder, final int position, final T bean) {
        if (holder.getViewType() == TYPE_ADD) {
            holder.itemView.findViewWithTag("add_view").setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddViewClick(v);
                }
            });
        } else {
            SimpleDraweeView imageView = (SimpleDraweeView) holder.itemView.findViewWithTag("image_view");
            DraweeViewUtil.resize(imageView, Uri.fromFile(new File(getImageFilePath(position))), getItemSize(), getItemSize());
//            DraweeViewUtil.setDraweeViewFile(imageView, getImageFilePath(position));
//            ImageView imageView = (ImageView) holder.itemView.findViewWithTag("image_view");
//            Glide.with(mContext)                             //配置上下文
//                    .load(Uri.fromFile(new File(getImageFilePath(position))))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
//                    .error(R.mipmap.default_image)           //设置错误图片
//                    .placeholder(R.mipmap.default_image)     //设置占位图片
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
//                    .into(imageView);
            holder.itemView.findViewWithTag("click_view").setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemViewClick(v, position, bean);
                }
            });
        }
    }

    @Override
    public int getItemType(int position) {
        if (mAllDatas == null || mAllDatas.size() == 0) {
            return TYPE_ADD;
        }
        if (position == mAllDatas.size() && mAllDatas.size() < mMaxPhotoCount) {
            return TYPE_ADD;//最后一个添加item
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (mAllDatas == null || mAllDatas.isEmpty()) {
            return 1;
        }
        if (mAllDatas.size() < mMaxPhotoCount) {
            return mAllDatas.size() + 1;
        }
        return mAllDatas.size();
    }

    @Override
    protected View createContentView(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ADD) {
            return createAddView();
        } else {
            return createImageView();
        }
    }

    protected abstract String getImageFilePath(int position);

    protected abstract void onAddViewClick(View view);

    protected abstract void onItemViewClick(View view, int position, T bean);

    public BaseAddPhotoAdapter setItemCountLine(int itemCountLine) {
        mItemCountLine = itemCountLine;
        return this;
    }

    public BaseAddPhotoAdapter setMaxPhotoCount(int maxPhotoCount) {
        mMaxPhotoCount = maxPhotoCount;
        return this;
    }

    private int getItemSize() {
        int screenWidth = ResUtil.getScreenWidth(mContext);
        int itemSize = screenWidth / mItemCountLine;

        return itemSize;
    }

    private View createImageView() {
        int screenWidth = ResUtil.getScreenWidth(mContext);

        int itemSpace = (int) ResUtil.dpToPx(mContext.getResources(), 6);
        int itemSize = screenWidth / mItemCountLine;

        RelativeLayout layout = new RelativeLayout(mContext);
        layout.setLayoutParams(new ViewGroup.LayoutParams(itemSize, itemSize));
        layout.setPadding(itemSpace, itemSpace / 2, itemSpace, itemSpace / 2);
//        layout.setBackgroundColor(Color.RED);

        SimpleDraweeView imageView = new SimpleDraweeView(mContext);
//        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));

        View clickView = new View(mContext);
        clickView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
//        clickView.setBackgroundResource(R.drawable.default_bg_selector);

        Drawable drawable = ResUtil.generateRoundBorderDrawable(0, Color.parseColor("#80000000"), Color.TRANSPARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            clickView.setBackground(drawable);
        } else {
            clickView.setBackgroundDrawable(drawable);
        }

        imageView.setTag("image_view");
        clickView.setTag("click_view");
        layout.addView(imageView);
        layout.addView(clickView);
        return layout;
    }

    private View createAddView() {
        int screenWidth = ResUtil.getScreenWidth(mContext);

        int itemSpace = (int) ResUtil.dpToPx(mContext.getResources(), 6);
        int itemSize = screenWidth / mItemCountLine;

        RelativeLayout layout = new RelativeLayout(mContext);
        layout.setLayoutParams(new ViewGroup.LayoutParams(itemSize, itemSize));
        layout.setPadding(itemSpace, itemSpace / 2, itemSpace, itemSpace / 2);
//        layout.setBackgroundColor(Color.RED);

        ImageButton clickView = new ImageButton(mContext);
        clickView.setImageResource(R.drawable.base_add);
        clickView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        clickView.setBackgroundResource(R.drawable.default_add_button_selector);

        clickView.setTag("add_view");
        layout.addView(clickView);
        return layout;
    }
}
