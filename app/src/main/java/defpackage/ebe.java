package defpackage;

/* renamed from: ebe reason: default package */
/* compiled from: RouteLogoutListener */
public final class ebe implements anp {
    private static ebe a;

    public final void onUserInfoUpdate(ant ant) {
    }

    private ebe() {
    }

    public static synchronized ebe a() {
        ebe ebe;
        synchronized (ebe.class) {
            try {
                if (a == null) {
                    a = new ebe();
                }
                ebe = a;
            }
        }
        return ebe;
    }

    public final void onLoginStateChanged(boolean z, boolean z2) {
        if (!z2) {
            try {
                bky.b();
            } catch (Exception unused) {
            }
        }
    }
}
