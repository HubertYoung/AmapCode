package com.gauss.speex.encode;

public class Speex {
    public long a;

    private native int end(int i, long j);

    private native long open(int i);

    public native int close(int i, long j);

    public native int decode(byte[] bArr, short[] sArr, int i, long j);

    public native int encode(short[] sArr, int i, byte[] bArr, int i2, long j);

    static {
        System.loadLibrary("speex");
    }

    public final void a() {
        this.a = open(5);
    }

    public final void b() {
        end(5, this.a);
    }
}
