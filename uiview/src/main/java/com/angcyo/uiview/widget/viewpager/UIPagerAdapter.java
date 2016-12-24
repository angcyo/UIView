package com.angcyo.uiview.widget.viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.angcyo.uiview.container.UILayoutImpl;
import com.angcyo.uiview.view.IView;

/**
 * Created by angcyo on 2016-11-26.
 */

public abstract class UIPagerAdapter extends PagerAdapter {

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final UILayoutImpl layout = new UILayoutImpl(container.getContext(), getIView(position));
        container.addView(layout);
        return layout;
    }

    protected abstract IView getIView(int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        final UILayoutImpl layout = (UILayoutImpl) object;
        container.removeView(layout);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
