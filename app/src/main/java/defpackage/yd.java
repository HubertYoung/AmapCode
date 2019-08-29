package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/* renamed from: yd reason: default package */
/* compiled from: SpEncryptedRecord */
public final class yd {
    private static yd b;
    public SharedPreferences a;

    private yd(Context context) {
        this.a = context.getSharedPreferences("sp_encryt_sharepreference", 0);
    }

    public static yd a(Context context) {
        if (b != null) {
            return b;
        }
        synchronized (yd.class) {
            try {
                if (b == null) {
                    b = new yd(context);
                }
            }
        }
        return b;
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str) {
        Editor edit = this.a.edit();
        edit.putBoolean(str, true);
        edit.apply();
    }
}
