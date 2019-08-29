package defpackage;

import android.text.TextUtils;

/* renamed from: elw reason: default package */
/* compiled from: ConvertUtils */
public final class elw {
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
}
