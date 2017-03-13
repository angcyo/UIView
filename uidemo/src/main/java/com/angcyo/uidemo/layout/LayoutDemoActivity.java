package com.angcyo.uidemo.layout;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

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

        mLayout.startIView(new DemoListUIView2());
    }

}
