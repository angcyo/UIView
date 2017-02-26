package com.angcyo.uidemo.layout.demo;

import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.utils.T_;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by angcyo on 2017-02-26 11:02.
 */
public class AnimatorDemoUIView extends UIContentView {
    @BindView(R.id.red_view)
    View mRedView;
    @BindView(R.id.blue_view)
    View mBlueView;

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_animator_layout);
    }

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

    @OnClick({R.id.reset_button, R.id.move_button})
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

    @OnClick(R.id.blue_view)
    public void onClick() {
        T_.error(System.currentTimeMillis() + "");
    }
}
