package com.xiaomi.channel.commonutils.android;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.xiaomi.channel.commonutils.logger.b;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class a {

    /* renamed from: com.xiaomi.channel.commonutils.android.a$a reason: collision with other inner class name */
    public enum C0073a {
        UNKNOWN(0),
        ALLOWED(1),
        NOT_ALLOWED(2);
        
        private final int d;

        private C0073a(int i) {
            this.d = i;
        }

        public final int a() {
            return this.d;
        }
    }

    public static String a(Context context) {
        String str;
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses();
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if (runningAppProcesses != null && runningAppProcesses.size() > 0) {
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                String[] strArr = runningAppProcessInfo.pkgList;
                int i = 0;
                while (strArr != null && i < strArr.length) {
                    if (!arrayList.contains(strArr[i])) {
                        arrayList.add(strArr[i]);
                        if (arrayList.size() == 1) {
                            str = (String) arrayList.get(0);
                        } else {
                            sb.append(MetaRecord.LOG_SEPARATOR);
                            str = strArr[i];
                        }
                        sb.append(str.hashCode() % 100000);
                    }
                    i++;
                }
            }
        }
        return sb.toString();
    }

    public static String a(Context context, String str) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 16384);
        } catch (Exception e) {
            b.a((Throwable) e);
            packageInfo = null;
        }
        return packageInfo != null ? packageInfo.versionName : "1.0";
    }

    public static String a(String[] strArr) {
        boolean z;
        b[] values = b.values();
        byte[] bArr = new byte[((int) Math.ceil(((double) values.length) / 8.0d))];
        if (strArr != null) {
            int i = -1;
            for (String str : strArr) {
                if (!TextUtils.isEmpty(str) && str.startsWith("android.permission.")) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= values.length) {
                            i2 = i;
                            z = false;
                            break;
                        }
                        StringBuilder sb = new StringBuilder("android.permission.");
                        sb.append(values[i2].name());
                        if (TextUtils.equals(sb.toString(), str)) {
                            z = true;
                            break;
                        }
                        i2++;
                    }
                    if (z && i2 != -1) {
                        int i3 = i2 / 8;
                        bArr[i3] = (byte) (bArr[i3] | (1 << (7 - (i2 % 8))));
                    }
                    i = i2;
                }
            }
            return new String(Base64.encode(bArr, 0));
        }
        b.c("AppInfoUtils.: no permissions");
        return "";
    }

    public static int b(Context context, String str) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 16384);
        } catch (Exception e) {
            b.a((Throwable) e);
            packageInfo = null;
        }
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return 0;
    }

    @TargetApi(19)
    public static C0073a c(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str) || VERSION.SDK_INT < 19) {
            return C0073a.UNKNOWN;
        }
        try {
            Integer num = (Integer) com.xiaomi.channel.commonutils.reflect.a.a(AppOpsManager.class, (String) "OP_POST_NOTIFICATION");
            if (num == null) {
                return C0073a.UNKNOWN;
            }
            Integer num2 = (Integer) com.xiaomi.channel.commonutils.reflect.a.a((Object) (AppOpsManager) context.getSystemService("appops"), (String) "checkOpNoThrow", num, Integer.valueOf(context.getPackageManager().getApplicationInfo(str, 0).uid), str);
            return (num2 == null || num2.intValue() != 0) ? C0073a.NOT_ALLOWED : C0073a.ALLOWED;
        } catch (Throwable unused) {
            return C0073a.UNKNOWN;
        }
    }

    public static boolean d(Context context, String str) {
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (Arrays.asList(runningAppProcessInfo.pkgList).contains(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean e(Context context, String str) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 0);
        } catch (NameNotFoundException unused) {
            packageInfo = null;
        }
        return packageInfo != null;
    }

    public static String f(Context context, String str) {
        try {
            return a(context.getPackageManager().getPackageInfo(str, 4096).requestedPermissions);
        } catch (NameNotFoundException e) {
            b.d(e.toString());
            return "";
        }
    }

    public static boolean g(Context context, String str) {
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }
}
