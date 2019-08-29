package com.alipay.android.phone.inside.protobuf.wire;

import com.alipay.android.phone.inside.protobuf.okio.Buffer;
import com.alipay.android.phone.inside.protobuf.okio.BufferedSource;
import com.alipay.android.phone.inside.protobuf.okio.ByteString;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;

final class WireInput {
    private static final Charset b = Charset.forName("UTF-8");
    public int a;
    private final BufferedSource c;
    private int d = 0;
    private int e = Integer.MAX_VALUE;
    private int f;

    public static long a(long j) {
        return (-(j & 1)) ^ (j >>> 1);
    }

    public static int c(int i) {
        return (-(i & 1)) ^ (i >>> 1);
    }

    public static WireInput a(byte[] bArr) {
        Buffer buffer = new Buffer();
        if (bArr != null) {
            return new WireInput(buffer.a(bArr, 0, bArr.length));
        }
        throw new IllegalArgumentException("source == null");
    }

    public final void a(int i) throws IOException {
        if (this.f != i) {
            throw new IOException("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final String b() throws IOException {
        int c2 = c();
        this.d += c2;
        return this.c.a((long) c2, b);
    }

    public final ByteString b(int i) throws IOException {
        this.d += i;
        long j = (long) i;
        this.c.a(j);
        return this.c.b(j);
    }

    public final int c() throws IOException {
        byte b2;
        this.d++;
        byte b3 = this.c.b();
        if (b3 >= 0) {
            return b3;
        }
        byte b4 = b3 & Byte.MAX_VALUE;
        this.d++;
        byte b5 = this.c.b();
        if (b5 >= 0) {
            b2 = b4 | (b5 << 7);
        } else {
            byte b6 = b4 | ((b5 & Byte.MAX_VALUE) << 7);
            this.d++;
            byte b7 = this.c.b();
            if (b7 >= 0) {
                b2 = b6 | (b7 << 14);
            } else {
                byte b8 = b6 | ((b7 & Byte.MAX_VALUE) << 14);
                this.d++;
                byte b9 = this.c.b();
                if (b9 >= 0) {
                    b2 = b8 | (b9 << 21);
                } else {
                    byte b10 = b8 | ((b9 & Byte.MAX_VALUE) << 21);
                    this.d++;
                    byte b11 = this.c.b();
                    b2 = b10 | (b11 << 28);
                    if (b11 < 0) {
                        for (int i = 0; i < 5; i++) {
                            this.d++;
                            if (this.c.b() >= 0) {
                                return b2;
                            }
                        }
                        throw new IOException("WireInput encountered a malformed varint.");
                    }
                }
            }
        }
        return b2;
    }

    public final long d() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            this.d++;
            byte b2 = this.c.b();
            j |= ((long) (b2 & Byte.MAX_VALUE)) << i;
            if ((b2 & 128) == 0) {
                return j;
            }
        }
        throw new IOException("WireInput encountered a malformed varint.");
    }

    public final int e() throws IOException {
        this.d += 4;
        return this.c.c();
    }

    public final long f() throws IOException {
        this.d += 8;
        return this.c.d();
    }

    private WireInput(BufferedSource bufferedSource) {
        this.c = bufferedSource;
    }

    public final int d(int i) throws IOException {
        if (i < 0) {
            throw new IOException("Encountered a negative size");
        }
        int i2 = i + this.d;
        int i3 = this.e;
        if (i2 > i3) {
            throw new EOFException("The input ended unexpectedly in the middle of a field");
        }
        this.e = i2;
        return i3;
    }

    public final void e(int i) {
        this.e = i;
    }

    public final long g() {
        return (long) this.d;
    }

    public final void h() throws IOException {
        boolean z;
        do {
            int a2 = a();
            if (a2 != 0) {
                z = false;
                switch (WireType.valueOf(a2)) {
                    case VARINT:
                        d();
                        continue;
                    case FIXED32:
                        e();
                        continue;
                    case FIXED64:
                        f();
                        continue;
                    case LENGTH_DELIMITED:
                        long c2 = (long) c();
                        this.d = (int) (((long) this.d) + c2);
                        this.c.c(c2);
                        continue;
                    case START_GROUP:
                        h();
                        a((a2 & -8) | WireType.END_GROUP.value());
                        continue;
                    case END_GROUP:
                        z = true;
                        continue;
                    default:
                        throw new AssertionError();
                }
            } else {
                return;
            }
        } while (!z);
    }

    public final int a() throws IOException {
        boolean z;
        if (((long) this.d) == ((long) this.e)) {
            z = true;
        } else {
            z = this.c.a();
        }
        if (z) {
            this.f = 0;
            return 0;
        }
        this.f = c();
        if (this.f != 0) {
            return this.f;
        }
        throw new IOException("Protocol message contained an invalid tag (zero).");
    }
}
