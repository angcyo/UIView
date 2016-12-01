package com.angcyo.demo.uiview2;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.angcyo.demo.R;
import com.angcyo.library.utils.L;
import com.angcyo.uiview.base.UIBaseView;
import com.angcyo.uiview.container.UITitleBarContainer;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.net.base.Network;
import com.angcyo.uiview.utils.Reflect;
import com.angcyo.uiview.utils.T;
import com.angcyo.uiview.widget.UIViewPager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by angcyo on 2016-11-26.
 */

public class MineView extends UIBaseView {

    @BindView(R.id.base_title_bar_container)
    UITitleBarContainer mBaseTitleBarContainer;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        inflater.inflate(R.layout.view_mine_layout, baseContentLayout);
    }

    @Override
    protected void initContentLayout() {

    }

    @Override
    public void onShowInPager(UIViewPager viewPager) {
        L.w(this.getClass().getSimpleName() + " " + Reflect.getMethodName());
        postDelayed(new Runnable() {
            @Override
            public void run() {
                showContentLayout();
                ArrayList<TitleBarPattern.TitleBarItem> items = new ArrayList<TitleBarPattern.TitleBarItem>();
                items.add(TitleBarPattern.TitleBarItem.build().setRes(R.drawable.home_48).setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.show(mContext, "Home " + Network.getNetTypeName(mContext));
                    }
                }));
                items.add(TitleBarPattern.TitleBarItem.build().setRes(R.drawable.live_48).setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.show(mContext, "Love " + Network.isConnected(mContext));
                    }
                }));
                ArrayList<TitleBarPattern.TitleBarItem> items2 = new ArrayList<TitleBarPattern.TitleBarItem>();
                items2.addAll(items);
                mBaseTitleBarContainer.appendTitleBarPattern(TitleBarPattern.build().setRightItems(items).setLeftItems(items2));
            }
        }, 1000);
    }

    @Override
    public void onHideInPager(UIViewPager viewPager) {
        L.w(this.getClass().getSimpleName() + " " + Reflect.getMethodName());
    }
}
