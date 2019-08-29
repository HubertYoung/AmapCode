package defpackage;

/* renamed from: oj reason: default package */
/* compiled from: OfflineRequestClient */
class oj {
    private static int b = 5;
    private static volatile oj c;
    boy a = new boy();

    private oj() {
        this.a.a(b);
    }

    public static oj a() {
        if (c == null) {
            synchronized (oj.class) {
                try {
                    if (c == null) {
                        c = new oj();
                    }
                }
            }
        }
        return c;
    }
}
