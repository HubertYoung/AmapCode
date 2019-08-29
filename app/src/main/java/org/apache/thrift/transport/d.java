package org.apache.thrift.transport;

public abstract class d {
    public final int a(byte[] bArr, int i) {
        int i2 = 0;
        while (i2 < i) {
            int a = a(bArr, i2 + 0, i - i2);
            if (a <= 0) {
                StringBuilder sb = new StringBuilder("Cannot read. Remote side has closed. Tried to read ");
                sb.append(i);
                sb.append(" bytes, but only got ");
                sb.append(i2);
                sb.append(" bytes.");
                throw new e(sb.toString());
            }
            i2 += a;
        }
        return i2;
    }

    public abstract int a(byte[] bArr, int i, int i2);

    public void a(int i) {
    }

    public byte[] a() {
        return null;
    }

    public int b() {
        return 0;
    }

    public abstract void b(byte[] bArr, int i, int i2);

    public int c() {
        return -1;
    }
}
