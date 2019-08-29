package defpackage;

import java.util.concurrent.ConcurrentHashMap;

/* renamed from: bt reason: default package */
/* compiled from: SchemeGuesser */
public final class bt {
    final ConcurrentHashMap<String, String> a = new ConcurrentHashMap<>();
    boolean b = true;

    /* renamed from: bt$a */
    /* compiled from: SchemeGuesser */
    public static class a {
        public static bt a = new bt();
    }

    public final void a(String str) {
        this.a.put(str, "http");
    }
}
