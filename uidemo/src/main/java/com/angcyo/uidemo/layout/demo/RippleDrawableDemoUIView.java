package com.angcyo.uidemo.layout.demo;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.Item;
import com.angcyo.uiview.base.SingleItem;
import com.angcyo.uiview.base.UIItemUIView;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.resources.ResUtil;
import com.angcyo.uiview.skin.SkinHelper;
import com.angcyo.uiview.widget.RImageView;

import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/12 09:08
 * 修改人员：Robi
 * 修改时间：2017/04/12 09:08
 * 修改备注：
 * Version: 1.0.0
 */
public class RippleDrawableDemoUIView extends UIItemUIView<SingleItem> {
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_ripple_drawable_layout;
    }

    @Override
    protected void createItems(List<SingleItem> items) {
        items.add(new SingleItem() {
            @Override
            public void onBindView(final RBaseViewHolder holder, int posInData, Item dataBean) {
                ResUtil.setBgDrawable(holder.v(R.id.edit_text), SkinHelper.getThemeRoundBorderSelector());

                ResUtil.setBgDrawable(holder.v(R.id.view1), ResUtil.generateRippleDrawable(Color.RED));
                ResUtil.setBgDrawable(holder.v(R.id.view2), ResUtil.generateRippleMaskDrawable(Color.RED));
                ResUtil.setBgDrawable(holder.v(R.id.view3), ResUtil.generateRippleMaskDrawable(Color.RED, Color.BLUE, Color.GRAY));
                ResUtil.setBgDrawable(holder.v(R.id.view4), ResUtil.generateRippleRoundMaskDrawable(10, Color.RED, Color.BLUE, Color.GRAY));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Drawable content = ResUtil.generateBgDrawable(Color.BLUE, Color.GRAY);
                    RippleDrawable drawable = new RippleDrawable(ColorStateList.valueOf(Color.RED), content, new ColorDrawable(Color.YELLOW));

                    ResUtil.setBgDrawable(holder.v(R.id.view5), drawable);
                }

                postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        final TransitionDrawable td = new TransitionDrawable(new Drawable[]{new ColorDrawable(0xfffcfcfc), getDrawable(R.drawable.image_demo)});
//                        final TransitionDrawable td = new TransitionDrawable(
//                                new Drawable[]{getDrawable(R.drawable.test_image), getDrawable(R.drawable.image_demo)});
//                        holder.imgV(R.id.view6).setImageBitmap(td);
//                        td.startTransition(3000);

                        RImageView imageView = holder.v(R.id.view6);
//                        imageView.setImageDrawable(new ColorDrawable(0xfffcfcfc), getDrawable(R.drawable.image_demo));
                        imageView.setImageDrawable(getDrawable(R.drawable.test_image), getDrawable(R.drawable.image_demo));
                    }
                }, 500);

            }
        });
    }
}
