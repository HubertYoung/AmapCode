package com.alibaba.baichuan.android.trade.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.callback.AlibcFailureCallback;
import com.alibaba.baichuan.android.trade.utils.a.a;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;

public class d {
    private static final String a = "d";
    private static String b = "\\u";

    public static void a(AlibcFailureCallback alibcFailureCallback, int i, String str) {
        if (alibcFailureCallback != null) {
            AlibcContext.executorService.b(new f(alibcFailureCallback, i, str));
        }
    }

    public static void a(AlibcFailureCallback alibcFailureCallback, a aVar) {
        if (alibcFailureCallback != null) {
            AlibcContext.executorService.b(new e(alibcFailureCallback, aVar));
        }
    }

    public static boolean a(Context context) {
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

    public static boolean a(Object obj, Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    public static String b(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            StringBuilder sb = new StringBuilder();
            sb.append(packageInfo.versionName);
            sb.append(".");
            sb.append(packageInfo.versionCode);
            String sb2 = sb.toString();
            if (sb2 != null) {
                if (sb2.length() > 0) {
                    return sb2;
                }
            }
            a.a(a, "getVersion", "获取版本号失败");
            return "";
        } catch (Exception e) {
            a.a(a, "getVersion", e.getMessage());
            AlibcLogger.e(a, e.getMessage());
            e.printStackTrace();
            return "";
        }
    }
}
