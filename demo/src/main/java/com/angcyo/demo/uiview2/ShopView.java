package com.angcyo.demo.uiview2;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.demo.R;
import com.angcyo.library.utils.L;
import com.angcyo.uiview.base.UIBaseView;
import com.angcyo.uiview.container.UITitleBarContainer;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.utils.Reflect;
import com.angcyo.uiview.widget.UIViewPager;

/**
 * Created by angcyo on 2016-11-26.
 */

public class ShopView extends UIBaseView {

    private UITitleBarContainer mTitleBarContainer;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        mTitleBarContainer = new UITitleBarContainer(mContext);
        linearLayout.addView(mTitleBarContainer, new ViewGroup.LayoutParams(-1, -2));

        TextView textView = new TextView(mContext);
        textView.setText(this.getClass().getSimpleName());
        textView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0);
        layoutParams.weight = 1;
        textView.setBackgroundColor(Color.BLUE);
        linearLayout.addView(textView, layoutParams);

        baseContentLayout.addView(linearLayout, new ViewGroup.LayoutParams(-1, -1));
    }

    @Override
    public void onShowInPager(UIViewPager viewPager) {
        L.w(this.getClass().getSimpleName() + " " + Reflect.getMethodName());
        postDelayed(new Runnable() {
            @Override
            public void run() {
                showContentLayout();
                mTitleBarContainer.setTitleBarPattern(TitleBarPattern.build()
                        .setTitleString("商城列表")
                        .setTitleBarBGColor(mContext.getResources().getColor(R.color.theme_color_primary)));
            }
        }, 1000);
    }

    @Override
    public void onHideInPager(UIViewPager viewPager) {
        L.w(this.getClass().getSimpleName() + " " + Reflect.getMethodName());
    }
}
