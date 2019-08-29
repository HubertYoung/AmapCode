package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;

/* renamed from: fcy reason: default package */
/* compiled from: ConfigStoreManager */
public class fcy {
    private static volatile fcy a;

    private fcy() {
    }

    public static fcy a() {
        if (a == null) {
            synchronized (fcy.class) {
                if (a == null) {
                    a = new fcy();
                }
            }
        }
        return a;
    }

    public static boolean a(Context context, String str, String str2, String str3, String str4) {
        boolean z = false;
        if (context == null || fdd.b(str) || fdd.b(str3)) {
            return false;
        }
        try {
            Editor edit = context.getSharedPreferences(str, 0).edit();
            if (fdd.a(str2)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(str3);
                edit.putString(sb.toString(), str4);
            } else {
                edit.putString(str3, str4);
            }
            if (VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
            z = true;
        } catch (Exception unused) {
            if (TBSdkLog.a(LogEnable.WarnEnable)) {
                StringBuilder sb2 = new StringBuilder("[saveConfigItem] saveConfigItem error,store=");
                sb2.append(str);
                sb2.append(",keyprefix=");
                sb2.append(str2);
                sb2.append(",key=");
                sb2.append(str3);
                TBSdkLog.c("mtopsdk.ConfigStoreManager", sb2.toString());
            }
        }
        return z;
    }

    public static String a(Context context, String str, String str2, String str3) {
        String str4;
        if (context == null || fdd.b(str) || fdd.b(str3)) {
            return null;
        }
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
            if (fdd.a(str2)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(str3);
                str4 = sharedPreferences.getString(sb.toString(), null);
            } else {
                str4 = sharedPreferences.getString(str3, null);
            }
        } catch (Exception unused) {
            if (TBSdkLog.a(LogEnable.WarnEnable)) {
                StringBuilder sb2 = new StringBuilder("[getConfigItem] getConfigItem error,store=");
                sb2.append(str);
                sb2.append(",keyprefix=");
                sb2.append(str2);
                sb2.append(",key=");
                sb2.append(str3);
                TBSdkLog.c("mtopsdk.ConfigStoreManager", sb2.toString());
            }
            str4 = null;
        }
        return str4;
    }
}
