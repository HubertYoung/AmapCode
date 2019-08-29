package defpackage;

/* renamed from: cxf reason: default package */
/* compiled from: IPopWindow */
public abstract class cxf {
    /* access modifiers changed from: protected */
    public abstract void a();

    /* access modifiers changed from: protected */
    public abstract void b();

    /* access modifiers changed from: protected */
    public abstract void c();

    /* access modifiers changed from: protected */
    public abstract void d();

    /* access modifiers changed from: protected */
    public final void e() {
        try {
            a();
            b();
            c();
            d();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
