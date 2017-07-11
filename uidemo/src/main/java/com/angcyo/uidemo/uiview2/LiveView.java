package com.angcyo.uidemo.uiview2;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.base.UIBaseView;
import com.angcyo.uiview.container.ContentLayout;
import com.angcyo.uiview.utils.Reflect;
import com.angcyo.uiview.widget.viewpager.UIViewPager;

/**
 * Created by angcyo on 2016-11-26.
 */

public class LiveView extends UIBaseView {

    @Override
    protected void inflateContentLayout(ContentLayout baseContentLayout, LayoutInflater inflater) {
        TextView textView = new TextView(mActivity);
        textView.setText(this.getClass().getSimpleName());
        textView.setGravity(Gravity.CENTER);
        baseContentLayout.addView(textView, new ViewGroup.LayoutParams(-1, -1));
    }

    @Override
    public void onShowInPager(UIViewPager viewPager) {
        L.w(this.getClass().getSimpleName() + " " + Reflect.getMethodName());
        postDelayed(new Runnable() {
            @Override
            public void run() {
                showNonetLayout();
            }
        }, 2000);
    }

    @Override
    public void onHideInPager(UIViewPager viewPager) {
        L.w(this.getClass().getSimpleName() + " " + Reflect.getMethodName());
    }
}
