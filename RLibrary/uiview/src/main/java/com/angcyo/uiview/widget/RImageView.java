package com.angcyo.uiview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：自带点击效果的ImageView
 * 创建人员：Robi
 * 创建时间：2017/03/10 11:45
 * 修改人员：Robi
 * 修改时间：2017/03/10 11:45
 * 修改备注：
 * Version: 1.0.0
 */
public class RImageView extends AppCompatImageView {

    /**
     * 播放按钮图片
     */
    Drawable mPlayDrawable;
    private boolean isAttachedToWindow;
    private boolean mShowMask;//显示click时的蒙层

    /**
     * 当调用{@link android.widget.ImageView#setImageDrawable(Drawable)} 时, 是否显示过渡动画
     */
    private boolean showDrawableAnim = true;

    public RImageView(Context context) {
        super(context);
    }

    public RImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setColor();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                clearColor();
                break;
        }

        super.onTouchEvent(event);
        return true;
    }

    public void setColor(@ColorInt int color) {
        setColor(getDrawable(), color);
    }

    private void setColor(Drawable drawable, @ColorInt int color) {
        if (drawable != null) {
            if (drawable instanceof LayerDrawable) {
//                LayerDrawable layerDrawable = (LayerDrawable) drawable;
//                int numberOfLayers = layerDrawable.getNumberOfLayers();
////                if (numberOfLayers > 0) {
////                    setColor((layerDrawable).getDrawable(numberOfLayers - 1), color);
////                }
//                for (int i = 0; i < numberOfLayers; i++) {
//                    setColor((layerDrawable).getDrawable(i), color);
//                }

                mShowMask = true;
                postInvalidate();
//                layerDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            } else {
                drawable.mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            }
        }
    }

    private void clearColor(Drawable drawable) {
        if (drawable != null) {
            if (drawable instanceof LayerDrawable) {
//                LayerDrawable layerDrawable = (LayerDrawable) drawable;
//                int numberOfLayers = layerDrawable.getNumberOfLayers();
//                if (numberOfLayers > 0) {
//                    clearColor((layerDrawable).getDrawable(numberOfLayers - 1));
//                }
                mShowMask = false;
                postInvalidate();
//                layerDrawable.clearColorFilter();
            } else {
                drawable.mutate().clearColorFilter();
            }
        }
    }

    /**
     * 设置混合颜色
     */
    public void setColor() {
        setColor(Color.GRAY);
    }

    public void clearColor() {
        clearColor(getDrawable());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearColor();
        //setImageBitmap(null);
        isAttachedToWindow = false;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isAttachedToWindow = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPlayDrawable != null) {
            int height = getMeasuredHeight() / 2;
            int width = getMeasuredWidth() / 2;
            int w = mPlayDrawable.getIntrinsicWidth() / 2;
            int h = mPlayDrawable.getIntrinsicHeight() / 2;
            mPlayDrawable.setBounds(width - w, height - h, width + w, height + h);
            mPlayDrawable.draw(canvas);
        }
        if (mShowMask) {
            canvas.drawColor(Color.parseColor("#80000000"));
        }
    }

    public void setPlayDrawable(Drawable playDrawable) {
        mPlayDrawable = playDrawable;
        if (isAttachedToWindow) {
            postInvalidate();
        }
    }

    public void setPlayDrawable(@DrawableRes int res) {
        setPlayDrawable(ContextCompat.getDrawable(getContext(), res));
    }

//    @Override
//    public void setImageResource(@DrawableRes int resId) {
//        super.setImageResource(resId);
//    }
//
//    @Override
//    public void setImageBitmap(Bitmap bm) {
//        super.setImageBitmap(bm);
//    }
//
//    @Override
//    public void setImageBitmap(@Nullable Drawable drawable) {
//        if (showDrawableAnim) {
//            Drawable drawable1 = getDrawable();
//            final TransitionDrawable td = new TransitionDrawable(
//                    new Drawable[]{drawable1 == null ? new ColorDrawable(Color.WHITE) : drawable1,
//                            drawable});
//            super.setImageBitmap(td);
//            td.startTransition(300);
//        } else {
//            super.setImageBitmap(drawable);
//        }
//    }

    /**
     * 使用过渡的方式显示Drawable
     */
    public void setImageDrawable(@Nullable Drawable fromDrawable, @Nullable Drawable toDrawable) {
        final TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                fromDrawable, toDrawable});
        super.setImageDrawable(td);
        td.startTransition(300);
    }

    public void setImageBitmap(@Nullable Drawable fromDrawable, @Nullable Bitmap toBitmap) {
        final TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                fromDrawable, new BitmapDrawable(getResources(), toBitmap)});
        super.setImageDrawable(td);
        td.startTransition(300);
    }
}
