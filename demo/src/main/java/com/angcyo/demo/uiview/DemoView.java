package com.angcyo.demo.uiview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.angcyo.demo.R;
import com.angcyo.uiview.container.UIContainer;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.utils.T;
import com.angcyo.uiview.view.IView;

import java.util.ArrayList;

/**
 * Created by angcyo on 2016-11-06.
 */

public class DemoView implements IView {

    private Context mContext;
    private UIContainer mUIContainer;

    @Override
    public TitleBarPattern loadTitleBar(Context context) {
        mContext = context;
        TitleBarPattern pattern = new TitleBarPattern();
        pattern.setTitleString("测试标题1")
                .setShowBackImageView(true)
                .setLeftItems(getLeftItems())
                .setRightItems(getRightItems())
                .setTitleBarBGColor(Color.GREEN);
        return pattern;
    }

    private ArrayList<TitleBarPattern.TitleBarItem> getRightItems() {
        ArrayList<TitleBarPattern.TitleBarItem> items = new ArrayList<>();
        items.add(TitleBarPattern.TitleBarItem.build()
                .setRes(R.drawable.iconfontjixieqimo).setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.show(mContext, "设置");
                    }
                }));
        items.add(TitleBarPattern.TitleBarItem.build()
                .setRes(R.drawable.wxbbiaowang).setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.show(mContext, "皇冠");
                    }
                }));
        items.add(TitleBarPattern.TitleBarItem.build()
                .setRes(R.drawable.wxbgongju).setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.show(mContext, "扳手");
                    }
                }));
        return items;
    }

    private ArrayList<TitleBarPattern.TitleBarItem> getLeftItems() {
        return getRightItems();
    }

    @Override
    public View loadContentView(Context context, UIContainer uiContainer, FrameLayout container, LayoutInflater inflater) {
        mContext = context;
        mUIContainer = uiContainer;
        final View view = inflater.inflate(R.layout.content_main, container);
        container.getChildAt(container.getChildCount() - 1).findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUIContainer.startView(new Demo2View());
            }
        });
        return view;
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewLoad() {

    }

    @Override
    public void onViewShow() {

    }

    @Override
    public void onViewHide() {

    }

    @Override
    public void onViewUnload() {

    }
}
