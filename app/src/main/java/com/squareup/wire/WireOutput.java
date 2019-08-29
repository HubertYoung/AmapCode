package com.squareup.wire;

import java.io.IOException;

public final class WireOutput {
    private final byte[] buffer;
    private final int limit;
    private int position;

    static int varint32Size(int i) {
        if ((i & -128) == 0) {
            return 1;
        }
        if ((i & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & i) == 0) {
            return 3;
        }
        return (i & -268435456) == 0 ? 4 : 5;
    }

    static int varint64Size(long j) {
        if ((-128 & j) == 0) {
            return 1;
        }
        if ((-16384 & j) == 0) {
            return 2;
        }
        if ((-2097152 & j) == 0) {
            return 3;
        }
        if ((-268435456 & j) == 0) {
            return 4;
        }
        if ((-34359738368L & j) == 0) {
            return 5;
        }
        if ((-4398046511104L & j) == 0) {
            return 6;
        }
        if ((-562949953421312L & j) == 0) {
            return 7;
        }
        if ((-72057594037927936L & j) == 0) {
            return 8;
        }
        return (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    static int zigZag32(int i) {
        return (i >> 31) ^ (i << 1);
    }

    static long zigZag64(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public static int int32Size(int i) {
        if (i >= 0) {
            return varint32Size(i);
        }
        return 10;
    }

    public static int int64Size(long j) {
        if (j >= 0) {
            return varint64Size(j);
        }
        return 10;
    }

    public static int tagSize(int i, WireType wireType) {
        return int32Size(makeTag(i, wireType));
    }

    public static int messageSize(int i, int i2) {
        return tagSize(i, WireType.LENGTH_DELIMITED) + int32Size(i2) + i2;
    }

    public static int writeTag(int i, WireType wireType, byte[] bArr, int i2) {
        return writeVarint((long) makeTag(i, wireType), bArr, i2);
    }

    public static int writeVarint(long j, byte[] bArr, int i) {
        int i2 = i;
        while ((-128 & j) != 0) {
            bArr[i2] = (byte) ((int) ((127 & j) | 128));
            j >>>= 7;
            i2++;
        }
        bArr[i2] = (byte) ((int) j);
        return (i2 + 1) - i;
    }

    public static int messageHeaderSize(int i, int i2) {
        return tagSize(i, WireType.LENGTH_DELIMITED) + int32Size(i2);
    }

    public static int writeMessageHeader(int i, byte[] bArr, int i2, int i3) {
        int writeTag = writeTag(i, WireType.LENGTH_DELIMITED, bArr, i2) + i2;
        return (writeTag + writeVarint((long) i3, bArr, writeTag)) - i2;
    }

    public static int makeTag(int i, WireType wireType) {
        return (i << 3) | wireType.value();
    }

    private WireOutput(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.position = i;
        this.limit = i + i2;
    }

    static WireOutput newInstance(byte[] bArr) {
        return newInstance(bArr, 0, bArr.length);
    }

    static WireOutput newInstance(byte[] bArr, int i, int i2) {
        return new WireOutput(bArr, i, i2);
    }

    static int varintTagSize(int i) {
        return varint32Size(makeTag(i, WireType.VARINT));
    }

    /* access modifiers changed from: 0000 */
    public final void writeRawByte(byte b) throws IOException {
        if (this.position == this.limit) {
            StringBuilder sb = new StringBuilder("Out of space: position=");
            sb.append(this.position);
            sb.append(", limit=");
            sb.append(this.limit);
            throw new IOException(sb.toString());
        }
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        bArr[i] = b;
    }

    /* access modifiers changed from: 0000 */
    public final void writeRawByte(int i) throws IOException {
        writeRawByte((byte) i);
    }

    /* access modifiers changed from: 0000 */
    public final void writeRawBytes(byte[] bArr) throws IOException {
        writeRawBytes(bArr, 0, bArr.length);
    }

    /* access modifiers changed from: 0000 */
    public final void writeRawBytes(byte[] bArr, int i, int i2) throws IOException {
        if (this.limit - this.position >= i2) {
            System.arraycopy(bArr, i, this.buffer, this.position, i2);
            this.position += i2;
            return;
        }
        StringBuilder sb = new StringBuilder("Out of space: position=");
        sb.append(this.position);
        sb.append(", limit=");
        sb.append(this.limit);
        throw new IOException(sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public final void writeTag(int i, WireType wireType) throws IOException {
        writeVarint32(makeTag(i, wireType));
    }

    /* access modifiers changed from: 0000 */
    public final void writeSignedVarint32(int i) throws IOException {
        if (i >= 0) {
            writeVarint32(i);
        } else {
            writeVarint64((long) i);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void writeVarint32(int i) throws IOException {
        while ((i & -128) != 0) {
            writeRawByte((i & 127) | 128);
            i >>>= 7;
        }
        writeRawByte(i);
    }

    /* access modifiers changed from: 0000 */
    public final void writeVarint64(long j) throws IOException {
        while ((-128 & j) != 0) {
            writeRawByte((((int) j) & 127) | 128);
            j >>>= 7;
        }
        writeRawByte((int) j);
    }

    /* access modifiers changed from: 0000 */
    public final void writeFixed32(int i) throws IOException {
        writeRawByte(i & 255);
        writeRawByte((i >> 8) & 255);
        writeRawByte((i >> 16) & 255);
        writeRawByte((i >> 24) & 255);
    }

    /* access modifiers changed from: 0000 */
    public final void writeFixed64(long j) throws IOException {
        writeRawByte(((int) j) & 255);
        writeRawByte(((int) (j >> 8)) & 255);
        writeRawByte(((int) (j >> 16)) & 255);
        writeRawByte(((int) (j >> 24)) & 255);
        writeRawByte(((int) (j >> 32)) & 255);
        writeRawByte(((int) (j >> 40)) & 255);
        writeRawByte(((int) (j >> 48)) & 255);
        writeRawByte(((int) (j >> 56)) & 255);
    }
}
