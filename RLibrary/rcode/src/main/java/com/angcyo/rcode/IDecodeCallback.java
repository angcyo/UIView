package com.angcyo.rcode;

import android.graphics.Bitmap;
import android.os.Handler;

import com.angcyo.rcode.zxing.camera.CameraManager;
import com.angcyo.rcode.zxing.view.ViewfinderView;
import com.google.zxing.Result;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/18 14:56
 * 修改人员：Robi
 * 修改时间：2017/04/18 14:56
 * 修改备注：
 * Version: 1.0.0
 */
public interface IDecodeCallback {
    int decode_succeeded = 1;
    int decode_failed = -1;
    int decode = 2;
    int quit = 0;
    int restart_preview = 3;

    CameraManager getCameraManager();

    Handler getHandler();

    ViewfinderView getViewfinderView();

    void drawViewfinder();

    void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor);

}
