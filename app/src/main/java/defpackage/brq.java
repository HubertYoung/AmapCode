package defpackage;

/* renamed from: brq reason: default package */
/* compiled from: IndoorOrScenicManager */
public class brq {
    private static volatile brq a;
    private agl<a> b = new agl<>();

    /* renamed from: brq$a */
    /* compiled from: IndoorOrScenicManager */
    public interface a {
        void a(boolean z);
    }

    private brq() {
    }

    public static brq a() {
        if (a == null) {
            synchronized (brq.class) {
                try {
                    if (a == null) {
                        a = new brq();
                    }
                }
            }
        }
        return a;
    }

    public final void a(a aVar) {
        this.b.a(aVar);
    }

    public final void b(a aVar) {
        this.b.b(aVar);
    }

    public final void a(final boolean z) {
        this.b.a((defpackage.agl.a<T>) new defpackage.agl.a<a>() {
            public final /* synthetic */ void onNotify(Object obj) {
                ((a) obj).a(z);
            }
        });
    }
}
