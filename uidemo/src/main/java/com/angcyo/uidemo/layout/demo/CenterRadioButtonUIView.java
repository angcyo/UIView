package com.angcyo.uidemo.layout.demo;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.angcyo.library.utils.L;
import com.angcyo.uidemo.R;
import com.angcyo.uidemo.layout.demo.view.CenterButton;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.container.ContentLayout;
import com.angcyo.uiview.dialog.UIDialog;
import com.angcyo.uiview.utils.T2;

import java.lang.reflect.Field;

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
    CenterButton mCenterButton;
    int type = 0;

    private static void makeToastFullscreen(Context context, Toast toast) {

        try {
            Field mTN = toast.getClass().getDeclaredField("mTN");
            mTN.setAccessible(true);
            Object mTNObj = mTN.get(toast);

            Field mParams = mTNObj.getClass().getDeclaredField("mParams");
            mParams.setAccessible(true);
            WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams.get(mTNObj);
            params.width = -1;
            params.height = -2;// (int) dpToPx(context, T_HEIGHT);
            params.gravity = Gravity.TOP;
            params.windowAnimations = R.style.BaseToastAnimation;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void inflateContentLayout(ContentLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_center_button_layout);
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();
        mCenterButton = mViewHolder.v(R.id.center_button);
        mCenterButton.setOnCheckedChangeListener(new CenterButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CenterButton buttonView, boolean isChecked) {
                //T_.show(isChecked + "");
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
                } else {
                    //show();
                    T2.show(mActivity, System.currentTimeMillis() + "-->", type++);
                }
            }
        });

        final View maxLayout = mViewHolder.v(R.id.max_layout);
        mViewHolder.v(R.id.max_test_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (int i = 0; i < 10; i++) {
//                    final int finalI = i;
//                    maxLayout.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            maxLayout.requestLayout();
//                            L.w("call: run([])-> " + finalI);
//                        }
//                    }, 1 * i);
//                }

                final UIDialog dialog = UIDialog.build().setDialogTitle("测试对话框");
                dialog.showDialog(mParentILayout);

                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.finishIView();
                    }
                }, 500);
            }
        });

        mViewHolder.click(R.id.setChecked, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetChecked();
            }
        });
        mViewHolder.click(R.id.setEnabled, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetEnabled();
            }
        });
    }

    public void onSetChecked() {
        mCenterButton.setChecked(!mCenterButton.isChecked());
    }

    public void onSetEnabled() {
        mCenterButton.setEnabled(!mCenterButton.isEnabled());
    }

    public void show() {
        final Toast toast = Toast.makeText(mActivity, "Angcyo Test Demo", Toast.LENGTH_SHORT);
        makeToastFullscreen(mActivity, toast);

//        TitleBarLayout layout = new TitleBarLayout(mActivity);
//        layout.setBackgroundColor(Color.RED);
//        layout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
//
//        TextView textView = new TextView(mActivity);
//        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(-2, -2);
//        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        layout.addView(textView, p);
//        textView.setTextColor(Color.WHITE);
//        textView.setText("---->Demo __");X

        View layout = LayoutInflater.from(mActivity).inflate(R.layout.base_toast, null);
        ((TextView) layout.findViewById(R.id.base_toast_text_view)).setText("很帅的Toast");
        toast.setView(layout);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        toast.show();
//        toast.getView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                toast.setGravity(Gravity.TOP, 0, 0);
//                toast.getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//                toast.setText("---->2");
//                toast.show();
//            }
//        }, 1000);

        final ExToast exToast = ExToast.makeText(mActivity, "------->Demo", 2);
        exToast.setAnimations(R.style.BaseToastAnimation);
        exToast.show();

        /*try {
            Field mTN = toast.getClass().getDeclaredField("mTN");
            mTN.setAccessible(true);
            Object mObj = mTN.get(toast);

            // 取消掉各个系统的默认toast弹出动画 modify by yangsq 2014-01-04
            Field field = mObj.getClass().getDeclaredField("mParams");
            if (field != null) {
                field.setAccessible(true);
                Object mParams = field.get(mObj);
                if (mParams != null
                        && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    params.windowAnimations = -1;
                }
            }

            showMethod = mObj.getClass().getDeclaredMethod("show", null);
            hideMethod = mObj.getClass().getDeclaredMethod("hide", null);
            hasReflectException = false;
        } catch (NoSuchFieldException e) {
            hasReflectException = true;
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            hasReflectException = true;
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            hasReflectException = true;
            System.out.println(e.getMessage());
        } catch (NoSuchMethodException e) {
            hasReflectException = true;
            System.out.println(e.getMessage());
        }*/
    }
}
