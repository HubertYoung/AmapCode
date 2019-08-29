package defpackage;

import android.content.Context;
import java.util.HashMap;

/* renamed from: fbc reason: default package */
/* compiled from: SystemCache */
public final class fbc implements fai {
    private static final HashMap<String, Integer> b = new HashMap<>();
    private static final HashMap<String, Long> c = new HashMap<>();
    private static final HashMap<String, String> d = new HashMap<>();
    private static fbc e;
    public Context a;
    private fai f;
    private boolean g = false;

    private fbc(Context context) {
        this.a = context;
        this.g = a(context);
        StringBuilder sb = new StringBuilder("init status is ");
        sb.append(this.g);
        sb.append(";  curCache is ");
        sb.append(this.f);
        fat.d("SystemCache", sb.toString());
    }

    public static synchronized fbc b(Context context) {
        fbc fbc;
        synchronized (fbc.class) {
            try {
                if (e == null) {
                    e = new fbc(context.getApplicationContext());
                }
                fbc = e;
            }
        }
        return fbc;
    }

    public final boolean a(Context context) {
        this.f = new faz();
        boolean a2 = this.f.a(context);
        if (!a2) {
            this.f = new fay();
            a2 = this.f.a(context);
        }
        if (!a2) {
            this.f = new fbb();
            a2 = this.f.a(context);
        }
        if (!a2) {
            this.f = null;
        }
        return a2;
    }

    public final String a(String str, String str2) {
        String str3 = d.get(str);
        return (str3 != null || this.f == null) ? str3 : this.f.a(str, str2);
    }

    public final void b(String str, String str2) {
        d.put(str, str2);
        if (this.g && this.f != null) {
            this.f.b(str, str2);
        }
    }
}
