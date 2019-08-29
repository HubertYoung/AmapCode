package defpackage;

import android.app.Activity;
import android.app.Application;

@Deprecated
/* renamed from: ccm reason: default package */
/* compiled from: StartProcessManager */
public class ccm {
    public static volatile ccr a;
    public static ccq b;
    private static volatile ccm d;
    public volatile Application c;

    public static ccm a() {
        if (d == null) {
            synchronized (ccm.class) {
                try {
                    if (d == null) {
                        d = new ccm();
                        b = new cco();
                    }
                }
            }
        }
        return d;
    }

    public static void a(Activity activity) {
        b.a(activity);
    }

    public static void b(Activity activity) {
        b.b(activity);
    }
}
