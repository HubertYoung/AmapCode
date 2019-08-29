package defpackage;

import android.support.annotation.Nullable;

/* renamed from: apa reason: default package */
/* compiled from: AccountScope */
public final class apa {
    @Nullable
    public static String a(String str) {
        if (str == null) {
            return null;
        }
        if (str.startsWith("aplogin")) {
            return "aplogin";
        }
        if (str.startsWith("kuaijie")) {
            return "kuaijie";
        }
        if (str.startsWith("authtra")) {
            return "auth_transport";
        }
        return null;
    }
}
