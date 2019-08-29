package defpackage;

import java.text.SimpleDateFormat;

/* renamed from: fhw reason: default package */
/* compiled from: AutoRemoteFileLogUtil */
public class fhw {
    private static volatile fhw a;
    private String b = null;
    private SimpleDateFormat c = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");

    private fhw() {
    }

    public static fhw a() {
        if (a == null) {
            synchronized (fhw.class) {
                if (a == null) {
                    a = new fhw();
                }
            }
        }
        return a;
    }
}
