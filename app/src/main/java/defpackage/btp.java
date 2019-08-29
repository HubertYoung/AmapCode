package defpackage;

import android.app.Dialog;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/* renamed from: btp reason: default package */
/* compiled from: DialogLifecycleManager */
public class btp implements btq {
    private static volatile btp a;
    private static List<btq> b;

    private btp() {
        b = new LinkedList();
    }

    public static btp a() {
        if (a == null) {
            synchronized (btp.class) {
                try {
                    if (a == null) {
                        a = new btp();
                    }
                }
            }
        }
        return a;
    }

    public static void a(btq btq) {
        b.add(btq);
    }

    public final void a(WeakReference<? extends Dialog> weakReference) {
        for (btq next : b) {
            if (next != null) {
                next.a(weakReference);
            }
        }
    }

    public final void b(WeakReference<? extends Dialog> weakReference) {
        for (btq next : b) {
            if (next != null) {
                next.b(weakReference);
            }
        }
    }
}
