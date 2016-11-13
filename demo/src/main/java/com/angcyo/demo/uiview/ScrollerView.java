package com.angcyo.demo.uiview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.angcyo.demo.R;
import com.angcyo.uiview.base.UIBaseView;
import com.angcyo.uiview.container.ILayout;

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
    public View inflateContentView(Context context, ILayout iLayout, FrameLayout container, LayoutInflater inflater) {
        super.inflateContentView(context, iLayout, container, inflater);
        return inflater.inflate(R.layout.scroller_layout, container);
    }

    @OnClick(R.id.jump_to_view_pager)
    public void onJumpToViewPager() {
        //T.show(mContext, "onJumpToViewPager");
        mILayout.startIView(new ViewPagerView());
    }
}
