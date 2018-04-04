package com.angcyo.uidemo.layout.demo

import android.content.Intent
import com.angcyo.iview.UIScanView2
import com.lzy.imagepicker.ImagePickerHelper
import rx.functions.Action1

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：扫一扫
 * 创建人员：Robi
 * 创建时间：2017/06/16 13:44
 * 修改人员：Robi
 * 修改时间：2017/06/16 13:44
 * 修改备注：
 * Version: 1.0.0
 */
class MyScanUIView2(resultAction: Action1<String>? = null) : UIScanView2() {

    init {
        onScanResult = {
            resultAction?.call(it)
        }
    }

    override fun onSelectorPhotoClick() {
        super.onSelectorPhotoClick()
        ImagePickerHelper.startImagePicker(mActivity, false, true, false, false, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val images = ImagePickerHelper.getImages(mActivity, requestCode, resultCode, data)
        if (images.size > 0) {
            scanPicture(images[0])
        }
    }
}