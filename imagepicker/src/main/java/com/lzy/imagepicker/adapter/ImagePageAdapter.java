package com.lzy.imagepicker.adapter;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.R;
import com.lzy.imagepicker.Utils;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;
import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧 Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：2016-12-13
 * ================================================
 */
public class ImagePageAdapter extends PagerAdapter {

    public PhotoViewClickListener listener;
    private int screenWidth;
    private int screenHeight;
    private ImagePicker imagePicker;
    private ArrayList<ImageItem> images = new ArrayList<>();
    private Activity mActivity;

    public ImagePageAdapter(Activity activity, ArrayList<ImageItem> images) {
        this.mActivity = activity;
        this.images = images;

        DisplayMetrics dm = Utils.getScreenPix(activity);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        imagePicker = ImagePicker.getInstance();
    }

    public static void displayImage(Activity activity, String path, String thumbPath, String url, ImageView imageView, int width, int height) {
        if (TextUtils.isEmpty(path)) {
            final DrawableRequestBuilder<String> drawableRequestBuilder = Glide.with(activity)                             //配置上下文
                    .load(url)                                       //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .error(R.mipmap.default_image)                    //设置错误图片
                    //.fitCenter()
                    //.centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            if (TextUtils.isEmpty(thumbPath)) {
                drawableRequestBuilder.placeholder(R.mipmap.default_image)     //设置占位图片
                        .into(imageView);
            } else {
                drawableRequestBuilder.placeholder(new BitmapDrawable(activity.getResources(), thumbPath))
                        .into(imageView);
            }
        } else {

            final DrawableRequestBuilder<Uri> requestBuilder = Glide.with(activity)                             //配置上下文
                    .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .error(R.mipmap.default_image)           //设置错误图片
                    //.fitCenter()
                    //.centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            if (TextUtils.isEmpty(thumbPath)) {
                requestBuilder.placeholder(R.mipmap.default_image)     //设置占位图片
                        .into(imageView);
            } else {
                requestBuilder.placeholder(new BitmapDrawable(activity.getResources(), thumbPath))
                        .into(imageView);
            }
        }
    }

    public void setData(ArrayList<ImageItem> images) {
        this.images = images;
    }

    public void setPhotoViewClickListener(PhotoViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(mActivity);
        ImageItem imageItem = images.get(position);
        /*imagePicker.getImageLoader().*/displayImage(mActivity, imageItem.path, imageItem.thumbPath,
                imageItem.url, photoView, screenWidth, screenHeight);
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                if (listener != null) listener.OnPhotoTapListener(view, x, y);
            }
        });
        container.addView(photoView);
        return photoView;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public interface PhotoViewClickListener {
        void OnPhotoTapListener(View view, float v, float v1);
    }
}
