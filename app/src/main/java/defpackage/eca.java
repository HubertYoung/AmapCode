package defpackage;

/* renamed from: eca reason: default package */
/* compiled from: FootNaviTypeManager */
public final class eca {
    private static eca c = new eca();
    public int a = 1;
    public a b;

    /* renamed from: eca$a */
    /* compiled from: FootNaviTypeManager */
    public interface a {
        void a(int i);
    }

    private eca() {
    }

    public static eca a() {
        return c;
    }

    public final void a(int i) {
        this.a = i;
        if (this.b != null) {
            this.b.a(i);
        }
    }
}
