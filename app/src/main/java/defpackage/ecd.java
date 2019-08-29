package defpackage;

/* renamed from: ecd reason: default package */
/* compiled from: FootNaviManager */
public class ecd {
    private static ecd b;
    public boolean a;

    private ecd() {
    }

    public static ecd a() {
        if (b == null) {
            synchronized (ecd.class) {
                try {
                    if (b == null) {
                        b = new ecd();
                    }
                }
            }
        }
        return b;
    }
}
