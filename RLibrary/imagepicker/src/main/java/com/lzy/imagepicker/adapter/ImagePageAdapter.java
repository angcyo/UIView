package com.lzy.imagepicker.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ImagePickerHelper;
import com.lzy.imagepicker.Utils;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.view.MaterialProgressView;

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

    /**
     * @param path      本地大图路径
     * @param thumbPath 本地小图路径
     * @param url       图片网络路径
     */
    public static void displayImage(Activity activity, String path, String thumbPath, String url, ImageView imageView, int width, int height) {
//        if (TextUtils.isEmpty(path)) {
//            final DrawableRequestBuilder<String> drawableRequestBuilder = Glide.with(activity)                             //配置上下文
//                    .load(url)                                       //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
//                    //.error(R.mipmap.default_image)                    //设置错误图片
//                    //.fitCenter()
//                    //.centerCrop()
//                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
//                    ;
//
//            if (TextUtils.isEmpty(thumbPath)) {
//                drawableRequestBuilder.placeholder(R.mipmap.default_image)     //设置占位图片
//                        .into(imageView);
//            } else {
//                drawableRequestBuilder.placeholder(new BitmapDrawable(activity.getResources(), thumbPath))
//                        .into(imageView);
//            }
//        } else {
//            DrawableRequestBuilder<File> fileDrawableRequestBuilder = Glide.with(activity)
//                    .load(new File(path));//.error(R.mipmap.default_image);
//
//            if (TextUtils.isEmpty(thumbPath)) {
//                fileDrawableRequestBuilder.placeholder(R.mipmap.default_image)     //设置占位图片
//                        .into(imageView);
//            } else {
//                fileDrawableRequestBuilder.placeholder(new BitmapDrawable(activity.getResources(), thumbPath))
//                        .into(imageView);
//            }
//        }
        ImageLoader imageLoader = ImagePicker.getInstance().getImageLoader();
        if (imageLoader == null) {
            ImagePickerHelper.init();
        }
        imageLoader.displayImage(activity, path, thumbPath, url, imageView, width, height);
    }

    public void setData(ArrayList<ImageItem> images) {
        this.images = images;
    }

    public void setPhotoViewClickListener(PhotoViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        FrameLayout itemLayout = new FrameLayout(mActivity);

        //支持手势的图片
        final PhotoView photoView = new PhotoView(mActivity);

        //缩略图显示
        final ImageView imageView = new ImageView(mActivity);
        imageView.setScaleType(ImageView.ScaleType.CENTER);

        //进度条显示
        final MaterialProgressView progressView = new MaterialProgressView(mActivity);

        final ImageItem imageItem = images.get(position);
        /*imagePicker.getImageLoader().*/
        if (imageItem.placeholderDrawable == null) {
            displayImage(mActivity, imageItem.path, imageItem.thumbPath,
                    imageItem.url, photoView, screenWidth, screenHeight);
        } else {
            imageView.setVisibility(View.VISIBLE);
            progressView.setVisibility(View.VISIBLE);

            progressView.start();
            imageView.setImageDrawable(imageItem.placeholderDrawable);

            Glide.with(mActivity).load(imageItem.url).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (progressView == null || photoView == null) {
                        return;
                    }
                    imageView.setVisibility(View.GONE);
                    progressView.setVisibility(View.GONE);
                    progressView.stop();
                    photoView.setImageBitmap(resource);
                }

            });
        }

        itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.OnPhotoTapListener(v, 0, 0);
            }
        });

        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                if (listener != null) listener.OnPhotoTapListener(view, x, y);
            }
        });

        itemLayout.addView(imageView);
        itemLayout.addView(photoView);
        itemLayout.addView(progressView, new FrameLayout.LayoutParams(-2, -2, Gravity.CENTER));
        container.addView(itemLayout);
        return itemLayout;
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
