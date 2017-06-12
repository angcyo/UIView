package com.angcyo.uidemo.layout.demo;

import android.graphics.Color;

import com.angcyo.uidemo.R;
import com.angcyo.uidemo.layout.base.BaseItemUIView;
import com.angcyo.uidemo.layout.demo.view.SegmentStepView2;
import com.angcyo.uiview.base.Item;
import com.angcyo.uiview.base.SingleItem;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.widget.ExEditText;
import com.angcyo.uiview.widget.RSeekBar;
import com.angcyo.uiview.widget.SimpleProgressBar;
import com.lzy.imagepicker.view.MaterialProgressView;
import com.lzy.imagepicker.view.SimpleCircleProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/26 14:53
 * 修改人员：Robi
 * 修改时间：2017/04/26 14:53
 * 修改备注：
 * Version: 1.0.0
 */
public class CustomViewUIView extends BaseItemUIView {
    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == 0) {
            return R.layout.item_custom_view1;
        }
        return R.layout.item_custom_view2;
    }

    @Override
    protected void createItems(List<SingleItem> items) {
        items.add(new SingleItem() {
            @Override
            public void onBindView(final RBaseViewHolder holder, final int posInData, Item dataBean) {
                RSeekBar seekBar = holder.v(R.id.seek_bar);
                seekBar.addOnProgressChangeListener(new RSeekBar.OnProgressChangeListener() {
                    @Override
                    public void onProgress(int progress) {
                        holder.tv(R.id.progress_view).setText(progress + "");
                    }
                });

                ExEditText editText = holder.v(R.id.input_tip_view);
                List<String> list = new ArrayList<>();
                list.add("1885555555555");
                list.add("1888866666666");
                list.add("1888888888888");
                list.add("1888877777777");
                editText.setInputTipTextList(list);
            }
        });
        items.add(new SingleItem() {
            @Override
            public void onBindView(final RBaseViewHolder holder, final int posInData, Item dataBean) {
                SegmentStepView2 segmentStepView2 = holder.v(R.id.segment_step_view2);
                List<String> values = new ArrayList<>();
                values.add("较差");
                values.add("中等");
                values.add("良好");
                values.add("优秀");
                values.add("极好");
                segmentStepView2.setSteps(values);

                List<Integer> colors = new ArrayList<>();
                colors.add(Color.BLUE);
                colors.add(Color.CYAN);
                colors.add(Color.RED);
                colors.add(Color.GREEN);
                colors.add(Color.YELLOW);
                colors.add(Color.YELLOW);
                segmentStepView2.setStepColors(colors);

                segmentStepView2.setCurProgress(70);

                SimpleProgressBar progressBar = holder.v(R.id.progress_bar);
                progressBar.setIncertitudeProgress(true);

                MaterialProgressView materialProgressView = holder.v(R.id.material_progress_view);
                materialProgressView.start();

                SimpleCircleProgressBar simpleCircleProgressBar = holder.v(R.id.simple_progress_view);
                simpleCircleProgressBar.start();
            }
        });
    }
}
