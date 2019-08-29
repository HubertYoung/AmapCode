package defpackage;

/* renamed from: al reason: default package */
/* compiled from: NetworkAnalysis */
public final class al {
    private static volatile ak a = new a(null);

    /* renamed from: al$a */
    /* compiled from: NetworkAnalysis */
    static class a implements ak {
        private ak a = null;

        a(ak akVar) {
            this.a = akVar;
        }

        public final void a(aj ajVar) {
            if (this.a != null) {
                this.a.a(ajVar);
            }
        }
    }

    public static ak a() {
        return a;
    }

    public static void a(ak akVar) {
        a = new a(akVar);
    }
}
