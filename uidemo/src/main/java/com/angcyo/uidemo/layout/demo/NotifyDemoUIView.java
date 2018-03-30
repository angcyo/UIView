package com.angcyo.uidemo.layout.demo;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.Gravity;
import android.view.View;

import com.angcyo.uidemo.R;
import com.angcyo.uidemo.layout.LayoutDemoActivity;
import com.angcyo.uidemo.layout.base.BaseItemUIView;
import com.angcyo.uiview.base.Item;
import com.angcyo.uiview.base.SingleItem;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.utils.NotifyUtil;
import com.angcyo.uiview.utils.ProgressNotify;
import com.angcyo.uiview.utils.T;
import com.angcyo.uiview.utils.T_;
import com.angcyo.uiview.utils.Tip;

import java.util.List;

/**
 * Created by angcyo on 2017-05-28.
 */

public class NotifyDemoUIView extends BaseItemUIView {

    private int mIndex;
    private Runnable mProgressRunnable;

    @Override
    protected void createItems(List<SingleItem> items) {
        items.add(new SingleItem() {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                holder.click(R.id.notify_normal_singline, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new NotifyUtil(mActivity, 10002).notify_normal_singline(getPendingIntent(), R.mipmap.demo_logo,
                                "ticker", "title", "notify_normal_singline", true, true, true);
                    }
                });
                holder.click(R.id.notify_bigPic, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new NotifyUtil(mActivity, 10001).notify_bigPic(getPendingIntent(), R.mipmap.demo_logo,
                                "ticker", "title", "notify_bigPic", R.mipmap.demo_logo, true, true, true);
                    }
                });
                holder.click(R.id.show_notify, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showNotify();
                    }
                });
                holder.click(R.id.show_progress_notify, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mIndex = 0;
                        mProgressRunnable = new Runnable() {
                            @Override
                            public void run() {
                                ProgressNotify.instance()
                                        .setClickActivity(LayoutDemoActivity.class)
                                        .show("title", R.mipmap.demo_logo, mIndex);
                                mIndex++;

                                if (mIndex >= 100) {
                                    ProgressNotify.instance()
                                            .setClickActivity(LayoutDemoActivity.class)
                                            .show("下载完成", R.mipmap.demo_logo, mIndex);
                                } else {
                                    postDelayed(mProgressRunnable, 100);
                                }
                            }
                        };
                        post(mProgressRunnable);
                    }
                });

                holder.click(R.id.t1_view, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.show(mActivity, "T Toast Test");
                    }
                });
                holder.click(R.id.t2_view, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T_.info("T_ Toast Test");
                        postDelayed(1000, new Runnable() {
                            @Override
                            public void run() {
                                T_.error("T_ Toast TestT_ Toast TestT_ Toast TestT_ Toast Test" +
                                        "T_ Toast TestT_ Toast TestT_ Toast TestT_ Toast Test" +
                                        "T_ Toast TestT_ Toast TestT_ Toast TestT_ Toast Test" +
                                        "T_ Toast TestT_ Toast TestT_ Toast TestT_ Toast Test" +
                                        "T_ Toast TestT_ Toast TestT_ Toast TestT_ Toast TestT_ Toast Test" +
                                        "T_ Toast TestT_ Toast TestT_ Toast TestT_ Toast TestT_ Toast Test", Gravity.LEFT);
                            }
                        });
                    }
                });
                holder.click(R.id.tip_view, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T_.ok("T_ Toast Test", Gravity.LEFT);
                        Tip.tip("Tip Toast");
                    }
                });
            }

            @Override
            public int getItemLayoutId() {
                return R.layout.item_notify;
            }
        });

    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(mActivity, LayoutDemoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(mActivity, 10000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void showNotify() {
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(mActivity);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mActivity);

        builder.setContentText("showNotify");
        builder.setContentTitle("title");
        builder.setSmallIcon(R.mipmap.demo_logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.demo_logo));//显示在通知内容右边的图片
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        builder.setContentIntent(getPendingIntent());

        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//如果不设置提示, 横幅通知就不会出现. (横幅需要系统允许)

//        builder.setTicker("TickerText");

//        RemoteViews mRemoteViews = new RemoteViews(mActivity.getPackageName(), R.layout.base_progress_notify_layout);
//        builder.setTicker("TickerText", mRemoteViews);

        managerCompat.notify((int) System.currentTimeMillis(), builder.build());
    }
}
