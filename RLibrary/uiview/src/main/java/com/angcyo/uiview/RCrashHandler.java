package com.angcyo.uiview;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 异常崩溃处理
 * Created by angcyo on 16-02-21-021.
 */
public class RCrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String INTENT_ACTION_RESTART_ACTIVITY = "com.angcyo.crash";
    private static final String DEFAULT_LOG_DIR = "crash";
    // log文件的后缀名
    private static final String FILE_NAME_SUFFIX = ".log";
    private static DecimalFormat fileIntegerFormat = new DecimalFormat("#0");
    private static DecimalFormat fileDecimalFormat = new DecimalFormat("#0.#");
    private final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;
    private Context context;

    private RCrashHandler(Context context) {
        this.context = context.getApplicationContext();
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static RCrashHandler init(Context context) {
        return new RCrashHandler(context);
    }

    public static String getAllErrorDetailsFromIntent(Context context, Intent intent) {
        //I don't think that this needs localization because it's a development string...

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        //Get build date
        String buildDateAsString = getBuildDateAsString(context, dateFormat);

        //Get app version
        String versionName = getVersionName(context);

        String errorDetails = "";

        errorDetails += "Build version: " + versionName + " \n";
        errorDetails += "Build date: " + buildDateAsString + " \n";
        errorDetails += "Current date: " + dateFormat.format(currentDate) + " \n";
        errorDetails += "Device: " + getDeviceModelName() + " \n\n";
        errorDetails += "Stack trace:  \n";
        errorDetails += "EXTRA_STACK_TRACE";
        return errorDetails;
    }

    @SuppressWarnings("unchecked")
    public static Class<? extends Activity> getRestartActivityClassFromIntent(Intent intent) {
        Serializable serializedClass = intent.getSerializableExtra("EXTRA_RESTART_ACTIVITY_CLASS");

        if (serializedClass != null && serializedClass instanceof Class) {
            return (Class<? extends Activity>) serializedClass;
        } else {
            return null;
        }
    }

    /**
     * Given an Intent, restarts the app and launches a startActivity to that intent.
     * The flags NEW_TASK and CLEAR_TASK are set if the Intent does not have them, to ensure
     * the app stack is fully cleared.
     * Must only be used from your error activity.
     *
     * @param activity The current error activity. Must not be null.
     * @param intent   The Intent. Must not be null.
     */
    public static void restartApplicationWithIntent(Activity activity, Intent intent) {
        intent.addFlags(getStartIntentFlags());
        activity.finish();
        activity.startActivity(intent);
        killCurrentProcess();
    }

    /**
     * Closes the app. Must only be used from your error activity.
     *
     * @param activity The current error activity. Must not be null.
     */
    public static void closeApplication(Activity activity) {
        activity.finish();
        killCurrentProcess();
    }
    /// INTERNAL METHODS NOT TO BE USED BY THIRD PARTIES

    /**
     * INTERNAL method that checks if the stack trace that just crashed is conflictive. This is true in the following scenarios:
     * - The application has crashed while initializing (handleBindApplication is in the stack)
     * - The error activity has crashed (activityClass is in the stack)
     *
     * @param throwable     The throwable from which the stack trace will be checked
     * @param activityClass The activity class to launch when the app crashes
     * @return true if this stack trace is conflictive and the activity must not be launched, false otherwise
     */
    private static boolean isStackTraceLikelyConflictive(Throwable throwable, Class<? extends Activity> activityClass) {
        do {
            StackTraceElement[] stackTrace = throwable.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                if ((element.getClassName().equals("android.app.ActivityThread") && element.getMethodName().equals("handleBindApplication")) || element.getClassName().equals(activityClass.getName())) {
                    return true;
                }
            }
        } while ((throwable = throwable.getCause()) != null);
        return false;
    }

    /**
     * INTERNAL method that returns the build date of the current APK as a string, or null if unable to determine it.
     *
     * @param context    A valid context. Must not be null.
     * @param dateFormat DateFormat to use to convert from Date to String
     * @return The formatted date, or "Unknown" if unable to determine it.
     */
    private static String getBuildDateAsString(Context context, DateFormat dateFormat) {
        String buildDate;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            ZipFile zf = new ZipFile(ai.sourceDir);
            ZipEntry ze = zf.getEntry("classes.dex");
            long time = ze.getTime();
            buildDate = dateFormat.format(new Date(time));
            zf.close();
        } catch (Exception e) {
            buildDate = "Unknown";
        }
        return buildDate;
    }

    /**
     * INTERNAL method that returns the version name of the current app, or null if unable to determine it.
     *
     * @param context A valid context. Must not be null.
     * @return The version name, or "Unknown if unable to determine it.
     */
    private static String getVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            return "Unknown";
        }
    }

    /**
     * INTERNAL method that returns the device model name with correct capitalization.
     * Taken from: http://stackoverflow.com/a/12707479/1254846
     *
     * @return The device model name (i.e., "LGE Nexus 5")
     */
    private static String getDeviceModelName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    /**
     * INTERNAL method that capitalizes the first character of a string
     *
     * @param s The string to capitalize
     * @return The capitalized string
     */
    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    /**
     * INTERNAL method used to get the first activity with an intent-filter <action android:name="cat.ereza.customactivityoncrash.RESTART" />,
     * If there is no activity with that intent filter, this returns null.
     *
     * @param context A valid context. Must not be null.
     * @return A valid activity class, or null if no suitable one is found
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends Activity> getRestartActivityClassWithIntentFilter(Context context) {
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(
                new Intent().setAction(INTENT_ACTION_RESTART_ACTIVITY),
                PackageManager.GET_RESOLVED_FILTER);

        for (ResolveInfo info : resolveInfos) {
            if (info.activityInfo.packageName.equalsIgnoreCase(context.getPackageName())) {
                try {
                    return (Class<? extends Activity>) Class.forName(info.activityInfo.name);
                } catch (ClassNotFoundException e) {
                    //Should not happen, print it to the log!
                    Log.e("TAG", "Failed when resolving the restart activity class via intent filter, stack trace follows!", e);
                }
            }
        }

        return null;
    }

    /**
     * INTERNAL method used to get the default launcher activity for the app.
     * If there is no launchable activity, this returns null.
     *
     * @param context A valid context. Must not be null.
     * @return A valid activity class, or null if no suitable one is found
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends Activity> getLauncherActivity(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) {
            try {
                return (Class<? extends Activity>) Class.forName(intent.getComponent().getClassName());
            } catch (ClassNotFoundException e) {
                //Should not happen, print it to the log!
                Log.e("TAG", "Failed when resolving the restart activity class via getLaunchIntentForPackage, stack trace follows!", e);
            }
        }

        return null;
    }

    /**
     * INTERNAL method used to get the first activity with an intent-filter <action android:name="cat.ereza.customactivityoncrash.ERROR" />,
     * If there is no activity with that intent filter, this returns null.
     *
     * @param context A valid context. Must not be null.
     * @return A valid activity class, or null if no suitable one is found
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends Activity> getErrorActivityClassWithIntentFilter(Context context) {
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(
                new Intent().setAction("INTENT_ACTION_ERROR_ACTIVITY"),
                PackageManager.GET_RESOLVED_FILTER);

        if (resolveInfos != null && resolveInfos.size() > 0) {
            ResolveInfo resolveInfo = resolveInfos.get(0);
            try {
                return (Class<? extends Activity>) Class.forName(resolveInfo.activityInfo.name);
            } catch (ClassNotFoundException e) {
                //Should not happen, print it to the log!
                Log.e("TAG", "Failed when resolving the error activity class via intent filter, stack trace follows!", e);
            }
        }

        return null;
    }

    /**
     * INTERNAL method that kills the current process.
     * It is used after restarting or killing the app.
     */
    private static void killCurrentProcess() {
        Process.killProcess(Process.myPid());
        System.exit(10);
    }

    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    public static String getSaveFolder(String folderName, String fileName) {
        String filePath = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + folderName
                + File.separator + fileName;
        return filePath;
    }

    private static int getStartIntentFlags() {
        return Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK;
    }

    //-----------------------------------------------start------------------------------------------//


    /**
     * 获取手机内部剩余存储空间
     *
     * @return
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取手机内部总的存储空间
     *
     * @return
     */
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }


    /**
     * SDCARD是否存
     */
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SDCARD剩余存储空间
     *
     * @return
     */
    public static long getAvailableExternalMemorySize() {
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
    public static long getTotalExternalMemorySize() {
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
     * 获取系统总内存
     *
     * @param context 可传入应用程序上下文。
     * @return 总内存大单位为B。
     */
    public static long getTotalMemorySize(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            return Integer.parseInt(subMemoryLine.replaceAll("\\D+", "")) * 1024l;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前可用内存，返回数据以字节为单位。
     *
     * @param context 可传入应用程序上下文。
     * @return 当前可用内存单位为B。
     */
    public static long getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo memoryInfo = getMemoryInfo(context);
        return memoryInfo.availMem;
    }

    public static ActivityManager.MemoryInfo getMemoryInfo(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    /**
     * 单位换算
     *
     * @param size      单位为B
     * @param isInteger 是否返回取整的单位
     * @return 转换后的单位
     */
    public static String formatFileSize(long size, boolean isInteger) {
        DecimalFormat df = isInteger ? fileIntegerFormat : fileDecimalFormat;
        String fileSizeString = "0M";
        if (size < 1024 && size > 0) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1024 * 1024) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1024 * 1024 * 1024) {
            fileSizeString = df.format((double) size / (1024 * 1024)) + "M";
        } else {
            fileSizeString = df.format((double) size / (1024 * 1024 * 1024)) + "G";
        }
        return fileSizeString;
    }

    //-----------------------------------------------end------------------------------------------//


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
//        boolean isShow = false;
        try {
            Class<? extends Activity> restartClass = getRestartActivityClassWithIntentFilter(context);
            if (restartClass != null) {
                Intent intent = new Intent(context, restartClass);
                intent.addFlags(getStartIntentFlags());
                Bundle args = new Bundle();
                args.putString("msg", getMsgFromThrowable(ex));
                intent.putExtras(args);
                context.startActivity(intent);
//                isShow = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ex.printStackTrace();

        try {
            saveToSDCard(ex);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (!isShow) {
            /*注意下面的代码, 不能少哦*/
//            if (defaultUncaughtExceptionHandler != null) {
//                defaultUncaughtExceptionHandler.uncaughtException(thread, ex);
//            } else {
//                Process.killProcess(Process.myPid());
//            }
//        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
        Process.killProcess(Process.myPid());
    }

    private String getMsgFromThrowable(Throwable ex) {
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    private void saveToSDCard(Throwable ex) throws Exception {
        String saveFolder = Environment.getExternalStorageDirectory().getAbsoluteFile() +
                File.separator + "DValley" + File.separator + DEFAULT_LOG_DIR;
        File folder = new File(saveFolder);
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                return;
            }
        }
        String dataTime = getDataTime("yyyy-MM-dd-HH-mm-ss");
        File file = new File(saveFolder, dataTime + FILE_NAME_SUFFIX);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        // 导出发生异常的时间
        pw.println(dataTime);
        // 导出手机信息
        dumpPhoneInfo(pw);

        pw.println();
        // 导出异常的调用栈信息
        ex.printStackTrace(pw);
        pw.close();
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        // 应用的版本名称和版本号
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App VersionName: ");
        pw.print(pi.versionName);
        pw.print(" VersionCode: ");
        pw.println(pi.versionCode);
//        pw.println();

        // android版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print(" ");
        pw.println(Build.VERSION.SDK_INT);
        pw.println();

        // 手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
//        pw.println();

        // 手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
        pw.print("Device: ");
        pw.println(Build.DEVICE);
        pw.print("Hardware: ");
        pw.println(Build.HARDWARE);
        pw.println();

        // cpu架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
//        pw.println();

        pw.print("CPU ABI 2: ");
        pw.println(Build.CPU_ABI2);
        pw.println();

        pw.print("手机内存大小:");
        pw.println(Formatter.formatFileSize(context, getTotalMemorySize(context)));
//        pw.println();

        pw.print("JVM可用内存大小:");
        pw.println(Formatter.formatFileSize(context, Runtime.getRuntime().maxMemory()));
//        pw.println();

        final ActivityManager.MemoryInfo memoryInfo = getMemoryInfo(context);
        pw.print("系统总内存:");
        pw.println(Formatter.formatFileSize(context, memoryInfo.totalMem));
        pw.print("系统剩余内存:");
        pw.println(Formatter.formatFileSize(context, memoryInfo.availMem));
        pw.print("是否内存警告:");
        pw.println(memoryInfo.lowMemory);
        pw.print("阈值:");
        pw.println(Formatter.formatFileSize(context, memoryInfo.threshold));
        pw.println();

        pw.print("getNativeHeapSize:");
        pw.println(Formatter.formatFileSize(context, Debug.getNativeHeapSize()));

        pw.print("getNativeHeapAllocatedSize:");
        pw.println(Formatter.formatFileSize(context, Debug.getNativeHeapAllocatedSize()));

        pw.print("getNativeHeapFreeSize:");
        pw.println(Formatter.formatFileSize(context, Debug.getNativeHeapFreeSize()));
        pw.println();

        pw.print("SD空间大小:");
        pw.println(Formatter.formatFileSize(context, getTotalExternalMemorySize()));

        pw.print("SD可用空间大小:");
        pw.println(Formatter.formatFileSize(context, getAvailableExternalMemorySize()));
//        pw.println();

    }
}
