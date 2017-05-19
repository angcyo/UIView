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
        intent.addFlags(Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS);
//        intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.e("call: onCreate([savedInstanceState])-> " + getClass().getSimpleName() + " :" + getTaskId());

        logActivity();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        L.e("call: onNewIntent([intent])-> " + getClass().getSimpleName() + " :" + getTaskId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.e("call: onDestroy([])-> " + getClass().getSimpleName() + " :" + getTaskId());
    }

    private void logActivity() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//获得当前正在运行的activity
        List<ActivityManager.RunningTaskInfo> appList3 = mActivityManager.getRunningTasks(1000);
        for (ActivityManager.RunningTaskInfo running : appList3) {
            if (!running.baseActivity.getClassName().contains("angcyo")) {
                continue;
            }
            L.e("call: logActivity([])-> \nbase:" + running.baseActivity.getClassName() +
                    "\ntop:" + running.topActivity.getClassName() +
                    "\nnum:" + running.numActivities +
                    "\nid:" + running.id + "\n----------------------------------------------------"
            );
        }

//        List<ActivityManager.AppTask> appTasks = mActivityManager.getAppTasks();
//        for (ActivityManager.AppTask running : appTasks) {
//            System.out.println(running.getTaskInfo().baseActivity.getClassName());
//        }
//        System.out.println("================2");
    }
}
