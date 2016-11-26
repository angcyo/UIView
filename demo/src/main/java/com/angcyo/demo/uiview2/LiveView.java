package com.angcyo.demo.uiview2;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.utils.Reflect;
import com.angcyo.uiview.view.UIBaseIViewImpl;
import com.angcyo.uiview.widget.UIViewPager;

/**
 * Created by angcyo on 2016-11-26.
 */

public class LiveView extends UIBaseIViewImpl {
    @Override
    protected View inflateBaseView(FrameLayout container, LayoutInflater inflater) {
        TextView textView = new TextView(mContext);
        textView.setText(this.getClass().getSimpleName());
        textView.setGravity(Gravity.CENTER);
        container.addView(textView, new ViewGroup.LayoutParams(-1, -1));
        return textView;
    }

    @Override
    public void onShowInPager(UIViewPager viewPager) {
        L.w(this.getClass().getSimpleName() + " " + Reflect.getMethodName());
    }

    @Override
    public void onHideInPager(UIViewPager viewPager) {
        L.w(this.getClass().getSimpleName() + " " + Reflect.getMethodName());
    }
}
