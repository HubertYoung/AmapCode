package defpackage;

/* renamed from: dnm reason: default package */
/* compiled from: ManagerFactory */
public final class dnm {
    private static volatile dps a;

    public static dps a() {
        if (a == null) {
            a = new dps();
        }
        return a;
    }
}
