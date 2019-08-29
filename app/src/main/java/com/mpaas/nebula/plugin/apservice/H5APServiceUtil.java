package com.mpaas.nebula.plugin.apservice;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.support.annotation.NonNull;
import java.util.List;

public class H5APServiceUtil {
    public static boolean isAppInstalled(@NonNull Context context, @NonNull String packageName) {
        List<PackageInfo> packageInfoList = context.getPackageManager().getInstalledPackages(0);
        if (packageInfoList == null) {
            return false;
        }
        for (PackageInfo packageInfo : packageInfoList) {
            if (packageName.equals(packageInfo.packageName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAlipayInstalled(@NonNull Context context) {
        return isAppInstalled(context, "com.eg.android.AlipayGphone");
    }
}
