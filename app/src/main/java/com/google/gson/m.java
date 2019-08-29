package com.google.gson;

import com.google.gson.b.h;
import java.util.Map.Entry;
import java.util.Set;

/* compiled from: JsonObject */
public final class m extends j {
    private final h<String, j> a = new h<>();

    public final void a(String str, j jVar) {
        if (jVar == null) {
            jVar = l.a;
        }
        this.a.put(str, jVar);
    }

    public final Set<Entry<String, j>> o() {
        return this.a.entrySet();
    }

    public final boolean equals(Object obj) {
        return obj == this || ((obj instanceof m) && ((m) obj).a.equals(this.a));
    }

    public final int hashCode() {
        return this.a.hashCode();
    }
}
