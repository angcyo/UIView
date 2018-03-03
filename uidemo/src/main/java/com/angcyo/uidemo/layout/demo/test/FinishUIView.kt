package com.angcyo.uidemo.layout.demo.test

import android.os.Bundle

/**
 * Created by angcyo on 2018-03-03.
 */
class FinishUIView : BaseTestUIView() {
    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
    }

    override fun onViewShowFirst(bundle: Bundle?) {
        super.onViewShowFirst(bundle)
        showDialog()

        postDelayed(2000) {
            finishIView()
        }
    }
}
