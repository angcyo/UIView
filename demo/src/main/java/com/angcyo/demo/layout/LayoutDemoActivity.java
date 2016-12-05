package com.angcyo.demo.layout;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

import com.angcyo.demo.uiview3.view.RefreshLayoutDemo;
import com.angcyo.library.utils.L;
import com.angcyo.uiview.base.UILayoutActivity;

public class LayoutDemoActivity extends UILayoutActivity {
    @Override
    protected void onLoadView() {
        mLayout.getLayout().post(new Runnable() {
            @Override
            public void run() {
                final View view = mLayout.getLayout();
                final DisplayMetrics metrics = getResources().getDisplayMetrics();
                Rect outRect = new Rect();
                view.getHitRect(outRect);

                Rect outRect2 = new Rect();
                view.getWindowVisibleDisplayFrame(outRect2);
                Rect outRect3 = new Rect();
                view.getGlobalVisibleRect(outRect3);
                Rect outRect4 = new Rect();
                view.getLocalVisibleRect(outRect4);
                Rect outRect5 = new Rect();
                view.getDrawingRect(outRect5);

                L.i(view.getMeasuredHeight() + " " + view.getMeasuredWidth());
            }
        });

//        mLayout.startIView(new ScrollerIView());

        mLayout.startIView(new RefreshLayoutDemo());

        //mLayout.startIView(new TestDemo());
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_layout_demo);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }

}
