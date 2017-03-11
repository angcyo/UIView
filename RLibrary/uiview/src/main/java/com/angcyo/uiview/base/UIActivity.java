package com.angcyo.uiview.base;

import android.view.ViewGroup;

import com.angcyo.uiview.container.UIContainer;

/**
 * Created by angcyo on 2016-11-05.
 */

@Deprecated
public abstract class UIActivity extends StyleActivity {

    protected UIContainer mUIContainer;

    @Override
    protected void onCreateView() {
        mUIContainer = new UIContainer(this);
        mUIContainer.attachToActivity(this);
        setContentView(mUIContainer, new ViewGroup.LayoutParams(-1, -1));
    }
}
