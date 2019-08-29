package com.alipay.android.phone.mobilesdk.permission.utils;

import java.util.ArrayDeque;

/* compiled from: Pool */
public abstract class g<T> {
    public final int a = 24;
    public int b;
    protected final ArrayDeque<T> c = new ArrayDeque<>(8);

    /* compiled from: Pool */
    public interface a {
        void b();
    }

    public void a(T object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null.");
        }
        if (this.c.size() < this.a) {
            this.c.add(object);
            this.b = Math.max(this.b, this.c.size());
        }
        if (object instanceof a) {
            ((a) object).b();
        }
    }
}
