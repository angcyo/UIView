package com.angcyo.library.facebook;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

import jp.wasabeef.fresco.processors.MaskPostprocessor;

public class RFresco {
    public static void mask(Context context, SimpleDraweeView draweeView, int maskId, String url, boolean isFile) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(isFile ? Uri.fromFile(new File(url)) : Uri.parse(url))
                .setPostprocessor(new MaskPostprocessor(context, maskId))
                .build();

        PipelineDraweeController controller =
                (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(draweeView.getController())
                        .build();

        draweeView.setController(controller);
    }
}