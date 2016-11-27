package com.angcyo.demo.uiview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.angcyo.demo.R;
import com.angcyo.uiview.view.UIIViewImpl;
import com.angcyo.uiview.container.ILayout;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.utils.T;

import java.util.ArrayList;

/**
 * Created by angcyo on 2016-11-06.
 */

public class DemoIView extends UIIViewImpl {

    private Context mContext;
    private ILayout mUIContainer;

    @Override
    public TitleBarPattern loadTitleBar(Context context) {
        mContext = context;
        TitleBarPattern pattern = new TitleBarPattern();
        pattern.setTitleString("测试标题1")
                .setShowBackImageView(false)
                .setLeftItems(getLeftItems())
                .setRightItems(getRightItems())
                .setTitleBarBGColor(Color.GREEN);
        return pattern;
    }

    private ArrayList<TitleBarPattern.TitleBarItem> getRightItems() {
        ArrayList<TitleBarPattern.TitleBarItem> items = new ArrayList<>();
        items.add(TitleBarPattern.TitleBarItem.build()
                .setRes(R.drawable.iconfontjixieqimo));
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
    protected View inflateBaseView(FrameLayout container, LayoutInflater inflater) {
        final View view = inflater.inflate(R.layout.content_main, container);
        container.getChildAt(container.getChildCount() - 1).findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUIContainer.startIView(new Demo2IView());
            }
        });
        return view;
    }
}
