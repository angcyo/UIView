package com.angcyo.demo.uiview;

import android.animation.LayoutTransition;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.angcyo.demo.R;
import com.angcyo.uiview.base.UIBaseView;
import com.angcyo.uiview.dialog.UIDialog;
import com.angcyo.uiview.utils.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by angcyo on 2016-11-12.
 */

public class ScrollerView extends UIBaseView {

    @BindView(R.id.jump_to_view_pager)
    TextView mTextView;

    public ScrollerView() {
    }

    @Override
    protected View inflateBaseView(FrameLayout container, LayoutInflater inflater) {
        return inflater.inflate(R.layout.scroller_layout, container);
    }

    @Override
    public void onViewShow() {
        super.onViewShow();
        final ViewGroup rootView = (ViewGroup) ((ViewGroup) mRootView).getChildAt(0);
//        final ViewGroup rootView = (ViewGroup) mRootView;

        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f,
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f);
        translateAnimation.setDuration(700);
//        ObjectAnimator translate = ObjectAnimator.ofObject();
//        layoutTransition.setAnimator(LayoutTransition.APPEARING, );
        //rootView.setLayoutTransition(layoutTransition);

//        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
//        alphaAnimation.setDuration(3000);
        final LayoutAnimationController layoutAnimationController = new LayoutAnimationController(translateAnimation);
//        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_RANDOM);
//        rootView.setLayoutAnimation(layoutAnimationController);
//        rootView.startLayoutAnimation();

        final LayoutTransition layoutTransition = new LayoutTransition();
//        final ObjectAnimator translationX = ObjectAnimator.ofFloat(null, "translationX", -1f, 0f);
//        translationX.setDuration(3000);
//        layoutTransition.setAnimator(LayoutTransition.APPEARING, translationX);
//        layoutTransition.setAnimator(LayoutTransition.CHANGE_APPEARING, translationX);
//        layoutTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, translationX);
//        layoutTransition.setAnimator(LayoutTransition.CHANGING, translationX);
//        layoutTransition.setAnimator(LayoutTransition.DISAPPEARING, translationX);
//        layoutTransition.setDuration(3000);
//        rootView.setLayoutTransition(layoutTransition);
//        rootView.scheduleLayoutAnimation();
    }

    @OnClick(R.id.jump_to_view_pager)
    public void onJumpToViewPager() {
        //T.show(mContext, "onJumpToViewPager");
        mILayout.startIView(new ViewPagerView());
    }

    @OnClick(R.id.view)
    public void onViewClick(View view) {
        T.show(mContext, "你看到我了吗?");
        mILayout.startIView(new UIDialog().setGravity(Gravity.CENTER));
    }

    @OnClick(R.id.view2)
    public void onViewC2lick(View view) {
//        mILayout.startIView(new UIDialog().setDialogTitle("很帅的标题").setDialogContent("无知的内容"));
        final ViewGroup rootView = (ViewGroup) ((ViewGroup) mRootView).getChildAt(0);
        TextView textView = new TextView(mContext);
        textView.setText("刚创建的VIEW");
        textView.setBackgroundColor(Color.LTGRAY);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewC2lick(v);
            }
        });
        rootView.addView(textView, new ViewGroup.LayoutParams(-1, 500));
    }
}
