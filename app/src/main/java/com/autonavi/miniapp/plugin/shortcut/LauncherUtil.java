package com.autonavi.miniapp.plugin.shortcut;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.List;

public class LauncherUtil {
    private static final String TAG = "LauncherUtil";
    private static String mBufferedValue;

    public static String getCurrentLauncherPackageName(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 0);
        if (resolveActivity == null || resolveActivity.activityInfo == null) {
            return "";
        }
        if (resolveActivity.activityInfo.packageName.equals("android")) {
            return "";
        }
        return resolveActivity.activityInfo.packageName;
    }

    public static String getAuthorityFromPermissionDefault(Context context) {
        if (TextUtils.isEmpty(mBufferedValue)) {
            mBufferedValue = getAuthorityFromPermission(context, "com.android.launcher.permission.READ_SETTINGS");
        }
        return mBufferedValue;
    }

    public static String getAuthorityFromPermission(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(8);
            if (installedPackages == null) {
                return "";
            }
            for (PackageInfo packageInfo : installedPackages) {
                ProviderInfo[] providerInfoArr = packageInfo.providers;
                if (providerInfoArr != null) {
                    int length = providerInfoArr.length;
                    int i = 0;
                    while (i < length) {
                        ProviderInfo providerInfo = providerInfoArr[i];
                        if (!str.equals(providerInfo.readPermission)) {
                            if (!str.equals(providerInfo.writePermission)) {
                                String str2 = providerInfo.authority;
                                if (!TextUtils.isEmpty(str2) && (str2.contains(".launcher.settings") || str2.contains(".twlauncher.settings") || str2.contains(".launcher2.settings"))) {
                                    return str2;
                                }
                                i++;
                            }
                        }
                        return providerInfo.authority;
                    }
                    continue;
                }
            }
            return "";
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (Throwable) e);
        }
    }
}
