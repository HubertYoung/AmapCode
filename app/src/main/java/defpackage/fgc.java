package defpackage;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* renamed from: fgc reason: default package */
/* compiled from: XcmdEventMgr */
public final class fgc {
    static Set<fgb> a = new CopyOnWriteArraySet();

    /* renamed from: fgc$a */
    /* compiled from: XcmdEventMgr */
    static class a {
        static fgc a = new fgc(0);
    }

    /* synthetic */ fgc(byte b) {
        this();
    }

    public static fgc a() {
        return a.a;
    }

    private fgc() {
    }

    public static void a(String str) {
        if (!fdd.b(str)) {
            fga fga = new fga(str);
            for (fgb onEvent : a) {
                try {
                    onEvent.onEvent(fga);
                } catch (Throwable unused) {
                }
            }
        }
    }
}
