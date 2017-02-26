package com.angcyo.uiview.container;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.R;
import com.angcyo.uiview.resources.ResUtil;
import com.angcyo.uiview.view.IView;

/**
 * 对FrameLayout进行分层显示, 不同层显示不同内容
 * Created by angcyo on 2016-11-05.
 */
@Deprecated
public abstract class ContainerWrapper extends FrameLayout {
    private static final String TAG = "ContainerWrapper";

    /**
     * 整个布局的背景颜色
     */
    protected static int mBackgroundColor = Color.WHITE;
    /**
     * 标题栏,最后一次的颜色, 颜色动画的开始值
     */
    @ColorInt
    protected int mTitleBarBGColor = Color.WHITE;
    protected boolean isAttachedToWindow = false;

    /**
     * 是否加载状态栏, 不加载以便节约资源
     */
    protected boolean loadTitleBar = true;

    /**
     * 内容层, 用来呈现内容
     */
    protected UILayoutImpl mContentLayout;

    /**
     * 标题层, 用来呈现标题, 需要在 内容层上面
     */
    protected FrameLayout mTitleBarLayout;

    /**
     * 覆盖层, 在标题层上面
     */
    protected UILayoutImpl mOverlayLayout;

    /**
     * 对话框弹出层, 在最上面
     */
    protected UILayoutImpl mDialogLayout;

    /**
     * {@link #loadTitleBar }为true时, 才会创建标题栏
     */
    protected UITitleBarContainer mUITitleBarContainer;

    protected Context mContext;

    public ContainerWrapper(Context context) {
        super(context);
        post(new Runnable() {
            @Override
            public void run() {
                L.e("on post run in UIContainer");
            }
        });
        initContainer(context);
    }

    public ContainerWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        initContainer(context);
    }

    public ContainerWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initContainer(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ContainerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initContainer(context);
    }

    /**
     * 获取标题栏的高度
     */
    public static int getTitleBarHeight(Activity activity) {
        final Resources resources = activity.getResources();
        int height;
        if (ResUtil.isLayoutFullscreen(activity)) {
            height = resources.getDimensionPixelSize(R.dimen.title_bar_height);
        } else {
            height = resources.getDimensionPixelSize(R.dimen.action_bar_height);
        }

        return height;
    }

    private void initContainer(Context context) {
        mContext = context;
        setWillNotDraw(false);
        setBackgroundColor(mBackgroundColor);

        addContentLayout(context);//内容
        addTitleBarLayout(context);//标题
        addOverlayLayout(context);//覆盖
        addDialogLayout(context);//对话框

        post(new Runnable() {
            @Override
            public void run() {
                L.e("on post run in initContainer");
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        L.i(TAG, "onMeasure: " + widthMeasureSpec + " " + heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        L.i(TAG, "onLayout: " + changed + " " + left + " " + top + " " + right + " " + bottom);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void onViewAdded(View child) {
        L.i(TAG, "onViewAdded: " + child);
        super.onViewAdded(child);
    }

    @Override
    public void onViewRemoved(View child) {
        L.i(TAG, "onViewRemoved: " + child);
        super.onViewRemoved(child);
    }

    @Override
    protected void onAttachedToWindow() {
        L.i(TAG, "onAttachedToWindow: ");
        super.onAttachedToWindow();
        post(new Runnable() {
            @Override
            public void run() {
                L.e("on post run onAttachedToWindow");
                isAttachedToWindow = true;
                loadOnAttachedToWindow();
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        L.i(TAG, "onDetachedFromWindow: ");
        super.onDetachedFromWindow();
        isAttachedToWindow = false;
        unLoadOnDetachedFromWindow();
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        L.i(TAG, "onFocusChanged: " + gainFocus + " " + direction);
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        L.i(TAG, "onWindowFocusChanged: " + hasWindowFocus);
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        L.i(TAG, "onVisibilityChanged: " + changedView + " " + visibility);
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        L.i(TAG, "onWindowVisibilityChanged: " + visibility);
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        L.i(TAG, "onConfigurationChanged: " + newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        L.i(TAG, "onScrollChanged: " + l + " " + t + " " + oldl + " " + oldt);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        L.i(TAG, "onSizeChanged: " + w + " " + h + " " + oldw + " " + oldh);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        L.i(TAG, "onDraw: ");
        super.onDraw(canvas);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        L.i(TAG, "onSaveInstanceState: ");
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        L.i(TAG, "onRestoreInstanceState: ");
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onAnimationStart() {
        L.i(TAG, "onAnimationStart: ");
        super.onAnimationStart();
    }

    //------------------------------共有方法--------------------------//

    @Override
    protected void onAnimationEnd() {
        L.i(TAG, "onAnimationEnd: ");
        super.onAnimationEnd();
    }

    /**
     * 隐藏标题栏
     */
    public void hideTitelBar() {
        mTitleBarLayout.setVisibility(GONE);
    }

    public void showTitleBar() {
        mTitleBarLayout.setVisibility(VISIBLE);
    }

    /**
     * 启动一个普通的IView
     */
    public void startIView(final IView iView, boolean anim) {
        mContentLayout.startIView(iView, new UIParam(anim));
    }

    public void startIView(final IView iView) {
        startIView(iView, true);
    }

    /**
     * 启动一个Dialog层的IView
     */
    public void startDialogIView(final IView iView, boolean anim) {
        mDialogLayout.startIView(iView, new UIParam(anim));
    }

    public void startDialogIView(final IView iView) {
        startDialogIView(iView, true);
    }

    /**
     * 启动一个Overlay层的IView
     */
    public void startOverlayIView(final IView iView, boolean anim) {
        mOverlayLayout.startIView(iView, new UIParam(anim));
    }

    public void startOverlayIView(final IView iView) {
        startOverlayIView(iView, true);
    }

    //------------------------------保护方法--------------------------//

    /**
     * 不加载标题栏,可以节约资源,请在显示之前调用.否则加载完成后, 无效果
     */
    public void setLoadTitleBar(boolean loadTitleBar) {
        this.loadTitleBar = loadTitleBar;
    }

    protected void addContentLayout(Context context) {
        mContentLayout = new UILayoutImpl(context);
        addView(mContentLayout, new LayoutParams(-1, -1));
    }

    protected void addTitleBarLayout(Context context) {
        mTitleBarLayout = new FrameLayout(context);

        mTitleBarLayout.setMinimumHeight(getTitleBarHeight((Activity) mContext));

        mUITitleBarContainer = new UITitleBarContainer(context);
        mTitleBarLayout.addView(mUITitleBarContainer, new LayoutParams(-1, -2));

        addView(mTitleBarLayout, new LayoutParams(-1, -2));
    }

    protected void addOverlayLayout(Context context) {
        mOverlayLayout = new UILayoutImpl(context);
        addView(mOverlayLayout, new LayoutParams(-1, -1));
    }

    protected void addDialogLayout(Context context) {
        mDialogLayout = new UILayoutImpl(context);
        addView(mDialogLayout, new LayoutParams(-1, -1));
    }

    //------------------------------私有方法--------------------------//

    /**
     * 在这个方法里面, 可以通过 {@link #getMeasuredWidth()} {@link #getMeasuredHeight()} 获取布局的宽高
     */
    protected abstract void loadOnAttachedToWindow();

    protected void unLoadOnDetachedFromWindow() {
    }

}
