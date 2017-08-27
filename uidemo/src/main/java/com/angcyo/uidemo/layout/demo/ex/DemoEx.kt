package com.angcyo.uidemo.layout.demo.ex

import android.view.View
import com.angcyo.uiview.utils.ScreenUtil
import com.angcyo.uiview.utils.T_

/**
 * Created by angcyo on 2017-08-27.
 */
public fun View.show() {
    T_.show("W:$measuredWidth H:$measuredHeight   SW:${ScreenUtil.screenWidth} SH:${ScreenUtil.screenHeight} DPI:${ScreenUtil.densityDpi}")
}
