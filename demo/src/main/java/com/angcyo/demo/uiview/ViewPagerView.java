package com.angcyo.demo.uiview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.angcyo.demo.R;
import com.angcyo.library.utils.L;
import com.angcyo.uiview.base.UIBaseView;
import com.angcyo.uiview.container.ILayout;

import butterknife.BindView;
import butterknife.OnCheckedChanged;

/**
 * Created by angcyo on 2016-11-13.
 */

public class ViewPagerView extends UIBaseView {

    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;

    @BindView(R.id.home_radio)
    RadioButton mHomeRadio;

    @Override
    public View inflateContentView(Context context, ILayout iLayout, FrameLayout container, LayoutInflater inflater) {
        super.inflateContentView(context, iLayout, container, inflater);
        return inflater.inflate(R.layout.view_pager_layout, container);
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                L.w("onCheckedChanged:" + checkedId + " " + ((TextView) group.findViewById(checkedId)).getText()
                        + " " + ((RadioButton) group.findViewById(checkedId)).isChecked());
            }
        });
//        mRadioGroup.check(R.id.home_radio);

        mHomeRadio.setChecked(true);
    }

    @OnCheckedChanged(R.id.home_radio)
    public void onChecked(CompoundButton view, boolean checked) {
        L.w("onChecked:" + view.getId() + " " + view.getText() + " " + view.isChecked());
    }
}
