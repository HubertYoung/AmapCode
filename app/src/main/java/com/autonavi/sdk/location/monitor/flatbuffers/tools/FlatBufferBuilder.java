package com.autonavi.sdk.location.monitor.flatbuffers.tools;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.Arrays;

public class FlatBufferBuilder {
    static final Charset c = Charset.forName("UTF-8");
    static final /* synthetic */ boolean q = true;
    public ByteBuffer a;
    public int b;
    public int d;
    int[] e;
    int f;
    boolean g;
    public boolean h;
    int i;
    int[] j;
    int k;
    int l;
    boolean m;
    CharsetEncoder n;
    ByteBuffer o;
    ByteBufferFactory p;

    public interface ByteBufferFactory {
        ByteBuffer newByteBuffer(int i);
    }

    public static final class a implements ByteBufferFactory {
        public final ByteBuffer newByteBuffer(int i) {
            return ByteBuffer.allocate(i).order(ByteOrder.LITTLE_ENDIAN);
        }
    }

    private FlatBufferBuilder(ByteBufferFactory byteBufferFactory) {
        this.d = 1;
        this.e = null;
        this.f = 0;
        this.g = false;
        this.h = false;
        this.j = new int[16];
        this.k = 0;
        this.l = 0;
        this.m = false;
        this.n = c.newEncoder();
        this.b = 1024;
        this.p = byteBufferFactory;
        this.a = byteBufferFactory.newByteBuffer(1024);
    }

    private FlatBufferBuilder() {
        this((ByteBufferFactory) new a());
    }

    public FlatBufferBuilder(byte b2) {
        this();
    }

    private int c() {
        return this.a.capacity() - this.b;
    }

    private void c(int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            ByteBuffer byteBuffer = this.a;
            int i4 = this.b - 1;
            this.b = i4;
            byteBuffer.put(i4, 0);
        }
    }

    public final void a(int i2, int i3) {
        if (i2 > this.d) {
            this.d = i2;
        }
        int i4 = ((~((this.a.capacity() - this.b) + i3)) + 1) & (i2 - 1);
        while (this.b < i4 + i2 + i3) {
            int capacity = this.a.capacity();
            ByteBuffer byteBuffer = this.a;
            ByteBufferFactory byteBufferFactory = this.p;
            int capacity2 = byteBuffer.capacity();
            if ((-1073741824 & capacity2) != 0) {
                throw new AssertionError("FlatBuffers: cannot grow buffer beyond 2 gigabytes.");
            }
            int i5 = capacity2 == 0 ? 1 : capacity2 << 1;
            byteBuffer.position(0);
            ByteBuffer newByteBuffer = byteBufferFactory.newByteBuffer(i5);
            newByteBuffer.position(i5 - capacity2);
            newByteBuffer.put(byteBuffer);
            this.a = newByteBuffer;
            this.b += this.a.capacity() - capacity;
        }
        c(i4);
    }

    private void d(int i2) {
        ByteBuffer byteBuffer = this.a;
        int i3 = this.b - 4;
        this.b = i3;
        byteBuffer.putInt(i3, i2);
    }

    private void a(boolean z) {
        a(1, 0);
        ByteBuffer byteBuffer = this.a;
        int i2 = this.b - 1;
        this.b = i2;
        byteBuffer.put(i2, z ? (byte) 1 : 0);
    }

    private void a(byte b2) {
        a(1, 0);
        ByteBuffer byteBuffer = this.a;
        int i2 = this.b - 1;
        this.b = i2;
        byteBuffer.put(i2, b2);
    }

    private void a(short s) {
        a(2, 0);
        ByteBuffer byteBuffer = this.a;
        int i2 = this.b - 2;
        this.b = i2;
        byteBuffer.putShort(i2, s);
    }

    private void e(int i2) {
        a(4, 0);
        d(i2);
    }

    private void a(long j2) {
        a(8, 0);
        ByteBuffer byteBuffer = this.a;
        int i2 = this.b - 8;
        this.b = i2;
        byteBuffer.putLong(i2, j2);
    }

    private void a(float f2) {
        a(4, 0);
        ByteBuffer byteBuffer = this.a;
        int i2 = this.b - 4;
        this.b = i2;
        byteBuffer.putFloat(i2, f2);
    }

    private void a(double d2) {
        a(8, 0);
        ByteBuffer byteBuffer = this.a;
        int i2 = this.b - 8;
        this.b = i2;
        byteBuffer.putDouble(i2, d2);
    }

    public final void a(int i2) {
        a(4, 0);
        if (q || i2 <= c()) {
            d((c() - i2) + 4);
            return;
        }
        throw new AssertionError();
    }

    public final void a(int i2, int i3, int i4) {
        d();
        this.l = i3;
        int i5 = i2 * i3;
        a(4, i5);
        a(i4, i5);
        this.g = true;
    }

    public final int a() {
        if (!this.g) {
            throw new AssertionError("FlatBuffers: endVector called without startVector");
        }
        this.g = false;
        d(this.l);
        return c();
    }

    public final int a(CharSequence charSequence) {
        int length = (int) (((float) charSequence.length()) * this.n.maxBytesPerChar());
        if (this.o == null || this.o.capacity() < length) {
            this.o = ByteBuffer.allocate(Math.max(128, length));
        }
        this.o.clear();
        CoderResult encode = this.n.encode(CharBuffer.wrap(charSequence), this.o, true);
        if (encode.isError()) {
            try {
                encode.throwException();
            } catch (CharacterCodingException e2) {
                throw new Error(e2);
            }
        }
        this.o.flip();
        ByteBuffer byteBuffer = this.o;
        int remaining = byteBuffer.remaining();
        a(0);
        a(1, remaining, 1);
        ByteBuffer byteBuffer2 = this.a;
        int i2 = this.b - remaining;
        this.b = i2;
        byteBuffer2.position(i2);
        this.a.put(byteBuffer);
        return a();
    }

    private void d() {
        if (this.g) {
            throw new AssertionError("FlatBuffers: object serialization must not be nested.");
        }
    }

    public final void b(int i2) {
        d();
        if (this.e == null || this.e.length < i2) {
            this.e = new int[i2];
        }
        this.f = i2;
        Arrays.fill(this.e, 0, this.f, 0);
        this.g = true;
        this.i = c();
    }

    public final void a(int i2, boolean z) {
        if (this.m || z) {
            a(z);
            f(i2);
        }
    }

    public final void a(int i2, byte b2, int i3) {
        if (this.m || b2 != i3) {
            a(b2);
            f(i2);
        }
    }

    public final void a(int i2, short s) {
        if (this.m || s != 0) {
            a(s);
            f(i2);
        }
    }

    public final void b(int i2, int i3, int i4) {
        if (this.m || i3 != i4) {
            e(i3);
            f(i2);
        }
    }

    public final void a(int i2, long j2) {
        if (this.m || j2 != 0) {
            a(j2);
            f(i2);
        }
    }

    public final void a(int i2, float f2) {
        if (this.m || ((double) f2) != -999.0d) {
            a(f2);
            f(i2);
        }
    }

    public final void a(int i2, double d2) {
        if (this.m || d2 != -999.0d) {
            a(d2);
            f(i2);
        }
    }

    public final void b(int i2, int i3) {
        if (this.m || i3 != 0) {
            a(i3);
            f(i2);
        }
    }

    private void f(int i2) {
        this.e[i2] = c();
    }

    public final int b() {
        int i2;
        if (this.e == null || !this.g) {
            throw new AssertionError("FlatBuffers: endObject called without startObject");
        }
        e(0);
        int c2 = c();
        int i3 = this.f - 1;
        while (i3 >= 0 && this.e[i3] == 0) {
            i3--;
        }
        int i4 = i3 + 1;
        while (i3 >= 0) {
            a((short) (this.e[i3] != 0 ? c2 - this.e[i3] : 0));
            i3--;
        }
        a((short) (c2 - this.i));
        a((short) ((i4 + 2) * 2));
        int i5 = 0;
        loop2:
        while (true) {
            if (i5 >= this.k) {
                i2 = 0;
                break;
            }
            int capacity = this.a.capacity() - this.j[i5];
            int i6 = this.b;
            short s = this.a.getShort(capacity);
            if (s == this.a.getShort(i6)) {
                int i7 = 2;
                while (i7 < s) {
                    if (this.a.getShort(capacity + i7) == this.a.getShort(i6 + i7)) {
                        i7 += 2;
                    }
                }
                i2 = this.j[i5];
                break loop2;
            }
            i5++;
        }
        if (i2 != 0) {
            this.b = this.a.capacity() - c2;
            this.a.putInt(this.b, i2 - c2);
        } else {
            if (this.k == this.j.length) {
                this.j = Arrays.copyOf(this.j, this.k * 2);
            }
            int[] iArr = this.j;
            int i8 = this.k;
            this.k = i8 + 1;
            iArr[i8] = c();
            this.a.putInt(this.a.capacity() - c2, c() - c2);
        }
        this.g = false;
        return c2;
    }
}
