package com.angcyo.uidemo.activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.angcyo.uidemo.R

/**
 * Created by angcyo on 2017-06-03.
 */
abstract class BaseLaunchModeActivity : BaseActivity() {
    var log: String = ""
    var logView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_launch_mode)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            onFabClick()
        }

        LauncherMode.test(this)

        val textView = findViewById(R.id.text_view) as TextView
        textView.text = getTextString()

        logView = findViewById(R.id.log_view) as TextView
        logView?.text = log
    }

    override fun onLogActivity(log: String?) {
        this.log = log ?: ""
        logView?.text = log
    }

    fun getTextString(): CharSequence {
        return title
    }

    abstract fun onFabClick()
}
