package com.angcyo.uidemo.layout.demo;

import android.widget.CompoundButton;

import com.angcyo.library.utils.L;
import com.angcyo.uidemo.R;
import com.angcyo.uidemo.layout.base.BaseItemUIView;
import com.angcyo.uiview.base.Item;
import com.angcyo.uiview.base.SingleItem;
import com.angcyo.uiview.github.finger.FingerPrinterView;
import com.angcyo.uiview.github.finger.RxFingerPrinter;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.utils.T_;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by angcyo on 2017-05-29.
 * <p>
 * https://github.com/Zweihui/RxFingerPrinter
 */

public class FingerPrinterUIDemo extends BaseItemUIView {

    RxFingerPrinter mFingerPrinter;
    FingerPrinterView mFingerPrinterView;

    boolean autoAgain = false;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.view_finger_printer;
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();
        post(new Runnable() {
            @Override
            public void run() {
                Subscription subscribe = mFingerPrinter.begin()
                        .subscribe(new Subscriber<Boolean>() {

                            @Override
                            public void onStart() {
                                super.onStart();
                                L.e("onStart() -> ");
                                mFingerPrinterView.setState(FingerPrinterView.STATE_NO_SCANING);
                            }

                            @Override
                            public void onCompleted() {
                                L.e("onCompleted() -> ");
                            }

                            @Override
                            public void onError(Throwable e) {
                                L.e("onError() -> " + e);
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                L.e("onNext() -> " + aBoolean);
                                if (aBoolean) {
                                    T_.ok("指纹识别成功.");
                                    mFingerPrinterView.setState(FingerPrinterView.STATE_CORRECT_PWD);
                                } else {
                                    T_.error("指纹识别失败.");
                                    mFingerPrinterView.setState(FingerPrinterView.STATE_WRONG_PWD);
                                }

                                if (autoAgain) {
                                    mFingerPrinter.authAgain();

                                    postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mFingerPrinterView.setState(FingerPrinterView.STATE_SCANING);
                                        }
                                    }, 1000);
                                }
                            }
                        });
                mFingerPrinter.addSubscription(FingerPrinterUIDemo.this, subscribe);
            }
        });
    }

    @Override
    public void onViewLoad() {
        super.onViewLoad();
        mFingerPrinter = new RxFingerPrinter(mActivity);
    }

    @Override
    public void onViewUnload() {
        super.onViewUnload();
        mFingerPrinter.unSubscribe(this);
    }

    @Override
    protected void createItems(List<SingleItem> items) {
        items.add(new SingleItem() {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                mFingerPrinterView = holder.v(R.id.finger_view);

                holder.cV(R.id.checkbox).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        autoAgain = isChecked;
                    }
                });
            }
        });
    }
}
