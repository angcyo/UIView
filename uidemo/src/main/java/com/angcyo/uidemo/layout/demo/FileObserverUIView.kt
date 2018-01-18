package com.angcyo.uidemo.layout.demo

import android.graphics.Color
import android.os.FileObserver
import android.text.TextUtils
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseContentUIView
import com.angcyo.uiview.Root
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.kotlin.OnAddViewCallback
import com.angcyo.uiview.kotlin.addView
import com.angcyo.uiview.kotlin.nowTime
import com.angcyo.uiview.utils.RUtils
import com.angcyo.uiview.utils.Tip
import com.angcyo.uiview.widget.CircleImageView
import com.angcyo.uiview.widget.GlideImageView
import com.angcyo.uiview.widget.RecentlyPhotoImageView
import com.angcyo.uiview.widget.group.PileFrameLayout
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

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()

        val targetPath = Root.getScreenshotsDirectory() //Root.sd()

        tv(R.id.sd_tip_text_view).text = "监听$targetPath 中..."

        if (fileObserver == null) {

            /*只能监听根目录的文件变化, 子目录中的文件变化不监听, 根目录的文件夹变化不监听*/
            fileObserver = object : FileObserver(targetPath) {
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
                        val file = File("$targetPath/$path")
                        L.e("\nevent:$event isFile:${file.isFile} \npath:$targetPath/$path \n")
                        post {
                            mViewHolder.tv(R.id.text_view).text = "${mViewHolder.tv(R.id.text_view).text}\n" +
                                    "${RUtils.getDataTime("yyyy-MM-dd HH:mm:ss:SSS")}\n" +
                                    "event:$event isFile:${file.isFile} " +
                                    "size:${if (file.isFile) Formatter.formatFileSize(mActivity, file.length()) else "${file.list().size}项"}\n" +
                                    "path:$targetPath/$path \n"
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

        val pileFrameLayout: PileFrameLayout = v(R.id.pile_frame_layout)
        pileFrameLayout.addView(listOf("", "", "", ""), object : OnAddViewCallback<String>() {
            override fun getView(): View? {
                val view = GlideImageView(mActivity)
                view.lineColor = Color.WHITE
                view.lineWidth = 3 * density()
                view.showType = CircleImageView.ROUND_RECT
                view.roundRadius = 45 * density()
                //view.setImageResource(R.drawable.image_demo)
                view.layoutParams = ViewGroup.LayoutParams(30 * density().toInt(), 30 * density().toInt())
                return view
            }
        })

        click(R.id.float_image_view) {
            Tip.tip("Float Touch ${nowTime()}")
        }
        click(R.id.float_image_view2) {
            Tip.tip("Touch ${nowTime()}")
        }
    }

    override fun onViewUnload() {
        super.onViewUnload()
        fileObserver?.stopWatching()
    }
}