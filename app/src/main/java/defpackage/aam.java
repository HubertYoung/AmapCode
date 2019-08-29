package defpackage;

import java.util.Collections;
import java.util.Set;

/* renamed from: aam reason: default package */
/* compiled from: OptConfigManager */
public final class aam implements aak {
    private h a;

    public final synchronized boolean a(String str) {
        try {
            h d = d();
            if (d == null) {
                return false;
            }
            return d.a(str);
        }
    }

    public final synchronized boolean a() {
        h d = d();
        if (d == null) {
            return false;
        }
        return d.a();
    }

    public final synchronized String b() {
        h d = d();
        if (d == null) {
            return "";
        }
        return d.b();
    }

    public final synchronized Set<String> c() {
        h d = d();
        if (d == null) {
            return Collections.EMPTY_SET;
        }
        return d.c();
    }

    private h d() {
        if (this.a == null) {
            this.a = aaf.h();
        }
        return this.a;
    }
}
