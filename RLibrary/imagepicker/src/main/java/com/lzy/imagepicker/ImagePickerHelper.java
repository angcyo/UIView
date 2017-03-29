package com.lzy.imagepicker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.GifRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/11/24 12:23
 * 修改人员：Robi
 * 修改时间：2016/11/24 12:23
 * 修改备注：
 * Version: 1.0.0
 */
public class ImagePickerHelper {
    public static void init() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
    }

    public static void startImagePicker(Activity activity, boolean crop, boolean multiMode, int selectLimit) {
        startImagePicker(activity, true, crop, multiMode, selectLimit);
    }

    public static void startImagePicker(Activity activity, boolean clear, boolean crop, boolean multiMode, int selectLimit) {
        startImagePicker(activity, true, clear, crop, multiMode, selectLimit);
    }

    public static void startImagePicker(Activity activity, boolean showCamera,
                                        boolean clear, boolean crop,
                                        boolean multiMode, int selectLimit) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setCrop(crop);
        imagePicker.setMultiMode(multiMode);
        imagePicker.setShowCamera(showCamera);
        imagePicker.setSelectLimit(selectLimit);
        imagePicker.setOutPutX(800);
        imagePicker.setOutPutY(800);
        imagePicker.setFocusWidth(600);
        imagePicker.setFocusHeight(600);
        Intent intent = new Intent(activity, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.CLEAR_SELECTOR, clear);
        activity.startActivityForResult(intent, 100);
    }

    /**
     * 拿到原始的图片磁盘路径
     */
    public static ArrayList<String> getImages(Activity activity, int requestCode, int resultCode, Intent data) {
        ArrayList<String> list = new ArrayList<>();
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (ImageItem item : images) {
                    list.add(item.path);
                }
            } else {
                Toast.makeText(activity, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
        return list;
    }

    /**
     * 根据路径,删除选中的图片
     */
    public static void deleteItemFromSelected(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        ArrayList<ImageItem> selectedImages = ImagePicker.getInstance().getSelectedImages();
        for (ImageItem item : selectedImages) {
            if (TextUtils.equals(item.path, path)) {
                selectedImages.remove(item);
                break;
            }
        }
    }

    /**
     * 清空所有状态
     */
    public static void clearAllSelectedImages() {
        ImagePicker.getInstance().clear();
    }

    /**
     * 是否是原图
     */
    public static boolean isOrigin(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                return data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
            }
        }
        return false;
    }

    public static class GlideImageLoader implements ImageLoader {

        @Override
        public void displayImage(Activity activity, String path, String thumbPath, String url, ImageView imageView, int width, int height) {

            if (TextUtils.isEmpty(path)) {
                final DrawableRequestBuilder<String> drawableRequestBuilder = Glide.with(activity)                             //配置上下文
                        .load(url)                                       //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                        //.error(R.mipmap.default_image)                    //设置错误图片
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
                File file = new File(path);
                if ("GIF".equalsIgnoreCase(ImageUtils.getImageType(file))) {
                    GifRequestBuilder<File> gifRequestBuilder = Glide.with(activity)                             //配置上下文
                            .load(file)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                            //.error(R.mipmap.default_image)           //设置错误图片
                            //.fitCenter()
                            .asGif()
                            //.centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            ;

                    if (TextUtils.isEmpty(thumbPath)) {
                        gifRequestBuilder.placeholder(R.mipmap.default_image)     //设置占位图片
                                .into(imageView);
                    } else {
                        gifRequestBuilder.placeholder(new BitmapDrawable(activity.getResources(), thumbPath))
                                .into(imageView);
                    }
                } else {
                    final DrawableRequestBuilder<File> requestBuilder = Glide.with(activity)                             //配置上下文
                            .load(file)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                            //.error(R.mipmap.default_image)           //设置错误图片
                            //.fitCenter()
                            //.centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.NONE);

                    if (TextUtils.isEmpty(thumbPath)) {
                        requestBuilder.placeholder(R.mipmap.default_image)     //设置占位图片
                                .into(imageView);
                    } else {
                        requestBuilder.placeholder(new BitmapDrawable(activity.getResources(), thumbPath))
                                .into(imageView);
                    }
                }
            }


        }

        @Override
        public void clearMemoryCache() {
            //这里是清除缓存的方法,根据需要自己实现
        }
    }

    public static class PicassoImageLoader implements ImageLoader {

        @Override
        public void displayImage(Activity activity, String path, String thumbPath, String url, ImageView imageView, int width, int height) {
//            Picasso.with(activity)//
//                    .load(new File(path))//
//                    .placeholder(R.mipmap.default_image)//
//                    .error(R.mipmap.default_image)//
//                    .resize(width, height)//
//                    .centerInside()//
//                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
//                    .into(imageView);
        }

        @Override
        public void clearMemoryCache() {
        }
    }
}
