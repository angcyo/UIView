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

public class Demo2View implements IView {

    private Context mContext;
    private UIContainer mUIContainer;

    @Override
    public TitleBarPattern loadTitleBar(Context context) {
        mContext = context;
        TitleBarPattern pattern = new TitleBarPattern();
        pattern.setTitleString("测试标题2")
                .setShowBackImageView(true)
                .setLeftItems(getLeftItems())
                .setRightItems(getRightItems())
                .setTitleBarBGColor(Color.BLUE);
        return pattern;
    }

    private ArrayList<TitleBarPattern.TitleBarItem> getRightItems() {
        ArrayList<TitleBarPattern.TitleBarItem> items = new ArrayList<>();
        items.add(TitleBarPattern.TitleBarItem.build()
                .setText("Demo").setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.show(mContext, "Demo");
                    }
                }));
        items.add(TitleBarPattern.TitleBarItem.build()
                .setText("Item").setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.show(mContext, "Item");
                    }
                }));
        return items;
    }

    private ArrayList<TitleBarPattern.TitleBarItem> getLeftItems() {
        return getRightItems();
    }

    @Override
    public View loadContentView(Context context, final UIContainer uiContainer, FrameLayout container, LayoutInflater inflater) {
        mContext = context;
        mUIContainer = uiContainer;
        final View view = inflater.inflate(R.layout.content_main2, container);
        container.getChildAt(container.getChildCount() - 1).findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiContainer.startView(new Demo3View());
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
