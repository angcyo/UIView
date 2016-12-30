package com.angcyo.uidemo.layout;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.angcyo.uidemo.NavUIView;
import com.angcyo.uidemo.R;
import com.angcyo.uidemo.layout.demo.EmojiUIView;
import com.angcyo.uidemo.layout.demo.ExEmojiUIView;
import com.angcyo.uidemo.layout.demo.RTLUIView;
import com.angcyo.uidemo.layout.demo.SwipeRecyclerViewUIView;
import com.angcyo.uidemo.refresh.RefreshLayoutDemo;
import com.angcyo.uidemo.uiview.ScrollerIView;
import com.angcyo.uidemo.uiview.TestDemo;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.container.UILayoutImpl;
import com.angcyo.uiview.resources.ResUtil;
import com.angcyo.uiview.widget.ItemInfoLayout;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by angcyo on 2016-12-18.
 */

public class DemoListUIView extends UIContentView {

    LinearLayout rootLayout;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        NestedScrollView scrollView = new NestedScrollView(mActivity);

        rootLayout = new LinearLayout(mActivity);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE | LinearLayout.SHOW_DIVIDER_END);
        rootLayout.setDividerDrawable(mActivity.getResources().getDrawable(R.drawable.base_shape_line));

        addItem("-> Scroller IView", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIView(new ScrollerIView());
            }
        });

        addItem("-> Refresh Layout Demo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIView(new RefreshLayoutDemo());
            }
        });

        addItem("-> Test Demo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIView(new TestDemo());
            }
        });

        addItem("-> RTL Demo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIView(new RTLUIView());
            }
        });

        addItem("-> Emoji Layout Demo (Android5.+)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIView(new EmojiUIView());
            }
        });

        addItem("-> ExEmoji Layout Demo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIView(new ExEmojiUIView());
            }
        });

        addItem("-> Swipe RecyclerView Demo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIView(new SwipeRecyclerViewUIView());
            }
        });

        addItem("-> Link Url Demo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIView(new NavUIView());
            }
        });

        addItem("-> Center Radio Button Demo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIView(new NavUIView());
            }
        });

        scrollView.addView(rootLayout, new ViewGroup.LayoutParams(-1, -1));
        baseContentLayout.addView(scrollView);
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

    @Override
    public void onViewShow(Bundle bundle) {
        super.onViewShow(bundle);
        ((UILayoutImpl) mILayout).unlock();

        Set<String> set = new HashSet<>();
        set.add("123");
        set.add("123");
        set.add("123");
        set.size();

        Set<Integer> set2 = new HashSet<>();
        set2.add(1);
        set2.add(1);
        set2.add(2);
        set2.size();
    }
}
