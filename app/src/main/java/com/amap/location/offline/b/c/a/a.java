package com.amap.location.offline.b.c.a;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: AbstractBuilder */
public abstract class a {
    protected c a = new c(this.b);
    private ByteBuffer b;

    protected a(int i) {
        this.b = ByteBuffer.allocate(i);
        this.b.order(ByteOrder.LITTLE_ENDIAN);
    }

    public a a() {
        this.a.init(this.b);
        return this;
    }
}
