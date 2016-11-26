package com.angcyo.demo.uiview;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.angcyo.demo.R;
import com.angcyo.demo.uiview2.UIViewPagerIView;
import com.angcyo.uiview.dialog.UIDialog;
import com.angcyo.uiview.utils.T;
import com.angcyo.uiview.view.UIBaseIViewImpl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by angcyo on 2016-11-12.
 */

public class ScrollerIView extends UIBaseIViewImpl {

    @BindView(R.id.jump_to_view_pager)
    TextView mTextView;

    @Override
    protected View inflateBaseView(FrameLayout container, LayoutInflater inflater) {
        return inflater.inflate(R.layout.scroller_layout, container);
    }

    @OnClick(R.id.jump_to_view_pager)
    public void onJumpToViewPager() {
        //T.show(mContext, "onJumpToViewPager");
        mILayout.startIView(new ViewPagerIView());
    }

    @OnClick(R.id.jump_to_ui_view_pager)
    public void onJumpToUIViewPager() {
        //T.show(mContext, "onJumpToViewPager");
        mILayout.startIView(new UIViewPagerIView());
    }

    @OnClick(R.id.view)
    public void onViewClick(View view) {
        T.show(mContext, "你看到我了吗?");
        mILayout.startIView(new UIDialog());
        mILayout.startIView(new UIDialog().setDialogTitle("标题测试").setGravity(Gravity.TOP));
        mILayout.startIView(new UIDialog().setDialogContent("内容测试").setGravity(Gravity.CENTER));
        mILayout.startIView(new UIDialog().setDialogTitle("标题测试").setDialogContent("标题测试"));
    }

    @OnClick(R.id.view2)
    public void onViewC2lick(View view) {
        mILayout.startIView(new UIDialog()
                .setDialogTitle("很帅的标题很帅的标题很帅的标题很帅的标题很帅的标题很帅的标题")
                .setDialogContent("无知的内容无知的内容无知的内容无知的内容无知的内容无知的内容无知的内容无知的内容"));

        final ViewGroup rootView = (ViewGroup) ((ViewGroup) mRootView).getChildAt(0);
        TextView textView = new TextView(mContext);
        textView.setText("刚创建的VIEW");
        textView.setBackgroundColor(Color.LTGRAY);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewC2lick(v);
            }
        });
        rootView.addView(textView, new ViewGroup.LayoutParams(-1, 500));
    }
}
