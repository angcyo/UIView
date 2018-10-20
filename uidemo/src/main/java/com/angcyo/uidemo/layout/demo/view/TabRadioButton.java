package com.angcyo.uidemo.layout.demo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.kotlin.ExKt;

/**
 * Created by angcyo on 2018/10/19 20:13
 */
public class TabRadioButton extends android.support.v7.widget.AppCompatRadioButton {

    private Rect mRect;
    private float mScale = 1f;

    public TabRadioButton(Context context) {
        this(context, null);
    }

    public TabRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRect = new Rect();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                L.d("onClick");
                startAnimator();
            }
        });
    }

    private Drawable topDrawable() {
        Drawable[] drawables = getCompoundDrawables();
        return drawables[1];
    }

    @Override
    public boolean performClick() {
        L.d("performClick");
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            L.d("touch up");
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        L.d("onDraw");
        canvas.drawColor(Color.YELLOW);
        Drawable topDrawable = topDrawable();
        if (topDrawable != null) {

            int left = 0;
            int top = 0;

            mRect.set(left, top,
                    left + topDrawable.getIntrinsicWidth(), top + topDrawable.getIntrinsicHeight());

            ExKt.scale(mRect, mScale, mScale);
            topDrawable.setBounds(mRect);
        }
        super.onDraw(canvas);
    }

    ValueAnimator mValueAnimator;

    private void startAnimator() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }

        mValueAnimator = ValueAnimator.ofFloat(0.5f, 1f);
        mValueAnimator.setInterpolator(new BounceInterpolator());
        mValueAnimator.setDuration(300);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mScale = (float) animation.getAnimatedValue();
                ViewCompat.postInvalidateOnAnimation(TabRadioButton.this);
            }
        });
        mValueAnimator.start();
    }
}
