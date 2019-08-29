package org.apache.thrift.transport;

public final class c extends d {
    public byte[] a;
    public int b;
    public int c;

    public final int a(byte[] bArr, int i, int i2) {
        int c2 = c();
        if (i2 > c2) {
            i2 = c2;
        }
        if (i2 > 0) {
            System.arraycopy(this.a, this.b, bArr, i, i2);
            a(i2);
        }
        return i2;
    }

    public final void a(int i) {
        this.b += i;
    }

    public final byte[] a() {
        return this.a;
    }

    public final int b() {
        return this.b;
    }

    public final void b(byte[] bArr, int i, int i2) {
        throw new UnsupportedOperationException("No writing allowed!");
    }

    public final int c() {
        return this.c - this.b;
    }
}
