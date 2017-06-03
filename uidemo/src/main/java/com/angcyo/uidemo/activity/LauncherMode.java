package com.angcyo.uidemo.activity;

import android.app.Activity;
import android.view.View;

import com.angcyo.uidemo.R;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/05/19 16:02
 * 修改人员：Robi
 * 修改时间：2017/05/19 16:02
 * 修改备注：
 * Version: 1.0.0
 */
public class LauncherMode {
    public static void test(final Activity activity) {
        activity.findViewById(R.id.single_instance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleInstanceActivity.launcher(activity, SingleInstanceActivity.class);
            }
        });
        activity.findViewById(R.id.single_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleTaskActivity.launcher(activity, SingleTaskActivity.class);
            }
        });
        activity.findViewById(R.id.single_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleTopActivity.launcher(activity, SingleTopActivity.class);
            }
        });
        activity.findViewById(R.id.standard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StandardActivity.launcher(activity, StandardActivity.class);
            }
        });
        activity.findViewById(R.id.new_affinity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewAffinityActivity.launcher(activity, NewAffinityActivity.class);
            }
        });
    }
}
