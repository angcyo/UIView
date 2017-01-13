package com.angcyo.uidemo.layout.demo;

import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.angcyo.library.utils.L;
import com.angcyo.uidemo.R;
import com.angcyo.uidemo.T_;
import com.angcyo.uidemo.layout.demo.view.CenterButton;
import com.angcyo.uiview.base.UIContentView;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2016/12/30 15:46
 * 修改人员：Robi
 * 修改时间：2016/12/30 15:46
 * 修改备注：
 * Version: 1.0.0
 */
public class CenterRadioButtonUIView extends UIContentView {
    @BindView(R.id.center_button)
    CenterButton mCenterButton;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_center_button_layout);
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();
        mCenterButton.setOnCheckedChangeListener(new CenterButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CenterButton buttonView, boolean isChecked) {
                T_.show(isChecked + "");
                if (isChecked) {
                    Observable
                            .zip(Observable.just("1").subscribeOn(Schedulers.newThread()),
                                    Observable.just("2").subscribeOn(Schedulers.io()),
                                    new Func2<Object, Object, Object>() {
                                        @Override
                                        public Object call(Object o, Object o2) {
                                            return null;
                                        }
                                    })
                            .subscribeOn(Schedulers.computation())
//                            .observeOn(Schedulers.computation())
                            .subscribe(new Action1<Object>() {
                                @Override
                                public void call(Object o) {
                                    L.e("");
                                }
                            });
                }
            }
        });
    }

    @OnClick(R.id.setChecked)
    public void onSetChecked() {
        mCenterButton.setChecked(!mCenterButton.isChecked());
    }

    @OnClick(R.id.setEnabled)
    public void onSetEnabled() {
        mCenterButton.setEnabled(!mCenterButton.isEnabled());
    }
}
