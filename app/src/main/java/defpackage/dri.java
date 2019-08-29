package defpackage;

import android.text.TextUtils;

/* renamed from: dri reason: default package */
/* compiled from: ConvertUtils */
public final class dri {
    public static int a(String str) {
        int i;
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            i = Integer.parseInt(str);
        } catch (Exception unused) {
            i = 0;
        }
        return i;
    }

    public static long b(String str) {
        long j = 0;
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            j = Long.parseLong(str, 16);
        } catch (Exception unused) {
        }
        return j;
    }
}
