package defpackage;

import android.util.Base64;

/* renamed from: eno reason: default package */
/* compiled from: Base64Util */
public final class eno {
    public static byte[] a(String str) {
        try {
            return Base64.decode(str.getBytes("UTF-8"), 0);
        } catch (Exception unused) {
            return null;
        }
    }
}
