package defpackage;

import android.text.TextUtils;
import android.util.Log;
import com.amap.bundle.logs.AMapLog;

/* renamed from: epk reason: default package */
/* compiled from: ALLog */
public final class epk {
    public static final boolean a = bno.a;

    public static void a(Throwable th) {
        a("LMError", Log.getStackTraceString(th));
    }

    public static void a(String str, String str2, Object... objArr) {
        a(str, String.format(str2, objArr));
    }

    public static void a(String str, String str2) {
        if (a && !TextUtils.isEmpty(str2)) {
            AMapLog.d(str, str2);
        }
    }
}
