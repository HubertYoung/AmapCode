package defpackage;

/* renamed from: ezw reason: default package */
/* compiled from: PushClientManager */
final class ezw implements ewu {
    final /* synthetic */ a a;
    final /* synthetic */ ezv b;

    ezw(ezv ezv, a aVar) {
        this.b = ezv;
        this.a = aVar;
    }

    public final void a(int i) {
        if (i == 0) {
            Object[] objArr = this.a.c;
            if (objArr == null || objArr.length == 0) {
                fat.a((String) "PushClientManager", (String) "bind app result is null");
            } else {
                this.b.a((String) this.a.c[0]);
            }
        } else {
            this.b.e = null;
            this.b.d.c("APP_TOKEN");
        }
    }
}
