package com.angcyo.library.facebook;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.View;

import com.angcyo.library.utils.Http;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/09/30 10:00
 * 修改人员：Robi
 * 修改时间：2016/09/30 10:00
 * 修改备注：
 * Version: 1.0.0
 */
public class DraweeViewUtil {

    public static int DEFAULT_WIDTH = 300;
    public static int DEFAULT_HEIGHT = 400;

    public static void init(Context context) {
        final float density = context.getResources().getDisplayMetrics().density;
        DEFAULT_WIDTH *= density;
        DEFAULT_HEIGHT *= density;
    }

    public static void setDraweeViewRes(SimpleDraweeView view, @DrawableRes int res) {
        String url = "res://" + view.getContext().getPackageName() + "/" + res;
//        view.setImageURI(Uri.parse(url));
        resize(view, Uri.parse(url), DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public static void setDraweeViewFile(SimpleDraweeView view, String filePath) {
        String url = "file://" + filePath;
        view.setImageURI(Uri.parse(url));
//        resize(view, Uri.parse(url), DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public static void setDraweeViewHttp(SimpleDraweeView view, String url) {
        if (TextUtils.isEmpty(url)) {
            view.setImageURI("");
            return;
        }
//        resize(view, Uri.parse(url), DEFAULT_WIDTH, DEFAULT_HEIGHT);
        if (url.startsWith("http")) {
            view.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
            view.setImageURI(Uri.parse(url));
        } else {
            setDraweeViewHttp2Inner(view, url);
        }
    }

    public static void setDraweeViewHttp2(SimpleDraweeView view, String url) {
        setDraweeViewHttp(view, url);
    }

    public static void setDraweeViewHttp2Inner(SimpleDraweeView view, String url) {
        Uri uri = Uri.parse(Http.BASE_IMAGE_URL + url);
        view.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
        view.setImageURI(uri);
    }

    public static void setDraweeViewHttp2(SimpleDraweeView view, String url, boolean progressive) {
        Uri uri = Uri.parse(Http.BASE_IMAGE_URL + url);
        //view.setImageURI(uri);

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(progressive)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(view.getController())
                .build();
        view.setController(controller);
    }

    /**
     * 重置加载的图片大小,不修改原图, 效果很好
     */
    public static void resize(SimpleDraweeView view, Uri uri, int width, int height) {
        view.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width == 0 ? DEFAULT_WIDTH : width, height == 0 ? DEFAULT_HEIGHT : height))
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(view.getController())
                .setImageRequest(request)
                .build();
        view.setController(controller);
    }

    public static void resize(SimpleDraweeView view, String url, int width, int height) {
        if (TextUtils.isEmpty(url)) {
            view.setImageURI("");
            return;
        }
        if (url.startsWith("http")) {
            resize(view, Uri.parse(url), width, height);
        } else {
            resize(view, Uri.parse(Http.BASE_IMAGE_URL + url), width, height);
        }
    }

    public static void resize(SimpleDraweeView view, String url) {
        resize(view, url, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public static void resize(SimpleDraweeView view, String url, View taget) {
        resize(view, url, taget.getMeasuredWidth(), taget.getMeasuredHeight());
    }

    /**
     * 设置占位图以及缩放类型
     */
    public static void setPlaceholderImage(SimpleDraweeView view) {
        //view.getHierarchy().setPlaceholderImage(R.drawable.load_banner_failed, ScalingUtils.ScaleType.CENTER_INSIDE);
    }

    /**
     * 设置圆形
     */
    public static void setDraweeViewRound(SimpleDraweeView view) {
        RoundingParams roundingParams = RoundingParams.asCircle();
        view.getHierarchy().setRoundingParams(roundingParams);
    }

    /**
     * 设置圆形
     */
    public static void setDraweeViewRound(SimpleDraweeView view, boolean circle) {
        RoundingParams roundingParams = RoundingParams.asCircle();
        roundingParams.setRoundAsCircle(circle);
        view.getHierarchy().setRoundingParams(roundingParams);
    }

    /**
     * 设置圆角
     */
    public static void setDraweeViewRadius(SimpleDraweeView view, float radius) {
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(radius);//圆角图片
        view.getHierarchy().setRoundingParams(roundingParams);
    }
}

