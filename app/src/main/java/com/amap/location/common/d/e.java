package com.amap.location.common.d;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Process;
import android.text.TextUtils;
import com.amap.location.common.b;

/* compiled from: LogFileHeaderCollector */
class e {
    private static int a = 0;
    private static String b = "";
    private static String c = "";

    public static String a(Context context) {
        if (TextUtils.isEmpty(c)) {
            b(context);
            StringBuilder sb = new StringBuilder();
            if (a != 0) {
                StringBuilder sb2 = new StringBuilder("versionCode:");
                sb2.append(a);
                sb2.append("\n");
                sb.append(sb2.toString());
            }
            if (!TextUtils.isEmpty(b)) {
                StringBuilder sb3 = new StringBuilder("versionName:");
                sb3.append(b);
                sb3.append("\n");
                sb.append(sb3.toString());
            }
            StringBuilder sb4 = new StringBuilder("pid:");
            sb4.append(Process.myPid());
            sb4.append("\n");
            sb.append(sb4.toString());
            StringBuilder sb5 = new StringBuilder("uid:");
            sb5.append(Process.myUid());
            sb5.append("\n");
            sb.append(sb5.toString());
            StringBuilder sb6 = new StringBuilder("processName:");
            sb6.append(b.e());
            sb6.append("\n");
            sb.append(sb6.toString());
            StringBuilder sb7 = new StringBuilder("packageName:");
            sb7.append(context.getPackageName());
            sb7.append("\n");
            sb.append(sb7.toString());
            sb.append("-----------------------------\n");
            c = sb.toString();
        }
        return c;
    }

    public static void b(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            b = packageInfo.versionName;
            a = packageInfo.versionCode;
        } catch (Exception unused) {
        }
    }
}
