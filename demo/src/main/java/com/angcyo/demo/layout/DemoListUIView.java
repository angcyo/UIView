package com.angcyo.demo.layout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.angcyo.demo.R;
import com.angcyo.demo.refresh.RefreshLayoutDemo;
import com.angcyo.demo.uiview.ScrollerIView;
import com.angcyo.demo.uiview.TestDemo;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.resources.ResUtil;
import com.angcyo.uiview.widget.ItemInfoLayout;

/**
 * Created by angcyo on 2016-12-18.
 */

public class DemoListUIView extends UIContentView {

    LinearLayout rootLayout;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        rootLayout = new LinearLayout(mActivity);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE | LinearLayout.SHOW_DIVIDER_END);
        rootLayout.setDividerDrawable(mActivity.getResources().getDrawable(R.drawable.base_shape_line));

        addItem("-> ScrollerIView", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIView(new ScrollerIView());
            }
        });

        addItem("-> RefreshLayoutDemo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIView(new RefreshLayoutDemo());

            }
        });

        addItem("-> TestDemo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIView(new TestDemo());
            }
        });

        baseContentLayout.addView(rootLayout);
    }

    private void addItem(String text, View.OnClickListener clickListener) {
        ItemInfoLayout itemInfoLayout = new ItemInfoLayout(mActivity);
        itemInfoLayout.setItemText(text);
        itemInfoLayout.setOnClickListener(clickListener);
        itemInfoLayout.setBackgroundResource(R.drawable.base_bg_selector);
        itemInfoLayout.setRightDrawableRes(R.drawable.base_next);
        itemInfoLayout.setPadding((int) ResUtil.dpToPx(mActivity.getResources(), 10f), 0, 0, 0);
        itemInfoLayout.setLeftDrawableRes(R.drawable.live_48);
        rootLayout.addView(itemInfoLayout, new ViewGroup.LayoutParams(-1, (int) ResUtil.dpToPx(mActivity.getResources(), 45f)));
    }
}
