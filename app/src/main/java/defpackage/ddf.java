package defpackage;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

/* renamed from: ddf reason: default package */
/* compiled from: AppInstallCheck */
public final class ddf {
    public static boolean a(Context context, String str) {
        try {
            context.getPackageManager().getApplicationInfo(str, 0);
            return true;
        } catch (NameNotFoundException | RuntimeException unused) {
            return false;
        }
    }
}
