package defpackage;

/* renamed from: ddk reason: default package */
/* compiled from: ShareFactory */
public final class ddk {
    private static volatile dcc a;

    @Deprecated
    public static synchronized dcc a() {
        dcc dcc;
        synchronized (ddk.class) {
            try {
                if (a == null) {
                    a = new ddm();
                }
                dcc = a;
            }
        }
        return dcc;
    }
}
