package com.angcyo.uiview.recycler;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextPaint;
import android.view.View;

/**
 * 自定制超强的分割线
 * Created by angcyo on 星期三 2017-2-15
 */

public class RExItemDecoration extends RecyclerView.ItemDecoration {

    TextPaint mTextPaint;
    ItemDecorationCallback mItemDecorationCallback;

    public RExItemDecoration(ItemDecorationCallback itemDecorationCallback) {
        mItemDecorationCallback = itemDecorationCallback;
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    }

    public static RExItemDecoration build(ItemDecorationCallback itemDecorationCallback) {
        return new RExItemDecoration(itemDecorationCallback);
    }

    /**
     * 判断 viewLayoutPosition 是否是一排的结束位置 (垂直水平通用)
     */
    public static boolean isEndOfGrid(int viewLayoutPosition, int spanCount) {
        return viewLayoutPosition % spanCount == spanCount - 1;
    }

    /**
     * 判断 viewLayoutPosition 所在的位置,是否是最后一排(垂直水平通用)
     */
    public static boolean isLastOfGrid(int itemCount, int viewLayoutPosition, int spanCount) {
        boolean result = false;
        final double ceil = Math.ceil(((float) itemCount) / spanCount);
        if (viewLayoutPosition >= ceil * spanCount - spanCount) {
            result = true;
        }
        return result;
    }

    //------------------------------------------公共方法---------------------------------

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof StaggeredGridLayoutManager) {
            return;
        }

        //线性布局
        final LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
        final int firstItem = layoutManager.findFirstVisibleItemPosition();
        for (int i = 0; i < layoutManager.getChildCount(); i++) {
            final int viewAdapterPosition = firstItem + i;
            final View view = layoutManager.findViewByPosition(viewAdapterPosition);
            if (view != null) {
                mItemDecorationCallback.draw(c, mTextPaint, view,
                        mItemDecorationCallback.getItemOffsets(layoutManager, viewAdapterPosition),
                        layoutManager.getItemCount(), viewAdapterPosition);
            }
        }
    }

    //------------------------------------------私有方法---------------------------------

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //do nothing here
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();//布局管理器
        final RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        final int viewLayoutPosition = layoutParams.getViewLayoutPosition();//布局时当前View的位置
        final int viewAdapterPosition = layoutParams.getViewAdapterPosition();

        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return;
        }

        final Rect offsets = mItemDecorationCallback.getItemOffsets((LinearLayoutManager) layoutManager, viewAdapterPosition);
        outRect.set(offsets.left, offsets.top, offsets.right, offsets.bottom);
    }

    public interface ItemDecorationCallback {
        /**
         * 返回需要腾出的空间大小
         */
        Rect getItemOffsets(LinearLayoutManager layoutManager, int position);

        /**
         * 绘制分割线
         */
        void draw(Canvas canvas, TextPaint paint, View itemView, Rect offsetRect, int itemCount, int position);
    }

    public static abstract class SingleItemCallback implements ItemDecorationCallback {

        Rect mRect;

        public SingleItemCallback() {
            mRect = new Rect();
        }

        @Override
        final public Rect getItemOffsets(LinearLayoutManager layoutManager, int position) {
            mRect.set(0, 0, 0, 0);
            getItemOffsets(mRect, position);
            return mRect;
        }

        public abstract void getItemOffsets(Rect outRect, int position);

        @Override
        public void draw(Canvas canvas, TextPaint paint, View itemView, Rect offsetRect, int itemCount, int position) {
            paint.setColor(Color.GRAY);
            offsetRect.set(0, itemView.getTop() - offsetRect.top, itemView.getRight(), itemView.getTop());
            canvas.drawRect(offsetRect, paint);
        }
    }
}
