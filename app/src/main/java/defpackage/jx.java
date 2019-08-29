package defpackage;

/* renamed from: jx reason: default package */
/* compiled from: UpdateHintConfigManager */
public class jx {
    private static volatile jx a;
    /* access modifiers changed from: private */
    public jv b = new jv();

    public static jx a() {
        if (a == null) {
            synchronized (jx.class) {
                try {
                    if (a == null) {
                        a = new jx();
                    }
                }
            }
        }
        return a;
    }

    private void b() {
        synchronized (jx.class) {
            this.b.a();
        }
    }

    public final void a(String str) {
        synchronized (jx.class) {
            b();
            jw.a();
            jw.b(str);
        }
    }

    private jx() {
    }
}
