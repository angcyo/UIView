package com.angcyo.uidemo.uiview2;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angcyo.library.utils.L;
import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIBaseView;
import com.angcyo.uiview.container.ContentLayout;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RRecyclerView;
import com.angcyo.uiview.recycler.adapter.RBaseAdapter;
import com.angcyo.uiview.resources.ResUtil;
import com.angcyo.uiview.utils.Reflect;
import com.angcyo.uiview.widget.viewpager.UIViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angcyo on 2016-11-26.
 */

public class MessageView extends UIBaseView {

    RRecyclerView mRecyclerView;

    int count = 0;
    ArrayList<TextView> mArrayList = new ArrayList<>();
    private RBaseAdapter<String> mBaseAdapter;

    @Override
    protected void inflateContentLayout(ContentLayout baseContentLayout, LayoutInflater inflater) {

//        TextView textView = new TextView(mActivity);
//        textView.setText(this.getClass().getSimpleName());
//        textView.setGravity(Gravity.CENTER);
//        baseContentLayout.addView(textView, new ViewGroup.LayoutParams(-1, -1));
        inflater.inflate(R.layout.view_recycler_view_demo_layout, baseContentLayout);
    }

    @Override
    protected void initOnShowContentLayout() {
        mRecyclerView = v(R.id.recycler_view);
        mBaseAdapter = new RBaseAdapter<String>(mActivity) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return 0;
            }

            @Override
            protected View createContentView(ViewGroup parent, int viewType) {
                TextView textView = new TextView(mActivity);
                textView.setText(this.getClass().getSuperclass().getSimpleName() + " " + count++);
                textView.setLayoutParams(new ViewGroup.LayoutParams(-1, (int) ResUtil.dpToPx(mActivity.getResources(), 50)));
                mArrayList.add(textView);
                return textView;
            }

            @Override
            protected void onBindView(RBaseViewHolder holder, int position, String bean) {

            }
        };

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    logInfo();
                }
            }
        });

        mRecyclerView.setAdapter(mBaseAdapter);
        mBaseAdapter.resetData(getDatas());
        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(mBaseAdapter.getItemCount() - 1, 0);
    }

    private void logInfo() {
        L.i("-----------------------------start-----------------------------");
        for (int i = 0; i < mArrayList.size(); i++) {
            L.i("第" + i + "个:" + mArrayList.get(i).getText());
        }
        L.i("-----------------------------end-----------------------------");
    }

    private List<String> getDatas() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i + "");
        }
        return list;
    }

    @Override
    public void onShowInPager(UIViewPager viewPager) {
        L.w(this.getClass().getSimpleName() + " " + Reflect.getMethodName());
        postDelayed(new Runnable() {
            @Override
            public void run() {
                showContentLayout();
            }
        }, 2000);
    }

    @Override
    public void onHideInPager(UIViewPager viewPager) {
        L.w(this.getClass().getSimpleName() + " " + Reflect.getMethodName());
    }
}
