package com.angcyo.uidemo.uiview;

import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.uidemo.R;
import com.angcyo.uidemo.uiview2.UIViewPagerIView;
import com.angcyo.uidemo.uiview3.view.DialogLoginView;
import com.angcyo.uidemo.uiview3.view.LoginView;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.dialog.UIDialog;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.utils.T;
import com.angcyo.uiview.widget.ExEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by angcyo on 2016-11-12.
 */

public class ScrollerIView extends UIContentView {

    @BindView(R.id.jump_to_view_pager)
    TextView mTextView;

    @BindView(R.id.edit_text_view2)
    ExEditText mExEditText2;
    @BindView(R.id.edit_text_view3)
    ExEditText mExEditText3;
    @BindView(R.id.first_layout)
    LinearLayout mFirstLayout;
    @BindView(R.id.scroller_layout_root)
    NestedScrollView mScrollerLayoutRoot;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.scroller_layout);
    }

    @Override
    protected TitleBarPattern getTitleBar() {
        return null;
    }

    @OnClick(R.id.edit_text_view1)
    public void onEditText1Click() {
        mFirstLayout.removeView(mExEditText2);
    }

    @OnClick(R.id.test_button)
    public void onTestButtonClick() {
        if (mExEditText3.getParent() == null) {
            mFirstLayout.addView(mExEditText3, 2);
        } else {
            mFirstLayout.removeView(mExEditText3);
        }
    }

    @OnClick(R.id.jump_to_view_pager)
    public void onJumpToViewPager() {

        //T.show(mActivity, "onJumpToViewPager");
        mILayout.startIView(new ViewPagerIView());
    }

    @OnClick(R.id.jump_to_ui_view_pager)
    public void onJumpToUIViewPager() {
        //T.show(mActivity, "onJumpToViewPager");
        mILayout.startIView(new UIViewPagerIView());
    }

    @OnClick(R.id.view)
    public void onViewClick(View view) {
        T.show(mActivity, "你看到我了吗?");
        mILayout.startIView(UIDialog.build());
        mILayout.startIView(UIDialog.build().setDialogTitle("标题测试").setGravity(Gravity.TOP));
        mILayout.startIView(UIDialog.build().setDialogContent("内容测试").setGravity(Gravity.CENTER));
        mILayout.startIView(UIDialog.build().setDialogTitle("标题测试").setDialogContent("标题测试"));
    }

    @OnClick(R.id.view2)
    public void onViewC2lick(View view) {
        mILayout.startIView(UIDialog.build()
                .setDialogTitle("很帅的标题很帅的标题很帅的标题很帅的标题很帅的标题很帅的标题")
                .setDialogContent("无知的内容无知的内容无知的内容无知的内容无知的内容无知的内容无知的内容无知的内容"));

        final ViewGroup rootView = (ViewGroup) ((ViewGroup) mRootView).getChildAt(0);
        TextView textView = new TextView(mActivity);
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

    @OnClick(R.id.login_button)
    public void onLoginClick(View view) {
        startIView(new LoginView());
    }

    @OnClick(R.id.dialog_login_button)
    public void onDialogLoginClick(View view) {
        startIView(new DialogLoginView());
    }
}
