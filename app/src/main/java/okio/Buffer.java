package okio;

import com.standardar.common.IMUReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Buffer implements Cloneable, BufferedSink, BufferedSource {
    private static final byte[] DIGITS = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    static final int REPLACEMENT_CHARACTER = 65533;
    Segment head;
    long size;

    public final Buffer buffer() {
        return this;
    }

    public final void close() {
    }

    public final BufferedSink emit() {
        return this;
    }

    public final Buffer emitCompleteSegments() {
        return this;
    }

    public final void flush() {
    }

    public final long size() {
        return this.size;
    }

    public final OutputStream outputStream() {
        return new OutputStream() {
            public void close() {
            }

            public void flush() {
            }

            public void write(int i) {
                Buffer.this.writeByte((int) (byte) i);
            }

            public void write(byte[] bArr, int i, int i2) {
                Buffer.this.write(bArr, i, i2);
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(".outputStream()");
                return sb.toString();
            }
        };
    }

    public final boolean exhausted() {
        return this.size == 0;
    }

    public final void require(long j) throws EOFException {
        if (this.size < j) {
            throw new EOFException();
        }
    }

    public final boolean request(long j) {
        return this.size >= j;
    }

    public final InputStream inputStream() {
        return new InputStream() {
            public void close() {
            }

            public int read() {
                if (Buffer.this.size > 0) {
                    return Buffer.this.readByte() & 255;
                }
                return -1;
            }

            public int read(byte[] bArr, int i, int i2) {
                return Buffer.this.read(bArr, i, i2);
            }

            public int available() {
                return (int) Math.min(Buffer.this.size, 2147483647L);
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(Buffer.this);
                sb.append(".inputStream()");
                return sb.toString();
            }
        };
    }

    public final Buffer copyTo(OutputStream outputStream) throws IOException {
        return copyTo(outputStream, 0, this.size);
    }

    public final Buffer copyTo(OutputStream outputStream, long j, long j2) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, j, j2);
        if (j2 == 0) {
            return this;
        }
        Segment segment = this.head;
        while (j >= ((long) (segment.limit - segment.pos))) {
            j -= (long) (segment.limit - segment.pos);
            segment = segment.next;
        }
        while (j2 > 0) {
            int i = (int) (((long) segment.pos) + j);
            int min = (int) Math.min((long) (segment.limit - i), j2);
            outputStream.write(segment.data, i, min);
            j2 -= (long) min;
            segment = segment.next;
            j = 0;
        }
        return this;
    }

    public final Buffer copyTo(Buffer buffer, long j, long j2) {
        if (buffer == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, j, j2);
        if (j2 == 0) {
            return this;
        }
        buffer.size += j2;
        Segment segment = this.head;
        while (j >= ((long) (segment.limit - segment.pos))) {
            j -= (long) (segment.limit - segment.pos);
            segment = segment.next;
        }
        while (j2 > 0) {
            Segment segment2 = new Segment(segment);
            segment2.pos = (int) (((long) segment2.pos) + j);
            segment2.limit = Math.min(segment2.pos + ((int) j2), segment2.limit);
            if (buffer.head == null) {
                segment2.prev = segment2;
                segment2.next = segment2;
                buffer.head = segment2;
            } else {
                buffer.head.prev.push(segment2);
            }
            j2 -= (long) (segment2.limit - segment2.pos);
            segment = segment.next;
            j = 0;
        }
        return this;
    }

    public final Buffer writeTo(OutputStream outputStream) throws IOException {
        return writeTo(outputStream, this.size);
    }

    public final Buffer writeTo(OutputStream outputStream, long j) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, 0, j);
        Segment segment = this.head;
        while (j > 0) {
            int min = (int) Math.min(j, (long) (segment.limit - segment.pos));
            outputStream.write(segment.data, segment.pos, min);
            segment.pos += min;
            long j2 = (long) min;
            this.size -= j2;
            j -= j2;
            if (segment.pos == segment.limit) {
                Segment pop = segment.pop();
                this.head = pop;
                SegmentPool.recycle(segment);
                segment = pop;
            }
        }
        return this;
    }

    public final Buffer readFrom(InputStream inputStream) throws IOException {
        readFrom(inputStream, Long.MAX_VALUE, true);
        return this;
    }

    public final Buffer readFrom(InputStream inputStream, long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: ".concat(String.valueOf(j)));
        }
        readFrom(inputStream, j, false);
        return this;
    }

    private void readFrom(InputStream inputStream, long j, boolean z) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        while (true) {
            if (j > 0 || z) {
                Segment writableSegment = writableSegment(1);
                int read = inputStream.read(writableSegment.data, writableSegment.limit, (int) Math.min(j, (long) (8192 - writableSegment.limit)));
                if (read != -1) {
                    writableSegment.limit += read;
                    long j2 = (long) read;
                    this.size += j2;
                    j -= j2;
                } else if (!z) {
                    throw new EOFException();
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public final long completeSegmentByteCount() {
        long j = this.size;
        if (j == 0) {
            return 0;
        }
        Segment segment = this.head.prev;
        if (segment.limit < 8192 && segment.owner) {
            j -= (long) (segment.limit - segment.pos);
        }
        return j;
    }

    public final byte readByte() {
        if (this.size == 0) {
            throw new IllegalStateException("size == 0");
        }
        Segment segment = this.head;
        int i = segment.pos;
        int i2 = segment.limit;
        int i3 = i + 1;
        byte b = segment.data[i];
        this.size--;
        if (i3 == i2) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = i3;
        }
        return b;
    }

    public final byte getByte(long j) {
        Util.checkOffsetAndCount(this.size, j, 1);
        Segment segment = this.head;
        while (true) {
            long j2 = (long) (segment.limit - segment.pos);
            if (j < j2) {
                return segment.data[segment.pos + ((int) j)];
            }
            j -= j2;
            segment = segment.next;
        }
    }

    public final short readShort() {
        if (this.size < 2) {
            StringBuilder sb = new StringBuilder("size < 2: ");
            sb.append(this.size);
            throw new IllegalStateException(sb.toString());
        }
        Segment segment = this.head;
        int i = segment.pos;
        int i2 = segment.limit;
        if (i2 - i < 2) {
            return (short) (((readByte() & 255) << 8) | (readByte() & 255));
        }
        byte[] bArr = segment.data;
        int i3 = i + 1;
        int i4 = i3 + 1;
        byte b = ((bArr[i] & 255) << 8) | (bArr[i3] & 255);
        this.size -= 2;
        if (i4 == i2) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = i4;
        }
        return (short) b;
    }

    public final int readInt() {
        if (this.size < 4) {
            StringBuilder sb = new StringBuilder("size < 4: ");
            sb.append(this.size);
            throw new IllegalStateException(sb.toString());
        }
        Segment segment = this.head;
        int i = segment.pos;
        int i2 = segment.limit;
        if (i2 - i < 4) {
            return ((readByte() & 255) << 24) | ((readByte() & 255) << 16) | ((readByte() & 255) << 8) | (readByte() & 255);
        }
        byte[] bArr = segment.data;
        int i3 = i + 1;
        int i4 = i3 + 1;
        byte b = ((bArr[i] & 255) << 24) | ((bArr[i3] & 255) << 16);
        int i5 = i4 + 1;
        byte b2 = b | ((bArr[i4] & 255) << 8);
        int i6 = i5 + 1;
        byte b3 = b2 | (bArr[i5] & 255);
        this.size -= 4;
        if (i6 == i2) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = i6;
        }
        return b3;
    }

    public final long readLong() {
        if (this.size < 8) {
            StringBuilder sb = new StringBuilder("size < 8: ");
            sb.append(this.size);
            throw new IllegalStateException(sb.toString());
        }
        Segment segment = this.head;
        int i = segment.pos;
        int i2 = segment.limit;
        if (i2 - i < 8) {
            return ((((long) readInt()) & 4294967295L) << 32) | (4294967295L & ((long) readInt()));
        }
        byte[] bArr = segment.data;
        int i3 = i + 1;
        int i4 = i3 + 1;
        int i5 = i4 + 1;
        int i6 = i5 + 1;
        int i7 = i6 + 1;
        int i8 = i7 + 1;
        int i9 = i8 + 1;
        int i10 = i9 + 1;
        long j = (((long) bArr[i9]) & 255) | ((((long) bArr[i]) & 255) << 56) | ((((long) bArr[i3]) & 255) << 48) | ((((long) bArr[i4]) & 255) << 40) | ((((long) bArr[i5]) & 255) << 32) | ((((long) bArr[i6]) & 255) << 24) | ((((long) bArr[i7]) & 255) << 16) | ((((long) bArr[i8]) & 255) << 8);
        this.size -= 8;
        if (i10 == i2) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = i10;
        }
        return j;
    }

    public final short readShortLe() {
        return Util.reverseBytesShort(readShort());
    }

    public final int readIntLe() {
        return Util.reverseBytesInt(readInt());
    }

    public final long readLongLe() {
        return Util.reverseBytesLong(readLong());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004f, code lost:
        r2 = new okio.Buffer().writeDecimalLong(r6).writeByte((int) r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005c, code lost:
        if (r1 != false) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005e, code lost:
        r2.readByte();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0061, code lost:
        r3 = new java.lang.StringBuilder("Number too large: ");
        r3.append(r2.readUtf8());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0078, code lost:
        throw new java.lang.NumberFormatException(r3.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00ae, code lost:
        if (r10 != r11) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b0, code lost:
        r8 = r17;
        r0.head = r8.pop();
        okio.SegmentPool.recycle(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00bc, code lost:
        r17.pos = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00c0, code lost:
        if (r4 != false) goto L_0x00c6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0092 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final long readDecimalLong() {
        /*
            r19 = this;
            r0 = r19
            long r1 = r0.size
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 != 0) goto L_0x0012
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "size == 0"
            r1.<init>(r2)
            throw r1
        L_0x0012:
            r1 = -7
            r5 = 0
            r6 = r3
            r4 = 0
            r2 = r1
            r1 = 0
        L_0x0019:
            okio.Segment r8 = r0.head
            byte[] r9 = r8.data
            int r10 = r8.pos
            int r11 = r8.limit
        L_0x0021:
            r12 = 1
            if (r10 >= r11) goto L_0x00ac
            byte r13 = r9[r10]
            r14 = 48
            if (r13 < r14) goto L_0x0079
            r15 = 57
            if (r13 > r15) goto L_0x0079
            int r14 = r14 - r13
            r15 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r12 = (r6 > r15 ? 1 : (r6 == r15 ? 0 : -1))
            if (r12 < 0) goto L_0x004f
            if (r12 != 0) goto L_0x0044
            r17 = r8
            r18 = r9
            long r8 = (long) r14
            int r8 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r8 >= 0) goto L_0x0048
            goto L_0x004f
        L_0x0044:
            r17 = r8
            r18 = r9
        L_0x0048:
            r8 = 10
            long r6 = r6 * r8
            long r8 = (long) r14
            long r6 = r6 + r8
            goto L_0x0087
        L_0x004f:
            okio.Buffer r2 = new okio.Buffer
            r2.<init>()
            okio.Buffer r2 = r2.writeDecimalLong(r6)
            okio.Buffer r2 = r2.writeByte(r13)
            if (r1 != 0) goto L_0x0061
            r2.readByte()
        L_0x0061:
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Number too large: "
            r3.<init>(r4)
            java.lang.String r2 = r2.readUtf8()
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            r1.<init>(r2)
            throw r1
        L_0x0079:
            r17 = r8
            r18 = r9
            r8 = 45
            if (r13 != r8) goto L_0x0090
            if (r5 != 0) goto L_0x0090
            r8 = 1
            long r2 = r2 - r8
            r1 = 1
        L_0x0087:
            int r10 = r10 + 1
            int r5 = r5 + 1
            r8 = r17
            r9 = r18
            goto L_0x0021
        L_0x0090:
            if (r5 != 0) goto L_0x00aa
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Expected leading [0-9] or '-' character but was 0x"
            r2.<init>(r3)
            java.lang.String r3 = java.lang.Integer.toHexString(r13)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x00aa:
            r4 = 1
            goto L_0x00ae
        L_0x00ac:
            r17 = r8
        L_0x00ae:
            if (r10 != r11) goto L_0x00bc
            r8 = r17
            okio.Segment r9 = r8.pop()
            r0.head = r9
            okio.SegmentPool.recycle(r8)
            goto L_0x00c0
        L_0x00bc:
            r8 = r17
            r8.pos = r10
        L_0x00c0:
            if (r4 != 0) goto L_0x00c6
            okio.Segment r8 = r0.head
            if (r8 != 0) goto L_0x0019
        L_0x00c6:
            long r2 = r0.size
            long r4 = (long) r5
            long r2 = r2 - r4
            r0.size = r2
            if (r1 == 0) goto L_0x00cf
            return r6
        L_0x00cf:
            long r1 = -r6
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Buffer.readDecimalLong():long");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0093, code lost:
        if (r8 != r9) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0095, code lost:
        r14.head = r6.pop();
        okio.SegmentPool.recycle(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x009f, code lost:
        r6.pos = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a1, code lost:
        if (r1 != false) goto L_0x00a7;
     */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x007a A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final long readHexadecimalUnsignedLong() {
        /*
            r14 = this;
            long r0 = r14.size
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto L_0x0010
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "size == 0"
            r0.<init>(r1)
            throw r0
        L_0x0010:
            r0 = 0
            r4 = r2
            r1 = 0
        L_0x0013:
            okio.Segment r6 = r14.head
            byte[] r7 = r6.data
            int r8 = r6.pos
            int r9 = r6.limit
        L_0x001b:
            if (r8 >= r9) goto L_0x0093
            byte r10 = r7[r8]
            r11 = 48
            if (r10 < r11) goto L_0x002a
            r11 = 57
            if (r10 > r11) goto L_0x002a
            int r11 = r10 + -48
            goto L_0x0043
        L_0x002a:
            r11 = 97
            if (r10 < r11) goto L_0x0037
            r11 = 102(0x66, float:1.43E-43)
            if (r10 > r11) goto L_0x0037
            int r11 = r10 + -97
            int r11 = r11 + 10
            goto L_0x0043
        L_0x0037:
            r11 = 65
            if (r10 < r11) goto L_0x0078
            r11 = 70
            if (r10 > r11) goto L_0x0078
            int r11 = r10 + -65
            int r11 = r11 + 10
        L_0x0043:
            r12 = -1152921504606846976(0xf000000000000000, double:-3.105036184601418E231)
            long r12 = r12 & r4
            int r12 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r12 == 0) goto L_0x006f
            okio.Buffer r0 = new okio.Buffer
            r0.<init>()
            okio.Buffer r0 = r0.writeHexadecimalUnsignedLong(r4)
            okio.Buffer r0 = r0.writeByte(r10)
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Number too large: "
            r2.<init>(r3)
            java.lang.String r0 = r0.readUtf8()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        L_0x006f:
            r10 = 4
            long r4 = r4 << r10
            long r10 = (long) r11
            long r4 = r4 | r10
            int r8 = r8 + 1
            int r0 = r0 + 1
            goto L_0x001b
        L_0x0078:
            if (r0 != 0) goto L_0x0092
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Expected leading [0-9a-fA-F] character but was 0x"
            r1.<init>(r2)
            java.lang.String r2 = java.lang.Integer.toHexString(r10)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0092:
            r1 = 1
        L_0x0093:
            if (r8 != r9) goto L_0x009f
            okio.Segment r7 = r6.pop()
            r14.head = r7
            okio.SegmentPool.recycle(r6)
            goto L_0x00a1
        L_0x009f:
            r6.pos = r8
        L_0x00a1:
            if (r1 != 0) goto L_0x00a7
            okio.Segment r6 = r14.head
            if (r6 != 0) goto L_0x0013
        L_0x00a7:
            long r1 = r14.size
            long r6 = (long) r0
            long r1 = r1 - r6
            r14.size = r1
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Buffer.readHexadecimalUnsignedLong():long");
    }

    public final ByteString readByteString() {
        return new ByteString(readByteArray());
    }

    public final ByteString readByteString(long j) throws EOFException {
        return new ByteString(readByteArray(j));
    }

    public final void readFully(Buffer buffer, long j) throws EOFException {
        if (this.size < j) {
            buffer.write(this, this.size);
            throw new EOFException();
        } else {
            buffer.write(this, j);
        }
    }

    public final long readAll(Sink sink) throws IOException {
        long j = this.size;
        if (j > 0) {
            sink.write(this, j);
        }
        return j;
    }

    public final String readUtf8() {
        try {
            return readString(this.size, Util.UTF_8);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final String readUtf8(long j) throws EOFException {
        return readString(j, Util.UTF_8);
    }

    public final String readString(Charset charset) {
        try {
            return readString(this.size, charset);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final String readString(long j, Charset charset) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0, j);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (j > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: ".concat(String.valueOf(j)));
        } else if (j == 0) {
            return "";
        } else {
            Segment segment = this.head;
            if (((long) segment.pos) + j > ((long) segment.limit)) {
                return new String(readByteArray(j), charset);
            }
            String str = new String(segment.data, segment.pos, (int) j, charset);
            segment.pos = (int) (((long) segment.pos) + j);
            this.size -= j;
            if (segment.pos == segment.limit) {
                this.head = segment.pop();
                SegmentPool.recycle(segment);
            }
            return str;
        }
    }

    public final String readUtf8Line() throws EOFException {
        long indexOf = indexOf(10);
        if (indexOf != -1) {
            return readUtf8Line(indexOf);
        }
        if (this.size != 0) {
            return readUtf8(this.size);
        }
        return null;
    }

    public final String readUtf8LineStrict() throws EOFException {
        long indexOf = indexOf(10);
        if (indexOf != -1) {
            return readUtf8Line(indexOf);
        }
        Buffer buffer = new Buffer();
        copyTo(buffer, 0, Math.min(32, this.size));
        StringBuilder sb = new StringBuilder("\\n not found: size=");
        sb.append(size());
        sb.append(" content=");
        sb.append(buffer.readByteString().hex());
        sb.append("...");
        throw new EOFException(sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public final String readUtf8Line(long j) throws EOFException {
        if (j > 0) {
            long j2 = j - 1;
            if (getByte(j2) == 13) {
                String readUtf8 = readUtf8(j2);
                skip(2);
                return readUtf8;
            }
        }
        String readUtf82 = readUtf8(j);
        skip(1);
        return readUtf82;
    }

    public final int readUtf8CodePoint() throws EOFException {
        byte b;
        int i;
        byte b2;
        if (this.size == 0) {
            throw new EOFException();
        }
        byte b3 = getByte(0);
        int i2 = 1;
        if ((b3 & 128) == 0) {
            b2 = b3 & Byte.MAX_VALUE;
            i = 1;
            b = 0;
        } else if ((b3 & 224) == 192) {
            b2 = b3 & 31;
            i = 2;
            b = 128;
        } else if ((b3 & 240) == 224) {
            b2 = b3 & 15;
            i = 3;
            b = 2048;
        } else if ((b3 & 248) == 240) {
            b2 = b3 & 7;
            i = 4;
            b = 65536;
        } else {
            skip(1);
            return REPLACEMENT_CHARACTER;
        }
        long j = (long) i;
        if (this.size < j) {
            StringBuilder sb = new StringBuilder("size < ");
            sb.append(i);
            sb.append(": ");
            sb.append(this.size);
            sb.append(" (to read code point prefixed 0x");
            sb.append(Integer.toHexString(b3));
            sb.append(")");
            throw new EOFException(sb.toString());
        }
        while (i2 < i) {
            long j2 = (long) i2;
            byte b4 = getByte(j2);
            if ((b4 & 192) == 128) {
                b2 = (b2 << 6) | (b4 & 63);
                i2++;
            } else {
                skip(j2);
                return REPLACEMENT_CHARACTER;
            }
        }
        skip(j);
        if (b2 > 1114111) {
            return REPLACEMENT_CHARACTER;
        }
        if ((b2 < 55296 || b2 > 57343) && b2 >= b) {
            return b2;
        }
        return REPLACEMENT_CHARACTER;
    }

    public final byte[] readByteArray() {
        try {
            return readByteArray(this.size);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final byte[] readByteArray(long j) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0, j);
        if (j > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: ".concat(String.valueOf(j)));
        }
        byte[] bArr = new byte[((int) j)];
        readFully(bArr);
        return bArr;
    }

    public final int read(byte[] bArr) {
        return read(bArr, 0, bArr.length);
    }

    public final void readFully(byte[] bArr) throws EOFException {
        int i = 0;
        while (i < bArr.length) {
            int read = read(bArr, i, bArr.length - i);
            if (read == -1) {
                throw new EOFException();
            }
            i += read;
        }
    }

    public final int read(byte[] bArr, int i, int i2) {
        Util.checkOffsetAndCount((long) bArr.length, (long) i, (long) i2);
        Segment segment = this.head;
        if (segment == null) {
            return -1;
        }
        int min = Math.min(i2, segment.limit - segment.pos);
        System.arraycopy(segment.data, segment.pos, bArr, i, min);
        segment.pos += min;
        this.size -= (long) min;
        if (segment.pos == segment.limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return min;
    }

    public final void clear() {
        try {
            skip(this.size);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final void skip(long j) throws EOFException {
        while (j > 0) {
            if (this.head == null) {
                throw new EOFException();
            }
            int min = (int) Math.min(j, (long) (this.head.limit - this.head.pos));
            long j2 = (long) min;
            this.size -= j2;
            j -= j2;
            this.head.pos += min;
            if (this.head.pos == this.head.limit) {
                Segment segment = this.head;
                this.head = segment.pop();
                SegmentPool.recycle(segment);
            }
        }
    }

    public final Buffer write(ByteString byteString) {
        if (byteString == null) {
            throw new IllegalArgumentException("byteString == null");
        }
        byteString.write(this);
        return this;
    }

    public final Buffer writeUtf8(String str) {
        return writeUtf8(str, 0, str.length());
    }

    public final Buffer writeUtf8(String str, int i, int i2) {
        char c;
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        } else if (i < 0) {
            throw new IllegalAccessError("beginIndex < 0: ".concat(String.valueOf(i)));
        } else if (i2 < i) {
            StringBuilder sb = new StringBuilder("endIndex < beginIndex: ");
            sb.append(i2);
            sb.append(" < ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 > str.length()) {
            StringBuilder sb2 = new StringBuilder("endIndex > string.length: ");
            sb2.append(i2);
            sb2.append(" > ");
            sb2.append(str.length());
            throw new IllegalArgumentException(sb2.toString());
        } else {
            while (i < i2) {
                char charAt = str.charAt(i);
                if (charAt < 128) {
                    Segment writableSegment = writableSegment(1);
                    byte[] bArr = writableSegment.data;
                    int i3 = writableSegment.limit - i;
                    int min = Math.min(i2, 8192 - i3);
                    int i4 = i + 1;
                    bArr[i + i3] = (byte) charAt;
                    while (true) {
                        i = i4;
                        if (i >= min) {
                            break;
                        }
                        char charAt2 = str.charAt(i);
                        if (charAt2 >= 128) {
                            break;
                        }
                        i4 = i + 1;
                        bArr[i + i3] = (byte) charAt2;
                    }
                    int i5 = (i3 + i) - writableSegment.limit;
                    writableSegment.limit += i5;
                    this.size += (long) i5;
                } else if (charAt < 2048) {
                    writeByte((charAt >> 6) | 192);
                    writeByte((int) (charAt & '?') | 128);
                    i++;
                } else if (charAt < 55296 || charAt > 57343) {
                    writeByte((charAt >> 12) | 224);
                    writeByte(((charAt >> 6) & 63) | 128);
                    writeByte((int) (charAt & '?') | 128);
                    i++;
                } else {
                    int i6 = i + 1;
                    if (i6 < i2) {
                        c = str.charAt(i6);
                    } else {
                        c = 0;
                    }
                    if (charAt > 56319 || c < 56320 || c > 57343) {
                        writeByte(63);
                        i = i6;
                    } else {
                        int i7 = (((charAt & 10239) << 10) | (9215 & c)) + 0;
                        writeByte((i7 >> 18) | 240);
                        writeByte(((i7 >> 12) & 63) | 128);
                        writeByte(((i7 >> 6) & 63) | 128);
                        writeByte((i7 & 63) | 128);
                        i += 2;
                    }
                }
            }
            return this;
        }
    }

    public final Buffer writeUtf8CodePoint(int i) {
        if (i < 128) {
            writeByte(i);
        } else if (i < 2048) {
            writeByte((i >> 6) | 192);
            writeByte((i & 63) | 128);
        } else if (i < 65536) {
            if (i < 55296 || i > 57343) {
                writeByte((i >> 12) | 224);
                writeByte(((i >> 6) & 63) | 128);
                writeByte((i & 63) | 128);
            } else {
                StringBuilder sb = new StringBuilder("Unexpected code point: ");
                sb.append(Integer.toHexString(i));
                throw new IllegalArgumentException(sb.toString());
            }
        } else if (i <= 1114111) {
            writeByte((i >> 18) | 240);
            writeByte(((i >> 12) & 63) | 128);
            writeByte(((i >> 6) & 63) | 128);
            writeByte((i & 63) | 128);
        } else {
            StringBuilder sb2 = new StringBuilder("Unexpected code point: ");
            sb2.append(Integer.toHexString(i));
            throw new IllegalArgumentException(sb2.toString());
        }
        return this;
    }

    public final Buffer writeString(String str, Charset charset) {
        return writeString(str, 0, str.length(), charset);
    }

    public final Buffer writeString(String str, int i, int i2, Charset charset) {
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        } else if (i < 0) {
            throw new IllegalAccessError("beginIndex < 0: ".concat(String.valueOf(i)));
        } else if (i2 < i) {
            StringBuilder sb = new StringBuilder("endIndex < beginIndex: ");
            sb.append(i2);
            sb.append(" < ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 > str.length()) {
            StringBuilder sb2 = new StringBuilder("endIndex > string.length: ");
            sb2.append(i2);
            sb2.append(" > ");
            sb2.append(str.length());
            throw new IllegalArgumentException(sb2.toString());
        } else if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (charset.equals(Util.UTF_8)) {
            return writeUtf8(str);
        } else {
            byte[] bytes = str.substring(i, i2).getBytes(charset);
            return write(bytes, 0, bytes.length);
        }
    }

    public final Buffer write(byte[] bArr) {
        if (bArr != null) {
            return write(bArr, 0, bArr.length);
        }
        throw new IllegalArgumentException("source == null");
    }

    public final Buffer write(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = (long) i2;
        Util.checkOffsetAndCount((long) bArr.length, (long) i, j);
        int i3 = i2 + i;
        while (i < i3) {
            Segment writableSegment = writableSegment(1);
            int min = Math.min(i3 - i, 8192 - writableSegment.limit);
            System.arraycopy(bArr, i, writableSegment.data, writableSegment.limit, min);
            i += min;
            writableSegment.limit += min;
        }
        this.size += j;
        return this;
    }

    public final long writeAll(Source source) throws IOException {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = 0;
        while (true) {
            long read = source.read(this, 8192);
            if (read == -1) {
                return j;
            }
            j += read;
        }
    }

    public final BufferedSink write(Source source, long j) throws IOException {
        while (j > 0) {
            long read = source.read(this, j);
            if (read == -1) {
                throw new EOFException();
            }
            j -= read;
        }
        return this;
    }

    public final Buffer writeByte(int i) {
        Segment writableSegment = writableSegment(1);
        byte[] bArr = writableSegment.data;
        int i2 = writableSegment.limit;
        writableSegment.limit = i2 + 1;
        bArr[i2] = (byte) i;
        this.size++;
        return this;
    }

    public final Buffer writeShort(int i) {
        Segment writableSegment = writableSegment(2);
        byte[] bArr = writableSegment.data;
        int i2 = writableSegment.limit;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 8) & 255);
        bArr[i3] = (byte) (i & 255);
        writableSegment.limit = i3 + 1;
        this.size += 2;
        return this;
    }

    public final Buffer writeShortLe(int i) {
        return writeShort((int) Util.reverseBytesShort((short) i));
    }

    public final Buffer writeInt(int i) {
        Segment writableSegment = writableSegment(4);
        byte[] bArr = writableSegment.data;
        int i2 = writableSegment.limit;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 24) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i >>> 16) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((i >>> 8) & 255);
        bArr[i5] = (byte) (i & 255);
        writableSegment.limit = i5 + 1;
        this.size += 4;
        return this;
    }

    public final Buffer writeIntLe(int i) {
        return writeInt(Util.reverseBytesInt(i));
    }

    public final Buffer writeLong(long j) {
        Segment writableSegment = writableSegment(8);
        byte[] bArr = writableSegment.data;
        int i = writableSegment.limit;
        int i2 = i + 1;
        bArr[i] = (byte) ((int) ((j >>> 56) & 255));
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((int) ((j >>> 48) & 255));
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((int) ((j >>> 40) & 255));
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((int) ((j >>> 32) & 255));
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((int) ((j >>> 24) & 255));
        int i7 = i6 + 1;
        bArr[i6] = (byte) ((int) ((j >>> 16) & 255));
        int i8 = i7 + 1;
        bArr[i7] = (byte) ((int) ((j >>> 8) & 255));
        bArr[i8] = (byte) ((int) (j & 255));
        writableSegment.limit = i8 + 1;
        this.size += 8;
        return this;
    }

    public final Buffer writeLongLe(long j) {
        return writeLong(Util.reverseBytesLong(j));
    }

    public final Buffer writeDecimalLong(long j) {
        int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i == 0) {
            return writeByte(48);
        }
        boolean z = false;
        int i2 = 1;
        if (i < 0) {
            j = -j;
            if (j < 0) {
                return writeUtf8((String) "-9223372036854775808");
            }
            z = true;
        }
        if (j >= 100000000) {
            i2 = j < 1000000000000L ? j < 10000000000L ? j < IMUReader.NS_PER_SECOND ? 9 : 10 : j < 100000000000L ? 11 : 12 : j < 1000000000000000L ? j < 10000000000000L ? 13 : j < 100000000000000L ? 14 : 15 : j < 100000000000000000L ? j < 10000000000000000L ? 16 : 17 : j < 1000000000000000000L ? 18 : 19;
        } else if (j >= 10000) {
            i2 = j < 1000000 ? j < 100000 ? 5 : 6 : j < 10000000 ? 7 : 8;
        } else if (j >= 100) {
            i2 = j < 1000 ? 3 : 4;
        } else if (j >= 10) {
            i2 = 2;
        }
        if (z) {
            i2++;
        }
        Segment writableSegment = writableSegment(i2);
        byte[] bArr = writableSegment.data;
        int i3 = writableSegment.limit + i2;
        while (j != 0) {
            i3--;
            bArr[i3] = DIGITS[(int) (j % 10)];
            j /= 10;
        }
        if (z) {
            bArr[i3 - 1] = 45;
        }
        writableSegment.limit += i2;
        this.size += (long) i2;
        return this;
    }

    public final Buffer writeHexadecimalUnsignedLong(long j) {
        if (j == 0) {
            return writeByte(48);
        }
        int numberOfTrailingZeros = (Long.numberOfTrailingZeros(Long.highestOneBit(j)) / 4) + 1;
        Segment writableSegment = writableSegment(numberOfTrailingZeros);
        byte[] bArr = writableSegment.data;
        int i = writableSegment.limit;
        for (int i2 = (writableSegment.limit + numberOfTrailingZeros) - 1; i2 >= i; i2--) {
            bArr[i2] = DIGITS[(int) (15 & j)];
            j >>>= 4;
        }
        writableSegment.limit += numberOfTrailingZeros;
        this.size += (long) numberOfTrailingZeros;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public final Segment writableSegment(int i) {
        if (i <= 0 || i > 8192) {
            throw new IllegalArgumentException();
        } else if (this.head == null) {
            this.head = SegmentPool.take();
            Segment segment = this.head;
            Segment segment2 = this.head;
            Segment segment3 = this.head;
            segment2.prev = segment3;
            segment.next = segment3;
            return segment3;
        } else {
            Segment segment4 = this.head.prev;
            if (segment4.limit + i > 8192 || !segment4.owner) {
                segment4 = segment4.push(SegmentPool.take());
            }
            return segment4;
        }
    }

    public final void write(Buffer buffer, long j) {
        if (buffer == null) {
            throw new IllegalArgumentException("source == null");
        } else if (buffer == this) {
            throw new IllegalArgumentException("source == this");
        } else {
            Util.checkOffsetAndCount(buffer.size, 0, j);
            while (j > 0) {
                if (j < ((long) (buffer.head.limit - buffer.head.pos))) {
                    Segment segment = this.head != null ? this.head.prev : null;
                    if (segment != null && segment.owner) {
                        if ((((long) segment.limit) + j) - ((long) (segment.shared ? 0 : segment.pos)) <= 8192) {
                            buffer.head.writeTo(segment, (int) j);
                            buffer.size -= j;
                            this.size += j;
                            return;
                        }
                    }
                    buffer.head = buffer.head.split((int) j);
                }
                Segment segment2 = buffer.head;
                long j2 = (long) (segment2.limit - segment2.pos);
                buffer.head = segment2.pop();
                if (this.head == null) {
                    this.head = segment2;
                    Segment segment3 = this.head;
                    Segment segment4 = this.head;
                    Segment segment5 = this.head;
                    segment4.prev = segment5;
                    segment3.next = segment5;
                } else {
                    this.head.prev.push(segment2).compact();
                }
                buffer.size -= j2;
                this.size += j2;
                j -= j2;
            }
        }
    }

    public final long read(Buffer buffer, long j) {
        if (buffer == null) {
            throw new IllegalArgumentException("sink == null");
        } else if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: ".concat(String.valueOf(j)));
        } else if (this.size == 0) {
            return -1;
        } else {
            if (j > this.size) {
                j = this.size;
            }
            buffer.write(this, j);
            return j;
        }
    }

    public final long indexOf(byte b) {
        return indexOf(b, 0);
    }

    public final long indexOf(byte b, long j) {
        long j2;
        long j3 = 0;
        if (j < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        Segment segment = this.head;
        if (segment == null) {
            return -1;
        }
        if (this.size - j >= j) {
            while (true) {
                long j4 = ((long) (segment.limit - segment.pos)) + j2;
                if (j4 >= j) {
                    break;
                }
                segment = segment.next;
                j3 = j4;
            }
        } else {
            j2 = this.size;
            while (j2 > j) {
                segment = segment.prev;
                j2 -= (long) (segment.limit - segment.pos);
            }
        }
        while (j2 < this.size) {
            byte[] bArr = segment.data;
            int i = segment.limit;
            for (int i2 = (int) ((((long) segment.pos) + j) - j2); i2 < i; i2++) {
                if (bArr[i2] == b) {
                    return ((long) (i2 - segment.pos)) + j2;
                }
            }
            j = ((long) (segment.limit - segment.pos)) + j2;
            segment = segment.next;
            j2 = j;
        }
        return -1;
    }

    public final long indexOf(ByteString byteString) throws IOException {
        return indexOf(byteString, 0);
    }

    public final long indexOf(ByteString byteString, long j) throws IOException {
        long j2;
        byte[] bArr;
        Segment segment;
        Buffer buffer = this;
        if (byteString.size() == 0) {
            throw new IllegalArgumentException("bytes is empty");
        }
        long j3 = 0;
        if (j < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        Segment segment2 = buffer.head;
        long j4 = -1;
        if (segment2 == null) {
            return -1;
        }
        if (buffer.size - j >= j) {
            while (true) {
                long j5 = ((long) (segment2.limit - segment2.pos)) + j2;
                if (j5 >= j) {
                    break;
                }
                segment2 = segment2.next;
                j3 = j5;
            }
        } else {
            j2 = buffer.size;
            while (j2 > j) {
                segment2 = segment2.prev;
                j2 -= (long) (segment2.limit - segment2.pos);
            }
        }
        ByteString byteString2 = byteString;
        byte b = byteString2.getByte(0);
        int size2 = byteString.size();
        long j6 = (buffer.size - ((long) size2)) + 1;
        long j7 = j;
        long j8 = j2;
        Segment segment3 = segment2;
        while (j8 < j6) {
            byte[] bArr2 = segment3.data;
            int min = (int) Math.min((long) segment3.limit, (((long) segment3.pos) + j6) - j8);
            int i = (int) ((((long) segment3.pos) + j7) - j8);
            while (i < min) {
                if (bArr2[i] == b) {
                    Buffer buffer2 = buffer;
                    bArr = bArr2;
                    segment = segment3;
                    if (buffer2.rangeEquals(segment3, i + 1, byteString2, 1, size2)) {
                        return ((long) (i - segment.pos)) + j8;
                    }
                } else {
                    bArr = bArr2;
                    segment = segment3;
                }
                i++;
                segment3 = segment;
                bArr2 = bArr;
                buffer = this;
            }
            Segment segment4 = segment3;
            j7 = ((long) (segment4.limit - segment4.pos)) + j8;
            segment3 = segment4.next;
            j8 = j7;
            buffer = this;
            j4 = -1;
        }
        return j4;
    }

    public final long indexOfElement(ByteString byteString) {
        return indexOfElement(byteString, 0);
    }

    public final long indexOfElement(ByteString byteString, long j) {
        long j2;
        long j3 = 0;
        if (j < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        Segment segment = this.head;
        if (segment == null) {
            return -1;
        }
        if (this.size - j >= j) {
            while (true) {
                long j4 = ((long) (segment.limit - segment.pos)) + j2;
                if (j4 >= j) {
                    break;
                }
                segment = segment.next;
                j3 = j4;
            }
        } else {
            j2 = this.size;
            while (j2 > j) {
                segment = segment.prev;
                j2 -= (long) (segment.limit - segment.pos);
            }
        }
        if (byteString.size() == 2) {
            byte b = byteString.getByte(0);
            byte b2 = byteString.getByte(1);
            while (j2 < this.size) {
                byte[] bArr = segment.data;
                int i = segment.limit;
                for (int i2 = (int) ((((long) segment.pos) + j) - j2); i2 < i; i2++) {
                    byte b3 = bArr[i2];
                    if (b3 == b || b3 == b2) {
                        return ((long) (i2 - segment.pos)) + j2;
                    }
                }
                j = ((long) (segment.limit - segment.pos)) + j2;
                segment = segment.next;
                j2 = j;
            }
        } else {
            byte[] internalArray = byteString.internalArray();
            while (j2 < this.size) {
                byte[] bArr2 = segment.data;
                int i3 = segment.limit;
                for (int i4 = (int) ((((long) segment.pos) + j) - j2); i4 < i3; i4++) {
                    byte b4 = bArr2[i4];
                    for (byte b5 : internalArray) {
                        if (b4 == b5) {
                            return ((long) (i4 - segment.pos)) + j2;
                        }
                    }
                }
                j = ((long) (segment.limit - segment.pos)) + j2;
                segment = segment.next;
                j2 = j;
            }
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public final boolean rangeEquals(long j, ByteString byteString) {
        int size2 = byteString.size();
        if (this.size - j < ((long) size2)) {
            return false;
        }
        for (int i = 0; i < size2; i++) {
            if (getByte(((long) i) + j) != byteString.getByte(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean rangeEquals(Segment segment, int i, ByteString byteString, int i2, int i3) {
        int i4 = segment.limit;
        byte[] bArr = segment.data;
        while (i2 < i3) {
            if (i == i4) {
                segment = segment.next;
                byte[] bArr2 = segment.data;
                int i5 = segment.pos;
                bArr = bArr2;
                i = i5;
                i4 = segment.limit;
            }
            if (bArr[i] != byteString.getByte(i2)) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    public final Timeout timeout() {
        return Timeout.NONE;
    }

    /* access modifiers changed from: 0000 */
    public final List<Integer> segmentSizes() {
        if (this.head == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(this.head.limit - this.head.pos));
        Segment segment = this.head;
        while (true) {
            segment = segment.next;
            if (segment == this.head) {
                return arrayList;
            }
            arrayList.add(Integer.valueOf(segment.limit - segment.pos));
        }
    }

    public final ByteString md5() {
        return digest("MD5");
    }

    public final ByteString sha1() {
        return digest("SHA-1");
    }

    public final ByteString sha256() {
        return digest("SHA-256");
    }

    private ByteString digest(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.update(this.head.data, this.head.pos, this.head.limit - this.head.pos);
            Segment segment = this.head;
            while (true) {
                segment = segment.next;
                if (segment == this.head) {
                    return ByteString.of(instance.digest());
                }
                instance.update(segment.data, segment.pos, segment.limit - segment.pos);
            }
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Buffer)) {
            return false;
        }
        Buffer buffer = (Buffer) obj;
        if (this.size != buffer.size) {
            return false;
        }
        long j = 0;
        if (this.size == 0) {
            return true;
        }
        Segment segment = this.head;
        Segment segment2 = buffer.head;
        int i = segment.pos;
        int i2 = segment2.pos;
        while (j < this.size) {
            long min = (long) Math.min(segment.limit - i, segment2.limit - i2);
            int i3 = i2;
            int i4 = i;
            int i5 = 0;
            while (((long) i5) < min) {
                int i6 = i4 + 1;
                int i7 = i3 + 1;
                if (segment.data[i4] != segment2.data[i3]) {
                    return false;
                }
                i5++;
                i4 = i6;
                i3 = i7;
            }
            if (i4 == segment.limit) {
                segment = segment.next;
                i = segment.pos;
            } else {
                i = i4;
            }
            if (i3 == segment2.limit) {
                segment2 = segment2.next;
                i2 = segment2.pos;
            } else {
                i2 = i3;
            }
            j += min;
        }
        return true;
    }

    public final int hashCode() {
        Segment segment = this.head;
        if (segment == null) {
            return 0;
        }
        int i = 1;
        do {
            for (int i2 = segment.pos; i2 < segment.limit; i2++) {
                i = (i * 31) + segment.data[i2];
            }
            segment = segment.next;
        } while (segment != this.head);
        return i;
    }

    public final String toString() {
        if (this.size == 0) {
            return "Buffer[size=0]";
        }
        if (this.size <= 16) {
            return String.format("Buffer[size=%s data=%s]", new Object[]{Long.valueOf(this.size), clone().readByteString().hex()});
        }
        return String.format("Buffer[size=%s md5=%s]", new Object[]{Long.valueOf(this.size), md5().hex()});
    }

    public final Buffer clone() {
        Buffer buffer = new Buffer();
        if (this.size == 0) {
            return buffer;
        }
        buffer.head = new Segment(this.head);
        Segment segment = buffer.head;
        Segment segment2 = buffer.head;
        Segment segment3 = buffer.head;
        segment2.prev = segment3;
        segment.next = segment3;
        Segment segment4 = this.head;
        while (true) {
            segment4 = segment4.next;
            if (segment4 != this.head) {
                buffer.head.prev.push(new Segment(segment4));
            } else {
                buffer.size = this.size;
                return buffer;
            }
        }
    }

    public final ByteString snapshot() {
        if (this.size <= 2147483647L) {
            return snapshot((int) this.size);
        }
        StringBuilder sb = new StringBuilder("size > Integer.MAX_VALUE: ");
        sb.append(this.size);
        throw new IllegalArgumentException(sb.toString());
    }

    public final ByteString snapshot(int i) {
        if (i == 0) {
            return ByteString.EMPTY;
        }
        return new SegmentedByteString(this, i);
    }
}
