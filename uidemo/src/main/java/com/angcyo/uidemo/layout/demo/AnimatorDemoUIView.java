package com.angcyo.uidemo.layout.demo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.container.ContentLayout;
import com.angcyo.uiview.resources.AnimUtil;
import com.angcyo.uiview.resources.RAnimListener;
import com.angcyo.uiview.utils.ScreenUtil;
import com.angcyo.uiview.utils.T_;
import com.angcyo.uiview.widget.RImageView;
import com.github.florent37.viewanimator.ViewAnimator;


/**
 * Created by angcyo on 2017-02-26 11:02.
 */
public class AnimatorDemoUIView extends UIContentView {
    View mRedView;
    View mBlueView;
    private Rect mRect;
    private int mTitleHeight;

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

    private static void layoutCenterAtPoint(View view, float x, float y) {
        ViewCompat.setX(view, x - view.getMeasuredWidth() / 2);
        ViewCompat.setY(view, y - view.getMeasuredHeight() / 2);
    }

    @Override
    protected void inflateContentLayout(ContentLayout baseContentLayout, LayoutInflater inflater) {
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

        mViewHolder.click(R.id.button1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAnimator.animate(v)
                        .translationX(0, ((View) v.getParent()).getMeasuredWidth() / 2 - v.getLeft() - v.getMeasuredWidth() / 2)
                        .translationY(0, ((View) v.getParent()).getMeasuredHeight() / 2 - v.getTop() - v.getMeasuredHeight() / 2)
//                        .width(v.getMeasuredWidth(), ScreenUtil.screenWidth)
//                        .height(v.getMeasuredHeight(), ScreenUtil.screenHeight)
                        .start();
            }
        });

        post(new Runnable() {
            @Override
            public void run() {
                mTitleHeight = getUITitleBarContainer().getMeasuredHeight();
            }
        });
        mViewHolder.click(R.id.preview_image_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T_.info("preview_image_view:" + System.nanoTime());
            }
        });

        mViewHolder.click(R.id.image_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                v.getLocationInWindow(location);

                int measuredWidth = v.getMeasuredWidth();
                int measuredHeight = v.getMeasuredHeight();

                final Rect rect = new Rect();
                v.getGlobalVisibleRect(rect);

                Drawable drawable = RImageView.copyDrawable((ImageView) mViewHolder.v(R.id.image_view));

                final ImageView preview = mViewHolder.v(R.id.preview_image_view);
                preview.setImageDrawable(drawable);

//                mViewHolder.v(R.id.preview_layout).setVisibility(View.VISIBLE);

//                ViewCompat.setX(preview, rect.left);
//                ViewCompat.setY(preview, rect.top);
//                UI.setView(preview, rect.width(), rect.height());

                final int targetWidth = Math.min(ScreenUtil.screenWidth, drawable.getIntrinsicWidth());
                final int targetHeight = Math.min(ScreenUtil.screenHeight, drawable.getIntrinsicHeight());

//                ViewAnimator.animate(preview)
//                        .pivotX()
//                        .scaleX(rect.width() * 1f / targetWidth, 1f)
//                        .scaleY(rect.height() * 1f / targetHeight, 1f)
//
//                ;


//                ViewCompat.setScaleX(preview, rect.width() * 1f / targetWidth);
//                ViewCompat.setScaleY(preview, rect.height() * 1f / targetHeight);

                AnimationSet animationSet = new AnimationSet(true);

//                TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, rect.left * 1f / ScreenUtil.screenWidth,
//                        Animation.RELATIVE_TO_PARENT, 0f,
//                        Animation.RELATIVE_TO_PARENT, rect.top * 1f / ScreenUtil.screenHeight,
//                        Animation.RELATIVE_TO_PARENT, 0f);

                TranslateAnimation translateAnimation = new TranslateAnimation(rect.left,
                        0f,
                        rect.top,
                        0f);

                ScaleAnimation scaleAnimation = new ScaleAnimation(rect.width() * 1f / targetWidth, 1f,
                        rect.height() * 1f / targetHeight, 1f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);

//                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(translateAnimation);
                animationSet.setInterpolator(new LinearInterpolator());
                animationSet.setDuration(1000);

//                preview.startAnimation(animationSet);

                int titleHeight = getUITitleBarContainer().getMeasuredHeight();

                int previewWidth = preview.getMeasuredWidth();
                int previewHeight = preview.getMeasuredHeight();

                final float sX = rect.width() * 1f / targetWidth;
                final float sY = rect.height() * 1f / targetHeight;

//                final ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
//                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        float value = (float) animation.getAnimatedValue();
//                        //L.e("call: onAnimationUpdate([animation])-> " + value + " " + animation.getAnimatedFraction());
//
//                        ViewCompat.setScaleX(preview, sX + (1 - sX) * value);
//                        ViewCompat.setScaleY(preview, sY + (1 - sY) * value);
//
//                        float scaleWidth = previewWidth * preview.getScaleX();
//                        float scaleHeight = previewHeight * preview.getScaleY();
//
//                        float tX = rect.left - (previewWidth - scaleWidth) / 2;
//                        float tY = rect.top - titleHeight - (previewHeight - scaleHeight) / 2;
//
//                        if (value == 0) {
//                            mViewHolder.v(R.id.preview_layout).setVisibility(View.VISIBLE);
//                            ViewCompat.setY(preview, tY);
//                            ViewCompat.setX(preview, tX);
//                        } else {
//
//                        }
//
////                        ViewCompat.setX(preview, tX);
//                        ViewCompat.setX(preview, tX + (0 - tX) * value);
//                        //L.e("call: onAnimationUpdate([animation])-> " + preview.getX());
////                        ViewCompat.setY(preview, tY);
//                        ViewCompat.setY(preview, tY + ((ScreenUtil.screenHeight - titleHeight - previewHeight) / 2 - tY) * value);
//
//                    }
//                });

                final Point startPoint = new Point(rect.centerX(), rect.centerY() - titleHeight);
                final Point endPoint = new Point(ScreenUtil.screenWidth / 2, (ScreenUtil.screenHeight - titleHeight) / 2);

                final ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();

                        ViewCompat.setScaleX(preview, sX + (1 - sX) * value);
                        ViewCompat.setScaleY(preview, sY + (1 - sY) * value);
                        if (value == 0) {
                            mViewHolder.v(R.id.preview_layout).setVisibility(View.VISIBLE);
                        }

                        layoutCenterAtPoint(preview, startPoint.x + (endPoint.x - startPoint.x) * value,
                                startPoint.y + (endPoint.y - startPoint.y) * value);
                    }
                });

                animator.setDuration(3000);
                animator.setInterpolator(new LinearInterpolator());

                preview.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        animator.start();
                        preview.getViewTreeObserver().removeOnPreDrawListener(this);
                        return false;
                    }
                });
            }
        });
        mRect = new Rect();
        mViewHolder.click(R.id.image_view, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Drawable drawable = RImageView.copyDrawable((ImageView) mViewHolder.v(R.id.image_view));
                ImageView preview = mViewHolder.v(R.id.preview_image_view);
                preview.setImageDrawable(drawable);
                v.getGlobalVisibleRect(mRect);

                mViewHolder.v(R.id.preview_layout).setVisibility(View.VISIBLE);
                AnimUtil.startToMaxAnim(mRect, mViewHolder.v(R.id.preview_image_view),
                        new Point(mRect.centerX(), mRect.centerY() - mTitleHeight),
                        new Point(ScreenUtil.screenWidth / 2, (ScreenUtil.screenHeight - mTitleHeight) / 2),
                        drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), 0, new RAnimListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                mViewHolder.v(R.id.preview_layout).setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationProgress(Animator animation, float progress) {
                                super.onAnimationProgress(animation, progress);
                                mViewHolder.v(R.id.preview_layout).setBackgroundColor(AnimUtil.evaluateColor(progress, Color.TRANSPARENT, Color.BLACK));
                            }
                        });
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        ImageView preview = mViewHolder.v(R.id.preview_image_view);
        if (preview.getScaleX() == 1) {
            AnimUtil.startToMinAnim(mRect, preview,
                    new Point(ScreenUtil.screenWidth / 2, (ScreenUtil.screenHeight - mTitleHeight) / 2),
                    new Point(mRect.centerX(), mRect.centerY() - mTitleHeight),
                    preview.getMeasuredWidth(), preview.getMeasuredHeight(), new RAnimListener() {

                        @Override
                        public void onAnimationFinish(Animator animation, boolean cancel) {
                            super.onAnimationFinish(animation, cancel);
                            mViewHolder.v(R.id.preview_layout).setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationProgress(Animator animation, float progress) {
                            super.onAnimationProgress(animation, progress);
                            mViewHolder.v(R.id.preview_layout).setBackgroundColor(AnimUtil.evaluateColor(progress, Color.BLACK, Color.TRANSPARENT));
                        }
                    });
            return false;
        } else {
            return super.onBackPressed();
        }
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
