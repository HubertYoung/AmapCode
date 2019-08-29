package defpackage;

import android.content.Context;
import android.text.TextUtils;

/* renamed from: bc reason: default package */
/* compiled from: NoSecurityGuardImpl */
final class bc implements ba {
    private String a = null;

    public final boolean a() {
        return true;
    }

    public final boolean a(Context context, String str, byte[] bArr) {
        return false;
    }

    public final byte[] a(Context context, String str) {
        return null;
    }

    public final byte[] a(Context context, String str, String str2, byte[] bArr) {
        return null;
    }

    bc(String str) {
        this.a = str;
    }

    public final String a(Context context, String str, String str2, String str3) {
        if (!TextUtils.isEmpty(this.a) && "HMAC_SHA1".equalsIgnoreCase(str)) {
            return cp.a(this.a.getBytes(), str3.getBytes());
        }
        return null;
    }
}
