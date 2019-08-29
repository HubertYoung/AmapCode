package defpackage;

import android.text.TextUtils;

/* renamed from: bcv reason: default package */
/* compiled from: ConvertUtils */
public final class bcv {
    public static int a(String str, int i) {
        int i2;
        if (TextUtils.isEmpty(str)) {
            return i;
        }
        try {
            i2 = Integer.parseInt(str);
        } catch (Exception unused) {
            i2 = i;
        }
        return i2;
    }

    public static double a(String str, double d) {
        if (TextUtils.isEmpty(str)) {
            return d;
        }
        try {
            d = Double.parseDouble(str);
        } catch (Exception unused) {
        }
        return d;
    }

    public static float a(String str) {
        float f;
        if (TextUtils.isEmpty(str)) {
            return 0.0f;
        }
        try {
            f = Float.parseFloat(str);
        } catch (Exception unused) {
            f = 0.0f;
        }
        return f;
    }

    public static boolean b(String str) {
        boolean z;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            z = Boolean.parseBoolean(str);
        } catch (Exception unused) {
            z = false;
        }
        return z;
    }

    public static long b(String str, int i) {
        long j;
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            j = Long.parseLong(str, i);
        } catch (Exception unused) {
            j = 0;
        }
        return j;
    }
}
