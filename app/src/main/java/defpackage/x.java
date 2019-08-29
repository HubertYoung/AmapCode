package defpackage;

import anet.channel.statist.StatObject;

/* renamed from: x reason: default package */
/* compiled from: AppMonitor */
public final class x {
    private static volatile z a = new a(null);
    /* access modifiers changed from: private */
    public static volatile z b;

    /* renamed from: x$a */
    /* compiled from: AppMonitor */
    static class a implements z {
        z a = null;

        a(z zVar) {
            this.a = zVar;
        }

        public final void a(StatObject statObject) {
            if (x.b != null) {
                x.b.a(statObject);
            }
            if (this.a != null) {
                this.a.a(statObject);
            }
        }

        public final void a(bj bjVar) {
            if (this.a != null) {
                this.a.a(bjVar);
            }
        }

        public final void a(bk bkVar) {
            if (this.a != null) {
                this.a.a(bkVar);
            }
        }
    }

    public static z a() {
        return a;
    }

    public static void a(z zVar) {
        a = new a(zVar);
    }
}
