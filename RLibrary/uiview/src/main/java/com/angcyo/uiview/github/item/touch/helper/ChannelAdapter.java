package com.angcyo.uiview.github.item.touch.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.angcyo.uiview.R;
import com.angcyo.uiview.utils.RUtils;

import java.util.List;


/**
 * https://github.com/YoKeyword/ItemTouchHelperDemo
 * 拖拽排序 + 增删
 * Created by YoKeyword on 15/12/28.
 */
public class ChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemMoveListener {
    // 我的频道 标题部分
    public static final int TYPE_MY_CHANNEL_HEADER = 0;
    // 我的频道
    public static final int TYPE_MY = 1;
    // 其他频道 标题部分
    public static final int TYPE_OTHER_CHANNEL_HEADER = 2;
    // 其他频道
    public static final int TYPE_OTHER = 3;

    // 我的频道之前的header数量  该demo中 即标题部分 为 1
    private static final int COUNT_PRE_MY_HEADER = 1;
    // 其他频道之前的header数量  该demo中 即标题部分 为 COUNT_PRE_MY_HEADER + 1
    private static final int COUNT_PRE_OTHER_HEADER = COUNT_PRE_MY_HEADER + 1;

    private static final long ANIM_TIME = 300L;
    // touch 间隔时间  用于分辨是否是 "点击"
    private static final long SPACE_TIME = 100;
    ItemDragHelperCallback.OnDragCallback mOnDragCallback;
    OnFinishListener mOnFinishListener;
    // touch 点击开始时间
    private long startTime;
    private LayoutInflater mInflater;
    private ItemTouchHelper mItemTouchHelper;
    // 是否为 编辑 模式
    private boolean isEditMode;
    private List<ChannelEntity> mMyChannelItems, mOtherChannelItems;
    // 我的频道点击事件
    private OnMyChannelItemClickListener mChannelItemClickListener;
    private Handler delayHandler = new Handler();
    private RecyclerView mRecyclerView;

    public ChannelAdapter(Context context, ItemTouchHelper helper, List<ChannelEntity> mMyChannelItems, List<ChannelEntity> mOtherChannelItems) {
        this.mInflater = LayoutInflater.from(context);
        this.mItemTouchHelper = helper;
        this.mMyChannelItems = mMyChannelItems;
        this.mOtherChannelItems = mOtherChannelItems;
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        mOnFinishListener = onFinishListener;
    }

    public void setOnDragCallback(ItemDragHelperCallback.OnDragCallback onDragCallback) {
        mOnDragCallback = onDragCallback;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {    // 我的频道 标题部分
            return TYPE_MY_CHANNEL_HEADER;
        } else if (position == mMyChannelItems.size() + 1) {    // 其他频道 标题部分
            return TYPE_OTHER_CHANNEL_HEADER;
        } else if (position > 0 && position < mMyChannelItems.size() + 1) {
            return TYPE_MY;
        } else {
            return TYPE_OTHER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view;
        mRecyclerView = (RecyclerView) parent;
        switch (viewType) {
            case TYPE_MY_CHANNEL_HEADER:
                view = mInflater.inflate(R.layout.item_my_channel_header, parent, false);
                final MyChannelHeaderViewHolder holder = new MyChannelHeaderViewHolder(view);
                holder.tvBtnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isEditMode) {
                            startEditMode(mRecyclerView);
                            holder.tvBtnEdit.setText(R.string.base_finish);
                        } else {
                            cancelEditMode(mRecyclerView);
                            holder.tvBtnEdit.setText(R.string.base_edit);
                        }
                    }
                });
                return holder;

            case TYPE_MY:
                view = mInflater.inflate(R.layout.item_my, parent, false);
                final MyViewHolder myHolder = new MyViewHolder(view);

                myHolder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        int position = myHolder.getAdapterPosition();
                        RecyclerView recyclerView = (mRecyclerView);
                        int targetX, targetY;

                        if (isEditMode) {
                            View targetView = recyclerView.getLayoutManager().findViewByPosition(mMyChannelItems.size() + COUNT_PRE_OTHER_HEADER);
                            View currentView = recyclerView.getLayoutManager().findViewByPosition(position);
                            // 如果targetView不在屏幕内,则indexOfChild为-1  此时不需要添加动画,因为此时notifyItemMoved自带一个向目标移动的动画
                            // 如果在屏幕内,则添加一个位移动画
                            if (recyclerView.indexOfChild(targetView) >= 0) {

                                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                                int spanCount = ((GridLayoutManager) manager).getSpanCount();

                                // 移动后 高度将变化 (我的频道Grid 最后一个item在新的一行第一个)
                                if ((mMyChannelItems.size() - COUNT_PRE_MY_HEADER) % spanCount == 0) {
                                    View preTargetView = recyclerView.getLayoutManager().findViewByPosition(mMyChannelItems.size() + COUNT_PRE_OTHER_HEADER - 1);
                                    targetX = preTargetView.getLeft();
                                    targetY = preTargetView.getTop();
                                } else {
                                    targetX = targetView.getLeft();
                                    targetY = targetView.getTop();
                                }

                                moveMyToOther(myHolder);
                                startAnimation(recyclerView, currentView, targetX, targetY);

                            } else if (targetView == null) {
                                View preTargetView = recyclerView.getLayoutManager().findViewByPosition(mMyChannelItems.size() + COUNT_PRE_OTHER_HEADER - 1);
                                targetX = preTargetView.getLeft();
                                targetY = preTargetView.getTop();
                                moveMyToOther(myHolder);
                                startAnimation(recyclerView, currentView, targetX, targetY + preTargetView.getMeasuredHeight());
                            } else {
                                moveMyToOther(myHolder);
                            }
                        } else {
                            if (mChannelItemClickListener != null) {
                                mChannelItemClickListener.onItemClick(v, position - COUNT_PRE_MY_HEADER);
                            }
                        }
                    }
                });

                myHolder.textView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View v) {
                        if (!isEditMode) {
                            startEditMode(mRecyclerView);

                            // header 按钮文字 改成 "完成"
                            View view = mRecyclerView.getChildAt(0);
                            if (view == mRecyclerView.getLayoutManager().findViewByPosition(0)) {
                                TextView tvBtnEdit = (TextView) view.findViewById(R.id.tv_btn_edit);
                                tvBtnEdit.setText(R.string.base_finish);
                            }
                        }

                        mItemTouchHelper.startDrag(myHolder);
                        return true;
                    }
                });

                myHolder.textView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (isEditMode) {
                            switch (MotionEventCompat.getActionMasked(event)) {
                                case MotionEvent.ACTION_DOWN:
                                    startTime = System.currentTimeMillis();
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    if (System.currentTimeMillis() - startTime > SPACE_TIME) {
                                        mItemTouchHelper.startDrag(myHolder);
                                    }
                                    break;
                                case MotionEvent.ACTION_CANCEL:
                                case MotionEvent.ACTION_UP:
                                    startTime = 0;
                                    break;
                            }

                        }
                        return false;
                    }
                });
                myHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myHolder.textView.performClick();
                    }
                });
                return myHolder;

            case TYPE_OTHER_CHANNEL_HEADER:
                view = mInflater.inflate(R.layout.item_other_channel_header, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };

            case TYPE_OTHER:
                view = mInflater.inflate(R.layout.item_other, parent, false);
                final OtherViewHolder otherHolder = new OtherViewHolder(view);
                otherHolder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecyclerView recyclerView = (mRecyclerView);
                        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                        int currentPiosition = otherHolder.getAdapterPosition();
                        // 如果RecyclerView滑动到底部,移动的目标位置的y轴 - height
                        View currentView = manager.findViewByPosition(currentPiosition);
                        // 目标位置的前一个item  即当前MyChannel的最后一个
                        View preTargetView = manager.findViewByPosition(mMyChannelItems.size() - 1 + COUNT_PRE_MY_HEADER);

                        // 如果targetView不在屏幕内,则为-1  此时不需要添加动画,因为此时notifyItemMoved自带一个向目标移动的动画
                        // 如果在屏幕内,则添加一个位移动画
                        if (recyclerView.indexOfChild(preTargetView) >= 0) {
                            int targetX = preTargetView.getLeft();
                            int targetY = preTargetView.getTop();

                            int targetPosition = mMyChannelItems.size() - 1 + COUNT_PRE_OTHER_HEADER;

                            GridLayoutManager gridLayoutManager = ((GridLayoutManager) manager);
                            int spanCount = gridLayoutManager.getSpanCount();
                            // target 在最后一行第一个
                            if ((targetPosition - COUNT_PRE_MY_HEADER) % spanCount == 0) {
                                View targetView = manager.findViewByPosition(targetPosition);
                                targetX = targetView.getLeft();
                                targetY = targetView.getTop();
                            } else {
                                targetX += preTargetView.getWidth();

                                // 最后一个item可见
                                if (gridLayoutManager.findLastVisibleItemPosition() == getItemCount() - 1) {
                                    // 最后的item在最后一行第一个位置
                                    if ((getItemCount() - 1 - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER) % spanCount == 0) {
                                        // RecyclerView实际高度 > 屏幕高度 && RecyclerView实际高度 < 屏幕高度 + item.height
                                        int firstVisiblePostion = gridLayoutManager.findFirstVisibleItemPosition();
                                        if (firstVisiblePostion == 0) {
                                            // FirstCompletelyVisibleItemPosition == 0 即 内容不满一屏幕 , targetY值不需要变化
                                            // // FirstCompletelyVisibleItemPosition != 0 即 内容满一屏幕 并且 可滑动 , targetY值 + firstItem.getTop
                                            if (gridLayoutManager.findFirstCompletelyVisibleItemPosition() != 0) {
                                                int offset = (-recyclerView.getChildAt(0).getTop()) - recyclerView.getPaddingTop();
                                                targetY += offset;
                                            }
                                        } else { // 在这种情况下 并且 RecyclerView高度变化时(即可见第一个item的 position != 0),
                                            // 移动后, targetY值  + 一个item的高度
                                            targetY += preTargetView.getHeight();
                                        }
                                    }
                                } else {
                                    System.out.println("current--No");
                                }
                            }

                            // 如果当前位置是otherChannel可见的最后一个
                            // 并且 当前位置不在grid的第一个位置
                            // 并且 目标位置不在grid的第一个位置

                            // 则 需要延迟250秒 notifyItemMove , 这是因为这种情况 , 并不触发ItemAnimator , 会直接刷新界面
                            // 导致我们的位移动画刚开始,就已经notify完毕,引起不同步问题
                            if (currentPiosition == gridLayoutManager.findLastVisibleItemPosition()
                                    && (currentPiosition - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER) % spanCount != 0
                                    && (targetPosition - COUNT_PRE_MY_HEADER) % spanCount != 0) {
                                moveOtherToMyWithDelay(otherHolder);
                            } else {
                                moveOtherToMy(otherHolder);
                            }
                            startAnimation(recyclerView, currentView, targetX, targetY);

                        } else {
                            moveOtherToMy(otherHolder);
                        }
                    }
                });
                return otherHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {

            MyViewHolder myHolder = (MyViewHolder) holder;
            myHolder.textView.setText(mMyChannelItems.get(position - COUNT_PRE_MY_HEADER).getName());

            if (mOnDragCallback != null) {
                myHolder.textView.setEnabled(mOnDragCallback.canDragDirs(holder));
                myHolder.imgEdit.setVisibility(mOnDragCallback.canDragDirs(holder) && isEditMode ? View.VISIBLE : View.INVISIBLE);
            } else {
                myHolder.textView.setEnabled(true);
                myHolder.imgEdit.setVisibility(isEditMode ? View.VISIBLE : View.INVISIBLE);
            }

        } else if (holder instanceof OtherViewHolder) {

            ((OtherViewHolder) holder).textView.setText(mOtherChannelItems.get(position - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER).getName());

        } else if (holder instanceof MyChannelHeaderViewHolder) {

            MyChannelHeaderViewHolder headerHolder = (MyChannelHeaderViewHolder) holder;
            if (isEditMode) {
                headerHolder.tvBtnEdit.setText(R.string.base_finish);
            } else {
                headerHolder.tvBtnEdit.setText(R.string.base_edit);
            }
        }
    }

    @Override
    public int getItemCount() {
        // 我的频道  标题 + 我的频道.size + 其他频道 标题 + 其他频道.size
        return mMyChannelItems.size() + mOtherChannelItems.size() + COUNT_PRE_OTHER_HEADER;
    }

    /**
     * 开始增删动画
     */
    private void startAnimation(final RecyclerView recyclerView, final View currentView, final float targetX, final float targetY) {
        ViewParent parent = recyclerView.getParent();
        final ViewGroup viewGroup;
//        if (parent instanceof RecyclerView) {
//            ViewParent parent2 = parent.getParent();
//            if (parent2 instanceof RefreshLayout) {
//                ViewParent parent3 = parent2.getParent();
//                viewGroup = (ViewGroup) parent3;
//            } else {
//                viewGroup = (ViewGroup) parent2;
//            }
//        } else {
//            viewGroup = (ViewGroup) parent;
//        }

        viewGroup = (ViewGroup) ((Activity) recyclerView.getContext()).getWindow().findViewById(Window.ID_ANDROID_CONTENT);

        final Animation animation = getTranslateAnimator(
                targetX - currentView.getLeft(), targetY - currentView.getTop());
        currentView.setVisibility(View.INVISIBLE);

        final int[] locations = new int[2];
        currentView.getLocationOnScreen(locations);

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                final ImageView mirrorView = addMirrorView(viewGroup, recyclerView, currentView, locations);
                mirrorView.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        viewGroup.removeView(mirrorView);
                        if (currentView.getVisibility() == View.INVISIBLE) {
                            currentView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }

    /**
     * 我的频道 移动到 其他频道
     *
     * @param myHolder
     */
    private void moveMyToOther(MyViewHolder myHolder) {
        int position = myHolder.getAdapterPosition();

        int startPosition = position - COUNT_PRE_MY_HEADER;
        if (startPosition > mMyChannelItems.size() - 1) {
            return;
        }
        ChannelEntity item = mMyChannelItems.get(startPosition);
        mMyChannelItems.remove(startPosition);
        mOtherChannelItems.add(0, item);

        notifyItemMoved(position, mMyChannelItems.size() + COUNT_PRE_OTHER_HEADER);
    }

    /**
     * 其他频道 移动到 我的频道
     *
     * @param otherHolder
     */
    private void moveOtherToMy(OtherViewHolder otherHolder) {
        if (!isEditMode) {
            startEditMode(mRecyclerView);
            // header 按钮文字 改成 "完成"
            View view = mRecyclerView.getChildAt(0);
            if (view == mRecyclerView.getLayoutManager().findViewByPosition(0)) {
                TextView tvBtnEdit = (TextView) view.findViewById(R.id.tv_btn_edit);
                tvBtnEdit.setText(R.string.base_finish);
            }
        }

        int position = processItemRemoveAdd(otherHolder);
        if (position == -1) {
            return;
        }
        notifyItemMoved(position, mMyChannelItems.size() - 1 + COUNT_PRE_MY_HEADER);
    }

    /**
     * 其他频道 移动到 我的频道 伴随延迟
     *
     * @param otherHolder
     */
    private void moveOtherToMyWithDelay(OtherViewHolder otherHolder) {
        final int position = processItemRemoveAdd(otherHolder);
        if (position == -1) {
            return;
        }
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyItemMoved(position, mMyChannelItems.size() - 1 + COUNT_PRE_MY_HEADER);
            }
        }, ANIM_TIME);
    }

    private int processItemRemoveAdd(OtherViewHolder otherHolder) {
        int position = otherHolder.getAdapterPosition();

        int startPosition = position - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER;
        if (startPosition > mOtherChannelItems.size() - 1) {
            return -1;
        }
        ChannelEntity item = mOtherChannelItems.get(startPosition);
        mOtherChannelItems.remove(startPosition);
        mMyChannelItems.add(item);
        return position;
    }


    /**
     * 添加需要移动的 镜像View
     */
    private ImageView addMirrorView(ViewGroup parent, RecyclerView recyclerView, View view, int[] locations) {
        /**
         * 我们要获取cache首先要通过setDrawingCacheEnable方法开启cache，然后再调用getDrawingCache方法就可以获得view的cache图片了。
         buildDrawingCache方法可以不用调用，因为调用getDrawingCache方法时，若果cache没有建立，系统会自动调用buildDrawingCache方法生成cache。
         若想更新cache, 必须要调用destoryDrawingCache方法把旧的cache销毁，才能建立新的。
         当调用setDrawingCacheEnabled方法设置为false, 系统也会自动把原来的cache销毁。
         */
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        final ImageView mirrorView = new ImageView(recyclerView.getContext());
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        mirrorView.setImageBitmap(bitmap);
        view.setDrawingCacheEnabled(false);
        int[] parenLocations = new int[2];
        recyclerView.getLocationOnScreen(parenLocations);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        params.setMargins(locations[0], locations[1]/* - parenLocations[1]*/, 0, 0);
        parent.addView(mirrorView, params);

        return mirrorView;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        ChannelEntity item = mMyChannelItems.get(fromPosition - COUNT_PRE_MY_HEADER);
        mMyChannelItems.remove(fromPosition - COUNT_PRE_MY_HEADER);
        mMyChannelItems.add(toPosition - COUNT_PRE_MY_HEADER, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * 开启编辑模式
     *
     * @param parent
     */
    private void startEditMode(RecyclerView parent) {
        isEditMode = true;

        int visibleChildCount = parent.getChildCount();
        for (int i = 0; i < visibleChildCount; i++) {
            View view = parent.getChildAt(i);
            ImageView imgEdit = (ImageView) view.findViewById(R.id.img_edit);
            if (mOnDragCallback != null) {
                if (mOnDragCallback.canDragDirs(parent.findViewHolderForLayoutPosition(i))) {
                    if (imgEdit != null) {
                        imgEdit.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (imgEdit != null) {
                        imgEdit.setVisibility(View.INVISIBLE);
                    }
                }
            } else {
                if (imgEdit != null) {
                    imgEdit.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 完成编辑模式
     *
     * @param parent
     */
    private void cancelEditMode(RecyclerView parent) {
        isEditMode = false;

        int visibleChildCount = parent.getChildCount();
        for (int i = 0; i < visibleChildCount; i++) {
            View view = parent.getChildAt(i);
            ImageView imgEdit = (ImageView) view.findViewById(R.id.img_edit);
            if (imgEdit != null) {
                imgEdit.setVisibility(View.INVISIBLE);
            }
        }

        if (mOnFinishListener != null) {
            StringBuilder builder = new StringBuilder();
            for (ChannelEntity entity : mMyChannelItems) {
                builder.append(entity.getId());
                builder.append(",");
            }
            mOnFinishListener.onFinish(RUtils.safe(builder));
        }
    }

    /**
     * 获取位移动画
     */
    private TranslateAnimation getTranslateAnimator(float targetX, float targetY) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetX,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetY);
        // RecyclerView默认移动动画250ms 这里设置360ms 是为了防止在位移动画结束后 remove(view)过早 导致闪烁
        translateAnimation.setDuration(ANIM_TIME);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    public void setOnMyChannelItemClickListener(OnMyChannelItemClickListener listener) {
        this.mChannelItemClickListener = listener;
    }

    interface OnMyChannelItemClickListener {
        void onItemClick(View v, int position);
    }

    public interface OnFinishListener {
        void onFinish(String ids);
    }

    /**
     * 我的频道
     */
    class MyViewHolder extends RecyclerView.ViewHolder implements OnDragVHListener {
        private TextView textView;
        private ImageView imgEdit;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
            imgEdit = (ImageView) itemView.findViewById(R.id.img_edit);
        }

        /**
         * item 被选中时
         */
        @Override
        public void onItemSelected() {
            //textView.setBackgroundResource(R.drawable.bg_channel_p);
        }

        /**
         * item 取消选中时
         */
        @Override
        public void onItemFinish() {
            //textView.setBackgroundResource(R.drawable.bg_channel);
        }
    }

    /**
     * 其他频道
     */
    class OtherViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public OtherViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    /**
     * 我的频道  标题部分
     */
    class MyChannelHeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBtnEdit;

        public MyChannelHeaderViewHolder(View itemView) {
            super(itemView);
            tvBtnEdit = (TextView) itemView.findViewById(R.id.tv_btn_edit);
        }
    }
}
