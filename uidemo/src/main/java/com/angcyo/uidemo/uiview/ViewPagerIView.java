package com.angcyo.uidemo.uiview;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.angcyo.uidemo.R;
import com.angcyo.library.utils.L;
import com.angcyo.uiview.container.UIContainer;
import com.angcyo.uiview.utils.Reflect;
import com.angcyo.uiview.view.UIIViewImpl;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by angcyo on 2016-11-13.
 */

public class ViewPagerIView extends UIIViewImpl {

    ViewPager mViewPager;

    RadioGroup mRadioGroup;

    RadioButton mHomeRadio;


    @Override
    protected View inflateBaseView(FrameLayout container, LayoutInflater inflater) {
        return inflater.inflate(R.layout.view_pager_layout, container);
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);

        mViewPager = v(R.id.view_pager);
        mRadioGroup = v(R.id.radio_group);
        mHomeRadio = v(R.id.home_radio);

        CompoundButton compoundButton = v(R.id.home_radio);
        compoundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onChecked(buttonView, isChecked);
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                L.w("onCheckedChanged:" + checkedId + " " + ((TextView) group.findViewById(checkedId)).getText()
                        + " " + ((RadioButton) group.findViewById(checkedId)).isChecked());

            }
        });
//        mRadioGroup.check(R.id.home_radio);

        mHomeRadio.setChecked(true);

        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new ViewPagerAdapter());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                L.w("PagerAdapter", "onPageSelected: " + position);
                final ArrayList<Object> mItems = (ArrayList<Object>) Reflect.getMember(mViewPager, "mItems");
                if (mItems.size() == 0) {
                    mViewPager.post(new Runnable() {
                        @Override
                        public void run() {
                            final ArrayList<Object> mItems2 = (ArrayList<Object>) Reflect.getMember(mViewPager, "mItems");
                            L.w("PagerAdapter", "onPageSelected: ");
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(2);

        mHomeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(3);
            }
        });
    }

    public void onChecked(CompoundButton view, boolean checked) {
        L.w("onChecked:" + view.getId() + " " + view.getText() + " " + checked);
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
//            L.w("PagerAdapter", "getCount: ");
            return 5;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            L.w("PagerAdapter", "instantiateItem: " + position);
            Random random = new Random(SystemClock.uptimeMillis());
            View layoutWrapper = new UIContainer(mActivity);
//            layoutWrapper.setText("当前页:" + position);
//            layoutWrapper.setTextColor(Color.WHITE);
            layoutWrapper.setBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            container.addView(layoutWrapper, new ViewGroup.LayoutParams(-1, -1));
//            layoutWrapper.setVisibility(View.GONE);
            return layoutWrapper;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            L.w("PagerAdapter", "destroyItem: " + position);
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //L.w("PagerAdapter", "isViewFromObject: ");
            return view == object;
        }
    }

}

