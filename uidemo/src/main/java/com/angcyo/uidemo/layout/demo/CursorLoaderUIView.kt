package com.angcyo.uidemo.layout.demo

import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.base.UIVideoView
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.T_
import com.lzy.imagepicker.GlideImageLoader
import com.lzy.imagepicker.ImageDataSource
import com.lzy.imagepicker.ImageDataSource.VIDEO
import com.lzy.imagepicker.ImagePickerHelper
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.view.ImagePickerImageView
import java.util.*

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/06/13 09:45
 * 修改人员：Robi
 * 修改时间：2017/06/13 09:45
 * 修改备注：
 * Version: 1.0.0
 */
class CursorLoaderUIView : BaseItemUIView(), LoaderManager.LoaderCallbacks<Cursor> {
    private val VIDEO_PROJECTION = arrayOf(//查询视频需要的数据列
            MediaStore.Video.Media.DISPLAY_NAME, //视频的显示名称  aaa.jpg
            MediaStore.Video.Media.DATA, //视频的真实路径  /storage/emulated/0/pp/downloader/wallpaper/aaa.jpg
            MediaStore.Video.Media.DURATION, //视频录制时长 ms, 11849
            MediaStore.Video.Media.MINI_THUMB_MAGIC, //视频缩略图的 id, 用来查询缩略图路径, 8111973006845647128
            MediaStore.Video.Media.RESOLUTION, //视频分辨率, 1920x1080
            MediaStore.Video.Media.SIZE, //视频的大小，long型  132492
            MediaStore.Video.Media.WIDTH, //视频的宽度，int型  0
            MediaStore.Video.Media.HEIGHT, //视频的高度，int型  0
            MediaStore.Video.Media.MIME_TYPE, //视频的类型     video/mp4
            MediaStore.Video.Media.DATE_ADDED, //视频被添加的时间，long型  1450518608
            MediaStore.Video.Media._ID
    )

    private val VIDEO_THUMB_PROJECTION = arrayOf(
            MediaStore.Video.Thumbnails.DATA, //缩略图路径
            MediaStore.Video.Thumbnails.WIDTH, //缩略图宽度
            MediaStore.Video.Thumbnails.HEIGHT, //缩略图高度
            MediaStore.Video.Thumbnails.KIND, //MICRO_KIND(96 x 96) or MINI_KIND ( 512 x 384 )
            MediaStore.Video.Thumbnails._ID,
            MediaStore.Video.Thumbnails.VIDEO_ID
    )

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        L.e("call: onLoadFinished -> ")
        if (data != null) {
            val allImages = ArrayList<ImageItem>()   //所有图片的集合,不分文件夹
            var index = 0
            data.moveToFirst()
            while (data.moveToNext()) {
                //查询数据
                val imageName = data.getString(data.getColumnIndexOrThrow(VIDEO_PROJECTION[0]))
                val imagePath = data.getString(data.getColumnIndexOrThrow(VIDEO_PROJECTION[1]))

                val videoDuration = data.getLong(data.getColumnIndexOrThrow(VIDEO_PROJECTION[2]))
                val thumbId = data.getString(data.getColumnIndexOrThrow(VIDEO_PROJECTION[3])) //缩略图id
                val resolution = data.getString(data.getColumnIndexOrThrow(VIDEO_PROJECTION[4])) //分辨率

                val imageSize = data.getLong(data.getColumnIndexOrThrow(VIDEO_PROJECTION[5]))
                val imageWidth = data.getInt(data.getColumnIndexOrThrow(VIDEO_PROJECTION[6]))
                val imageHeight = data.getInt(data.getColumnIndexOrThrow(VIDEO_PROJECTION[7]))
                val imageMimeType = data.getString(data.getColumnIndexOrThrow(VIDEO_PROJECTION[8]))
                val imageAddTime = data.getLong(data.getColumnIndexOrThrow(VIDEO_PROJECTION[9]))

                val videoId = data.getString(data.getColumnIndexOrThrow(VIDEO_PROJECTION[10]))

                val thumbPath = getVideoThumb(mActivity.contentResolver, videoId)


//                L.e(" -> ${index++}\n $imageName\n$imagePath\n" +
//                        "$videoDuration\n$thumbId\n$resolution\n" +
//                        "$imageSize\n$imageWidth\n$imageHeight\n$imageMimeType\n$imageAddTime\n" +
//                        "$videoId\n$thumbPath")

//                val imageFile = File(imagePath)
//                if (!imageFile.exists() || !imageFile.isFile()) {
//                    continue
//                }
//
//                //封装实体
//                val imageItem = ImageItem()
//                imageItem.name = imageName
//                imageItem.path = imagePath
//                imageItem.size = imageSize
//                imageItem.width = imageWidth
//                imageItem.height = imageHeight
//                imageItem.mimeType = imageMimeType
//                imageItem.addTime = imageAddTime
//                allImages.add(imageItem)
//                //根据父路径分类存放图片
//                val imageParentFile = imageFile.getParentFile()
//                val imageFolder = ImageFolder()
//                imageFolder.name = imageParentFile.getName()
//                imageFolder.path = imageParentFile.getAbsolutePath()
//
//                if (!imageFolders.contains(imageFolder)) {
//                    val images = ArrayList<ImageItem>()
//                    images.add(imageItem)
//                    imageFolder.cover = imageItem
//                    imageFolder.images = images
//                    imageFolders.add(imageFolder)
//                } else {
//                    imageFolders.get(imageFolders.indexOf(imageFolder)).images.add(imageItem)
//                }
            }
            //防止没有图片报异常
//            if (data.count > 0) {
//                //构造所有图片的集合
//                val allImagesFolder = ImageFolder()
//                allImagesFolder.name = activity.getResources().getString(R.string.all_images)
//                allImagesFolder.path = "/"
//                allImagesFolder.cover = allImages[0]
//                allImagesFolder.images = allImages
//                imageFolders.add(0, allImagesFolder)  //确保第一条是所有图片
//            }
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        L.e("call: onLoaderReset -> ")
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor>? {
        L.e("call: onCreateLoader -> ")
        var cursorLoader: CursorLoader? = null
        cursorLoader = CursorLoader(mActivity, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEO_PROJECTION,
                null, null, VIDEO_PROJECTION[9] + " DESC")//按照添加时间逆序
        return cursorLoader
    }

    /**获取视频缩略图*/
    private fun getVideoThumb(contentResolver: ContentResolver, videoId: String): String {
        //L.e("call: getVideoThumb -> ")
        var path = ""
        val dataCursor = contentResolver.query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                VIDEO_THUMB_PROJECTION, MediaStore.Video.Thumbnails.VIDEO_ID + "=?",
                arrayOf(videoId), null)
        if (dataCursor != null) {
            var index = 0
            while (dataCursor.moveToNext()) {
                val imagePath = dataCursor.getString(dataCursor.getColumnIndexOrThrow(VIDEO_THUMB_PROJECTION[0]))
                val imageWidth = dataCursor.getInt(dataCursor.getColumnIndexOrThrow(VIDEO_THUMB_PROJECTION[1]))
                val imageHeight = dataCursor.getInt(dataCursor.getColumnIndexOrThrow(VIDEO_THUMB_PROJECTION[2]))
                val imageKind = dataCursor.getInt(dataCursor.getColumnIndexOrThrow(VIDEO_THUMB_PROJECTION[3]))
                val thumbId = dataCursor.getString(dataCursor.getColumnIndexOrThrow(VIDEO_THUMB_PROJECTION[4]))
                val videoId = dataCursor.getString(dataCursor.getColumnIndexOrThrow(VIDEO_THUMB_PROJECTION[5]))

                path = imagePath
                //L.e(" -> ${index++}\n $imagePath\n$imageWidth\n$imageHeight\n$imageKind\n$thumbId\n$videoId")
            }
            dataCursor.close()
        }
        return path
    }

    override fun getItemLayoutId(viewType: Int): Int {
        if (isLast(viewType)) {
            return R.layout.adapter_image_list_item
        }
        return super.getItemLayoutId(viewType)
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val itemInfo = holder.item(R.id.base_item_info_layout)
                itemInfo.setItemText("打开图片选择器(多选)")
                itemInfo.setOnClickListener { ImagePickerHelper.startImagePicker(mActivity, false, true, 3) }
            }
        })
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val itemInfo = holder.item(R.id.base_item_info_layout)
                itemInfo.setItemText("打开图片选择器(单选)")
                itemInfo.setOnClickListener { ImagePickerHelper.startImagePicker(mActivity, false, false, 3) }
            }
        })
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val itemInfo = holder.item(R.id.base_item_info_layout)
                itemInfo.setItemText("打开视频选择器(多选)")
                itemInfo.setOnClickListener { ImagePickerHelper.startImagePicker(mActivity, false, true, 3, VIDEO) }
            }
        })
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val itemInfo = holder.item(R.id.base_item_info_layout)
                itemInfo.setItemText("打开视频选择器(单选)")
                itemInfo.setOnClickListener { ImagePickerHelper.startImagePicker(mActivity, false, 3, VIDEO) }
            }
        })

        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                iv_thumb = holder.v(R.id.iv_thumb)
            }
        })
    }

    override fun onViewLoad() {
        super.onViewLoad()
        val loaderManager = mActivity.supportLoaderManager
        loaderManager.initLoader(1, null, this)

        //loaderManager.destroyLoader(1)
    }

    var iv_thumb: ImagePickerImageView? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val items = ImagePickerHelper.getItems(mActivity, requestCode, resultCode, data)

        val build = StringBuilder()
        items.mapIndexed { index, imageItem ->
            build.append("\n -> $index 类型:${ImageDataSource.getLoaderTypeString(imageItem.loadType)} Path:${imageItem.path} " +
                    "\nThumbPath:${imageItem.videoThumbPath} Duration:${imageItem.videoDuration}")
        }

        if (items.isEmpty()) {
            T_.error("未选择.")
        } else {
            T_.info("选择${items.size}个")
            items.first().apply {
                if (this.loadType == ImageDataSource.VIDEO) {
                    iv_thumb?.setPlayDrawable(R.drawable.image_picker_play)
                    GlideImageLoader.displayImage(iv_thumb, this.videoThumbPath, R.mipmap.base_zhanweitu_klg)

                    startIView(UIVideoView().also {
                        it.videoPath = this.path
                    })
                } else {
                    iv_thumb?.setPlayDrawable(null)
                    GlideImageLoader.displayImage(iv_thumb, this.path, R.mipmap.base_zhanweitu_klg)
                }
            }
        }

        L.e(build.toString())
    }
}