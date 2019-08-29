package com.ali.auth.third.core.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.text.format.Formatter;
import com.ali.auth.third.core.broadcast.LoginAction;
import com.ali.auth.third.core.callback.FailureCallback;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.message.Message;
import com.ali.auth.third.core.model.ResultCode;
import com.ali.auth.third.core.task.InitWaitTask;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

public class CommonUtils {
    private static String a = "\\u";
    private static String b;
    public static String mAppLabel;
    public static String mAppVersion;

    public static String getAndroidAppVersion() {
        StringBuilder sb = new StringBuilder("android_");
        sb.append(getAppVersion());
        return sb.toString();
    }

    public static String getAndroidId() {
        return Secure.getString(KernelContext.getApplicationContext().getContentResolver(), "android_id");
    }

    public static String getAppLabel() {
        if (mAppLabel == null) {
            try {
                PackageManager packageManager = KernelContext.getApplicationContext().getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(KernelContext.getApplicationContext().getPackageName(), 0);
                if (packageInfo != null) {
                    mAppVersion = packageInfo.versionName;
                    StringBuilder sb = new StringBuilder();
                    sb.append(packageManager.getApplicationLabel(packageInfo.applicationInfo));
                    mAppLabel = sb.toString();
                }
            } catch (Throwable unused) {
            }
        }
        return mAppLabel;
    }

    public static String getAppVersion() {
        if (mAppVersion == null) {
            try {
                PackageManager packageManager = KernelContext.getApplicationContext().getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(KernelContext.getApplicationContext().getPackageName(), 0);
                if (packageInfo != null) {
                    mAppVersion = packageInfo.versionName;
                    StringBuilder sb = new StringBuilder();
                    sb.append(packageManager.getApplicationLabel(packageInfo.applicationInfo));
                    mAppLabel = sb.toString();
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return mAppVersion;
    }

    public static String getAvailMemory() {
        MemoryInfo memoryInfo = new MemoryInfo();
        ((ActivityManager) KernelContext.getApplicationContext().getSystemService(WidgetType.ACTIVITY)).getMemoryInfo(memoryInfo);
        return Formatter.formatFileSize(KernelContext.getApplicationContext(), memoryInfo.availMem);
    }

    public static String getCurrentProcessName() {
        if (KernelContext.context == null) {
            return null;
        }
        if (b != null) {
            return b;
        }
        try {
            List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) KernelContext.context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses();
            if (runningAppProcesses == null) {
                return null;
            }
            int myPid = Process.myPid();
            for (RunningAppProcessInfo next : runningAppProcesses) {
                if (next.pid == myPid) {
                    b = next.processName;
                    return next.processName;
                }
            }
            return null;
        } catch (Exception unused) {
        }
    }

    public static String getHashString(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b2 : bArr) {
            sb.append(Integer.toHexString((b2 >> 4) & 15));
            sb.append(Integer.toHexString(b2 & 15));
        }
        return sb.toString();
    }

    public static String getLocalIPAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (true) {
                    if (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement = inetAddresses.nextElement();
                        if (!nextElement.isLoopbackAddress() && (nextElement instanceof Inet4Address)) {
                            return nextElement.getHostAddress().toString();
                        }
                    }
                }
            }
        } catch (SocketException unused) {
        }
        return null;
    }

    public static String getSDCardSize() {
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return "0";
        }
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return Formatter.formatFileSize(KernelContext.getApplicationContext(), ((long) statFs.getBlockSize()) * ((long) statFs.getBlockCount()));
    }

    public static String getSystemSize() {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getPath());
        return Formatter.formatFileSize(KernelContext.getApplicationContext(), ((long) statFs.getBlockSize()) * ((long) statFs.getBlockCount()));
    }

    public static String getTotalMemory() {
        long j;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            String[] split = bufferedReader.readLine().split("\\s+");
            for (String append : split) {
                StringBuilder sb = new StringBuilder();
                sb.append(append);
                sb.append("\t");
            }
            j = (long) Integer.valueOf(split[1]).intValue();
            try {
                bufferedReader.close();
            } catch (IOException unused) {
            }
        } catch (IOException unused2) {
            j = 0;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(j);
        sb2.append(" KB");
        return sb2.toString();
    }

    public static int isApplicationDefaultProcess() {
        if (KernelContext.context == null) {
            return -1;
        }
        String currentProcessName = getCurrentProcessName();
        if (currentProcessName == null) {
            return -1;
        }
        return currentProcessName.equals(KernelContext.context.getPackageName()) ? 1 : 0;
    }

    public static boolean isNetworkAvailable() {
        if (KernelContext.context == null) {
            return true;
        }
        return isNetworkAvailable(KernelContext.context);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
        if (allNetworkInfo == null) {
            return false;
        }
        for (NetworkInfo networkInfo : allNetworkInfo) {
            if (networkInfo != null && (networkInfo.getState() == State.CONNECTED || networkInfo.getState() == State.CONNECTING)) {
                return true;
            }
        }
        return false;
    }

    public static void onFailure(FailureCallback failureCallback, int i, String str) {
        KernelContext.executorService.postUITask(new c(failureCallback, i, str));
    }

    public static void onFailure(FailureCallback failureCallback, Message message) {
        KernelContext.executorService.postUITask(new a(failureCallback, message));
    }

    public static void onFailure(FailureCallback failureCallback, ResultCode resultCode) {
        KernelContext.executorService.postUITask(new b(failureCallback, resultCode));
    }

    public static void sendBroadcast(LoginAction loginAction) {
        Intent intent = new Intent();
        intent.setAction(loginAction.name());
        intent.setPackage(KernelContext.getApplicationContext().getPackageName());
        KernelContext.getApplicationContext().sendBroadcast(intent);
    }

    public static void startInitWaitTask(Activity activity, FailureCallback failureCallback, Runnable runnable, String str) {
        new InitWaitTask(activity, failureCallback, runnable, str).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public static String toString(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static void toast(String str) {
        KernelContext.executorService.postUITask(new d(str));
    }

    public static void toastSystemException() {
        toast("com_taobao_tae_sdk_system_exception");
    }
}
