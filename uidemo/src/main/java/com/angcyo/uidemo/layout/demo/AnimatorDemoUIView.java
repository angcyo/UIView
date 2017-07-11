package com.angcyo.uidemo.layout.demo;

import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.utils.T_;


/**
 * Created by angcyo on 2017-02-26 11:02.
 */
public class AnimatorDemoUIView extends UIContentView {
    View mRedView;
    View mBlueView;

    /**
     * 计算缩放比例, 从给定的宽高, 到最大的宽高, 计算出宽高需要变化的比例
     */
    private static float calcScale(int width, int height, int targetMaxWidth, int targetMaxHeight) {
        float result = 1f;
//        if (targetMaxWidth > width && targetMaxHeight > height) {
//            //目标比原来要大, 那么就是放大
//        } else if (width > targetMaxWidth && height > targetMaxHeight) {
//
//        }
        float widthScale = targetMaxWidth * 1f / width;
        float heightScale = targetMaxHeight * 1f / height;

        return Math.min(widthScale, heightScale);
    }

    private static void animTo(View target, float startX, float startY, float endX, float endY, float scaleX, float scaleY) {
        ViewCompat.animate(target)
                .setDuration(300)
                .setInterpolator(new LinearInterpolator())
                .x(endX)
                .y(endY)
                .scaleX(scaleX)
                .scaleY(scaleY)
                .start();
    }

    @Override
    protected void inflateContentLayout(FrameLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_animator_layout);
    }

    @Override
    protected String getTitleString() {
        return "图片查看动画";
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();
        mRedView = mViewHolder.v(R.id.red_view);
        mBlueView = mViewHolder.v(R.id.blue_view);

        mBlueView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorDemoUIView.this.onClick(mBlueView);
            }
        });

        mRedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorDemoUIView.this.onClick(mRedView);
            }
        });

        mViewHolder.v(R.id.blue_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorDemoUIView.this.onClick(mBlueView);
            }
        });

        mViewHolder.v(R.id.reset_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorDemoUIView.this.onClick(mViewHolder.v(R.id.reset_button));
            }
        });
        mViewHolder.v(R.id.move_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorDemoUIView.this.onClick(mViewHolder.v(R.id.move_button));
            }
        });
    }

    public void onClick(View view) {
        final int measuredWidth = mRedView.getMeasuredWidth();
        final int measuredHeight = mRedView.getMeasuredHeight();

        final int measuredWidth1 = mBaseContentRootLayout.getMeasuredWidth();
        final int measuredHeight1 = mBaseContentRootLayout.getMeasuredHeight();

        final float scaleTo = calcScale(measuredWidth, measuredHeight, measuredWidth1, measuredHeight1);
        final float scaleFrom = calcScale(measuredWidth1, measuredHeight1, measuredWidth, measuredHeight);

        switch (view.getId()) {
            case R.id.reset_button:
                animTo(mBlueView, mBlueView.getX(), mBlueView.getY(), mRedView.getX(), mRedView.getY(), 1, 1);
                break;
            case R.id.move_button:
                animTo(mBlueView, mBlueView.getX(), mBlueView.getY(),
                        measuredWidth1 / 2 - measuredWidth / 2,
                        measuredHeight1 / 2 - measuredHeight / 2,
                        scaleTo, scaleTo);
                break;
        }
    }

    public void onClick() {
        T_.error(System.currentTimeMillis() + "");
    }
}
