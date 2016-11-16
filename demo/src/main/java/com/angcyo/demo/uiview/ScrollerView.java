package com.angcyo.demo.uiview;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.angcyo.demo.R;
import com.angcyo.uiview.base.UIBaseView;
import com.angcyo.uiview.dialog.UIDialog;
import com.angcyo.uiview.utils.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by angcyo on 2016-11-12.
 */

public class ScrollerView extends UIBaseView {

    @BindView(R.id.jump_to_view_pager)
    TextView mTextView;

    public ScrollerView() {
    }

    @Override
    protected View inflateBaseView(FrameLayout container, LayoutInflater inflater) {
        return inflater.inflate(R.layout.scroller_layout, container);
    }

    @OnClick(R.id.jump_to_view_pager)
    public void onJumpToViewPager() {
        //T.show(mContext, "onJumpToViewPager");
        mILayout.startIView(new ViewPagerView());
    }

    @OnClick(R.id.view)
    public void onViewClick(View view) {
        T.show(mContext, "你看到我了吗?");
        mILayout.startIView(new UIDialog());
    }
}
