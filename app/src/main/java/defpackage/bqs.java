package defpackage;

import com.autonavi.io.monitor.BlockGuardOsProxy;
import java.lang.reflect.Field;
import libcore.io.Os;

/* renamed from: bqs reason: default package */
/* compiled from: IOMonitor */
public class bqs {
    private static volatile bqs a;
    private Os b;

    private bqs() {
    }

    public static bqs a() {
        if (a == null) {
            synchronized (bqs.class) {
                if (a == null) {
                    a = new bqs();
                }
            }
        }
        return a;
    }

    public final boolean a(bqr bqr) {
        try {
            Field declaredField = Class.forName("libcore.io.Libcore").getDeclaredField("os");
            Os os = (Os) declaredField.get(null);
            if (os instanceof bqt) {
                return false;
            }
            declaredField.set(null, new BlockGuardOsProxy(os, bqr));
            this.b = os;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
