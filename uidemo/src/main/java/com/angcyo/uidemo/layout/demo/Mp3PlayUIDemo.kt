package com.angcyo.uidemo.layout.demo

import android.content.Context
import android.media.AudioManager
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.layout.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.dialog.UIFileSelectorDialog
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.RPlayer
import com.angcyo.uiview.utils.RUtils
import com.angcyo.uiview.widget.RTextView
import java.io.File

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/10/24 18:21
 * 修改人员：Robi
 * 修改时间：2017/10/24 18:21
 * 修改备注：
 * Version: 1.0.0
 */

class Mp3PlayUIDemo : BaseItemUIView() {

    private var path: String? = null
    private val player = RPlayer()

    private val url = "http://audio.klgwl.com/63362/AAC_20171024_133804.aac_t_7.aac"

    override fun createItems(items: MutableList<SingleItem>?) {
        player.init()

        items?.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {

                val textView: RTextView = holder.v(R.id.text_view)

                val fileRadio: RadioButton = holder.v(R.id.file_radio)
                val urlRadio: RadioButton = holder.v(R.id.url_radio)

                //选择音乐
                holder.click(R.id.selector_button) {
                    startIView(UIFileSelectorDialog(if (path.isNullOrEmpty()) "" else File(path).parent) {
                        textView.text = it.absolutePath
                        path = it.absolutePath
                    })
                }

                holder.eV(R.id.edit_text).setText(url, TextView.BufferType.NORMAL)

                //开始播放
                holder.click(R.id.play_button) {
                    //                    if (holder.exV(R.id.edit_text).isEmpty) {
//                        if (!path.isNullOrEmpty()) {
//                            player.startPlay(path!!)
//                        }
//                    } else {
//                        player.startPlay(holder.exV(R.id.edit_text).string())
//                    }
                    if (fileRadio.isChecked) {
                        if (!path.isNullOrEmpty()) {
                            player.startPlay(path!!)
                        }
                    } else if (urlRadio.isChecked) {
                        if (!holder.exV(R.id.edit_text).isEmpty) {
                            player.startPlay(holder.exV(R.id.edit_text).string())
                        }
                    }
                }
                //停止播放
                holder.click(R.id.stop_button) {
                    player.stopPlay()
                }

                //事件监听
                player.onPlayListener = object : RPlayer.OnPlayerListener {
                    override fun onPlayError(what: Int, extra: Int) {
                        L.e("call: onPlayError -> $what $extra")
                    }

                    override fun onPlayCompletion(duration: Int) {
                        L.e("call: onPlayCompletion -> $duration")
                    }

                    override fun onPlayProgress(progress: Int, duration: Int) {
                        L.e("call: onPlayProgress -> $progress")
                        holder.tv(R.id.progress_view).text = RUtils.formatTime(progress.toLong())
                    }

                    override fun onPreparedCompletion(duration: Int) {
                        holder.tv(R.id.duration_view).text = RUtils.formatTime(duration.toLong())
                    }
                }

                initMode(holder)
                initStream(holder)
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_mp3_play
            }
        })
    }

    override fun onViewUnload() {
        super.onViewUnload()
        player.release()
    }

    private fun initStream(holder: RBaseViewHolder) {
        val streamGroup: RadioGroup = holder.v(R.id.stream_group)

        streamGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.alarm_radio -> player.audioStreamType = AudioManager.STREAM_ALARM
                R.id.music_radio -> player.audioStreamType = AudioManager.STREAM_MUSIC
                R.id.ring_radio -> player.audioStreamType = AudioManager.STREAM_RING
                R.id.system_radio -> player.audioStreamType = AudioManager.STREAM_SYSTEM
                R.id.voice_call_radio -> player.audioStreamType = AudioManager.STREAM_VOICE_CALL
            }
        }
    }

    private fun initMode(holder: RBaseViewHolder) {
        val modeGroup: RadioGroup = holder.v(R.id.mode_group)
        fun setMode(mode: Int) {
            val am: AudioManager = mActivity.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            am.isSpeakerphoneOn = false
            am.mode = mode
        }

        modeGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.normal_radio -> setMode(AudioManager.MODE_NORMAL)
                R.id.ringtone_radio -> setMode(AudioManager.MODE_RINGTONE)
                R.id.in_call_radio -> setMode(AudioManager.MODE_IN_CALL)
                R.id.in_communication_radio -> setMode(AudioManager.MODE_IN_COMMUNICATION)
            }
        }
    }
}