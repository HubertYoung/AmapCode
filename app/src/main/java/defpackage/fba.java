package defpackage;

import android.content.Context;

/* renamed from: fba reason: default package */
/* compiled from: SharePreferenceManager */
public final class fba extends fag {
    private static fba b;

    public static synchronized fba b() {
        fba fba;
        synchronized (fba.class) {
            try {
                if (b == null) {
                    b = new fba();
                }
                fba = b;
            }
        }
        return fba;
    }

    public final void a(Context context) {
        if (this.a == null) {
            this.a = context;
            a(context, (String) "com.vivo.push_preferences");
        }
    }
}
