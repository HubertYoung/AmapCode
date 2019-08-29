package defpackage;

import anet.channel.strategy.ConnProtocol;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: by reason: default package */
/* compiled from: StrategyTemplate */
public final class by {
    Map<String, ConnProtocol> a = new ConcurrentHashMap();

    /* renamed from: by$a */
    /* compiled from: StrategyTemplate */
    public static class a {
        public static by a = new by();
    }

    public final void a(String str, ConnProtocol connProtocol) {
        if (connProtocol != null) {
            this.a.put(str, connProtocol);
            try {
                bq a2 = bu.a();
                if (a2 instanceof bv) {
                    ((bv) a2).b.c.a(str, connProtocol);
                }
            } catch (Exception unused) {
            }
        }
    }
}
