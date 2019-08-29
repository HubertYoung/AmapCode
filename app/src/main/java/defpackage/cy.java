package defpackage;

import android.text.TextUtils;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: cy reason: default package */
/* compiled from: SessionSeq */
public final class cy {
    private static AtomicInteger a = new AtomicInteger();

    public static String a(String str) {
        if (a.get() == Integer.MAX_VALUE) {
            a.set(0);
        }
        if (!TextUtils.isEmpty(str)) {
            return cz.a(str, ".AWCN", String.valueOf(a.incrementAndGet()));
        }
        return cz.a((String) "AWCN", String.valueOf(a.incrementAndGet()));
    }
}
