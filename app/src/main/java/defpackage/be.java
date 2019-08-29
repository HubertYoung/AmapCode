package defpackage;

/* renamed from: be reason: default package */
/* compiled from: SecurityManager */
public final class be {
    private static volatile bb a;

    public static bb a() {
        if (a == null) {
            a = new bb() {
                public final ba a(String str) {
                    return new bd(str);
                }

                public final ba b(String str) {
                    return new bc(str);
                }
            };
        }
        return a;
    }
}
