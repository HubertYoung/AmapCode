package com.oppo.oms.sdk.Util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utils {
    public static int getVersionCode(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static int getApkVersionCode(Context context, String str) {
        try {
            return context.getPackageManager().getPackageArchiveInfo(str, 1).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean isAppInstalled(Context context, String str) {
        List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(0);
        ArrayList arrayList = new ArrayList();
        if (installedPackages != null) {
            for (int i = 0; i < installedPackages.size(); i++) {
                arrayList.add(installedPackages.get(i).packageName);
            }
        }
        return arrayList.contains(str);
    }

    static boolean hasInstallNearmePayApk(Context context) {
        return getVersionCode(context, "com.nearme.atlas") > 1;
    }

    static int getNearmePayApkVersinCode(Context context) {
        return getVersionCode(context, "com.nearme.atlas");
    }

    public static boolean isWifi(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == 1;
    }

    public static boolean isNetworkAvailable(Context context) {
        if (((ConnectivityManager) context.getSystemService("connectivity")) == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getState() == State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static int getMMApiLevel(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo("com.tencent.mm", 128);
            int i = applicationInfo.metaData.getInt("wechat_ext_api_level", 0);
            if (i > 0) {
                return i;
            }
            String string = applicationInfo.metaData.getString("wechat_fun_support");
            if (string == null || string.length() <= 0) {
                return 0;
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int yuanToFen(float f) {
        float f2 = f * 100.0f;
        try {
            f2 = Float.valueOf(new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.CHINA)).format((double) f2).toString()).floatValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) f2;
    }
}
