package com.angcyo.uidemo.fragment.base

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import com.angcyo.fragment.ui.RFragment
import com.angcyo.library.utils.L
import com.angcyo.uiview.resources.AnimUtil

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/07/16 16:35
 * 修改人员：Robi
 * 修改时间：2018/07/16 16:35
 * 修改备注：
 * Version: 1.0.0
 */
open class LogInfoFragment : RFragment() {
    override fun setExitTransition(transition: Any?) {
        super.setExitTransition(transition)
        log("call: setExitTransition -> ")
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        log("call: onConfigurationChanged -> ")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        log("call: onAttach -> ")
    }

    override fun onPause() {
        super.onPause()
        log("call: onPause -> ")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        log("call: onActivityResult -> ")
    }

    override fun onInflate(context: Context?, attrs: AttributeSet?, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
        log("call: onInflate -> ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("call: onViewCreated -> ")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        log("call: onActivityCreated -> ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("call: onCreate -> ")
    }

    override fun onStart() {
        super.onStart()
        log("call: onStart -> ")
    }

    override fun onResume() {
        super.onResume()
        log("call: onResume -> ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        log("call: onSaveInstanceState -> ")
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        log("call: onCreateAnimation -> $transit $enter $nextAnim")
        if (enter) {
            return AnimUtil.translateStartAnimation()
        } else {
            return AnimUtil.translateFinishAnimation()
        }
    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        log("call: onCreateAnimator -> ")
        return super.onCreateAnimator(transit, enter, nextAnim)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        log("call: onCreateView -> ")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)
        log("call: onAttachFragment -> ")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        log("call: onHiddenChanged -> $hidden")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        log("call: setUserVisibleHint -> $isVisibleToUser")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log("call: onDestroyView -> ")
    }

    override fun onStop() {
        super.onStop()
        log("call: onStop -> ")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("call: onDestroy -> ")
    }

    protected fun log(log: String) {
        L.e("fragment", log)
    }
}