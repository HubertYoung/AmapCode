package defpackage;

import anet.channel.monitor.NetworkSpeed;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: aq reason: default package */
/* compiled from: BandWidthListenerHelper */
public class aq {
    private static volatile aq a;
    private Map<as, au> b = new ConcurrentHashMap();
    private au c = new au();

    private aq() {
    }

    public static aq a() {
        if (a == null) {
            synchronized (aq.class) {
                if (a == null) {
                    a = new aq();
                }
            }
        }
        return a;
    }

    public final void a(double d) {
        for (Entry next : this.b.entrySet()) {
            as asVar = (as) next.getKey();
            au auVar = (au) next.getValue();
            if (!(asVar == null || auVar == null || auVar.a())) {
                boolean z = d < 40.0d;
                if (auVar.a != z) {
                    auVar.a = z;
                    if (z) {
                        NetworkSpeed networkSpeed = NetworkSpeed.Slow;
                    } else {
                        NetworkSpeed networkSpeed2 = NetworkSpeed.Fast;
                    }
                }
            }
        }
    }
}
