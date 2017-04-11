package com.angcyo.uiview.recycler;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.angcyo.uiview.recycler.adapter.RBaseAdapter;

import java.util.Collections;
import java.util.List;

public class RDragCallback extends ItemTouchHelper.SimpleCallback {
    OnDragCallback mDragCallback;

    public RDragCallback(OnDragCallback callback) {
        //第一个参数表示,什么方向支持拖拽
        super(ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0);
        mDragCallback = callback;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return mDragCallback.isLongPressDragEnabled();
    }

    @Override
    public int getDragDirs(RecyclerView recyclerView, ViewHolder viewHolder) {
        if (mDragCallback != null) {
            if (mDragCallback.canDragDirs(recyclerView, viewHolder)) {
                int dirs = 0;
                boolean dragDirs = mDragCallback.canHorizontalDragDirs(recyclerView, viewHolder);
                if (dragDirs) {
                    dirs |= ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                }
                dragDirs = mDragCallback.canVerticalDragDirs(recyclerView, viewHolder);
                if (dragDirs) {
                    dirs |= ItemTouchHelper.DOWN | ItemTouchHelper.UP;
                }
                return dirs;
            } else {
                return 0;
            }
        }
        return super.getDragDirs(recyclerView, viewHolder);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
        if (mDragCallback != null) {
            if (!mDragCallback.canDragDirs(recyclerView, target)) {
                return false;
            }

            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();

            mDragCallback.onMove(recyclerView, from, to);
            return true;
        }
        return false;
    }

    @Override
    public void onSwiped(ViewHolder viewHolder, int direction) {

    }

    @Override
    public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        mDragCallback.onClearView(recyclerView, viewHolder);
    }

    @Override
    public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    public interface OnDragCallback {
        boolean canDragDirs(RecyclerView recyclerView, ViewHolder viewHolder);

        boolean canVerticalDragDirs(RecyclerView recyclerView, ViewHolder viewHolder);

        boolean canHorizontalDragDirs(RecyclerView recyclerView, ViewHolder viewHolder);

        void onMove(RecyclerView recyclerView, int fromPosition, int toPosition);

        boolean isLongPressDragEnabled();

        void onClearView(RecyclerView recyclerView, ViewHolder viewHolder);
    }

    public static abstract class SingleDragCallback implements OnDragCallback {
        @Override
        public boolean canDragDirs(RecyclerView recyclerView, ViewHolder viewHolder) {
            return true;
        }

        @Override
        public boolean canHorizontalDragDirs(RecyclerView recyclerView, ViewHolder viewHolder) {
            return true;
        }

        @Override
        public boolean canVerticalDragDirs(RecyclerView recyclerView, ViewHolder viewHolder) {
            return true;
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public void onClearView(RecyclerView recyclerView, ViewHolder viewHolder) {

        }

        @Override
        public void onMove(RecyclerView recyclerView, int fromPosition, int toPosition) {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();

            if (adapter instanceof RBaseAdapter) {
                List allDatas = ((RBaseAdapter) adapter).getAllDatas();

                int size = allDatas.size();
                if (size > fromPosition && size > toPosition) {
                    Collections.swap(allDatas, fromPosition, toPosition);
                }
                adapter.notifyItemMoved(fromPosition, toPosition);
            }
        }
    }

}
