package com.alipay.android.phone.inside.protobuf.okio;

import java.io.EOFException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Buffer implements BufferedSink, BufferedSource, Cloneable {
    Segment a;
    long b;

    public final void close() {
    }

    public final boolean a() {
        return this.b == 0;
    }

    public final void a(long j) throws EOFException {
        if (this.b < j) {
            throw new EOFException();
        }
    }

    public final byte b() {
        if (this.b == 0) {
            throw new IllegalStateException("size == 0");
        }
        Segment segment = this.a;
        int i = segment.b;
        int i2 = segment.c;
        int i3 = i + 1;
        byte b2 = segment.a[i];
        this.b--;
        if (i3 == i2) {
            this.a = segment.a();
            SegmentPool.a.a(segment);
        } else {
            segment.b = i3;
        }
        return b2;
    }

    private int e() {
        if (this.b < 4) {
            StringBuilder sb = new StringBuilder("size < 4: ");
            sb.append(this.b);
            throw new IllegalStateException(sb.toString());
        }
        Segment segment = this.a;
        int i = segment.b;
        int i2 = segment.c;
        if (i2 - i < 4) {
            return ((b() & 255) << 24) | ((b() & 255) << 16) | ((b() & 255) << 8) | (b() & 255);
        }
        byte[] bArr = segment.a;
        int i3 = i + 1;
        int i4 = i3 + 1;
        byte b2 = ((bArr[i] & 255) << 24) | ((bArr[i3] & 255) << 16);
        int i5 = i4 + 1;
        byte b3 = b2 | ((bArr[i4] & 255) << 8);
        int i6 = i5 + 1;
        byte b4 = b3 | (bArr[i5] & 255);
        this.b -= 4;
        if (i6 == i2) {
            this.a = segment.a();
            SegmentPool.a.a(segment);
        } else {
            segment.b = i6;
        }
        return b4;
    }

    public final int c() {
        return Util.a(e());
    }

    public final ByteString b(long j) {
        return new ByteString(d(j));
    }

    public final String a(long j, Charset charset) {
        Util.a(this.b, 0, j);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (j > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: ".concat(String.valueOf(j)));
        } else if (j == 0) {
            return "";
        } else {
            Segment segment = this.a;
            if (((long) segment.b) + j > ((long) segment.c)) {
                return new String(d(j), charset);
            }
            String str = new String(segment.a, segment.b, (int) j, charset);
            segment.b = (int) (((long) segment.b) + j);
            this.b -= j;
            if (segment.b == segment.c) {
                this.a = segment.a();
                SegmentPool.a.a(segment);
            }
            return str;
        }
    }

    private byte[] d(long j) {
        Util.a(this.b, 0, j);
        if (j > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: ".concat(String.valueOf(j)));
        }
        int i = 0;
        byte[] bArr = new byte[((int) j)];
        while (true) {
            long j2 = (long) i;
            if (j2 < j) {
                int min = (int) Math.min(j - j2, (long) (this.a.c - this.a.b));
                System.arraycopy(this.a.a, this.a.b, bArr, i, min);
                i += min;
                this.a.b += min;
                if (this.a.b == this.a.c) {
                    Segment segment = this.a;
                    this.a = segment.a();
                    SegmentPool.a.a(segment);
                }
            } else {
                this.b -= j;
                return bArr;
            }
        }
    }

    public final void c(long j) {
        Util.a(this.b, 0, j);
        this.b -= j;
        while (j > 0) {
            int min = (int) Math.min(j, (long) (this.a.c - this.a.b));
            j -= (long) min;
            this.a.b += min;
            if (this.a.b == this.a.c) {
                Segment segment = this.a;
                this.a = segment.a();
                SegmentPool.a.a(segment);
            }
        }
    }

    public final Buffer a(byte[] bArr, int i, int i2) {
        Segment segment;
        if (bArr == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = (long) i2;
        Util.a((long) bArr.length, (long) i, j);
        int i3 = i2 + i;
        while (i < i3) {
            if (this.a == null) {
                this.a = SegmentPool.a.a();
                Segment segment2 = this.a;
                Segment segment3 = this.a;
                segment = this.a;
                segment3.e = segment;
                segment2.d = segment;
            } else {
                Segment segment4 = this.a.e;
                if (segment4.c + 1 > 2048) {
                    Segment a2 = SegmentPool.a.a();
                    a2.e = segment4;
                    a2.d = segment4.d;
                    segment4.d.e = a2;
                    segment4.d = a2;
                    segment = a2;
                } else {
                    segment = segment4;
                }
            }
            int min = Math.min(i3 - i, 2048 - segment.c);
            System.arraycopy(bArr, i, segment.a, segment.c, min);
            i += min;
            segment.c += min;
        }
        this.b += j;
        return this;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Buffer)) {
            return false;
        }
        Buffer buffer = (Buffer) obj;
        if (this.b != buffer.b) {
            return false;
        }
        long j = 0;
        if (this.b == 0) {
            return true;
        }
        Segment segment = this.a;
        Segment segment2 = buffer.a;
        int i = segment.b;
        int i2 = segment2.b;
        while (j < this.b) {
            long min = (long) Math.min(segment.c - i, segment2.c - i2);
            int i3 = i2;
            int i4 = i;
            int i5 = 0;
            while (((long) i5) < min) {
                int i6 = i4 + 1;
                int i7 = i3 + 1;
                if (segment.a[i4] != segment2.a[i3]) {
                    return false;
                }
                i5++;
                i4 = i6;
                i3 = i7;
            }
            if (i4 == segment.c) {
                segment = segment.d;
                i = segment.b;
            } else {
                i = i4;
            }
            if (i3 == segment2.c) {
                segment2 = segment2.d;
                i2 = segment2.b;
            } else {
                i2 = i3;
            }
            j += min;
        }
        return true;
    }

    public final int hashCode() {
        Segment segment = this.a;
        if (segment == null) {
            return 0;
        }
        int i = 1;
        do {
            for (int i2 = segment.b; i2 < segment.c; i2++) {
                i = (i * 31) + segment.a[i2];
            }
            segment = segment.d;
        } while (segment != this.a);
        return i;
    }

    public final String toString() {
        if (this.b == 0) {
            return "Buffer[size=0]";
        }
        if (this.b <= 16) {
            return String.format("Buffer[size=%s data=%s]", new Object[]{Long.valueOf(this.b), clone().b(this.b).hex()});
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(this.a.a, this.a.b, this.a.c - this.a.b);
            Segment segment = this.a;
            while (true) {
                segment = segment.d;
                if (segment != this.a) {
                    instance.update(segment.a, segment.b, segment.c - segment.b);
                } else {
                    return String.format("Buffer[size=%s md5=%s]", new Object[]{Long.valueOf(this.b), ByteString.of(instance.digest()).hex()});
                }
            }
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public Buffer clone() {
        Buffer buffer = new Buffer();
        if (this.b == 0) {
            return buffer;
        }
        buffer.a(this.a.a, this.a.b, this.a.c - this.a.b);
        Segment segment = this.a;
        while (true) {
            segment = segment.d;
            if (segment == this.a) {
                return buffer;
            }
            buffer.a(segment.a, segment.b, segment.c - segment.b);
        }
    }

    public final long d() {
        long j;
        if (this.b < 8) {
            StringBuilder sb = new StringBuilder("size < 8: ");
            sb.append(this.b);
            throw new IllegalStateException(sb.toString());
        }
        Segment segment = this.a;
        int i = segment.b;
        int i2 = segment.c;
        if (i2 - i < 8) {
            j = ((((long) e()) & 4294967295L) << 32) | (4294967295L & ((long) e()));
        } else {
            byte[] bArr = segment.a;
            int i3 = i + 1;
            int i4 = i3 + 1;
            int i5 = i4 + 1;
            int i6 = i5 + 1;
            int i7 = i6 + 1;
            int i8 = i7 + 1;
            int i9 = i8 + 1;
            int i10 = i9 + 1;
            long j2 = (((long) bArr[i9]) & 255) | ((((long) bArr[i]) & 255) << 56) | ((((long) bArr[i3]) & 255) << 48) | ((((long) bArr[i4]) & 255) << 40) | ((((long) bArr[i5]) & 255) << 32) | ((((long) bArr[i6]) & 255) << 24) | ((((long) bArr[i7]) & 255) << 16) | ((((long) bArr[i8]) & 255) << 8);
            this.b -= 8;
            if (i10 == i2) {
                this.a = segment.a();
                SegmentPool.a.a(segment);
            } else {
                segment.b = i10;
            }
            j = j2;
        }
        return Util.a(j);
    }
}
