package defpackage;

import java.util.HashSet;
import java.util.Set;

/* renamed from: acw reason: default package */
/* compiled from: PlanHomeLifecyleDispatcher */
public class acw implements acz {
    private static acw d;
    public Set<acz> a = new HashSet();
    public boolean b = false;
    private final String c = acw.class.getSimpleName();

    private acw() {
    }

    public static acw a() {
        if (d == null) {
            synchronized (acw.class) {
                try {
                    if (d == null) {
                        d = new acw();
                    }
                }
            }
        }
        return d;
    }

    public void onCreate() {
        this.b = true;
        for (acz next : this.a) {
            if (next != null) {
                next.onCreate();
            }
        }
    }

    public void onDestroy() {
        this.b = false;
        for (acz next : this.a) {
            if (next != null) {
                next.onDestroy();
            }
        }
    }
}
