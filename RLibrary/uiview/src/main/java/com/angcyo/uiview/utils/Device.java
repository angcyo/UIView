package com.angcyo.uiview.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;

/**
 * Created by angcyo on 2017-04-17.
 */

public class Device {

    public static String appVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "unknown";
        }
        return pi.versionName;
    }

    public static String appVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "unknown";
        }
        return pi.versionCode + "";
    }

    public static String osVersion() {
        //OS Version: 6.0.1 23
        return Build.VERSION.RELEASE + " " + Build.VERSION.SDK_INT;
    }

    public static String modelVendor() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    public static String deviceHardware() {
        return Build.DEVICE + " " + Build.HARDWARE;
    }

    public static String cpu() {
        return Build.CPU_ABI + " " + Build.CPU_ABI2;
    }

    public static String memorySize(Context context) {
        final ActivityManager.MemoryInfo memoryInfo = getMemoryInfo(context);

        return Formatter.formatFileSize(context, memoryInfo.totalMem) +
                " " + Formatter.formatFileSize(context, memoryInfo.availMem);
    }

    public static String sdSize(Context context) {
        return Formatter.formatFileSize(context, getTotalExternalMemorySize()) + " " +
                Formatter.formatFileSize(context, getAvailableExternalMemorySize());
    }

    private static ActivityManager.MemoryInfo getMemoryInfo(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    /**
     * 获取SDCARD剩余存储空间
     *
     * @return
     */
    private static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return -1;
        }
    }

    /**
     * 获取SDCARD总的存储空间
     *
     * @return
     */
    private static long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return -1;
        }
    }

    /**
     * SDCARD是否存
     */
    private static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }
}
