package defpackage;

/* renamed from: cdw reason: default package */
/* compiled from: GPSBtnDispatcher */
public final class cdw {
    public agl<ced> a = new agl<>();

    public final void a(final int i) {
        this.a.a((a<T>) new a<ced>() {
            public final /* synthetic */ void onNotify(Object obj) {
                ced ced = (ced) obj;
                if (i != ced.getState()) {
                    ced.setState(i);
                }
            }
        });
    }

    public final ced a() {
        return (ced) this.a.b();
    }
}
