package com.angcyo.uidemo;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.uidemo.activity.SingleInstanceActivity;
import com.angcyo.uidemo.activity.SingleTaskActivity;
import com.angcyo.uidemo.activity.SingleTopActivity;
import com.angcyo.uidemo.activity.StandardActivity;
import com.angcyo.uiview.base.UIContentView;

import java.util.Random;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/30 10:39
 * 修改人员：Robi
 * 修改时间：2016/12/30 10:39
 * 修改备注：
 * Version: 1.0.0
 */
public class NavUIView extends UIContentView {
    TextView mTextView;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_nav_layout);
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();

        mTextView = v(R.id.text_view);
        click(R.id.open_url, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenUrlClick();
            }
        });

        final Uri data = mActivity.getIntent().getData();
        if (data != null) {
            StringBuilder builder = new StringBuilder(data.toString());
            builder.append("\nScheme:");
            builder.append(data.getScheme());
            builder.append("\nAuthority:");
            builder.append(data.getAuthority());
            builder.append("\nHost:");
            builder.append(data.getHost());
            builder.append("\nEncodedPath:");
            builder.append(data.getEncodedPath());
            builder.append("\nEncodedQuery:");
            builder.append(data.getEncodedQuery());
            builder.append("\nPath:");
            builder.append(data.getPath());
            builder.append("\nQueryParameterNames:");
            builder.append(data.getQueryParameterNames());
            builder.append("\n");

            mTextView.setText(builder.toString());
        } else {
            mTextView.setText("--");
        }

        mViewHolder.v(R.id.single_instance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleInstanceActivity.launcher(mActivity, SingleInstanceActivity.class);
            }
        });
        mViewHolder.v(R.id.single_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleTaskActivity.launcher(mActivity, SingleTaskActivity.class);
            }
        });
        mViewHolder.v(R.id.single_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleTopActivity.launcher(mActivity, SingleTopActivity.class);
            }
        });
        mViewHolder.v(R.id.standard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StandardActivity.launcher(mActivity, StandardActivity.class);
            }
        });
    }

    private void openUrl(String url) {
        Uri webPage = Uri.parse(url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webPage);
        mActivity.startActivity(webIntent);
    }

    public void onOpenUrlClick() {
        if (new Random().nextInt(10) > 4) {
//            openUrl("open://angcyo.com/");
            openUrl("open://angcyo.com/");
        } else {
            openUrl("open://angcyo.com/?arg0=1&arg1=2");
        }
    }
}
