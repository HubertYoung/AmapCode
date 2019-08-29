package org.android.spdy;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* compiled from: HTTPHeaderPool */
class ByteBufferAndString {
    int a;
    ByteBuffer b;
    public long c = 0;
    private String d;

    public ByteBufferAndString(ByteBuffer byteBuffer) {
        this.b = byteBuffer;
        this.d = null;
        this.a = -1;
        this.c = System.currentTimeMillis();
    }

    public String toString() {
        if (this.d == null) {
            synchronized (this) {
                if (this.d == null) {
                    try {
                        this.d = new String(this.b.array(), this.b.position(), this.b.limit() - this.b.position(), "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return this.d;
    }

    public int hashCode() {
        return this.b.hashCode();
    }
}
