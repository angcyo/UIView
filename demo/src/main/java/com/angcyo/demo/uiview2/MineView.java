package com.angcyo.demo.uiview2;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.base.UIBaseView;
import com.angcyo.uiview.utils.Reflect;
import com.angcyo.uiview.widget.UIViewPager;

/**
 * Created by angcyo on 2016-11-26.
 */

public class MineView extends UIBaseView {

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        TextView textView = new TextView(mContext);
        textView.setText(this.getClass().getSimpleName());
        textView.setGravity(Gravity.CENTER);
        baseContentLayout.addView(textView, new ViewGroup.LayoutParams(-1, -1));
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
