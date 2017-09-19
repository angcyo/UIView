package com.angcyo.uidemo.layout.demo;

import android.graphics.Color;
import android.view.View;

import com.angcyo.uidemo.R;
import com.angcyo.uidemo.layout.base.BaseItemUIView;
import com.angcyo.uidemo.layout.demo.view.RProgressBar;
import com.angcyo.uidemo.layout.demo.view.SegmentStepView2;
import com.angcyo.uiview.anim.RotateAnimation;
import com.angcyo.uiview.base.Item;
import com.angcyo.uiview.base.SingleItem;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.utils.T_;
import com.angcyo.uiview.widget.BlockSeekBar;
import com.angcyo.uiview.widget.ExEditText;
import com.angcyo.uiview.widget.RRatingBar;
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
        if (viewType == 1) {
            return R.layout.item_custom_view2;
        }
        if (viewType == 2) {
            return R.layout.item_custom_view3;
        }
        if (viewType == 3) {
            return R.layout.item_custom_view4;
        }
        if (viewType == 4) {
            return R.layout.item_custom_view5;
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
                    public void onProgress(int progress, boolean fromTouch) {
                        holder.tv(R.id.progress_view).setText(progress + "");
                    }

                    @Override
                    public void onStartTouch() {

                    }

                    @Override
                    public void onStopTouch() {

                    }

                });

                final ExEditText editText = holder.v(R.id.input_tip_view);
                List<String> list = new ArrayList<>();
                list.add("1885555555555");
                list.add("1888866666666");
                list.add("1888888888888");
                list.add("1888877777777");
                editText.setInputTipTextList(list);


                holder.click(R.id.roll_view, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExEditText editText1 = holder.v(R.id.edit_anim_text);
                        T_.show(editText1.getInputNumber() + "");
                        editText1.rollTo(0.88f, 0.0f, 12.88f, 30);
                    }
                });
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
        items.add(new SingleItem() {
            @Override
            public void onBindView(final RBaseViewHolder holder, final int posInData, Item dataBean) {
                holder.click(R.id.button1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T_.show(System.currentTimeMillis() + "  button1");
                    }
                });
                holder.click(R.id.button2, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T_.show(System.currentTimeMillis() + " button2");
                    }
                });

                SimpleProgressBar simpleProgressBar1 = holder.v(R.id.progress_view1);
                simpleProgressBar1.setProgress(50);
                SimpleProgressBar simpleProgressBar2 = holder.v(R.id.progress_view2);
                simpleProgressBar2.setIncertitudeProgress(true);

                holder.click(R.id.progress_bar, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((RProgressBar) v).animToProgress(100);
                    }
                });
            }
        });
        items.add(new SingleItem() {
            @Override
            public void onBindView(final RBaseViewHolder holder, final int posInData, Item dataBean) {
                final View view = holder.v(R.id.image_view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.startAnimation(new RotateAnimation(view.getMeasuredWidth() / 2, view.getMeasuredHeight() / 2));
                    }
                });
            }
        });
        items.add(new SingleItem() {
            @Override
            public void onBindView(final RBaseViewHolder holder, final int posInData, Item dataBean) {
                BlockSeekBar blockSeekBar = holder.v(R.id.block_seek_bar);
                blockSeekBar.setBlockSeekListener(new BlockSeekBar.OnBlockSeekListener() {
                    @Override
                    public void onTouchStart(BlockSeekBar view) {
                        super.onTouchStart(view);
                    }

                    @Override
                    public void onSeekChange(BlockSeekBar view, int startX, int endX) {
                        super.onSeekChange(view, startX, endX);
                    }

                    @Override
                    public void onTouchEnd(BlockSeekBar view) {
                        super.onTouchEnd(view);
                    }
                });

                RRatingBar ratingBar = holder.v(R.id.rating_bar);
                ratingBar.setRatingNormalDrawable(getDrawable(R.drawable.live_48));
                ratingBar.setRatingSelectorDrawable(getDrawable(R.drawable.live_48_color));
            }
        });
    }
}
