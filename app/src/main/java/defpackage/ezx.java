package defpackage;

/* renamed from: ezx reason: default package */
/* compiled from: PushClientManager */
final class ezx implements Runnable {
    final /* synthetic */ exc a;
    final /* synthetic */ String b;
    final /* synthetic */ ezv c;

    ezx(ezv ezv, exc exc, String str) {
        this.c = ezv;
        this.a = exc;
        this.b = str;
    }

    public final void run() {
        this.c.a((fbh) this.a);
        fbf.a((Runnable) new fab(this.c, this.b));
    }
}
