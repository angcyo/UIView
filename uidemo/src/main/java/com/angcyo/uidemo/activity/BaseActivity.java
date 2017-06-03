package com.angcyo.uidemo.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.angcyo.library.utils.L;

import java.util.List;

public class BaseActivity extends AppCompatActivity {
    public static void launcher(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.addFlags(Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS);
//        intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.e("call: onCreate([savedInstanceState])-> " + getClass().getSimpleName() + " :" + getTaskId());

        if (com.angcyo.uidemo.BuildConfig.SHOW_DEBUG) {
            logActivity("onCreate :" + System.currentTimeMillis());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        L.e("call: onNewIntent([intent])-> " + getClass().getSimpleName() + " :" + getTaskId());
        logActivity("onNewIntent :" + System.currentTimeMillis());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.e("call: onDestroy([])-> " + getClass().getSimpleName() + " :" + getTaskId());
    }

    private void logActivity(String log) {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//获得当前正在运行的activity
        List<ActivityManager.RunningTaskInfo> appList3 = mActivityManager.getRunningTasks(10);

        StringBuilder builder = new StringBuilder(log);
        int index = 0;
        for (ActivityManager.RunningTaskInfo running : appList3) {
            if (!running.baseActivity.getClassName().contains("angcyo")) {
                continue;
            }
            builder.append("\n-----------------------" + index + "-------------------------------");
            builder.append("\nbase:");
            builder.append(running.baseActivity.getClassName());
            builder.append("\ntop:");
            builder.append(running.topActivity.getClassName());
            builder.append("\nnum:");
            builder.append(running.numActivities);
            builder.append("\nid:");
            builder.append(running.id);
            builder.append("\n---------------------------------------------------------");

            index++;
        }
        L.e("call: logActivity([])-> " + builder.toString());
        onLogActivity(builder.toString());

//        List<ActivityManager.AppTask> appTasks = mActivityManager.getAppTasks();
//        for (ActivityManager.AppTask running : appTasks) {
//            System.out.println(running.getTaskInfo().baseActivity.getClassName());
//        }
//        System.out.println("================2");
    }

    protected void onLogActivity(String log) {

    }
}
