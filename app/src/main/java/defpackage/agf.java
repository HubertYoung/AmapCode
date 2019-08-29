package defpackage;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.util.Locale;

@Deprecated
/* renamed from: agf reason: default package */
/* compiled from: Utils */
public final class agf {
    @SuppressLint({"InlinedApi"})
    @TargetApi(19)
    public static boolean a(Context context) {
        if (VERSION.SDK_INT < 19) {
            return true;
        }
        try {
            int checkOpNoThrow = ((AppOpsManager) context.getApplicationContext().getSystemService("appops")).checkOpNoThrow("android:fine_location", Binder.getCallingUid(), context.getPackageName());
            if (checkOpNoThrow == 0 || checkOpNoThrow == 4) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return true;
        }
    }

    public static boolean a() {
        String upperCase = Build.MODEL.toUpperCase(Locale.getDefault());
        String upperCase2 = Build.BRAND.toUpperCase(Locale.getDefault());
        return !TextUtils.isEmpty(upperCase) && (upperCase.contains("MI") || upperCase.contains("HM") || (!TextUtils.isEmpty(upperCase2) && upperCase2.contains("XIAOMI"))) && !TextUtils.isEmpty(aht.a().a("ro.miui.ui.version.name"));
    }
}
