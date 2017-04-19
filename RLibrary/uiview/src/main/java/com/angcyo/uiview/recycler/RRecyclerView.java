package com.angcyo.uiview.recycler;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.angcyo.uiview.recycler.adapter.RBaseAdapter;
import com.angcyo.uiview.recycler.recyclerview.adapters.AnimationAdapter;
import com.angcyo.uiview.recycler.recyclerview.adapters.ScaleInAnimationAdapter;
import com.angcyo.uiview.recycler.recyclerview.animators.BaseItemAnimator;
import com.angcyo.uiview.recycler.recyclerview.animators.FadeInDownAnimator;
import com.angcyo.uiview.resources.AnimUtil;
import com.angcyo.uiview.skin.SkinHelper;
import com.angcyo.uiview.utils.Reflect;
import com.angcyo.uiview.utils.UI;

import java.lang.reflect.Constructor;

/**
 * 简单封装的RecyclerView
 * <p>
 * 动画样式:https://github.com/wasabeef/recyclerview-animators
 * Created by angcyo on 16-03-01-001.
 */
public class RRecyclerView extends RecyclerView {
    protected LayoutManager layoutManager;
    protected int spanCount = 2;
    protected int orientation = LinearLayout.VERTICAL;
    protected Class<? extends AnimationAdapter> animatorAdapter;
    protected RBaseAdapter mAdapterRaw;
    protected AnimationAdapter mAnimationAdapter;
    protected boolean mItemAnim = false;
    protected boolean isFirstAnim = true;//布局动画只执行一次
    protected boolean layoutAnim = false;//是否使用布局动画
    OnTouchListener mInterceptTouchListener;
    OnScrollEndListener mOnScrollEndListener;
    private OnScrollListener mScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Adapter adapter = getAdapterRaw();
            if (adapter != null && adapter instanceof RBaseAdapter) {
                ((RBaseAdapter) adapter).onScrollStateChanged(recyclerView, newState);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            Adapter adapter = getAdapterRaw();
            if (adapter != null && adapter instanceof RBaseAdapter) {
                ((RBaseAdapter) adapter).onScrolled(recyclerView, dx, dy);
            }
        }
    };
    private float mLastVelocity;
    private int mLastScrollOffset;

    public RRecyclerView(Context context) {
        this(context, null);
    }

    public RRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public static void ensureGlow(RecyclerView recyclerView, int color) {
        Reflect.invokeMethod(RecyclerView.class, recyclerView, "ensureTopGlow");
        Reflect.invokeMethod(RecyclerView.class, recyclerView, "ensureBottomGlow");
        Reflect.invokeMethod(RecyclerView.class, recyclerView, "ensureRightGlow");
        Reflect.invokeMethod(RecyclerView.class, recyclerView, "ensureLeftGlow");

        setEdgeEffect(recyclerView, color);
    }

    private static void setEdgeEffect(RecyclerView recyclerView, int color) {
        Object mGlow = Reflect.getMember(RecyclerView.class, recyclerView, "mTopGlow");
        setEdgetEffect(mGlow, color);
        mGlow = Reflect.getMember(RecyclerView.class, recyclerView, "mLeftGlow");
        setEdgetEffect(mGlow, color);
        mGlow = Reflect.getMember(RecyclerView.class, recyclerView, "mRightGlow");
        setEdgetEffect(mGlow, color);
        mGlow = Reflect.getMember(RecyclerView.class, recyclerView, "mBottomGlow");
        setEdgetEffect(mGlow, color);
    }

    public static void setEdgetEffect(Object edgeEffectCompat, @ColorInt int color) {
        Object mEdgeEffect = Reflect.getMember(edgeEffectCompat, "mEdgeEffect");
        Object mPaint = Reflect.getMember(mEdgeEffect, "mPaint");
        if (mPaint instanceof Paint) {
            ((Paint) mPaint).setColor(color);
        }
    }

    protected void initView(Context context) {
        String tag = (String) this.getTag();
        if (TextUtils.isEmpty(tag) || "V".equalsIgnoreCase(tag)) {
            layoutManager = new LinearLayoutManager(context, orientation, false);
        } else {
            //线性布局管理器
            if ("H".equalsIgnoreCase(tag)) {
                orientation = LinearLayoutManager.HORIZONTAL;
                layoutManager = new LinearLayoutManager(context, orientation, false);
            } else {
                //读取其他配置信息(数量和方向)
                final String type = tag.substring(0, 1);
                if (tag.length() >= 3) {
                    try {
                        spanCount = Integer.valueOf(tag.substring(2));//数量
                    } catch (Exception e) {
                    }
                }
                if (tag.length() >= 2) {
                    if ("H".equalsIgnoreCase(tag.substring(1, 2))) {
                        orientation = StaggeredGridLayoutManager.HORIZONTAL;//方向
                    }
                }

                //交错布局管理器
                if ("S".equalsIgnoreCase(type)) {
                    layoutManager = new StaggeredGridLayoutManager(spanCount, orientation);
                }
                //网格布局管理器
                else if ("G".equalsIgnoreCase(type)) {
                    layoutManager = new GridLayoutManager(context, spanCount, orientation, false);
                }
            }
        }

        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).setRecycleChildrenOnDetach(true);
        }
        this.setLayoutManager(layoutManager);

        setItemAnim(mItemAnim);
        //clearOnScrollListeners();
        removeOnScrollListener(mScrollListener);
        //添加滚动事件监听
        addOnScrollListener(mScrollListener);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ensureGlow(RRecyclerView.this, SkinHelper.getSkin().getThemeSubColor());
    }

    //-----------获取 默认的adapter, 获取 RBaseAdapter, 获取 AnimationAdapter----------//

    @Override
    public void setTag(Object tag) {
        super.setTag(tag);
        initView(getContext());
    }

    @Override
    public void startLayoutAnimation() {
        if (isFirstAnim) {
            super.startLayoutAnimation();
        }
        isFirstAnim = false;
    }

    /**
     * 是否设置布局动画
     */
    public void setLayoutAnim(boolean layoutAnim) {
        this.layoutAnim = layoutAnim;
        if (layoutAnim) {
            AnimUtil.applyLayoutAnimation(this);
        } else {
            setLayoutAnimation(null);
        }
    }

    //----------------end--------------------//

    /**
     * 请在{@link RRecyclerView#setAdapter(Adapter)}方法之前调用
     */
    public void setItemAnim(boolean itemAnim) {
        mItemAnim = itemAnim;
        if (mItemAnim) {
            this.setItemAnimator(new FadeInDownAnimator());
        } else {
            this.setItemAnimator(new DefaultItemAnimator());
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof RBaseAdapter) {
            mAdapterRaw = (RBaseAdapter) adapter;
        }
        mAnimationAdapter = getAnimationAdapter(adapter);

        if (mItemAnim) {
            super.setAdapter(mAnimationAdapter);
        } else {
            super.setAdapter(adapter);
        }
    }

    public RBaseAdapter getAdapterRaw() {
        return mAdapterRaw;
    }

    public AnimationAdapter getAnimationAdapter() {
        return mAnimationAdapter;
    }

    /**
     * 设置Item 动画类, 用于 添加 和 删除 Item时候的动画
     */
    public RRecyclerView setBaseItemAnimator(Class<? extends BaseItemAnimator> animator) {
        try {
            super.setItemAnimator(animator.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 设置Item滑动时的动画,用于滑动查看时的动画
     */
    public void setAnimatorAdapter(Class<? extends AnimationAdapter> animatorAdapter, Adapter adapter) {
        setAnimatorAdapter(animatorAdapter);
        setAdapter(adapter);
    }

    public RRecyclerView setAnimatorAdapter(Class<? extends AnimationAdapter> animatorAdapter) {
        this.animatorAdapter = animatorAdapter;
        return this;
    }

    /**
     * 将默认的adapter, 包裹一层动画adapter
     */
    private AnimationAdapter getAnimationAdapter(Adapter adapter) {
        AnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
        if (animatorAdapter != null) {
            try {
                final Constructor<? extends AnimationAdapter> constructor =
                        animatorAdapter.getDeclaredConstructor(Adapter.class);
                animationAdapter = constructor.newInstance(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return animationAdapter;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean result = super.onTouchEvent(e);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (mInterceptTouchListener != null) {
            mInterceptTouchListener.onTouch(this, e);
        }

        //项目特殊处理, 可以删除
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(0);
            Rect rect = new Rect();
            childAt.getGlobalVisibleRect(rect);
            if (childAt instanceof RecyclerView && rect.contains(((int) e.getRawX()), (int) e.getRawY())) {
                //如果touch在另一个RecycleView上面, 那么不拦截事件
                return false;
            }
        }
        //--------end--------
        return super.onInterceptTouchEvent(e);
    }

    public void setOnInterceptTouchListener(OnTouchListener l) {
        mInterceptTouchListener = l;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        //L.e("call: onScrolled([dx, dy])-> " + getLastVelocity());
    }

    @Override
    public void onScrollStateChanged(int state) {
        //L.e("call: onScrollStateChanged([state])-> " + state + " :" + getLastVelocity());
        final int scrollOffset = computeVerticalScrollOffset();
        if (state == SCROLL_STATE_IDLE) {
            post(new Runnable() {
                @Override
                public void run() {
                    if (mLastScrollOffset != scrollOffset && mOnScrollEndListener != null) {
                        if (scrollOffset == 0
                                && UI.canChildScrollDown(RRecyclerView.this)) {
                            mOnScrollEndListener.onScrollTopEnd(getLastVelocity());
                        }
                    }
                    mLastScrollOffset = -1;
                }
            });
        } else {
            mLastScrollOffset = scrollOffset;
        }
    }

    public void setOnScrollEndListener(OnScrollEndListener onScrollEndListener) {
        mOnScrollEndListener = onScrollEndListener;
    }

    /**
     * 滚动结束后时的速率
     */
    public float getLastVelocity() {
        Object mViewFlinger = Reflect.getMember(RecyclerView.class, this, "mViewFlinger");
        ScrollerCompat mScroller = (ScrollerCompat) Reflect.getMember(mViewFlinger, "mScroller");
        float currVelocity = mScroller.getCurrVelocity();
        if (Float.isNaN(currVelocity)) {
            currVelocity = mLastVelocity;
        } else {
            mLastVelocity = currVelocity;
        }
        return currVelocity;
    }

    /**
     * RecyclerView滚动结束后的回调
     */
    public interface OnScrollEndListener {
        /**
         * 突然滚动到顶部, 还剩余的滚动速率
         */
        void onScrollTopEnd(float currVelocity);
    }
}