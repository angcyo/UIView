package com.angcyo.uidemo.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.angcyo.fragment.base.UIFragmentActivity
import com.angcyo.fragment.widget.layout.FragmentDebugLayout
import com.angcyo.library.utils.L
import com.angcyo.uidemo.R
import com.angcyo.uidemo.fragment.ui.Main2Fragment
import com.angcyo.uidemo.fragment.ui.MainFragment

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/07/16 13:42
 * 修改人员：Robi
 * 修改时间：2018/07/16 13:42
 * 修改备注：
 * Version: 1.0.0
 */
class MainFragmentActivity : UIFragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fragment_layout)

        findViewById<View>(R.id.add_button).setOnClickListener {
            val lastFragment = supportFragmentManager.fragments.lastOrNull()
            if (lastFragment == null) {
                supportFragmentManager.beginTransaction()
                        //.setCustomAnimations(R.anim.top_in, R.anim.top_out, R.anim.top_in, R.anim.top_out)
                        .add(R.id.content_layout, MainFragment(), MainFragment::class.java.simpleName)
                        .addToBackStack(MainFragment::class.java.simpleName)
                        .commit()
            } else {
                supportFragmentManager.beginTransaction()
                        //.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out)
                        .hide(lastFragment)
                        .add(R.id.content_layout, Main2Fragment(), MainFragment::class.java.simpleName)
                        .addToBackStack(MainFragment::class.java.simpleName)
                        .commit()
            }
        }

        findViewById<View>(R.id.replace_button).setOnClickListener {
            //            supportFragmentManager.beginTransaction()
//                    .replace(R.id.content_layout, MainFragment(), MainFragment::class.java.simpleName)
//                    .addToBackStack(MainFragment::class.java.simpleName)
//                    .commit()

            supportFragmentManager.popBackStack()
            supportFragmentManager.beginTransaction()
                    .add(R.id.content_layout, MainFragment(), MainFragment::class.java.simpleName)
                    .addToBackStack(MainFragment::class.java.simpleName)
                    .commit()
        }

        findViewById<View>(R.id.replace_back_button).setOnClickListener {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.content_layout, MainFragment(), MainFragment::class.java.simpleName)
                    .addToBackStack(MainFragment::class.java.simpleName)
                    .commit()
        }

        findViewById<View>(R.id.pop_button).setOnClickListener {
            supportFragmentManager.popBackStack()
            supportFragmentManager.popBackStack()
        }

        findViewById<View>(R.id.remove_button).setOnClickListener {
            supportFragmentManager.fragments.lastOrNull()?.let {
                supportFragmentManager.beginTransaction()
                        .remove(it)
                        //.addToBackStack(MainFragment::class.java.simpleName)
                        .commit()
            }
        }

        findViewById<View>(R.id.show_button).setOnClickListener {
            supportFragmentManager.fragments.lastOrNull()?.let {
                supportFragmentManager.beginTransaction()
                        .show(it)
                        //.addToBackStack(MainFragment::class.java.simpleName)
                        .commit()
            }
        }

        findViewById<View>(R.id.hide_button).setOnClickListener {
            supportFragmentManager.fragments.lastOrNull()?.let {
                supportFragmentManager.beginTransaction()
                        .hide(it)
                        //.addToBackStack(MainFragment::class.java.simpleName)
                        .commit()
            }
        }

        findViewById<View>(R.id.remove_button).setOnClickListener {
            supportFragmentManager.fragments.lastOrNull()?.let {
                supportFragmentManager.beginTransaction()
                        .remove(it)
                        //.addToBackStack(MainFragment::class.java.simpleName)
                        .commit()
            }
        }

        findViewById<View>(R.id.detach_button).setOnClickListener {
            supportFragmentManager.fragments.lastOrNull()?.let {
                supportFragmentManager.beginTransaction()
                        .detach(it)
                        //.addToBackStack(MainFragment::class.java.simpleName)
                        .commit()
            }
        }

        findViewById<View>(R.id.attach_button).setOnClickListener {
            supportFragmentManager.fragments.lastOrNull()?.let {
                supportFragmentManager.beginTransaction()
                        .attach(it)
                        //.addToBackStack(MainFragment::class.java.simpleName)
                        .commit()
            }
        }

        findViewById<View>(R.id.log_button).setOnClickListener {
            val build = StringBuilder()
            supportFragmentManager.fragments.forEachIndexed { index, fragment ->
                build.append("\n")
                build.append(" $index ->")
                build.append(fragment)
            }
            L.w(build.toString())

            findViewById<TextView>(R.id.text_view).text = build.toString()
        }
    }

    override fun onBackPressed() {
        val debugLayout: FragmentDebugLayout = findViewById(R.id.content_layout)
        if (debugLayout.isInDebugLayout) {
            debugLayout.closeDebugLayout()
            return
        }
        super.onBackPressed()
    }
}
