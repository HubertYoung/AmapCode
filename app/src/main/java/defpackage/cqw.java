package defpackage;

import java.util.List;

/* renamed from: cqw reason: default package */
/* compiled from: CarPageManager */
public class cqw {
    private static volatile cqw c;
    public List<cqs> a = null;
    public boolean b = true;

    private cqw() {
    }

    public static cqw a() {
        if (c == null) {
            synchronized (cqw.class) {
                try {
                    if (c == null) {
                        c = new cqw();
                    }
                }
            }
        }
        return c;
    }

    public final void a(String str, cqr<Boolean> cqr) {
        cqs cqs = new cqs();
        cqs.a = str;
        cqs.b = cqr;
        this.a.add(cqs);
    }

    public final boolean a(String str, cqr<Boolean> cqr, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            cqs cqs = this.a.get(i3);
            if (cqs.a.equals(str)) {
                cqs.b = cqr;
            } else {
                i2++;
            }
        }
        if (i2 == i) {
            return true;
        }
        return false;
    }
}
