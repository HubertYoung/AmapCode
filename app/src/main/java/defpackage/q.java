package defpackage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: q reason: default package */
/* compiled from: SessionAttributeManager */
public final class q {
    public Map<String, Integer> a = new HashMap();
    public Map<String, t> b = new ConcurrentHashMap();

    q() {
    }

    /* access modifiers changed from: 0000 */
    public final t a(String str) {
        return this.b.get(str);
    }

    public final int b(String str) {
        Integer num;
        synchronized (this.a) {
            num = this.a.get(str);
        }
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }
}
