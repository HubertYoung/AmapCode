package defpackage;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

/* renamed from: esy reason: default package */
public final class esy {
    public static int a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                StringBuilder sb = new StringBuilder("parseInt--NumberFormatException");
                sb.append(e.getMessage());
                esx.b(sb.toString());
            }
        }
        return -1;
    }

    public static boolean a(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (NameNotFoundException e) {
            StringBuilder sb = new StringBuilder("isExistPackage NameNotFoundException:");
            sb.append(e.getMessage());
            esx.b(sb.toString());
            return false;
        }
    }

    public static boolean a(Context context, String str, String str2) {
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
        } catch (NameNotFoundException e) {
            StringBuilder sb = new StringBuilder("isSupportPush NameNotFoundException:");
            sb.append(e.getMessage());
            esx.b(sb.toString());
            applicationInfo = null;
        }
        if (applicationInfo != null) {
            return applicationInfo.metaData.getBoolean(str2, false);
        }
        return false;
    }

    public static int b(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionCode;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("getVersionCode--Exception:");
            sb.append(e.getMessage());
            esx.a(sb.toString());
            return 0;
        }
    }
}
