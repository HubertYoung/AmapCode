package defpackage;

/* renamed from: eec reason: default package */
/* compiled from: RideNaviManager */
public class eec {
    private static eec b;
    public boolean a;

    private eec() {
    }

    public static eec a() {
        if (b == null) {
            synchronized (eec.class) {
                try {
                    if (b == null) {
                        b = new eec();
                    }
                }
            }
        }
        return b;
    }
}
