package com.taobao.accs.utl;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.text.TextUtils;
import com.ta.utdid2.device.UTDevice;
import com.taobao.accs.client.AdapterGlobalClientInfo;
import java.io.File;

public class AdapterUtilityImpl {
    public static String BACK_APP_KEY = "";
    private static final String TAG = "AdapterUtilityImpl";
    public static final String channelService = "com.taobao.accs.ChannelService";
    private static String currentProcess = "";
    public static String mAgooAppSecret = null;
    private static boolean mChecked = false;
    private static boolean mIsMainProc = true;
    private static String mainProcess = "";
    public static final String msgService = "com.taobao.accs.data.MsgDistributeService";

    public static boolean isMainProcess(Context context) {
        String str;
        if (mChecked) {
            return mIsMainProc;
        }
        try {
            if (TextUtils.isEmpty(AdapterGlobalClientInfo.mMainProcessName)) {
                if (TextUtils.isEmpty(mainProcess)) {
                    mainProcess = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.processName;
                }
                str = mainProcess;
            } else {
                str = AdapterGlobalClientInfo.mMainProcessName;
            }
            if (TextUtils.isEmpty(currentProcess)) {
                currentProcess = getProcessName(context, Process.myPid());
            }
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(currentProcess)) {
                mIsMainProc = str.equalsIgnoreCase(currentProcess);
                mChecked = true;
            }
        } catch (Throwable th) {
            ALog.e(TAG, "isMainProcess", th, new Object[0]);
        }
        return mIsMainProc;
    }

    public static String getProcessName(Context context, int i) {
        if (AdapterGlobalClientInfo.mProcessNameImpl != null) {
            return AdapterGlobalClientInfo.mProcessNameImpl.getCurrProcessName();
        }
        String str = "";
        for (RunningAppProcessInfo next : AdapterGlobalClientInfo.getInstance(context).getActivityManager().getRunningAppProcesses()) {
            try {
                if (next.pid == i) {
                    str = next.processName;
                }
            } catch (Exception unused) {
            }
        }
        return str;
    }

    public static long getUsableSpace() {
        try {
            File dataDirectory = Environment.getDataDirectory();
            if (dataDirectory == null) {
                return -1;
            }
            if (VERSION.SDK_INT >= 9) {
                return dataDirectory.getUsableSpace();
            }
            if (!dataDirectory.exists()) {
                return -1;
            }
            StatFs statFs = new StatFs(dataDirectory.getPath());
            return ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
        } catch (Throwable th) {
            ALog.e(TAG, "getUsableSpace", th, new Object[0]);
            return -1;
        }
    }

    public static String getStackMsg(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                    stringBuffer.append(sb.toString());
                }
            }
        } catch (Exception unused) {
        }
        return stringBuffer.toString();
    }

    public static String getDeviceId(Context context) {
        return UTDevice.getUtdid(context);
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            try {
                NetworkInfo activeNetworkInfo = AdapterGlobalClientInfo.getInstance(context).getConnectivityManager().getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    return activeNetworkInfo.isConnected();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static final boolean checkIsWritable(String str, int i) {
        if (str == null) {
            return false;
        }
        StatFs statFs = new StatFs(str);
        int blockSize = statFs.getBlockSize();
        long availableBlocks = (long) statFs.getAvailableBlocks();
        StringBuilder sb = new StringBuilder("st.getAvailableBlocks()=");
        sb.append(statFs.getAvailableBlocks());
        sb.append(",st.getAvailableBlocks() * blockSize=");
        long j = (long) blockSize;
        sb.append(((long) statFs.getAvailableBlocks()) * j);
        if (statFs.getAvailableBlocks() <= 10 || availableBlocks * j <= ((long) i)) {
            return false;
        }
        return true;
    }

    public static String isNotificationEnabled(Context context) {
        try {
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            String packageName = context.getApplicationContext().getPackageName();
            int i = applicationInfo.uid;
            Class<?> cls = Class.forName(AppOpsManager.class.getName());
            boolean z = true;
            if (((Integer) cls.getMethod("checkOpNoThrow", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(appOpsManager, new Object[]{Integer.valueOf(((Integer) cls.getDeclaredField("OP_POST_NOTIFICATION").get(appOpsManager)).intValue()), Integer.valueOf(i), packageName})).intValue() != 0) {
                z = false;
            }
            return String.valueOf(z);
        } catch (Throwable th) {
            ALog.e(TAG, "isNotificationEnabled", th, new Object[0]);
            r11 = "unknown";
            return "unknown";
        }
    }
}
