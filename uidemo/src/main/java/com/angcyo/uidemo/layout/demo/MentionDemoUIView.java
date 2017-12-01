package com.angcyo.uidemo.layout.demo;

import android.graphics.Color;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.angcyo.uidemo.R;
import com.angcyo.uidemo.layout.base.BaseItemUIView;
import com.angcyo.uiview.base.Item;
import com.angcyo.uiview.base.SingleItem;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.utils.RUtils;
import com.angcyo.uiview.utils.UI;
import com.angcyo.uiview.utils.string.SingleTextWatcher;
import com.angcyo.uiview.widget.ExEditText;
import com.angcyo.uiview.widget.RExTextView;

import java.util.List;
import java.util.Random;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/03/14 11:47
 * 修改人员：Robi
 * 修改时间：2017/03/14 11:47
 * 修改备注：
 * Version: 1.0.0
 */
public class MentionDemoUIView extends BaseItemUIView {

    int index = 0;
    private ExEditText mEditText;
    private TextView mTipTextView;

    @Override
    protected String getTitleString() {
        return "@功能测试";
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == 1) {
            return R.layout.item_mention_control_layout;
        }
        if (viewType == 3) {
            return R.layout.item_text_view;
        }
        return R.layout.item_input_layout;
    }

    @Override
    protected void createItems(List<SingleItem> items) {
        items.add(new SingleItem() {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                mEditText = holder.v(R.id.edit_text);
                UI.setViewHeight(mEditText, (int) (100 * density()));
                mEditText.setGravity(Gravity.TOP);
                mEditText.setOnMentionInputListener(new ExEditText.OnMentionInputListener() {
                    @Override
                    public void onMentionCharacterInput() {
                        post(new Runnable() {
                            @Override
                            public void run() {
                                switch (index) {
                                    case 1:
                                        mEditText.addMention("测试1");
                                        break;
                                    case 2:
                                        mEditText.addMention("测试2");
                                        break;
                                    case 3:
                                        mEditText.addMention("测试3");
                                        break;
                                    case 4:
                                        mEditText.addMention("测试4");
                                        break;
                                    case 5:
                                        mEditText.addMention("测试5");
                                        break;
                                    default:
                                        mEditText.addMention("angcyo");
                                }
                                index = 0;
                            }
                        });
                    }

                    @Override
                    public void onMentionTextChanged(List<String> allMention) {

                    }
                });

                mEditText.addTextChangedListener(new SingleTextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        super.afterTextChanged(s);
                        StringBuilder builder = new StringBuilder();
                        for (String mention : mEditText.getAllMention()) {
                            builder.append("@");
                            builder.append(mention);
                            builder.append("\n");
                        }

                        ExEditText.checkMentionSpannable(mTipTextView, RUtils.safe(builder), mEditText.getAllMention());

                        //mTipTextView.setText(RUtils.safe(builder));
//                        index = 0;
//
//                        if (mTipTextView.getText() instanceof Spannable) {
//                            index = 0;
//                        }
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                holder.v(R.id.button1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        index = 1;
                        mEditText.insert("@");
                    }
                });
                holder.v(R.id.button2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        index = 2;
                        mEditText.insert("*");
                    }
                });
                holder.v(R.id.button3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        index = 3;
                        mEditText.insert("@");
                    }
                });
                holder.v(R.id.button4).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        index = 4;
                        mEditText.insert("@");
                    }
                });
            }
        });

        items.add(new SingleItem(SingleItem.Type.TOP) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                //UI.setViewHeight(Holder.v(R.id.edit_text), (int) (300 * density()));
            }
        });

        items.add(new SingleItem(SingleItem.Type.TOP) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                mTipTextView = holder.v(R.id.text_view);
                mTipTextView.setBackgroundColor(Color.WHITE);
            }
        });


        items.add(new SingleItem(SingleItem.Type.TOP) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                final RExTextView textView = holder.v(R.id.text_view);
                final TextView textTipView = holder.v(R.id.text_tip_view);
                final CheckBox textColorCheckBox = holder.v(R.id.span_text_color);
                final Random random = new Random(System.currentTimeMillis());

                textView.setOnImageSpanClick(new RExTextView.ImageTextSpan.OnImageSpanClick() {
                    @Override
                    public boolean onClick(TextView view, String showContent, String url) {
                        textTipView.setText("显示:" + showContent + "\n地址:" + url);
                        return true;
                    }
                });

                CheckBox checkBox = holder.v(R.id.no_url_ico);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        textView.setShowPatternUrlIco(!isChecked);
                        if (textColorCheckBox.isChecked()) {
                            textView.setImageSpanTextColor(getRgbColor(random));
                        }
                        textView.setText(textView.getText().toString());
                    }
                });
                checkBox = holder.v(R.id.check_url_http);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        textView.setNeedPatternUrlCheckHttp(!isChecked);
                        if (textColorCheckBox.isChecked()) {
                            textView.setImageSpanTextColor(getRgbColor(random));
                        }
                        textView.setText(textView.getText().toString());
                    }
                });
                checkBox = holder.v(R.id.show_url);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        textView.setShowUrlRawText(isChecked);
                        if (textColorCheckBox.isChecked()) {
                            textView.setImageSpanTextColor(getRgbColor(random));
                        }
                        textView.setText(textView.getText().toString());
                    }
                });
                checkBox = holder.v(R.id.show_span_bg);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        textView.setShowSelectionSpanBgColor(isChecked);
                    }
                });


                RadioGroup radioGroup = holder.v(R.id.radio_group);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.not_pattern_url:
                                textView.setNeedPatternUrl(false);
                                break;
                            case R.id.pattern_url:
                                textView.setNeedPatternUrl(true);
                                break;
                        }
                        //L.e("call: onCheckedChanged([group, checkedId])-> " + checkedId);
                        if (textColorCheckBox.isChecked()) {
                            textView.setImageSpanTextColor(getRgbColor(random));
                        }
                        textView.setText(textView.getText());
                    }
                });
            }

            @Override
            public int getItemLayoutId() {
                return R.layout.item_ex_edit_text_demo;
            }
        });
    }

    private int getRgbColor(Random random) {
        return Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
}
