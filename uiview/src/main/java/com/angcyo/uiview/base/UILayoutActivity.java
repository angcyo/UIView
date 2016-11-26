package com.angcyo.uiview.base;

import com.angcyo.uiview.container.ILayout;
import com.angcyo.uiview.container.UILayoutImpl;

/**
 * Created by angcyo on 2016-11-12.
 */

public abstract class UILayoutActivity extends StyleActivity {

    protected ILayout mLayout;

    @Override
    protected void onCreateView() {
        mLayout = new UILayoutImpl(this);
        setContentView(mLayout.getLayout());
    }

    @Override
    public void onBackPressed() {
        if (mLayout.requestBackPressed()) {
            super.onBackPressed();
        }
    }
}
