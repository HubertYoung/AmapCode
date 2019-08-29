package defpackage;

/* renamed from: aik reason: default package */
/* compiled from: DataMessageManager */
public final class aik {
    private static aik b;
    public a a = null;

    /* renamed from: aik$a */
    /* compiled from: DataMessageManager */
    public interface a {
        void a(String str);
    }

    private aik() {
    }

    public static synchronized aik a() {
        aik aik;
        synchronized (aik.class) {
            try {
                if (b == null) {
                    b = new aik();
                }
                aik = b;
            }
        }
        return aik;
    }

    public final void a(String str) {
        if (this.a != null) {
            this.a.a(str);
        }
    }

    public final void b() {
        a(ais.b(true));
    }

    public final void c() {
        a(ais.b(false));
    }
}
