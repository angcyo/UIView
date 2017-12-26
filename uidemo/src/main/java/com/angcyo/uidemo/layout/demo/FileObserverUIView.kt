package com.angcyo.uidemo.layout.demo

import android.os.FileObserver
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uiview.Root
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.widget.RecentlyPhotoImageView
import com.lzy.imagepicker.ImageDataSource
import java.io.File

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/12/26 13:44
 * 修改人员：Robi
 * 修改时间：2017/12/26 13:44
 * 修改备注：
 * Version: 1.0.0
 */
class FileObserverUIView : BaseContentUIView() {
    override fun inflateContentLayout(baseContentLayout: ContentLayout?, inflater: LayoutInflater?) {
        inflate(R.layout.file_observer_layout)
    }

    private var fileObserver: FileObserver? = null

    private val IMAGE_PROJECTION = arrayOf(//查询图片需要的数据列
            MediaStore.Images.Media.DISPLAY_NAME, //图片的显示名称  aaa.jpg
            MediaStore.Images.Media.DATA, //图片的真实路径  /storage/emulated/0/pp/downloader/wallpaper/aaa.jpg
            MediaStore.Images.Media.SIZE, //图片的大小，long型  132492
            MediaStore.Images.Media.WIDTH, //图片的宽度，int型  1920
            MediaStore.Images.Media.HEIGHT, //图片的高度，int型  1080
            MediaStore.Images.Media.MIME_TYPE, //图片的类型     image/jpeg
            MediaStore.Images.Media.DATE_ADDED)    //图片被添加的时间，long型  1450518608

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()

        tv(R.id.sd_tip_text_view).text = "监听 ${Root.sd()} 中..."

        if (fileObserver == null) {

            /*只能监听根目录的文件变化, 子目录中的文件变化不监听, 根目录的文件夹变化不监听*/
            fileObserver = object : FileObserver(Root.sd()) {
                override fun onEvent(e: Int, path: String?) {

                    val event = when (e) {
                        FileObserver.ACCESS -> "ACCESS"
                        FileObserver.ATTRIB -> "ATTRIB"
                        FileObserver.CLOSE_NOWRITE -> "CLOSE_NOWRITE"
                        FileObserver.CLOSE_WRITE -> "CLOSE_WRITE"
                        FileObserver.CREATE -> "CREATE"
                        FileObserver.DELETE -> "DELETE"
                        FileObserver.DELETE_SELF -> "DELETE_SELF"
                        FileObserver.MODIFY -> "MODIFY"
                        FileObserver.MOVED_FROM -> "MOVED_FROM"
                        FileObserver.MOVED_TO -> "MOVED_TO"
                        FileObserver.MOVE_SELF -> "MOVE_SELF"
                        FileObserver.OPEN -> "OPEN"
                        else -> ""
                    }

                    if (!TextUtils.isEmpty(event) && !TextUtils.isEmpty(path)) {
                        val file = File("${Root.sd()}/$path")
                        L.e("\nevent:$event isFile:${file.isFile} \npath:${Root.sd()}/$path \n")
                        post {
                            mViewHolder.tv(R.id.text_view).text = "${mViewHolder.tv(R.id.text_view).text}\nevent:$event isFile:${file.isFile} \npath:${Root.sd()}/$path \n"
                        }
                    }
                }
            }.apply {
                startWatching()
            }
        }

        val allImages = ImageDataSource.queryRecentlyPhoto(mActivity.contentResolver)
        L.e("call: initOnShowContentLayout -> ${allImages.size}")
        tv(R.id.photo_tip_text_view).text = "最近(5小时前)拍摄的新照片${allImages.size}张"
        val recentlyPhotoImageView: RecentlyPhotoImageView = v(R.id.image_view)
        val photos = mutableListOf<String>()
        allImages.map { image ->
            photos.add(image.path)
        }
        recentlyPhotoImageView.addPhotoList(photos)
    }

    override fun onViewUnload() {
        super.onViewUnload()
        fileObserver?.stopWatching()
    }
}