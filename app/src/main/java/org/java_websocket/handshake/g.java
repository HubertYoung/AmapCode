package org.java_websocket.handshake;

import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

public class g implements c {
    private byte[] a;
    private TreeMap<String, String> b = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public final void a(String str, String str2) {
        this.b.put(str, str2);
    }

    public final void a(byte[] bArr) {
        this.a = bArr;
    }

    public final String b(String str) {
        String str2 = this.b.get(str);
        return str2 == null ? "" : str2;
    }

    public final Iterator<String> b() {
        return Collections.unmodifiableSet(this.b.keySet()).iterator();
    }

    public final boolean c(String str) {
        return this.b.containsKey(str);
    }

    public final byte[] c() {
        return this.a;
    }
}
