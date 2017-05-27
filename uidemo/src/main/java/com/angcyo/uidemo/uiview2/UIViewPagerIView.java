package com.angcyo.uidemo.uiview2;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.view.IView;
import com.angcyo.uiview.view.UIIViewImpl;
import com.angcyo.uiview.widget.viewpager.UIPagerAdapter;
import com.angcyo.uiview.widget.viewpager.UIViewPager;

/**
 * Created by angcyo on 2016-11-26.
 */

public class UIViewPagerIView extends UIIViewImpl {
    UIViewPager mViewPager;
    RadioGroup mRadioGroup;

    @Override
    protected View inflateBaseView(FrameLayout container, LayoutInflater inflater) {
        return inflater.inflate(R.layout.ui_view_pager_layout, container);
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);
        mViewPager = v(R.id.view_pager);
        mRadioGroup = v(R.id.radio_group);
//        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(new UIPagerAdapter() {
            @Override
            protected IView getIView(int position) {
                if (position == 0) {
                    return new HomeView();
                }
                if (position == 1) {
                    return new ShopView();
                }
                if (position == 2) {
                    return new LiveView();
                }
                if (position == 3) {
                    return new MessageView();
                }
                return new MineView();
            }

            @Override
            public int getCount() {
                return 5;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mRadioGroup.check(R.id.home_radio);
                }
                if (position == 1) {
                    mRadioGroup.check(R.id.shop_radio);
                }
                if (position == 2) {
                    mRadioGroup.check(R.id.live_radio);
                }
                if (position == 3) {
                    mRadioGroup.check(R.id.message_radio);
                }
                if (position == 4) {
                    mRadioGroup.check(R.id.me_radio);
                }
            }
        });

        mViewPager.setCurrentItem(0);

        click(R.id.home_radio, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIViewPagerIView.this.onClick(v);
            }
        });
        click(R.id.shop_radio, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIViewPagerIView.this.onClick(v);
            }
        });
        click(R.id.live_radio, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIViewPagerIView.this.onClick(v);
            }
        });
        click(R.id.message_radio, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIViewPagerIView.this.onClick(v);
            }
        });
        click(R.id.me_radio, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIViewPagerIView.this.onClick(v);
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_radio:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.shop_radio:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.live_radio:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.message_radio:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.me_radio:
                mViewPager.setCurrentItem(4);
                break;
        }
    }
}
