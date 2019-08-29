package defpackage;

/* renamed from: fab reason: default package */
/* compiled from: PushClientManager */
final class fab implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ ezv b;

    fab(ezv ezv, String str) {
        this.b = ezv;
        this.a = str;
    }

    public final void run() {
        a b2 = this.b.b(this.a);
        if (b2 != null) {
            b2.a(1003, new Object[0]);
        }
    }
}
