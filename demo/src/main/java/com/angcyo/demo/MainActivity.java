package com.angcyo.demo;

import android.os.Bundle;
import android.view.ViewGroup;

import com.angcyo.uiview.base.UIActivity;
import com.angcyo.uiview.container.UIContainer;

public class MainActivity extends UIActivity {

    boolean isAdd = false;
    TestView mTestUIView;
    UIContainer mTestUIContainer;

    ViewGroup mContentLayout;


    @Override
    protected void onLoadView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_main);
//        mContentLayout = (ViewGroup) findViewById(R.id.content_main);

//        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//
//                if (isAdd) {
//                    //mContentLayout.removeView(mTestUIView);
//                    mContentLayout.removeView(mTestUIContainer);
//
//                    isAdd = false;
//                } else {
////                    if (mTestUIView == null) {
////                        mTestUIView = new TestView(MainActivity.this);
////                        mTestUIView.setText("测试:" + System.currentTimeMillis());
////                    }
//                    if (mTestUIContainer == null) {
//                        mTestUIContainer = new UIContainer(MainActivity.this);
//                    }
//
//                    mContentLayout.addView(mTestUIContainer);
//                    isAdd = true;
//                }
//            }
//        });
    }
}
