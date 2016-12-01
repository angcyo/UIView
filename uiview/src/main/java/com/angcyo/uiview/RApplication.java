package com.angcyo.uiview;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.hawk.Hawk;

import java.util.Iterator;
import java.util.List;

/**
 * Created by robi on 2016-06-21 11:24.
 * Copyright (c) 2016, angcyo@126.com All Rights Reserved.
 * *                                                   #
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #                                                   #
 */

public class RApplication extends Application {

    private static RApplication app;

    /**
     * 确保只初始化一次
     */
    public static boolean isInitOnce(Application application) {

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(application, pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(application.getPackageName())) {

            // 则此application::onCreate 是被service 调用的，直接返回
            return false;
        }

        return true;
    }

    private static String getAppName(Application application, int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = application.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    public static RApplication getApp() {
        if (app == null) {
            throw new IllegalArgumentException("application 初始化了吗?");
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (isInitOnce(this)) {

            /*崩溃异常处理*/
            RCrashHandler.init(this);

             /*sp持久化库*/
            Hawk.init(this)
                    .build();

            /*Realm数据库初始化*/
            //RRealm.init(this, "r_jcenter.realm", true);

            /*Facebook图片加载库, 必须*/
            Fresco.initialize(this);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        app = this;
        /*65535限制*/
        //MultiDex.install(this);
    }
}
