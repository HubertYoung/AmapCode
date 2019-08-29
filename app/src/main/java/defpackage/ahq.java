package defpackage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import java.util.List;

/* renamed from: ahq reason: default package */
/* compiled from: LauncherUtil */
public final class ahq {
    private static String a;

    public static String a(Context context) {
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

    public static String b(Context context) {
        if (TextUtils.isEmpty(a)) {
            a = a(context, new String[]{"com.android.launcher.permission.READ_SETTINGS", "com.google.android.launcher.permission.READ_SETTINGS"});
        }
        return a;
    }

    public static String a(Context context, String[] strArr) {
        if (strArr.length == 0) {
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
                    for (ProviderInfo providerInfo : providerInfoArr) {
                        int length = strArr.length;
                        int i = 0;
                        while (i < length) {
                            String str = strArr[i];
                            if (!str.equals(providerInfo.readPermission)) {
                                if (!str.equals(providerInfo.writePermission)) {
                                    i++;
                                }
                            }
                            return providerInfo.authority;
                        }
                    }
                    continue;
                }
            }
            return "";
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
