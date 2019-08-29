package defpackage;

/* renamed from: aab reason: default package */
/* compiled from: AosContextImpl */
public class aab implements ir {
    private volatile aac a;
    private volatile aaa b;

    public final is a() {
        if (this.a == null) {
            synchronized (aab.class) {
                if (this.a == null) {
                    this.a = new aac();
                }
            }
        }
        return this.a;
    }

    public final iq b() {
        if (this.b == null) {
            synchronized (aab.class) {
                if (this.b == null) {
                    this.b = new aaa();
                }
            }
        }
        return this.b;
    }
}
